package com.andrei1058.bedwars.arena.mapreset;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class FAWE extends MapManager {


    private ClipboardFormat format = ClipboardFormat.STRUCTURE;
    private File schematic;
    private Vector wMin, wMax, lMin, lMax;

    /**
     * Create a new map manager for an arena.
     *
     * @param arena
     * @param name
     */
    @SuppressWarnings("deprecation")
    public FAWE(Arena arena, String name) {
        super(arena, name);
        switch (Main.getServerVersion()) {
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                format = ClipboardFormat.SCHEMATIC;
                break;
        }
        schematic = new File(Main.plugin.getDataFolder() + "/Cache/" + getName() + "." + format.getExtension());

        ConfigManager cm = new ConfigManager(name, Main.plugin.getDataFolder() + "/Arenas", true);

        int border = cm.getInt("worldBorder");

        wMin = new Vector(-1 * border, 0, -1 * border);
        wMax = new Vector(border, 200, border);

        if (arena != null) {
            Location loc1 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                    loc2 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
            if (!(loc1 == null && loc2 == null)) {
                int minX, minY, minZ;
                int maxX, maxY, maxZ;
                minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
                maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
                minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
                maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
                minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
                maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                lMin = new Vector(minX, minY, minZ);
                lMax = new Vector(maxX, maxY, maxZ);
            }
        }
    }

    /**
     * Leave it empty because there is no need to unload the world in this case.
     */
    public void unloadWorld() {

    }

    public void restoreWorld(String name, Arena arena) {
        if (!schematic.exists()) return;
        try {
            EditSession editSession = format.load(schematic).paste(new BukkitWorld(Bukkit.getWorld(getName())), wMin, false, false, null);
            editSession.flushQueue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backupWorld(boolean replace) {
        if (schematic.exists() && !replace) return;
        CuboidRegion region = new CuboidRegion(new BukkitWorld(Bukkit.getWorld(getName())), wMin, wMax);
        Schematic s = new Schematic(region);
        try {
            s.save(schematic, format);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeLobby() {
        if (lMin == null || lMax == null) return;
        TaskManager.IMP.async(() -> {
            AsyncWorld world = AsyncWorld.create(new WorldCreator(getName()));

            for (int x = lMin.getBlockX(); x < lMax.getBlockX(); x++) {
                for (int y = lMin.getBlockY(); y < lMax.getBlockY(); y++) {
                    for (int z = lMin.getBlockZ(); z < lMax.getBlockZ(); z++) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }

            world.commit();
        });
    }
}
