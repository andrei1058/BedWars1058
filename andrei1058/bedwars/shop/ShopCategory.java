package com.andrei1058.bedwars.shop;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.configuration.Language.*;

public class ShopCategory {

    private static List<ShopCategory> shopCategories = new ArrayList<>();

    private int invSize = 27;
    private List<CategoryContent> content = new ArrayList<>();
    private String name;
    private ShopCategory parent;

    public ShopCategory(int invSize, String name){
        this.invSize = invSize;
        this.name = name;
        shopCategories.add(this);
    }

    public void addContent(CategoryContent content){
        this.content.add(content);
    }

    public List<CategoryContent> getContent() {
        return content;
    }

    public int getInvSize() {
        return invSize;
    }

    public String getName() {
        return name;
    }

    public void openToPlayer(Player p){
        String path = Misc.replaceLast((getName().replace("main.", Messages.SHOP_PATH)+".name"), ".invContents", "");
        String name = "§c"+getName()+" name not set :(";
        if (getPlayerLanguage(p).exists(path)){
            name = getMsg(p, path);
        } else {
            getPlayerLanguage(p).set(path, name);
        }
        Inventory inv = Bukkit.createInventory(p, invSize, name);
        for (CategoryContent cc : getContent()){
            ItemStack i = cc.getItemStack().clone();
            ItemMeta im = i.getItemMeta();
            String ccNameP = getName().replace("main.", Messages.SHOP_PATH)+"."+cc.getName()+".name";
            String ccLoreP = getName().replace("main.", Messages.SHOP_PATH)+"."+cc.getName()+".lore";
            if (getPlayerLanguage(p).exists(ccNameP)) {
                im.setDisplayName(getMsg(p, ccNameP));
            } else {
                im.setDisplayName("&cName not set");
                getPlayerLanguage(p).set(ccNameP, "Not set");
            }
            List<String> lore = new ArrayList<>();
            if (getPlayerLanguage(p).exists(ccLoreP)){
                for (String s : getList(p, ccLoreP)){
                    lore.add(s.replace("{cost}", String.valueOf(cc.getContentAction().getCost())).replace("{currency}", getMsg(p, "meaning."+cc.getContentAction().getCurrency().toLowerCase())));
                }
            } else {
                getPlayerLanguage(p).set(ccLoreP, new ArrayList<>());
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(cc.getSlot(), i);
        }
        p.openInventory(inv);
    }

    public static List<ShopCategory> getShopCategories() {
        return shopCategories;
    }

    public String getDisplayName(Player p){
        return getMsg(p, Misc.replaceLast((getName().replace("main.", Messages.SHOP_PATH)+".name"), ".invContents", ""));
    }

    public ShopCategory getParent() {
        return parent;
    }

    public void setParent(ShopCategory parent) {
        this.parent = parent;
    }

    public static ShopCategory getByName(String name){
        for (ShopCategory sc : shopCategories){
            if (sc.getName().equalsIgnoreCase(name)){
                return sc;
            }
        }
        return null;
    }
}
