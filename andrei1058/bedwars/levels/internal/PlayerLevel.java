package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLevel {

    private UUID uuid;
    private int level;
    private int nextLevelCost;
    private String levelName;
    private int currentXp;
    private String progressBar;

    private static HashMap<UUID, PlayerLevel> levelByPlayer = new HashMap<>();


    /**
     * Cache a player level.
     */
    public PlayerLevel(UUID player, int level, int currentXp) {
        this.uuid = player;
        this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name")).replace("{number}", String.valueOf(level));
        this.nextLevelCost = LevelsConfig.levels.getYml().get("levels." + level + ".rankup-cost") == null ?
                LevelsConfig.levels.getYml().getInt("levels.others.rankup-cost") : LevelsConfig.levels.getYml().getInt("levels." + level + ".rankup-cost");
        this.level = level;
        this.currentXp = currentXp;
        updateProgressBar();
        levelByPlayer.put(player, this);
    }

    private void updateProgressBar() {
        int locked = (nextLevelCost - currentXp) % nextLevelCost;
        int unlocked = 10 - locked;
        progressBar = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getString("progress-bar.format").replace("{progress}",
                LevelsConfig.levels.getString("progress-bar.unlocked-color") + String.valueOf(new char[unlocked]).replace("\0", LevelsConfig.levels.getString("progress-bar.symbol")))
                + LevelsConfig.levels.getString("progress-bar.locked-color") + String.valueOf(new char[locked]).replace("\0", LevelsConfig.levels.getString("progress-bar.symbol")));
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevelCost() {
        return nextLevelCost;
    }

    public static PlayerLevel getLevelByPlayer(UUID player) {
        return levelByPlayer.getOrDefault(player, null);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public String getProgress() {
        return progressBar;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
        upgradeLevel();
        updateProgressBar();
    }

    /**
     * Used to upgrade player level.
     */
    public void upgradeLevel() {
        if (currentXp >= nextLevelCost) {
            level++;
            nextLevelCost = LevelsConfig.levels.getYml().get("levels." + level + "rankup-cost") == null ?
                    LevelsConfig.levels.getInt("levels.others.rankup-cost") : LevelsConfig.levels.getInt("levels." + level + ".rankup-cost");
            currentXp = currentXp - nextLevelCost;
            this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                    LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name")).replace("{number}", String.valueOf(level));
        }
    }

    /**
     * Destroy data.
     */
    public void destroy() {
        levelByPlayer.remove(uuid);
        Main.getRemoteDatabase().setLevelData(uuid, level, currentXp);
    }
}
