package com.andrei1058.bedwars.arena.spectator;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class SpectatorListeners implements Listener {

    @EventHandler
    public void onSpectatorItemInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = nms.getItemInHand(p);
        if (i == null) return;
        if (i.getType() == Material.AIR) return;
        if (!nms.isCustomBedWarsItem(i)) return;
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (!a.isSpectator(p)) return;

        /* Disable spectator interact */
        e.setCancelled(true);

        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        /* Spectator Items */
        switch (nms.getCustomData(i)) {
            case SpectatorItems.NBT_SPECTATOR_TELEPORTER_ITEM:
                TeleporterGUI.openGUI(p);
                break;
            case SpectatorItems.NBT_SPECTATOR_LEAVE_ITEM:
                a.removePlayer(p, Main.getServerType() == ServerType.BUNGEE);
                break;
        }
    }

    @EventHandler
    public void onSpectatorInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        TeleporterGUI.closeGUI(p);
    }

    @EventHandler
    public void onSpectatorClick(InventoryClickEvent e) {
        if (e.getWhoClicked().getGameMode() == GameMode.SPECTATOR) {
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem() == null) return;
        ItemStack i = e.getCurrentItem();
        if (i.getType() == Material.AIR) return;
        Player p = (Player) e.getWhoClicked();
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;

        /* Teleporter heads */
        if (i.getType() == Material.SKULL_ITEM) {
            if (nms.isCustomBedWarsItem(i)) {
                e.setCancelled(true);

                String data = nms.getCustomData(i);
                if (data.contains(TeleporterGUI.NBT_SPECTATOR_TELEPORTER_GUI_HEAD)) {
                    String player = data.split("_")[1];
                    Player target = Bukkit.getPlayer(player);
                    if (target == null) return;
                    if (target.isDead()) return;
                    if (!target.isOnline()) return;
                    SpectatorTeleportToPlayerEvent event = new SpectatorTeleportToPlayerEvent(p, target, a);
                    Bukkit.getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        p.teleport(target);
                    }
                    p.closeInventory();
                }
            }
        }
    }

    @EventHandler
    /* Refresh placeholders from GUIs*/
    public void onHealthChange(EntityRegainHealthEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player p = (Player) e.getEntity();
        Arena arena = Arena.getArenaByPlayer(p);
        if (arena == null) return;
        if (arena.isPlayer(p)) {
            TeleporterGUI.refreshAllGUIs();
        }
    }

    @EventHandler
    /* Refresh placeholders from GUIs*/
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER) return;
        Player p = (Player) e.getEntity();
        Arena arena = Arena.getArenaByPlayer(p);
        if (arena == null) return;
        if (arena.isPlayer(p)) {
            TeleporterGUI.refreshAllGUIs();
        }
    }

    @EventHandler
    /* Refresh placeholders from GUIs*/
    public void onPlayerLeave(PlayerLeaveArenaEvent e) {
        if (e.getArena().isPlayer(e.getPlayer())) {
            TeleporterGUI.refreshAllGUIs();
        }
    }

    @EventHandler
    /* Triggered when a spectator starts spectating in first person */
    public void onSpectatorInteractPlayer(PlayerInteractAtEntityEvent e){
        if (e.getRightClicked().getType() != EntityType.PLAYER) return;
        Player p = e.getPlayer();
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (a.isPlayer(p)) return;
        e.setCancelled(true);
        Player target = (Player) e.getRightClicked();
        if (a.isPlayer(target)){
            SpectatorFirstPersonEnterEvent event = new SpectatorFirstPersonEnterEvent(p, target, a, getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE).replace("{player}", target.getDisplayName()), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE));
            Bukkit.getPluginManager().callEvent(event);
            nms.sendTitle(p, event.getTitle(), event.getSubtitle(), 0, 30, 0);
            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().setHeldItemSlot(5);
            p.setSpectatorTarget(target);
        }
    }

    @EventHandler
    public void onSpectatorInteract(PlayerInteractEntityEvent e){
        Player p = e.getPlayer();
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (a.isPlayer(p)) return;
        e.setCancelled(true);
    }

    @EventHandler
    /* Triggered when a spectator leaves first person */
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) return;
        if (SpectatorFirstPersonEnterEvent.getSpectatingInFirstPerson().contains(p)){
            SpectatorFirstPersonLeaveEvent event = new SpectatorFirstPersonLeaveEvent(p, a, getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE));
            Bukkit.getPluginManager().callEvent(event);
            nms.sendTitle(p, event.getTitle(), event.getSubtitle(), 0, 30, 0);
            p.setGameMode(GameMode.ADVENTURE);
            p.setAllowFlight(true);
            p.setFlying(true);
        }
    }

    @EventHandler
    /* Prevent gamemode 3 menu */
    public void onTeleport(PlayerTeleportEvent e){
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE){
            Player p = e.getPlayer();
            e.setCancelled(true);
            if (SpectatorFirstPersonEnterEvent.getSpectatingInFirstPerson().contains(p)){
                SpectatorFirstPersonLeaveEvent event = new SpectatorFirstPersonLeaveEvent(p, Arena.getArenaByPlayer(p), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE));
                Bukkit.getPluginManager().callEvent(event);
                nms.sendTitle(p, event.getTitle(), event.getSubtitle(), 0, 30, 0);
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        }
    }

    @EventHandler
    /* Remove from first person on target die */
    public void onTargetDeath(PlayerKillEvent e){
        for (Player p : new ArrayList<>(SpectatorFirstPersonEnterEvent.getSpectatingInFirstPerson())){
            if (p.getSpectatorTarget() == e.getVictim()){
                p.setGameMode(GameMode.ADVENTURE);
                p.setAllowFlight(true);
                p.setFlying(true);
                SpectatorFirstPersonLeaveEvent event = new SpectatorFirstPersonLeaveEvent(p, e.getArena(), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE), getMsg(p, Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE));
                Bukkit.getPluginManager().callEvent(event);
                nms.sendTitle(p, event.getTitle(), event.getSubtitle(), 0, 30, 0);
            }
        }
    }
}
