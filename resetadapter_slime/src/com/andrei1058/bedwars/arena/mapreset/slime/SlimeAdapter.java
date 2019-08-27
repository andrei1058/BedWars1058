package com.andrei1058.bedwars.arena.mapreset.slime;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.*;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;

import java.io.IOException;

public class SlimeAdapter extends MapManager {

    private SlimePlugin slime;

    public SlimeAdapter(Arena arena, String name) {
        super(arena, name);
        slime = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
    }

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            SlimeLoader sqlLoader = slime.getLoader("mysql");
            String[] spawn = getArena().getCm().getString("waiting.Loc").split(",");
            SlimeWorld.SlimeProperties props = SlimeWorld.SlimeProperties.builder().difficulty(1).allowAnimals(false).allowMonsters(false).spawnX(Double.parseDouble(spawn[0]))
                    .spawnY(Double.parseDouble(spawn[1])).spawnZ(Double.parseDouble(spawn[2])).pvp(true).readOnly(true).build();
            try {
                // Note that this method should be called asynchronously
                SlimeWorld world = slime.loadWorld(sqlLoader, getName(), props);

                // This method must be called synchronously
                Bukkit.getScheduler().runTask(Main.plugin, () -> {
                    slime.generateWorld(world);
                    getArena().init(Bukkit.getWorld(getName()));
                });
            } catch (UnknownWorldException | IOException | CorruptedWorldException | NewerFormatException | WorldInUseException | UnsupportedWorldException ex) {
                /* Exception handling */
            }
        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }
}
