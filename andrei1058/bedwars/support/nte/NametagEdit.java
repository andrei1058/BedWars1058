package com.andrei1058.bedwars.support.nte;

import com.nametagedit.plugin.api.data.FakeTeam;
import com.nametagedit.plugin.api.data.Nametag;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class NametagEdit {

    private static boolean nteSupport = false;
    private static HashMap<Player, Nametag> cache = new HashMap<>();


    public static boolean isNametagEditSupport() {
        return nteSupport;
    }

    public static void setNteSupport(boolean nteSupport) {
        NametagEdit.nteSupport = nteSupport;
    }

    /** Save a player's NameTag Prefix/ Suffix before joins an arena*/
    public static void saveNametag(Player p){
        if (!isNametagEditSupport()) return;
        Nametag nt = com.nametagedit.plugin.NametagEdit.getApi().getNametag(p);
        FakeTeam ft = com.nametagedit.plugin.NametagEdit.getApi().getFakeTeam(p);
        if (nt == null) return;
        if (ft == null) return;
        if (cache.containsKey(p)){
            cache.replace(p, nt);
        } else {
            cache.put(p, nt);
        }
    }

    /**
     * Restore a player NameTag Prefix/ Suffix
     *
     * @since API 8
     */
    public static void restoreNametag(Player p) {
        if (!isNametagEditSupport()) return;
        if (cache.containsKey(p)){
            com.nametagedit.plugin.NametagEdit.getApi().reloadNametag(p);
            com.nametagedit.plugin.NametagEdit.getApi().setNametag(p, cache.get(p).getPrefix(), cache.get(p).getSuffix());
            cache.remove(p);
        }
    }
}
