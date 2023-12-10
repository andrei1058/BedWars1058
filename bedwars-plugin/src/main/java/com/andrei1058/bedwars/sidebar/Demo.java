package com.andrei1058.bedwars.sidebar;

import com.andrei1058.spigot.sidebar.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Demo {

    public void lol() {

        // on server start
        SidebarManager sidebarHandler = SidebarManager.init();

        // tab logic
        LinkedList<SidebarLine> headers = new LinkedList<>();
        LinkedList<SidebarLine> footers = new LinkedList<>();

        headers.add(new SidebarLineAnimated(new String[]{
                ChatColor.RED+"AAAAAAA",
                ChatColor.BLUE+"BBBBBBB"
        }));
        headers.add(new SidebarLine() {
            @Override
            public @NotNull String getLine() {
                return "This is static";
            }
        });

        footers.add(new SidebarLineAnimated(new String[]{
                ChatColor.YELLOW+"CCCCCC",
                ChatColor.AQUA+"DDDDDD"
        }));

        LinkedList<PlaceholderProvider> placeholders = new LinkedList<>();
        placeholders.add(new PlaceholderProvider("{server}", () -> "andrei1058.dev"));

        TabHeaderFooter tab = new TabHeaderFooter(headers, footers, placeholders);

        // apply to player
        sidebarHandler.sendHeaderFooter(player, tab);

        // refresh tabs (apply animations)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                sidebarHandler.sendHeaderFooter(player, tab);
            });
        }, 1L, 10L);

    }
}
