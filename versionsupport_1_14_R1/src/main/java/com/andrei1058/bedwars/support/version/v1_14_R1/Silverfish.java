package com.andrei1058.bedwars.support.version.v1_14_R1;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import net.minecraft.server.v1_14_R1.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;


@SuppressWarnings("ALL")
public class Silverfish extends EntitySilverfish {

    private ITeam team;

    private Silverfish(EntityTypes<? extends EntitySilverfish> entitytypes, World world, ITeam bedWarsTeam) {
        super(entitytypes, world);
        this.team = bedWarsTeam;
        //this.targetSelector.a(4, new AttackEnemies(this, true, bedWarsTeam));
    }

    @SuppressWarnings("unchecked")
    public Silverfish(EntityTypes entityTypes, World world) {
        super(entityTypes, world);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this,2D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this));
        this.goalSelector.a(3, new PathfinderGoalRandomStroll(this, 1.0D));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 15, true, false, player -> {
            //todo finish respawn thing is not ok
            return ((EntityHuman)player).isAlive() && !team.wasMember(((EntityHuman)player).getUniqueID()) && !team.getArena().getRespawn().containsKey(((EntityHuman)player).getUniqueID());
        }));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, IGolem.class, 15, true, false, golem -> {
            return ((IGolem)golem).getTeam() != team;
        }));
        this.targetSelector.a(4, new PathfinderGoalNearestAttackableTarget(this, Silverfish.class, 15, true, false, sf -> {
            return ((Silverfish)sf).getTeam() != team;
        }));
    }

    public ITeam getTeam() {
        return team;
    }

    public static LivingEntity spawn(VersionSupport versionSupport, Location loc, ITeam team, double speed, double health, int despawn, double damage) {
        if (IGolem.vs == null) IGolem.vs = versionSupport;
        WorldServer mcWorld = ((CraftWorld)loc.getWorld()).getHandle();
        Silverfish customEnt = new Silverfish(EntityTypes.SILVERFISH, mcWorld, team);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        customEnt.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(health);
        customEnt.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(speed);
        customEnt.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(damage);
        customEnt.setPersistent();
        ((CraftLivingEntity)customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        customEnt.setCustomNameVisible(true);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        customEnt.getBukkitEntity().setCustomName(Language.getDefaultLanguage().m(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME)
                .replace("{despawn}", String.valueOf(despawn)
                        .replace("{health}", StringUtils.repeat(Language.getDefaultLanguage().m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)+" ", 10))
                        .replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString())));
        return (LivingEntity) customEnt.getBukkitEntity();
    }
}
