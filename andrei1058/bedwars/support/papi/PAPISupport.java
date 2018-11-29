package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.Main.database;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class PAPISupport extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "bw1058";
    }

    @Override
    public String getPlugin() {
        return Main.plugin.getName();
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
            Arena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null){
                return "0";
            }
            return String.valueOf(a.getPlayers().size());
        }

        String replay = "";
        switch (s) {
            case "stats_firstplay":
                replay = String.valueOf(new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(database.getFirstPlay(p)));
                break;
            case "stats_lastplay":
                replay = String.valueOf(new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(database.getLastPlay(p)));
                break;
            case "stats_kills":
                replay = String.valueOf(database.getKills(p));
                break;
            case "stats_wins":
                replay = String.valueOf(database.getWins(p));
                break;
            case "stats_finalkills":
                replay = String.valueOf(database.getFinalKills(p));
                break;
            case "stats_deaths":
                replay = String.valueOf(database.getDeaths(p));
                break;
            case "stats_looses":
                replay = String.valueOf(database.getLooses(p));
                break;
            case "stats_finaldeaths":
                replay = String.valueOf(database.getFinalDeaths(p));
                break;
            case "stats_bedsdestroyed":
                replay = String.valueOf(database.getBedsDestroyed(p));
                break;
            case "stats_gamesplayed":
                replay = String.valueOf(database.getGamesPlayed(p));
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
                                        bwt.getName().replace("{TeamColor}", String.valueOf(TeamColor.getChatColor(bwt.getColor()))));
                            }
                        }
                    } else {
                        replay += Language.getMsg(p, Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR);
                    }
                }
                break;
        }
        return replay;
    }
}
