package com.andrei1058.bedwars.support.version.v1_14_R1;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;

import org.bukkit.event.entity.EntityTargetEvent;

public class AttackEnemies extends PathfinderGoalTarget {

    private ITeam bedWarsTeam;

    private int d;

    @SuppressWarnings("WeakerAccess")
    public AttackEnemies(EntityInsentient entityinsentient, boolean flag, ITeam bedWarsTeam) {
        super(entityinsentient, flag, false);
        this.bedWarsTeam = bedWarsTeam;
    }

    @Override
    public boolean a() {
        return b();
    }

    public boolean b() {
        System.out.println("1");
        EntityLiving entityliving = this.e.getGoalTarget();
        if (entityliving == null) {
            entityliving = this.g;
        }

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            System.out.println("2");
            ScoreboardTeamBase scoreboardteambase = this.e.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();
            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
                return false;
            } else {
                System.out.println("3");
                double d0 = this.k();
                if (this.e.h(entityliving) > d0 * d0) {
                    return false;
                } else {
                    if (this.f) {
                        System.out.println("4");
                        if (this.e.getEntitySenses().a(entityliving)) {
                            this.d = 0;
                        } else if (++this.d > this.h) {
                            return false;
                        }
                    }

                    if (entityliving instanceof EntityHuman) {
                        System.out.println("a");
                        if (((EntityHuman) entityliving).abilities.isInvulnerable) {
                            System.out.println("b");
                            return false;
                        } else if (bedWarsTeam.getMembers().contains(Bukkit.getPlayer(entityliving.getName()))) {
                            System.out.println("c");
                            return false;
                        }
                        // do not hurt mobs with same owner
                    } else if (IGolem.vs.isDespawnable(entityliving.getBukkitEntity())){
                        System.out.println("d");
                        if (IGolem.vs.getDespawnablesList().get(entityliving.getBukkitEntity().getUniqueId()).getTeam() == bedWarsTeam) return false;
                    }
                    System.out.println("e");
                    this.e.setGoalTarget(entityliving, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
                    return true;
                }
            }
        }
    }

    protected AxisAlignedBB a(double d0) {
        return this.e.getBoundingBox().grow(d0, 4.0D, d0);
    }
}
