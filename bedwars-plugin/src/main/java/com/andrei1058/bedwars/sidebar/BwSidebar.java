package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.sidebar.ISidebar;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import com.andrei1058.bedwars.stats.PlayerStats;
import com.andrei1058.spigot.sidebar.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class BwSidebar implements ISidebar {

    private static final SidebarLine EMPTY_TITLE = new SidebarLine() {
        @Override
        public @NotNull String getLine() {
            return "";
        }
    };

    private static final String SPECTATOR_TAB = "spectators010101";
    private static final String TEAM_PREFIX = "?_";

    private final Player player;
    private IArena arena;
    private Sidebar handle;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat nextEventDateFormat;
    private final HashMap<String, PlayerTab> tabList = new HashMap<>();

    private final List<PlaceholderProvider> persistentProviders = new ArrayList<>();


    protected BwSidebar(Player player) {
        this.player = player;
        nextEventDateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        nextEventDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_DATE));
    }

    public void remove() {
        if (handle == null) {
            return;
        }
        tabList.forEach((k, v) -> handle.removeTab(k));
        handle.remove(player);
    }

    public void setContent(List<String> titleArray, List<String> lineArray, @Nullable IArena arena) {
        this.arena = arena;
        SidebarLine title = this.normalizeTitle(titleArray);
        List<SidebarLine> lines = this.normalizeLines(lineArray);

        List<PlaceholderProvider> placeholders = this.getPlaceholders();
        placeholders.addAll(this.persistentProviders);

        // if it is the first time setting content we create the handle
        if (null == handle) {
            handle = SidebarService.getInstance().getSidebarHandler().createSidebar(title, lines, placeholders);
            handle.add(player);
        } else {
            while (handle.lineCount() > 0) {
                handle.removeLine(0);
            }
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                new ArrayList<>(handle.getPlaceholders()).forEach(p -> handle.removePlaceholder(p.getPlaceholder()));
                placeholders.forEach(p -> handle.addPlaceholder(p));
                handle.setTitle(title);
                lines.forEach(l -> handle.addLine(l));
            }, 2L);
        }
        handlePlayerList();
        setHeaderFooter();
    }

    public Player getPlayer() {
        return player;
    }

    @SuppressWarnings("ConstantConditions")
    public SidebarLine normalizeTitle(@Nullable List<String> titleArray) {
        String[] aolo = new String[titleArray.size()];
        for (int x = 0; x < titleArray.size(); x++) {
            aolo[x] = titleArray.get(x);
        }
        return null == titleArray || titleArray.isEmpty() ?
                EMPTY_TITLE :
                new SidebarLineAnimated(aolo);
    }

    @Contract(pure = true)
    public @NotNull List<SidebarLine> normalizeLines(@NotNull List<String> lineArray) {
        List<SidebarLine> lines = new ArrayList<>();

        int teamCount = 0;
        Language language = Language.getPlayerLanguage(player);
        String genericTeamFormat = language.m(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC);

        for (String line : lineArray) {
            // convert old placeholders
            line = line.replace("{server_ip}", "{serverIp}");

            // generic team placeholder {team}
            if (arena != null) {
                if (line.trim().equals("{team}")) {
                    if (arena.getTeams().size() > teamCount) {
                        ITeam team = arena.getTeams().get(teamCount++);
                        String teamName = team.getDisplayName(language);
                        line = genericTeamFormat
                                .replace("{TeamLetter}", String.valueOf(teamName.length() != 0 ? teamName.charAt(0) : ""))
                                .replace("{TeamColor}", team.getColor().chat().toString())
                                .replace("{TeamName}", teamName)
                                .replace("{TeamStatus}", "{Team" + team.getName() + "Status}");
                    } else {
                        // skip line
                        continue;
                    }
                }

                line = line
                        .replace("{map}", arena.getDisplayName())
                        .replace("{map_name}", arena.getArenaName())
                        .replace("{group}", arena.getDisplayGroup(player));

                for (ITeam currentTeam : arena.getTeams()) {
                    final ChatColor color = currentTeam.getColor().chat();
                    // Static team placeholders
                    line = line
                            .replace("{Team" + currentTeam.getName() + "Color}", color.toString())
                            .replace("{Team" + currentTeam.getName() + "Name}", currentTeam.getDisplayName(language));

                }
            }

            // General static placeholders
            line = line
                    .replace("{serverIp}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{server}", config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID))
                    .replace("{playername}", player.getName())
                    .replace("{player}", player.getDisplayName())
                    .replace("{money}", String.valueOf(getEconomy().getMoney(player)));

            // Add the line to the sidebar
            String finalTemp = line;

            lines.add(new SidebarLine() {
                @Override
                public @NotNull String getLine() {
                    return finalTemp;
                }
            });
        }
        return lines;
    }

    @Contract(pure = true)
    private @NotNull List<PlaceholderProvider> getPlaceholders() {
        List<PlaceholderProvider> providers = new ArrayList<>();

        providers.add(new PlaceholderProvider("{player}", player::getDisplayName));
        providers.add(new PlaceholderProvider("{playerName}", player::getCustomName));
        providers.add(new PlaceholderProvider("{money}", () -> String.valueOf(getEconomy().getMoney(player))));
        providers.add(new PlaceholderProvider("{date}", () -> dateFormat.format(new Date(System.currentTimeMillis()))));
        providers.add(new PlaceholderProvider("{serverIp}", () -> BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP)));
        providers.add(new PlaceholderProvider("{version}", () -> plugin.getDescription().getVersion()));
        providers.add(new PlaceholderProvider("{server}", () -> config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID)));
        PlayerLevel level = PlayerLevel.getLevelByPlayer(getPlayer().getUniqueId());
        if (null != level) {
            providers.add(new PlaceholderProvider("{progress}", level::getProgress));
            providers.add(new PlaceholderProvider("{level}", () -> String.valueOf(level.getLevelName())));
            providers.add(new PlaceholderProvider("{levelUnformatted}", () -> String.valueOf(level.getLevel())));
            providers.add(new PlaceholderProvider("{currentXp}", level::getFormattedCurrentXp));
            providers.add(new PlaceholderProvider("{requiredXp}", level::getFormattedRequiredXp));
        }

        if (noArena()) {
            providers.add(new PlaceholderProvider("{on}", () ->
                    String.valueOf(Bukkit.getOnlinePlayers().size()))
            );
            PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
            //noinspection ConstantConditions
            if (null != stats) {
                providers.add(new PlaceholderProvider("{kills}", () ->
                        String.valueOf(stats.getKills()))
                );
                providers.add(new PlaceholderProvider("{finalKills}", () ->
                        String.valueOf(stats.getFinalKills()))
                );
                providers.add(new PlaceholderProvider("{beds}", () ->
                        String.valueOf(stats.getBedsDestroyed()))
                );
                providers.add(new PlaceholderProvider("{deaths}", () ->
                        String.valueOf(stats.getDeaths()))
                );
                providers.add(new PlaceholderProvider("{finalDeaths}", () ->
                        String.valueOf(stats.getFinalDeaths()))
                );
                providers.add(new PlaceholderProvider("{wins}", () ->
                        String.valueOf(stats.getWins()))
                );
                providers.add(new PlaceholderProvider("{losses}", () ->
                        String.valueOf(stats.getLosses()))
                );
                providers.add(new PlaceholderProvider("{gamesPlayed}", () ->
                        String.valueOf(stats.getGamesPlayed()))
                );
            }
        } else {
            providers.add(new PlaceholderProvider("{on}", () -> String.valueOf(arena.getPlayers().size())));
            providers.add(new PlaceholderProvider("{max}", () -> String.valueOf(arena.getMaxPlayers())));
            providers.add(new PlaceholderProvider("{nextEvent}", this::getNextEventName));
            providers.add(new PlaceholderProvider("{time}", () -> {
                GameState status = this.arena.getStatus();
                if (status == GameState.playing || status == GameState.restarting) {
                    return getNextEventTime();
                } else {
                    if (status == GameState.starting) {
                        if (arena.getStartingTask() != null) {
                            return String.valueOf(arena.getStartingTask().getCountdown() + 1);
                        }
                    }
                    return dateFormat.format(new Date(System.currentTimeMillis()));
                }
            }));
            providers.add(new PlaceholderProvider("{kills}", () ->
                    String.valueOf(arena.getPlayerKills(player, false))
            ));
            providers.add(new PlaceholderProvider("{finalKills}", () ->
                    String.valueOf(arena.getPlayerKills(player, true))
            ));
            providers.add(new PlaceholderProvider("{beds}", () ->
                    String.valueOf(arena.getPlayerBedsDestroyed(player))
            ));
            providers.add(new PlaceholderProvider("{deaths}", () ->
                    String.valueOf(arena.getPlayerDeaths(player, false))
            ));

            // Dynamic team placeholders
            for (ITeam currentTeam : arena.getTeams()) {
                providers.add(new PlaceholderProvider("{Team" + currentTeam.getName() + "Status}", () -> {
                    String result;
                    if (currentTeam.isBedDestroyed()) {
                        if (currentTeam.getSize() > 0) {
                            result = getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED)
                                    .replace("{remainingPlayers}", String.valueOf(currentTeam.getSize()));
                        } else {
                            result = getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED);
                        }
                    } else {
                        result = getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE);
                    }
                    if (currentTeam.isMember(getPlayer())) {
                        result += getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM);
                    }
                    return result;
                }));
            }
        }

        return providers;
    }

    @NotNull
    private String getNextEventName() {
        if (!(arena instanceof Arena)) return "-";
        Arena arena = (Arena) this.arena;
        String st = "-";
        switch (arena.getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_EMERALD_UPGRADE_II);
                break;
            case EMERALD_GENERATOR_TIER_III:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_EMERALD_UPGRADE_III);
                break;
            case DIAMOND_GENERATOR_TIER_II:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_II);
                break;
            case DIAMOND_GENERATOR_TIER_III:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_III);
                break;
            case GAME_END:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_GAME_END);
                break;
            case BEDS_DESTROY:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_BEDS_DESTROY);
                break;
            case ENDER_DRAGON:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DRAGON_SPAWN);
                break;
        }

        return st;
    }

    @NotNull
    private String getNextEventTime() {
        if (!(arena instanceof Arena)) return nextEventDateFormat.format((0L));
        Arena arena = (Arena) this.arena;
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
        return time == 0 ? "0" : nextEventDateFormat.format(new Date(time));
    }

    private boolean noArena() {
        return null == arena;
    }

    private void handlePlayerList() {
        if (null != handle) {
            tabList.forEach((k, v) -> handle.removeTab(k));
        }

        handleHealthIcon();

        if (this.isTabFormattingDisabled()) {
            return;
        }

        if (noArena()) {
            // if tab formatting is enabled in lobby world
            if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                    !config.getLobbyWorldName().trim().isEmpty()) {

                World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                if (null == lobby) {
                    return;
                }
                lobby.getPlayers().forEach(inLobby -> giveUpdateTabFormat(inLobby, true));
            }
            return;
        }

        handleHealthIcon();

        arena.getPlayers().forEach(playing -> giveUpdateTabFormat(playing, true));
        arena.getSpectators().forEach(spectating -> giveUpdateTabFormat(spectating, true));
    }

    /**
     * Handle given player in sidebar owner tab list.
     * Will remove existing tab and give a new one based on game conditions list like spectator, team red, etc.
     * Will handle invisibility potion as well.
     */
    public void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck) {
        // if sidebar was not created
        if (handle == null) {
            return;
        }

        // unique tab list name
        String tabListName = player.getName();

        if (tabList.containsKey(tabListName)) {
            handle.removeTab(tabListName);
            tabList.remove(tabListName);
            // SidebarManager.getInstance().sendHeaderFooter(player, "", "");
        }

        if (!skipStateCheck) {
            if (this.isTabFormattingDisabled()) {
                return;
            }
        }

        SidebarLine prefix;
        SidebarLine suffix;

        if (noArena()) {
            prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, player, null);
            suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, player, null);

            PlayerTab tab = handle.playerTabCreate(
                    tabListName, player, prefix, suffix, PlayerTab.PushingRule.NEVER
            );
            tab.add(player);
            tabList.put(tabListName, tab);
            return;
        }

        // in-game tab has a special treatment
        if (arena.isSpectator(player)) {
            PlayerTab tab = tabList.get(SPECTATOR_TAB);
            if (null == tab) {
                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR, player, null);
                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR, player, null);
                tab = handle.playerTabCreate(SPECTATOR_TAB, null, prefix, suffix, PlayerTab.PushingRule.NEVER);
                tabList.put(SPECTATOR_TAB, tab);
            }
            tab.add(player);

            return;
        }

        GameState status = arena.getStatus();
        if (status != GameState.playing) {
            switch (status) {
                case waiting:
                    prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, player, null);
                    suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, player, null);
                    break;
                case starting:
                    prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, player, null);
                    suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, player, null);
                    break;
                case restarting:
                    ITeam team = arena.getTeam(player);
                    if (null == team) {
                        team = arena.getExTeam(player.getUniqueId());
                    }

                    String displayName = null == team ? "" : team.getDisplayName(Language.getPlayerLanguage(this.player));

                    HashMap<String, String> replacements = new HashMap<>();
                    replacements.put("{team}", null == team ? "" : team.getColor().chat() + displayName);
                    replacements.put("{teamLetter}", null == team ? "" : team.getColor().chat() + (displayName.substring(0, 1)));
                    replacements.put("{teamColor}", null == team ? "" : team.getColor().chat().toString());


                    prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, player, replacements);
                    suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING, player, replacements);
                    break;
                default:
                    throw new IllegalStateException("Unhandled game status!");
            }
            PlayerTab t = handle.playerTabCreate(tabListName, player, prefix, suffix, PlayerTab.PushingRule.NEVER);
            t.add(player);
            tabList.put(tabListName, t);
            return;
        }

        ITeam team = arena.getTeam(player);
        if (null == team) {
            team = arena.getExTeam(player.getUniqueId());
        }
        if (null == team) {
            throw new RuntimeException("Wtf dude");
        }

        String tabName = this.getTabName(team);
        String tabNameInvisible = tabName = tabName.substring(0, tabName.length() >= 16 ? 15 : tabName.length());
        tabNameInvisible += "^!";

        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            if (!team.isMember(getPlayer())) {
                // remove player from its tab group (if team tab group)
                PlayerTab teamTab = tabList.getOrDefault(tabName, null);
                if (null != teamTab) {
                    teamTab.remove(player);

                    // create or get tab group for the invisible players in that team
                    // set tab group name visibility to false
                    // identifier for invisibility
                    tabName = tabNameInvisible;
                }
            }
        } else {
            PlayerTab invTab = tabList.getOrDefault(tabNameInvisible, null);
            if (null != invTab) {
                invTab.remove(player);
            }
        }

        PlayerTab teamTab = tabList.get(tabName);
        if (null == teamTab) {
            String displayName = team.getDisplayName(Language.getPlayerLanguage(this.player));

            HashMap<String, String> replacements = new HashMap<>();
            replacements.put("{team}", team.getColor().chat() + displayName);
            replacements.put("{teamLetter}", team.getColor().chat() + (displayName.substring(0, 1)));
            replacements.put("{teamColor}", team.getColor().chat().toString());

            prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, player, replacements);
            suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, player, replacements);

            teamTab = handle.playerTabCreate(tabName, null, prefix, suffix, PlayerTab.PushingRule.PUSH_OTHER_TEAMS);
            tabList.put(tabName, teamTab);
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                teamTab.setNameTagVisibility(PlayerTab.NameTagVisibility.NEVER);
            }
        }

        teamTab.add(player);
    }

    // Provide header and footer for current game state
    private void setHeaderFooter() {
//        if (isTabFormattingDisabled()) {
//            return;
//        }
        Language lang = Language.getPlayerLanguage(player);

        if (noArena()) {
            SidebarManager.getInstance().sendHeaderFooter(
                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY),
                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY)
            );
            return;
        }
        if (arena.isSpectator(player)) {
            SidebarManager.getInstance().sendHeaderFooter(
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
            default:
                throw new IllegalStateException("Unhandled arena status");
        }

        SidebarManager.getInstance().sendHeaderFooter(
                player, lang.m(headerPath),
                lang.m(footerPath)
        );
    }

    private @NotNull String getTabName(@NotNull ITeam team) {
        String tabName = TEAM_PREFIX + Base64.getEncoder().encodeToString((team.getName()).getBytes(StandardCharsets.UTF_8));
        if (tabName.length() > 16) {
            tabName = tabName.substring(0, 16);
        }
        return tabName;
    }

    @NotNull
    private SidebarLine getTabText(String path, Player targetPlayer, @Nullable HashMap<String, String> replacements) {
        List<String> strings = Language.getList(getPlayer(), path);
        if (strings.isEmpty()) {
            return new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return "";
                }
            };
        }

        strings = new ArrayList<>();
        for (String string : Language.getList(getPlayer(), path)) {
            String parsed = string.replace("{vPrefix}", BedWars.getChatSupport().getPrefix(targetPlayer))
                    .replace("{vSuffix}", BedWars.getChatSupport().getSuffix(targetPlayer));

            if (null != replacements) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    parsed = parsed.replace(entry.getKey(), entry.getValue());
                }
            }

            strings.add(parsed);
        }

        if (strings.size() == 1) {
            final String line = strings.get(0);
            return new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return line;
                }
            };
        }

        final String[] lines = new String[strings.size()];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = strings.get(i);
        }
        return new SidebarLineAnimated(lines);
    }

    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage
     */
    public boolean isTabFormattingDisabled() {
        if (noArena()) {

            if (getServerType() == ServerType.SHARED) {
                if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                    return null != lobby;
                }
            }

            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
        }
        GameState status = arena.getStatus();

        // if tab formatting is disabled in game
        if (status == GameState.playing && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING)) {
            return false;
        }

        // if tab formatting is disabled in starting
        if (status == GameState.starting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING)) {
            return false;
        }

        // if tab formatting is disabled in waiting
        if (status == GameState.waiting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING)) {
            return false;
        }

        // if tab formatting is disabled in restarting
        return status != GameState.restarting || !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING);
    }

    @Override
    public boolean registerPersistentPlaceholder(PlaceholderProvider placeholderProvider) {
        this.persistentProviders.add(placeholderProvider);
        return true;
    }

    public void handleHealthIcon() {
        if (null == handle) {
            return;
        }

        if (noArena()) {
            handle.hidePlayersHealth();
            return;
        } else if (arena.getStatus() != GameState.playing) {
            handle.hidePlayersHealth();
            return;
        }

        List<String> animation = Language.getList(player, Messages.FORMATTING_SCOREBOARD_HEALTH);
        if (animation.isEmpty()) return;
        SidebarLine line;
        if (animation.size() > 1) {
            String[] lines = new String[animation.size()];
            for (int i = 0; i < animation.size(); i++) {
                lines[i] = animation.get(i);
            }
            line = new SidebarLineAnimated(lines);
        } else {
            final String text = animation.get(0);
            line = new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return text;
                }
            };
        }

        if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_IN_TAB)) {
            handle.showPlayersHealth(line, true);
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (arena != null && handle != null) {
                arena.getPlayers().forEach(player -> handle.setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
                if (arena.isSpectator(getPlayer())) {
                    arena.getSpectators().forEach(player -> handle.setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
                }
            }
        }, 20L);
    }

    /**
     * Hide player name tag on head when he drinks an invisibility potion.
     * This is required because not all clients hide it automatically.
     *
     * @param _toggle true when applied, false when expired.
     */
    public void handleInvisibilityPotion(@NotNull Player player, boolean _toggle) {
        if (null == arena) {
            throw new RuntimeException("This can only be used when the player is in arena");
        }
        this.giveUpdateTabFormat(player, false);
    }

    public Sidebar getHandle() {
        return handle;
    }

    public IArena getArena() {
        return arena;
    }
}
