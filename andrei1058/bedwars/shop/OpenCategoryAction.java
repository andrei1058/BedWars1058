package com.andrei1058.bedwars.shop;

import org.bukkit.entity.Player;

public class OpenCategoryAction extends ContentAction {

    private ShopCategory shop;

    public OpenCategoryAction(ShopCategory shop){
        this.shop = shop;
    }

    @Override
    public void doStuff(Player p) {
        shop.openToPlayer(p);
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getCurrency() {
        return "iron";
    }
}
