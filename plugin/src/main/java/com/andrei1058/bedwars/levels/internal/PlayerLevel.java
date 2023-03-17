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

package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerLevelUpEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("WeakerAccess")
public class PlayerLevel {

    private UUID uuid;
    private int level;
    private int nextLevelCost;
    private String levelName;
    private int currentXp;
    private String progressBar;
    private String requiredXp;
    private String formattedCurrentXp;

    // keep trace if current level is different than the one in database
    private boolean modified = false;

    private static ConcurrentHashMap<UUID, PlayerLevel> levelByPlayer = new ConcurrentHashMap<>();


    /**
     * Cache a player level.
     */
    public PlayerLevel(UUID player, int level, int currentXp) {
        this.uuid = player;
        setLevelName(level);
        setNextLevelCost(level, true);

        //fix levels broken in the past by an issue
        if (level < 1) level = 1;
        if (currentXp < 0) currentXp = 0;

        this.level = level;
        this.currentXp = currentXp;
        updateProgressBar();
        //requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        //formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
        if (!levelByPlayer.containsKey(player)) levelByPlayer.put(player, this);
    }

    public void setLevelName(int level) {
        this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.getLevelName(level)).replace("{number}", String.valueOf(level));

    }

    public void setNextLevelCost(int level, boolean initialize) {
        if (!initialize) modified = true;
        this.nextLevelCost = LevelsConfig.getNextCost(level);
    }

    public void lazyLoad(int level, int currentXp) {
        modified = false;
        if (level < 1) level = 1;
        if (currentXp < 0) currentXp = 0;
        setLevelName(level);
        setNextLevelCost(level, true);
        this.level = level;
        this.currentXp = currentXp;
        updateProgressBar();

        modified = false;
    }

    /**
     * Update the player progress bar.
     */
    private void updateProgressBar() {
        double l1 = ((nextLevelCost - currentXp) / (double) (nextLevelCost)) * 10;
        int locked = (int) l1;
        int unlocked = 10 - locked;
        if (locked < 0 || unlocked < 0) {
            locked = 10;
            unlocked = 0;
        }
        progressBar = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getString("progress-bar.format").replace("{progress}",
                LevelsConfig.levels.getString("progress-bar.unlocked-color") + String.valueOf(new char[unlocked]).replace("\0", LevelsConfig.levels.getString("progress-bar.symbol"))
                        + LevelsConfig.levels.getString("progress-bar.locked-color") + String.valueOf(new char[locked]).replace("\0", LevelsConfig.levels.getString("progress-bar.symbol"))));
        requiredXp = formatNumber(nextLevelCost);
        formattedCurrentXp = formatNumber(currentXp);
    }

    /**
     * Get player current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the amount of xp required to level up.
     */
    public int getNextLevelCost() {
        return nextLevelCost;
    }

    /**
     * Get PlayerLevel by player.
     */
    public static PlayerLevel getLevelByPlayer(UUID player) {
        return levelByPlayer.getOrDefault(player, new PlayerLevel(player, 1, 0));
    }

    /**
     * Get player uuid.
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get player current level display name.
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * Get player xp.
     */
    public int getCurrentXp() {
        return currentXp;
    }

    /**
     * Get progress bar for player.
     */
    public String getProgress() {
        return progressBar;
    }

    /**
     * Get target xp already formatted.
     * Like: 2000 is 2k
     */
    public String getFormattedRequiredXp() {
        return requiredXp;
    }

    /**
     * Add xp to player with source.
     */
    public void addXp(int xp, PlayerXpGainEvent.XpSource source) {
        if (xp < 0) return;
        this.currentXp += xp;
        upgradeLevel();
        updateProgressBar();
        Bukkit.getPluginManager().callEvent(new PlayerXpGainEvent(Bukkit.getPlayer(uuid), xp, source));
        modified = true;
    }

    /**
     * Set player xp.
     */
    public void setXp(int currentXp) {
        if (currentXp <= 0) currentXp = 0;
        this.currentXp = currentXp;
        upgradeLevel();
        updateProgressBar();
        modified = true;
    }

    /**
     * Set player level.
     */
    public void setLevel(int level) {
        this.level = level;
        nextLevelCost = LevelsConfig.getNextCost(level);
        this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.getLevelName(level)).replace("{number}", String.valueOf(level));
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        updateProgressBar();
        modified = true;
    }

    /**
     * Get player xp already formatted.
     * Like: 1000 is 1k
     */
    public String getFormattedCurrentXp() {
        return formattedCurrentXp;
    }

    /**
     * Used to upgrade player level.
     */
    public void upgradeLevel() {
        if (currentXp >= nextLevelCost) {
            currentXp = currentXp - nextLevelCost;
            level++;
            nextLevelCost = LevelsConfig.getNextCost(level);
            this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.getLevelName(level)).replace("{number}", String.valueOf(level));
            requiredXp = formatNumber(nextLevelCost);
            formattedCurrentXp = formatNumber(currentXp);
            Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(Bukkit.getPlayer(getUuid()), level, nextLevelCost));
            modified = true;
        }
    }

    private String formatNumber(int score) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);

        if (score >= 1000) {
            return format.format(score/1000.0)+"k";
        }
        return format.format(score);
    }

    /**
     * Get player level as int.
     */
    public int getPlayerLevel() {
        return level;
    }

    /**
     * Destroy data.
     */
    public void destroy() {
        levelByPlayer.remove(uuid);
        //BedWars.getRemoteDatabase().setLevelData(uuid, level, currentXp, LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
        //        LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name"), nextLevelCost);
        updateDatabase();
    }

    public void updateDatabase() {
        if (modified) {
            Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> BedWars.getRemoteDatabase().setLevelData(uuid, level, currentXp, LevelsConfig.getLevelName(level), nextLevelCost));
            modified = false;
        }
    }
}
