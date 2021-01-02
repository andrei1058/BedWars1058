package com.andrei1058.bedwars.listeners.dropshandler;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PlayerDrops {

    private PlayerDrops() {
    }

    /**
     * if bedWars should handle drops behavior.
     *
     * @return true if event drops must be cleared.
     */
    public static boolean handlePlayerDrops(IArena arena, Player victim, Player killer, ITeam victimsTeam, ITeam killersTeam, PlayerKillEvent.PlayerKillCause cause) {
        if (arena.getConfig().getBoolean(ConfigPath.ARENA_NORMAL_DEATH_DROPS)) {
            return false;
        }
        if (cause == PlayerKillEvent.PlayerKillCause.PLAYER_PUSH || cause == PlayerKillEvent.PlayerKillCause.PLAYER_PUSH_FINAL) {
            // if died by fall damage drop items at location
            dropItems(victim);
            return true;
        }
        if (killer == null) {
            // Death without a attacker drops items on the floor
            dropItems(victim);
            return true;
        }
        if (cause.isDespawnable()) {
            // If killed by a ironGolem or silverFish drop on floor
            dropItems(victim);
            return true;
        }
        if (cause.isPvpLogOut()) {
            // if is pvp log out drop at disconnect location
            dropItems(victim);
            return true;
        }
        if (cause.isFinalKill()) {
            // if is final kill drop items at generator
            if (victimsTeam != null) {
                Location dropsLocation = new Location(victim.getWorld(), victimsTeam.getKillDropsLocation().getBlockX(), victimsTeam.getKillDropsLocation().getY(), victimsTeam.getKillDropsLocation().getZ());
                victim.getEnderChest().forEach(item -> {
                    if (item != null) {
                        victim.getWorld().dropItemNaturally(dropsLocation, item);
                    }
                });
                victim.getEnderChest().clear();
            }
        }

        // victim's inventory
        ItemStack[] drops = victim.getInventory().getContents();

        if (victimsTeam != null && !(victimsTeam.equals(killersTeam)) && victim.equals(killer)) {
            // if final kill give items at kill drops location (team generator)
            if (victimsTeam.isBedDestroyed()) {
                for (ItemStack i : drops) {
                    if (i == null) continue;
                    if (i.getType() == Material.AIR) continue;
                    if (nms.isArmor(i) || nms.isBow(i) || nms.isSword(i) || nms.isTool(i)) continue;
                    if (!nms.getShopUpgradeIdentifier(i).trim().isEmpty()) continue;
                    if (arena.getTeam(killer) != null) {
                        Vector v = victimsTeam.getKillDropsLocation();
                        killer.getWorld().dropItemNaturally(new Location(arena.getWorld(), v.getX(), v.getY(), v.getZ()), i);
                    }
                }

            } else {
                // add-to-inventory feature if receiver is not respawning
                if (!arena.isReSpawning(killer)) {
                    Map<Material, Integer> materialDrops = new HashMap<>();
                    for (ItemStack i : drops) {
                        if (i == null) continue;
                        if (i.getType() == Material.AIR) continue;
                        if (i.getType() == Material.DIAMOND || i.getType() == Material.EMERALD || i.getType() == Material.IRON_INGOT || i.getType() == Material.GOLD_INGOT) {

                            // add to killer inventory
                            killer.getInventory().addItem(i);

                            // count items
                            if (materialDrops.containsKey(i.getType())) {
                                materialDrops.replace(i.getType(), materialDrops.get(i.getType()) + i.getAmount());
                            } else {
                                materialDrops.put(i.getType(), i.getAmount());
                            }
                        }

                        for (Map.Entry<Material, Integer> entry : materialDrops.entrySet()) {
                            String msg = "";
                            int amount = entry.getValue();
                            switch (entry.getKey()) {
                                case DIAMOND:
                                    msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_DIAMOND).replace("{meaning}", amount == 1 ?
                                            getMsg(killer, Messages.MEANING_DIAMOND_SINGULAR) : getMsg(killer, Messages.MEANING_DIAMOND_PLURAL));
                                    break;
                                case EMERALD:
                                    msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_EMERALD).replace("{meaning}", amount == 1 ?
                                            getMsg(killer, Messages.MEANING_EMERALD_SINGULAR) : getMsg(killer, Messages.MEANING_EMERALD_PLURAL));
                                    break;
                                case IRON_INGOT:
                                    msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_IRON).replace("{meaning}", amount == 1 ?
                                            getMsg(killer, Messages.MEANING_IRON_SINGULAR) : getMsg(killer, Messages.MEANING_IRON_PLURAL));
                                    break;
                                case GOLD_INGOT:
                                    msg = getMsg(killer, Messages.PLAYER_DIE_REWARD_GOLD).replace("{meaning}", amount == 1 ?
                                            getMsg(killer, Messages.MEANING_GOLD_SINGULAR) : getMsg(killer, Messages.MEANING_GOLD_PLURAL));
                                    break;
                            }
                            killer.sendMessage(msg.replace("{amount}", String.valueOf(amount)));
                        }
                    }
                    materialDrops.clear();
                }
            }

        }
        return true;
    }

    private static void dropItems(Player player) {
        for (ItemStack i : player.getInventory().getContents()) {
            if (i == null) continue;
            if (i.getType() == Material.AIR) continue;
            if (i.getType() == Material.DIAMOND || i.getType() == Material.EMERALD || i.getType() == Material.IRON_INGOT || i.getType() == Material.GOLD_INGOT) {
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), i);
            }
        }
    }
}
