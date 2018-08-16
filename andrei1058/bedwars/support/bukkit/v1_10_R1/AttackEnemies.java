package com.andrei1058.bedwars.support.bukkit.v1_10_R1;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttackEnemies<T extends EntityLiving> extends PathfinderGoalTarget {

    protected final Class<T> a;
    private final int i;
    protected final PathfinderGoalNearestAttackableTarget.DistanceComparator b;
    protected final Predicate<? super T> c;
    protected T d;
    private List<EntityLiving> excluded = new ArrayList<>();

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, List<Player> exclude) {
        this(entitycreature, oclass, flag, false);
        for (Player p : exclude) {
            this.excluded.add(((CraftPlayer) p).getHandle());
        }
    }

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
        this(entitycreature, oclass, 10, flag, flag1, null);
    }

    public AttackEnemies(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, final Predicate<? super T> predicate) {
        super(entitycreature, flag, flag1);
        this.a = oclass;
        this.i = i;
        this.b = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entitycreature);
        this.a(1);
        this.c = new Predicate() {
            public boolean a(T t0) {
                return t0 == null ? false : excluded.contains(t0) ? false : (predicate != null && !predicate.apply(t0) ? false : (!IEntitySelector.e.apply(t0) ? false : AttackEnemies.this.a(t0, false)));
            }

            public boolean apply(Object object) {
                return this.a((T) object);
            }
        };
    }

    public boolean a() {
        if (this.i > 0 && this.e.getRandom().nextInt(this.i) != 0) {
            return false;
        } else if (this.a != EntityHuman.class && this.a != EntityPlayer.class) {
            List list = this.e.world.a(this.a, this.a(this.i()), this.c);
            if (list.isEmpty()) {
                return false;
            } else {
                Collections.sort(list, this.b);
                for (int x = 0; x < list.size(); x++) {
                    if (list.get(x) instanceof Silverfish) {
                        continue;
                    } else if (!excluded.contains(list.get(x))) {
                        this.d = (T) list.get(x);
                        return true;
                    }
                }
                return false;
            }
        } else {
            this.d = (T) this.e.world.a(this.e.locX, this.e.locY + (double) this.e.getHeadHeight(), this.e.locZ, this.i(), this.i(), new Function<EntityHuman, Double>() {
                @Nullable
                public Double a(@Nullable EntityHuman entityhuman) {
                    ItemStack itemstack = entityhuman.getEquipment(EnumItemSlot.HEAD);
                    if (itemstack != null && itemstack.getItem() == Items.SKULL) {
                        int i = itemstack.h();
                        boolean flag = AttackEnemies.this.e instanceof EntitySkeleton && ((EntitySkeleton) AttackEnemies.this.e).getSkeletonType() == EnumSkeletonType.NORMAL && i == 0;
                        boolean flag1 = AttackEnemies.this.e instanceof EntityZombie && i == 2;
                        boolean flag2 = AttackEnemies.this.e instanceof EntityCreeper && i == 4;
                        if (flag || flag1 || flag2) {
                            return 0.5D;
                        }
                    }

                    return 1.0D;
                }

                public Double apply(EntityHuman object) {
                    return this.a(object);
                }
            }, (Predicate<EntityHuman>) this.c);
            return this.d != null;
        }
    }

    protected AxisAlignedBB a(double d0) {
        return this.e.getBoundingBox().grow(d0, 4.0D, d0);
    }

    public void c() {
        this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }
}
