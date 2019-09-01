package com.andrei1058.bedwars.arena.upgrades;

import com.andrei1058.bedwars.api.events.player.PlayerBaseEnterEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBaseLeaveEvent;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class BaseListener implements Listener {

    public static HashMap<Player, BedWarsTeam> isOnABase = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent e) {
        Arena a = Arena.getArenaByName(e.getPlayer().getWorld().getName());
        if (a == null) return;
        if (a.getStatus() != GameState.playing) return;
        Player p = e.getPlayer();
        checkEvents(p, a);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if (isOnABase.containsKey(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a == null) {
                isOnABase.remove(p);
                return;
            }
            checkEvents(p, a);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getEntity());
        if (a == null) return;
        checkEvents(e.getEntity(), a);
    }

    /**
     * Check the Enter/ Leave events and call them
     */
    private static void checkEvents(Player p, Arena a) {
        if (a.isSpectator(p)) return;
        boolean notOnBase = true;
        for (BedWarsTeam bwt : a.getTeams()) {
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
        BedWarsTeam team = (BedWarsTeam) e.getTeam();
        if (team.isMember(e.getPlayer())) {
            /* Give base effects */
            for (BedWarsTeam.Effect ef : team.getBaseEffects()) {
                e.getPlayer().addPotionEffect(new PotionEffect(ef.getPotionEffectType(), ef.getDuration(), ef.getAmplifier()));
            }
        } else {
            /* Give base effects */
            for (BedWarsTeam.Effect ef : team.getEnemyBaseEnter()) {
                e.getPlayer().addPotionEffect(new PotionEffect(ef.getPotionEffectType(), ef.getDuration(), ef.getAmplifier()));
            }
            /* Downgrade base enter effects for enemies */
            team.getEnemyBaseEnter().clear();
            for (int i : team.getEnemyBaseEnterSlots()) {
                team.getUpgradeTier().remove(i);
            }
            team.getEnemyBaseEnterSlots().clear();

            /* Manage trap */
            if (team.isTrapActive()) {
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
            }
        }
    }

    @EventHandler
    public void onBaseLeave(PlayerBaseLeaveEvent e) {
        BedWarsTeam t = (BedWarsTeam) e.getTeam();
        if (t.isMember(e.getPlayer())) {
            /* Remove effects for members */
            for (PotionEffect pef : e.getPlayer().getActivePotionEffects()) {
                for (BedWarsTeam.Effect pf : t.getBaseEffects()) {
                    if (pef.getType() == pf.getPotionEffectType()) {
                        e.getPlayer().removePotionEffect(pf.getPotionEffectType());
                    }
                }
            }
        } else {
            /* Remove effects for enemies */
            for (PotionEffect pef : e.getPlayer().getActivePotionEffects()) {
                for (BedWarsTeam.Effect pf : t.getEbseEffectsStatic()) {
                    if (pef.getType() == pf.getPotionEffectType()) {
                        e.getPlayer().removePotionEffect(pf.getPotionEffectType());
                    }
                }
            }
        }
    }
}
