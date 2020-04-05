package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.stats.StatsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

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
        return BedWars.plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if (s == null) return "";

        if (s.startsWith("arena_status_")){
            IArena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null){
                return Language.getMsg(p, Messages.ARENA_STATUS_RESTARTING_NAME);
            }
            return a.getDisplayStatus(Language.getDefaultLanguage());
        }

        if (s.startsWith("arena_count_")){
            int players = 0;

            String[] arenas = s.replace("arena_count_", "").split("\\+");
            IArena a;
            for (String arena : arenas){
                a = Arena.getArenaByName(arena);
                if (a != null){
                    players += a.getPlayers().size();
                }
            }

            return String.valueOf(players);
        }

        if (s.startsWith("group_count_")){
            return String.valueOf(Arena.getPlayers( s.replace("group_count_", "")));
        }

        if (s.startsWith("arena_group_")){
            String a = s.replace("arena_group_", "");
            IArena arena = Arena.getArenaByName(a);
            if (arena != null){
                return arena.getGroup();
            }
            return "-";
        }

        String replay = "";
        IArena a = Arena.getArenaByPlayer(p);
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
            case "player_team_color":
                if (a != null && a.isPlayer(p) && a.getStatus() == GameState.playing) {
                    ITeam team = a.getTeam(p);
                    if (team != null) {
                        replay += String.valueOf(team.getColor().chat());
                    }
                }
                break;
            case "player_team":
                if (a != null) {
                    if (ShoutCommand.isShout(p)) {
                        replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT);
                    }
                    if (a.isPlayer(p)) {
                        if (a.getStatus() == GameState.playing) {
                            ITeam bwt = a.getTeam(p);
                            if (bwt != null) {
                                replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM).replace("{TeamName}",
                                        bwt.getDisplayName(Language.getPlayerLanguage(p))).replace("{TeamColor}", String.valueOf(bwt.getColor().chat()));
                            }
                        }
                    } else {
                        replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR);
                    }
                }
                break;
            case "player_level":
                replay = BedWars.getLevelSupport().getLevel(p);
                break;
            case "player_level_raw":
                replay = String.valueOf(BedWars.getLevelSupport().getPlayerLevel(p));
                break;
            case "player_progress":
                replay = BedWars.getLevelSupport().getProgressBar(p);
                break;
            case "player_xp_formatted":
                replay = BedWars.getLevelSupport().getCurrentXpFormatted(p);
                break;
            case "player_xp":
                replay = String.valueOf(BedWars.getLevelSupport().getCurrentXp(p));
                break;
            case "player_rerq_xp_formatted":
                replay = BedWars.getLevelSupport().getRequiredXpFormatted(p);
                break;
            case "player_rerq_xp":
                replay = String.valueOf(BedWars.getLevelSupport().getRequiredXp(p));
                break;
            case "current_arena_group":
                if (a != null){
                    replay = a.getGroup();
                }
                break;
        }
        return replay;
    }
}
