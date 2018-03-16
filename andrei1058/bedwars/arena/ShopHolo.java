package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.configuration.Language;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.nms;

public class ShopHolo {
    /** Shop holograms per language <iso, holo></iso,>*/
    private static List<ShopHolo> shopHolo = new ArrayList<>();

    private String iso;
    private ArmorStand a1, a2;
    private Location l;
    private Arena a;

    public ShopHolo(String iso, ArmorStand a1, ArmorStand a2, Location l, Arena a) {
        for (ShopHolo sh : getShopHolo()){
            if (sh.l == l && sh.iso.equalsIgnoreCase(iso)){
                update();
                return;
            }
        }
        this.a1 = a1;
        this.a2 = a2;
        this.iso = iso;
        this.l = l;
        this.a = a;
        update();
        shopHolo.add(this);
    }

    public void update(){
        for (Player p2 : l.getWorld().getPlayers()) {
            if (Language.getPlayerLanguage(p2).getIso().equalsIgnoreCase(iso)) continue;
            if (a1 != null) {
                nms.hideEntity(a1, p2);
            }
            if (a2 != null) {
                nms.hideEntity(a2, p2);
            }
        }
    }

    public static void clearForArena(Arena a){
        for (ShopHolo sh : new ArrayList<>(getShopHolo())){
            if (sh.a == a){
                shopHolo.remove(sh);
            }
        }
    }

    public String getIso() {
        return iso;
    }

    public static List<ShopHolo> getShopHolo() {
        return shopHolo;
    }
}
