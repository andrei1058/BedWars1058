package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.configuration.ConfigManager;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.plugin;

public class SetupSession {

    private static List<SetupSession> setupSessions = new ArrayList<>();
    private static String invName = "§8Choose a setup method";
    private static int assistedSlot = 3, advancedSlot = 5;

    private Player player;
    private String worldName;
    private SetupType setupType;
    private ConfigManager cm;
    public boolean started = false;
    public boolean autoCreatedEmerald = false;
    public boolean autoCreatedDiamond = false;
    public List<Location> skipAutoCreateGen = new ArrayList<>();

    public SetupSession(Player player, String worldName) {
        this.player = player;
        this.worldName = worldName;
        getSetupSessions().add(this);
        openGUI(player);
    }

    public void setSetupType(SetupType setupType) {
        this.setupType = setupType;
    }

    public static List<SetupSession> getSetupSessions() {
        return setupSessions;
    }

    /**
     * Gets the setup type gui inv name
     *
     * @since api 6
     */
    public static String getInvName() {
        return invName;
    }

    /**
     * Get advanced type item slot
     *
     * @since api 6
     */
    public static int getAdvancedSlot() {
        return advancedSlot;
    }

    /**
     * Get assisted type item slot
     *
     * @since api 6
     */
    public static int getAssistedSlot() {
        return assistedSlot;
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

    public boolean isStarted() {
        return started;
    }

    public enum SetupType {ASSISTED, ADVANCED}

    /**
     * Start setup session, loadStructure world etc
     *
     * @return true if started successfully
     */
    public boolean startSetup() {
        getPlayer().sendMessage("§6 ▪ §7Loading " + getWorldName());
        World w = null;
        try {
            w = Bukkit.createWorld(new WorldCreator(getWorldName()));
        } catch (Exception ex) {
            File uid = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + getWorldName() + "/uid.dat");
            uid.delete();
            try {
                w = Bukkit.createWorld(new WorldCreator(getWorldName()));
            } catch (Exception exx) {
            }
        }
        if (w == null) {
            getPlayer().sendMessage("§c▪ §7There was an error while loading the map :(\n§c▪ §7Please delete uid.dat from " + getWorldName() + "'s folder.");
            return false;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.getWorld(getWorldName()).getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove);
        }, 30L);
        w.setAutoSave(true);
        w.setGameRuleValue("doMobSpawning", "false");
        getPlayer().teleport(w.getSpawnLocation());
        getPlayer().setGameMode(GameMode.CREATIVE);
        getPlayer().setAllowFlight(true);
        getPlayer().setFlying(true);
        getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        getPlayer().sendMessage("§6 ▪ §7You were teleported to the " + getWorldName() + "'s spawn.");
        started = true;
        getPlayer().getInventory().clear();
        cm = new ConfigManager(getWorldName(), "plugins/" + plugin.getName() + "/Arenas", true);
        if (getSetupType() == SetupType.ASSISTED && getCm().getYml().get("waiting.Loc") == null) {
            getPlayer().sendMessage("");
            getPlayer().sendMessage("§6 ▪ §c" + getWorldName() + " Setup");
            getPlayer().sendMessage("");
            getPlayer().sendMessage("§eHello " + getPlayer().getName() + "!");
            getPlayer().sendMessage("§fPlease set the waiting spawn.");
            getPlayer().sendMessage("§fIt is the place where players will wait the game to start.");
            getPlayer().spigot().sendMessage(Misc.msgHoverClick("§9     ▪     §6CLICK HERE TO SET THE WAITING LOBBY    §9 ▪", "§dClick to set the waiting spawn.", "/" + Main.mainCmd + " setWaitingSpawn", ClickEvent.Action.RUN_COMMAND));
            getPlayer().sendMessage("§eOr type: §7/" + Main.mainCmd + " setWaitingSpawn");
        } else {
            Bukkit.dispatchCommand(getPlayer(), Main.mainCmd + " cmds");
        }
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
     *
     * @since api 6
     */
    public void cancel() {
        if (!isStarted()) {
            getSetupSessions().remove(this);
            player.sendMessage("§6 ▪ §7" + getWorldName() + " setup cancelled!");
        }
    }

    /**
     * End setup session
     *
     * @since api 6
     */
    public void done() {
        getSetupSessions().remove(this);
        if (Main.getServerType() != ServerType.BUNGEE) getPlayer().teleport(config.getConfigLoc("lobbyLoc"));
        getPlayer().removePotionEffect(PotionEffectType.SPEED);
        if (Main.getServerType() == ServerType.MULTIARENA) Arena.sendLobbyCommandItems(getPlayer());
    }

    /**
     * Check if a player is in setup session
     *
     * @since api 6
     */
    public static boolean isInSetupSession(Player player) {
        for (SetupSession ss : getSetupSessions()) {
            if (ss.getPlayer() == player) return true;
        }
        return false;
    }

    /**
     * Get a player session
     *
     * @since api 6
     */
    public static SetupSession getSession(Player p) {
        for (SetupSession ss : getSetupSessions()) {
            if (ss.getPlayer() == p) return ss;
        }
        return null;
    }

    /**
     * Send setup commands
     *
     * @since api 6
     */
    public void sendCommands() {
    }

    /**
     * Get arena configuration
     *
     * @since api 6
     */
    public ConfigManager getCm() {
        return cm;
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
