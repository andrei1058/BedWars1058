package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.sidebar.IScoreboardService;
import com.andrei1058.bedwars.api.sidebar.ISidebar;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabConstants;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.placeholder.PlaceholderManager;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;
import static com.andrei1058.bedwars.api.language.Language.getScoreboard;

public class BoardManager implements IScoreboardService {

    private final HashMap<String, Scoreboard> sidebars = new HashMap<>();

    private static ScoreboardManager scoreboardManager;
    private static BoardManager instance;


    public static boolean init(){
        if (!TabAPI.getInstance().getFeatureManager().isFeatureEnabled(TabConstants.Feature.SCOREBOARD)) return false;
        if (null == instance) {
            instance = new BoardManager();
            instance.registerPlaceholders();
        }
        return instance != null;
    }

    private SimpleDateFormat getDateFormat(Player player){
        return new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_DATE));
    }

    private SimpleDateFormat getNextEventDateFormat(Player player){
        SimpleDateFormat nextEventDateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        nextEventDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return nextEventDateFormat;
    }

    private BoardManager() {
        scoreboardManager = TabAPI.getInstance().getScoreboardManager();
    }

    private void registerPlaceholders(){
        BedWars.debug("Registering TAB placeholders...");
        PlaceholderManager pm = TabAPI.getInstance().getPlaceholderManager();
        pm.registerPlayerPlaceholder("%bw_money%", 100, player -> getEconomy().getMoney((Player) player.getPlayer()));
        pm.registerServerPlaceholder("%bw_server_ip%", 100, () -> BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP));
        pm.registerServerPlaceholder("%bw_version%", 100, () -> plugin.getDescription().getVersion());
        pm.registerServerPlaceholder("%bw_server_id%", 100, () -> config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
        pm.registerPlayerPlaceholder("%bw_date%", 100, player -> getDateFormat((Player) player.getPlayer()).format(new Date(System.currentTimeMillis())));
        pm.registerPlayerPlaceholder("%bw_progress%", 100, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getProgress());
        pm.registerPlayerPlaceholder("%bw_level%", 100, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getLevelName());
        pm.registerPlayerPlaceholder("%bw_level_unformatted%", 100, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getLevel());
        pm.registerPlayerPlaceholder("%bw_current_xp%", 100, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getFormattedCurrentXp());
        pm.registerPlayerPlaceholder("%bw_required_xp%", 100, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getFormattedRequiredXp());
        pm.registerPlayerPlaceholder("%bw_map%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayName());
        pm.registerPlayerPlaceholder("%bw_map_name%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()).getArenaName());
        pm.registerPlayerPlaceholder("%bw_group%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayGroup((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_kills%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getKills());
        pm.registerPlayerPlaceholder("%bw_final_kills%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getFinalKills());
        pm.registerPlayerPlaceholder("%bw_beds%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getBedsDestroyed());
        pm.registerPlayerPlaceholder("%bw_deaths%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getDeaths());
        pm.registerPlayerPlaceholder("%bw_final_deaths%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getFinalDeaths());
        pm.registerPlayerPlaceholder("%bw_wins%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getWins());
        pm.registerPlayerPlaceholder("%bw_losses%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getLosses());
        pm.registerPlayerPlaceholder("%bw_gamesPlayed%", 100, player -> BedWars.getStatsManager().get(player.getUniqueId()).getGamesPlayed());
        pm.registerPlayerPlaceholder("%bw_next_event%", 50, player -> getNextEventName((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_on%", 50, player -> getOnlinePlayers((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_max%", 1000, player -> Arena.getArenaByPlayer((Player) player.getPlayer()).getMaxPlayers());
        pm.registerPlayerPlaceholder("%bw_time%", 50, player -> getTime((Player) player.getPlayer()));
//        pm.registerPlayerPlaceholder("%bw_%", 100, player -> );
    }


    public static BoardManager getInstance() {
        return instance;
    }

    @Override
    public void giveSidebar(@NotNull Player player, @Nullable IArena arena, boolean delay) {

        // set sidebar lines based on game state or lobby
        List<String> lines = null;
        String title;
        if (null == arena) {
            if (BedWars.getServerType() != ServerType.SHARED) {
                lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
            }
        } else {
            if (arena.getStatus() == GameState.waiting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
            } else if (arena.getStatus() == GameState.starting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING);
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
            }
        }

        title = lines.get(0);
        if (lines.size() == 1) {
            lines = new ArrayList<>();
        }
        lines = lines.subList(1, lines.size());
        lines.replaceAll(s -> s.isEmpty() ? " " : s); // TAB doesn't display empty lines, we need to replace them with spaces

        Board board = new Board(scoreboardManager, player, arena);

        Scoreboard scoreboard = sidebars.getOrDefault(player.getUniqueId(), board.getScoreboard(title, lines));
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());

        TabAPI.getInstance().getScoreboardManager().showScoreboard(tabPlayer, scoreboard);
    }

    @Override
    public void remove(@NotNull Player player) {

    }

    @Override
    public void refreshTitles() {

    }
    @NotNull
    private String getNextEventName(Player player) {
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return "-";
        String st = "-";
        switch (arena.getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
                st = getMsg(player, Messages.NEXT_EVENT_EMERALD_UPGRADE_II);
                break;
            case EMERALD_GENERATOR_TIER_III:
                st = getMsg(player, Messages.NEXT_EVENT_EMERALD_UPGRADE_III);
                break;
            case DIAMOND_GENERATOR_TIER_II:
                st = getMsg(player, Messages.NEXT_EVENT_DIAMOND_UPGRADE_II);
                break;
            case DIAMOND_GENERATOR_TIER_III:
                st = getMsg(player, Messages.NEXT_EVENT_DIAMOND_UPGRADE_III);
                break;
            case GAME_END:
                st = getMsg(player, Messages.NEXT_EVENT_GAME_END);
                break;
            case BEDS_DESTROY:
                st = getMsg(player, Messages.NEXT_EVENT_BEDS_DESTROY);
                break;
            case ENDER_DRAGON:
                st = getMsg(player, Messages.NEXT_EVENT_DRAGON_SPAWN);
                break;
        }

        return st;
    }

    private String getTime(Player player){
        Arena arena = (Arena) Arena.getArenaByPlayer(player);
        if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
            return getNextEventTime(arena, player);
        } else {
            if (arena.getStatus() == GameState.starting) {
                if (arena.getStartingTask() != null) {
                    return String.valueOf(arena.getStartingTask().getCountdown() + 1);
                }
            }
            return getNextEventDateFormat(player).format(new Date(System.currentTimeMillis()));
        }
    }

    @NotNull
    private String getNextEventTime(Arena arena, Player player) {
        if (!(arena instanceof Arena)) return getNextEventDateFormat(player).format((0L));
        long time = 0L;
        switch (arena.getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
            case EMERALD_GENERATOR_TIER_III:
                time = (arena.upgradeEmeraldsCount) * 1000L;
                break;
            case DIAMOND_GENERATOR_TIER_II:
            case DIAMOND_GENERATOR_TIER_III:
                time = (arena.upgradeDiamondsCount) * 1000L;
                break;
            case GAME_END:
                time = (arena.getPlayingTask().getGameEndCountdown()) * 1000L;
                break;
            case BEDS_DESTROY:
                time = (arena.getPlayingTask().getBedsDestroyCountdown()) * 1000L;
                break;
            case ENDER_DRAGON:
                time = (arena.getPlayingTask().getDragonSpawnCountdown()) * 1000L;
                break;
        }
        return time == 0 ? "0" : getNextEventDateFormat(player).format(new Date(time));
    }

    private int getOnlinePlayers(Player player){
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return Bukkit.getOnlinePlayers().size();
        return arena.getPlayers().size();
    }


    @Override
    public void refreshTabList() {

    }

    @Override
    public void refreshHealth() {

    }

    @Override
    public @Nullable ISidebar getSidebar(@NotNull Player player) {
        return null;
    }
}
