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
        Bukkit.broadcastMessage("initialized");
    }

    @Override
    public boolean a() {
        Bukkit.broadcastMessage("a");
        return b();
    }

    public boolean b() {
        Bukkit.broadcastMessage("1");
        EntityLiving entityliving = this.e.getGoalTarget();
        if (entityliving == null) {
            entityliving = this.g;
        }
        Bukkit.broadcastMessage("2");
        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            Bukkit.broadcastMessage("3");
            ScoreboardTeamBase scoreboardteambase = this.e.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();
            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
                return false;
            } else {
                Bukkit.broadcastMessage("4");
                double d0 = this.k();
                if (this.e.h(entityliving) > d0 * d0) {
                    return false;
                } else {
                    if (this.f) {
                        Bukkit.broadcastMessage("5");
                        if (this.e.getEntitySenses().a(entityliving)) {
                            this.d = 0;
                        } else if (++this.d > this.h) {
                            return false;
                        }
                    }
                    Bukkit.broadcastMessage("6");
                    if (entityliving instanceof EntityHuman) {
                        Bukkit.broadcastMessage("7");
                        if (((EntityHuman) entityliving).abilities.isInvulnerable) {
                            Bukkit.broadcastMessage("8");
                            return false;
                        } else if (bedWarsTeam.getMembers().contains(Bukkit.getPlayer(entityliving.getName()))) {
                            Bukkit.broadcastMessage("9");
                            return false;
                        }
                        // do not hurt mobs with same owner
                    } else if (IGolem.vs.isDespawnable(entityliving.getBukkitEntity())){
                        Bukkit.broadcastMessage("10");
                        if (IGolem.vs.getDespawnablesList().get(entityliving.getBukkitEntity().getUniqueId()).getTeam() == bedWarsTeam) return false;
                    }
                    Bukkit.broadcastMessage("11");
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
