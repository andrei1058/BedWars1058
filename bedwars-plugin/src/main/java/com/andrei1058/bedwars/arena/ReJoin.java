/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.tasks.ReJoinTask;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.lobbysocket.ArenaSocket;
import com.andrei1058.bedwars.shop.ShopCache;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class ReJoin {

    private UUID player;
    private IArena arena;
    private ITeam bwt;
    private ReJoinTask task = null;
    private final ArrayList<ShopCache.CachedItem> permanentsAndNonDowngradables = new ArrayList<>();

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

        if (player.getGameMode() != GameMode.SURVIVAL) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(true);
                player.setFlying(true);
            }, 20L);
        }
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
            if (bwt != null) {
                for (Player p2 : arena.getPlayers()) {
                    p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", bwt.getColor().chat().toString())
                            .replace("{TeamName}", bwt.getDisplayName(Language.getPlayerLanguage(p2))));
                }
                for (Player p2 : arena.getSpectators()) {
                    p2.sendMessage(getMsg(p2, Messages.TEAM_ELIMINATED_CHAT).replace("{TeamColor}", bwt.getColor().chat().toString())
                            .replace("{TeamName}", bwt.getDisplayName(Language.getPlayerLanguage(p2))));
                }
            }
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
