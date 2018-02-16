package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.shop.ShopCategory;
import com.andrei1058.bedwars.upgrades.UpgradeGroup;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.upgrades.UpgradeGroup.getUpgradeGroup;

public class Interact implements Listener {

    @EventHandler
    public void i(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            if (b == null) return;
            if (b.getType() == Material.AIR) return;
            if (Arena.isInArena(p)) {
                if (b.getType() == Material.BED_BLOCK) {
                    e.setCancelled(true);
                    return;
                }
            }
            if (b.getState() instanceof Sign){
                for (Arena a : Arena.getArenas()){
                    if (a.getSigns().contains(b.getState())){
                        a.addPlayer(p);
                        return;
                    }
                }
            }
        }
        //check hand
        ItemStack inHand = nms.getItemInHand(p);
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (inHand == null) return;
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null){
                if (a.isPlayer(p)){
                    if (inHand.getType() == Material.FIREBALL){
                        e.setCancelled(true);
                        Fireball fb = p.launchProjectile(Fireball.class);
                        fb.setIsIncendiary(false);
                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i == null) continue;
                            if (i.getType() == null) continue;
                            if (i.getType() == Material.AIR) continue;
                            if (i.getType() == Material.FIREBALL) {
                                if (i.getAmount() <= 1) {
                                    p.getInventory().remove(i);
                                    p.updateInventory();
                                    return;
                                } else {
                                    i.setAmount(i.getAmount() - 1);
                                    p.updateInventory();
                                    return;
                                }
                            }
                        }
                    }
                    if (shop.getBoolean("utilities.silverfish.enable")){
                        if (!Misc.isProjectile(Material.valueOf(shop.getYml().getString("utilities.silverfish.material")))) {
                            if (inHand.getType() == Material.valueOf(shop.getYml().getString("utilities.silverfish.material")) && inHand.getData().getData() == shop.getInt("utilities.silverfish.data")) {
                                a.getTeam(p).spawnSilverfish(p.getLocation().add(0, 1, 0), p);
                            }
                        }
                    }
                    if (shop.getBoolean("utilities.ironGolem.enable")){
                        if (!Misc.isProjectile(Material.valueOf(shop.getYml().getString("utilities.ironGolem.material")))) {
                            if (inHand.getType() == Material.valueOf(shop.getYml().getString("utilities.ironGolem.material")) && inHand.getData().getData() == shop.getInt("utilities.ironGolem.data")) {
                                //todo spawneaza golem
                            }
                        }
                    }
                    if (shop.getBoolean("utilities.bridge.enable")){
                        if (!Misc.isProjectile(Material.valueOf(shop.getYml().getString("utilities.bridge.material")))) {
                            if (inHand.getType() == Material.getMaterial(shop.getYml().getString("utilities.bridge.material")) && inHand.getData().getData() == shop.getInt("utilities.bridge.data")) {
                                //todo spawn bridge
                            }
                        }
                    }
                }
            }
            if (!inHand.hasItemMeta()) return;
            if (!inHand.getItemMeta().hasDisplayName()) return;
            if (inHand.getItemMeta().getDisplayName().equalsIgnoreCase(getMsg(p, lang.arenaGuiItemName))) {
                ArenaGUI.openGui(p);
            } else if (inHand.getItemMeta().getDisplayName().equalsIgnoreCase(getMsg(p, lang.leaveItemName))) {
                p.performCommand("bw leave");
            }
        }
    }

    @EventHandler
    public void e(PlayerInteractEntityEvent e) {
        if (Main.npcs.containsKey(e.getRightClicked())) {
            Arena.joinRandomFromGroup(e.getPlayer(), Main.npcs.get(e.getRightClicked()));
        } else {
            if (Arena.isInArena(e.getPlayer())) {
                Arena a = Arena.getArenaByPlayer(e.getPlayer());
                Location l = e.getRightClicked().getLocation();
                for (BedWarsTeam t : a.getTeams()) {
                    Location l2 = t.getShop(), l3 = t.getTeamUpgrades();
                    if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                        e.setCancelled(true);
                    } else if (l.getBlockX() == l3.getBlockX() && l.getBlockY() == l3.getBlockY() && l.getBlockZ() == l3.getBlockZ()) {
                        e.setCancelled(true);
                    }
                }

            }
        }
    }

    @EventHandler
    public void e2(PlayerInteractAtEntityEvent e) {
        if (Main.npcs.containsKey(e.getRightClicked())) {
            Arena.joinRandomFromGroup(e.getPlayer(), Main.npcs.get(e.getRightClicked()));
        } else {
            if (Arena.isInArena(e.getPlayer())) {
                Arena a = Arena.getArenaByPlayer(e.getPlayer());
                Location l = e.getRightClicked().getLocation();
                for (BedWarsTeam t : a.getTeams()) {
                    Location l2 = t.getShop(), l3 = t.getTeamUpgrades();
                    if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                        e.setCancelled(true);
                        if (a.isPlayer(e.getPlayer())) {
                            ShopCategory.getByName("main.invContents").openToPlayer(e.getPlayer());
                        }
                    } else if (l.getBlockX() == l3.getBlockX() && l.getBlockY() == l3.getBlockY() && l.getBlockZ() == l3.getBlockZ()) {
                        if (a.isPlayer(e.getPlayer())) {
                            UpgradeGroup ug = getUpgradeGroup(a.getGroup());
                            if (ug != null){
                                ug.openToPlayer(e.getPlayer(), a);
                            }
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void bedEnter(PlayerBedEnterEvent e){
        if (Arena.getArenaByPlayer(e.getPlayer()) != null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void m(PlayerArmorStandManipulateEvent e) {
        if (Arena.isInArena(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void d(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (e.getPlayer().getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
            e.setCancelled(true);
        }
        if (Arena.isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.isSpectator(p)) {
                e.setCancelled(true);
            } else {
                if (a.getStatus() != GameState.playing){
                    e.setCancelled(true);
                } else {
                    if (e.getItemDrop().getItemStack().getType() == Material.COMPASS) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void prepare(PrepareItemCraftEvent e){
        if (Arena.isInArena((Player) e.getView().getPlayer())){
            if (config.getBoolean("disableCrafting")){
                e.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
