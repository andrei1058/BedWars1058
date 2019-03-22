package com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldSaver implements WorldOperator {
	
	private final String worldName;
	private boolean jobDone = false;
	
	public WorldSaver(String worldName) {
		this.worldName = worldName;
	}
	
	public void execute() {
		World world = Bukkit.getWorld(worldName);
		if(world == null) return;
		world.save();
		jobDone = true;
	}
	
	public Boolean isJobDone() {
		return this.jobDone;
	}
	
	public String getResultMessage() {
		if(isJobDone()) {
			return "Successfully saved world " + worldName;
		} else {
			return "World " + worldName + " isn't saved yet.";
		}
	}
	
}
