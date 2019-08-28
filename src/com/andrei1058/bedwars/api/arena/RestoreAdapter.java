package com.andrei1058.bedwars.api.arena;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
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
     * Once you are done use Arena#init to mark it as done.
     */
    public abstract void onEnable(Arena a);

    /**
     * Restore the world.
     * call new Arena when it's done.
     */
    public abstract void onRestart(Arena a);

    /**
     * Unload the world.
     * This is usually used for /bw unloadArena name
     */
    public abstract void onDisable(Arena a);

    /**
     * Load the world for setting it up.
     */
    public abstract void onSetupSessionStart(SetupSession s);

    /**
     * Unload the world.
     */
    public abstract void onSetupSessionClose(SetupSession s);

    /**
     * Remove lobby blocks.
     */
    public abstract void onLobbyRemoval(Arena a);

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
     * If you do it async will probably cause issues.
     */
    public abstract void convertWorlds();
}
