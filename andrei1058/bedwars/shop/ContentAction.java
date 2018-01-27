package com.andrei1058.bedwars.shop;

import org.bukkit.entity.Player;

public abstract class ContentAction {

    public abstract void doStuff(Player p);

    public abstract int getCost();

    public abstract String getCurrency();
}
