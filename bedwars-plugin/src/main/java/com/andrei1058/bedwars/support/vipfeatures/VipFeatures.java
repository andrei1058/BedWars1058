package com.andrei1058.bedwars.support.vipfeatures;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.vipfeatures.api.MiniGame;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VipFeatures extends MiniGame {

    public VipFeatures(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean isPlaying(Player p) {
        IArena a = Arena.getArenaByPlayer(p);
        if (a != null) {
            return !(a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting);
        }
        return false;
    }

    @Override
    public boolean hasBoosters() {
        return false;
    }

    @Override
    public String getDisplayName() {
        return "BedWars1058";
    }
}
