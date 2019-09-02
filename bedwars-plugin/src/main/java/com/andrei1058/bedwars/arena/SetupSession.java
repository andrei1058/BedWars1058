package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.server.SetupSessionCloseEvent;
import com.andrei1058.bedwars.api.events.server.SetupSessionStartEvent;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.*;

public class SetupSession implements ISetupSession {

    private static List<SetupSession> setupSessions = new ArrayList<>();

    private Player player;
    private String worldName;
    private SetupType setupType;
    private ConfigManager cm;
    private boolean started = false;
    private boolean autoCreatedEmerald = false;
    private boolean autoCreatedDiamond = false;
    private List<Location> skipAutoCreateGen = new ArrayList<>();

    public SetupSession(Player player, String worldName) {
        this.player = player;
        this.worldName = worldName;
        getSetupSessions().add(this);
        openGUI(player);
    }

    public void setSetupType(SetupType setupType) {
        this.setupType = setupType;
    }

    @SuppressWarnings("WeakerAccess")
    public static List<SetupSession> getSetupSessions() {
        return setupSessions;
    }

    /**
     * Gets the setup type gui inv name
     */
    public static String getInvName() {
        return "§8Choose a setup method";
    }

    /**
     * Get advanced type item slot
     */
    public static int getAdvancedSlot() {
        return 5;
    }

    /**
     * Get assisted type item slot
     */
    public static int getAssistedSlot() {
        return 3;
    }

    public SetupType getSetupType() {
        return setupType;
    }

    public Player getPlayer() {
        return player;
    }

    public String getWorldName() {
        return worldName;
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isStarted() {
        return started;
    }

    /**
     * Start setup session, loadStructure world etc
     *
     * @return true if started successfully
     */
    public boolean startSetup() {
        getPlayer().sendMessage("§6 ▪ §7Loading " + getWorldName());
        cm = new ConfigManager(BedWars.plugin, getWorldName(), "plugins/" + plugin.getName() + "/Arenas");
        BedWars.getAPI().getRestoreAdapter().onSetupSessionStart(this);
        return true;
    }

    private static void openGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, getInvName());
        ItemStack assisted = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta am = assisted.getItemMeta();
        am.setDisplayName("§e§lASSISTED SETUP");
        am.setLore(Arrays.asList("", "§aEasy and quick setup!", "§7For beginners and lazy staff :D", "", "§3Reduced options."));
        assisted.setItemMeta(am);
        inv.setItem(getAssistedSlot(), assisted);

        ItemStack advanced = new ItemStack(Material.REDSTONE);
        ItemMeta amm = advanced.getItemMeta();
        amm.setDisplayName("§c§lADVANCED SETUP");
        amm.setLore(Arrays.asList("", "§aDetailed setup!", "§7For experienced staff :D", "", "§3Advanced options."));
        advanced.setItemMeta(amm);
        inv.setItem(getAdvancedSlot(), advanced);

        player.openInventory(inv);
    }

    /**
     * Cancel setup
     */
    public void cancel() {
        if (!isStarted()) {
            getSetupSessions().remove(this);
            player.sendMessage("§6 ▪ §7" + getWorldName() + " setup cancelled!");
        }
    }

    /**
     * End setup session
     */
    public void done() {
        BedWars.getAPI().getRestoreAdapter().onSetupSessionClose(this);
        getSetupSessions().remove(this);
        if (BedWars.getServerType() != ServerType.BUNGEE) getPlayer().teleport(config.getConfigLoc("lobbyLoc"));
        getPlayer().removePotionEffect(PotionEffectType.SPEED);
        if (BedWars.getServerType() == ServerType.MULTIARENA) Arena.sendLobbyCommandItems(getPlayer());
        Bukkit.getPluginManager().callEvent(new SetupSessionCloseEvent(this));
    }

    /**
     * Check if a player is in setup session
     */
    public static boolean isInSetupSession(UUID player) {
        for (SetupSession ss : getSetupSessions()) {
            if (ss.getPlayer().getUniqueId().equals(player)) return true;
        }
        return false;
    }

    /**
     * Get a player session
     */
    public static SetupSession getSession(UUID p) {
        for (SetupSession ss : getSetupSessions()) {
            if (ss.getPlayer().getUniqueId().equals(p)) return ss;
        }
        return null;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Get arena configuration
     */
    public ConfigManager getConfig() {
        return cm;
    }

    @Override
    public void teleportPlayer() {
        player.getInventory().clear();
        player.teleport(Bukkit.getWorld(getWorldName()).getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        player.sendMessage("\n" + ChatColor.WHITE + "\n");

        player.sendMessage(ChatColor.GREEN + "You were teleported to the " + ChatColor.BLUE + getWorldName() + ChatColor.GREEN + "'s spawn.");
        if (getSetupType() == SetupType.ASSISTED && getConfig().getYml().get("waiting.Loc") == null) {
            player.sendMessage("");
            player.sendMessage(ChatColor.BLUE + ">>>>>>>>>>>>" + getWorldName() + " Setup Session");
            player.sendMessage("");
            player.sendMessage(ChatColor.GREEN + "Hello " + player.getName() + "!");
            player.sendMessage(ChatColor.WHITE + "Please set the waiting spawn.");
            player.sendMessage(ChatColor.WHITE + "It is the place where players will wait the game to start.");
            player.spigot().sendMessage(Misc.msgHoverClick(ChatColor.BLUE + "     ▪     " + ChatColor.GOLD + "CLICK HERE TO SET THE WAITING LOBBY    " + ChatColor.BLUE + " ▪", ChatColor.LIGHT_PURPLE + "Click to set the waiting spawn.", "/" + BedWars.mainCmd + " setWaitingSpawn", ClickEvent.Action.RUN_COMMAND));
            MainCommand.createTC(ChatColor.YELLOW + "Or type: " + ChatColor.GRAY + "/" + BedWars.mainCmd + " setWaitingSpawn", "/" + BedWars.mainCmd + " setWaitingSpawn", ChatColor.WHITE + "Set the world spawn lobby.");
        } else {
            Bukkit.dispatchCommand(player, BedWars.mainCmd + " cmds");
        }

        World w = Bukkit.getWorld(getWorldName());
        Bukkit.getScheduler().runTaskLater(plugin, () -> w.getEntities().stream()
                .filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING)
                .filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove), 30L);
        w.setAutoSave(false);
        w.setGameRuleValue("doMobSpawning", "false");
        Bukkit.getPluginManager().callEvent(new SetupSessionStartEvent(this));
    }

    public List<Location> getSkipAutoCreateGen() {
        return new ArrayList<>(skipAutoCreateGen);
    }

    public void addSkipAutoCreateGen(Location location) {
        skipAutoCreateGen.add(location);
    }

    public void setAutoCreatedEmerald(boolean autoCreatedEmerald) {
        this.autoCreatedEmerald = autoCreatedEmerald;
    }

    public boolean isAutoCreatedEmerald() {
        return autoCreatedEmerald;
    }

    public void setAutoCreatedDiamond(boolean autoCreatedDiamond) {
        this.autoCreatedDiamond = autoCreatedDiamond;
    }

    public boolean isAutoCreatedDiamond() {
        return autoCreatedDiamond;
    }
}
