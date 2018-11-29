package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.upgrades.UpgradeGroup;
import org.bukkit.Bukkit;
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
import org.bukkit.metadata.FixedMetadataValue;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.language.Language.getMsg;
import static com.andrei1058.bedwars.upgrades.UpgradeGroup.getUpgradeGroup;

public class Interact implements Listener {

    @EventHandler
    /* Handle custom items with commands on them */
    public void onItemCommand(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack i = Main.nms.getItemInHand(p);
            if (!nms.isCustomBedWarsItem(i)) return;
            String[] customData = nms.getCustomData(i).split("_");
            if (customData.length >= 2) {
                if (customData[0].equals("RUNCOMMAND")) {
                    e.setCancelled(true);
                    Bukkit.dispatchCommand(p, customData[1]);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Arena.afkCheck.remove(p.getUniqueId());
        if (Main.api.isPlayerAFK(e.getPlayer())) {
            Main.api.setPlayerAFK(e.getPlayer(), false);
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b == null) return;
            if (b.getType() == Material.AIR) return;
            Arena a = Arena.getArenaByPlayer(p);
            if (a != null) {
                if (a.getRespawn().containsKey(e.getPlayer())) {
                    e.setCancelled(true);
                    return;
                }
                if (nms.isBed(b.getType())) {
                    if (p.isSneaking()) {
                        ItemStack i = nms.getItemInHand(p);
                        if (i == null) {
                            e.setCancelled(true);
                        } else if (i.getType() == Material.AIR) {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                    }
                    return;
                }
                if (b.getType() == Material.CHEST) {
                    if (a.isSpectator(p) || a.getRespawn().containsKey(p)) {
                        e.setCancelled(true);
                        return;
                    }
                    //make it so only team members can open chests while team is alive, and all when is eliminated
                    BedWarsTeam owner = null;
                    int isRad = a.getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
                    for (BedWarsTeam t : a.getTeams()) {
                        if (t.getSpawn().distance(e.getClickedBlock().getLocation()) <= isRad) {
                            owner = t;
                        }
                    }
                    if (owner != null) {
                        if (owner.getMembers().isEmpty()) {
                            e.setCancelled(true);
                            p.sendMessage(getMsg(p, Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED));
                        }
                    }
                }
                if (a.isSpectator(p) || a.getRespawn().containsKey(p)) {
                    switch (b.getType()) {
                        case CHEST:
                        case ENDER_CHEST:
                        case ANVIL:
                        case WORKBENCH:
                        case HOPPER:
                            e.setCancelled(true);
                            break;
                    }
                }
            }
            if (b.getState() instanceof Sign) {
                for (Arena a1 : Arena.getArenas()) {
                    if (a1.getSigns().contains(b.getState())) {
                        a1.addPlayer(p, false);
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
            if (a != null) {
                if (a.isPlayer(p)) {
                    if (inHand.getType() == nms.materialFireball()) {
                        e.setCancelled(true);
                        Fireball fb = p.launchProjectile(Fireball.class);
                        fb.setMetadata("bw1058", new FixedMetadataValue(plugin, "ceva"));
                        fb.setIsIncendiary(false);
                        //fb.setGlowing(true);

                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i == null) continue;
                            if (i.getType() == null) continue;
                            if (i.getType() == Material.AIR) continue;
                            if (i.getType() == nms.materialFireball()) {
                                nms.minusAmount(p, inHand, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
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

    @EventHandler
    public void onEntityInteract2(PlayerInteractAtEntityEvent e) {
        Arena a = Arena.getArenaByPlayer(e.getPlayer());
        if (a == null) return;
        Location l = e.getRightClicked().getLocation();
        for (BedWarsTeam t : a.getTeams()) {
            Location l3 = t.getTeamUpgrades();
            if (l.getBlockX() == l3.getBlockX() && l.getBlockY() == l3.getBlockY() && l.getBlockZ() == l3.getBlockZ()) {
                if (a.isPlayer(e.getPlayer())) {
                    UpgradeGroup ug = getUpgradeGroup(a.getGroup());
                    if (ug != null) {
                        ug.openToPlayer(e.getPlayer(), a);
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent e) {
        if (Arena.getArenaByPlayer(e.getPlayer()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorManipulate(PlayerArmorStandManipulateEvent e) {
        if (e.isCancelled()) return;
        //prevent from breaking generators
        if (Arena.getArenaByPlayer(e.getPlayer()) != null) {
            e.setCancelled(true);
        }

        //prevent from stealing from armor stands in lobby
        if (e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase(Main.getLobbyWorld())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCrafting(PrepareItemCraftEvent e) {
        if (Arena.getArenaByPlayer((Player) e.getView().getPlayer()) != null) {
            if (config.getBoolean("disableCrafting")) {
                e.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
