package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.exceptions.InvalidMaterialException;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.language.Language.getList;
import static com.andrei1058.bedwars.language.Language.getMsg;

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
            } else {
                forceKick(p);
            }
            return;
        }
        forceKick(p);
    }


    public static void forceKick(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(config.getYml().getString("lobbyServer"));
        p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        if (getServerType() == ServerType.BUNGEE) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (p.isOnline()) {
                    p.kickPlayer(getMsg(p, Messages.ARENA_RESTART_PLAYER_KICK));
                }
            }, 120L);
        }
    }

    /**
     * Win fireworks
     */
    public static void launchFirework(@NotNull Player p) {
        Color[] colors = {Color.WHITE, Color.AQUA, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.RED,
                Color.YELLOW, Color.BLACK, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE};
        Random r = new Random();
        Firework fw = p.getWorld().spawn(p.getEyeLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(1);
        meta.addEffect(FireworkEffect.builder()
                .withFade(colors[r.nextInt(colors.length - 1)])
                .withTrail().withColor(colors[r.nextInt(colors.length - 1)]).with(FireworkEffect.Type.BALL_LARGE).build());
        fw.setFireworkMeta(meta);
        fw.setVelocity(p.getEyeLocation().getDirection());
    }


    public static void launchFirework(@NotNull Location l) {
        Color[] colors = {Color.WHITE, Color.AQUA, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.RED,
                Color.YELLOW, Color.BLACK, Color.MAROON, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE};
        Random r = new Random();
        Firework fw = l.getWorld().spawn(l, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.setPower(1);
        meta.addEffect(FireworkEffect.builder()
                .withFade(colors[r.nextInt(colors.length - 1)])
                .withTrail().withColor(colors[r.nextInt(colors.length - 1)]).with(FireworkEffect.Type.BALL_LARGE).build());
        fw.setFireworkMeta(meta);
    }

    public static String replaceFirst(String text, String regex, String replacement) {
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

    /**
     * Create an item stack
     *
     * @param material item material
     * @param data     item data
     * @param name     item name
     * @param lore     item lore
     * @param owner    in case of skull, can be null, don't worry
     */
    public static ItemStack createItem(Material material, byte data, boolean enchanted, String name, List<String> lore, Player owner, String metaKey, String metaData) {
        ItemStack i = new ItemStack(material, 1, data);
        if (owner != null) {
            if (nms.isPlayerHead(material.toString(), data)) {
                SkullMeta sm = (SkullMeta) i.getItemMeta();
                sm.setOwner(owner.getName());
                i.setItemMeta(sm);
            }
        }
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        if (enchanted) {
            im.addEnchant(Enchantment.LUCK, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        i.setItemMeta(im);
        if (!(metaData.isEmpty() || metaKey.isEmpty())) {
            i = nms.addCustomData(i, metaKey + "_" + metaData);
        }
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
        if (!customData.isEmpty()) {
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
        return Material.EGG == i || nms.materialFireball() == i || nms.materialSnowball() == i || Material.ARROW == i;
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
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL.replace("%path%", path), itemstack.toString());
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_DATA.replace("%path%", path), data);
        yml.addDefault(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_SLOT.replace("%path%", path), slot);
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
        Inventory inv = Bukkit.createInventory(null, config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE), replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_INV_NAME),
                kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed, firstPlay, lastPlay, timeFormat, p.getName(), never, true));

        /** add custom items to gui */
        for (String s : config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_STATS_PATH).getKeys(false)) {
            /** skip inv size, it isn't a content */
            if (ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE.contains(s)) continue;
            /** create new itemStack for content */
            ItemStack i = nms.createItemStack(config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL.replace("%path%", s)).toUpperCase(), 1, (short) config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_DATA.replace("%path%", s)));
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_PATH + "-" + s + "-name"), kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed,
                    firstPlay, lastPlay, timeFormat, p.getName(), never, true));
            List<String> lore = new ArrayList<>();
            for (String string : getList(p, Messages.PLAYER_STATS_GUI_PATH + "-" + s + "-lore")) {
                lore.add(replaceStatsPlaceholders(p, string, kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed, firstPlay, lastPlay, timeFormat, p.getName(), never, true));
            }
            im.setLore(lore);
            i.setItemMeta(im);
            inv.setItem(config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_SLOT.replace("%path%", s)), i);
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
        if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                new SBoard(p, getList(p, Messages.SCOREBOARD_LOBBY), null);
            }, 15L);
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

    /**
     * Check if location is on a protected region
     */
    public static boolean isBuildProtected(Location l, Arena a) {
        if (!l.getWorld().getName().equals(a.getWorldName())) return false;
        for (BedWarsTeam t : a.getTeams()) {
            if (t.getSpawn().distance(l) <= a.getCm().getInt(ConfigPath.ARENA_SPAWN_PROTECTION)) {
                return true;
            }
            if (t.getShop().distance(l) <= a.getCm().getInt(ConfigPath.ARENA_SHOP_PROTECTION)) {
                return true;
            }
            if (t.getTeamUpgrades().distance(l) <= a.getCm().getInt(ConfigPath.ARENA_UPGRADES_PROTECTION)) {
                return true;
            }
        }
        for (OreGenerator o : OreGenerator.getGenerators()) {
            if (o.getLocation().distance(l) <= 1) {
                return true;
            }
        }
        return isOutsideOfBorder(l);
    }

    /**
     * Change the server MOTD in properties file
     */
    public static void updateMOTD(String motd) {
        Properties p = new Properties();
        OutputStream out = null;
        InputStream in = null;

        try {

            in = new FileInputStream(Main.plugin.getServer().getWorldContainer().getPath() + "/server.properties");
            p.load(in);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    try {
                        out = new FileOutputStream(Main.plugin.getServer().getWorldContainer().getPath() + "/server.properties");
                        p.setProperty("motd", motd);
                        p.store(out, null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
