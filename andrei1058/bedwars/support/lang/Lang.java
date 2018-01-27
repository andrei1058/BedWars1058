package com.andrei1058.bedwars.support.lang;

import org.bukkit.entity.Player;

public interface Lang {
    /*Returns iso code*/
    String getLang(Player p);
    void setLang(Player p, String iso);
}
