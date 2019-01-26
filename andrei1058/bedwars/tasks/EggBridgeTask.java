package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.api.events.EggBridgeBuildEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import static com.andrei1058.bedwars.Main.nms;

public class EggBridgeTask extends BukkitRunnable {

    private Projectile projectile;
    private TeamColor teamColor;
    private Player player;
    private Arena arena;

    public EggBridgeTask(Player player, Projectile projectile, TeamColor teamColor) {
        if (!(projectile instanceof Egg)) return;
        Arena a = Arena.getArenaByName(projectile.getWorld().getName());
        if (a == null) return;
        this.arena = a;
        this.projectile = projectile;
        this.teamColor = teamColor;
        this.player = player;
        this.runTaskTimer(Main.plugin, 0 ,1);
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

    public Arena getArena() {
        return arena;
    }

    @Override
    public void run() {

        Location loc = getProjectile().getLocation();
        loc.getWorld().playEffect(loc, nms.eggBridge(), 5);

        if (getProjectile().isDead()) {
            cancel();
            return;
        }

        if (getPlayer().getWorld() != getProjectile().getWorld()){
            cancel();
            return;
        }

        if (getPlayer().getLocation().distance(getProjectile().getLocation()) > 27) {
            cancel();
            return;
        }
        if (getPlayer().getLocation().getY() - getProjectile().getLocation().getY() > 9) {
            cancel();
            return;
        }

        if (getPlayer().getLocation().distance(loc) > 4.0D) {

            Block b2 = loc.clone().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b2.getLocation(), getArena())) {
                if (b2.getType() == Material.AIR) {
                    b2.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b2, getTeamColor());
                    getArena().getPlaced().add(b2);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b2));
                }
            }

            Block b3 = loc.clone().subtract(1.0D, 2.0D, 0.0D).getBlock();
            if (!Misc.isBuildProtected(b3.getLocation(), getArena())) {
                if (b3.getType() == Material.AIR) {
                    b3.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b3, getTeamColor());
                    getArena().getPlaced().add(b3);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b3));
                }
            }

            Block b4 = loc.clone().subtract(0.0D, 2.0D, 1.0D).getBlock();
            if (!Misc.isBuildProtected(b4.getLocation(), getArena())) {
                if (b4.getType() == Material.AIR) {
                    b4.setType(nms.woolMaterial());
                    nms.setBlockTeamColor(b4, getTeamColor());
                    getArena().getPlaced().add(b4);
                    Bukkit.getPluginManager().callEvent(new EggBridgeBuildEvent(getTeamColor(), getArena(), b4));
                }
            }
        }
    }
}
