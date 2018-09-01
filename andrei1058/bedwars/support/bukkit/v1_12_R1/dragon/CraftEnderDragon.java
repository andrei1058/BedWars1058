package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import com.google.common.collect.ImmutableSet;
import net.minecraft.server.v1_12_R1.EntityComplexPart;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftComplexLivingEntity;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Set;

public abstract class CraftEnderDragon extends CraftComplexLivingEntity implements EnderDragon {

    public CraftEnderDragon(CraftServer server, EntityEnderDragon entity) {
        super(server, entity);
    }

    public Set<ComplexEntityPart> getParts() {
        ImmutableSet.Builder<ComplexEntityPart> builder = ImmutableSet.builder();
        EntityComplexPart[] var5;
        int var4 = (var5 = this.getHandle().children).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            EntityComplexPart part = var5[var3];
            builder.add((ComplexEntityPart)part.getBukkitEntity());
        }

        return builder.build();
    }

    public EntityEnderDragon getHandle() {
        return (EntityEnderDragon)this.entity;
    }

    public String toString() {
        return "CraftEnderDragon";
    }

    public EntityType getType() {
        return EntityType.ENDER_DRAGON;
    }

    public Phase getPhase() {
        return Phase.values()[this.getHandle().getDataWatcher().get(EntityEnderDragon.PHASE)];
    }

    public void setPhase(org.bukkit.entity.EnderDragon.Phase phase) {
        this.getHandle().getDragonControllerManager().setControllerPhase(getMinecraftPhase(phase));
    }

    public static Phase getBukkitPhase(DragonControllerPhase phase) {
        return Phase.values()[phase.b()];
    }

    public static DragonControllerPhase getMinecraftPhase(Phase phase) {
        return DragonControllerPhase.getById(phase.ordinal());
    }
}

