package com.andrei1058.bedwars.listeners.joinhandler;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.sidebar.BedWarsScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.andrei1058.bedwars.BedWars.*;


public class JoinListenerMultiArena implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player p = e.getPlayer();
        p.getInventory().setArmorContents(null);

        JoinHandlerCommon.displayCustomerDetails(p);

        // Show commands if player is op and there is no set arenas
        if (p.isOp()) {
            if (Arena.getArenas().isEmpty()) {
                p.performCommand(mainCmd);
            }
        }

        ReJoin reJoin = ReJoin.getPlayer(p);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // Hide new player to players and spectators, and vice versa
            // Players from lobby will remain visible
            for (Player online : Bukkit.getOnlinePlayers()){
                if (Arena.isInArena(online)) {
                    BedWars.nms.spigotHidePlayer(online, p);
                    BedWars.nms.spigotHidePlayer(p, online);
                } else {
                    BedWars.nms.spigotShowPlayer(online, p);
                    BedWars.nms.spigotShowPlayer(p, online);
                }
            }

            // To prevent invisibility issues handle ReJoin after sending invisibility packets
            if (reJoin != null) {
                if (reJoin.canReJoin()) {
                    reJoin.reJoin(p);
                    return;
                }
                reJoin.destroy(false);
            }
        }, 14L);

        if (reJoin != null && reJoin.canReJoin()) return;

        // Teleport to lobby location
        Location lobbyLocation = config.getConfigLoc("lobbyLoc");
        if (lobbyLocation != null && lobbyLocation.getWorld() != null) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> p.teleport(lobbyLocation, PlayerTeleportEvent.TeleportCause.PLUGIN), 2L);
        }

        // Send items
        Arena.sendLobbyCommandItems(p);

        BedWarsScoreboard.giveScoreboard(p, null, true);

        p.setHealthScale(p.getMaxHealth());
        p.setExp(0);
        p.setHealthScale(20);
        p.setFoodLevel(20);
    }
}

