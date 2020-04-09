package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.stats.PlayerStats;
import com.andrei1058.bedwars.stats.StatsManager;
import com.andrei1058.spigot.sidebar.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.*;
import static com.andrei1058.bedwars.arena.Misc.replaceStatsPlaceholders;

public class BedWarsScoreboard {

    private static SidebarManager sidebarManager = null;

    private Sidebar handle;
    private IArena arena;
    private static ConcurrentHashMap<UUID, BedWarsScoreboard> scoreboards = new ConcurrentHashMap<>();
    private Player player;
    private SimpleDateFormat dateFormat;

    private BedWarsScoreboard(@NotNull Player p, @NotNull List<String> content, @Nullable IArena arena) {
        if (content.isEmpty()) return;
        if (sidebarManager == null) {
            try {
                sidebarManager = new SidebarManager();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        this.arena = arena;

        BedWarsScoreboard sbb = scoreboards.get(p.getUniqueId());
        if (sbb != null) {
            sbb.remove();
        }

        this.player = p;

        LinkedList<PlaceholderProvider> placeholders = new LinkedList<>();
        placeholders.add(new PlaceholderProvider("{on}", () -> {
            if (this.arena == null) {
                return String.valueOf(Bukkit.getOnlinePlayers().size());
            }
            return String.valueOf(this.arena.getPlayers().size());
        }));
        placeholders.add(new PlaceholderProvider("{max}", () -> {
            if (this.arena == null) {
                return String.valueOf(Bukkit.getMaxPlayers());
            }
            return String.valueOf(this.arena.getMaxPlayers());
        }));
        placeholders.add(new PlaceholderProvider("{time}", () -> {
            if (this.arena == null) {
                return new SimpleDateFormat(getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis()));
            } else if (this.arena.getStatus() != GameState.playing) {
                if (this.arena.getStatus() == GameState.starting) {
                    if (getArena().getStartingTask() != null) {
                        return String.valueOf(getArena().getStartingTask().getCountdown() + 1);
                    }
                }
                return new SimpleDateFormat(getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis()));
            }
            return getNextEventTime();
        }));
        placeholders.add(new PlaceholderProvider("{nextEvent}", this::getNextEventName));
        placeholders.add(new PlaceholderProvider("{date}", () -> new SimpleDateFormat(getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis()))));

        placeholders.add(new PlaceholderProvider("{kills}", () -> {
            if (this.arena == null) {
                PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
                return String.valueOf(stats.getKills());
            }
            return String.valueOf(this.arena.getPlayerKills(getPlayer(), false));
        }));
        placeholders.add(new PlaceholderProvider("{finalKills}", () -> {
            if (this.arena == null) {
                PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
                return String.valueOf(stats.getFinalKills());
            }
            return String.valueOf(this.arena.getPlayerKills(getPlayer(), true));
        }));
        placeholders.add(new PlaceholderProvider("{beds}", () -> {
            if (this.arena == null) {
                PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
                return String.valueOf(stats.getBedsDestroyed());
            }
            return String.valueOf(this.arena.getPlayerBedsDestroyed(getPlayer()));
        }));
        placeholders.add(new PlaceholderProvider("{deaths}", () -> {
            if (this.arena == null) {
                PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
                return String.valueOf(stats.getDeaths());
            }
            return String.valueOf(this.arena.getPlayerDeaths(getPlayer(), false));
        }));
        placeholders.add(new PlaceholderProvider("{progress}", () -> BedWars.getLevelSupport().getProgressBar(getPlayer())));
        placeholders.add(new PlaceholderProvider("{level}", () -> BedWars.getLevelSupport().getLevel(getPlayer())));
        placeholders.add(new PlaceholderProvider("{currentXp}", () -> BedWars.getLevelSupport().getCurrentXpFormatted(getPlayer())));
        placeholders.add(new PlaceholderProvider("{requiredXp}", () -> BedWars.getLevelSupport().getRequiredXpFormatted(getPlayer())));

        this.handle = sidebarManager.createSidebar(new SidebarLine() {
            @NotNull
            @Override
            public String getLine() {
                return "BedWars1058";
            }
        }, new ArrayList<>(), placeholders);

        dateFormat = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));

        this.setStrings(content);
        handle.apply(getPlayer());

        if (!p.isOnline()) {
            remove();
            return;
        }

        /* not ready
        if (arena != null) {
            if (arena.getStatus() == GameState.playing) {
                addHealthIcon();
                giveTeamColorTag();
                p.damage(0.2);
            }
        }
        */
    }

    public void setArena(IArena arena) {
        this.arena = arena;
    }

    private void setStrings(@NotNull List<String> strings) {
        scoreboards.remove(player.getUniqueId());
        while (handle.linesAmount() > 0) {
            handle.removeLine(0);
        }
        List<String> toRemove = new ArrayList<>();
        handle.getPlaceholders().forEach(c -> {
            if (c.getPlaceholder().startsWith("{Team")) {
                toRemove.add(c.getPlaceholder());
            }
        });
        toRemove.forEach(c -> handle.removePlaceholder(c));
        String title = strings.get(0);
        strings.remove(0);
        handle.setTitle(new SidebarLine() {
            @NotNull
            @Override
            public String getLine() {
                return title;
            }
        });
        for (String temp : strings) {
            temp = temp.replace("{server_ip}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{server}", config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
            if (arena != null) {
                if (arena.getStatus() == GameState.playing) {
                    addHealthIcon();

                    for (ITeam teams : arena.getTeams()) {
                        handle.addPlaceholder(new PlaceholderProvider("{Team" + teams.getName() + "Status}", () -> (teams.isBedDestroyed() ? teams.getSize() > 0 ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED).replace("{remainingPlayers}",
                                String.valueOf(teams.getSize())) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE)) + (teams.isMember(getPlayer()) ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM) : "")));
                        final ChatColor color = teams.getColor().chat();

                        teams.getMembers().forEach(c -> {
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
                temp = temp.replace("{money}", String.valueOf(getEconomy().getMoney(player))).replace("{player}", player.getName());
                temp = replaceStatsPlaceholders(getPlayer(), temp, true);
            } else {
                if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                    for (ITeam team : arena.getTeams()) {
                        temp = temp.replace("{Team" + team.getName() + "Color}", team.getColor().chat().toString()).replace("{Team" + team.getName() + "Name}",
                                team.getDisplayName(Language.getPlayerLanguage(getPlayer()))).replace("{Team" + team.getName() + "Status}", (team.isBedDestroyed() ? team.getSize() > 0 ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED).replace("{remainingPlayers}",
                                String.valueOf(team.getSize())) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED) : getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE)) + (team.isMember(getPlayer()) ? getMsg(getPlayer(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM) : ""));
                    }
                    temp = temp.replace("{map}", arena.getDisplayName())
                            .replace("{player}", player.getDisplayName())
                            .replace("{money}", String.valueOf(getEconomy().getMoney(player)));
                }
                temp = temp.replace("{map}", arena.getDisplayName())
                        .replace("{player}", player.getName())
                        .replace("{money}", String.valueOf(getEconomy().getMoney(player)))
                        .replace("{group}", arena.getDisplayGroup(player));
            }


            String finalTemp = temp;
            SidebarLine sidebarLine = new SidebarLine() {
                @NotNull
                @Override
                public String getLine() {
                    return finalTemp;
                }
            };
            handle.addLine(sidebarLine);
        }
        scoreboards.put(player.getUniqueId(), this);
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

    public void giveTeamColorTag() {
        /*if (scoreboard == null){
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
        }*/
    }

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

    public static ConcurrentHashMap<UUID, BedWarsScoreboard> getScoreboards() {
        return scoreboards;
    }

    public static BedWarsScoreboard getSBoard(UUID player) {
        return scoreboards.getOrDefault(player, null);
    }

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

        return new String[]{st, dateFormat.format((time))};
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
        if (!(arena instanceof Arena)) return dateFormat.format((0L));
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

        return dateFormat.format((time));
    }

    /**
     * Update spectators for player.
     */
    public void updateSpectator(Player p, boolean value) {
        /*if (getArena() == null) return;
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
        }*/
    }

    public void invisibilityPotion(@NotNull ITeam team, Player player, boolean trueRemoveAddFalse) {
        //todo I think it's still required, not sure
        /*Team t = sb.getTeam(team.getName());
        if (t != null) {
            if (trueRemoveAddFalse) t.removeEntry(player.getName());
            else t.addEntry(player.getName());
        }*/
    }

    /**
     * Give game scoreboard.
     *
     * @param p     target player.
     * @param arena target arena.
     */
    public static void giveScoreboard(@NotNull Player p, IArena arena, boolean delay) {
        BedWarsScoreboard sb;
        List<String> lines;
        if (arena == null) {
            if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) return;
            lines = Language.getList(p, Messages.SCOREBOARD_LOBBY);
            sb = BedWarsScoreboard.getSBoard(p.getUniqueId());
        } else {
            if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_GAME_SCOREBOARD)) return;
            lines = new ArrayList<>();
            sb = BedWarsScoreboard.getSBoard(p.getUniqueId());
            if (arena.getStatus() == GameState.waiting) {
                lines.addAll(getScoreboard(p, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING));
            } else if (arena.getStatus() == GameState.starting) {
                lines.addAll(getScoreboard(p, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING));
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                lines.addAll(getScoreboard(p, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING));
            }
        }
        if (!lines.isEmpty()) {
            if (delay && sb == null) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    if (p.isOnline()) {
                        new BedWarsScoreboard(p, lines, arena);
                    }
                }, 5L);
            } else {
                if (sb == null) {
                    new BedWarsScoreboard(p, lines, arena);
                } else {
                    sb.setArena(arena);
                    sb.setStrings(lines);
                    if (sb.getArena() == null) {
                        sb.handle.hidePlayersHealth();
                    } else {
                        if (sb.getArena().getStatus() != GameState.playing) {
                            sb.handle.hidePlayersHealth();
                        }
                    }
                }
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
