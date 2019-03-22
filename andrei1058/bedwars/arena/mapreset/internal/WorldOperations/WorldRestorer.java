package com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations;
import java.io.File;
import java.io.IOException;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.internal.Util.FileUtil;
import com.andrei1058.bedwars.arena.mapreset.internal.Util.ZipFileUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldRestorer implements WorldOperator {

	private final String worldName;
	private boolean jobDone = false;
	private Arena arena = null;
	
	public WorldRestorer(String worldName, Arena arena) {
		this.worldName = worldName;
		this.arena = arena;
	}
	
	public void execute() {
		Main.debug("Restoring arenaworld " + worldName + " : Kicking players out of the world ...");
		kickPlayers();
		Main.debug("Restoring arenaworld " + worldName + " : Unloading world ...");
		unloadWorld();
		Main.debug("Restoring arenaworld " + worldName + " : Cleaning world ...");
		cleanData();
		Main.debug("Restoring arenaworld " + worldName + " : Restoring data ...");
		restoreData();
		Main.debug("Reloading arenaworld " + worldName + " : Reloading world ...");
		if (arena != null) {
			reloadWorld();
			Main.debug("Reloading arenaworld " + worldName + " : Done !");
		}
		jobDone = true;
	}
	
	private void kickPlayers() {
		World world = Bukkit.getWorld(worldName);
		if(world == null) return;
		Location teleportLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
		for(Player p : world.getPlayers()) {
			p.teleport(teleportLocation);
			p.sendMessage(ChatColor.BLUE + "The arena you were in was restored. You were kicked out of it.");
		}
	}

	private void unloadWorld() {
		World world = Bukkit.getWorld(worldName);
		if(world == null) return;
		Bukkit.unloadWorld(world, false);
	}

	private void cleanData() {
		if (getBackupFile().exists()) FileUtil.delete(getWorldFolder());
	}
	
	private void restoreData() {
		if (!getBackupFile().exists()){
			if (arena != null) arena.getMapManager().backupWorld(true);
			return;
		}
		try {
			ZipFileUtil.unzipFileIntoDirectory(getBackupFile(), getWorldFolder());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void reloadWorld() {
		WorldCreator worldCreator = new WorldCreator(worldName);
		World w = worldCreator.createWorld();
		w.setKeepSpawnInMemory(false);
		w.setAutoSave(false);
		arena.init(w);
	}
	
	private File getWorldFolder() {
		File worldContainer = Bukkit.getWorldContainer();
		File worldFolder = new File(worldContainer, worldName);
		return worldFolder;
	}
	
	private File getBackupFile() {
		File backupFolder = MapManager.backupFolder;
		File backupFile = new File(backupFolder, worldName + ".zip");
		return backupFile;
	}
	
	public Boolean isJobDone() {
		return this.jobDone;
	}
	
	public String getResultMessage() {
		if(isJobDone()) {
			return "Successfully restored arenaworld " + worldName;
		} else {
			return "Arenaworld " + worldName + " isn't restored yet.";
		}
	}

}