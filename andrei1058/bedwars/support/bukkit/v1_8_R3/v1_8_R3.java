package com.andrei1058.bedwars.support.bukkit.v1_8_R3;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.ShopHolo;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.exceptions.InvalidSoundException;
import com.andrei1058.bedwars.support.bukkit.NMS;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.despawnables.TargetListener.owningTeam;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class v1_8_R3 implements NMS {

    private Sound bedDestroy = Sound.valueOf("ENDERDRAGON_GROWL"), playerKill = Sound.valueOf("WOLF_HURT"),
            countDown = Sound.valueOf("CHICKEN_EGG_POP"), bought = Sound.valueOf("NOTE_STICKS"), insuffMoney = Sound.valueOf("ENDERMAN_TELEPORT");


    /**
     * ArenaList of despawnable entities aka special shop mobs
     */
    private static List<Despawnable> despawnables = new ArrayList();

    @Override
    public Sound bedDestroy() {
        return bedDestroy;
    }

    @Override
    public void setBedDestroySound(String sound) throws InvalidSoundException {
        try {
            bedDestroy = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public Sound playerKill() {
        return playerKill;
    }

    @Override
    public void setPlayerKillsSound(String sound) throws InvalidSoundException {
        try {
            playerKill = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public void hidePlayer(Player player, List<Player> players) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(player.getEntityId());
        for (Player p : players) {
            if (p == player) continue;
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void refreshDespawnables() {
        for (Despawnable d : new ArrayList<>(despawnables)) {
            d.regresh();
        }
    }

    @Override
    public boolean isDespawnable(Entity e) {
        for (Despawnable d : despawnables) {
            if (d.getE() == ((CraftEntity) e).getHandle()) return true;
        }
        return false;
    }

    @Override
    public BedWarsTeam ownDespawnable(Entity e) {

        for (Despawnable d : despawnables) {
            if (d.getE() == ((CraftEntity) e).getHandle()) return d.getTeam();
        }
        return null;
    }

    @Override
    public Sound countdownTick() {
        return countDown;
    }

    @Override
    public void setCountdownSound(String sound) throws InvalidSoundException {
        try {
            countDown = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    public void spawnSilverfish(Location loc, BedWarsTeam bedWarsTeam) {
        new Despawnable(Silverfish.spawn(loc, bedWarsTeam), bedWarsTeam, shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN), Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME);
    }

    @Override
    public void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam) {
        new Despawnable(IGolem.spawn(loc, bedWarsTeam), bedWarsTeam, shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN), Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME);
    }

    @Override
    public Sound insufficientMoney() {
        return insuffMoney;
    }

    @Override
    public void setInsuffMoneySound(String sound) throws InvalidSoundException {
        try {
            insuffMoney = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public Sound bought() {
        return bought;
    }

    @Override
    public void setBoughtSound(String sound) throws InvalidSoundException {
        try {
            bought = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public void registerCommand(String name, Command clasa) {
        ((CraftServer) plugin.getServer()).getCommandMap().register(name, clasa);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title != null) {
            if (!title.isEmpty()) {
                IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
                PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, bc);
                PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
            }
        }
        if (subtitle != null) {
            if (!subtitle.isEmpty()) {
                IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
                PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, bc);
                PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
            }
        }
    }

    @Override
    public void playAction(Player p, String text) {
        CraftPlayer cPlayer = (CraftPlayer) p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        cPlayer.getHandle().playerConnection.sendPacket(ppoc);
    }

    @Override
    public void unregisterCommand(String name) {
        if (isBukkitCommandRegistered(name)) {
            ((CraftServer) plugin.getServer()).getCommandMap().getCommand(name).unregister(((CraftServer) plugin.getServer()).getCommandMap());
        }
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) plugin.getServer()).getCommandMap().getCommand(name) != null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getItemInHand(Player p) {
        return p.getItemInHand();
    }

    @Override
    public void hideEntity(org.bukkit.entity.Entity e, Player p) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(e.getEntityId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

    }

    @Override
    public boolean isArmor(ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor;
    }

    @Override
    public boolean isTool(ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemTool;
    }

    @Override
    public boolean isSword(ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemSword;
    }

    @Override
    public boolean isBow(ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemBow;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof IProjectile;
    }

    @Override
    public void registerEntities() {
        registerEntity("ShopNPC", 120, VillagerShop.class);
        registerEntity("Silverfish2", 60, Silverfish.class);
        registerEntity("IGolem", 99, IGolem.class);
        registerEntity("Dragon", 63, Dragon.class);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setCollide(Player p, Arena a, boolean value) {
        p.spigot().setCollidesWithEntities(value);
    }

    @Override
    public void minusAmount(Player p, ItemStack i, int amount) {
        if (i.getAmount() - amount <= 0) {
            p.getInventory().removeItem(i);
            return;
        }
        i.setAmount(i.getAmount() - amount);
        p.updateInventory();
    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players, Arena arena) {
        Location l = loc.clone();
        spawnVillager(l);
        for (Player p : players) {
            String[] nume = getMsg(p, name1).split(",");
            if (nume.length == 1) {
                ArmorStand a = createArmorStand(nume[0], l.clone().add(0, 1.85, 0));
                new ShopHolo(Language.getPlayerLanguage(p).getIso(), a, null, l, arena);
            } else {
                ArmorStand a = createArmorStand(nume[0], l.clone().add(0, 2.1, 0));
                ArmorStand b = createArmorStand(nume[1], l.clone().add(0, 1.85, 0));
                new ShopHolo(Language.getPlayerLanguage(p).getIso(), a, b, l, arena);
            }
        }
        for (ShopHolo sh : ShopHolo.getShopHolo()) {
            if (sh.getA() == arena) {
                sh.update();
            }
        }
    }

    @Override
    public double getDamage(ItemStack i) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.attackDamage");
    }

    @Override
    public double getProtection(ItemStack i) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.armor");
    }

    private static ArmorStand createArmorStand(String name, Location loc) {
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setGravity(false);
        a.setVisible(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
        return a;
    }


    public void registerEntity(String name, int id, Class customClass) {
        try {
            ArrayList<Map> dataMap = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (!f.getType().getSimpleName().equals(Map.class.getSimpleName())) continue;
                f.setAccessible(true);
                dataMap.add((Map) f.get(null));
            }
            if (dataMap.get(2).containsKey(id)) {
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class VillagerShop extends EntityVillager {
        public VillagerShop(World world) {
            super(world);
            try {
                Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
                bField.setAccessible(true);
                Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
                cField.setAccessible(true);
                bField.set(this.goalSelector, new UnsafeList());
                bField.set(this.targetSelector, new UnsafeList());
                cField.set(this.goalSelector, new UnsafeList());
                cField.set(this.targetSelector, new UnsafeList());
            } catch (Exception bField) {
            }
            this.goalSelector.a(0, new PathfinderGoalFloat(this));
            this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0f, 1.0f));
            this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        }

        @Override
        public void move(double d0, double d1, double d2) {
        }

        @Override
        public void collide(net.minecraft.server.v1_8_R3.Entity entity) {
        }

        @Override
        public boolean damageEntity(DamageSource damagesource, float f) {
            return false;
        }

        @Override
        public void g(double d0, double d1, double d2) {
        }

        @Override
        protected void initAttributes() {
            super.initAttributes();
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0D);
        }
    }

    private Villager spawnVillager(Location loc) {
        WorldServer mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        VillagerShop customEnt = new VillagerShop(mcWorld);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Villager) customEnt.getBukkitEntity();
    }

    private class Despawnable {
        EntityLiving e;
        BedWarsTeam team;
        int despawn = 250;
        String namePath;

        public Despawnable(EntityLiving e, BedWarsTeam team, int despawn, String namePath) {
            this.e = e;
            this.team = team;
            if (despawn != 0) {
                this.despawn = despawn;
            }
            this.namePath = namePath;
            despawnables.add(this);
            setName();
            owningTeam.put(e.getUniqueID(), team.getName());
        }

        public void regresh() {
            if (!e.isAlive()) {
                despawnables.remove(this);
                return;
            }
            setName();
            despawn--;
            if (despawn == 0) {
                e.damageEntity(DamageSource.OUT_OF_WORLD, 9000);
                despawnables.remove(this);
            }
        }

        private void setName() {
            int percentuale = (int) ((e.getHealth() * 100) / e.getMaxHealth() / 10);
            e.setCustomName(lang.m(namePath).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                    new String(new char[percentuale]).replace("\0", lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)) + new String(new char[10 - percentuale]).replace("\0", "§7" + lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH))
            ).replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getName()));
        }

        public EntityLiving getE() {
            return e;
        }

        public BedWarsTeam getTeam() {
            return team;
        }

    }

    @Override
    public void setSource(TNTPrimed tnt, Player owner) {
        EntityLiving nmsEntityLiving = (((CraftLivingEntity) owner).getHandle());
        EntityTNTPrimed nmsTNT = (((CraftTNTPrimed) tnt).getHandle());
        try {
            Field sourceField = EntityTNTPrimed.class.getDeclaredField("source");
            sourceField.setAccessible(true);
            sourceField.set(nmsTNT, nmsEntityLiving);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void voidKill(Player p) {
        ((CraftPlayer) p).getHandle().damageEntity(DamageSource.OUT_OF_WORLD, 1000);
    }

    @Override
    public void hideArmor(Player p, Player p2) {
        PacketPlayOutEntityEquipment hand = new PacketPlayOutEntityEquipment(p.getEntityId(), 0, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.AIR)));
        PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(p.getEntityId(), 1, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.AIR)));
        PacketPlayOutEntityEquipment chest = new PacketPlayOutEntityEquipment(p.getEntityId(), 2, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.AIR)));
        PacketPlayOutEntityEquipment pants = new PacketPlayOutEntityEquipment(p.getEntityId(), 3, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.AIR)));
        PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(p.getEntityId(), 4, CraftItemStack.asNMSCopy(new ItemStack(org.bukkit.Material.AIR)));
        PlayerConnection pc = ((CraftPlayer) p2).getHandle().playerConnection;
        pc.sendPacket(hand);
        pc.sendPacket(helmet);
        pc.sendPacket(chest);
        pc.sendPacket(pants);
        pc.sendPacket(boots);
    }

    @Override
    public void hidePlayer(Player victim, Player p) {
        if (victim == p) return;
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(victim.getEntityId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void showPlayer(Player victim, Player p) {
        if (victim == p) return;
        p.showPlayer(victim);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void showArmor(Player p, Player p2) {
        PacketPlayOutEntityEquipment hand1 = new PacketPlayOutEntityEquipment(p.getEntityId(), 0, CraftItemStack.asNMSCopy(p.getItemInHand()));
        PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(p.getEntityId(), 4, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));
        PacketPlayOutEntityEquipment chest = new PacketPlayOutEntityEquipment(p.getEntityId(), 3, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
        PacketPlayOutEntityEquipment pants = new PacketPlayOutEntityEquipment(p.getEntityId(), 2, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
        PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(p.getEntityId(), 1, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
        EntityPlayer pc = ((CraftPlayer) p2).getHandle();
        if (p != p2) {
            pc.playerConnection.sendPacket(hand1);
        }
        pc.playerConnection.sendPacket(helmet);
        pc.playerConnection.sendPacket(chest);
        pc.playerConnection.sendPacket(pants);
        pc.playerConnection.sendPacket(boots);
    }

    @Override
    public void spawnDragon(Location l, BedWarsTeam bwt) {
        WorldServer mcWorld = ((CraftWorld) l.getWorld()).getHandle();
        Dragon customEnt = new Dragon(mcWorld, bwt);
        customEnt.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.getBukkitEntity().setMetadata("DragonTeam", new FixedMetadataValue(plugin, bwt));
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public void colorBed(BedWarsTeam bwt) {

    }

    @Override
    public void registerTntWhitelist() {
        try {
            Field field = Block.class.getDeclaredField("durability");
            field.setAccessible(true);
            field.set(Block.getByName("glass"), 300f);
            field.set(Block.getByName("stained_glass"), 300f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Effect eggBridge() {
        return Effect.MOBSPAWNER_FLAMES;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockTeamColor(org.bukkit.block.Block block, TeamColor teamColor) {
        block.setData(TeamColor.itemColor(teamColor));
    }

    @Override
    public org.bukkit.inventory.ItemStack addCustomData(org.bukkit.inventory.ItemStack i, String data) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }

        tag.setString("BedWars1058", data);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public boolean isCustomBedWarsItem(org.bukkit.inventory.ItemStack i) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        if (itemStack == null) return false;
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) return false;
        return tag.hasKey("BedWars1058");
    }

    @Override
    public String getCustomData(org.bukkit.inventory.ItemStack i) {
        net.minecraft.server.v1_8_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) return "";
        return tag.getString("BedWars1058");
    }

    @Override
    public org.bukkit.inventory.ItemStack setSkullOwner(org.bukkit.inventory.ItemStack i, Player p) {
        if (i.getType() != org.bukkit.Material.SKULL_ITEM) return i;
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwner(p.getName());
        i.setItemMeta(sm);
        return i;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack colourItem(ItemStack itemStack, BedWarsTeam bedWarsTeam) {
        if (itemStack == null) return null;
        switch (itemStack.getType().toString()) {
            default:
                return itemStack;
            case "WOOL":
            case "STAINED_CLAY":
            case "STAINED_GLASS":
            case "GLASS":
                return new ItemStack(itemStack.getType(), itemStack.getAmount(), TeamColor.itemColor(bedWarsTeam.getColor()));
        }
    }

    @Override
    public org.bukkit.inventory.ItemStack createItemStack(String material, int amount, short data) {
        org.bukkit.inventory.ItemStack i;
        try {
            i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.valueOf(material), amount, data);
        } catch (Exception ex) {
            Main.plugin.getLogger().severe(material + " is not a valid " + com.andrei1058.bedwars.Main.getServerVersion() + " material!");
            i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.BEDROCK);
        }
        return i;
    }

    @Override
    public void teamCollideRule(Team team) {

    }

    @Override
    public boolean isPlayerHead(String material, int data) {
        return material.equals("SKULL_ITEM") && data == 3;
    }

    @Override
    public org.bukkit.Material materialFireball() {
        return org.bukkit.Material.valueOf("FIREBALL");
    }

    @Override
    public org.bukkit.Material materialPlayerHead() {
        return org.bukkit.Material.valueOf("SKULL_ITEM");
    }

    @Override
    public org.bukkit.Material materialSnowball() {
        return org.bukkit.Material.valueOf("SNOW_BALL");
    }

    @Override
    public org.bukkit.Material materialGoldenHelmet() {
        return org.bukkit.Material.valueOf("GOLD_HELMET");
    }

    @Override
    public org.bukkit.Material materialGoldenChestPlate() {
        return org.bukkit.Material.valueOf("GOLD_CHESTPLATE");
    }

    @Override
    public org.bukkit.Material materialGoldenLeggings() {
        return org.bukkit.Material.valueOf("GOLD_LEGGINGS");
    }

    @Override
    public boolean isBed(org.bukkit.Material material) {
        return material == org.bukkit.Material.valueOf("BED_BLOCK") || material == org.bukkit.Material.valueOf("BED");
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean itemStackDataCompare(ItemStack i, short data) {
        return i.getData().getData() == data;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBlockData(org.bukkit.block.Block block, byte data) {
        block.setData(data, true);
    }

    @Override
    public org.bukkit.Material woolMaterial() {
        return org.bukkit.Material.valueOf("WOOL");
    }

    @Override
    public String getShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.getTag();
        return tag == null ? "" : tag.hasKey("tierIdentifier") ? tag.getString("tierIdentifier") : "";
    }

    @Override
    public org.bukkit.inventory.ItemStack setShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack, String identifier) {
        net.minecraft.server.v1_8_R3.ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            i.setTag(tag);
        }
        tag.setString("tierIdentifier", identifier);
        return CraftItemStack.asBukkitCopy(i);
    }
}
