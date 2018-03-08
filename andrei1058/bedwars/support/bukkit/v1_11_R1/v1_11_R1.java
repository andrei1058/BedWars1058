package com.andrei1058.bedwars.support.bukkit.v1_11_R1;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.support.bukkit.NMS;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class v1_11_R1 implements NMS {
    /** List of despawnable entities aka special shop mobs */
    private static List<Despawnable> despawnables = new ArrayList();

    @Override
    public void registerCommand(String name, Command clasa) {
        ((CraftServer) plugin.getServer()).getCommandMap().register(name, clasa);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title != null) {
            IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, bc);
            PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
        }
        if (subtitle != null){
            IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, bc);
            PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
        }
    }

    @Override
    public Sound countdownTick() {
        return Sound.valueOf("ENTITY_CHICKEN_EGG");
    }

    @Override
    public org.bukkit.entity.Entity spawnSilverfish(Location loc, List<Player> exclude, String name) {
        return Silverfish.spawnSilverfish(loc, exclude, name);
    }

    @Override
    public void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam) {
        new Despawnable(IGolem.spawn(loc, bedWarsTeam), bedWarsTeam, shop.getInt("utilities.ironGolem.despawn"));
    }

    @Override
    public void hidePlayer(Player player, List<Player> players) {
        net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy packet = new net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy(player.getEntityId());
        for (Player p : players) {
            if (p == player) continue;
            ((org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public void refreshDespawnables() {
        for(Despawnable d : new ArrayList<>(despawnables)){
            d.regresh();
        }
    }

    @Override
    public void setCollidable(Player e, boolean b) {
        e.setCollidable(b);
    }

    @Override
    public void minusAmount(Player p, org.bukkit.inventory.ItemStack i, int amount) {
        i.setAmount(i.getAmount()-amount);
    }

    @Override
    public boolean isDespawnable(org.bukkit.entity.Entity e) {
        for (Despawnable d : despawnables){
            if (d.getE() == ((CraftEntity)e).getHandle()) return true;
        }
        return false;
    }

    @Override
    public BedWarsTeam ownDespawnable(org.bukkit.entity.Entity e) {
        for (Despawnable d : despawnables){
            if (d.getE() == ((CraftEntity)e).getHandle()) return d.getTeam();
        }
        return null;
    }

    @Override
    public Sound insufficientMoney() {
        return Sound.valueOf("ENTITY_ENDERMEN_TELEPORT");
    }

    @Override
    public Sound bought() {
        return Sound.valueOf("BLOCK_ANVIL_HIT");
    }

    @Override
    public void playAction(Player p, String text) {
        CraftPlayer cPlayer = (CraftPlayer)p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        cPlayer.getHandle().playerConnection.sendPacket(ppoc);
    }

    @Override
    public void spawnNPC(EntityType entity, Location location, String name, String group) {
        org.bukkit.entity.Entity e = location.getWorld().spawnEntity(location, entity);
        net.minecraft.server.v1_11_R1.Entity en = ((CraftEntity)e).getHandle();
        double height = en.getBoundingBox().e - en.getBoundingBox().b;
        ArmorStand a = createArmorStand(name, location.clone().add(0, height-1, 0));
        a.setSmall(true);
        NBTTagCompound tag = new NBTTagCompound();
        en.c(tag);
        tag.setInt("NoAI", 1);
        en.f(tag);
        npcs.put(e, group);
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) plugin.getServer()).getCommandMap().getCommand(name) != null;
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemInHand(Player p) {
        return p.getItemInHand();
    }

    @Override
    public void hideEntity(org.bukkit.entity.Entity e, Player... players) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(e.getEntityId());
        for (Player p : players){
            if (p == e)continue;
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }

    @Override
    public boolean isArmor(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor;
    }

    @Override
    public boolean isTool(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemTool;
    }

    @Override
    public boolean isSword(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemSword;
    }

    @Override
    public boolean isBow(org.bukkit.inventory.ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemBow;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack){
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof IProjectile;
    }

    @Override
    public void registerEntities() {
        registerEntity("ShopNPC", 120, VillagerShop.class);
        registerEntity("Silverfish2", 60, Silverfish.class);
        registerEntity("IGolem", 99, IGolem.class);
    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players) {
        spawnVillager(loc);
        for (Player p : players){
            String[] nume = getMsg(p, name1).split(",");
            if (nume.length  >= 2){
                ArmorStand a = createArmorStand(nume[0], loc.clone().add(0, 0.4, 0));
                ArmorStand b = createArmorStand(nume[1], loc);
                for (Player pl : p.getWorld().getPlayers()){
                    if (p != pl){
                        nms.hideEntity(a, pl);
                        nms.hideEntity(b, pl);
                    }
                }
            } else {
                ArmorStand a = createArmorStand(nume[0], loc);
                for (Player pl : p.getWorld().getPlayers()){
                    if (p != pl) {
                        nms.hideEntity(a, pl);
                    }
                }
            }
        }
    }

    @Override
    public double getDamage(org.bukkit.inventory.ItemStack i) {
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.attackDamage");
    }

    @Override
    public double getProtection(org.bukkit.inventory.ItemStack i) {
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.armor");
    }

    @Override
    public Sound bedDestroy() {
        return Sound.valueOf("ENTITY_ENDERDRAGON_GROWL");
    }

    @Override
    public Sound playerKill() {
        return Sound.valueOf("ENTITY_WOLF_HURT");
    }

    private static ArmorStand createArmorStand(String name, Location loc){
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setGravity(false);
        a.setVisible(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
        return a;
    }


    public void registerEntity(String name, int id, Class customClass) {
        EntityTypes.b.a(id, new MinecraftKey(name), customClass);
    }

    public class VillagerShop extends net.minecraft.server.v1_11_R1.EntityVillager {
        public VillagerShop(net.minecraft.server.v1_11_R1.World world) {
            super(world);
            try {
                Field bField = net.minecraft.server.v1_11_R1.PathfinderGoalSelector.class.getDeclaredField("b");
                bField.setAccessible(true);
                Field cField = net.minecraft.server.v1_11_R1.PathfinderGoalSelector.class.getDeclaredField("c");
                cField.setAccessible(true);
                bField.set(this.goalSelector, Sets.newLinkedHashSet());
                bField.set(this.targetSelector,Sets.newLinkedHashSet());
                cField.set(this.goalSelector, Sets.newLinkedHashSet());
                cField.set(this.targetSelector, Sets.newLinkedHashSet());
            } catch (Exception bField) {
            }
            this.goalSelector.a(0, new net.minecraft.server.v1_11_R1.PathfinderGoalFloat(this));
            this.goalSelector.a(9, new net.minecraft.server.v1_11_R1.PathfinderGoalInteract(this, net.minecraft.server.v1_11_R1.EntityHuman.class, 3.0f, 1.0f));
            this.goalSelector.a(10, new net.minecraft.server.v1_11_R1.PathfinderGoalLookAtPlayer(this, net.minecraft.server.v1_11_R1.EntityHuman.class, 8.0f));
        }
        @Override
        public void collide(net.minecraft.server.v1_11_R1.Entity entity) {
        }
        @Override
        public boolean damageEntity(net.minecraft.server.v1_11_R1.DamageSource damagesource, float f) {
            return false;
        }
        @Override
        public void move(EnumMoveType enummovetype, double d0, double d1, double d2){}
        public void g(double d0, double d1, double d2) {
        }
        @Override
        protected void initAttributes() {
            super.initAttributes();
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0D);
        }
    }

    private Villager spawnVillager(Location loc) {
        net.minecraft.server.v1_11_R1.WorldServer mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
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
        public Despawnable(EntityLiving e, BedWarsTeam team, int despawn){
            this.e = e;
            this.team = team;
            if (despawn != 0){
                this.despawn = despawn;
            }
            despawnables.add(this);
        }

        public void regresh() {
            if (!e.isAlive()){
                despawnables.remove(this);
                return;
            }
            int percentuale = (int) ((e.getHealth()*100)/e.getMaxHealth()/10);
            e.setCustomName(lang.m(lang.iGolemName).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                    new String(new char[percentuale]).replace("\0", lang.m(lang.iGolemHealthFormat))+new String(new char[10-percentuale]).replace("\0", "§7"+lang.m(lang.iGolemHealthFormat))
            ).replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()));
            despawn--;
            if (despawn == 0){
                e.damageEntity(DamageSource.OUT_OF_WORLD, 9000);
                despawnables.remove(this);
            }
        }

        public EntityLiving getE() {
            return e;
        }

        public BedWarsTeam getTeam() {
            return team;
        }
    }
}
