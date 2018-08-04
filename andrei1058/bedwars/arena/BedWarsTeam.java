package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GeneratorType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Colorable;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class BedWarsTeam {

    private List<Player> members = new ArrayList<>();
    private TeamColor color;
    private Location spawn, bed, shop, teamUpgrades;
    private com.andrei1058.bedwars.arena.OreGenerator ironGenerator, goldGenerator, emeraldGenerator;
    private String name;
    private Arena arena;
    private boolean bedDestroyed = false;
    private int dragons = 1;
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
     * Players with potions applied such as when they enter the base and then they leave it will remove the potion effects
     */
    private List<Player> potionEffectApplied = new ArrayList<>();
    /**
     * Enchantments for bows
     */
    private List<Enchant> bowsEnchantments = new ArrayList<>();
    /**
     * Enchantments for swords
     */
    private List<Enchant> swordsEnchantemnts = new ArrayList<>();
    /**
     * Enchantments for armors
     */
    private List<Enchant> armorsEnchantemnts = new ArrayList<>();
    /**
     * Used for show/ hide bed hologram
     */
    private static HashMap<Player, BedHolo> beds = new HashMap<>();
    /**
     * Used for it's a trap
     */
    private boolean trapActive = false, trapChat = false, trapAction = false, trapTitle = false, trapSubtitle;
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

    /**
     * Player cache, used for loosers stats and rejoin
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
        if (bed.getBlock().getType() != Material.BED_BLOCK) {
            bed.getBlock().setType(Material.BED_BLOCK);
        }
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
            new BedHolo(p);
        }
    }

    /**
     * first spawn
     */
    public void firstSpawn(Player p) {
        p.teleport(spawn);
        PlayerVault v;
        if (getVault(p) == null) {
            v = new PlayerVault(p);
        } else {
            v = getVault(p);
            v.invItems.clear();
        }
        v.setHelmet(createArmor(Material.LEATHER_HELMET));
        v.setChestplate(createArmor(Material.LEATHER_CHESTPLATE));
        v.setPants(createArmor(Material.LEATHER_LEGGINGS));
        v.setBoots(createArmor(Material.LEATHER_BOOTS));
        sendDefaultInventory(p);
    }

    /**
     * Gives the start inventory
     */
    public void sendDefaultInventory(Player p) {
        p.getInventory().clear();
        for (String s : config.getYml().getStringList("startItems")) {
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
                        i = new ItemStack(Material.valueOf(parm[0]), Integer.valueOf(parm[1]));
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
                        i.setAmount(Integer.valueOf(parm[2]));
                    }
                    ItemMeta im = i.getItemMeta();
                    if (parm.length > 3) {
                        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', parm[3]));
                    }
                    im.spigot().setUnbreakable(true);
                    i.setItemMeta(im);
                    p.getInventory().addItem(i);
                    if (nms.isSword(i)) {
                        if (getVault(p) != null) {
                            getVault(p).addInvItem(i);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
        sendArmor(p);
    }

    /**
     * Spawn the iron and gold generators
     */
    public void setGenerators(Location ironGenerator, Location goldGenerator) {
        this.ironGenerator = new OreGenerator(ironGenerator, arena, GeneratorType.IRON);
        this.goldGenerator = new OreGenerator(goldGenerator, arena, GeneratorType.GOLD);
    }

    /**
     * Respawn a member
     */
    public void respawnMember(Player p) {
        if (Arena.respawn.containsKey(p)) {
            Arena.respawn.remove(p);
        }
        p.teleport(getSpawn());
        if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        nms.setCollide(p, true);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.setHealth(20);
        for (Player on : arena.getPlayers()) {
            if (p == on) continue;
            on.showPlayer(p);
            nms.showPlayer(p, on);
        }
        for (Player on : arena.getSpectators()) {
            if (p == on) continue;
            on.showPlayer(p);
            nms.showPlayer(p, on);
        }
        nms.sendTitle(p, getMsg(p, Messages.PLAYER_DIE_RESPAWNED_TITLE), "", 0, 20, 0);
        PlayerVault pv = getVault(p);
        if (pv != null) {
            p.getInventory().setHelmet(pv.getHelmet());
            p.getInventory().setChestplate(pv.getChestplate());
            p.getInventory().setLeggings(pv.getPants());
            p.getInventory().setBoots(pv.getBoots());
            for (ItemStack i : pv.getInvItems()) {
                p.getInventory().addItem(i);
            }
        } else {
            sendArmor(p);
            sendDefaultInventory(p);
        }
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
                    for (Enchant e : getBowsEnchantments()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
        if (!getSwordsEnchantemnts().isEmpty()) {
            for (ItemStack i : p.getInventory().getContents()) {
                if (i == null) continue;
                if (nms.isSword(i)) {
                    ItemMeta im = i.getItemMeta();
                    for (Enchant e : getSwordsEnchantemnts()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
        if (!getArmorsEnchantemnts().isEmpty()) {
            for (ItemStack i : p.getInventory().getArmorContents()) {
                if (i == null) continue;
                if (nms.isArmor(i)) {
                    ItemMeta im = i.getItemMeta();
                    for (Enchant e : getArmorsEnchantemnts()) {
                        im.addEnchant(e.getEnchantment(), e.getAmplifier(), true);
                    }
                    i.setItemMeta(im);
                }
                p.updateInventory();
            }
        }
    }

    /**
     * Create a leather armor with team's color
     */
    private ItemStack createArmor(Material material) {
        ItemStack i = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) i.getItemMeta();
        lam.setColor(TeamColor.getColor(color));
        lam.spigot().setUnbreakable(true);
        i.setItemMeta(lam);
        return i;
    }

    /**
     * Equip a player with default armor
     */
    public void sendArmor(Player p) {
        p.getInventory().setHelmet(createArmor(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(createArmor(Material.LEATHER_CHESTPLATE));
        p.getInventory().setLeggings(createArmor(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(createArmor(Material.LEATHER_BOOTS));
    }

    /**
     * Creates a hologram on the team bed's per player
     */
    public class BedHolo {
        ArmorStand a;
        Player p;
        boolean hidden = false;

        public BedHolo(Player p) {
            this.p = p;
            spawn();
            beds.put(p, this);
        }

        public void spawn() {
            a = (ArmorStand) bed.getWorld().spawnEntity(bed.clone().add(0, 0.5, 0), EntityType.ARMOR_STAND);
            a.setGravity(false);
            if (name != null) {
                if (isBedDestroyed()) {
                    a.setCustomName(getMsg(p, Messages.BED_HOLOGRAM_DESTROYED));
                } else {
                    a.setCustomName(getMsg(p, Messages.BED_HOLOGRAM_DEFEND));
                }
                a.setCustomNameVisible(true);
            }
            a.setRemoveWhenFarAway(false);
            a.setVisible(false);
            a.setCanPickupItems(false);
            a.setArms(false);
            a.setBasePlate(false);
            a.setMarker(true);
            for (Player p2 : arena.getWorld().getPlayers()) {
                if (p != p2) {
                    nms.hideEntity(a, p2);
                }
            }
        }

        public void hide() {
            hidden = true;
            a.remove();
        }

        public void destroy() {
            a.remove();
        }

        public void show() {
            hidden = false;
            spawn();
        }

        public boolean isHidden() {
            return hidden;
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == members
     */
    public void addTeamEffect(PotionEffectType pef, int amp, int duration) {
        getTeamEffects().add(new BedWarsTeam.Effect(pef, amp, duration));
        for (Player p : getMembers()) {
            p.addPotionEffect(new PotionEffect(pef, Integer.MAX_VALUE, amp));
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == base
     */
    public void addBaseEffect(PotionEffectType pef, int amp, int duration) {
        getBaseEffects().add(new BedWarsTeam.Effect(pef, amp, duration));
        for (Player p : getMembers()) {
            if (p.getLocation().distance(getSpawn()) <= arena.getIslandRadius()) {
                p.addPotionEffect(new PotionEffect(pef, amp, duration));
            }
            if (!getPotionEffectApplied().contains(p)) {
                getPotionEffectApplied().add(p);
            }
        }
    }

    /**
     * Used when someone buys a new potion effect with apply == enemyBaseEnter
     */
    public void addEnemyBaseEnterEffect(PotionEffectType pef, int amp, int slot, int duration) {
        Effect e = new BedWarsTeam.Effect(pef, amp, duration);
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
        getSwordsEnchantemnts().add(new Enchant(e, a));
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
        getArmorsEnchantemnts().add(new Enchant(e, a));
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
    }

    private static List<PlayerVault> vaults = new ArrayList<>();

    /**
     * It contains items bought by a player from shop with permanent == true
     */
    public class PlayerVault {
        Player p;
        ItemStack pants = createArmor(Material.LEATHER_LEGGINGS), boots = createArmor(Material.LEATHER_BOOTS), chestplate = createArmor(Material.LEATHER_CHESTPLATE), helmet = createArmor(Material.LEATHER_HELMET);
        List<ItemStack> invItems = new ArrayList<>();

        public PlayerVault(Player p) {
            this.p = p;
            vaults.add(this);
        }

        public void addInvItem(ItemStack i) {
            invItems.add(i);
        }

        public void setPants(ItemStack pants) {
            this.pants = pants;
        }

        public void setBoots(ItemStack boots) {
            this.boots = boots;
        }

        public List<ItemStack> getInvItems() {
            return invItems;
        }

        public void setChestplate(ItemStack chestplate) {
            this.chestplate = chestplate;
        }

        public void setHelmet(ItemStack helmet) {
            this.helmet = helmet;
        }

        public ItemStack getHelmet() {
            return helmet;
        }

        public ItemStack getChestplate() {
            return chestplate;
        }

        public ItemStack getBoots() {
            return boots;
        }

        public ItemStack getPants() {
            return pants;
        }
    }

    /**
     * Potion effects from the team upgrades shop
     */
    public class Effect {
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
    public class Enchant {
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

    /**
     * Gets the player's inventory like a keepInventory
     */
    @Nullable
    @Contract(pure = true)
    public static PlayerVault getVault(Player p) {
        for (PlayerVault v : vaults) {
            if (v.p == p) {
                return v;
            }
        }
        return null;
    }

    /**
     * Getter, setter etc.
     */
    public boolean isMember(Player p) {
        return members.contains(p);
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

    private ItemStack createColorItem(Material material, int amount) {
        ItemStack i = new ItemStack(material, amount, TeamColor.itemColor(color));
        return i;
    }

    public List<Player> getPotionEffectApplied() {
        return potionEffectApplied;
    }

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
     * Since API 8 it will also remove the team's generators if true in config.
     */
    public void setBedDestroyed(boolean bedDestroyed) {
        this.bedDestroyed = bedDestroyed;
        if (!bedDestroyed) {
            if (bed.getBlock().getType() == arena.getBedBlock()) {
                if (getBed().getBlock().getType() == Material.BED_BLOCK) {
                    nms.colorBed(this, getBed().getBlock().getState());
                    for (int x = -1; x < 2; x++) {
                        for (int z = -1; z < 2; z++) {
                            Block b = getBed().clone().add(x, 0, z).getBlock();
                            if (b.getType() != Material.BED_BLOCK) continue;
                            nms.colorBed(this, b.getState());
                        }
                    }
                }
            } else {
                if (arena.getBedBlock() == Material.BED_BLOCK) {
                    if (Misc.getDirection(getBed()) == BlockFace.WEST || Misc.getDirection(getBed()) == BlockFace.EAST) {
                        BlockState baseState = getBed().getBlock().getState();
                        BlockState localBlockState = getBed().clone().add(-1, 0, 0).getBlock().getState();
                        baseState.setType(Material.BED_BLOCK);
                        localBlockState.setType(Material.BED_BLOCK);
                        baseState.setRawData((byte) 0x05);
                        localBlockState.setRawData((byte) 0x09);
                        baseState.update(true, false);
                        localBlockState.update(true, false);
                        nms.colorBed(this, getBed().getBlock().getState());
                        nms.colorBed(this, getBed().clone().add(-1, 0, 0).getBlock().getState());
                    } else {
                        BlockState baseState = getBed().getBlock().getState();
                        BlockState localBlockState = getBed().clone().add(0, 0, -1).getBlock().getState();
                        baseState.setType(Material.BED_BLOCK);
                        localBlockState.setType(Material.BED_BLOCK);
                        baseState.setRawData((byte) 0x08);
                        localBlockState.setRawData((byte) 0x00);
                        baseState.update(true, false);
                        localBlockState.update(true, false);
                        nms.colorBed(this, getBed().getBlock().getState());
                        nms.colorBed(this, getBed().clone().add(0, 0, -1).getBlock().getState());
                    }
                } else {
                    bed.getBlock().setType(arena.getBedBlock());
                }
            }

        } else if (bedDestroyed) {
            bed.getBlock().setType(Material.AIR);
            if (getArena().getCm().getBoolean(ConfigPath.ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS)){
                OreGenerator.getGenerators().remove(getGoldGenerator());
                OreGenerator.getGenerators().remove(getIronGenerator());
            }
        }
        for (BedHolo bh : beds.values()) {
            bh.hide();
            bh.show();
        }

    }

    public OreGenerator getIronGenerator() {
        return ironGenerator;
    }

    public OreGenerator getGoldGenerator() {
        return goldGenerator;
    }

    public OreGenerator getEmeraldGenerator() {
        return emeraldGenerator;
    }

    public void setEmeraldGenerator(OreGenerator emeraldGenerator) {
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

    public List<Enchant> getBowsEnchantments() {
        return bowsEnchantments;
    }

    public List<Enchant> getSwordsEnchantemnts() {
        return swordsEnchantemnts;
    }

    public List<Enchant> getArmorsEnchantemnts() {
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
            if (getUpgradeTier().containsKey(i)) {
                getUpgradeTier().remove(i);
            }
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
}
