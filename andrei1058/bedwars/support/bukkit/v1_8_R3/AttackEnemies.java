package com.andrei1058.bedwars.support.bukkit.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackEnemies<T extends EntityLiving> extends PathfinderGoalTarget {

    protected final Class<T> a;
    private final int g;
    protected final PathfinderGoalNearestAttackableTarget.DistanceComparator b;
    protected Predicate<? super T> c;
    protected EntityLiving d;
    private List<EntityLiving> excluded = new ArrayList<>();

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, List<Player> exclude) {
        this(entitycreature, oclass, flag, false);
        for (Player p : exclude){
            this.excluded.add(((CraftPlayer)p).getHandle());
        }
    }

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
        this(entitycreature, oclass, 10, flag, flag1, null);
    }

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate) {
        super(entitycreature, flag, flag1);
        this.a = oclass;
        this.g = i;
        this.b = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entitycreature);
        this.a(1);
        this.c = new Predicate() {
            public boolean a(T t0) {
                if (predicate != null && !predicate.apply(t0)) {
                    return false;
                } else {
                    if (t0 instanceof EntityHuman) {
                        if (excluded.contains(t0)) return false;
                        double d0 = AttackEnemies.this.f();
                        if (t0.isSneaking()) {
                            d0 *= 0.800000011920929D;
                        }

                        if (t0.isInvisible()) {
                            float f = ((EntityHuman)t0).bY();
                            if (f < 0.1F) {
                                f = 0.1F;
                            }

                            d0 *= (double)(0.7F * f);
                        }

                        if ((double)t0.g(AttackEnemies.this.e) > d0) {
                            return false;
                        }
                    }

                    return AttackEnemies.this.a(t0, false);
                }
            }

            public boolean apply(Object object) {
                return this.a((T) object);
            }
        };
    }

    public boolean a() {
        if (this.g > 0 && this.e.bc().nextInt(this.g) != 0) {
            return false;
        } else {
            double d0 = this.f();
            List list = this.e.world.a(this.a, this.e.getBoundingBox().grow(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));
            Collections.sort(list, this.b);
            if (list.isEmpty()) {
                return false;
            } else {
                for (int i = 0; i < list.size(); i++){
                    if (!excluded.contains(list.get(i))){
                        this.d = (EntityLiving)list.get(i);
                        return true;
                    }
                }
                return false;
            }
        }
    }

    public void c() {
        this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }
}
