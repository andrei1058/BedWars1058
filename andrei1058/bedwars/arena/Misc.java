package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.exceptions.InvalidMaterialException;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Misc {

    private static boolean updateAvailable = false;
    private static String newVersion = "";

    public static void moveToLobbyOrKick(Player p) {
        if (getServerType() != ServerType.BUNGEE) {
            if (!p.getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName())) {
                p.teleport(config.getConfigLoc("lobbyLoc"));
                Arena a = Arena.getArenaByPlayer(p);
                if (a != null) {
                    if (a.isSpectator(p)) {
                        a.removeSpectator(p, false);
                    } else {
                        a.removePlayer(p, false);
                    }
                }
                return;
            }
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(config.getYml().getString("lobbyServer"));
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (p.isOnline()) {
                p.kickPlayer(getMsg(p, Messages.ARENA_RESTART_PLAYER_KICK));
            }
        }, 120L);
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    /*public static void checkLobbyServer() {
        if (spigot.getBoolean("settings.bungeecord")) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("GetServers");
            plugin.getServer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            debug("Requesting bungee servers.");
        } else {
            if (getServerType() == ServerType.BUNGEE) {
                plugin.getLogger().severe("Please set bungeecord to true in spigot.yml");
            }
        }
    }*/

    public static ItemStack getArenaGUI(Player p) {
        ItemStack i;
        try {
            i = new ItemStack(Material.valueOf(config.getYml().getString("items.arenaGui.itemStack")),
                    1, (short) config.getYml().getInt("items.arenaGui.data"));
            if (Material.valueOf(config.getYml().getString("items.arenaGui.itemStack")) == Material.SKULL_ITEM &&
                    config.getYml().getInt("items.arenaGui.data") == 3) {
                SkullMeta sm = (SkullMeta) i.getItemMeta();
                sm.setOwner(p.getName());
                i.setItemMeta(sm);
            }
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading items.arenaGui.itemStack or Data");
            i = new ItemStack(Material.BEDROCK);
        }
        ItemMeta im = i.getItemMeta();
        im.spigot().setUnbreakable(true);
        try {
            im.setLore(getList(p, Messages.ARENA_GUI_ITEM_LORE));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading arena gui's lore");
        }
        try {
            im.setDisplayName(getMsg(p, Messages.ARENA_GUI_ITEM_NAME));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading arena gui's name");
        }
        try {
            if (config.getYml().getBoolean("items.arenaGui.enchanted")) {
                im.addEnchant(Enchantment.LUCK, 1, true);
            }
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } catch (Exception ex) {
        }
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack getStatsItem(Player p) {
        ItemStack i;
        try {
            i = new ItemStack(Material.valueOf(config.getYml().getString("items.stats.itemStack")),
                    1, (short) config.getYml().getInt("items.stats.data"));
            if (Material.valueOf(config.getYml().getString("items.stats.itemStack")) == Material.SKULL_ITEM &&
                    config.getYml().getInt("items.stats.data") == 3) {
                SkullMeta sm = (SkullMeta) i.getItemMeta();
                sm.setOwner(p.getName());
                i.setItemMeta(sm);
            }
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading items.stats.itemStack or Data");
            i = new ItemStack(Material.BEDROCK);
        }
        ItemMeta im = i.getItemMeta();
        im.spigot().setUnbreakable(true);
        try {
            im.setLore(getList(p, Messages.PLAYER_STATS_ITEM_LORE));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading stats lore");
        }
        try {
            im.setDisplayName(getMsg(p, Messages.PLAYER_STATS_ITEM_NAME));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading ststs name");
        }
        try {
            if (config.getYml().getBoolean("items.stats.enchanted")) {
                im.addEnchant(Enchantment.LUCK, 1, true);
            }
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } catch (Exception ex) {
        }
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack createItem(Material material, byte data, String name, List<String> lore) {
        ItemStack i = new ItemStack(material, 1, data);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    /**
     * Create an itemStack
     *
     * @since API 9
     */
    @Nullable
    public static ItemStack createItemStack(String material, int data, String name, List<String> lore, boolean enchanted, String customData) throws InvalidMaterialException {
        Material m;
        try {
            m = Material.valueOf(material);
        } catch (Exception e) {
            throw new InvalidMaterialException(material);
        }
        ItemStack i = new ItemStack(m, 1, (short) data);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        if (enchanted) {
            im.addEnchant(Enchantment.LUCK, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        i.setItemMeta(im);
        if (!customData.isEmpty()){
            i = nms.addCustomData(i, customData);
        }
        return i;
    }

    public static void checkUpdate() {
        try {
            HttpURLConnection checkUpdate = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=50942").openConnection();
            checkUpdate.setDoOutput(true);
            String old = plugin.getDescription().getVersion();
            String newV = new BufferedReader(new InputStreamReader(checkUpdate.getInputStream())).readLine();
            if (!newV.equalsIgnoreCase(old)) {
                updateAvailable = true;
                newVersion = newV;
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("------------------------------------");
                plugin.getLogger().info(" ");
                plugin.getLogger().info("There is a new version available!");
                plugin.getLogger().info("Version: " + newVersion);
                plugin.getLogger().info(" ");
                plugin.getLogger().info("https://www.spigotmc.org/resources/50942/");
                plugin.getLogger().info("------------------------------------");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
                plugin.getLogger().info("                                    ");
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Couldn't check for updates!");
        }
    }

    public static BlockFace getDirection(Location loc) {
        int rotation = (int) loc.getYaw();
        if (rotation < 0) {
            rotation += 360;
        }
        if (0 <= rotation && rotation < 22) {
            return BlockFace.SOUTH;
        } else if (22 <= rotation && rotation < 67) {
            return BlockFace.SOUTH;
        } else if (67 <= rotation && rotation < 112) {
            return BlockFace.WEST;
        } else if (112 <= rotation && rotation < 157) {
            return BlockFace.NORTH;
        } else if (157 <= rotation && rotation < 202) {
            return BlockFace.NORTH;
        } else if (202 <= rotation && rotation < 247) {
            return BlockFace.NORTH;
        } else if (247 <= rotation && rotation < 292) {
            return BlockFace.EAST;
        } else if (292 <= rotation && rotation < 337) {
            return BlockFace.SOUTH;
        } else if (337 <= rotation && rotation < 360) {
            return BlockFace.SOUTH;
        } else {
            return BlockFace.SOUTH;
        }
    }

    public static boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public static String getNewVersion() {
        return newVersion;
    }

    public static boolean isProjectile(Material i) {
        return Material.EGG == i || Material.FIREBALL == i || Material.SNOW_BALL == i || Material.ARROW == i;
    }

    /**
     * unknown die reason or unknown killer message
     */
    public static void unknownReason(BedWarsTeam t, Arena a, Player victim) {
        if (t.isBedDestroyed()) {
            for (Player on : a.getPlayers()) {
                on.sendMessage(getMsg(on, Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString())
                        .replace("{PlayerName}", victim.getName()));
            }
            for (Player on : a.getSpectators()) {
                on.sendMessage(getMsg(on, Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString())
                        .replace("{PlayerName}", victim.getName()));
            }
        } else {
            for (Player on : a.getPlayers()) {
                on.sendMessage(getMsg(on, Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString())
                        .replace("{PlayerName}", victim.getName()));
            }
            for (Player on : a.getSpectators()) {
                on.sendMessage(getMsg(on, Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR).replace("{PlayerColor}", TeamColor.getChatColor(t.getColor()).toString())
                        .replace("{PlayerName}", victim.getName()));
            }
        }
    }

    /**
     * create TextComponent message
     */
    public static TextComponent msgHoverClick(String msg, String hover, String click, ClickEvent.Action clickAction) {
        TextComponent tc = new TextComponent(msg);
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()));
        tc.setClickEvent(new ClickEvent(clickAction, click));
        return tc;
    }

    /**
     * add default stats gui item
     */
    public static void addDefaultStatsItem(YamlConfiguration yml, int slot, Material itemstack, int data, String path) {
        yml.addDefault("statsGUI." + path + ".itemStack", itemstack.toString());
        yml.addDefault("statsGUI." + path + ".data", data);
        yml.addDefault("statsGUI." + path + ".slot", slot);
    }

    /**
     * open stats GUI to player
     */
    public static void openStatsGUI(Player p) {

        /** cache stats */
        int kills = database.getKills(p), deaths = database.getDeaths(p), looses = database.getLooses(p), wins = database.getWins(p),
                finalKills = database.getFinalKills(p), finalDeaths = database.getFinalDeaths(p), bedsDestroyed = database.getBedsDestroyed(p), gamesPlayed = database.getGamesPlayed(p);
        Timestamp firstPlay = database.getFirstPlay(p), lastPlay = database.getLastPlay(p);

        /** cache time format */
        String timeFormat = getMsg(p, Messages.FORMATTING_STATS_DATE_FORMAT), never = getMsg(p, Messages.MEANING_NEVER);

        /** create inventory */
        Inventory inv = Bukkit.createInventory(null, config.getInt("statsGUI.invSize"), replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_INV_NAME),
                kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed, firstPlay, lastPlay, timeFormat, p.getName(), never, true));

        /** add custom items to gui */
        for (String s : config.getYml().getConfigurationSection("statsGUI").getKeys(false)) {
            /** skip inv size, it isn't a content */
            if (s.equalsIgnoreCase("invSize")) continue;
            /** create new itemStack for content */
            ItemStack i = new ItemStack(Material.valueOf(config.getYml().getString("statsGUI." + s + ".itemStack").toUpperCase()), 1, (byte) config.getInt("statsGUI." + s + ".data"));
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_PATH + "." + s + ".name"), kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed,
                    firstPlay, lastPlay, timeFormat, p.getName(), never, true));
            List<String> lore = new ArrayList<>();
            for (String string : getList(p, Messages.PLAYER_STATS_GUI_PATH + "." + s + ".lore")) {
                lore.add(replaceStatsPlaceholders(p, string, kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed, firstPlay, lastPlay, timeFormat, p.getName(), never, true));
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(config.getInt("statsGUI." + s + ".slot"), i);
        }

        p.openInventory(inv);
    }

    public static String replaceStatsPlaceholders(Player pl, String s, int kills, int deaths, int looses, int wins, int finalKills, int finalDeaths,
                                                  int beds, int games, Timestamp first, Timestamp last, String timeFormat, String player, String never, boolean papiReplacements) {
        String lastS = last == null ? never : String.valueOf(new SimpleDateFormat(timeFormat).format(last)),
                firstS = first == null ? never : String.valueOf(new SimpleDateFormat(timeFormat).format(first));
        s = s.replace("{kills}", String.valueOf(kills)).replace("{deaths}", String.valueOf(deaths)).replace("{losses}", String.valueOf(looses)).replace("{looses}", String.valueOf(looses)).replace("{wins}", String.valueOf(wins))
                .replace("{finalKills}", String.valueOf(finalKills)).replace("{fKills}", String.valueOf(finalKills)).replace("{finalDeaths}",
                        String.valueOf(finalDeaths)).replace("{bedsDestroyed}", String.valueOf(beds)).replace("{beds}", String.valueOf(beds))
                .replace("{gamesPlayed}", String.valueOf(games)).replace("{firstPlay}", firstS).replace("{lastPlay}", lastS).replace("{player}", player);
        return papiReplacements ? SupportPAPI.getSupportPAPI().replace(pl, s) : s;
    }

    public static void giveLobbySb(Player p) {
        if (config.getBoolean("lobbyScoreboard")) {
            new SBoard(p, getList(p, Messages.SCOREBOARD_LOBBY), null);
        }
    }

    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (Exception e) {
            try {
                Integer.parseInt(s);
            } catch (Exception ex) {
                try {
                    Long.parseLong(s);
                } catch (Exception exx) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if a location is outside the World Border
     *
     * @since API 8
     */
    public static boolean isOutsideOfBorder(Location l) {
        WorldBorder border = l.getWorld().getWorldBorder();
        double radius = border.getSize() / 2;
        Location location = l, center = border.getCenter();

        return center.distanceSquared(location) >= (radius * radius);
    }
}
