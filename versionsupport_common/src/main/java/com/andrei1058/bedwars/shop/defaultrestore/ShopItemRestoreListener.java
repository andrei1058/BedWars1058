package com.andrei1058.bedwars.shop.defaultrestore;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.support.version.common.VersionCommon.api;

// Used to restore default swords and bows if they are removed from the inventory and you remain with a less powerful weapon of the same kind. 1.12-.
public class ShopItemRestoreListener {

    // 1.11 or older
    public static class PlayerDrop implements Listener {
        @EventHandler
        public void onDrop(PlayerDropItemEvent e){
            if (manageDrop(e.getPlayer(), e.getItemDrop())) e.setCancelled(true);
        }
    }

    // 1.11 or older
    public static class PlayerPickup implements Listener {
        @SuppressWarnings("deprecation")
        @EventHandler
        public void onDrop(PlayerPickupItemEvent e){
            if (managePickup(e.getItem(), e.getPlayer())) e.setCancelled(true);
        }
    }

    // 1.12 or newer
    public static class EntityDrop implements Listener {
        @EventHandler
        public void onDrop(EntityDropItemEvent e){
            if (manageDrop(e.getEntity(), e.getItemDrop())) e.setCancelled(true);
        }
    }

    // 1.12 or newer
    public static class EntityPickup implements Listener {
        @EventHandler
        public void onDrop(EntityPickupItemEvent e){
            if (managePickup(e.getItem(), e.getEntity())) e.setCancelled(true);
        }
    }

    /**
     * Remove the default swords/ bow if the picked item is more powerful.
     *
     * @return true to cancel the event
     */
    public static boolean managePickup(Item item, LivingEntity player) {
        if (!(player instanceof Player)) return false;
        if (api.getArenaUtil().getArenaByPlayer((Player) player) == null) return false;
        if (api.getArenaUtil().getArenaByPlayer((Player) player).getStatus() != GameState.playing) return false;
        if (!api.getArenaUtil().getArenaByPlayer((Player) player).isPlayer((Player) player)) return false;

        if (api.getVersionSupport().isSword(item.getItemStack())) {
            for (ItemStack is : ((Player) player).getInventory()) {
                if (is == null) continue;
                if (is.getType() == Material.AIR) continue;
                if (!api.getVersionSupport().isCustomBedWarsItem(is)) continue;
                if (!api.getVersionSupport().getCustomData(is).equalsIgnoreCase("DEFAULT_ITEM")) continue;

                if (api.getVersionSupport().isSword(item.getItemStack())) {
                    if (api.getVersionSupport().getDamage(item.getItemStack()) >= api.getVersionSupport().getDamage(is)) {
                        ((Player) player).getInventory().remove(is);
                        ((Player) player).updateInventory();
                        return false;
                    }
                }
            }
            item.remove();
            return true;
        }
        return false;
    }

    /**
     * If the dropped sword/ bow is a default item and is more powerful
     * than the others in the inventory give it back.
     * <p>
     * If the player remains without a sword give it the swords from the default items.
     * If the player remains without a bow give it bows from the default items.
     *
     * @return true to cancel the event.
     */
    private static boolean manageDrop(Entity player, Item item) {
        if (!(player instanceof Player)) return false;
        if (api.getArenaUtil().getArenaByPlayer((Player) player) == null) return false;
        IArena a = api.getArenaUtil().getArenaByPlayer((Player) player);
        if (a.getStatus() != GameState.playing) return false;
        if (!a.isPlayer((Player) player)) return false;
        if (api.getVersionSupport().isCustomBedWarsItem(item.getItemStack())
                && api.getVersionSupport().getCustomData(item.getItemStack()).equalsIgnoreCase("DEFAULT_ITEM")
                && api.getVersionSupport().isSword(item.getItemStack())) {
            boolean hasSword = false;
            for (ItemStack is : ((Player) player).getInventory()) {
                if (is == null) continue;
                if (api.getVersionSupport().isSword(is)) {
                    if (api.getVersionSupport().getDamage(is) >= api.getVersionSupport().getDamage(item.getItemStack())) {
                        hasSword = true;
                        break;
                    }
                }
            }

            if (!hasSword) return true;
        } else {
            boolean sword = false;
            for (ItemStack is : ((Player) player).getInventory()) {
                if (is == null) continue;
                if (api.getVersionSupport().isSword(is)) {
                    sword = true;
                    break;
                }
            }
            if (!sword) a.getTeam((Player) player).defaultSword((Player) player, true);
        }
        return false;
    }


    public static class DefaultRestoreInvClose implements Listener {

        /**
         * If the player moves a default sword or bow into another inventory
         * and he remains with a less powerful weapon restore the lost one.
         */
        @EventHandler
        public void onInventoryClose(InventoryCloseEvent e) {
            if (e.getInventory().getType() == InventoryType.PLAYER) return;
            if (api.getArenaUtil().getArenaByPlayer((Player) e.getPlayer()) == null) return;
            IArena a = api.getArenaUtil().getArenaByPlayer((Player) e.getPlayer());
            if (a.getStatus() != GameState.playing) return;
            if (!a.isPlayer((Player) e.getPlayer())) return;

            boolean sword = false;
            for (ItemStack is : e.getPlayer().getInventory()) {
                if (is == null) continue;
                if (is.getType() == Material.AIR) continue;
                if (api.getVersionSupport().isSword(is)) sword = true;
            }

            if (!sword) a.getTeam((Player) e.getPlayer()).defaultSword((Player) e.getPlayer(), true);
        }
    }
}
