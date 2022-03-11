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
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PAPISupport extends PlaceholderExpansion {

    private static final SimpleDateFormat elapsedFormat = new SimpleDateFormat("HH:mm");

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
    public String onPlaceholderRequest(Player player, String s) {
        if (s == null) return null;

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

        if (player == null) return null;
        String replay = "";
        IArena a = Arena.getArenaByPlayer(player);
        switch (s) {
            case "stats_firstplay":
                Instant firstPlay = BedWars.getStatsManager().get(player.getUniqueId()).getFirstPlay();
                replay = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_STATS_DATE_FORMAT)).format(firstPlay != null ? Timestamp.from(firstPlay) : null);
                break;
            case "stats_lastplay":
                Instant lastPlay = BedWars.getStatsManager().get(player.getUniqueId()).getLastPlay();
                replay = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_STATS_DATE_FORMAT)).format(lastPlay != null ? Timestamp.from(lastPlay) : null);
                break;
            case "stats_total_kills":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getTotalKills());
                break;
            case "stats_kills":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getKills());
                break;
            case "stats_wins":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getWins());
                break;
            case "stats_finalkills":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getFinalKills());
                break;
            case "stats_deaths":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getDeaths());
                break;
            case "stats_losses":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getLosses());
                break;
            case "stats_finaldeaths":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getFinalDeaths());
                break;
            case "stats_bedsdestroyed":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getBedsDestroyed());
                break;
            case "stats_gamesplayed":
                replay = String.valueOf(BedWars.getStatsManager().get(player.getUniqueId()).getGamesPlayed());
                break;
            case "current_online":
                replay = String.valueOf(Arena.getArenaByPlayer().size());
                break;
            case "current_arenas":
                replay = String.valueOf(Arena.getArenas().size());
                break;
            case "current_playing":
                if (a != null) {
                    replay = String.valueOf(a.getPlayers().size());
                }
                break;
            case "player_team_color":
                if (a != null && a.isPlayer(player) && a.getStatus() == GameState.playing) {
                    ITeam team = a.getTeam(player);
                    if (team != null) {
                        replay += String.valueOf(team.getColor().chat());
                    }
                }
                break;
            case "player_team":
                if (a != null) {
                    if (ShoutCommand.isShout(player)) {
                        replay += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT);
                    }
                    if (a.isPlayer(player)) {
                        if (a.getStatus() == GameState.playing) {
                            ITeam bwt = a.getTeam(player);
                            if (bwt != null) {
                                replay += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM).replace("{TeamName}",
                                        bwt.getDisplayName(Language.getPlayerLanguage(player))).replace("{TeamColor}", String.valueOf(bwt.getColor().chat()));
                            }
                        }
                    } else {
                        replay += Language.getMsg(player, Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR);
                    }
                }
                break;
            case "player_level":
                replay = BedWars.getLevelSupport().getLevel(player);
                break;
            case "player_level_raw":
                replay = String.valueOf(BedWars.getLevelSupport().getPlayerLevel(player));
                break;
            case "player_progress":
                replay = BedWars.getLevelSupport().getProgressBar(player);
                break;
            case "player_xp_formatted":
                replay = BedWars.getLevelSupport().getCurrentXpFormatted(player);
                break;
            case "player_xp":
                replay = String.valueOf(BedWars.getLevelSupport().getCurrentXp(player));
                break;
            case "player_rerq_xp_formatted":
                replay = BedWars.getLevelSupport().getRequiredXpFormatted(player);
                break;
            case "player_rerq_xp":
                replay = String.valueOf(BedWars.getLevelSupport().getRequiredXp(player));
                break;
            case "player_status":
                if(a != null) {
                    switch (a.getStatus()) {
                        case waiting:
                        case starting:
                            replay = "WAITING";
                            break;
                        case playing:
                            if(a.isPlayer(player)) {
                                replay = "PLAYING";
                            } else if(a.isSpectator(player)) {
                                replay = "SPECTATING";
                            } else {
                                replay = "IN_GAME_BUT_NOT"; // this shouldnt happen
                            }
                            break;
                        case restarting:
                            replay = "RESTARTING";
                            break;
                    }
                } else {
                    replay = "NONE";
                }
                break;
            case "current_arena_group":
                if (a != null) {
                    replay = a.getGroup();
                }
                break;
            case "elapsed_time":
                if (a != null) {
                    replay = elapsedFormat.format(Instant.now().minusMillis(a.getStartTime().toEpochMilli()));
                }
                break;

        }
        return replay;
    }
}
