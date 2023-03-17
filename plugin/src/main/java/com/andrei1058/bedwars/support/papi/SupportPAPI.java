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

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class SupportPAPI {

    private static supp supportPAPI = new noPAPI();

    public interface supp {
        String replace(Player p, String s);

        List<String> replace(Player p, List<String> strings);
    }

    public static class noPAPI implements supp {

        @Override
        public String replace(Player p, String s) {
            return s;
        }

        @Override
        public List<String> replace(Player p, List<String> strings) {
            return strings;
        }
    }

    public static class withPAPI implements supp {

        @Override
        public String replace(Player p, String s) {
            return PlaceholderAPI.setPlaceholders(p, s);
        }

        @Override
        public List<String> replace(Player p, List<String> strings) {
            return PlaceholderAPI.setPlaceholders(p, strings);
        }
    }

    public static supp getSupportPAPI() {
        return supportPAPI;
    }

    public static void setSupportPAPI(supp s) {
        supportPAPI = s;
    }
}
