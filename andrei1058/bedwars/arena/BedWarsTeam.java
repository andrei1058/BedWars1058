package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GeneratorType;
import com.andrei1058.bedwars.api.TeamColor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
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
    /** slot, tier*/
    private HashMap<Integer, Integer> upgradeTier = new HashMap<>();
    private List<Effect> teamEffects = new ArrayList<>();
    private List<Effect> base = new ArrayList<>();
    private List<Effect> enemyBaseEnter = new ArrayList<>();
    private List<Player> potionEffectApplied = new ArrayList<>();

    public BedWarsTeam(String name, TeamColor color, Location spawn, Location bed, Location shop, Location teamUpgrades, Arena arena) {
        this.name = name;
        this.color = color;
        this.spawn = spawn; //todo protect spawn radius
        this.bed = bed; //todo bed radius mic hide hologram per player, bed radius mare for it's a trap
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

    public void addPlayers(Player... players) {
        for (Player p : players) {
            if (!members.contains(p)) members.add(p);
            new BedHolo(p);
            p.teleport(spawn);
            PlayerVault v;
            if (getVault(p) == null){
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
            //p.setPlayerListName(lang.m(lang.tablistFormat).replace("{TeamColor}", TeamColor.getChatColor(color).toString()).replace("{TeamLetter}", name.substring(0, 1).toUpperCase())
            //.replace("{TeamName}", name).replace("{PlayerName}", p.getName()).replace("{PlayerHealth}", String.valueOf((int)p.getHealth())));
        }
    }

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
                        } catch (Exception ex){
                            plugin.getLogger().severe(parm[1]+" is not an integer at: "+s+" (config)");
                            continue;
                        }
                        i = new ItemStack(Material.valueOf(parm[0]), Integer.valueOf(parm[1]));
                    } else {
                        i = new ItemStack(Material.valueOf(parm[0]));
                    }
                    if (parm.length > 2){
                        try {
                            Integer.parseInt(parm[2]);
                        } catch (Exception ex){
                            plugin.getLogger().severe(parm[2]+" is not an integer at: "+s+" (config)");
                            continue;
                        }
                        i.setAmount(Integer.valueOf(parm[2]));
                    }
                    ItemMeta im = i.getItemMeta();
                    if (parm.length > 3){
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

    public void setGenerators(Location ironGenerator, Location goldGenerator) {
        this.ironGenerator = new OreGenerator(ironGenerator, arena, GeneratorType.IRON);
        this.goldGenerator = new OreGenerator(goldGenerator, arena, GeneratorType.GOLD);
    }

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

    public void respawnMember(Player p) {
        nms.sendTitle(p, getMsg(p, lang.respawnedTitle), null, 0, 20, 0);
        //todo da-i busola
        PlayerVault pv = getVault(p);
        if (pv != null) {
            p.getInventory().setHelmet(pv.getHelmet());
            p.getInventory().setChestplate(pv.getChestplate());
            p.getInventory().setLeggings(pv.getPants());
            p.getInventory().setBoots(pv.getBoots());
            for (ItemStack i : pv.getInvItems()){
                p.getInventory().addItem(i);
            }
        } else {
            sendArmor(p);
            sendDefaultInventory(p);
        }
        p.setHealth(20);
    }

    private ItemStack createArmor(Material material) {
        ItemStack i = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta) i.getItemMeta();
        lam.setColor(TeamColor.getColor(color));
        lam.spigot().setUnbreakable(true);
        i.setItemMeta(lam);
        return i;
    }

    private void sendArmor(Player p) {
        p.getInventory().setHelmet(createArmor(Material.LEATHER_HELMET));
        p.getInventory().setChestplate(createArmor(Material.LEATHER_CHESTPLATE));
        p.getInventory().setLeggings(createArmor(Material.LEATHER_LEGGINGS));
        p.getInventory().setBoots(createArmor(Material.LEATHER_BOOTS));
    }

    private static HashMap<Player, BedHolo> beds = new HashMap<>();

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
            a = (ArmorStand) bed.getWorld().spawnEntity(bed.clone().add(0, -1, 0), EntityType.ARMOR_STAND);
            a.setGravity(false);
            if (name != null) {
                if (isBedDestroyed()) {
                    a.setCustomName(getMsg(p, lang.bedHologramDestroyed));
                } else {
                    a.setCustomName(getMsg(p, lang.bedHologram));
                }
                a.setCustomNameVisible(true);
            }
            a.setRemoveWhenFarAway(false);
            a.setVisible(false);
            a.setCanPickupItems(false);
            a.setArms(false);
            a.setBasePlate(false);
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

    public Location getBed() {
        return bed;
    }

    public BedHolo getBedHolo(Player p) {
        return beds.get(p);
    }

    public void setBedDestroyed(boolean bedDestroyed) {
        this.bedDestroyed = bedDestroyed;
        if (bed.getBlock().getType() == Material.AIR && !bedDestroyed) {
            bed.getBlock().setType(Material.BED_BLOCK);
        } else if (bedDestroyed && bed.getBlock().getType() == Material.BED_BLOCK) {
            bed.getBlock().setType(Material.AIR);
        }
        for (BedHolo bh : beds.values()){
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

    public void addTeamEffect(String name, PotionEffectType pef, int amp){
        getTeamEffects().add(new BedWarsTeam.Effect(name, pef, amp));
        for (Player p : getMembers()){
            p.addPotionEffect(new PotionEffect(pef, Integer.MAX_VALUE, amp));
        }
    }

    public void addBaseEffect(String name, PotionEffectType pef, int amp){
        getBaseEffects().add(new BedWarsTeam.Effect(name, pef, amp));
    }

    public void addEnemyBaseEnterEffect(String name, PotionEffectType pef, int amp){
        getBaseEffects().add(new BedWarsTeam.Effect(name, pef, amp));
    }

    private static List<PlayerVault> vaults = new ArrayList<>();

    public class PlayerVault {
        Player p;
        ItemStack pants = createArmor(Material.LEATHER_LEGGINGS), boots = createArmor(Material.LEATHER_BOOTS), chestplate = createArmor(Material.LEATHER_CHESTPLATE), helmet = createArmor(Material.LEATHER_HELMET);
        List<ItemStack> invItems = new ArrayList<>();

        public PlayerVault(Player p) {
            this.p = p;
            vaults.add(this);
        }

        public void addInvItem(ItemStack i){
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


    public class Effect {
        String name;
        PotionEffectType potionEffectType;
        int amplifier;
        public Effect(String name, PotionEffectType potionEffectType, int amplifier){
            this.name = name;
            this.potionEffectType = potionEffectType;
            this.amplifier = amplifier;
        }

        public PotionEffectType getPotionEffectType() {
            return potionEffectType;
        }

        public int getAmplifier() {
            return amplifier;
        }
    }

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
}
