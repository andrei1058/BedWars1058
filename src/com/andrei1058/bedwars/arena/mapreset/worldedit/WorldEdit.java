package com.andrei1058.bedwars.arena.mapreset.worldedit;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations.WorldRestorer;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.io.*;
import java.util.Objects;

public class WorldEdit extends MapManager {

    private File lobbySchema;
    private ClipboardFormat format;
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    public WorldEdit(Arena arena, String name) {
        super(arena, name);
        format = BuiltInClipboardFormat.SPONGE_SCHEMATIC;
        lobbySchema = new File(Main.plugin.getDataFolder() + "/Cache", name + "_lobby.schem");
    }

    public void onEnable(String name, Arena arena) {

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            if (Bukkit.getWorld(name) == null) {
                WorldRestorer we = new WorldRestorer(name, arena);
                we.restoreData();
                Bukkit.createWorld(new WorldCreator(name));
            }
            arena.init(Bukkit.getWorld(getName()));

            Location loc1 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                    loc2 = arena.getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
            if (loc1 == null || loc2 == null) return;
            minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
            maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
            minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
            maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
            minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
            maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

            //backup lobby
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                if (!lobbySchema.exists()) {
                    createSchematic(BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ), lobbySchema);
                }

                File bed;
                Location loc;
                for (BedWarsTeam bwt : getArena().getTeams()) {
                    bed = new File("plugins/BedWars1058/Cache/" + getName() + "_bed_" + bwt.getName() + "." + format.getPrimaryFileExtension());
                    if (bed.exists()) continue;
                    loc = bwt.getBed();
                    createSchematic(BlockVector3.at(loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1), BlockVector3.at(loc.getBlockX() + 1, loc.getBlockY() + 1, loc.getBlockZ() + 1), bed);
                }
            }, 60L);

        }, 25L);
    }

    /**
     * Unload world.
     */
    public void onRestart() {
        //if (isLevelWorld()) return;
        Bukkit.getScheduler().runTask(Main.plugin, () -> {
            if (lobbySchema.exists()) {
                pasteSchematic(lobbySchema, minX, minY, minZ);
            }

            for (Block b : getArena().getPlaced()) {
                b.setType(Material.AIR);
            }

            File bed;
            Location loc;
            for (BedWarsTeam bwt : getArena().getTeams()) {
                bed = new File("plugins/BedWars1058/Cache/" + getName() + "_bed_" + bwt.getName() + "." + format.getPrimaryFileExtension());
                if (!bed.exists()) {
                    Main.plugin.getLogger().severe("Could not restore " + bwt.getName() + " bed on arena " + getName());
                    continue;
                }
                loc = bwt.getBed();
                pasteSchematic(bed, Math.min(loc.getBlockX() - 1, loc.getBlockX() + 2), Math.min(loc.getBlockY() - 1, loc.getBlockY() + 1), Math.min(loc.getBlockZ() - 1, loc.getBlockZ() + 1));
            }

            for (Entity e : getArena().getWorld().getEntities()) {
                e.remove();
            }
        });
    }

    private void createSchematic(BlockVector3 min, BlockVector3 max, File out) {
        Main.debug("Creating schematic " + out.getPath() + " on " + getName());
        CuboidRegion region = new CuboidRegion(new BukkitWorld(Bukkit.getWorld(getName())), min, max);
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
        forwardExtentCopy.setCopyingEntities(true);
        try {
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
        try (ClipboardWriter writer = format.getWriter(new FileOutputStream(out))) {
            writer.write(clipboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pasteSchematic(File file, int x, int y, int z){
        Main.debug("Pasting schematic " + file.getPath() + " on " + getName());
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clipboard != null) {
            try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(Bukkit.getWorld(getName())), -1)) {
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BlockVector3.at(x, y, z))
                        .ignoreAirBlocks(true)
                        .build();
                Operations.complete(operation);
                editSession.flushSession();
            } catch (WorldEditException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove lobby.
     */
    public void removeLobby() {
        Location loc1 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1),
                loc2 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
        if (loc1 == null || loc2 == null) return;
        try (EditSession editSession = com.sk89q.worldedit.WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(Bukkit.getWorld(getName())), -1)) {
            editSession.setBlocks(new CuboidRegion(new BukkitWorld(Bukkit.getWorld(getName())), BlockVector3.at(minX, minY, minZ), BlockVector3.at(maxX, maxY, maxZ)), BlockTypes.AIR.getDefaultState());
            editSession.flushSession();
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSetupSession() {
        super.onSetupSession();
        if (lobbySchema.exists()) {
            lobbySchema.delete();
        }
    }

    @Override
    public void backupWorld(boolean replace) {
        super.backupWorld(replace);
        if (replace) {
            if (lobbySchema.exists()) lobbySchema.delete();
            for (File f : Objects.requireNonNull(new File("plugins/BedWars1058/Cache").listFiles())) {
                if (f.getName().contains(getName() + "_bed_")) //noinspection ResultOfMethodCallIgnored
                    f.delete();
            }
        }
    }
}
