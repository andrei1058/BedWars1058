package com.andrei1058.bedwars.shop.main;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IBuyItem;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BuyCommand implements IBuyItem {

    private final List<String> asPlayer = new ArrayList<>();
    private final List<String> asConsole = new ArrayList<>();
    private final String upgradeIdentifier;


    public BuyCommand(String path, YamlConfiguration yml, String upgradeIdentifier) {
        BedWars.debug("Loading BuyCommand: " + path);
        this.upgradeIdentifier = upgradeIdentifier;
        for (String cmd : yml.getStringList(path + ".as-console")) {
            if (cmd.startsWith("/")) {
                cmd = cmd.replaceFirst("/", "");
            }
            asConsole.add(cmd);
        }
        for (String cmd : yml.getStringList(path + ".as-player")) {
            if (!cmd.startsWith("/")) {
                cmd = "/" + cmd;
            }
            asPlayer.add(cmd);
        }
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public void give(Player player, IArena arena) {
        BedWars.debug("Giving BuyCMD: " + getUpgradeIdentifier() + " to: " + player.getName());
        String playerName = player.getName();
        String playerUUID = player.getUniqueId().toString();
        ITeam team = arena.getTeam(player);
        String teamName = team == null ? "null" : team.getName();
        String teamDisplay = team == null ? "null" : team.getDisplayName(Language.getPlayerLanguage(player));
        String teamColor = team == null ? ChatColor.WHITE.toString() : team.getColor().chat().toString();
        String arenaIdentifier = arena.getArenaName();
        String arenaWorld = arena.getWorldName();
        String arenaDisplay = arena.getDisplayName();
        String arenaGroup = arena.getGroup();
        for (String playerCmd : asPlayer) {
            player.chat(playerCmd.replace("{player}", playerName)
                    .replace("{player_uuid}", playerUUID)
                    .replace("{team}", teamName).replace("{team_display}", teamDisplay)
                    .replace("{team_color}", teamColor).replace("{arena}", arenaIdentifier)
                    .replace("{arena_world}", arenaWorld).replace("{arena_display}", arenaDisplay)
                    .replace("{arena_group}", arenaGroup));
        }
        for (String consoleCmd : asConsole) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), consoleCmd
                    .replace("{player}", playerName).replace("{player_uuid}", playerUUID)
                    .replace("{team}", teamName).replace("{team_display}", teamDisplay)
                    .replace("{team_color}", teamColor).replace("{arena}", arenaIdentifier)
                    .replace("{arena_world}", arenaWorld).replace("{arena_display}", arenaDisplay)
                    .replace("{arena_group}", arenaGroup));
        }
    }

    @Override
    public String getUpgradeIdentifier() {
        return upgradeIdentifier;
    }

    @Override
    public ItemStack getItemStack() {
        return null;
    }

    @Override
    public void setItemStack(ItemStack itemStack) {

    }

    @Override
    public boolean isAutoEquip() {
        return false;
    }

    @Override
    public void setAutoEquip(boolean autoEquip) {

    }

    @Override
    public boolean isPermanent() {
        return false;
    }

    @Override
    public void setPermanent(boolean permanent) {

    }
}
