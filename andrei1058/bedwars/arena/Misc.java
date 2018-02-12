package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.configuration.Language;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Misc implements PluginMessageListener {

    private static boolean updateAvailable = false;
    private static String newVersion = "";

    public static void moveToLobbyOrKick(Player p) {
        if (lobbyServer) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(config.getYml().getString("lobbyServer"));
            p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        } else {
            p.kickPlayer(getMsg(p, Language.restartKick));
        }
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equalsIgnoreCase("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServers")) {
            String[] serverList = in.readUTF().split(", ");
            for (String server : serverList) {
                if (server.equalsIgnoreCase(config.getYml().getString("lobbyServer"))) {
                    lobbyServer = true;
                }
            }
        }
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    public static void checkLobbyServer() {
        if (Bukkit.getServer().spigot().getConfig().getBoolean("settings.bungeecord")) {
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
            plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", new Misc());
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("GetServers");
            Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        } else {
            if (getServerType() == ServerType.BUNGEE) {
                plugin.getLogger().severe("Please set bungeecord to true in spigot.yml");
            }
        }
    }

    public static ItemStack getArenaGUI(Player p) {
        ItemStack i;
        try {
            i = new ItemStack(Material.valueOf(config.getYml().getString("items.arenaGui.itemStack")),
                    1, (short) config.getYml().getInt("items.arenaGui.data"));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading items.arenaGui.itemStack or Data");
            i = new ItemStack(Material.BEDROCK);
        }
        ItemMeta im = i.getItemMeta();
        im.spigot().setUnbreakable(true);
        try {
            im.setLore(getList(p, Language.arenaGuiItemLore));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading arena gui's lore");
        }
        try {
            im.setDisplayName(getMsg(p, Language.arenaGuiItemName));
        } catch (Exception ex) {
            plugin.getLogger().severe("There was a problem when loading arena gui's name");
        }
        try {
            if (config.getYml().getBoolean("items.arenaGui.enchanted")) {
                im.addEnchant(Enchantment.LUCK, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
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

    public static void checkUpdate() {
        try {
            HttpURLConnection checkUpdate = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=50942").openConnection();
            checkUpdate.setDoOutput(true);
            String old = plugin.getDescription().getVersion();
            String newV = new BufferedReader(new InputStreamReader(checkUpdate.getInputStream())).readLine();
            if (!newV.equalsIgnoreCase(old)) {
                updateAvailable = true;
                newVersion = newV;
                plugin.getLogger().info("------------------------------------");
                plugin.getLogger().info(" ");
                plugin.getLogger().info("There is a nev version available!");
                plugin.getLogger().info("Version: " + newVersion);
                plugin.getLogger().info(" ");
                plugin.getLogger().info("https://www.spigotmc.org/resources/50942/");
                plugin.getLogger().info("------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        plugin.spawnNPCs();
    }

    public static BlockFace getDirection(Location loc){
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
}
