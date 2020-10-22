package com.andrei1058.bedwars.halloween;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.player.PlayerJoinArenaEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class HalloweenListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.isCancelled()) return;
        LivingEntity entity = e.getEntity();
        if (entity.getType() == EntityType.ARMOR_STAND) return;
        entity.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoad(WorldLoadEvent e) {
        // check if it is time to disable this special
        if (HalloweenSpecial.getINSTANCE() != null) {
            if (!HalloweenSpecial.checkAvailabilityDate()) {
                CreatureSpawnEvent.getHandlerList().unregister(this);
            }
        }
    }

    @EventHandler
    public void onPlayerDie(PlayerKillEvent e) {
        if (e.getKiller() != null) {
            Location location = e.getVictim().getLocation().add(0, 1, 0);
            if (location.getBlock().getType() == Material.AIR) {
                location.getBlock().setType(Material.valueOf(BedWars.getForCurrentVersion("WEB", "WEB", "COBWEB")));
                location.getWorld().playSound(location, Sound.GHAST_SCREAM2, 2f, 1f);
                e.getArena().addPlacedBlock(location.getBlock());
                location.getBlock().setMetadata("give-bw-exp", new FixedMetadataValue(BedWars.plugin, "ok"));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (e.getBlock().hasMetadata("give-bw-exp")) {
            PlayerLevel level = PlayerLevel.getLevelByPlayer(e.getPlayer().getUniqueId());
            if (level != null) {
                e.getBlock().getDrops().clear();
                level.addXp(5, PlayerXpGainEvent.XpSource.OTHER);
                e.getPlayer().sendMessage(ChatColor.GOLD + "+5 xp!");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinArenaEvent e) {
        if (!e.isSpectator()) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.AMBIENCE_CAVE, 3f, 1f), 20L);
        }
    }
}
