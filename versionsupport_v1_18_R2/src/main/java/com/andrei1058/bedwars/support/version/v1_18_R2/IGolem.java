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

package com.andrei1058.bedwars.support.version.v1_18_R2;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.support.version.common.VersionCommon;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R2.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class IGolem extends EntityIronGolem {
    private ITeam team;

    private IGolem(EntityTypes<? extends EntityIronGolem> entitytypes, World world, ITeam bedWarsTeam) {
        super(entitytypes, world);
        this.team = bedWarsTeam;
    }

    public IGolem(EntityTypes entityTypes, World world) {
        super(entityTypes, world);
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void u() {
        this.bQ.a(1, new PathfinderGoalFloat(this));
        this.bQ.a(2, new PathfinderGoalMeleeAttack(this, 1.5D, false));
        this.bR.a(3, new PathfinderGoalHurtByTarget(this));
        this.bQ.a(4, new PathfinderGoalRandomStroll(this, 1D));
        this.bQ.a(5, new PathfinderGoalRandomLookaround(this));
        this.bR.a(6, new PathfinderGoalNearestAttackableTarget(
                this, EntityHuman.class, 20, true, false,
                player -> !((EntityHuman)player).getBukkitEntity().isDead() &&
                        !team.wasMember(((EntityHuman)player).getBukkitEntity().getUniqueId()) &&
                        !team.getArena().isReSpawning(((EntityHuman)player).getBukkitEntity().getUniqueId())
                && !team.getArena().isSpectator(((EntityHuman)player).getBukkitEntity().getUniqueId()))
        );
        this.bR.a(7, new PathfinderGoalNearestAttackableTarget(
                this, IGolem.class, 20, true, false,
                golem -> ((IGolem)golem).getTeam() != team)
        );
        this.bR.a(8, new PathfinderGoalNearestAttackableTarget(
                this, Silverfish.class, 20, true, false,
                sf -> ((Silverfish)sf).getTeam() != team)
        );
    }

    public ITeam getTeam() {
        return team;
    }

    public static LivingEntity spawn(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn) {
        WorldServer mcWorld = ((CraftWorld) Objects.requireNonNull(loc.getWorld())).getHandle();
        IGolem customEnt = new IGolem(EntityTypes.P, mcWorld, bedWarsTeam);
        customEnt.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        Objects.requireNonNull(customEnt.a(GenericAttributes.a)).a(health);
        Objects.requireNonNull(customEnt.a(GenericAttributes.d)).a(speed);

        if (!CraftEventFactory.doEntityAddEventCalling(mcWorld, customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM)){
            mcWorld.O.a(customEnt);
        }

        mcWorld.a(customEnt);
        customEnt.getBukkitEntity().setPersistent(true);
        customEnt.getBukkitEntity().setCustomNameVisible(true);
        customEnt.getBukkitEntity().setCustomName(Language.getDefaultLanguage().m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME)
                .replace("{despawn}", String.valueOf(despawn)
                        .replace("{health}", StringUtils.repeat(Language.getDefaultLanguage().m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH) + " ", 10))
                        .replace("{TeamColor}", bedWarsTeam.getColor().chat().toString())));
        return (LivingEntity) customEnt.getBukkitEntity();
    }

    @Override
    public void a(DamageSource damagesource) {
        super.a(damagesource);
        team = null;
        VersionCommon.api.getVersionSupport().getDespawnablesList().remove(this.getBukkitEntity().getUniqueId());
    }

    @Override
    public boolean d_() {
        return super.d_();
    }
}
