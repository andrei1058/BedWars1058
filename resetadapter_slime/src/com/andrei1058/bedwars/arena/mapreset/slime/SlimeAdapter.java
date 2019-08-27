package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getName;

public class SlimeAdapter extends RestoreAdapter {

    private SlimePlugin slime;

    public SlimeAdapter(Plugin plugin) {
        super(plugin);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    }

    @Override
    public void onEnable(Arena a) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeLoader sqlLoader = slime.getLoader("mysql");
            String[] spawn = a.getCm().getString("waiting.Loc").split(",");
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(sqlLoader, getName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    slime.generateWorld(world);
                    a.init(Bukkit.getWorld(getName()));
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException ex) {
                /* Exception handling */
            }
        });
    }

    @Override
    public void onRestart(Arena a) {

    }

    @Override
    public void onDisable(Arena a) {

    }

    @Override
    public boolean onSetupSessionStart(SetupSession s) {
        return false;
    }

    @Override
    public void onSetupSessionClose(SetupSession s) {

    }

    @Override
    public void onLobbyRemoval(Arena a) {

    }

    @Override
    public boolean isWorld(String name) {
        return false;
    }

    @Override
    public void deleteWorld(String name) {

    }

    @Override
    public void cloneArena(String name1, String name2) {

    }

    @Override
    public File getWorldContainer() {
        return null;
    }
}
