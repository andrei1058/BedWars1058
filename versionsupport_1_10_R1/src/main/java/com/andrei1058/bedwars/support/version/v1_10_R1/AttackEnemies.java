package com.andrei1058.bedwars.support.version.v1_10_R1;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("SimplifiableConditionalExpression")
public class AttackEnemies<T extends EntityLiving> extends PathfinderGoalTarget {

    private final Class<T> a;
    private final int i;
    private final PathfinderGoalNearestAttackableTarget.DistanceComparator b;
    @SuppressWarnings("Guava")
    private final Predicate<? super T> c;
    private T d;
    private ITeam bedWarsTeam;
    private static VersionSupport vs;

    AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, ITeam bedWarsTeam, VersionSupport versionSupport) {
        this(entitycreature, oclass, flag, false);
        this.bedWarsTeam = bedWarsTeam;
        if (vs == null) vs = versionSupport;
    }

    private AttackEnemies(EntityCreature entitycreature, Class<T> oclass, boolean flag, boolean flag1) {
        this(entitycreature, oclass, 10, flag, flag1, null);
    }

    private boolean isMember(EntityLiving t0) {
        return (t0 instanceof EntityPlayer && bedWarsTeam.isMember(Bukkit.getPlayer(t0.getName()))) ? true :
                (vs.isDespawnable(t0.getBukkitEntity()) && vs.getDespawnablesList().get(t0.getBukkitEntity().getUniqueId()).getTeam() == bedWarsTeam);
    }

    @SuppressWarnings("unchecked")
    private AttackEnemies(EntityCreature entitycreature, Class<T> oclass, int i, boolean flag, boolean flag1, @SuppressWarnings("Guava") final Predicate<? super T> predicate) {
        super(entitycreature, flag, flag1);
        this.a = oclass;
        this.i = i;
        this.b = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entitycreature);
        this.a(1);
        //noinspection WeakerAccess
        this.c = new Predicate() {
            @SuppressWarnings("UnclearExpression")
            public boolean a(T t0) {
                return t0 == null ? false : isMember(t0) ? false : (predicate != null && !predicate.apply(t0) ? false : (!IEntitySelector.e.apply(t0) ? false : AttackEnemies.this.a(t0, false)));
            }

            public boolean apply(Object object) {
                return this.a((T) object);
            }
        };
    }

    @SuppressWarnings({"unchecked", "UnnecessaryContinue"})
    public boolean a() {
        if (this.i > 0 && this.e.getRandom().nextInt(this.i) != 0) {
            return false;
        } else if (this.a != EntityHuman.class && this.a != EntityPlayer.class) {
            List list = this.e.world.a(this.a, this.a(this.i()), this.c);
            if (list.isEmpty()) {
                return false;
            } else {
                list.sort(this.b);
                for (Object o : list) {
                    if (o instanceof Silverfish) {
                        continue;
                    } else if (!isMember((EntityLiving) o)) {
                        this.d = (T) o;
                        return true;
                    }
                }
                return false;
            }
        } else {
            this.d = (T) this.e.world.a(this.e.locX, this.e.locY + (double) this.e.getHeadHeight(), this.e.locZ, this.i(), this.i(), new Function<EntityHuman, Double>() {
                @SuppressWarnings("WeakerAccess")
                public Double a(@Nullable EntityHuman entityhuman) {
                    assert entityhuman != null;
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

    private AxisAlignedBB a(double d0) {
        return this.e.getBoundingBox().grow(d0, 4.0D, d0);
    }

    public void c() {
        this.e.setGoalTarget(this.d, this.d instanceof EntityPlayer ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }
}
