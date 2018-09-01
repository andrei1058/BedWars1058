package com.andrei1058.bedwars.support.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class SupportPAPI {

    private static supp supportPAPI = new noPAPI();

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

    public static void setSupportPAPI(supp s) {
        supportPAPI = s;
    }
}
