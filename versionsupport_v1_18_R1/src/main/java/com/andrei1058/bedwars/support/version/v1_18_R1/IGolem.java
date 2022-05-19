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

package com.andrei1058.bedwars.support.version.v1_18_R1;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.support.version.common.VersionCommon;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R1.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Objects;


@SuppressWarnings("unchecked")
public class IGolem extends IronGolem {
    private ITeam team;

    private IGolem(EntityType<? extends IronGolem> entitytypes, Level world, ITeam bedWarsTeam) {
        super(entitytypes, world);
        this.team = bedWarsTeam;
    }

    public IGolem(EntityType entityTypes, Level world) {
        super(entityTypes, world);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(
                this, Player.class, 20, true, false,
                player -> !((Player)player).getBukkitEntity().isDead() &&
                        !team.wasMember(((Player)player).getBukkitEntity().getUniqueId()) &&
                        !team.getArena().isReSpawning(((Player)player).getBukkitEntity().getUniqueId())
                && !team.getArena().isSpectator(((Player)player).getBukkitEntity().getUniqueId()))
        );
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(
                this, IGolem.class, 20, true, false,
                golem -> ((IGolem)golem).getBwTeam() != team)
        );
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(
                this, Silverfish.class, 20, true, false,
                sf -> ((Silverfish)sf).getBwTeam() != team)
        );
    }

    public ITeam getBwTeam() {
        return team;
    }

    public static LivingEntity spawn(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn) {
        ServerLevel mcWorld = ((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle();
        IGolem customEnt = new IGolem(EntityType.IRON_GOLEM, mcWorld, bedWarsTeam);
        customEnt.setPos(loc.getX(), loc.getY(), loc.getZ());
        customEnt.setRot(loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        Objects.requireNonNull(customEnt.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(health);
        Objects.requireNonNull(customEnt.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(speed);

        if (!CraftEventFactory.doEntityAddEventCalling(mcWorld, customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM)){
            mcWorld.entityManager.addNewEntity(customEnt);
        }

        mcWorld.addFreshEntity(customEnt,CreatureSpawnEvent.SpawnReason.CUSTOM);
        customEnt.getBukkitEntity().setPersistent(true);
        customEnt.getBukkitEntity().setCustomNameVisible(true);
        customEnt.getBukkitEntity().setCustomName(Language.getDefaultLanguage().m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME)
                .replace("{despawn}", String.valueOf(despawn)
                        .replace("{health}", StringUtils.repeat(Language.getDefaultLanguage().m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH) + " ", 10))
                        .replace("{TeamColor}", bedWarsTeam.getColor().chat().toString())));
        return (LivingEntity) customEnt.getBukkitEntity();
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        team = null;
        VersionCommon.api.getVersionSupport().getDespawnablesList().remove(this.getBukkitEntity().getUniqueId());
    }
}
