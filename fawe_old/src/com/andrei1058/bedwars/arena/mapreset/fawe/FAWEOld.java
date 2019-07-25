package com.andrei1058.bedwars.arena.mapreset.fawe;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.Util.ZipFileUtil;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldOperator;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldZipper;
import com.andrei1058.bedwars.configuration.ConfigPath;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.boydti.fawe.FaweAPI.getWorld;

public class FAWEOld extends MapManager {

    private int minX, minY, minZ, maxX, maxY, maxZ;
    private File schmFile;

    private ClipboardFormat format;

    public FAWEOld(Arena arena, String name) {
        super(arena, name);
        this.format = ClipboardFormat.SCHEMATIC;
        schmFile = new File("plugins/BedWars1058/Cache/" + getName() + "." + format.getExtension());
    }

    @Override
    public void onSetupSession() {
        Bukkit.createWorld(new WorldCreator(getName()));
    }

    @Override
    public void onRestart() {
        if (getArena() == null) return;
        World world = Bukkit.getWorld(getName());
        if (world != null) {
            TaskManager.IMP.async(() -> {
                AsyncWorld aw = AsyncWorld.wrap(getArena().getWorld());

                try {
                    format.load(schmFile).paste(aw, new Vector(minX, minY, minZ), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                aw.commit();
            });
            for (Entity e : world.getEntities()) {
                e.remove();
            }
        }
    }

    @Override
    public void onEnable(String name, Arena arena) {
        if (arena == null) return;
        World world = Bukkit.getWorld(name);
        if (world != null) {
            if (!world.getPlayers().isEmpty()) {
                Main.debug("Restoring arena world " + name + " : Kicking players out of the world ...");
                Location teleportLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
                for (Player p : world.getPlayers()) {
                    p.teleport(teleportLocation);
                    p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it.");
                }
            }
            arena.init(world);
            createSchematic();
        } else {

            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> TaskManager.IMP.async(() -> {
                if (!getBackupFile().exists()) {
                    arena.getMapManager().backupWorld(true);
                    return;
                }

                try {
                    ZipFileUtil.unzipFileIntoDirectory(getBackupFile(), getWorldFolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (Bukkit.getWorlds().get(0).getName().equals(getName())) {
                    World w = Bukkit.getWorlds().get(0);
                    w.setKeepSpawnInMemory(true);
                    arena.init(w);
                    createSchematic();
                } else {
                    // shared, multi arena
                    AsyncWorld w = AsyncWorld.create(new WorldCreator(name));
                    w.setKeepSpawnInMemory(true);
                    arena.init(w.getBukkitWorld());
                    createSchematic();
                }
            }), 40L);
        }
    }

    /**
     * Backup arena world.
     */
    public void backupWorld(boolean replace) {
        //if (isLevelWorld()) return;
        TaskManager.IMP.async(() -> {
            WorldOperator worldOperator = new WorldZipper(getName(), replace);
            if (replace) {
                if (schmFile.exists()) //noinspection ResultOfMethodCallIgnored
                    schmFile.delete();
            }
            worldOperator.execute();
        });
    }

    public void createSchematic() {
        TaskManager.IMP.laterAsync(() -> {
            Location w = getArena().getCm().getArenaLoc("waiting.Loc");
            int ra = getArena().getCm().getInt("worldBorder");
            minX = Math.min(w.getBlockX() + ra, w.getBlockX() - ra);
            minY = 0;
            minZ = Math.min(w.getBlockZ() + ra, w.getBlockZ() - ra);
            maxX = Math.max(w.getBlockX() + ra, w.getBlockX() - ra);
            maxY = getArena().getCm().getInt(ConfigPath.ARENA_CONFIGURATION_MAX_BUILD_Y);
            maxZ = Math.max(w.getBlockZ() + ra, w.getBlockZ() - ra);

            if (!schmFile.exists()) {
                try {
                    CuboidRegion r = new CuboidRegion(getWorld(getName()), new Vector(minX, minY, minZ), new Vector(maxX, maxY, maxZ));
                    new Schematic((Region) r).save(schmFile, format);
                } catch (IOException e) {
                    Main.plugin.getLogger().severe("Could not create " + getName() + " schematic!");
                    e.printStackTrace();
                }
            }
        }, 80);
    }


    private File getWorldFolder() {
        File worldContainer = Bukkit.getWorldContainer();
        return new File(worldContainer, getName());
    }

    private File getBackupFile() {
        File backupFolder = MapManager.backupFolder;
        return new File(backupFolder, getName() + ".zip");
    }

    @Override
    public void removeLobby() {
        Location loc1 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        TaskManager.IMP.async(() -> {
            int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
            int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
            int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

            AsyncWorld aw = AsyncWorld.wrap(getArena().getWorld());

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {
                        aw.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }

            aw.commit();
        });
    }
}