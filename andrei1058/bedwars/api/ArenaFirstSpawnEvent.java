package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaFirstSpawnEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private Arena arena;
    private BedWarsTeam team;
    private BedWarsTeam.PlayerVault playerVault;

    /**
     * Called when a member is respawned on his island
     * This is called about 5 seconds after PlayerRespawnEvent from Bukkit
     * After the respawn countdown.
     *
     * @since API 9
     */
    public ArenaFirstSpawnEvent(Player player, Arena arena, BedWarsTeam team, BedWarsTeam.PlayerVault playerVault) {
        this.player = player;
        this.arena = arena;
        this.team = team;
        this.playerVault = playerVault;
    }

    /**
     * Get the arena
     *
     * @since API 9
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get the player's team
     *
     * @since API 9
     */
    public BedWarsTeam getTeam() {
        return team;
    }

    /**
     * Get the player
     *
     * @since API 9
     */
    public Player getPlayer() {
        return player;
    }

    /** Get the PlayerVault
     * It contains the permanet items given to the player at the first spawn and
     * also there can be added permanent items bought from the shop.
     *
     * @since API9*/
    public BedWarsTeam.PlayerVault getPlayerVault() {
        return playerVault;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
