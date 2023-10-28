/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.server.SetupSessionCloseEvent;
import com.andrei1058.bedwars.api.events.server.SetupSessionStartEvent;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.ArenaConfig;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.plugin;
import static com.andrei1058.bedwars.commands.Misc.createArmorStand;

public class SetupSession implements ISetupSession {

    private static List<SetupSession> setupSessions = new ArrayList<>();

    private Player player;
    private String worldName;
    private SetupType setupType;
    private ArenaConfig cm;
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
     * @return return is broken. do not use it.
     */
    public boolean startSetup() {
        getPlayer().sendMessage("§6 ▪ §7Loading " + getWorldName());
        cm = new ArenaConfig(BedWars.plugin, getWorldName(), plugin.getDataFolder().getPath() + "/Arenas");
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
        getSetupSessions().remove(this);
        if (isStarted()) {
            player.sendMessage("§6 ▪ §7" + getWorldName() + " setup cancelled!");
            done();
        }
    }

    /**
     * End setup session
     */
    public void done() {
        BedWars.getAPI().getRestoreAdapter().onSetupSessionClose(this);
        getSetupSessions().remove(this);
        if (BedWars.getServerType() != ServerType.BUNGEE) {
            try {
                TeleportManager.teleportC(getPlayer(), config.getConfigLoc("lobbyLoc"), PlayerTeleportEvent.TeleportCause.PLUGIN);
            } catch (Exception ex) {
                TeleportManager.teleportC(getPlayer(), Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
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
    public ArenaConfig getConfig() {
        return cm;
    }

    @Override
    public void teleportPlayer() {
        player.getInventory().clear();
        TeleportManager.teleport(player, Bukkit.getWorld(getWorldName()).getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
        Bukkit.getScheduler().runTaskLater(plugin, ()->{
            player.setAllowFlight(true);
            player.setFlying(true);
        }, 5L);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        player.sendMessage("\n" + ChatColor.WHITE + "\n");

        for (int x = 0; x < 10; x++) {
            getPlayer().sendMessage(" ");
        }
        player.sendMessage(ChatColor.GREEN + "You were teleported to the " + ChatColor.GOLD + getWorldName() + ChatColor.GREEN + "'s spawn.");
        if (getSetupType() == SetupType.ASSISTED && getConfig().getYml().get("waiting.Loc") == null) {
            player.sendMessage("");
            player.sendMessage(ChatColor.GREEN + "Hello " + player.getDisplayName() + "!");
            player.sendMessage(ChatColor.WHITE + "Please set the waiting spawn.");
            player.sendMessage(ChatColor.WHITE + "It is the place where players will wait the game to start.");
            player.spigot().sendMessage(Misc.msgHoverClick(ChatColor.BLUE + "     ▪     " + ChatColor.GOLD + "CLICK HERE TO SET THE WAITING LOBBY    " + ChatColor.BLUE + " ▪", ChatColor.LIGHT_PURPLE + "Click to set the waiting spawn.", "/" + BedWars.mainCmd + " setWaitingSpawn", ClickEvent.Action.RUN_COMMAND));
            player.spigot().sendMessage(MainCommand.createTC(ChatColor.YELLOW + "Or type: " + ChatColor.GRAY + "/" + BedWars.mainCmd + " to see the command list.", "/" + BedWars.mainCmd + "", ChatColor.WHITE + "Show commands list."));
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
        setStarted(true);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (String team : getTeams()) {
                for (String gen : new String[]{"Iron", "Gold", "Emerald"}) {
                    if (getConfig().getYml().get("Team." + team + "." + gen) != null) {
                        for (String loc : getConfig().getList("Team." + team + ".Iron")) {
                            createArmorStand(ChatColor.GOLD + gen + " generator added for team: " + getTeamColor(team) + team, getConfig().convertStringToArenaLocation(loc), loc);
                        }
                    }
                    if (getConfig().getYml().get("Team." + team + ".Spawn") != null) {
                        createArmorStand(getTeamColor(team) + team + " " + ChatColor.GOLD + "SPAWN SET", getConfig().getArenaLoc("Team." + team + ".Spawn"), getConfig().getString("Team." + team + ".Spawn"));
                    }
                    if (getConfig().getYml().get("Team." + team + ".Bed") != null) {
                        createArmorStand(getTeamColor(team) + team + " " + ChatColor.GOLD + "BED SET", getConfig().getArenaLoc("Team." + team + ".Bed"), getConfig().getString("Team." + team + ".Bed"));
                    }
                }
                if (getConfig().getYml().get("Team." + team + ".Shop") != null) {
                    createArmorStand(getTeamColor(team) + team + " " + ChatColor.GOLD + "SHOP SET", getConfig().getArenaLoc("Team." + team + ".Shop"), null);
                }
                if (getConfig().getYml().get("Team." + team + ".Upgrade") != null) {
                    createArmorStand(getTeamColor(team) + team + " " + ChatColor.GOLD + "UPGRADE SET", getConfig().getArenaLoc("Team." + team + ".Upgrade"), null);
                }
                if (getConfig().getYml().get("Team." + team + "." + ConfigPath.ARENA_TEAM_KILL_DROPS_LOC) != null) {
                    createArmorStand(ChatColor.GOLD + "Kill drops " + team, getConfig().getArenaLoc("Team." + team + "." + ConfigPath.ARENA_TEAM_KILL_DROPS_LOC), null);
                }
            }

            for (String type : new String[]{"Emerald", "Diamond"}) {
                if (getConfig().getYml().get("generator." + type) != null) {
                    for (String loc : getConfig().getList("generator." + type)) {
                        createArmorStand(ChatColor.GOLD + type + " SET", getConfig().convertStringToArenaLocation(loc), loc);
                    }
                }
            }
        }, 90L);
    }

    @Override
    public void close() {
        cancel();
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

    public String getPrefix() {
        return ChatColor.GREEN + "[" + getWorldName() + ChatColor.GREEN + "] " + ChatColor.GOLD;
    }

    /**
     * Get a team color.
     *
     * @param team team name.
     * @return team color.
     */
    public ChatColor getTeamColor(String team) {
        return TeamColor.getChatColor(getConfig().getString("Team." + team + ".Color"));
    }

    /**
     * Show available teams.
     */
    public void displayAvailableTeams() {
        if (getConfig().getYml().get("Team") != null) {
            getPlayer().sendMessage(getPrefix() + "Available teams: ");
            for (String team : Objects.requireNonNull(getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                getPlayer().sendMessage(getPrefix() + TeamColor.getChatColor(Objects.requireNonNull(getConfig().getYml().getString("Team." + team + ".Color"))) + team);
            }
        }
    }

    /**
     * Get nearest team name.
     *
     * @return empty if not found.
     */
    public String getNearestTeam() {
        String foundTeam = "";
        ConfigurationSection cs = getConfig().getYml().getConfigurationSection("Team");
        if (cs == null) return foundTeam;
        double distance = 100;
        for (String team : cs.getKeys(false)) {
            if (getConfig().getYml().get("Team." + team + ".Spawn") == null) continue;
            double dis = getConfig().getArenaLoc("Team." + team + ".Spawn").distance(getPlayer().getLocation());
            if (dis <= getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS)) {
                if (dis < distance) {
                    distance = dis;
                    foundTeam = team;
                }
            }
        }
        return foundTeam;
    }

    public String dot() {
        return ChatColor.BLUE + " " + '▪' + " " + ChatColor.GRAY + "/" + BedWars.mainCmd + " ";
    }

    public List<String> getTeams() {
        if (getConfig().getYml().get("Team") == null) return new ArrayList<>();
        return new ArrayList<>(getConfig().getYml().getConfigurationSection("Team").getKeys(false));
    }
}
