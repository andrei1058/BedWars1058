package com.tomkeuper.bedwars.sidebar;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.api.server.ServerType;
import com.tomkeuper.bedwars.api.sidebar.IScoreboardService;
import com.tomkeuper.bedwars.api.tasks.PlayingTask;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.levels.internal.PlayerLevel;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabConstants;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;
import me.neznamy.tab.api.placeholder.PlaceholderManager;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import me.neznamy.tab.api.team.UnlimitedNametagManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.text.SimpleDateFormat;

import static com.tomkeuper.bedwars.api.language.Language.getMsg;

public class BoardManager implements IScoreboardService {
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
            instance.registerLobbyScoreboards();
        }
        return instance != null;
    }

    public void registerLoadEvent(){
        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class,
                event -> {
                    IArena arena = Arena.getArenaByPlayer((Player) event.getPlayer().getPlayer());
                    if (BedWars.getServerType() == ServerType.SHARED && !((Player) event.getPlayer().getPlayer()).getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) return;
                    BoardManager.getInstance().giveTabFeatures((Player) event.getPlayer().getPlayer(), arena, false);
                });
    }

    public void registerLobbyScoreboards(){
        for (Language language : Language.getLanguages()){
            List<String> lines = language.l(Messages.SCOREBOARD_LOBBY);
            lines.replaceAll(s -> s.isEmpty() ? " " : s); // TAB doesn't display empty lines, we need to replace them with spaces
            scoreboardManager.createScoreboard("bw_lobby_" + language.getIso(),"%bw_scoreboard_title%", lines.subList(1, lines.size()));
        }
    }
    public void registerArenaScoreboards(Arena arena){
        //Technically it's possible to have per arena scoreboards. Future feature?
        for (Language language: Language.getLanguages()){
            List<String> waiting = getScoreboardLines(arena, language, "waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
            String scoreboardWaitingName = "bw_" + arena.getGroup() + "_waiting_" + language.getIso();
            if (!scoreboardManager.getRegisteredScoreboards().containsKey(scoreboardWaitingName)) scoreboardManager.createScoreboard(scoreboardWaitingName,"%bw_scoreboard_title%", waiting.subList(1, waiting.size()));

            List<String> starting = getScoreboardLines(arena, language, "starting", Messages.SCOREBOARD_DEFAULT_STARTING);
            String scoreboardStartingName = "bw_" + arena.getGroup() + "_starting_" + language.getIso();
            if (!scoreboardManager.getRegisteredScoreboards().containsKey(scoreboardStartingName)) scoreboardManager.createScoreboard(scoreboardStartingName,"%bw_scoreboard_title%", starting.subList(1, starting.size()));

            List<String> playing = getScoreboardLines(arena, language, "playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
            String scoreboardPlayingName = "bw_" + arena.getGroup() + "_playing_" + language.getIso();
            if (!scoreboardManager.getRegisteredScoreboards().containsKey(scoreboardPlayingName)) scoreboardManager.createScoreboard(scoreboardPlayingName,"%bw_scoreboard_title%", playing.subList(1, playing.size()));
        }
    }

    private List<String> getScoreboardLines(Arena arena, Language language, String phase, String path){
        List<String> lines = Language.getScoreboard(language, "scoreboard." + arena.getGroup() + "." + phase, path);
        lines.replaceAll(s -> s.isEmpty() ? " " : s); // TAB doesn't display empty lines, we need to replace them with spaces
        return lines;
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

        int placeholderRefresh = BedWars.config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL);
        if (placeholderRefresh < 50) {
            BedWars.plugin.getLogger().warning("Placeholder refresh interval is set to `" + placeholderRefresh + "` but cannot be lower than 50! Overriding to 100 now...");
            BedWars.config.set(ConfigPath.SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL, 100);
            placeholderRefresh = 100;
        }

        int PrefixRefresh = BedWars.config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_PREFIX_REFRESH_INTERVAL);
        if (PrefixRefresh < 50) {
            BedWars.plugin.getLogger().warning("Prefix Suffix refresh interval is set to `" + PrefixRefresh + "` but cannot be lower than 50! Overriding to 100 now...");
            BedWars.config.set(ConfigPath.SB_CONFIG_SIDEBAR_PREFIX_REFRESH_INTERVAL, 100);
            PrefixRefresh = 100;
        }

        int SuffixRefresh = BedWars.config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_SUFFIX_REFRESH_INTERVAL);
        if (SuffixRefresh < 50) {
            BedWars.plugin.getLogger().warning("Prefix Suffix refresh interval is set to `" + SuffixRefresh + "` but cannot be lower than 50! Overriding to 100 now...");
            BedWars.config.set(ConfigPath.SB_CONFIG_SIDEBAR_SUFFIX_REFRESH_INTERVAL, 100);
            SuffixRefresh = 100;
        }

        int titleRefresh = BedWars.config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL);
        if (titleRefresh < 50) {
            BedWars.plugin.getLogger().warning("Scoreboard title refresh interval is set to `" + titleRefresh + "` but cannot be lower than 50! Overriding to 100 now...");
            BedWars.config.set(ConfigPath.SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL, 100);
            titleRefresh = 100;
        }

        pm.registerPlayerPlaceholder("%bw_v_prefix%",placeholderRefresh, player -> BedWars.getChatSupport().getPrefix((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_v_suffix%", placeholderRefresh, player -> BedWars.getChatSupport().getSuffix((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_playername%", placeholderRefresh, player -> player.getName());
        pm.registerPlayerPlaceholder("%bw_player%", placeholderRefresh, player -> ((Player) player.getPlayer()).getDisplayName());
        pm.registerPlayerPlaceholder("%bw_money%", placeholderRefresh, player -> BedWars.getEconomy().getMoney((Player) player.getPlayer()));
        pm.registerServerPlaceholder("%bw_server_ip%", placeholderRefresh, () -> BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP));
        pm.registerServerPlaceholder("%bw_version%", placeholderRefresh, () -> BedWars.plugin.getDescription().getVersion());
        pm.registerServerPlaceholder("%bw_server_id%", placeholderRefresh, () -> BedWars.config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
        pm.registerPlayerPlaceholder("%bw_date%", placeholderRefresh, player -> getDateFormat((Player) player.getPlayer()).format(new Date(System.currentTimeMillis())));
        pm.registerPlayerPlaceholder("%bw_progress%", placeholderRefresh, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getProgress());
        pm.registerPlayerPlaceholder("%bw_level%", placeholderRefresh, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getLevelName());
        pm.registerPlayerPlaceholder("%bw_level_unformatted%", placeholderRefresh, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getLevel());
        pm.registerPlayerPlaceholder("%bw_current_xp%", placeholderRefresh, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getFormattedCurrentXp());
        pm.registerPlayerPlaceholder("%bw_required_xp%", placeholderRefresh, player -> PlayerLevel.getLevelByPlayer(player.getUniqueId()).getFormattedRequiredXp());
        pm.registerPlayerPlaceholder("%bw_map%", placeholderRefresh, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayName());
        pm.registerPlayerPlaceholder("%bw_map_name%", placeholderRefresh, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getArenaName());
        pm.registerPlayerPlaceholder("%bw_group%", placeholderRefresh, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getDisplayGroup((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_kills%", placeholderRefresh, player -> {
            if (null != Arena.getArenaByPlayer((Player) player.getPlayer())) return Arena.getArenaByPlayer((Player) player.getPlayer()).getPlayerKills((Player) player.getPlayer(), false);
            return BedWars.getStatsManager().get(player.getUniqueId()).getKills();
        });
        pm.registerPlayerPlaceholder("%bw_final_kills%", placeholderRefresh, player -> {
            if (null != Arena.getArenaByPlayer((Player) player.getPlayer())) return Arena.getArenaByPlayer((Player) player.getPlayer()).getPlayerKills((Player) player.getPlayer(), true);
            return BedWars.getStatsManager().get(player.getUniqueId()).getKills();
        });
        pm.registerPlayerPlaceholder("%bw_beds%", placeholderRefresh, player -> {
            if (null != Arena.getArenaByPlayer((Player) player.getPlayer())) return Arena.getArenaByPlayer((Player) player.getPlayer()).getPlayerBedsDestroyed((Player) player.getPlayer());
            return BedWars.getStatsManager().get(player.getUniqueId()).getBedsDestroyed();
        });
        pm.registerPlayerPlaceholder("%bw_deaths%", placeholderRefresh, player -> {
            if (null != Arena.getArenaByPlayer((Player) player.getPlayer())) return Arena.getArenaByPlayer((Player) player.getPlayer()).getPlayerDeaths((Player) player.getPlayer(), false);
            return BedWars.getStatsManager().get(player.getUniqueId()).getDeaths();
        });
        pm.registerPlayerPlaceholder("%bw_final_deaths%", placeholderRefresh, player -> BedWars.getStatsManager().get(player.getUniqueId()).getFinalDeaths());
        pm.registerPlayerPlaceholder("%bw_wins%", placeholderRefresh, player -> BedWars.getStatsManager().get(player.getUniqueId()).getWins());
        pm.registerPlayerPlaceholder("%bw_losses%", placeholderRefresh, player -> BedWars.getStatsManager().get(player.getUniqueId()).getLosses());
        pm.registerPlayerPlaceholder("%bw_games_played%", placeholderRefresh, player -> BedWars.getStatsManager().get(player.getUniqueId()).getGamesPlayed());
        pm.registerPlayerPlaceholder("%bw_next_event%", placeholderRefresh, player -> getNextEventName((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_on%", placeholderRefresh, player -> getOnlinePlayers((Player) player.getPlayer()));
        pm.registerPlayerPlaceholder("%bw_max%", placeholderRefresh, player -> Arena.getArenaByPlayer((Player) player.getPlayer()) == null ? "" : Arena.getArenaByPlayer((Player) player.getPlayer()).getMaxPlayers());
        pm.registerPlayerPlaceholder("%bw_time%", placeholderRefresh, tabPlayer -> {
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

        pm.registerPlayerPlaceholder("%bw_team%", placeholderRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? "" : arena.getTeam(player).getColor().chat() + arena.getTeam(player).getDisplayName(Language.getPlayerLanguage(player));
        });
        pm.registerPlayerPlaceholder("%bw_team_letter%", placeholderRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? "" :  arena.getTeam(player).getColor().chat() + (arena.getTeam(player).getDisplayName(Language.getPlayerLanguage(player)).substring(0, 1));
        });
        pm.registerPlayerPlaceholder("%bw_team_color%", placeholderRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            return null == arena ? "" : null == arena.getTeam(player) ? ""  : arena.getTeam(player).getColor().chat();
        });

        pm.registerPlayerPlaceholder("%bw_prefix%", PrefixRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            int i = tabPlayersPrefix.getOrDefault(tabPlayer,0);
            List<String> fixList = Collections.singletonList("");
            if (null == arena) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY);
            } else if (arena.isSpectator(player)){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR);
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
            if (i+1 >= fixList.size()){
                tabPlayersPrefix.put(tabPlayer,0);
                i = 0;
            } else {
                tabPlayersPrefix.put(tabPlayer,i+1);
            }
            if (!fixList.isEmpty()) prefix = fixList.get(i);
            return null == prefix ? "" : prefix;
        });

        pm.registerPlayerPlaceholder("%bw_suffix%", SuffixRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            int i = tabPlayersSuffix.getOrDefault(tabPlayer,0);
            List<String> fixList = Collections.singletonList("");
            if (null == arena) {
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY);
            } else if (arena.isSpectator(player)){
                fixList = Language.getList(player, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR);
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
            if (i+1 >= fixList.size()){
                tabPlayersSuffix.put(tabPlayer,0);
                i = 0;
            } else {
                tabPlayersSuffix.put(tabPlayer,i+1);
            }
            if (!fixList.isEmpty()) suffix = fixList.get(i);
            return null == suffix ? "" : suffix;
        });

        pm.registerPlayerPlaceholder("%bw_scoreboard_title%", titleRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            int i = tabPlayersTitle.getOrDefault(tabPlayer,0);
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
                i = 0;
            } else {
                tabPlayersTitle.put(tabPlayer,i+1);
            }
            String title = titleArray[i];
            return null ==  title? "" : title;
        });

        pm.registerPlayerPlaceholder("%bw_tab_health%", SuffixRefresh, tabPlayer -> {
            Player player = (Player) tabPlayer.getPlayer();
            IArena arena = Arena.getArenaByPlayer(player);
            // set sidebar lines based on game state or lobby
            String line = null;
            if (null != arena && null != arena.getStatus()) {
                if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                    line = Language.getMsg((Player) tabPlayer.getPlayer(), Messages.FORMATTING_SCOREBOARD_HEALTH);
                }
            }
            return null ==  line? "" : line;
        });
    }


    public static BoardManager getInstance() {
        return instance;
    }

    @Override
    public void giveTabFeatures(@NotNull Player player, @Nullable IArena arena, boolean delay) {
        String arenaDisplayname = "null";
        if (null != arena) arenaDisplayname = arena.getDisplayName();
        BedWars.debug("giveTabFeatures() player: " + player.getDisplayName() + " arena: " + arenaDisplayname);

        // if sidebar is disabled in lobby on shared mode
        if (null == arena){ if (!BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR)) return;}
        else if (!BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR)) return;

        TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());

        String scoreboardName = "bw_lobby_" + Language.getPlayerLanguage(player).getIso();
        // set sidebar lines based on game state or lobby
        if (null != arena){
            if (arena.getStatus() == GameState.waiting) {
                scoreboardName = "bw_" + arena.getGroup() + "_waiting_" + Language.getPlayerLanguage(player).getIso();
                tabPlayer.resetTemporaryGroup();
            } else if (arena.getStatus() == GameState.starting) {
                scoreboardName = "bw_" + arena.getGroup() + "_starting_" + Language.getPlayerLanguage(player).getIso();
                tabPlayer.resetTemporaryGroup();
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                scoreboardName = "bw_" + arena.getGroup() + "_playing_" + Language.getPlayerLanguage(player).getIso();
                tabPlayer.setTemporaryGroup(arena.getTeam(player) != null ? arena.getTeam(player).getName() : "");
            }
        }
        if (BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_BELOW_NAME)){
            if (TabAPI.getInstance().getTeamManager() instanceof UnlimitedNametagManager) {
                UnlimitedNametagManager unm = (UnlimitedNametagManager) TabAPI.getInstance().getTeamManager();
                if (null == unm) Bukkit.getLogger().warning("Below name health is enabled in BedWars config but unlimited nametags is disabled in TAB config!");
                unm.setLine(tabPlayer,"belowname", "%bw_tab_health%");
            }
        }

        Scoreboard scoreboard = scoreboardManager.getRegisteredScoreboards().get(scoreboardName);
        scoreboardManager.showScoreboard(tabPlayer, scoreboard);

        setHeaderFooter(tabPlayer,arena);

        TabAPI.getInstance().getTablistFormatManager().setPrefix(tabPlayer, "%bw_prefix%");
        TabAPI.getInstance().getTablistFormatManager().setSuffix(tabPlayer, "%bw_suffix%");

        TabAPI.getInstance().getTeamManager().setPrefix(tabPlayer, "%bw_prefix%");
        TabAPI.getInstance().getTeamManager().setSuffix(tabPlayer, "%bw_suffix%");
    }

    @Override
    public void remove(@NotNull Player player) {
        scoreboardManager.resetScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()));
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
        PlayingTask playingTask = arena.getPlayingTask();
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
                if (null == playingTask) {
                    time = 0;
                    break;
                }
                time = (playingTask.getGameEndCountdown()) * 1000L;
                break;
            case BEDS_DESTROY:
                if (null == playingTask) {
                    time = 0;
                    break;
                }
                time = (arena.getPlayingTask().getBedsDestroyCountdown()) * 1000L;
                break;
            case ENDER_DRAGON:
                if (null == playingTask) {
                    time = 0;
                    break;
                }
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

            if (BedWars.getServerType() == ServerType.SHARED) {
                if (BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !BedWars.config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(BedWars.config.getLobbyWorldName());
                    return null != lobby;
                }
            }

            return !BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
        }
        // if tab formatting is disabled in game
        if (arena.getStatus() == GameState.playing && BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING)) {
            return false;
        }

        // if tab formatting is disabled in starting
        if (arena.getStatus() == GameState.starting && BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING)) {
            return false;
        }

        // if tab formatting is disabled in waiting
        if (arena.getStatus() == GameState.waiting && BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING)) {
            return false;
        }

        // if tab formatting is disabled in restarting
        return arena.getStatus() != GameState.restarting || !BedWars.config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING);
    }

    @Override
    public @Nullable Scoreboard getScoreboard(@NotNull Player player) {
        return scoreboardManager.getActiveScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()));
    }
}
