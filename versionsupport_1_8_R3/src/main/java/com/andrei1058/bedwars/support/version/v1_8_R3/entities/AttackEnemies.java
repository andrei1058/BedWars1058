package com.andrei1058.bedwars.support.version.v1_8_R3.entities;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityTargetEvent;

@SuppressWarnings("unchecked")
public class AttackEnemies<T extends EntityLiving> extends PathfinderGoalTarget {

    private EntityLiving d;
    private ITeam bedWarsTeam;
    private VersionSupport vs;

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, ITeam bedWarsTeam, VersionSupport vs) {
        this(entitycreature, oclass, flag, false);
        this.bedWarsTeam = bedWarsTeam;
    }

    private boolean isMember(EntityLiving t0) {
        return (t0 instanceof EntityPlayer && bedWarsTeam.isMember(Bukkit.getPlayer(t0.getName())))
                || (vs.isDespawnable(t0.getBukkitEntity()) && vs.getDespawnablesList().get(t0.getBukkitEntity().getUniqueId()).getTeam() == bedWarsTeam);
    }

    private AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
        this(entitycreature, oclass, 10, flag, flag1, null);
    }

    private AttackEnemies(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, @SuppressWarnings("Guava") final Predicate<? super T> predicate) {
        super(entitycreature, flag, flag1);
        PathfinderGoalNearestAttackableTarget.DistanceComparator b = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entitycreature);
        this.a(1);
        //noinspection WeakerAccess,Guava
        Predicate<? super T> c = new Predicate() {
            public boolean a(T t0) {
                if (predicate != null && !predicate.apply(t0)) {
                    return false;
                } else {
                    if (t0 instanceof EntityHuman) {
                        if (isMember(t0)) return false;
                        double d0 = AttackEnemies.this.f();
                        if (t0.isSneaking()) {
                            d0 *= 0.800000011920929D;
                        }

                        if (t0.isInvisible()) {
                            float f = ((EntityHuman) t0).bY();
                            if (f < 0.1F) {
                                f = 0.1F;
                            }

                            d0 *= 0.7F * f;
                        }

                        if ((double) t0.g(AttackEnemies.this.e) > d0) {
                            return false;
                        }
                    } else if (t0 instanceof Silverfish) return false;

                    return AttackEnemies.this.a(t0, false);
                }
            }

            public boolean apply(Object object) {
                return this.a((T) object);
            }
        };
    }

    @Override
    public boolean a() {
        return false;
    }

    public void c() {
        this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }
}
