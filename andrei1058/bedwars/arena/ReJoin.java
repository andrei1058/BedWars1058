package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GameState;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReJoin {

    private Player player;
    private Arena arena;
    private BedWarsTeam bwt;

    private int kills = 0, finalKills = 0, deaths = 0, finalDeaths = 0, beds = 0;

    private static List<ReJoin> reJoinList = new ArrayList<>();

    /**
     * Make rejoin possible for a player
     */
    public ReJoin(Player player, Arena arena) {
        if (exists(player)) getPlayer(player).destroy();
        this.bwt = arena.getTeam(player);
        if (bwt == null) return;
        if (bwt.isBedDestroyed()) return;
        this.player = player;
        this.arena = arena;
        storeStatsDiff(arena.getPlayerKills(player, false), arena.getPlayerKills(player, true), arena.getPlayerDeaths(player, false), arena.getPlayerDeaths(player, true), arena.getPlayerBedsDestroyed(player));
    }

    /**
     * Check if a player has stored data
     */
    public static boolean exists(Player player) {
        for (ReJoin rj : new ArrayList<>(reJoinList)) {
            if (rj.player == player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a player ReJoin
     */
    public static ReJoin getPlayer(Player player) {
        for (ReJoin rj : new ArrayList<>(reJoinList)) {
            if (rj.player == player) {
                return rj;
            }
        }
        return null;
    }

    /**
     * Check if can reJoin
     */
    public boolean canReJoin() {
        if (arena == null) {
            destroy();
            return false;
        }
        if (arena.getStatus() == GameState.restarting) {
            destroy();
            return false;
        }
        if (bwt == null) {
            destroy();
            return false;
        }
        if (bwt.isBedDestroyed()) {
            destroy();
            return false;
        }
        return true;
    }

    /**
     * Make a player re-join the arena
     */
    public boolean reJoin() {
        return arena.reJoin(this);
    }

    /**
     * Destroy data and rejoin possibility
     */
    public void destroy() {
        reJoinList.remove(this);
    }

    /**
     * Get Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get player team
     */
    public BedWarsTeam getBwt() {
        return bwt;
    }

    /**
     * Get arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Save arena stats cache for player in order to save them correctly to database
     */
    private void storeStatsDiff(int kills, int finalKills, int deaths, int finalDeaths, int beds) {
        this.kills = kills;
        this.finalKills = finalKills;
        this.deaths = deaths;
        this.finalDeaths = finalDeaths;
        this.beds = beds;
    }

    public int getKills() {
        return kills;
    }

    public int getFinalDeaths() {
        return finalDeaths;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getFinalKills() {
        return finalKills;
    }

    public int getBeds() {
        return beds;
    }
}
