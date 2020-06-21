package com.andrei1058.bedwars.arena.upgrades;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.player.PlayerBaseEnterEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBaseLeaveEvent;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Map;
import java.util.WeakHashMap;

public class BaseListener implements Listener {

    public static Map<Player, ITeam> isOnABase = new WeakHashMap<>();

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent e) {
        IArena a = Arena.getArenaByIdentifier(e.getPlayer().getWorld().getName());
        if (a == null) return;
        if (a.getStatus() != GameState.playing) return;
        Player p = e.getPlayer();
        checkEvents(p, a);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (isOnABase.containsKey(p)) {
            IArena a = Arena.getArenaByPlayer(p);
            if (a == null) {
                isOnABase.remove(p);
                return;
            }
            checkEvents(p, a);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        IArena a = Arena.getArenaByPlayer(e.getEntity());
        if (a == null) return;
        checkEvents(e.getEntity(), a);
    }

    /**
     * Check the Enter/ Leave events and call them
     */
    private static void checkEvents(Player p, IArena a) {
        if (p == null || a == null) return;
        if (a.isSpectator(p)) return;
        boolean notOnBase = true;
        for (ITeam bwt : a.getTeams()) {
            /* BaseEnterEvent */
            if (p.getLocation().distance(bwt.getBed()) <= a.getIslandRadius()) {
                notOnBase = false;
                if (isOnABase.containsKey(p)) {
                    if (isOnABase.get(p) != bwt) {
                        Bukkit.getPluginManager().callEvent(new PlayerBaseLeaveEvent(p, isOnABase.get(p)));
                        if (!Arena.magicMilk.containsKey(p.getUniqueId())) {
                            Bukkit.getPluginManager().callEvent(new PlayerBaseEnterEvent(p, bwt));
                        }
                        isOnABase.replace(p, bwt);
                    }
                } else {
                    if (!Arena.magicMilk.containsKey(p.getUniqueId())) {
                        Bukkit.getPluginManager().callEvent(new PlayerBaseEnterEvent(p, bwt));
                        isOnABase.put(p, bwt);
                    }
                }
            }
        }
        /* BaseLeaveEvent */
        if (notOnBase) {
            if (isOnABase.containsKey(p)) {
                Bukkit.getPluginManager().callEvent(new PlayerBaseLeaveEvent(p, isOnABase.get(p)));
                isOnABase.remove(p);
            }
        }
    }

    @EventHandler
    public void onBaseEnter(PlayerBaseEnterEvent e) {
        if (e == null) return;
        ITeam team = e.getTeam();
        if (team.isMember(e.getPlayer())) {
            // Give base effects
            for (PotionEffect ef : team.getBaseEffects()) {
                e.getPlayer().addPotionEffect(ef);
            }
        } else {
            // Trigger trap
            if (!team.getActiveTraps().isEmpty()) {
                if (!team.isBedDestroyed()) {
                    team.getActiveTraps().get(0).trigger(team, e.getPlayer());
                    team.getActiveTraps().remove(0);
                }
            }

            /* Manage trap */
            /*if (team.isTrapActive()) {
                team.disableTrap();
                for (Player mem : team.getMembers()) {
                    if (team.isTrapTitle()) {
                        nms.sendTitle(mem, getMsg(mem, Messages.TRAP_ENEMY_BASE_ENTER_TITLE), null, 0, 50, 0);
                    }
                    if (team.isTrapSubtitle()) {
                        nms.sendTitle(mem, null, getMsg(mem, Messages.TRAP_ENEMY_BASE_ENTER_SUBTITLE), 0, 50, 0);
                    }
                    if (team.isTrapAction()) {
                        nms.playAction(mem, getMsg(mem, Messages.TRAP_ENEMY_BASE_ENTER_ACTION));
                    }
                    if (team.isTrapChat()) {
                        mem.sendMessage(getMsg(mem, Messages.TRAP_ENEMY_BASE_ENTER_CHAT));
                    }
                }
            }*/
        }
    }

    @EventHandler
    public void onBaseLeave(PlayerBaseLeaveEvent e) {
        if (e == null) return;
        BedWarsTeam t = (BedWarsTeam) e.getTeam();
        if (t.isMember(e.getPlayer())) {
            // Remove effects for members
            for (PotionEffect pef : e.getPlayer().getActivePotionEffects()) {
                for (PotionEffect pf : t.getBaseEffects()) {
                    if (pef.getType() == pf.getType()) {
                        e.getPlayer().removePotionEffect(pf.getType());
                    }
                }
            }
        }/* else {
            // Remove effects for enemies
            for (PotionEffect pef : e.getPlayer().getActivePotionEffects()) {
                for (BedWarsTeam.Effect pf : t.getEbseEffectsStatic()) {
                    if (pef.getType() == pf.getPotionEffectType()) {
                        e.getPlayer().removePotionEffect(pf.getPotionEffectType());
                    }
                }
            }
        }*/
    }
}
