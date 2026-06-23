package com.andrei1058.bedwars.platform.paper;

import com.mojang.datafixers.util.Pair;
import com.andrei1058.mc.bedwars.AbstractVerImplCmn1;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_21_R2.CraftServer;
import org.bukkit.craftbukkit.v1_21_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_21_R2.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_21_R2.inventory.CraftItemStack;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class v1_21_R2_NMS extends AbstractVerImplCmn1
{

    private ClientboundPlayerInfoUpdatePacket.a ADD_PLAYER_ACTION = null;

    public v1_21_R2_NMS(Plugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void registerCommand(String name, Command cmd) {
        ((CraftServer) getPlugin().getServer()).getCommandMap().register(name, cmd);
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) getPlugin().getServer()).getCommandMap().getCommand(name) != null;
    }

    @Override
    public void setSource(TNTPrimed tnt, Player owner) {
        EntityLiving nmsEntityLiving = (((CraftLivingEntity) owner).getHandle());
        EntityTNTPrimed nmsTNT = (((CraftTNTPrimed) tnt).getHandle());
        nmsTNT.i = nmsEntityLiving;
    }

    @Override
    public ClientboundPlayerInfoUpdatePacket getAddPlayer(EntityPlayer player) {
        if (null == ADD_PLAYER_ACTION) {
            ADD_PLAYER_ACTION = (ClientboundPlayerInfoUpdatePacket.a) getPlayerSpawnAction("ADD_PLAYER");
        }
        return new ClientboundPlayerInfoUpdatePacket(ADD_PLAYER_ACTION, player);
    }

    @Override
    public boolean isArmor(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemArmor || itemStack.getType() == Material.ELYTRA;
    }

    @Override
    public boolean isTool(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemTool;
    }

    @Override
    public boolean isSword(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemSword;
    }

    @Override
    public boolean isAxe(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemAxe;
    }

    @Override
    public boolean isBow(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemBow;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack) {
        var entity = getEntity(itemStack);
        if (null == entity) return false;
        return entity instanceof IProjectile;
    }

    @Override
    public void voidKill(Player p) {
        EntityPlayer player = getPlayer(p);
        // out of world
        var damageSource = ((CraftWorld)p.getWorld()).getHandle().aj().m();
        player.a(damageSource, 1000);
    }
    @Override
    public void showArmor(@NotNull Player victim, Player receiver) {
        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();
        items.add(new Pair<>(EnumItemSlot.f, CraftItemStack.asNMSCopy(victim.getInventory().getHelmet())));
        items.add(new Pair<>(EnumItemSlot.e, CraftItemStack.asNMSCopy(victim.getInventory().getChestplate())));
        items.add(new Pair<>(EnumItemSlot.d, CraftItemStack.asNMSCopy(victim.getInventory().getLeggings())));
        items.add(new Pair<>(EnumItemSlot.c, CraftItemStack.asNMSCopy(victim.getInventory().getBoots())));
        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(victim.getEntityId(), items);
        sendPacket(receiver, packet1);
    }

    @Override
    public String getMainLevel() {
        //noinspection deprecation
        return ((DedicatedServer) MinecraftServer.getServer()).a().l;
    }

    @Override
    public int getVersion() {
        return 25;
    }


    @Override
    public Fireball setFireballDirection(Fireball fireball, @NotNull Vector vector) {
        EntityFireball fb = ((CraftFireball) fireball).getHandle();
        fb.a(new Vec3D(vector.getX(), vector.getY(), vector.getZ()), 0.1D);
        return (Fireball) fb.getBukkitEntity();
    }


    /**
     * Gets the NMS Item from ItemStack
     */
    private @Nullable Item getItem(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            return null;
        }
        return i.h();
    }


    /**
     * Gets the NMS Entity from ItemStack
     */
    private @Nullable net.minecraft.world.entity.Entity getEntity(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            return null;
        }
        // todo experimental
        return i.I();
    }

    public EntityPlayer getPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().f.b(packet);
    }

    public void sendPackets(Player player, Packet<?> @NotNull ... packets) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().f;
        for (Packet<?> p : packets) {
            connection.b(p);
        }
    }

    private Object getPlayerSpawnAction(@SuppressWarnings("SameParameterValue") String action) {
        try {
            Class<?> cls = Class.forName("net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$a");
            for (Object obj : cls.getEnumConstants()) {
                try {
                    Method m = cls.getMethod("name");
                    String name = (String) m.invoke(obj);
                    if (action.equals(name)) {
                        return obj;
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    getPlugin().getLogger().warning("Could not get player spawn action: " + action);
                }
            }
        } catch (Exception exception) {
            getPlugin().getLogger().warning("Could not get player spawn action: " + action);
        }
        throw new RuntimeException("Something went wrong... please report this to BedWars1058 by andrei1058");
    }

    @Override
    public void registerTntWhitelist(float endStoneBlast, float glassBlast) {
        try {
            // blast resistance
            Field field = BlockBase.class.getDeclaredField("aI");
            field.setAccessible(true);
            // end stone - on 1.21 it breaks the first level of end stones
            field.set(Blocks.fN, endStoneBlast);
            // standard glass
            field.set(Blocks.aX, glassBlast);

            var coloredGlass = new net.minecraft.world.level.block.Block[]{
                    Blocks.ev, Blocks.ew, Blocks.ex, Blocks.ey,
                    Blocks.ez, Blocks.eA, Blocks.eB, Blocks.eC,
                    Blocks.eD, Blocks.eE, Blocks.eF, Blocks.eG,
                    Blocks.eH, Blocks.eI, Blocks.eJ, Blocks.eK,

                    // tinted glass
                    Blocks.rj,
            };

            Arrays.stream(coloredGlass).forEach(
                    glass -> {
                        try {
                            field.set(glass, glassBlast);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
