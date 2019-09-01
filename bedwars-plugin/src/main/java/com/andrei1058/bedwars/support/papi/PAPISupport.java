package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.stats.StatsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.language.Language.getMsg;

public class PAPISupport extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "bw1058";
    }

    @Override
    public String getAuthor() {
        return "andrei1058";
    }

    @Override
    public String getVersion() {
        return Main.plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {

        if (s.contains("arena_status_")){
            Arena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null){
                return "NULL";
            }
            return a.getDisplayStatus(Main.lang);
        }

        if (s.contains("arena_count_")){
            Arena a = Arena.getArenaByName(s.replace("arena_count_", ""));
            if (a == null){
                return "0";
            }
            return String.valueOf(a.getPlayers().size());
        }

        if (s.contains("group_count_")){
            return String.valueOf(Arena.getPlayers( s.replace("group_count_", "")));
        }

        String replay = "";
        switch (s) {
            case "stats_firstplay":
                replay = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(StatsManager.getStatsCache().getPlayerFirstPlay(p.getUniqueId()));
                break;
            case "stats_lastplay":
                replay = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(StatsManager.getStatsCache().getPlayerLastPlay(p.getUniqueId()));
                break;
            case "stats_kills":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerKills(p.getUniqueId()));
                break;
            case "stats_wins":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerWins(p.getUniqueId()));
                break;
            case "stats_finalkills":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerFinalKills(p.getUniqueId()));
                break;
            case "stats_deaths":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerDeaths(p.getUniqueId()));
                break;
            case "stats_losses":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerLoses(p.getUniqueId()));
                break;
            case "stats_finaldeaths":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerFinalDeaths(p.getUniqueId()));
                break;
            case "stats_bedsdestroyed":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerBedsDestroyed(p.getUniqueId()));
                break;
            case "stats_gamesplayed":
                replay = String.valueOf(StatsManager.getStatsCache().getPlayerGamesPlayed(p.getUniqueId()));
                break;
            case "current_online":
                replay = String.valueOf(Arena.getArenaByPlayer().size());
                break;
            case "current_arenas":
                replay = String.valueOf(Arena.getArenas().size());
                break;
            case "player_team":
                Arena a = Arena.getArenaByPlayer(p);
                if (a != null) {
                    if (ShoutCommand.isShout(p)) {
                        replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT);
                    }
                    if (a.isPlayer(p)) {
                        if (a.getStatus() == GameState.playing) {
                            BedWarsTeam bwt = a.getTeam(p);
                            if (bwt != null) {
                                replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM).replace("{TeamName}",
                                        bwt.getName()).replace("{TeamColor}", String.valueOf(TeamColor.getChatColor(bwt.getColor())));
                            }
                        }
                    } else {
                        replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR);
                    }
                }
                break;
            case "player_level":
                replay = Main.getLevelSupport().getLevel(p);
                break;
            case "player_progress":
                replay = Main.getLevelSupport().getProgressBar(p);
                break;
            case "player_xp_formatted":
                replay = Main.getLevelSupport().getCurrentXpFormatted(p);
                break;
            case "player_xp":
                replay = String.valueOf(Main.getLevelSupport().getCurrentXp(p));
                break;
            case "player_rerq_xp_formatted":
                replay = Main.getLevelSupport().getRequiredXpFormatted(p);
                break;
            case "player_rerq_xp":
                replay = String.valueOf(Main.getLevelSupport().getRequiredXp(p));
                break;
        }
        return replay;
    }
}
