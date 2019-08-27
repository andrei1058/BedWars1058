package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.player.PlayerLevelUpEvent;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.util.HashMap;
import java.util.UUID;

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

    private static HashMap<UUID, PlayerLevel> levelByPlayer = new HashMap<>();


    /**
     * Cache a player level.
     */
    public PlayerLevel(UUID player, int level, int currentXp) {
        levelByPlayer.put(player, this);
        this.uuid = player;
        setLevelName(level);
        setNextLevelCost(level);

        //fix levels broken in the past by an issue
        if (level < 1) level = 1;
        if (currentXp < 0) currentXp = 0;

        this.level = level;
        this.currentXp = currentXp;
        updateProgressBar();
        //requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        //formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
    }

    public void setLevelName(int level) {
        this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name")).replace("{number}", String.valueOf(level));

    }

    public void setNextLevelCost(int level) {
        this.nextLevelCost = LevelsConfig.levels.getYml().get("levels." + level + ".rankup-cost") == null ?
                LevelsConfig.levels.getYml().getInt("levels.others.rankup-cost") : LevelsConfig.levels.getYml().getInt("levels." + level + ".rankup-cost");
    }

    public void lazyLoad(int level, int currentXp) {
        if (level < 1) level = 1;
        if (currentXp < 0) currentXp = 0;
        setLevelName(level);
        setNextLevelCost(level);
        this.level = level;
        this.currentXp = currentXp;
        updateProgressBar();

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            for (SBoard sb : SBoard.getScoreboards()) {
                if (sb.getP().getUniqueId().equals(uuid)) {
                    sb.refresh();
                    break;
                }
            }
        }, 5L);
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
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
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
    @Nullable
    public static PlayerLevel getLevelByPlayer(UUID player) {
        return levelByPlayer.getOrDefault(player, null);
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
        Bukkit.getPluginManager().callEvent(new PlayerXpGainEvent(uuid, xp, source));
    }

    /**
     * Set player xp.
     */
    public void setXp(int currentXp) {
        if (currentXp <= 0) currentXp = 0;
        this.currentXp = currentXp;
        upgradeLevel();
        updateProgressBar();
    }

    /**
     * Set player level.
     */
    public void setLevel(int level) {
        this.level = level;
        nextLevelCost = LevelsConfig.levels.getYml().get("levels." + level + ".rankup-cost") == null ?
                LevelsConfig.levels.getInt("levels.others.rankup-cost") : LevelsConfig.levels.getInt("levels." + level + ".rankup-cost");
        this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name")).replace("{number}", String.valueOf(level));
        requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
        updateProgressBar();
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
            level++;
            nextLevelCost = LevelsConfig.levels.getYml().get("levels." + level + ".rankup-cost") == null ?
                    LevelsConfig.levels.getInt("levels.others.rankup-cost") : LevelsConfig.levels.getInt("levels." + level + ".rankup-cost");
            currentXp = currentXp - nextLevelCost;
            this.levelName = ChatColor.translateAlternateColorCodes('&', LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                    LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name")).replace("{number}", String.valueOf(level));
            requiredXp = nextLevelCost >= 1000 ? nextLevelCost % 1000 == 0 ? nextLevelCost / 1000 + "k" : (double) nextLevelCost / 1000 + "k" : String.valueOf(nextLevelCost);
            formattedCurrentXp = currentXp >= 1000 ? currentXp % 1000 == 0 ? currentXp / 1000 + "k" : (double) currentXp / 1000 + "k" : String.valueOf(currentXp);
            Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(Bukkit.getPlayer(getUuid()), level, nextLevelCost));
        }
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
        Main.getRemoteDatabase().setLevelData(uuid, level, currentXp, LevelsConfig.levels.getYml().get("levels." + level + ".name") == null ?
                LevelsConfig.levels.getYml().getString("levels.others.name") : LevelsConfig.levels.getYml().getString("levels." + level + ".name"), nextLevelCost);
    }
}
