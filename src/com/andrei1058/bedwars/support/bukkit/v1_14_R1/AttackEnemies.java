package com.andrei1058.bedwars.support.bukkit.v1_14_R1;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;

import org.bukkit.event.entity.EntityTargetEvent;

public class AttackEnemies extends PathfinderGoalTarget {

    private BedWarsTeam bedWarsTeam;

    private int d;

    public AttackEnemies(EntityInsentient entityinsentient, boolean flag, BedWarsTeam bedWarsTeam) {
        super(entityinsentient, flag, false);
        this.bedWarsTeam = bedWarsTeam;

    }

    @Override
    public boolean a() {
        return b();
    }

    public boolean b() {
        EntityLiving entityliving = this.e.getGoalTarget();
        if (entityliving == null) {
            entityliving = this.g;
        }

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            ScoreboardTeamBase scoreboardteambase = this.e.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();
            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
                return false;
            } else {
                double d0 = this.k();
                if (this.e.h(entityliving) > d0 * d0) {
                    return false;
                } else {
                    if (this.f) {
                        if (this.e.getEntitySenses().a(entityliving)) {
                            this.d = 0;
                        } else if (++this.d > this.h) {
                            return false;
                        }
                    }

                    if (entityliving instanceof EntityHuman) {
                        if (((EntityHuman) entityliving).abilities.isInvulnerable) {
                            return false;
                        } else if (bedWarsTeam.getMembers().contains(Bukkit.getPlayer(entityliving.getName()))) {
                            return false;
                        }
                    } else if (entityliving instanceof Silverfish){
                        if (((Silverfish)entityliving).getBedWarsTeam() == bedWarsTeam) return false;
                    } else if (entityliving instanceof IGolem){
                        if (((IGolem)entityliving).getBedWarsTeam() == bedWarsTeam) return false;
                    }

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
