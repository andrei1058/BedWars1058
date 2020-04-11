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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Struct;
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

        if(!player.isOnline()) {
            return;
        }

        // Cache the next event date format
        nextEventDateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        dateFormat = new SimpleDateFormat(getMsg(player, Messages.FORMATTING_SCOREBOARD_DATE));

        // Define common placeholders
        List<PlaceholderProvider> placeholders = Arrays.asList(
                new PlaceholderProvider("{on}", () -> String.valueOf(arena == null ? Bukkit.getOnlinePlayers().size() : arena.getPlayers().size())),
                new PlaceholderProvider("{max}", () -> String.valueOf(arena == null ? Bukkit.getMaxPlayers() : arena.getMaxPlayers())),
                new PlaceholderProvider("{time}", () -> {
                    if (this.arena == null) {
                        return dateFormat.format(new Date(System.currentTimeMillis()));
                    } else if (this.arena.getStatus() != GameState.playing) {
                        if (this.arena.getStatus() == GameState.starting) {
                            if (getArena().getStartingTask() != null) {
                                return String.valueOf(getArena().getStartingTask().getCountdown() + 1);
                            }
                        }
                        return dateFormat.format(new Date(System.currentTimeMillis()));
                    }
                    return getNextEventTime();
                }),
                new PlaceholderProvider("{nextEvent}", this::getNextEventName),
                new PlaceholderProvider("{date}", () -> dateFormat.format(new Date(System.currentTimeMillis()))),
                new PlaceholderProvider("{kills}", () -> String.valueOf(arena == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getKills() : arena.getPlayerKills(getPlayer(), false))),
                new PlaceholderProvider("{finalKills}", () -> String.valueOf(arena == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getFinalKills() : arena.getPlayerKills(getPlayer(), true))),
                new PlaceholderProvider("{beds}", () -> String.valueOf(arena == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getBedsDestroyed() : arena.getPlayerBedsDestroyed(getPlayer()))),
                new PlaceholderProvider("{deaths}", () -> String.valueOf(arena == null ? BedWars.getStatsManager().get(getPlayer().getUniqueId()).getDeaths() : arena.getPlayerDeaths(getPlayer(), false))),
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

        // Set lines
        setStrings(content);

        // Apply the sidebar to the player
        handle.apply(player);

        /* TODO: not ready
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
        //scoreboards.remove(player.getUniqueId()); TODO: shouldn't be needed

        // Remove existing lines
        while (handle.linesAmount() > 0) {
            handle.removeLine(0);
        }

        // TODO: config
        if (arena == null) {
            handle.playerListClear();
        }

        // Remove team/game related placeholders
        handle.getPlaceholders().removeIf(placeholder -> placeholder.getPlaceholder().startsWith("{Team"));

        // Set the title
        String title = strings.remove(0);
        handle.setTitle(new SidebarLine() {
            @NotNull
            @Override
            public String getLine() {
                return title;
            }
        });

        for (String current : strings) {
            // General static placeholders
            current = current
                    .replace("{server_ip}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{server}", config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));

            if (arena != null) {
                if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                    addHealthIcon();

                    for (ITeam teams : arena.getTeams()) {
                        handle.addPlaceholder(new PlaceholderProvider("{Team" + teams.getName() + "Status}", () -> (teams.isBedDestroyed() ? teams.getSize() > 0 ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED).replace("{remainingPlayers}",
                                String.valueOf(teams.getSize())) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE)) + (teams.isMember(getPlayer()) ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM) : "")));
                        final ChatColor color = teams.getColor().chat();

                        teams.getMembers().forEach(c -> {
                            handle.refreshHealth(c, (int) c.getHealth());
                            handle.playerListCreate(c, color, getTeamListText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, c), getTeamListText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, c));
                            handle.playerListAddPlaceholders(c, new PlaceholderProvider("{team}", () -> {
                                        if (arena == null) {
                                            return "";
                                        } else {
                                            if (arena.isSpectator(c)) {
                                                return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR) + Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                                            } else {
                                                ITeam team = arena.getTeam(c);
                                                if (team == null) {
                                                    team = arena.getExTeam(c.getUniqueId());
                                                }
                                                if (team == null) {
                                                    return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR) + Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                                                }
                                                return team.getColor().chat() + team.getDisplayName(Language.getPlayerLanguage(getPlayer()));
                                            }
                                        }
                                    }),
                                    new PlaceholderProvider("{teamLetter}", () -> {
                                        if (arena == null) {
                                            return "";
                                        } else {
                                            if (arena.isSpectator(c)) {
                                                return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM).substring(0, 1);
                                            } else {
                                                ITeam team = arena.getTeam(c);
                                                if (team == null) {
                                                    team = arena.getExTeam(c.getUniqueId());
                                                }
                                                if (team == null) {
                                                    return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM).substring(0, 1);
                                                }
                                                return team.getDisplayName(Language.getPlayerLanguage(getPlayer())).substring(0, 1);
                                            }
                                        }
                                    }),
                                    new PlaceholderProvider("{teamName}", () -> {
                                        if (arena == null) {
                                            return "";
                                        } else {
                                            if (arena.isSpectator(c)) {
                                                return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                                            } else {
                                                ITeam team = arena.getTeam(c);
                                                if (team == null) {
                                                    team = arena.getExTeam(c.getUniqueId());
                                                }
                                                if (team == null) {
                                                    return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_TEAM);
                                                }
                                                return team.getDisplayName(Language.getPlayerLanguage(getPlayer()));
                                            }
                                        }
                                    }),
                                    new PlaceholderProvider("{teamColor}", () -> {
                                        if (arena == null) {
                                            return "";
                                        } else {
                                            if (arena.isSpectator(c)) {
                                                return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR);
                                            } else {
                                                ITeam team = arena.getTeam(c);
                                                if (team == null) {
                                                    team = arena.getExTeam(c.getUniqueId());
                                                }
                                                if (team == null) {
                                                    return Language.getMsg(getPlayer(), Messages.FORMATTING_SPECTATOR_COLOR);
                                                }
                                                return team.getColor().chat().toString();
                                            }
                                        }
                                    }));
                        });
                    }
                }
            }
            if (arena == null) {
                current = current.replace("{money}", String.valueOf(getEconomy().getMoney(player))).replace("{player}", player.getName());
                current = replaceStatsPlaceholders(getPlayer(), current, true);
            } else {
                if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                    for (ITeam team : arena.getTeams()) {
                        current = current.replace("{Team" + team.getName() + "Color}", team.getColor().chat().toString()).replace("{Team" + team.getName() + "Name}",
                                team.getDisplayName(Language.getPlayerLanguage(getPlayer())));
                    }
                    current = current.replace("{map}", arena.getDisplayName())
                            .replace("{player}", player.getDisplayName())
                            .replace("{money}", String.valueOf(getEconomy().getMoney(player)));
                }
                current = current.replace("{map}", arena.getDisplayName())
                        .replace("{player}", player.getName())
                        .replace("{money}", String.valueOf(getEconomy().getMoney(player)))
                        .replace("{group}", arena.getDisplayGroup(player));
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

        //scoreboards.put(player.getUniqueId(), this); TODO: shouldn't be needed
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
            handle.showPlayersHealth(line, false);
        }
    }

    /* TODO: not ready
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

    public void invisibilityPotion(@NotNull ITeam team, Player player, boolean trueRemoveAddFalse) {
        /* TODO: i think it's still required, not sure
        Team t = sb.getTeam(team.getName());
        if (t != null) {
            if (trueRemoveAddFalse) t.removeEntry(player.getName());
            else t.addEntry(player.getName());
        }
        */
    }

    /**
     * Give game scoreboard.
     *
     * @param player target player.
     * @param arena  target arena.
     */
    public static void giveScoreboard(@NotNull Player player, IArena arena, boolean delay) {
        BedWarsScoreboard scoreboard = BedWarsScoreboard.getSBoard(player.getUniqueId());
        List<String> lines = null;

        if (arena == null) {
            // Lobby scoreboard
            if (getServerType() == ServerType.SHARED) return;
            if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
                if (scoreboard != null) {
                    scoreboard.remove();
                }
                return;
            }
            lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
        } else {
            // Game scoreboard
            if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_GAME_SCOREBOARD)) {
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
