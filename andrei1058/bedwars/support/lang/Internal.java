package com.andrei1058.bedwars.support.lang;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Language;
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
        if (Language.getLangByPlayer().containsKey(p)){
            Language.getLangByPlayer().replace(p, Language.getLang(iso));
        } else {
            Language.getLangByPlayer().put(p, Language.getLang(iso));
        }
        x.set(p.getName(), iso);
        if (config.getLobbyWorldName().equalsIgnoreCase(p.getWorld().getName())){
            Arena.sendMultiarenaLobbyItems(p);
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
