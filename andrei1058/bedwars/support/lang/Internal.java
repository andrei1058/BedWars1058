package com.andrei1058.bedwars.support.lang;

import com.andrei1058.bedwars.api.PlayerLangChangeEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.plugin;

public class Internal implements Lang {

    private static ConfigManager x = new ConfigManager("database", "plugins/"+plugin.getName()+"/Languages", false);
    @Override
    public String getLang(Player p) {
        if (x.getYml().get(p.getName()) != null){
            if (Language.isLanguageExist(x.getYml().getString(p.getName()))){
                return x.getYml().getString(p.getName());
            }
        }
        return lang.getIso();
    }

    @Override
    public void setLang(Player p, String iso) {
        Language newLang = Language.getLang(iso);
        Language oldLang = Language.getLangByPlayer().containsKey(p) ? Language.getPlayerLanguage(p) : Language.getLanguages().get(0);
        PlayerLangChangeEvent e = new PlayerLangChangeEvent(p, oldLang, newLang);
        Bukkit.getPluginManager().callEvent(e);
        if (e.isCancelled()) return;

        if (Language.getLangByPlayer().containsKey(p)){
            Language.getLangByPlayer().replace(p, lang);
        } else {
            Language.getLangByPlayer().put(p, lang);
        }

        x.set(p.getName(), iso);
        if (config.getLobbyWorldName().equalsIgnoreCase(p.getWorld().getName())){
            Arena.sendLobbyCommandItems(p);
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getP() == p) {
                    sb.remove();
                }
            }
            if (p.getScoreboard() != null){
                Misc.giveLobbySb(p);
            }
        }
    }
}
