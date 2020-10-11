package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.lobbysocket.ArenaSocket;
import com.andrei1058.bedwars.shop.ShopCache;
import com.andrei1058.bedwars.arena.tasks.ReJoinTask;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ReJoin {

    private UUID player;
    private IArena arena;
    private ITeam bwt;
    private ReJoinTask task = null;
    private final ArrayList<ShopCache.CachedItem> permanentsAndNonDowngradables = new ArrayList<>();

    private int kills = 0, finalKills = 0, deaths = 0, finalDeaths = 0, beds = 0;

    private static final List<ReJoin> reJoinList = new ArrayList<>();

    /**
     * Make rejoin possible for a player
     */
    public ReJoin(Player player, IArena arena, ITeam bwt, List<ShopCache.CachedItem> cachedArmor) {
        ReJoin rj = getPlayer(player);
        if (rj != null) {
            rj.destroy(true);
        }
        if (bwt == null) return;
        if (bwt.isBedDestroyed()) return;
        this.bwt = bwt;
        this.player = player.getUniqueId();
        this.arena = arena;
        reJoinList.add(this);
        BedWars.debug("Created ReJoin for " + player.getName() + " " + player.getUniqueId() + " at " + arena.getArenaName());
        storeStatsDiff(arena.getPlayerKills(player, false), arena.getPlayerKills(player, true), arena.getPlayerDeaths(player, false), arena.getPlayerDeaths(player, true), arena.getPlayerBedsDestroyed(player));
        if (bwt.getMembers().isEmpty()) task = new ReJoinTask(arena, bwt);
        this.permanentsAndNonDowngradables.addAll(cachedArmor);

        if (BedWars.autoscale) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "RC");
            json.addProperty("uuid", player.getUniqueId().toString());
            json.addProperty("arena_id", arena.getWorldName());
            json.addProperty("server", BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
            ArenaSocket.sendMessage(json.toString());
        }
    }

    /**
     * Check if a player has stored data
     */
    public static boolean exists(@NotNull Player pl) {
        BedWars.debug("ReJoin exists check " + pl.getUniqueId());
        for (ReJoin rj : getReJoinList()) {
            BedWars.debug("ReJoin exists check list scroll: " + rj.getPl().toString());
            if (rj.getPl().equals(pl.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a player ReJoin
     */
    @Nullable
    public static ReJoin getPlayer(@NotNull Player player) {
        BedWars.debug("ReJoin getPlayer " + player.getUniqueId());
        for (ReJoin rj : getReJoinList()) {
            if (rj.getPl().equals(player.getUniqueId())) {
                return rj;
            }
        }
        return null;
    }

    /**
     * Check if can reJoin
     */
    public boolean canReJoin() {
        BedWars.debug("ReJoin canReJoin  check.");
        if (arena == null) {
            BedWars.debug("ReJoin canReJoin arena is null " + player.toString());
            destroy(true);
            return false;
        }
        if (arena.getStatus() == GameState.restarting) {
            BedWars.debug("ReJoin canReJoin status is restarting " + player.toString());
            destroy(true);
            return false;
        }
        if (bwt == null) {
            BedWars.debug("ReJoin canReJoin bwt is null " + player.toString());
            destroy(true);
            return false;
        }
        if (bwt.isBedDestroyed()) {
            BedWars.debug("ReJoin canReJoin bed is destroyed " + player.toString());
            destroy(false);
            return false;
        }
        return true;
    }

    /**
     * Make a player re-join the arena
     */
    public boolean reJoin(Player player) {
        Sounds.playSound("rejoin-allowed", player);
        player.sendMessage(Language.getMsg(player, Messages.REJOIN_ALLOWED).replace("{arena}", getArena().getDisplayName()));
        return arena.reJoin(player);
    }

    /**
     * Destroy data and rejoin possibility
     */
    public void destroy(boolean destroyTeam) {
        BedWars.debug("ReJoin destroy for " + player.toString());
        reJoinList.remove(this);
        JsonObject json = new JsonObject();
        json.addProperty("type", "RD");
        json.addProperty("uuid", player.toString());
        json.addProperty("server", BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
        ArenaSocket.sendMessage(json.toString());
        if (bwt != null && destroyTeam && bwt.getMembers().isEmpty()) {
            bwt.setBedDestroyed(true);
            arena.checkWinner();
        }
    }

    /**
     * Get Player
     */
    public UUID getPlayer() {
        return player;
    }

    /**
     * Get player team
     */
    public ITeam getBwt() {
        return bwt;
    }

    /**
     * Get arena
     */
    public IArena getArena() {
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

    public ReJoinTask getTask() {
        return task;
    }

    public UUID getPl() {
        return player;
    }

    @SuppressWarnings("WeakerAccess")
    public List<ShopCache.CachedItem> getPermanentsAndNonDowngradables() {
        return permanentsAndNonDowngradables;
    }

    public static List<ReJoin> getReJoinList() {
        return Collections.unmodifiableList(reJoinList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof ReJoin)) return false;
        ReJoin reJoin = (ReJoin) o;
        return reJoin.getPl().equals(getPl());
    }
}
