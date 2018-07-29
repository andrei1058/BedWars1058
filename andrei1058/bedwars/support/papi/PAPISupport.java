package com.andrei1058.bedwars.support.papi;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.Main.database;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class PAPISupport extends PlaceholderExpansion {

    private static supp supportPAPI = new noPAPI();

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
        if (s.equalsIgnoreCase("stats_firstplay")){
            return String.valueOf(new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(database.getFirstPlay(p)));
        } else if (s.equalsIgnoreCase("stats_lastplay")){
            return String.valueOf(new SimpleDateFormat(getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT)).format(database.getLastPlay(p)));
        } else if (s.equalsIgnoreCase("stats_kills")) {
            return String.valueOf(database.getKills(p));
        } else if (s.equalsIgnoreCase("stats_wins")){
            return String.valueOf(database.getWins(p));
        } else if (s.equalsIgnoreCase("stats_finalkills")){
            return String.valueOf(database.getFinalKills(p));
        } else if (s.equalsIgnoreCase("stats_deaths")){
            return String.valueOf(database.getDeaths(p));
        } else if (s.equalsIgnoreCase("stats_looses")){
            return String.valueOf(database.getLooses(p));
        } else if (s.equalsIgnoreCase("stats_finaldeaths")){
            return String.valueOf(database.getFinalDeaths(p));
        } else if (s.equalsIgnoreCase("stats_bedsdestroyed")){
            return String.valueOf(database.getBedsDestroyed(p));
        } else if (s.equalsIgnoreCase("stats_gamesplayed")){
            return String.valueOf(database.getGamesPlayed(p));
        } else if (s.equalsIgnoreCase("current_online")){
            return String.valueOf(Arena.getArenaByPlayer().size());
        } else if (s.equalsIgnoreCase("current_arenas")){
            return String.valueOf(Arena.getArenas().size());
        }
        return null;
    }

    public interface supp {
        String replace(Player p, String s);
    }

    public static class noPAPI implements supp {

        @Override
        public String replace(Player p, String s) {
            return s;
        }
    }

    public static class withPAPI implements supp {

        @Override
        public String replace(Player p, String s) {
            return PlaceholderAPI.setPlaceholders(p, s);
        }
    }

    public static supp getSupportPAPI() {
        return supportPAPI;
    }
}
