package com.andrei1058.bedwars.api.events.gameplay;

import com.andrei1058.bedwars.api.team.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;

public class EggBridgeBuildEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private TeamColor teamColor;
    private Arena arena;
    private Block block;

    /**
     * Called when the eggBridge is building another block
     */
    public EggBridgeBuildEvent(TeamColor teamColor, Arena arena, Block block) {
        this.teamColor = teamColor;
        this.arena = arena;
        this.block = block;
    }

    /**
     * Get the arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the built block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Get the block's team color
     */
    public TeamColor getTeamColor() {
        return teamColor;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
