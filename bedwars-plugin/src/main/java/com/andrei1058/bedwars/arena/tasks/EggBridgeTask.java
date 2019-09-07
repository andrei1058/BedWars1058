package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.events.gameplay.EggBridgeBuildEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitTask;

import static com.andrei1058.bedwars.BedWars.nms;

@SuppressWarnings("WeakerAccess")
public class EggBridgeTask implements Runnable {

    private Projectile projectile;
    private TeamColor teamColor;
    private Player player;
    private IArena arena;
    private BukkitTask task;

    public EggBridgeTask(Player player, Projectile projectile, TeamColor teamColor) {
        if (!(projectile instanceof Egg)) return;
        IArena a = Arena.getArenaByName(projectile.getWorld().getName());
        if (a == null) return;
        this.arena = a;
        this.projectile = projectile;
        this.teamColor = teamColor;
        this.player = player;
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 1);
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Player getPlayer() {
        return player;
    }

    public IArena getArena() {
        return arena;
    }

    @Override
    public void run() {

        Location loc = getProjectile().getLocation();

        if (getProjectile().isDead()) {
            task.cancel();
            return;
        }

        if (!arena.isPlayer(getPlayer())){
            task.cancel();
            return;
        }

        if (getPlayer().getLocation().distance(getProjectile().getLocation()) > 27) {
            task.cancel();
            return;
        }
        if (getPlayer().getLocation().getY() - getProjectile().getLocation().getY() > 9) {
            task.cancel();
            return;
        }

        if (getPlayer().getLocation().distance(loc) > 4.0D) {

            Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b2.getLocation(), getArena())) {
                if (b2.getType() == Material.AIR) {
                    b2.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b2, getTeamColor());
                    getArena().addPlacedBlock(b2);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b2));
                    loc.getWorld().playEffect(b2.getLocation(), nms.eggBridge(), 3);
                }
            }

            Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b3.getLocation(), getArena())) {
                if (b3.getType() == Material.AIR) {
                    b3.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b3, getTeamColor());
                    getArena().addPlacedBlock(b3);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b3));
                    loc.getWorld().playEffect(b3.getLocation(), nms.eggBridge(), 3);
                }
            }

            Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
            if (!Misc.isBuildProtected(b4.getLocation(), getArena())) {
                if (b4.getType() == Material.AIR) {
                    b4.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b4, getTeamColor());
                    getArena().addPlacedBlock(b4);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b4));
                    loc.getWorld().playEffect(b4.getLocation(), nms.eggBridge(), 3);
                }
            }
        }
    }

    public void cancel(){
        task.cancel();
    }
}
