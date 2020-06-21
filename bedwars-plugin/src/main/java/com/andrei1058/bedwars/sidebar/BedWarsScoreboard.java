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
import com.andrei1058.spigot.sidebar.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;
import static com.andrei1058.bedwars.api.language.Language.getScoreboard;
import static com.andrei1058.bedwars.arena.Misc.replaceStatsPlaceholders;

public class BedWarsScoreboard {

    private static SidebarManager sidebarManager = null;
    private static HashMap<UUID, BedWarsScoreboard> scoreboards = new HashMap<>();

    private final Player player;
    private IArena arena;

    private Sidebar handle;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat nextEventDateFormat;

    private BedWarsScoreboard(@NotNull Player player, @NotNull List<String> content, @Nullable IArena arena) {
        this.arena = arena;
        this.player = player;

        // Ignore if content is empty
        if (content.isEmpty()) return;

        // Remove previous player scoreboard
        BedWarsScoreboard previousScoreboard = scoreboards.get(player.getUniqueId());
        if (previousScoreboard != null) {
            previousScoreboard.remove();
        }

        if (!player.isOnline()) {
            return;
        }

        // Cache the next event date format
        nextEventDateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        dateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_DATE));

        // Define common placeholders
        List<PlaceholderProvider> placeholders = Arrays.asList(
                new PlaceholderProvider("{on}", () -> String.valueOf(getArena() == null ? Bukkit.getOnlinePlayers().size() : getArena().getPlayers().size())),
                new PlaceholderProvider("{max}", () -> String.valueOf(getArena() == null ? Bukkit.getMaxPlayers() : getArena().getMaxPlayers())),
                new PlaceholderProvider("{time}", () -> {
                    if (this.arena == null) {
                        return dateFormat.format(new Date(System.currentTimeMillis()));
                    } else if (this.arena.getStatus() == GameState.playing || this.arena.getStatus() == GameState.restarting) {
                        return getNextEventTime();
                    } else {
                        if (this.arena.getStatus() == GameState.starting) {
                            if (getArena().getStartingTask() != null) {
                                return String.valueOf(getArena().getStartingTask().getCountdown() + 1);
                            }
                        }
                        return dateFormat.format(new Date(System.currentTimeMillis()));
                    }
                }),
                new PlaceholderProvider("{nextEvent}", this::getNextEventName),
                new PlaceholderProvider("{date}", () -> dateFormat.format(new Date(System.currentTimeMillis()))),
                new PlaceholderProvider("{kills}", () -> String.valueOf(getArena() == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getKills() : getArena().getPlayerKills(getPlayer(), false))),
                new PlaceholderProvider("{finalKills}", () -> String.valueOf(getArena() == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getFinalKills() : getArena().getPlayerKills(getPlayer(), true))),
                new PlaceholderProvider("{beds}", () -> String.valueOf(getArena() == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getBedsDestroyed() : getArena().getPlayerBedsDestroyed(getPlayer()))),
                new PlaceholderProvider("{deaths}", () -> String.valueOf(getArena() == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getDeaths() : getArena().getPlayerDeaths(getPlayer(), false))),
                new PlaceholderProvider("{progress}", () -> BedWars.getLevelSupport().getProgressBar(getPlayer())),
                new PlaceholderProvider("{level}", () -> BedWars.getLevelSupport().getLevel(getPlayer())),
                new PlaceholderProvider("{currentXp}", () -> BedWars.getLevelSupport().getCurrentXpFormatted(getPlayer())),
                new PlaceholderProvider("{requiredXp}", () -> BedWars.getLevelSupport().getRequiredXpFormatted(getPlayer()))
        );

        // Initialize sidebar manager if not initialized
        if (sidebarManager == null) {
            try {
                sidebarManager = new SidebarManager();
            } catch (InstantiationException e) {
                throw new IllegalStateException(e);
            }
        }

        // Create the sidebar, with a title but empty content
        // Note: we can set the title to null as we set it in setStrings and the first update is sent on handle.apply(player)
        handle = sidebarManager.createSidebar(null, Collections.emptyList(), placeholders);

        // this needs to be before setStrings
        scoreboards.put(player.getUniqueId(), this);
        // Set lines
        setStrings(content);

        // Apply the sidebar to the player
        handle.apply(player);

        /* not sure if still required. but I think not
        if (arena != null) {
            if (arena.getStatus() == GameState.playing) {
                addHealthIcon();
                giveTeamColorTag();
                player.damage(0.2);
            }
        }
        */
    }

    public void setArena(IArena arena) {
        this.arena = arena;
    }

    private void setStrings(@NotNull List<String> strings) {
        // Remove existing lines
        while (handle.linesAmount() > 0) {
            handle.removeLine(0);
        }

        // Remove team/game related placeholders
        List<String> placeholdersToRemove = new LinkedList<>();
        handle.getPlaceholders().forEach(placeholder -> {
            if (placeholder.getPlaceholder().startsWith("{Team")) {
                placeholdersToRemove.add(placeholder.getPlaceholder());
            }
        });
        placeholdersToRemove.forEach(placeholder -> handle.removePlaceholder(placeholder));

        // Set the title
        String[] title = strings.remove(0).split("[\\n,]");
        if (title.length == 1) {
            handle.setTitle(new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return title[0];
                }
            });
        } else {
            handle.setTitle(new SidebarLineAnimated(title));
        }

        //remove previous list formatting
        handle.playerListClear();

        // Update player list and health bars
        if (arena != null) {
            addHealthIcon();
            arena.getPlayers().forEach(player -> handle.refreshHealth(player, (int) player.getHealth()));

            // Dynamic team placeholders
            for (ITeam currentTeam : arena.getTeams()) {
                handle.addPlaceholder(new PlaceholderProvider("{Team" + currentTeam.getName() + "Status}", () -> {
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

            if ((arena.getStatus() == GameState.playing && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING))
                    || (arena.getStatus() == GameState.restarting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING))) {
                String prefixListPath = arena.getStatus() == GameState.playing ? Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING : Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PRESTARTING;
                String suffixListPath = arena.getStatus() == GameState.playing ? Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING : Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PRESTARTING;

                // format current teams in tab
                for (ITeam currentTeam : arena.getTeams()) {
                    currentTeam.getMembers().forEach(currentMember -> addToTabList(currentMember, prefixListPath, suffixListPath));
                }
                // format spectators in tab for current spectator
                if (arena.isSpectator(getPlayer())) {
                    arena.getSpectators().forEach(spectator -> {
                        addToTabList(spectator, prefixListPath, suffixListPath);
                        BedWarsScoreboard currentSpectatorScoreboard = getSBoard(spectator.getUniqueId());
                        if (currentSpectatorScoreboard != null) {
                            // add current player to current spectator tab list
                            currentSpectatorScoreboard.addToTabList(getPlayer(), prefixListPath, suffixListPath);
                        }
                    });
                }
            } else {
                // waiting/ starting tab formatting
                if (arena.getStatus() == GameState.waiting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING) ||
                        arena.getStatus() == GameState.starting && config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING)) {
                    String prefixListPath = arena.getStatus() == GameState.waiting ? Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING : Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING;
                    String suffixListPath = arena.getStatus() == GameState.waiting ? Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING : Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING;
                    arena.getPlayers().forEach(player -> addToTabList(player, prefixListPath, suffixListPath));
                }
            }
        } else {
            // multi-arena lobby tab formatting
            if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY)) {
                World lobbyWorld = Bukkit.getWorld(config.getLobbyWorldName());
                if (lobbyWorld != null) {
                    lobbyWorld.getPlayers().forEach(player -> {
                        addToTabList(player, Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY);
                        BedWarsScoreboard currentPlayerScoreboard = getSBoard(player.getUniqueId());
                        if (currentPlayerScoreboard != null) {
                            currentPlayerScoreboard.addToTabList(getPlayer(), Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY);
                        }
                    });
                }
            }
        }

        for (String current : strings) {
            // General static placeholders
            current = current
                    .replace("{server_ip}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{server}", config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID))
                    .replace("{player}", player.getDisplayName())
                    .replace("{money}", String.valueOf(getEconomy().getMoney(player)));

            if (arena == null) {
                // Lobby scoreboard
                current = replaceStatsPlaceholders(getPlayer(), current, true);
            } else {
                // Game scoreboard
                current = current
                        .replace("{map}", arena.getDisplayName())
                        .replace("{group}", arena.getDisplayGroup(player));

                for (ITeam currentTeam : arena.getTeams()) {
                    final ChatColor color = currentTeam.getColor().chat();
                    // Static team placeholders
                    current = current
                            .replace("{Team" + currentTeam.getName() + "Color}", color.toString())
                            .replace("{Team" + currentTeam.getName() + "Name}", currentTeam.getDisplayName(Language.getPlayerLanguage(getPlayer())));

                }
            }

            // Add the line to the sidebar
            String finalTemp = current;
            SidebarLine sidebarLine = new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return finalTemp;
                }
            };
            handle.addLine(sidebarLine);
        }

    }

    // alter scoreboard list
    // must be used when a player joins/ re-joins/ invisibility potion has expired
    public void addToTabList(Player player, @NotNull String prefixListPath, @NotNull String suffixListPath) {
        handle.playerListCreate(player, getTeamListText(prefixListPath, player), getTeamListText(suffixListPath, player));
        if (arena != null) {
            handle.playerListAddPlaceholders(player,
                    new PlaceholderProvider("{team}", () -> {
                        if (arena.isSpectator(player)) {
                            return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR) + Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                        }
                        ITeam team = arena.getTeam(player);
                        if (team == null) {
                            team = arena.getExTeam(player.getUniqueId());
                        }
                        if (team == null) {
                            //waiting/ starting phase
                            return "";
                        }
                        return team.getColor().chat() + team.getDisplayName(Language.getPlayerLanguage(getPlayer()));
                    }),
                    new PlaceholderProvider("{teamLetter}", () -> {
                        if (arena.isSpectator(player)) {
                            return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM).substring(0, 1);
                        }
                        ITeam team = arena.getTeam(player);
                        if (team == null) {
                            team = arena.getExTeam(player.getUniqueId());
                        }
                        if (team == null) {
                            //waiting/ starting phase
                            return "";
                        }
                        return team.getDisplayName(Language.getPlayerLanguage(getPlayer())).substring(0, 1);
                    }),
                    new PlaceholderProvider("{teamName}", () -> {
                        if (arena.isSpectator(player)) {
                            return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                        }
                        ITeam team = arena.getTeam(player);
                        if (team == null) {
                            team = arena.getExTeam(player.getUniqueId());
                        }
                        if (team == null) {
                            //waiting/ starting phase
                            return "";
                        }
                        return team.getDisplayName(Language.getPlayerLanguage(getPlayer()));
                    }),
                    new PlaceholderProvider("{teamColor}", () -> {
                        if (arena.isSpectator(player)) {
                            return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR);
                        }
                        ITeam team = arena.getTeam(player);
                        if (team == null) {
                            team = arena.getExTeam(player.getUniqueId());
                        }
                        if (team == null) {
                            //waiting/ starting phase
                            return "";
                        }
                        return team.getColor().chat().toString();
                    }));
        }
    }

    public void addHealthIcon() {
        if (handle != null) {
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
        }
    }

    /* I think this is no longer required
    public void giveTeamColorTag() {
        if (scoreboard == null){
            scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        }
        if (scoreboard.getObjective("list") == null) {
            Objective objective = scoreboard.registerNewObjective("list", "health");
            objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        }
        for (ITeam t : arena.getTeams()) {
            Team team;
            if (scoreboard.getTeam(t.getName()) == null) {
                team = sb.registerNewTeam(t.getName());
            } else {
                team = sb.getTeam(t.getName());
            }
            team.setPrefix(t.getColor().chat() + "" + ChatColor.BOLD + t.getName().substring(0, 1).toUpperCase() + ChatColor.RESET + " " + t.getColor().chat());
            for (Player p : t.getMembers()) {
                team.addEntry(p.getName());
            }
        /
    }
    */

    public Player getPlayer() {
        return player;
    }

    public void remove() {
        scoreboards.remove(getPlayer().getUniqueId());
        if (handle != null) {
            handle.remove(player.getUniqueId());
            handle = null;

            // remove player list formatting from other scoreboards
            getScoreboards().values().forEach(scoreboard -> scoreboard.handle.playerListRemove(getPlayer().getName()));
        }
    }

    public IArena getArena() {
        return arena;
    }

    public static Map<UUID, BedWarsScoreboard> getScoreboards() {
        return scoreboards;
    }

    public static BedWarsScoreboard getSBoard(UUID player) {
        return scoreboards.get(player);
    }

    /* TODO: unused
    public static String formatGenTimer(int duration) {
        long absSeconds = Math.abs((long) duration);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return (long) duration < 0 ? "-" + positive : positive;
    }

    private String[] getNextEvent() {
        if (!(arena instanceof Arena)) return new String[]{"null", "null"};
        Arena arena = (Arena) this.arena;
        long time = 0L;
        String st = "";
        switch (arena.getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_EMERALD_UPGRADE_II);
                time = (arena.upgradeEmeraldsCount) * 1000L;
                break;
            case EMERALD_GENERATOR_TIER_III:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_EMERALD_UPGRADE_III);
                time = (arena.upgradeEmeraldsCount) * 1000L;
                break;
            case DIAMOND_GENERATOR_TIER_II:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_II);
                time = (arena.upgradeDiamondsCount) * 1000L;
                break;
            case DIAMOND_GENERATOR_TIER_III:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_III);
                time = (arena.upgradeDiamondsCount) * 1000L;
                break;
            case GAME_END:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_GAME_END);
                time = (arena.getPlayingTask().getGameEndCountdown()) * 1000L;
                break;
            case BEDS_DESTROY:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_BEDS_DESTROY);
                time = (arena.getPlayingTask().getBedsDestroyCountdown()) * 1000L;
                break;
            case ENDER_DRAGON:
                st = getMsg(getPlayer(), Messages.NEXT_EVENT_DRAGON_SPAWN);
                time = (arena.getPlayingTask().getDragonSpawnCountdown()) * 1000L;
                break;
        }

        return new String[]{st, nextEventDateFormat.format((time))};
    }
    */

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

        return nextEventDateFormat.format((time));
    }

    /**
     * Update spectators for player.
     */
    public void updateSpectator(Player player, boolean value) {
        /* TODO: not ready
        if (getArena() == null) return;
        if (getArena().getTeam(p) != null) return;
        Team collide;
        if (sb.getTeam("spectators") == null) {
            collide = sb.registerNewTeam("spectators");
            nms.teamCollideRule(collide);
            collide.setPrefix("§7[SPECT] §r");
        } else {
            collide = sb.getTeam("spectators");
        }
        if (!value) {
            if (!collide.hasEntry(p.getName())) collide.addEntry(p.getName());
        } else {
            if (collide.hasEntry(p.getName())) collide.removeEntry(p.getName());
        }
        */
    }

    /**
     * This will remove the player name tag when he drinks a potion.
     *
     * @param toggle true when applied, false when expired.
     */
    public void invisibilityPotion(@NotNull ITeam team, Player player, boolean toggle) {
        if (toggle){
            handle.playerListHideNameTag(player);
        } else {
            handle.playerListRestoreNameTag(player);
        }
    }

    /**
     * Give game scoreboard.
     *
     * @param player target player.
     * @param arena  target arena.
     */
    public static void giveScoreboard(@NotNull Player player, IArena arena, boolean delay) {
        if (!player.isOnline()) return;
        BedWarsScoreboard scoreboard = BedWarsScoreboard.getSBoard(player.getUniqueId());
        List<String> lines = null;

        if (arena == null) {
            // Lobby scoreboard
            if (getServerType() == ServerType.SHARED) return;
            if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR)) {
                if (scoreboard != null) {
                    scoreboard.remove();
                }
                return;
            }
            lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
        } else {
            // Game scoreboard
            if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR)) {
                if (scoreboard != null) {
                    scoreboard.remove();
                }
                return;
            }
            if (arena.getStatus() == GameState.waiting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
            } else if (arena.getStatus() == GameState.starting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING);
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
            }
        }

        if (lines == null || lines.isEmpty()) {
            if (scoreboard != null) {
                scoreboard.remove();
            }
            return;
        }

        if (scoreboard == null) {
            if (delay) {
                List<String> finalLines = lines;
                Bukkit.getScheduler().runTaskLater(plugin, () -> new BedWarsScoreboard(player, finalLines, arena), 5L);
            } else {
                new BedWarsScoreboard(player, lines, arena);
            }
        } else {
            scoreboard.setArena(arena);
            scoreboard.setStrings(lines);
            if (arena != null && arena.getStatus() != GameState.playing) {
                scoreboard.handle.hidePlayersHealth();
            }
        }
    }

    public Sidebar getHandle() {
        return handle;
    }

    @NotNull
    private SidebarLine getTeamListText(String path, Player targetPlayer) {
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
            strings.add(string.replace("{vPrefix}", BedWars.getChatSupport().getPrefix(targetPlayer)).replace("{vSuffix}", BedWars.getChatSupport().getSuffix(targetPlayer)));
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
}
