package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

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
    public String onPlaceholderRequest(Player player, String s) {
        if (s == null) return null;

        if (s.startsWith("arena_status_")){
            IArena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null){
                return player == null ? Language.getDefaultLanguage().m(Messages.ARENA_STATUS_RESTARTING_NAME) :
                Language.getMsg(player, Messages.ARENA_STATUS_RESTARTING_NAME);
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
            case "current_arena_group":
                if (a != null){
                    replay = a.getGroup();
                }
                break;
        }
        return replay;
    }

    @Override
    public String onRequest(org.bukkit.OfflinePlayer player, String s) {
        if (s == null) return null;

        if (s.startsWith("arena_status_")){
            IArena a = Arena.getArenaByName(s.replace("arena_status_", ""));
            if (a == null){
                return player == null ? Language.getDefaultLanguage().m(Messages.ARENA_STATUS_RESTARTING_NAME) :
                        Language.getPlayerLanguage(player.getUniqueId()).m(Messages.ARENA_STATUS_RESTARTING_NAME);
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

        if (player == null) return null;
        String replay = "";
        Language playerLang = Language.getPlayerLanguage(player.getUniqueId());
        switch (s) {
            /*case "stats_firstplay":
                Instant firstPlay = BedWars.getStatsManager().getUnsafe(player.getUniqueId()).getFirstPlay();
                replay = new SimpleDateFormat(playerLang.m(Messages.FORMATTING_STATS_DATE_FORMAT)).format(firstPlay != null ? Timestamp.from(firstPlay) : null);
                break;
            case "stats_lastplay":
                Instant lastPlay = BedWars.getStatsManager().getUnsafe(player.getUniqueId()).getLastPlay();
                replay = new SimpleDateFormat(playerLang.m(Messages.FORMATTING_STATS_DATE_FORMAT)).format(lastPlay != null ? Timestamp.from(lastPlay) : null);
                break;*/
            case "stats_kills":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "kills"));
                break;
            case "stats_wins":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "wins"));
                break;
            case "stats_finalkills":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "final_kills"));
                break;
            case "stats_deaths":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "deaths"));
                break;
            case "stats_losses":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "looses"));
                break;
            case "stats_finaldeaths":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "final_deaths"));
                break;
            case "stats_bedsdestroyed":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "beds_destroyed"));
                break;
            case "stats_gamesplayed":
                replay = String.valueOf(BedWars.getRemoteDatabase().getColumn(player.getUniqueId(), "games_played"));
                break;
            case "current_online":
                replay = String.valueOf(Arena.getArenaByPlayer().size());
                break;
            case "current_arenas":
                replay = String.valueOf(Arena.getArenas().size());
                break;
        }
        return replay;
    }
}
