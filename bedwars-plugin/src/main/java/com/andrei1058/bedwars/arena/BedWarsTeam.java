package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.arena.team.TeamEnchant;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerFirstSpawnEvent;
import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.region.Cuboid;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.shop.ShopCache;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

@SuppressWarnings("WeakerAccess")
public class BedWarsTeam implements ITeam {

    private List<Player> members = new ArrayList<>();
    private TeamColor color;
    private Location spawn, bed, shop, teamUpgrades;
    private IGenerator ironGenerator = null, goldGenerator = null, emeraldGenerator = null;
    private String name;
    private Arena arena;
    private boolean bedDestroyed = false;

    /**
     * slot, tier
     */
    private HashMap<Integer, Integer> upgradeTier = new HashMap<>();
    /**
     * Potion effects for teammates from the upgrades
     */
    private List<Effect> teamEffects = new ArrayList<>();
    /**
     * Potion effects for teammates on base only
     */
    private List<Effect> base = new ArrayList<>();
    /**
     * Potion effects for enemies when they enter in this team's base
     */
    private List<Effect> enemyBaseEnter = new ArrayList<>();

    /**
     * Enchantments for bows
     */
    private List<TeamEnchant> bowsEnchantments = new ArrayList<>();
    /**
     * Enchantments for swords
     */
    private List<TeamEnchant> swordsEnchantemnts = new ArrayList<>();
    /**
     * Enchantments for armors
     */
    private List<TeamEnchant> armorsEnchantemnts = new ArrayList<>();
    /**
     * Used for show/ hide bed hologram
     */
    private HashMap<Player, BedHolo> beds = new HashMap<>();
    /**
     * Used for it's a trap
     */
    private boolean trapActive = false, trapChat = false, trapAction = false, trapTitle = false, trapSubtitle = false;
    private List<Integer> trapSlots = new ArrayList<>();
    /**
     * One time upgrades with effects slots
     */
    private List<Integer> enemyBaseEnterSlots = new ArrayList<>();
    /**
     * A list with all potions for clear them when someone leaves the island
     */
    private List<Effect> ebseEffectsStatic = new ArrayList<>();

    /**
     * A list with team's dragons  at Sudden Death phase
     */
    private int dragons = 1;

    /**
     * Player cache, used for losers stats and rejoin
     */
    private List<Player> membersCache = new ArrayList<>();

    public BedWarsTeam(String name, TeamColor color, Location spawn, Location bed, Location shop, Location teamUpgrades, Arena arena) {
        this.name = name;
        this.color = color;
        this.spawn = spawn;
        this.bed = bed;
        this.arena = arena;
        this.shop = shop;
        this.teamUpgrades = teamUpgrades;

        arena.getRegionsList().add(new Cuboid(spawn, arena.getConfig().getInt(ConfigPath.ARENA_SPAWN_PROTECTION), true));
    }

    public int getSize() {
        return members.size();
    }

    /**
     * Add a new member to the team
     */
    public void addPlayers(Player... players) {
        for (Player p : players) {
            if (!members.contains(p)) members.add(p);
            if (!membersCache.contains(p)) membersCache.add(p);
            new BedHolo(p, getArena());
        }
    }

    /**
     * first spawn
     */
    public void firstSpawn(Player p) {
        p.teleport(spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
        p.setGameMode(GameMode.SURVIVAL);
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
        }, 70L);

        Cuboid c1 = new Cuboid(getArena().getConfig().getArenaLoc("Team." + getName() + ".Upgrade"), 1, true);
        c1.setMinY(c1.getMinY() - 1);
        c1.setMaxY(c1.getMaxY() + 4);
        getArena().getRegionsList().add(c1);

        Cuboid c2 = new Cuboid(getArena().getConfig().getArenaLoc("Team." + getName() + ".Shop"), 1, true);
        c2.setMinY(c2.getMinY() - 1);
        c2.setMaxY(c2.getMaxY() + 4);
        getArena().getRegionsList().add(c2);
    }

    /**
     * Rejoin a team
     */
    public void reJoin(Player p) {
        members.add(Bukkit.getPlayer(p.getUniqueId()));
        Bukkit.getScheduler().runTaskLater(plugin, () -> nms.hidePlayer(p, arena.getPlayers()), 5L);
        nms.setCollide(p, arena, false);
        p.setAllowFlight(true);
        p.setFlying(true);
        arena.getRespawn().put(p, 5);
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

    /**
     * Restore lost sword.
     */
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
     * Spawn the iron and gold generators
     */
    public void setGenerators(Location ironGenerator, Location goldGenerator) {
        getArena().getOreGenerators().add(this.ironGenerator = new OreGenerator(ironGenerator, arena, GeneratorType.IRON, this));
        getArena().getOreGenerators().add(this.goldGenerator = new OreGenerator(goldGenerator, arena, GeneratorType.GOLD, this));
    }

    /**
     * Respawn a member
     */
    public void respawnMember(Player p) {
        getArena().getRespawn().remove(p);
        p.teleport(getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        nms.setCollide(p, arena, true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setHealth(20);

        nms.sendTitle(p, getMsg(p, Messages.PLAYER_DIE_RESPAWNED_TITLE), "", 0, 20, 0);
        ShopCache sc = ShopCache.getShopCache(p.getUniqueId());
        if (sc != null) {
            sc.managePermanentsAndDowngradables(getArena());
        }
        sendDefaultInventory(p, false);
        p.setHealth(20);
        if (!getBaseEffects().isEmpty()) {
            for (BedWarsTeam.Effect ef : getBaseEffects()) {
                p.addPotionEffect(new PotionEffect(ef.getPotionEffectType(), ef.getDuration(), ef.getAmplifier()));
            }
        }
        if (!getTeamEffects().isEmpty()) {
            for (BedWarsTeam.Effect ef : getTeamEffects()) {
                p.addPotionEffect(new PotionEffect(ef.getPotionEffectType(), ef.getDuration(), ef.getAmplifier()));
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

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // #274
            if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_ARMOR_PACKETS)) {
                for (Player on : getArena().getShowTime().keySet()) {
                    BedWars.nms.hideArmor(on, p);
                }
            }
            //
        }, 5L);

        /*if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_RESPAWN_PACKETS)) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> nms.invisibilityFix(p, getArena()), 12L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> nms.invisibilityFix(p, getArena()), 30L);
            Bukkit.getScheduler().runTaskLater(plugin, () -> arena.getPlayers().forEach(pl -> nms.showPlayer(pl, p)), 25L);
        }*/

        // un-vanish from respawn
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
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
        }, 10L);

        Sounds.playSound("player-re-spawn", p);
    }

    /**
     * Create a leather armor with team's color
     */
    private ItemStack createArmor(Material material) {
        ItemStack i = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) i.getItemMeta();
        lam.setColor(TeamColor.getColor(color));
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

    /**
     * Creates a hologram on the team bed's per player
     */
    @SuppressWarnings("WeakerAccess")
    public class BedHolo {
        private ArmorStand a;
        private Player p;
        private Arena arena;
        private boolean hidden = false, bedDestroyed = false;

        public BedHolo(Player p, Arena arena) {
            this.p = p;
            this.arena = arena;
            spawn();
            beds.put(p, this);
        }

        public void spawn() {
            if (!arena.getConfig().getBoolean(ConfigPath.ARENA_USE_BED_HOLO)) return;
            a = (ArmorStand) bed.getWorld().spawnEntity(bed.getBlock().getLocation().add(+0.5, 1, +0.5), EntityType.ARMOR_STAND);
            a.setGravity(false);
            if (name != null) {
                if (isBedDestroyed()) {
                    a.setCustomName(getMsg(p, Messages.BED_HOLOGRAM_DESTROYED));
                    bedDestroyed = true;
                } else {
                    a.setCustomName(getMsg(p, Messages.BED_HOLOGRAM_DEFEND));
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
                if (p != p2) {
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
        getTeamEffects().add(new Effect(pef, amp, duration));
        for (Player p : getMembers()) {
            p.addPotionEffect(new PotionEffect(pef, Integer.MAX_VALUE, amp));
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == base
     */
    public void addBaseEffect(PotionEffectType pef, int amp, int duration) {
        getBaseEffects().add(new Effect(pef, amp, duration));
        for (Player p : new ArrayList<>(getMembers())) {
            if (p.getLocation().distance(getBed()) <= getArena().getIslandRadius()) {
                for (Effect e : getBaseEffects()) {
                    p.addPotionEffect(new PotionEffect(e.getPotionEffectType(), e.getDuration(), e.getAmplifier()));
                }
            }
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == enemyBaseEnter
     */
    public void addEnemyBaseEnterEffect(PotionEffectType pef, int amp, int slot, int duration) {
        Effect e = new Effect(pef, amp, duration);
        getEnemyBaseEnter().add(e);
        getEnemyBaseEnterSlots().add(slot);
        getEbseEffectsStatic().add(e);
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
                if (nms.isSword(i)) {
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
        if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_PERFORMANCE_DISABLE_ARMOR_PACKETS)) {
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
        //
    }


    /**
     * Potion effects from the team upgrades shop
     */
    public static class Effect {
        PotionEffectType potionEffectType;
        int amplifier;
        int duration;

        public Effect(PotionEffectType potionEffectType, int amplifier, int duration) {
            this.potionEffectType = potionEffectType;
            this.amplifier = amplifier;
            if (duration < 1) {
                this.duration = Integer.MAX_VALUE;
            } else {
                this.duration = duration;
            }
        }

        public PotionEffectType getPotionEffectType() {
            return potionEffectType;
        }

        public int getAmplifier() {
            return amplifier;
        }

        public int getDuration() {
            return duration;
        }
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

    public TeamColor getColor() {
        return color;
    }

    public List<Player> getMembers() {
        return members;
    }

    /*public List<Player> getPotionEffectApplied() {
        return potionEffectApplied;
    }*/

    /*public void removePotionEffectApplied(Player p) {
        p.sendMessage("removePotionEffectApplied " + p.getName());
        potionEffectApplied.remove(p);
    }*/

    public HashMap<Integer, Integer> getUpgradeTier() {
        return upgradeTier;
    }

    public Location getBed() {
        return bed;
    }

    public BedHolo getBedHolo(Player p) {
        return beds.get(p);
    }

    /**
     * Destroy the bed for a team.
     */
    public void setBedDestroyed(boolean bedDestroyed) {
        this.bedDestroyed = bedDestroyed;
        if (!bedDestroyed) {
            if (!getBed().getBlock().getType().toString().contains("BED")) {
                BedWars.plugin.getLogger().severe("Bed not set for team: " + getName() + " in arena: " + getArena().getWorldName());
                return;
            }
            nms.colorBed(this);
        } else {
            bed.getBlock().setType(Material.AIR);
            if (getArena().getConfig().getBoolean(ConfigPath.ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS)) {
                getGoldGenerator().disable();
                getIronGenerator().disable();
            }
        }
        for (BedHolo bh : beds.values()) {
            bh.hide();
            bh.show();
        }

    }

    public IGenerator getIronGenerator() {
        return ironGenerator;
    }

    public IGenerator getGoldGenerator() {
        return goldGenerator;
    }

    public IGenerator getEmeraldGenerator() {
        return emeraldGenerator;
    }

    public void setEmeraldGenerator(IGenerator emeraldGenerator) {
        this.emeraldGenerator = emeraldGenerator;
    }


    public List<Effect> getBaseEffects() {
        return base;
    }

    public List<Effect> getTeamEffects() {
        return teamEffects;
    }

    public List<Effect> getEnemyBaseEnter() {
        return enemyBaseEnter;
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

    public boolean isTrapActive() {
        return trapActive;
    }

    public void setTrapAction(boolean trapAction) {
        this.trapAction = trapAction;
    }

    public void enableTrap(int slot) {
        this.trapActive = true;
        trapSlots.add(slot);
    }

    public void disableTrap() {
        this.trapActive = false;
        for (Integer i : trapSlots) {
            getUpgradeTier().remove(i);
        }
        trapSlots.clear();
    }

    public void setTrapChat(boolean trapChat) {
        this.trapChat = trapChat;
    }

    public void setTrapSubtitle(boolean trapSubtitle) {
        this.trapSubtitle = trapSubtitle;
    }

    public void setTrapTitle(boolean trapTitle) {
        this.trapTitle = trapTitle;
    }

    public boolean isTrapAction() {
        return trapAction;
    }

    public boolean isTrapChat() {
        return trapChat;
    }

    public boolean isTrapSubtitle() {
        return trapSubtitle;
    }

    public boolean isTrapTitle() {
        return trapTitle;
    }

    public List<Integer> getEnemyBaseEnterSlots() {
        return enemyBaseEnterSlots;
    }

    public List<Effect> getEbseEffectsStatic() {
        return ebseEffectsStatic;
    }

    public Arena getArena() {
        return arena;
    }

    public int getDragons() {
        return dragons;
    }

    public List<Player> getMembersCache() {
        return membersCache;
    }

    public HashMap<Player, BedHolo> getBeds() {
        return beds;
    }

    public void destroyData() {
        members = null;
        spawn = null;
        bed = null;
        shop = null;
        teamUpgrades = null;
        ironGenerator.destroyData();
        ironGenerator = null;
        goldGenerator.destroyData();
        goldGenerator = null;
        if (emeraldGenerator != null) emeraldGenerator.destroyData();
        emeraldGenerator = null;
        arena = null;
        upgradeTier = null;
        teamEffects = null;
        base = null;
        enemyBaseEnter = null;
        bowsEnchantments = null;
        swordsEnchantemnts = null;
        armorsEnchantemnts = null;
        trapSlots = null;
        enemyBaseEnterSlots = null;
        ebseEffectsStatic = null;
        membersCache = null;
    }

    @Override
    public void destroyBedHolo(Player player) {
        if (getBeds().get(player) != null) getBeds().get(player).destroy();
    }
}
