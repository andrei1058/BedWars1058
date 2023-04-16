package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.sidebar.IScoreboardService;
import com.andrei1058.bedwars.api.tasks.PlayingTask;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabConstants;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.placeholder.PlaceholderManager;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.text.SimpleDateFormat;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class BoardManager implements IScoreboardService {

    private final HashMap<String, Scoreboard> sidebars = new HashMap<>();
    private static ScoreboardManager scoreboardManager;
    private static BoardManager instance;
    private final HashMap<TabPlayer, Integer> tabPlayersPrefix = new HashMap<>();
    private final HashMap<TabPlayer, Integer> tabPlayersSuffix = new HashMap<>();
    private final HashMap<TabPlayer, Integer> tabPlayersTitle = new HashMap<>();


    public static boolean init(){
        if (!TabAPI.getInstance().getFeatureManager().isFeatureEnabled(TabConstants.Feature.SCOREBOARD)) return false;
        if (null == instance) {
            instance = new BoardManager();
            instance.registerPlaceholders();
            instance.registerLoadEvent();
        }
        return instance != null;
    }

    public static void registerLoadEvent(){
        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class,
                event -> {
                    IArena arena = Arena.getArenaByPlayer((Player) event.getPlayer().getPlayer());
                    if (BedWars.getServerType() == ServerType.SHARED && !((Player) event.getPlayer().getPlayer()).getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) return;
                    BoardManager.getInstance().giveSidebar((Player) event.getPlayer().getPlayer(), arena, false);
                });
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
        pm.registerPlayerPlaceholder("%bw_map%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayName());
        pm.registerPlayerPlaceholder("%bw_map_name%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getArenaName());
        pm.registerPlayerPlaceholder("%bw_group%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayGroup((Player) player.getPlayer()));
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
        pm.registerPlayerPlaceholder("%bw_max%", 100, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getMaxPlayers());
        pm.registerPlayerPlaceholder("%bw_time%", 50, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            Arena arena = (Arena) Arena.getArenaByPlayer(player);
            if (null == arena) return "";
            if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                return getNextEventTime(arena, player);
            } else if (arena.getStatus() == GameState.starting) {
                if (arena.getStartingTask() != null) {
                    return arena.getStartingTask().getCountdown()+1;
                }
            }
            return getNextEventDateFormat(player).format(new Date(System.currentTimeMillis()));
        });
//        pm.registerPlayerPlaceholder("%bw_%", 100, player -> );

        pm.registerPlayerPlaceholder("%bw_team%", 100, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? "" : arena.getTeam(player).getColor().chat() + arena.getTeam(player).getDisplayName(Language.getPlayerLanguage(player));
        });
        pm.registerPlayerPlaceholder("%bw_team_letter%", 100, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? "" :  arena.getTeam(player).getColor().chat() + (arena.getTeam(player).getDisplayName(Language.getPlayerLanguage(player)).substring(0, 1));
        });
        pm.registerPlayerPlaceholder("%bw_team_color%", 100, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? ""  : arena.getTeam(player).getColor().chat();
        });

        pm.registerPlayerPlaceholder("%bw_prefix%", 10000, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            Integer i = tabPlayersPrefix.getOrDefault(tabPlayer,0);
            List<String> fixList = Collections.singletonList("");
            if (null == arena) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY);
            } else if (arena.isSpectator(player)){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR);//todo do not show spectators to in game players
            } else if (arena.getStatus() == GameState.playing) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING);
            } else if (arena.getStatus() == GameState.waiting) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING);
            } else if (arena.getStatus() == GameState.starting){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING);
            }else if (arena.getStatus() == GameState.restarting) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING);
            } else {
                BedWars.debug("Unhandled game state bw prefix");
            }
            String prefix = null;
            if (!fixList.isEmpty()) prefix = fixList.get(i);
            if (i+1 >= fixList.size()){
                tabPlayersPrefix.put(tabPlayer,0);
            } else {
                tabPlayersPrefix.put(tabPlayer,i+1);
            }
            return null == prefix ? "" : prefix;
        });

        pm.registerPlayerPlaceholder("%bw_suffix%", 10000, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            Integer i = tabPlayersSuffix.getOrDefault(tabPlayer,0);
            List<String> fixList = Collections.singletonList("");
            if (null == arena) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY);
            } else if (arena.isSpectator(player)){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR);//todo do not show spectators to in game players
            } else if (arena.getStatus() == GameState.playing) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING);
            } else if (arena.getStatus() == GameState.waiting) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING);
            } else if (arena.getStatus() == GameState.starting){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING);
            }else if (arena.getStatus() == GameState.restarting) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING);
            } else {
                BedWars.debug("Unhandled game state bw suffix");
            }
            String suffix = null;
            if (!fixList.isEmpty()) suffix = fixList.get(i);
            if (i+1 >= fixList.size()){
                tabPlayersSuffix.put(tabPlayer,0);
            } else {
                tabPlayersSuffix.put(tabPlayer,i+1);
            }
            return null == suffix ? "" : suffix;
        });

        pm.registerPlayerPlaceholder("%bw_scoreboard_title%", 500, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            Integer i = tabPlayersTitle.getOrDefault(tabPlayer,0);
            // set sidebar lines based on game state or lobby
            List<String> lines = null;
            String titleLine;
            if (null == arena) {
                if (BedWars.getServerType() != ServerType.SHARED) {
                    lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
                }
            } else {
                if (arena.getStatus() == GameState.waiting) {
                    lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
                } else if (arena.getStatus() == GameState.starting) {
                    lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING);
                } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                    lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
                }
            }

            titleLine = lines.get(0);
            String[] titleArray = titleLine.split(",");

            if (i+1 >= titleArray.length){
                tabPlayersTitle.put(tabPlayer,0);
            } else {
                tabPlayersTitle.put(tabPlayer,i+1);
            }
            String title = titleArray[i];
            return null ==  title? "" : title;
        });
    }


    public static BoardManager getInstance() {
        return instance;
    }

    @Override
    public void giveSidebar(@NotNull Player player, @Nullable IArena arena, boolean delay) {
        String arenaDisplayname = "null";
        if (null != arena) arenaDisplayname = arena.getDisplayName();
        BedWars.debug("giveSidebar() player: " + player.getDisplayName() + " arena: " + arenaDisplayname);
        // if sidebar is disabled in lobby on shared mode
        if (null == arena) if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR)) return;
        else if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR)) return;


        // set sidebar lines based on game state or lobby
        List<String> lines = null;
        String title;
        if (null == arena) {
            if (BedWars.getServerType() != ServerType.SHARED) {
                lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
            }
        } else {
            if (arena.getStatus() == GameState.waiting) {
                lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
            } else if (arena.getStatus() == GameState.starting) {
                lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING);
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                lines = Language.getScoreboard(player, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
            }
        }

        if (lines.size() == 1) {
            lines = new ArrayList<>();
        }
        lines = lines.subList(1, lines.size());
        lines.replaceAll(s -> s.isEmpty() ? " " : s); // TAB doesn't display empty lines, we need to replace them with spaces

        Scoreboard scoreboard = sidebars.getOrDefault(player.getUniqueId(), getScoreboard(arena, "%bw_scoreboard_title%", lines));
        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());

        TabAPI.getInstance().getScoreboardManager().showScoreboard(tabPlayer, scoreboard);

        setHeaderFooter(tabPlayer,arena);

        TabAPI.getInstance().getTablistFormatManager().setPrefix(tabPlayer, "%bw_prefix%");
        TabAPI.getInstance().getTablistFormatManager().setSuffix(tabPlayer, "%bw_suffix%");
    }

    @Override
    public void remove(@NotNull Player player) {

    }

    public Scoreboard getScoreboard(IArena arena, String title, List<String> lineArray){ //todo create global during init
        String status = "default";
        if (arena != null) status = arena.getStatus().toString();
        return scoreboardManager.createScoreboard(status, title, lineArray);
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
                PlayingTask playingTask = arena.getPlayingTask();
                if (null == playingTask) {
                    time = 0;
                    break;
                }
                time = (playingTask.getGameEndCountdown()) * 1000L;
                break;
            case BEDS_DESTROY:
                time = (arena.getPlayingTask().getBedsDestroyCountdown()) * 1000L;
                break;
            case ENDER_DRAGON:
                time = (arena.getPlayingTask().getDragonSpawnCountdown()) * 1000L;
                break;
        }
        return getNextEventDateFormat(player).format(new Date(time));
    }

    private int getOnlinePlayers(Player player){
        IArena arena = Arena.getArenaByPlayer(player);
        if (arena == null) return Bukkit.getOnlinePlayers().size();
        return arena.getPlayers().size();
    }

    private void setHeaderFooter(TabPlayer player, IArena arena) {
        if (isTabFormattingDisabled(arena)) {
            return;
        }
        Language lang = Language.getPlayerLanguage((Player) player.getPlayer());

        if (null == arena) {
            TabAPI.getInstance().getHeaderFooterManager().setHeaderAndFooter(
                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY),
                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY)
            );
            return;
        }
        if (arena.isSpectator((Player) player.getPlayer())) {
            TabAPI.getInstance().getHeaderFooterManager().setHeaderAndFooter(
                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR),
                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR)
            );
            return;
        }

        String headerPath = null;
        String footerPath = null;

        switch (arena.getStatus()) {
            case waiting:
                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING;
                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING;
                break;
            case starting:
                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING;
                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING;
                break;
            case playing:
                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING;
                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING;
                break;
            case restarting:
                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING;
                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING;
                break;
        }

        TabAPI.getInstance().getHeaderFooterManager().setHeaderAndFooter(
                player, lang.m(headerPath),
                lang.m(footerPath)
        );
    }

    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage
     */
    @Override
    public boolean isTabFormattingDisabled(IArena arena) {
        if (null == arena) {

            if (getServerType() == ServerType.SHARED) {
                if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                    return null != lobby;
                }
            }

            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
        }
        // if tab formatting is disabled in game
        if (arena.getStatus() == GameState.playing && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING)) {
            return false;
        }

        // if tab formatting is disabled in starting
        if (arena.getStatus() == GameState.starting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING)) {
            return false;
        }

        // if tab formatting is disabled in waiting
        if (arena.getStatus() == GameState.waiting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING)) {
            return false;
        }

        // if tab formatting is disabled in restarting
        return arena.getStatus() != GameState.restarting || !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING);
    }

//    @Override
//    public void refreshTabList(TabPlayer player) {
//        TabPlayer[] players = TabAPI.getInstance().getOnlinePlayers();
//
//            String prefix;
//            String suffix;
//
//            IArena arena = Arena.getArenaByPlayer((Player) player.getPlayer());
//
//            if (null == arena) {
//                List<String> prefixes = Language.getList((Player) player.getPlayer(), Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY);
//                List<String> suffixes = Language.getList((Player) player.getPlayer(), Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY);
//
//                return;
//            }
//    }

    private String getNextItem(List<String> myList) {
        Iterator<String> iterator = myList.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            iterator = myList.iterator(); // Create a new iterator to start from the beginning
            return iterator.next();
        }
    }
}
