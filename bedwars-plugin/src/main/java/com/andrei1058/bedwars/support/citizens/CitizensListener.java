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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.support.citizens;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Sounds;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class CitizensListener implements Listener {

    @EventHandler
    public void removeNPC(NPCRemoveEvent e) {
        if (e == null) return;
        if (e.getNPC() == null) return;
        if (e.getNPC().getEntity() == null) return;
        List<String> locations = BedWars.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE);
        boolean removed = false;
        if (JoinNPC.npcs.containsKey(e.getNPC().getId())) {
            JoinNPC.npcs.remove(e.getNPC().getId());
            removed = true;
        }
        for (String s : new ArrayList<>(locations)) {
            String[] data = s.split(",");
            if (data.length >= 10) {
                if (Misc.isNumber(data[9])) {
                    if (Integer.parseInt(data[9]) == e.getNPC().getId()) {
                        locations.remove(s);
                        removed = true;
                    }
                }
            }
        }
        for (Entity e2 : e.getNPC().getEntity().getNearbyEntities(0, 3, 0)) {
            if (e2.getType() == EntityType.ARMOR_STAND) {
                e2.remove();
            }
        }
        if (removed) BedWars.config.set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE, locations);
    }

    @EventHandler
    // Citizens support
    public void onNPCInteract(PlayerInteractEntityEvent e) {
        if (!JoinNPC.isCitizensSupport()) return;
        if (e.getPlayer().isSneaking()) return;
        if (!e.getRightClicked().hasMetadata("NPC")) return;
        net.citizensnpcs.api.npc.NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getRightClicked());
        if (npc == null) return;
        if (JoinNPC.npcs.containsKey(npc.getId())) {
            if (!Arena.joinRandomFromGroup(e.getPlayer(), JoinNPC.npcs.get(npc.getId()))) {
                e.getPlayer().sendMessage(getMsg(e.getPlayer(), Messages.COMMAND_JOIN_NO_EMPTY_FOUND));
                Sounds.playSound("join-denied", e.getPlayer());
            } else {
                Sounds.playSound("join-allowed", e.getPlayer());
            }
        }
    }

}
