package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IContentTier;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.levels.Level;
import com.andrei1058.bedwars.api.party.Party;
import com.andrei1058.bedwars.api.server.*;
import com.andrei1058.bedwars.api.command.ParentCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.UUID;

public interface BedWars {

    /**
     * Get stats utils.
     */
    IStats getStatsUtil();

    interface IStats {
        /**
         * Get player first play date.
         * You get data from the local cache.
         */
        Timestamp getPlayerFirstPlay(UUID p);

        /**
         * Get player last play date.
         * You get data from the local cache.
         */
        Timestamp getPlayerLastPlay(UUID p);

        /**
         * Get player total wins.
         * You get data from the local cache.
         */
        int getPlayerWins(UUID p);

        /**
         * Get player total kills.
         * You get data from the local cache.
         */
        int getPlayerKills(UUID p);

        /**
         * Get player total final kills.
         * You get data from the local cache.
         */
        int getPlayerFinalKills(UUID p);

        /**
         * Get player total looses.
         * You get data from the local cache.
         */
        int getPlayerLoses(UUID p);

        /**
         * Get player total deaths.
         * You get data from the local cache.
         */
        int getPlayerDeaths(UUID p);

        /**
         * Get player total final deaths.
         * You get data from the local cache.
         */
        int getPlayerFinalDeaths(UUID p);

        /**
         * Get player beds destroyed.
         * You get data from the local cache.
         */
        int getPlayerBedsDestroyed(UUID p);

        /**
         * Get player games played.
         * You get data from the local cache.
         */
        int getPlayerGamesPlayed(UUID p);
    }


    /**
     * Get afk system methods
     */
    AFKUtil getAFKUtil();

    interface AFKUtil {
        /**
         * Check if a player is AFK.
         */
        boolean isPlayerAFK(Player player);

        /**
         * Set a player afk.
         */
        void setPlayerAFK(Player player, boolean value);

        /**
         * Get the seconds since the player is AFK
         */
        int getPlayerTimeAFK(Player player);
    }

    ArenaUtil getArenaUtil();

    interface ArenaUtil {
        /**
         * Add a custom arena to the enable queue.
         */
        void addToEnableQueue(IArena a);

        /**
         * Remove an arena from the enable queue.
         */
        void removeFromEnableQueue(IArena a);

        /**
         * Check if a player is playing.
         */
        boolean isPlaying(Player p);

        /**
         * Check if a player is spectating.
         */
        boolean isSpectating(Player p);

        /**
         * Load an arena.
         * Add it to the enable queue.
         *
         * @param sender If you want to send feedback. Use null otherwise.
         */
        void loadArena(String worldName, Player sender);

        /**
         * Set how many games to the next serve restart.
         * This is used only if {@link com.andrei1058.bedwars.api.server.ServerType#BUNGEE}
         */
        void setGamesBeforeRestart(int games);

        /**
         * Get how many games till the next restart.
         * This is used only if {@link com.andrei1058.bedwars.api.server.ServerType#BUNGEE}
         */
        int getGamesBeforeRestart();

        /**
         * Get an arena by a player. Spectator or Player.
         *
         * @param player Target player
         * @return The arena where the player is in. Can be NULL.
         */
        IArena getArenaByPlayer(Player player);

        /**
         * Set an arena by player if the player is in this arena.
         */
        void setArenaByPlayer(Player p, IArena arena);

        /**
         * Remove
         */
        void removeArenaByPlayer(Player p, IArena a);

        /**
         * Get an arena by world name
         *
         * @param worldName World name
         */
        IArena getArenaByName(String worldName);

        void setArenaByName(IArena arena);

        /**
         * Remove
         */
        void removeArenaByName(String worldName);

        LinkedList<IArena> getArenas();

        /**
         * Check if a player has vip join.
         */
        boolean vipJoin(Player p);

        /**
         * Get players count for a group
         */
        int getPlayers(String group);

        /**
         * Add a player to the most filled arena.
         * Check if is the party owner first.
         *
         * @return true if joined.
         */
        boolean joinRandomArena(Player p);

        /**
         * Add a player to the most filled arena from a group.
         *
         * @return true if added.
         */
        boolean joinRandomFromGroup(Player p, String group);

        /**
         * Arena enable queue.
         */
        LinkedList<IArena> getEnableQueue();

        /**
         * This will give the lobby items to the player.
         * Not used in serverType BUNGEE.
         * This will clear the inventory first.
         */
        void sendLobbyCommandItems(Player p);
    }

    Configs getConfigs();

    interface Configs {
        /**
         * Get plugin main configuration.
         */
        ConfigManager getMainConfig();

        /**
         * Get signs configuration.
         */
        ConfigManager getSignsConfig();

        /**
         * Get generators configuration.
         */
        ConfigManager getGeneratorsConfig();

        /**
         * Get shop configuration.
         */
        ConfigManager getShopConfig();

        /**
         * Get upgrades configuration.
         */
        ConfigManager getUpgradesConfig();
    }

    /**
     * Get shop util.
     */
    ShopUtil getShopUtil();

    interface ShopUtil {

        /**
         * Get player's money amount
         */
        int calculateMoney(Player player, Material currency);

        /**
         * Get currency as material
         *
         * @return {@link Material#AIR} if is vault.
         */
        Material getCurrency(String currency);

        ChatColor getCurrencyColor(Material currency);

        /**
         * Cet currency path
         */
        String getCurrencyMsgPath(IContentTier contentTier);

        /**
         * Get roman number for given int.
         */
        String getRomanNumber(int n);

        /**
         * Take money from player on buy
         */
        void takeMoney(Player player, Material currency, int amount);
    }

    /**
     * Get levels methods.
     */
    Level getLevelsUtil();

    /**
     * Get party util.
     */
    Party getPartyUtil();

    /**
     * Get active setup session.
     *
     * @param player player uuid.
     * @return null if no session was found.
     */
    ISetupSession getSetupSession(UUID player);

    /**
     * Check if a player is in setup session.
     */
    boolean isInSetupSession(UUID player);

    /**
     * Get server type.
     **/
    ServerType getServerType();

    /**
     * Get a player language iso code
     */
    String getLangIso(Player p);


    /**
     * Get bedWars main command
     */
    ParentCommand getBedWarsCommand();

    /**
     * Get the restore adapter.
     */
    RestoreAdapter getRestoreAdapter();

    /**
     * Change the arena restore adapter.
     *
     * @param restoreAdapter your custom adapter.
     */
    void setRestoreAdapter(RestoreAdapter restoreAdapter) throws IllegalAccessError;

    /**
     * Get nms operations.
     */
    VersionSupport getVersionSupport();

    /**
     * Get server default language.
     */
    Language getDefaultLang();

    /**
     * Get lobby world name.
     */
    String getLobbyWorld();

    String getForCurrentVersion(String v18, String v12, String v13);

    void setLevelAdapter(Level level);
}
