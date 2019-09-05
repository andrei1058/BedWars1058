package com.andrei1058.bedwars.api.server;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class RestoreAdapter {

    private Plugin plugin;

    /**
     * @param owner adapter owner.
     */
    public RestoreAdapter(Plugin owner) {
        this.plugin = owner;
    }

    /**
     * Get adapter owner.
     */
    public Plugin getOwner() {
        return plugin;
    }

    /**
     * Load the world.
     * Arenas will be initialized automatically based on WorldLoadEvent.
     */
    public abstract void onEnable(IArena a);

    /**
     * Restore the world.
     * call new Arena when it's done.
     */
    public abstract void onRestart(IArena a);

    /**
     * Unload the world.
     * This is usually used for /bw unloadArena name
     */
    public abstract void onDisable(IArena a);

    /**
     * Load the world for setting it up.
     */
    public abstract void onSetupSessionStart(ISetupSession s);

    /**
     * Unload the world.
     */
    public abstract void onSetupSessionClose(ISetupSession s);

    /**
     * Remove lobby blocks.
     */
    public abstract void onLobbyRemoval(IArena a);

    /**
     * Check if given world exists.
     */
    public abstract boolean isWorld(String name);

    /**
     * Delete a world.
     */
    public abstract void deleteWorld(String name);

    /**
     * Clone an arena world.
     */
    public abstract void cloneArena(String name1, String name2);

    /**
     * Get world container.
     */
    public abstract List<String> getWorldsList();

    /**
     * Convert worlds if it is necessary before loading them.
     * Let them load on BedWars1058 main Thread so they will be converted before getting loaded.
     */
    public abstract void convertWorlds();
}
