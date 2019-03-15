package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.exceptions.InvalidMaterialException;
import com.andrei1058.bedwars.stats.StatsCache;
import com.andrei1058.bedwars.stats.StatsManager;
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
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
                i = nms.getPlayerHead(owner);
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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
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
        });
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

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {

            /** create inventory */
            Inventory inv = Bukkit.createInventory(null, config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE), replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_INV_NAME), true));

            /** add custom items to gui */
            for (String s : config.getYml().getConfigurationSection(ConfigPath.GENERAL_CONFIGURATION_STATS_PATH).getKeys(false)) {
                /** skip inv size, it isn't a content */
                if (ConfigPath.GENERAL_CONFIGURATION_STATS_GUI_SIZE.contains(s)) continue;
                /** create new itemStack for content */
                ItemStack i = nms.createItemStack(config.getYml().getString(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL.replace("%path%", s)).toUpperCase(), 1, (short) config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_DATA.replace("%path%", s)));
                ItemMeta im = i.getItemMeta();
                im.setDisplayName(replaceStatsPlaceholders(p, getMsg(p, Messages.PLAYER_STATS_GUI_PATH + "-" + s + "-name"), true));
                List<String> lore = new ArrayList<>();
                for (String string : getList(p, Messages.PLAYER_STATS_GUI_PATH + "-" + s + "-lore")) {
                    lore.add(replaceStatsPlaceholders(p, string, true));
                }
                im.setLore(lore);
                i.setItemMeta(im);
                inv.setItem(config.getInt(ConfigPath.GENERAL_CONFIGURATION_STATS_ITEMS_SLOT.replace("%path%", s)), i);
            }

            p.openInventory(inv);
        });
    }

    public static String replaceStatsPlaceholders(Player pl, String s, boolean papiReplacements) {

        if (s.contains("{kills}"))
            s = s.replace("{kills}", String.valueOf(StatsManager.getStatsCache().getKills(pl.getUniqueId())));
        if (s.contains("{deaths}"))
            s = s.replace("{deaths}", String.valueOf(StatsManager.getStatsCache().getDeaths(pl.getUniqueId())));
        if (s.contains("{losses}"))
            s = s.replace("{losses}", String.valueOf(StatsManager.getStatsCache().getLosses(pl.getUniqueId())));
        if (s.contains("{wins}"))
            s = s.replace("{wins}", String.valueOf(StatsManager.getStatsCache().getWins(pl.getUniqueId())));
        if (s.contains("{finalKills}"))
            s = s.replace("{finalKills}", String.valueOf(StatsManager.getStatsCache().getFinalKills(pl.getUniqueId())));
        if (s.contains("{finalDeaths}"))
            s = s.replace("{finalDeaths}", String.valueOf(StatsManager.getStatsCache().getFinalDeaths(pl.getUniqueId())));
        if (s.contains("{bedsDestroyed}"))
            s = s.replace("{bedsDestroyed}", String.valueOf(StatsManager.getStatsCache().getBedsDestroyed(pl.getUniqueId())));
        if (s.contains("{gamesPlayed}"))
            s = s.replace("{gamesPlayed}", String.valueOf(StatsManager.getStatsCache().getGamesPlayed(pl.getUniqueId())));
        if (s.contains("{firstPlay}"))
            s = s.replace("{firstPlay}", new SimpleDateFormat(getMsg(pl, Messages.FORMATTING_STATS_DATE_FORMAT)).format(StatsManager.getStatsCache().getFirstPlay(pl.getUniqueId())));
        if (s.contains("{lastPlay}"))
            s = s.replace("{lastPlay}", new SimpleDateFormat(getMsg(pl, Messages.FORMATTING_STATS_DATE_FORMAT)).format(StatsManager.getStatsCache().getLastPlay(pl.getUniqueId())));
        if (s.contains("{player}")) s = s.replace("{player}", pl.getName());
        if (s.contains("{prefix}")) s = s.replace("{prefix}", Main.getChatSupport().getPrefix(pl));

        return papiReplacements ? SupportPAPI.getSupportPAPI().replace(pl, s) : s;
    }


    public static void giveLobbySb(Player p) {
        if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> new SBoard(p, getList(p, Messages.SCOREBOARD_LOBBY), null), 15L);
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
        for (OreGenerator o : a.getOreGenerators()) {
            if (o.getLocation().distance(l) <= 1) {
                return true;
            }
        }
        return isOutsideOfBorder(l);
    }

    /**
     * Get lower location between 2 locations.
     *
     * @return a new Location instance.
     */
    public static Location minLoc(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) throw new IllegalStateException("Locations are not in the same world!");
        double x = Math.min(loc1.getX(), loc2.getX());
        double y = Math.min(loc1.getY(), loc2.getY());
        double z = Math.min(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), x, y, z);
    }

    /**
     * Get higher location between 2 locations.
     *
     * @return a new Location instance.
     */
    public static Location maxLoc(Location loc1, Location loc2) {
        if (loc1.getWorld() != loc2.getWorld()) throw new IllegalStateException("Locations are not in the same world!");
        double x = Math.max(loc1.getX(), loc2.getX());
        double y = Math.max(loc1.getY(), loc2.getY());
        double z = Math.max(loc1.getZ(), loc2.getZ());
        return new Location(loc1.getWorld(), x, y, z);
    }
}
