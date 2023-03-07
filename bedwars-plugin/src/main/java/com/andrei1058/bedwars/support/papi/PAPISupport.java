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

package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.stats.PlayerStats;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PAPISupport extends PlaceholderExpansion {

    @NotNull
    @Override
    public String getIdentifier() {
        return "bw1058";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "andrei1058";
    }

    @NotNull
    @Override
    public String getVersion() {
        return BedWars.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String s) {

        /* Non-Player required placeholders */

        if (s.startsWith("arena_status_")) {
            IArena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null) {
                return player == null ? Language.getDefaultLanguage().m(Messages.ARENA_STATUS_RESTARTING_NAME) :
                        Language.getMsg(player, Messages.ARENA_STATUS_RESTARTING_NAME);
            }
            return a.getDisplayStatus(Language.getDefaultLanguage());
        }

        if (s.startsWith("arena_count_")) {
            int players = 0;

            String[] arenas = s.replace("arena_count_", "").split("\\+");
            IArena a;
            for (String arena : arenas) {
                a = Arena.getArenaByName(arena);
                if (a != null) {
                    players += a.getPlayers().size();
                }
            }

            return String.valueOf(players);
        }

        if (s.startsWith("group_count_")) {
            return String.valueOf(Arena.getPlayers(s.replace("group_count_", "")));
        }

        if (s.startsWith("arena_group_")) {
            String a = s.replace("arena_group_", "");
            IArena arena = Arena.getArenaByName(a);
            if (arena != null) {
                return arena.getGroup();
            }
            return "-";
        }

        /* Player required placeholders */
        if (player == null) return null;

        // stats placeholders
        if(s.startsWith("stats_")) {
            String targetedStat = s.replaceFirst("stats_", "");
            if(targetedStat.isEmpty() || targetedStat.isBlank()) {
                return null;
            }
            PlayerStats stats = BedWars.getStatsManager().getUnsafe(player.getUniqueId());
            if(stats == null) {
                return null;
            }
            switch (targetedStat) {
                case "firstplay":
                    Instant firstPlay = stats.getFirstPlay();
                    return new SimpleDateFormat(getMsg(player, Messages.FORMATTING_STATS_DATE_FORMAT)).format(firstPlay != null ? Timestamp.from(firstPlay) : null);
                case "lastplay":
                    Instant lastPlay = stats.getLastPlay();
                    return new SimpleDateFormat(getMsg(player, Messages.FORMATTING_STATS_DATE_FORMAT)).format(lastPlay != null ? Timestamp.from(lastPlay) : null);
                case "total_kills":
                    return String.valueOf(stats.getTotalKills());
                case "kills":
                    return String.valueOf(stats.getKills());
                case "wins":
                    return String.valueOf(stats.getWins());
                case "finalkills":
                    return String.valueOf(stats.getFinalKills());
                case "deaths":
                    return String.valueOf(stats.getDeaths());
                case "losses":
                    return String.valueOf(stats.getLosses());
                case "finaldeaths":
                    return String.valueOf(stats.getFinalDeaths());
                case "bedsdestroyed":
                    return String.valueOf(stats.getBedsDestroyed());
                case "gamesplayed":
                    return String.valueOf(stats.getGamesPlayed());
            }
        }

        // other placeholders
        String response = "";
        IArena a = Arena.getArenaByPlayer(player);
        switch (s) {
            case "current_online":
                response = String.valueOf(Arena.getArenaByPlayer().size());
                break;
            case "current_arenas":
                response = String.valueOf(Arena.getArenas().size());
                break;
            case "current_playing":
                if (a != null) {
                    response = String.valueOf(a.getPlayers().size());
                }
                break;
            case "player_team_color":
                if (a != null && a.isPlayer(player) && a.getStatus() == GameState.playing) {
                    ITeam team = a.getTeam(player);
                    if (team != null) {
                        response += String.valueOf(team.getColor().chat());
                    }
                }
                break;
            case "player_team":
                if (a != null) {
                    if (ShoutCommand.isShout(player)) {
                        response += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT);
                    }
                    if (a.isPlayer(player)) {
                        if (a.getStatus() == GameState.playing) {
                            ITeam bwt = a.getTeam(player);
                            if (bwt != null) {
                                response += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM).replace("{TeamName}",
                                        bwt.getDisplayName(Language.getPlayerLanguage(player))).replace("{TeamColor}", String.valueOf(bwt.getColor().chat()));
                            }
                        }
                    } else {
                        response += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR);
                    }
                }
                break;
            case "player_level":
                response = BedWars.getLevelSupport().getLevel(player);
                break;
            case "player_level_raw":
                response = String.valueOf(BedWars.getLevelSupport().getPlayerLevel(player));
                break;
            case "player_progress":
                response = BedWars.getLevelSupport().getProgressBar(player);
                break;
            case "player_xp_formatted":
                response = BedWars.getLevelSupport().getCurrentXpFormatted(player);
                break;
            case "player_xp":
                response = String.valueOf(BedWars.getLevelSupport().getCurrentXp(player));
                break;
            case "player_rerq_xp_formatted":
                response = BedWars.getLevelSupport().getRequiredXpFormatted(player);
                break;
            case "player_rerq_xp":
                response = String.valueOf(BedWars.getLevelSupport().getRequiredXp(player));
                break;
            case "player_status":
                if(a != null) {
                    switch (a.getStatus()) {
                        case waiting:
                        case starting:
                            response = "WAITING";
                            break;
                        case playing:
                            if(a.isPlayer(player)) {
                                response = "PLAYING";
                            } else if(a.isSpectator(player)) {
                                response = "SPECTATING";
                            } else {
                                response = "IN_GAME_BUT_NOT"; // this shouldnt happen
                            }
                            break;
                        case restarting:
                            response = "RESTARTING";
                            break;
                    }
                } else {
                    response = "NONE";
                }
                break;
            case "current_arena_group":
                if (a != null) {
                    response = a.getGroup();
                }
                break;
            case "elapsed_time":
                if (a != null) {
                    Instant startTime = a.getStartTime();
                    if (null != startTime){
                        Duration time = Duration.ofMillis(Instant.now().minusMillis(startTime.toEpochMilli()).toEpochMilli());
                        if (time.toHours() == 0){
                            response = String.format("%02d:%02d", time.toMinutes(), time.toSeconds());
                        } else {
                            response = String.format("%02d:%02d:%02d", time.toHours(), time.toMinutes(), time.toSeconds());
                        }
                    } else response = "";
                }
                break;
        }
        return response;
    }
}
