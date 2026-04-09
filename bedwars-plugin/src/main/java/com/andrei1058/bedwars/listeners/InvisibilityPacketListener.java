/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

/**
 * Autore: Giustino C. Miglionico con l'aiuto della tecnologia peruviana
 *
 * Logica di Implementazione:
 * FASE 1 - Intercettazione NAMED_SOUND_EFFECT (opzionale, da config)
 *   Obiettivo: Sopprimere i suoni di footstep per i giocatori invisibili nell'arena.
 *   Scelta tecnica: Intercetto il pacchetto NAMED_SOUND_EFFECT, controllo se la sorgente
 *   sonora coincide con un giocatore invisibile nell'arena. Se suppress-footstep-sounds=true,
 *   cancello il pacchetto per i ricevitori che sono nemici.
 *
 * FASE 2 - Intercettazione WORLD_PARTICLES (opzionale, da config)
 *   Obiettivo: Sopprimere le particelle emesse dai giocatori invisibili.
 *   Scelta tecnica: Intercetto WORLD_PARTICLES e cancello i pacchetti quando il
 *   giocatore che dovrebbe vederli e' un nemico del giocatore invisibile.
 *
 * NOTE IMPORTANTI SUL BUG ARMATURA:
 *   - NON intercettiamo ENTITY_EQUIPMENT perche' bloccerebbe anche i nostri stessi
 *     pacchetti di hideArmor (che inviano slot=AIR per nascondere l'armatura).
 *   - La gestione dell'armatura (hide/show) e' delegata a GamePlayingTask (re-hide periodico)
 *     e a InvisibilityPotionListener (hide al bere, show allo scadere).
 *
 * MODIFICA 08/04/2026: Aggiunto supporto flag configurabili per suoni e particelle.
 *                       Rimosso ENTITY_EQUIPMENT che bloccava hideArmor.
 */
public class InvisibilityPacketListener {

    /**
     * Registra il listener ProtocolLib per sopprimere suoni e particelle.
     * Legge le flag dalla config al momento della registrazione.
     *
     * @param plugin Istanza del plugin BedWars.
     */
    public static void register(Plugin plugin) {
        boolean suppressFootsteps = BedWars.config.getBoolean(ConfigPath.INVISIBILITY_SUPPRESS_FOOTSTEPS);
        boolean suppressParticles  = BedWars.config.getBoolean(ConfigPath.INVISIBILITY_SUPPRESS_PARTICLES);

        if (!suppressFootsteps && !suppressParticles) {
            // Nessuna soppressione richiesta: non registrare nulla
            plugin.getLogger().info("[G25.2] Invisibility packet listener disabled (flags: footsteps=false, particles=false)");
            return;
        }

        ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        // [DEBUG] FASE 1 - Soppressione footstep sounds
        if (suppressFootsteps) {
            pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
                @Override
                public void onPacketSending(PacketEvent event) {
                    if (event.isCancelled()) return;
                    if (event.isPlayerTemporary()) return;
                    Player receiver = event.getPlayer();
                    IArena arena = Arena.getArenaByPlayer(receiver);
                    if (arena == null || arena.getStatus() != GameState.playing) return;

                    // In 1.20.4, PacketPlayOutNamedSoundEffect usa coordinate int*8 (non double)
                    // d = X*8, e = Y*8, f = Z*8 (vedi dump pacchetto nel log)
                    double x, y, z;
                    try {
                        int xi = event.getPacket().getIntegers().read(0);
                        int yi = event.getPacket().getIntegers().read(1);
                        int zi = event.getPacket().getIntegers().read(2);
                        x = xi / 8.0;
                        y = yi / 8.0;
                        z = zi / 8.0;
                    } catch (Exception ex) {
                        // Fallback per versioni con coordinate come double
                        try {
                            x = event.getPacket().getDoubles().read(0);
                            y = event.getPacket().getDoubles().read(1);
                            z = event.getPacket().getDoubles().read(2);
                        } catch (Exception ex2) {
                            return; // Impossibile leggere le coordinate: skip
                        }
                    }
                    org.bukkit.Location soundLoc = new org.bukkit.Location(receiver.getWorld(), x, y, z);

                    for (Player nearby : receiver.getWorld().getPlayers()) {
                        if (nearby.equals(receiver)) continue;
                        if (nearby.getLocation().distanceSquared(soundLoc) > 1.0) continue;
                        // E' abbastanza vicino alla sorgente: e' un giocatore arena?
                        // Stesso team? Lascia passare il suono ai compagni
                        IArena nearbyArena = Arena.getArenaByPlayer(nearby);
                        if (nearbyArena == null || !nearbyArena.equals(arena)) continue;
                        // Ha la pozione di invisibilita'?
                        if (!nearby.hasPotionEffect(PotionEffectType.INVISIBILITY)) continue;
                        // Stesso team del receiver? Non sopprimere
                        com.andrei1058.bedwars.api.arena.team.ITeam teamReceiver = arena.getTeam(receiver);
                        com.andrei1058.bedwars.api.arena.team.ITeam teamNearby   = arena.getTeam(nearby);
                        if (teamReceiver != null && teamReceiver.equals(teamNearby)) continue;
                        // Sopprimi il suono
                        event.setCancelled(true);
                        return;
                    }
                }
            });
            plugin.getLogger().info("[G25.2] Invisibility: footstep sound suppression ENABLED");
        }

        // [DEBUG] FASE 2 - Soppressione particelle
        if (suppressParticles) {
            pm.addPacketListener(new PacketAdapter(plugin, ListenerPriority.HIGH, PacketType.Play.Server.WORLD_PARTICLES) {
                @Override
                public void onPacketSending(PacketEvent event) {
                    if (event.isCancelled()) return;
                    if (event.isPlayerTemporary()) return;
                    Player receiver = event.getPlayer();
                    IArena arena = Arena.getArenaByPlayer(receiver);
                    if (arena == null || arena.getStatus() != GameState.playing) return;

                    double x = event.getPacket().getFloat().read(0);
                    double y = event.getPacket().getFloat().read(1);
                    double z = event.getPacket().getFloat().read(2);
                    org.bukkit.Location particleLoc = new org.bukkit.Location(receiver.getWorld(), x, y, z);

                    for (Player nearby : receiver.getWorld().getPlayers()) {
                        if (nearby.equals(receiver)) continue;
                        if (nearby.getLocation().distanceSquared(particleLoc) > 4.0) continue;
                        // Stesso team del receiver? Non sopprimere
                        IArena nearbyArena = Arena.getArenaByPlayer(nearby);
                        if (nearbyArena == null || !nearbyArena.equals(arena)) continue;
                        if (!nearby.hasPotionEffect(PotionEffectType.INVISIBILITY)) continue;
                        com.andrei1058.bedwars.api.arena.team.ITeam teamReceiver = arena.getTeam(receiver);
                        com.andrei1058.bedwars.api.arena.team.ITeam teamNearby   = arena.getTeam(nearby);
                        if (teamReceiver != null && teamReceiver.equals(teamNearby)) continue;
                        event.setCancelled(true);
                        return;
                    }
                }
            });
            plugin.getLogger().info("[G25.2] Invisibility: particle suppression ENABLED");
        }
    }
}
