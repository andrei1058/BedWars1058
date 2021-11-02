package com.andrei1058.bedwars.arena.spectator.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private final Player player;

    private boolean nightVision = false;

    public boolean isNightVision() {
        return this.nightVision;
    }

    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    public PlayerMenuUtility(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}

