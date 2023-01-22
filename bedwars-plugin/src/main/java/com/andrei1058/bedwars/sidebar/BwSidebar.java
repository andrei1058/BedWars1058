package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import com.andrei1058.bedwars.stats.PlayerStats;
import com.andrei1058.spigot.sidebar.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class BwSidebar {

    private static final SidebarLine EMPTY_TITLE = new SidebarLine() {
        @Override
        public @NotNull String getLine() {
            return "";
        }
    };

    private static final String SPECTATOR_TAB = Base64.getEncoder().encodeToString("spectators".getBytes(StandardCharsets.UTF_8));
    private static final String TEAM_PREFIX = "bw";

    private final Player player;
    private IArena arena;
    private Sidebar handle;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat nextEventDateFormat;
    private final HashMap<String, PlayerTab> tabList = new HashMap<>();


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
    }

    public Player getPlayer() {
        return player;
    }

    @SuppressWarnings("ConstantConditions")
    private SidebarLine normalizeTitle(@Nullable List<String> titleArray) {
        String[] aolo = new String[titleArray.size()];
        for (int x = 0; x < titleArray.size(); x++) {
            aolo[x] = titleArray.get(x);
        }
        return null == titleArray || titleArray.isEmpty() ?
                EMPTY_TITLE :
                new SidebarLineAnimated(aolo);
    }

    @Contract(pure = true)
    private @NotNull List<SidebarLine> normalizeLines(@NotNull List<String> lineArray) {
        List<SidebarLine> lines = new ArrayList<>();

        int teamCount = 0;
        Language language = Language.getPlayerLanguage(player);
        String genericTeamFormat = language.m(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC);

        for (String line : lineArray) {

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
                    .replace("{server_ip}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
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
        providers.add(new PlaceholderProvider("{date}", () -> dateFormat.format(new Date(System.currentTimeMillis()))));

        PlayerLevel level = PlayerLevel.getLevelByPlayer(getPlayer().getUniqueId());
        if (null != level) {
            providers.add(new PlaceholderProvider("{progress}", level::getProgress));
            providers.add(new PlaceholderProvider("{level}", () -> String.valueOf(level.getLevel())));
            providers.add(new PlaceholderProvider("{currentXp}", level::getFormattedCurrentXp));
            providers.add(new PlaceholderProvider("{requiredXp}", level::getFormattedRequiredXp));
        }

        if (null == arena) {
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
            }
        } else {
            providers.add(new PlaceholderProvider("{on}", () -> String.valueOf(arena.getPlayers().size())));
            providers.add(new PlaceholderProvider("{max}", () -> String.valueOf(arena.getMaxPlayers())));
            providers.add(new PlaceholderProvider("{nextEvent}", this::getNextEventName));
            providers.add(new PlaceholderProvider("{time}", () -> {
                if (this.arena.getStatus() == GameState.playing || this.arena.getStatus() == GameState.restarting) {
                    return getNextEventTime();
                } else {
                    if (this.arena.getStatus() == GameState.starting) {
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
            tabList.forEach((k, v) -> {
                String encodedName = Base64.getEncoder().encodeToString(k.getBytes(StandardCharsets.UTF_8));
                handle.removeTab(encodedName);
            });
        }

        if (this.isTabFormattingDisabled()) {
            return;
        }

        handleHealthIcon();

        if (arena == null) {
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

        arena.getPlayers().forEach(playing -> giveUpdateTabFormat(playing, true));
        arena.getSpectators().forEach(spectating -> giveUpdateTabFormat(spectating, true));
    }

    /**
     * Handle given player in sidebar owner tab list.
     * Will remove existing tab and give a new one based on game conditions list like spectator, team red, etc.
     */
    public void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck) {
        // if sidebar was not created
        if (handle == null) {
            return;
        }

        // unique tab list name
        String tabListName = Base64.getEncoder().encodeToString(
                player.getUniqueId().toString().getBytes(StandardCharsets.UTF_8)
        );
        Language lang = Language.getPlayerLanguage(player);

        if (tabList.containsKey(tabListName)) {
            handle.removeTab(tabListName);
            tabList.remove(tabListName);
            SidebarManager.getInstance().sendHeaderFooter(player, "", "");
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

            SidebarManager.getInstance().sendHeaderFooter(
                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY),
                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY)
            );
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

            SidebarManager.getInstance().sendHeaderFooter(
                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR),
                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR)
            );
            return;
        }

        if (arena.getStatus() != GameState.playing) {
            if (arena.getStatus() == GameState.waiting) {
                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, player, null);
                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, player, null);
                SidebarManager.getInstance().sendHeaderFooter(
                        player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING),
                        lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING)
                );
            } else if (arena.getStatus() == GameState.starting) {
                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, player, null);
                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, player, null);
                SidebarManager.getInstance().sendHeaderFooter(
                        player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING),
                        lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING)
                );
            } else if (arena.getStatus() == GameState.restarting) {
                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, player, null);
                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING, player, null);
                SidebarManager.getInstance().sendHeaderFooter(
                        player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING),
                        lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING)
                );
            } else {
                throw new RuntimeException("Unhandled game status!");
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

        String tabName = Base64.getEncoder().encodeToString((TEAM_PREFIX + team.getName()).getBytes(StandardCharsets.UTF_8));

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
        }

        teamTab.add(player);

        SidebarManager.getInstance().sendHeaderFooter(
                player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING),
                lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING)
        );
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
    private boolean isTabFormattingDisabled() {
        if (null == arena) {

            if (getServerType() == ServerType.SHARED) {
                if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                    return null != lobby;
                }
            }

            return config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
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
        if (arena.getStatus() == GameState.restarting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING)) {
            return false;
        }

        return true;
    }

    public void handleHealthIcon() {
        if (null == handle) {
            return;
        }

        if (null == arena) {
            handle.hidePlayersHealth();
        } else if (arena.getStatus() != GameState.playing) {
            handle.hidePlayersHealth();
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

        handle.showPlayersHealth(line, config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_IN_TAB));
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
     * @param toggle true when applied, false when expired.
     */
    public void handleInvisibilityPotion(@NotNull Player player, boolean toggle) {
        if (toggle) {
//            handle.remove(player);
//            handle.playerListHideNameTag(player);
            //todo the new sidebar does not provide a solution for invisibility potion?!!
        } else {
//            this.giveUpdateTabFormat(player, false);
//            handle.playerListRestoreNameTag(player);
        }
    }

    public Sidebar getHandle() {
        return handle;
    }

    public IArena getArena() {
        return arena;
    }
}
