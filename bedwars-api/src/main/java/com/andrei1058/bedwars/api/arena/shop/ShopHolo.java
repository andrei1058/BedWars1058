package com.andrei1058.bedwars.api.arena.shop;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ShopHolo {
    /**
     * Shop holograms per language <iso, holo></iso,>
     */
    private static List<ShopHolo> shopHolo = new ArrayList<>();
    private static BedWars api = null;

    private String iso;
    private ArmorStand a1, a2;
    private Location l;
    private IArena a;

    public ShopHolo(String iso, ArmorStand a1, ArmorStand a2, Location l, IArena a) {
        this.l = l;
        for (ShopHolo sh : getShopHolo()) {
            if (sh.l == l && sh.iso.equalsIgnoreCase(iso)) {
                if (a1 != null) a1.remove();
                if (a2 != null) a2.remove();
                return;
            }
        }
        this.a1 = a1;
        this.a2 = a2;
        this.iso = iso;
        this.a = a;
        if (a1 != null) a1.setMarker(true);
        if (a2 != null) a2.setMarker(true);
        shopHolo.add(this);
        if (api == null) api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
    }

    public void update() {
        if (l == null) {
            Bukkit.broadcastMessage("LOCATION IS NULL");
        }
        for (Player p2 : l.getWorld().getPlayers()) {
            if (Language.getPlayerLanguage(p2).getIso().equalsIgnoreCase(iso)) continue;
            if (a1 != null) {
                api.getVersionSupport().hideEntity(a1, p2);
            }
            if (a2 != null) {
                api.getVersionSupport().hideEntity(a2, p2);
            }
        }
    }

    public void updateForPlayer(Player p, String lang) {
        if (lang.equalsIgnoreCase(iso)) return;
        if (a1 != null) {
            api.getVersionSupport().hideEntity(a1, p);
        }
        if (a2 != null) {
            api.getVersionSupport().hideEntity(a2, p);
        }
    }

    public static void clearForArena(IArena a) {
        for (ShopHolo sh : new ArrayList<>(getShopHolo())) {
            if (sh.a == a) {
                shopHolo.remove(sh);
            }
        }
    }

    public IArena getA() {
        return a;
    }

    public String getIso() {
        return iso;
    }

    public static List<ShopHolo> getShopHolo() {
        return shopHolo;
    }
}
