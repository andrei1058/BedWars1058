/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2022 Andrei Dascălu
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

package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.player.PlayerLangChangeEvent;
import com.andrei1058.bedwars.api.language.LanguageOld;
import com.andrei1058.bedwars.api.language.LanguageService;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.language.defaults.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.plugin;

public class LanguageManager implements LanguageService {

    private static LanguageService INSTANCE;

    /**
     * Initialize language manager.
     */
    public static void onLoad() {
        if (null != INSTANCE) {
            return;
        }
        INSTANCE = new LanguageManager();
    }

    public static LanguageService getInstance() {
        return INSTANCE;
    }


    ///

    private final List<LanguageOld> registeredLanguages = new ArrayList<>();
    private final HashMap<UUID, LanguageOld> langByPlayer = new HashMap<>();
    private LanguageOld defaultLanguage;


    private LanguageManager() {
        // initialize available languages
        for (LanguageOld lang : new LanguageOld[]{
                new English(), new Romanian(), new Italian(), new Polish(), new Spanish(), new Russian(),
                new Bangla(), new Persian(), new Hindi(), new Portuguese(), new Turkish(), new Indonesia()
        }) {
            // update cached prefix in class
            lang.setPrefix(lang.m(Messages.PREFIX));
            // make language available
            registeredLanguages.add(lang);
        }
    }

    public Collection<LanguageOld> getRegisteredLanguages() {
        return Collections.unmodifiableCollection(registeredLanguages);
    }

    public void saveIfNotExists(String path, Object data) {
        for (LanguageOld l : registeredLanguages) {
            if (l.getYml().get(path) == null) {
                l.set(path, data);
            }
        }
    }

    public boolean isLanguageExist(String iso) {
        for (LanguageOld l : registeredLanguages) {
            if (l.getIso().equalsIgnoreCase(iso)) {
                return true;
            }
        }
        return false;
    }

    public LanguageOld getLang(String iso) {
        for (LanguageOld l : registeredLanguages) {
            if (l.getIso().equalsIgnoreCase(iso)) {
                return l;
            }
        }
        return null;
    }

    public LanguageOld getLangOrDefault(String iso) {
        for (LanguageOld l : registeredLanguages) {
            if (l.getIso().equalsIgnoreCase(iso)) {
                return l;
            }
        }
        return defaultLanguage;
    }

    public boolean setPlayerLanguage(UUID uuid, String iso) {

        if (iso == null) {
            if (langByPlayer.containsKey(uuid)) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    PlayerLangChangeEvent e = new PlayerLangChangeEvent(
                            player, langByPlayer.get(uuid).getIso(), defaultLanguage.getIso()
                    );
                    Bukkit.getPluginManager().callEvent(e);
                    if (e.isCancelled()) return false;
                }
            }
            langByPlayer.remove(uuid);
            return true;
        }

        LanguageOld newLang = getLang(iso);
        if (newLang == null) return false;
        LanguageOld oldLang = getPlayerLanguage(uuid);
        if (oldLang.getIso().equals(newLang.getIso())) return false;

        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            PlayerLangChangeEvent e = new PlayerLangChangeEvent(player, oldLang.getIso(), newLang.getIso());
            Bukkit.getPluginManager().callEvent(e);
            if (e.isCancelled()) return false;
        }

        if (getDefaultLanguage().getIso().equals(newLang.getIso())) {
            langByPlayer.remove(uuid);
            return true;
        }

        if (langByPlayer.containsKey(uuid)) {
            langByPlayer.replace(uuid, newLang);
        } else {
            langByPlayer.put(uuid, newLang);
        }
        return true;
    }

    @Override
    public void onPlayerLeave(@NotNull Player p) {
        //Save preferred language
        if (langByPlayer.containsKey(p.getUniqueId())) {
            final UUID u = p.getUniqueId();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                String iso = langByPlayer.get(p.getUniqueId()).getIso();
                if (LanguageManager.getInstance().isLanguageExist(iso)) {
                    if (com.andrei1058.bedwars.BedWars.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_DISABLED_LANGUAGES).contains(iso))
                        iso = getDefaultLanguage().getIso();
                    com.andrei1058.bedwars.BedWars.getRemoteDatabase().setLanguage(u, iso);
                }
                langByPlayer.remove(p.getUniqueId());
            });
        }
    }

    @Override
    public boolean register(LanguageOld lang) {
        if (isLanguageExist(lang.getIso())){
            return false;
        }
        lang.setPrefix(lang.m(Messages.PREFIX));
        return registeredLanguages.add(lang);
    }

    @Override
    public void setupCustomStatsMessages() {
        BedWars api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        for (LanguageOld l : getRegisteredLanguages()) {
            if (l == null) continue;
            if (l.getYml() == null) continue;
            /* save messages for stats gui items if custom items added */
            if (api.getConfigs().getMainConfig().getYml().get(ConfigPath.GENERAL_CONFIGURATION_STATS_PATH) == null)
                return;
            for (String item : api.getConfigs().getMainConfig().getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_STATS_PATH).getKeys(false)) {
                if (ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE.contains(item)) continue;
                if (l.getYml().getDefaults() == null || !l.getYml().getDefaults().contains(Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-name"))
                    l.getYml().addDefault(Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-name", "Name not set");
                if (l.getYml().getDefaults() == null || !l.getYml().getDefaults().contains(Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-lore"))
                    l.getYml().addDefault(Messages.PLAYER_STATS_GUI_PATH + "-" + item + "-lore", Collections.singletonList("lore not set"));
            }
            l.save();
        }
    }

    @Override
    public List<String> getScoreboard(Player p, String path, String alternative) {
        LanguageOld language = getPlayerLanguage(p);
        if (language.exists(path)) {
            return language.l(path);
        } else {
            if (path.split("\\.").length == 3) {
                String[] sp = path.split("\\.");
                String path2 = sp[1];
                path2 = String.valueOf(path2.charAt(0)).toUpperCase() + path2.substring(1).toLowerCase();
                path2 = sp[0] + "." + path2 + "." + sp[2];
                if (language.exists(path2)) {
                    return language.l(path);
                } else if (language.exists(sp[0] + "." + sp[1].toUpperCase() + "." + sp[2])) {
                    return language.l(sp[0] + "." + sp[1].toUpperCase() + "." + sp[2]);
                }
            }
        }
        return language.l(alternative);
    }

    @Override
    public LanguageOld getPlayerLanguage(UUID p) {
        return langByPlayer.getOrDefault(p, getDefaultLanguage());
    }

    public LanguageOld getDefaultLanguage() {
        return defaultLanguage;
    }

    @Override
    public LanguageOld getPlayerLanguage(@NotNull Player p) {
        return getPlayerLanguage(p.getUniqueId());
    }

    @Override
    public List<String> getList(Player p, String path) {
        return langByPlayer.getOrDefault(p.getUniqueId(), getDefaultLanguage()).l(path);
    }

    @Override
    public String getMsg(@Nullable Player p, String path) {
        if (p == null){
            return getDefaultLanguage().m(path);
        }
        return langByPlayer.getOrDefault(p.getUniqueId(), getDefaultLanguage()).m(path);
    }

    @Override
    public void setDefaultLanguage(LanguageOld defaultLanguage) {
        if (!registeredLanguages.contains(defaultLanguage)) {
            throw new IllegalStateException("Given language is not in the languages list!");
        }
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public void unregister(LanguageOld language) {
        if (!registeredLanguages.contains(language)) {
            throw new IllegalStateException("Given language is not registered!");
        }
        if (getDefaultLanguage().getIso().equals(language.getIso())) {
            throw new IllegalStateException("Cannot unregister server default language!");
        }
        registeredLanguages.remove(language);
    }
}
