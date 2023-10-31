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

package com.andrei1058.bedwars.arena.team;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerFirstSpawnEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.region.Cuboid;
import com.andrei1058.bedwars.api.upgrades.EnemyBaseEnterTrap;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

@SuppressWarnings("WeakerAccess")
public class BedWarsTeam implements ITeam {

    private List<Player> members = new ArrayList<>();
    private TeamColor color;
    private Location spawn, bed, shop, teamUpgrades;
    //private IGenerator ironGenerator = null, goldGenerator = null, emeraldGenerator = null;
    private String name;
    private Arena arena;
    private boolean bedDestroyed = false;
    private Vector killDropsLoc = null;

    // team generators
    private List<IGenerator> generators = new ArrayList<>();

    // team upgrade name, tier
    private ConcurrentHashMap<String, Integer> teamUpgradeList = new ConcurrentHashMap<>();
    // Potion effects for teammates from the upgrades
    private List<PotionEffect> teamEffects = new ArrayList<>();
    // Potion effects for teammates on base only
    private List<PotionEffect> base = new ArrayList<>();
    // Enchantments for bows
    private List<TeamEnchant> bowsEnchantments = new ArrayList<>();
    // Enchantments for swords
    private List<TeamEnchant> swordsEnchantemnts = new ArrayList<>();
    // Enchantments for armors
    private List<TeamEnchant> armorsEnchantemnts = new ArrayList<>();
    // Used for show/ hide bed hologram
    private HashMap<UUID, BedHolo> beds = new HashMap<>();
    // Queued traps
    private LinkedList<EnemyBaseEnterTrap> enemyBaseEnterTraps = new LinkedList<>();
    // Amount of dragons for Sudden Death phase
    private int dragons = 1;
    // Player cache, used for losers stats and rejoin
    private List<Player> membersCache = new ArrayList<>();
    // Invulnerability at re-spawn
    // Fall invulnerability when teammates respawn
    public static HashMap<UUID, Long> reSpawnInvulnerability = new HashMap<>();
    private UUID identity;

    public BedWarsTeam(String name, TeamColor color, Location spawn, Location bed, Location shop, Location teamUpgrades, Arena arena) {
        if (arena == null) return;
        this.name = name;
        this.color = color;
        this.spawn = spawn;
        this.bed = bed;
        this.arena = arena;
        this.shop = shop;
        this.teamUpgrades = teamUpgrades;
        Language.saveIfNotExists(ConfigPath.TEAM_NAME_PATH.replace("{arena}", getArena().getArenaName()).replace("{team}", getName()), name);
        arena.getRegionsList().add(new Cuboid(spawn, arena.getConfig().getInt(ConfigPath.ARENA_SPAWN_PROTECTION), true));

        Location drops = getArena().getConfig().getArenaLoc("Team." + getName() + "." + ConfigPath.ARENA_TEAM_KILL_DROPS_LOC);
        if (drops != null) {
            setKillDropsLocation(drops);
        }
        this.identity = UUID.randomUUID();
    }

    public int getSize() {
        return members.size();
    }

    /**
     * Add a new member to the team
     */
    public void addPlayers(Player... players) {
        if (players == null) return;
        for (Player p : players) {
            if (p == null) continue;
            members.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
            members.add(p);

            membersCache.removeIf(player -> player.getUniqueId().equals(p.getUniqueId()));
            membersCache.add(p);

            new BedHolo(p, getArena());
        }
    }

    /**
     * first spawn
     */
    public void firstSpawn(Player p) {
        if (p == null) return;
        TeleportManager.teleportC(p, spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
        p.setGameMode(GameMode.SURVIVAL);
        p.setCanPickupItems(true);
        nms.setCollide(p, getArena(), true);
        sendDefaultInventory(p, true);
        Bukkit.getPluginManager().callEvent(new PlayerFirstSpawnEvent(p, getArena(), this));
    }

    /**
     * Spawn shopkeepers for target team (if enabled).
     */
    public void spawnNPCs() {
        if (getMembers().isEmpty() && getArena().getConfig().getBoolean(ConfigPath.ARENA_DISABLE_NPCS_FOR_EMPTY_TEAMS))
            return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            nms.colorBed(this);
            nms.spawnShop(getArena().getConfig().getArenaLoc("Team." + getName() + ".Upgrade"), (getArena().getMaxInTeam() > 1 ? Messages.NPC_NAME_TEAM_UPGRADES : Messages.NPC_NAME_SOLO_UPGRADES), getArena().getPlayers(), getArena());
            nms.spawnShop(getArena().getConfig().getArenaLoc("Team." + getName() + ".Shop"), (getArena().getMaxInTeam() > 1 ? Messages.NPC_NAME_TEAM_SHOP : Messages.NPC_NAME_SOLO_SHOP), getArena().getPlayers(), getArena());
        }, 20L);

        Cuboid c1 = new Cuboid(getArena().getConfig().getArenaLoc("Team." + getName() + ".Upgrade"), getArena().getConfig().getInt(ConfigPath.ARENA_UPGRADES_PROTECTION), true);
        c1.setMinY(c1.getMinY() - 1);
        c1.setMaxY(c1.getMaxY() + 4);
        getArena().getRegionsList().add(c1);

        Cuboid c2 = new Cuboid(getArena().getConfig().getArenaLoc("Team." + getName() + ".Shop"), getArena().getConfig().getInt(ConfigPath.ARENA_SHOP_PROTECTION), true);
        c2.setMinY(c2.getMinY() - 1);
        c2.setMaxY(c2.getMaxY() + 4);
        getArena().getRegionsList().add(c2);
    }

    /**
     * Rejoin a team
     */
    public void reJoin(@NotNull Player p) {
        reJoin(p, BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_COUNTDOWN));
    }

    public void reJoin(@NotNull Player p, int respawnTime) {
        addPlayers(p);
        arena.startReSpawnSession(p, respawnTime);
    }

    /**
     * Gives the start inventory
     */
    public void sendDefaultInventory(Player p, boolean clean) {
        if (clean) p.getInventory().clear();
        String path = config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + "." + arena.getGroup()) == null ?
                ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + ".Default" : ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + "." + arena.getGroup();
        for (String s : config.getYml().getStringList(path)) {
            String[] parm = s.split(",");
            if (parm.length != 0) {
                try {
                    ItemStack i;
                    if (parm.length > 1) {
                        try {
                            Integer.parseInt(parm[1]);
                        } catch (Exception ex) {
                            plugin.getLogger().severe(parm[1] + " is not an integer at: " + s + " (config)");
                            continue;
                        }
                        i = new ItemStack(Material.valueOf(parm[0]), Integer.parseInt(parm[1]));
                    } else {
                        i = new ItemStack(Material.valueOf(parm[0]));
                    }
                    if (parm.length > 2) {
                        try {
                            Integer.parseInt(parm[2]);
                        } catch (Exception ex) {
                            plugin.getLogger().severe(parm[2] + " is not an integer at: " + s + " (config)");
                            continue;
                        }
                        i.setAmount(Integer.parseInt(parm[2]));
                    }
                    ItemMeta im = i.getItemMeta();
                    if (parm.length > 3) {
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', parm[3]));
                    }
                    nms.setUnbreakable(im);
                    i.setItemMeta(im);
                    i = nms.addCustomData(i, "DEFAULT_ITEM");

                    if (BedWars.nms.isSword(i)) {
                        boolean hasSword = false;
                        for (ItemStack item : p.getInventory().getContents()) {
                            if (item == null) continue;
                            if (item.getType() == Material.AIR) continue;
                            if (BedWars.nms.isSword(item)) {
                                hasSword = true;
                                break;
                            }
                        }
                        if (!hasSword) {
                            p.getInventory().addItem(i);
                        }
                    } else if (BedWars.nms.isBow(i)) {
                        boolean hasBow = false;
                        for (ItemStack item : p.getInventory().getContents()) {
                            if (item == null) continue;
                            if (item.getType() == Material.AIR) continue;
                            if (BedWars.nms.isBow(item)) {
                                hasBow = true;
                                break;
                            }
                        }
                        if (!hasBow) {
                            p.getInventory().addItem(i);
                        }
                    } else {
                        p.getInventory().addItem(i);
                    }
                } catch (Exception ignored) {
                }
            }
        }
        sendArmor(p);
    }

    public void defaultSword(Player p, boolean sword) {
        if (!sword) return;
        String path = config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + "." + arena.getGroup()) == null ?
                ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + ".Default" : ConfigPath.GENERAL_CONFIGURATION_DEFAULT_ITEMS + "." + arena.getGroup();
        for (String s : config.getYml().getStringList(path)) {
            String[] parm = s.split(",");
            if (parm.length != 0) {
                try {
                    ItemStack i;
                    if (parm.length > 1) {
                        try {
                            Integer.parseInt(parm[1]);
                        } catch (Exception ex) {
                            plugin.getLogger().severe(parm[1] + " is not an integer at: " + s + " (config)");
                            continue;
                        }
                        i = new ItemStack(Material.valueOf(parm[0]), Integer.parseInt(parm[1]));
                    } else {
                        i = new ItemStack(Material.valueOf(parm[0]));
                    }
                    if (parm.length > 2) {
                        try {
                            Integer.parseInt(parm[2]);
                        } catch (Exception ex) {
                            plugin.getLogger().severe(parm[2] + " is not an integer at: " + s + " (config)");
                            continue;
                        }
                        i.setAmount(Integer.parseInt(parm[2]));
                    }
                    ItemMeta im = i.getItemMeta();
                    if (parm.length > 3) {
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', parm[3]));
                    }
                    nms.setUnbreakable(im);
                    i.setItemMeta(im);

                    i = nms.addCustomData(i, "DEFAULT_ITEM");

                    if (BedWars.nms.isSword(i)) {
                        p.getInventory().addItem(i);
                        break;
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Spawn iron and gold generators
     */
    public void spawnGenerators() {
        for (String type : new String[]{"Iron", "Gold"}) {
            GeneratorType gt = GeneratorType.valueOf(type.toUpperCase());
            List<Location> locs = new ArrayList<>();
            Object o = getArena().getConfig().getYml().get("Team." + getName() + "." + type);
            if (o instanceof String) {
                locs.add(getArena().getConfig().getArenaLoc("Team." + getName() + "." + type));
            } else {
                locs = getArena().getConfig().getArenaLocations("Team." + getName() + "." + type);
            }
            for (Location loc : locs) {
                IGenerator gen = new OreGenerator(loc, getArena(), gt, this);
                //getArena().getOreGenerators().add(gen);
                generators.add(gen);
            }
        }
    }

    /**
     * Respawn a member
     */
    public void respawnMember(@NotNull Player p) {
        if (reSpawnInvulnerability.containsKey(p.getUniqueId())) {
            reSpawnInvulnerability.replace(p.getUniqueId(), System.currentTimeMillis() + config.getInt(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_INVULNERABILITY));
        } else {
            reSpawnInvulnerability.put(p.getUniqueId(), System.currentTimeMillis() + config.getInt(ConfigPath.GENERAL_CONFIGURATION_RE_SPAWN_INVULNERABILITY));
        }
        TeleportManager.teleportC(p, getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        p.setVelocity(new Vector(0, 0, 0));
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        nms.setCollide(p, arena, true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setHealth(20);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            getArena().getRespawnSessions().remove(p); //Fixes https://github.com/andrei1058/BedWars1058/issues/669

            for (Player inGame : arena.getPlayers()) {
                if (inGame.equals(p)) continue;
                BedWars.nms.spigotShowPlayer(p, inGame);
                BedWars.nms.spigotShowPlayer(inGame, p);
            }
            for (Player spectator : arena.getSpectators()) {
                BedWars.nms.spigotShowPlayer(p, spectator);
            }
        }, 8L);

        nms.sendTitle(p, getMsg(p, Messages.PLAYER_DIE_RESPAWNED_TITLE), "", 0, 20, 10);

        sendDefaultInventory(p, false);
        ShopCache sc = ShopCache.getShopCache(p.getUniqueId());
        if (sc != null) {
            sc.managePermanentsAndDowngradables(getArena());
        }
        p.setHealth(20);
        if (!getBaseEffects().isEmpty()) {
            for (PotionEffect ef : getBaseEffects()) {
                p.addPotionEffect(ef, true);
            }
        }
        if (!getTeamEffects().isEmpty()) {
            for (PotionEffect ef : getTeamEffects()) {
                p.addPotionEffect(ef, true);
            }
        }
        if (!getBowsEnchantments().isEmpty()) {
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (i.getType() == Material.BOW) {
                    ItemMeta im = i.getItemMeta();
                    for (TeamEnchant e : getBowsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
        if (!getSwordsEnchantments().isEmpty()) {
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (nms.isSword(i)) {
                    ItemMeta im = i.getItemMeta();
                    for (TeamEnchant e : getSwordsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
        if (!getArmorsEnchantments().isEmpty()) {
            for (ItemStack i : p.getInventory().getArmorContents()) {
                if (i == null) continue;
                if (nms.isArmor(i)) {
                    ItemMeta im = i.getItemMeta();
                    for (TeamEnchant e : getArmorsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
        Bukkit.getPluginManager().callEvent(new PlayerReSpawnEvent(p, getArena(), this));
        nms.sendPlayerSpawnPackets(p, getArena());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (getArena() != null) {
                nms.sendPlayerSpawnPackets(p, getArena());

                // #274
                for (Player on : getArena().getShowTime().keySet()) {
                    BedWars.nms.hideArmor(on, p);
                }
            }
            //
        }, 10L);

        /*if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_RESPAWN_PACKETS)) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> nms.invisibilityFix(p, getArena()), 12L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> nms.invisibilityFix(p, getArena()), 30L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> arena.getPlayers().forEach(pl -> nms.showPlayer(pl, p)), 25L);
        }*/

        // un-vanish from respawn
        /*Bukkit.getScheduler().runTaskLater(plugin, () -> {
            arena.getPlayers().forEach(pl -> {
                nms.showPlayer(p, pl);
                nms.showArmor(p, pl);
                nms.showPlayer(pl, p);
                nms.showArmor(pl, p);
            });
            arena.getSpectators().forEach(pl -> {
                nms.showPlayer(p, pl);
                nms.showArmor(p, pl);
            });
        }, 20L);*/

        Sounds.playSound("player-re-spawn", p);
    }

    /**
     * Create a leather armor with team's color
     */
    private ItemStack createArmor(Material material) {
        ItemStack i = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) i.getItemMeta();
        lam.setColor(color.bukkitColor());
        nms.setUnbreakable(lam);
        i.setItemMeta(lam);
        return i;
    }

    /**
     * Equip a player with default armor
     */
    public void sendArmor(Player p) {
        if (p.getInventory().getHelmet() == null) p.getInventory().setHelmet(createArmor(Material.LEATHER_HELMET));
        if (p.getInventory().getChestplate() == null)
            p.getInventory().setChestplate(createArmor(Material.LEATHER_CHESTPLATE));
        if (p.getInventory().getLeggings() == null)
            p.getInventory().setLeggings(createArmor(Material.LEATHER_LEGGINGS));
        if (p.getInventory().getBoots() == null) p.getInventory().setBoots(createArmor(Material.LEATHER_BOOTS));
    }

    @Override
    public UUID getIdentity() {
        return identity;
    }

    /**
     * Creates a hologram on the team bed's per player
     */
    @SuppressWarnings("WeakerAccess")
    public class BedHolo {
        private ArmorStand a;
        private UUID p;
        private Arena arena;
        private boolean hidden = false, bedDestroyed = false;

        public BedHolo(@NotNull Player p, Arena arena) {
            this.p = p.getUniqueId();
            this.arena = arena;
            spawn();
            beds.put(p.getUniqueId(), this);
        }

        public void spawn() {
            if (!arena.getConfig().getBoolean(ConfigPath.ARENA_USE_BED_HOLO)) return;
            a = (ArmorStand) bed.getWorld().spawnEntity(bed.getBlock().getLocation().add(+0.5, 1, +0.5), EntityType.ARMOR_STAND);
            a.setGravity(false);
            if (name != null) {
                if (isBedDestroyed()) {
                    a.setCustomName(getMsg(Bukkit.getPlayer(p), Messages.BED_HOLOGRAM_DESTROYED));
                    bedDestroyed = true;
                } else {
                    a.setCustomName(getMsg(Bukkit.getPlayer(p), Messages.BED_HOLOGRAM_DEFEND));
                }
                a.setCustomNameVisible(true);
            }
            a.setRemoveWhenFarAway(false);
            a.setCanPickupItems(false);
            a.setArms(false);
            a.setBasePlate(false);
            a.setMarker(true);
            a.setVisible(false);
            for (Player p2 : arena.getWorld().getPlayers()) {
                if (p != p2.getUniqueId()) {
                    nms.hideEntity(a, p2);
                }
            }
        }

        public void hide() {
            if (!arena.getConfig().getBoolean(ConfigPath.ARENA_USE_BED_HOLO)) return;
            if (bedDestroyed) return;
            hidden = true;
            a.remove();
        }

        public void destroy() {
            if (!arena.getConfig().getBoolean(ConfigPath.ARENA_USE_BED_HOLO)) return;
            a.remove();
            beds.remove(p);
        }

        public void show() {
            if (!arena.getConfig().getBoolean(ConfigPath.ARENA_USE_BED_HOLO)) return;
            hidden = false;
            spawn();
        }

        public Arena getArena() {
            return arena;
        }

        public boolean isHidden() {
            return hidden;
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == members
     */
    public void addTeamEffect(PotionEffectType pef, int amp, int duration) {
        getTeamEffects().add(new PotionEffect(pef, duration, amp));
        for (Player p : getMembers()) {
            p.addPotionEffect(new PotionEffect(pef, duration, amp), true);
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == base
     */
    public void addBaseEffect(PotionEffectType pef, int amp, int duration) {
        getBaseEffects().add(new PotionEffect(pef, duration, amp));
        for (Player p : new ArrayList<>(getMembers())) {
            if (p.getLocation().distance(getBed()) <= getArena().getIslandRadius()) {
                for (PotionEffect e : getBaseEffects()) {
                    p.addPotionEffect(e, true);
                }
            }
        }
    }

    /**
     * Used when someone buys a bew enchantment with apply == bow
     */
    public void addBowEnchantment(Enchantment e, int a) {
        getBowsEnchantments().add(new Enchant(e, a));
        for (Player p : getMembers()) {
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (i.getType() == Material.BOW) {
                    ItemMeta im = i.getItemMeta();
                    im.addEnchant(e, a, true);
                    i.setItemMeta(im);
                }
            }
            p.updateInventory();
        }
    }

    /**
     * Used when someone buys a new enchantment with apply == sword
     */
    public void addSwordEnchantment(Enchantment e, int a) {
        getSwordsEnchantments().add(new Enchant(e, a));
        for (Player p : getMembers()) {
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (nms.isSword(i) || nms.isAxe(i)) {
                    ItemMeta im = i.getItemMeta();
                    im.addEnchant(e, a, true);
                    i.setItemMeta(im);
                }
            }
            p.updateInventory();
        }
    }

    /**
     * Used when someone buys a new enchantment with apply == armor
     */
    public void addArmorEnchantment(Enchantment e, int a) {
        getArmorsEnchantments().add(new Enchant(e, a));
        for (Player p : getMembers()) {
            for (ItemStack i : p.getInventory().getArmorContents()) {
                if (i == null) continue;
                if (nms.isArmor(i)) {
                    ItemMeta im = i.getItemMeta();
                    im.addEnchant(e, a, true);
                    i.setItemMeta(im);
                }
            }
            p.updateInventory();
        }

        // #274
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
            for (Player m : getMembers()) {
                if (m.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    for (Player p : getArena().getPlayers()) {
                        BedWars.nms.hideArmor(m, p);
                    }
                    for (Player p : getArena().getSpectators()) {
                        BedWars.nms.hideArmor(m, p);
                    }
                }
            }
        }, 20L);
    }

    /**
     * Enchantments for bows, swords and armors from the team upgrades
     */
    public static class Enchant implements TeamEnchant {
        Enchantment enchantment;
        int amplifier;

        public Enchant(Enchantment enchantment, int amplifier) {
            this.enchantment = enchantment;
            this.amplifier = amplifier;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public int getAmplifier() {
            return amplifier;
        }
    }

    public boolean isMember(Player u) {
        if (u == null) return false;
        return members.contains(u);
    }

    /**
     * Getter, setter etc.
     */
    public boolean wasMember(UUID u) {
        if (u == null) return false;
        for (Player p : membersCache) {
            if (p.getUniqueId().equals(u)) return true;
        }
        return false;
    }

    public boolean isBedDestroyed() {
        return bedDestroyed;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getShop() {
        return shop;
    }

    public Location getTeamUpgrades() {
        return teamUpgrades;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName(Language language) {
        String m = language.m(ConfigPath.TEAM_NAME_PATH.replace("{arena}", getArena().getArenaName()).replace("{team}", getName()));
        return m == null ? getName() : m;
    }

    public TeamColor getColor() {
        return color;
    }

    public List<Player> getMembers() {
        return members;
    }

    public Location getBed() {
        return bed;
    }

    @Override
    public ConcurrentHashMap<String, Integer> getTeamUpgradeTiers() {
        return teamUpgradeList;
    }

    public BedHolo getBedHolo(@NotNull Player p) {
        return beds.get(p.getUniqueId());
    }

    /**
     * Destroy the bed for a team.
     */
    public void setBedDestroyed(boolean bedDestroyed) {
        this.bedDestroyed = bedDestroyed;
        if (!bedDestroyed) {
            if (!getBed().getBlock().getType().toString().contains("BED")) {
                BedWars.plugin.getLogger().severe("Bed not set for team: " + getName() + " in arena: " + getArena().getArenaName());
                return;
            }
            nms.colorBed(this);
        } else {
            bed.getBlock().setType(Material.AIR);
        }
        for (BedHolo bh : beds.values()) {
            bh.hide();
            bh.show();
        }

    }

    @Deprecated
    public IGenerator getIronGenerator() {
        IGenerator[] gens = (IGenerator[]) generators.stream().filter(f -> f.getType() == GeneratorType.IRON).toArray();
        if (gens.length == 0) return null;
        return gens[0];
    }

    @Deprecated
    public IGenerator getGoldGenerator() {
        IGenerator[] gens = (IGenerator[]) generators.stream().filter(f -> f.getType() == GeneratorType.GOLD).toArray();
        if (gens.length == 0) return null;
        return gens[0];
    }

    @Deprecated
    public IGenerator getEmeraldGenerator() {
        IGenerator[] gens = (IGenerator[]) generators.stream().filter(f -> f.getType() == GeneratorType.EMERALD).toArray();
        if (gens.length == 0) return null;
        return gens[0];
    }

    @Deprecated
    public void setEmeraldGenerator(IGenerator emeraldGenerator) {
        generators.add(emeraldGenerator);
    }

    @Override
    public List<IGenerator> getGenerators() {
        return generators;
    }


    public List<PotionEffect> getBaseEffects() {
        return base;
    }

    public List<PotionEffect> getTeamEffects() {
        return teamEffects;
    }

    public List<TeamEnchant> getBowsEnchantments() {
        return bowsEnchantments;
    }

    public List<TeamEnchant> getSwordsEnchantments() {
        return swordsEnchantemnts;
    }

    public List<TeamEnchant> getArmorsEnchantments() {
        return armorsEnchantemnts;
    }

    public Arena getArena() {
        return arena;
    }

    public int getDragons() {
        return dragons;
    }

    @Override
    public void setDragons(int amount) {
        this.dragons = amount;
    }

    public List<Player> getMembersCache() {
        return membersCache;
    }

    public HashMap<UUID, BedHolo> getBeds() {
        return beds;
    }

    public void destroyData() {
        members = null;
        spawn = null;
        bed = null;
        shop = null;
        teamUpgrades = null;
        for (IGenerator ig : new ArrayList<>(generators)) {
            ig.destroyData();
        }
        arena = null;
        teamEffects = null;
        base = null;
        bowsEnchantments = null;
        swordsEnchantemnts = null;
        armorsEnchantemnts = null;
        enemyBaseEnterTraps.clear();
        membersCache = null;
    }

    @Override
    public void destroyBedHolo(@NotNull Player player) {
        if (getBeds().get(player.getUniqueId()) != null) getBeds().get(player.getUniqueId()).destroy();
    }

    @Override
    public LinkedList<EnemyBaseEnterTrap> getActiveTraps() {
        return enemyBaseEnterTraps;
    }

    @Override
    public Vector getKillDropsLocation() {
        if (killDropsLoc == null) {
            List<IGenerator> gen = generators.stream().filter(p -> (p.getType() == GeneratorType.IRON || p.getType() == GeneratorType.GOLD)).collect(Collectors.toList());
            if (gen.isEmpty()) return new Vector(getSpawn().getX(), getSpawn().getY(), getSpawn().getZ());
            return new Vector(gen.get(0).getLocation().getX(), gen.get(0).getLocation().getY(), gen.get(0).getLocation().getZ());
        }
        return killDropsLoc;
    }

    @Override
    public void setKillDropsLocation(Vector loc) {
        if (loc == null) {
            this.killDropsLoc = null;
            return;
        }
        this.killDropsLoc = new Vector(loc.getBlockX() + 0.5, loc.getBlockY(), loc.getBlockZ() + 0.5);
    }

    @Override
    public boolean isBed(@NotNull Location location) {
        for (int x = location.getBlockX() - 1; x < location.getBlockX() + 1; x++) {
            for (int z = location.getBlockZ() - 1; z < location.getBlockZ() + 1; z++) {
                if (getBed().getBlockX() == x && getBed().getBlockY() == location.getBlockY() && getBed().getBlockZ() == z) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setKillDropsLocation(Location loc) {
        if (loc == null) {
            this.killDropsLoc = null;
            return;
        }
        this.killDropsLoc = new Vector(loc.getBlockX() + 0.5, loc.getBlockY(), loc.getBlockZ() + 0.5);
    }
}
