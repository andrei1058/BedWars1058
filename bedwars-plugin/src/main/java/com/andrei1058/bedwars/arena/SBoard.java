package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.*;
import static com.andrei1058.bedwars.arena.Misc.replaceStatsPlaceholders;

public class SBoard {

    private static ScoreboardManager sbm = Bukkit.getScoreboardManager();
    private List<String> placeholders = Arrays.asList("{on}", "{max}", "{time}", "{nextEvent}", "{date}");
    private static ConcurrentHashMap<UUID, SBoard> scoreboards = new ConcurrentHashMap<>();
    private HashMap<Team, String> toRefresh = new HashMap<>();
    private Scoreboard sb = sbm.getNewScoreboard();
    private Player p;
    private Objective o;
    private IArena arena;
    private SimpleDateFormat dateFormat;

    private SBoard(@NotNull Player p, @NotNull List<String> content, IArena arena) {
        SBoard sbb = scoreboards.get(p.getUniqueId());
        if (sbb != null) {
            sbb.remove();
        }

        this.p = p;
        o = sb.registerNewObjective("Sb", "or");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.arena = arena;
        dateFormat = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        if (this.arena != null) {
            placeholders = new ArrayList<>(placeholders);
            this.placeholders.add("{kills}");
            this.placeholders.add("{finalKills}");
            this.placeholders.add("{beds}");
            this.placeholders.add("{deaths}");
        } else {
            placeholders = new ArrayList<>(placeholders);
            this.placeholders.add("{progress}");
            this.placeholders.add("{level}");
            this.placeholders.add("{currentXp}");
            this.placeholders.add("{requiredXp}");
        }
        this.setStrings(content);
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
            if (!p.isOnline()) return;
            p.setScoreboard(sb);
            scoreboards.put(p.getUniqueId(), this);
        }, 10L);

        if (arena != null) {
            if (arena.getStatus() == GameState.playing) {
                addHealthIcon();
                giveTeamColorTag();
                p.damage(0.2);
            }
        }
    }

    /**
     * Used for spectators
     */
    private SBoard(@NotNull Player p, IArena arena) {
        this.p = p;
        o = sb.registerNewObjective("Sb", "or");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.arena = arena;
        dateFormat = new SimpleDateFormat(getMsg(p, Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        this.setStrings(getScoreboard(p, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING));
        Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
            if (!p.isOnline()) return;
            p.setScoreboard(sb);
            scoreboards.put(p.getUniqueId(), this);
            giveTeamColorTag();
            //show spectator tag in sb to other spectators
            for (SBoard sb : SBoard.getScoreboards().values()) {
                if (sb.getArena() == getArena()) {
                    sb.updateSpectator(sb.getP(), false);
                }
            }
        }, 10L);
    }

    public void setStrings(List<String> strings) {
        for (Team t1 : sb.getTeams()) {
            t1.unregister();
        }
        toRefresh.clear();
        o.setDisplayName(strings.get(0));
        int score = 0;
        for (int x = strings.size(); x > 1; x--) {
            Team t = sb.registerNewTeam("team" + x);
            t.addEntry(ChatColor.values()[x - 1].toString());
            o.getScore(ChatColor.values()[x - 1].toString()).setScore(score);
            score++;
            String temp = strings.get(x - 1);
            temp = temp.replace("{generatorUpgrade}", "{nextEvent}")
                    .replace("{generatorTimer}", "{time}");
            temp = temp.replace("{level}", BedWars.getLevelSupport().getLevel(p));
            temp = temp.replace("{progress}", BedWars.getLevelSupport().getProgressBar(p));
            temp = temp.replace("{currentXp}", BedWars.getLevelSupport().getCurrentXpFormatted(p));
            temp = temp.replace("{requiredXp}", BedWars.getLevelSupport().getRequiredXpFormatted(p));
            temp = temp.replace("{server_ip}", BedWars.config.getString(ConfigPath.GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP))
                    .replace("{version}", plugin.getDescription().getVersion())
                    .replace("{server}", config.getString(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID));
            for (String ph : placeholders) {
                if (temp.contains(ph)) {
                    if (!toRefresh.containsKey(t)) {
                        toRefresh.put(t, temp);
                    }
                }
            }
            if (arena != null) {
                for (ITeam teams : arena.getTeams()) {
                    if (temp.contains("{Team" + teams.getName() + "Status}")) {
                        if (!toRefresh.containsKey(t)) {
                            toRefresh.put(t, temp);
                        }
                    }
                }
            }
            if (arena == null) {

                temp = temp.replace("{on}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getServer().getMaxPlayers())).replace("{date}",
                                new SimpleDateFormat(getMsg(getP(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis())))
                        .replace("{money}", String.valueOf(getEconomy().getMoney(p))).replace("{progress}", PlayerLevel.getLevelByPlayer(p.getUniqueId()).getProgress())
                        .replace("{level}", PlayerLevel.getLevelByPlayer(p.getUniqueId()).getLevelName()).replace("{currentXp}", PlayerLevel.getLevelByPlayer(p.getUniqueId()).getFormattedCurrentXp())
                        .replace("{requiredXp}", PlayerLevel.getLevelByPlayer(p.getUniqueId()).getFormattedRequiredXp());

                setContent(t, replaceStatsPlaceholders(getP(), temp, false));

            } else if (arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) {
                String time = "0";
                if (getArena().getStartingTask() != null) {
                    time = String.valueOf(getArena().getStartingTask().getCountdown());
                }
                setContent(t, temp.replace("{map}", arena.getDisplayName())
                        .replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                        .replace("{time}", time).replace("{player}", p.getDisplayName())
                        .replace("{money}", String.valueOf(getEconomy().getMoney(p))).replace("{date}", new SimpleDateFormat(getMsg(getP(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis())))
                        .replace("{group}", arena.getGroup()).replace("{deaths}", String.valueOf(getArena().getPlayerDeaths(p, false))));
            } else if (arena.getStatus() == GameState.playing) {
                String[] ne = getNextEvent();
                for (ITeam team : arena.getTeams()) {
                    temp = temp.replace("{Team" + team.getName() + "Color}", team.getColor().chat().toString()).replace("{Team" + team.getName() + "Name}",
                            team.getDisplayName(Language.getPlayerLanguage(getP()))).replace("{Team" + team.getName() + "Status}", (team.isBedDestroyed() ? team.getSize() > 0 ? getMsg(getP(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED).replace("{remainingPlayers}",
                            String.valueOf(team.getSize())) : getMsg(getP(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED) : getMsg(getP(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE)) + (team.isMember(getP()) ? getMsg(getP(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM) : ""));
                }
                setContent(t, temp.replace("{map}", arena.getDisplayName())
                        .replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                        .replace("{player}", p.getDisplayName()).replace("{date}", new SimpleDateFormat(getMsg(getP(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis())))
                        .replace("{kills}", String.valueOf(arena.getPlayerKills(getP(), false))).replace("{finalKills}", String.valueOf(arena.getPlayerKills(getP(), true)))
                        .replace("{beds}", String.valueOf(arena.getPlayerBedsDestroyed(getP()))).replace("{time}", ne[1])
                        .replace("{nextEvent}", ne[0]).replace("{money}", String.valueOf(getEconomy().getMoney(p))).replace("{deaths}", String.valueOf(getArena().getPlayerDeaths(p, false))));
            }
        }
    }

    private void setContent(Team t, String s) {
        s = SupportPAPI.getSupportPAPI().replace(getP(), s);
        if (s.length() >= 16) {
            String prefix = s.substring(0, 16);
            String suffix;
            if (prefix.endsWith("&") || prefix.endsWith("§")) {
                prefix = prefix.substring(0, prefix.length() - 1);
                suffix = s.substring(prefix.length());
            } else if (prefix.substring(0, 15).endsWith("&") || prefix.substring(0, 15).endsWith("§")) {
                prefix = prefix.substring(0, prefix.length() - 2);
                suffix = s.substring(prefix.length());
            } else {
                suffix = ChatColor.getLastColors(prefix) + s.substring(prefix.length());
            }
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }
            t.setPrefix(prefix);
            t.setSuffix(suffix);
        } else {
            t.setPrefix(s);
            t.setSuffix("");
        }
    }


    public void refresh() {
        String date = new SimpleDateFormat(getMsg(getP(), Messages.FORMATTING_SCOREBOARD_DATE)).format(new Date(System.currentTimeMillis()));
        if (arena == null) {
            for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                setContent(e.getKey(), e.getValue().replace("{on}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getServer().getMaxPlayers())).replace("{date}", date));
            }
        } else {
            if (arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) {
                String time = "0";
                if (getArena().getStartingTask() != null) {
                    time = String.valueOf(getArena().getStartingTask().getCountdown());
                }
                for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                    setContent(e.getKey(), e.getValue().replace("{on}", String.valueOf(arena.getPlayers().size()))
                            .replace("{max}", String.valueOf(arena.getMaxPlayers()))
                            .replace("{time}", time).replace("{date}", date));
                }
            } else if (arena.getStatus() == GameState.playing) {
                if (getArena().getPlayingTask() == null) return;
                String kills = String.valueOf(arena.getPlayerKills(getP(), false)), finalKills = String.valueOf(arena.getPlayerKills(getP(), true)),
                        beds = String.valueOf(arena.getPlayerBedsDestroyed(getP()));
                String[] ne = getNextEvent();
                for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                    String text = e.getValue();
                    for (ITeam team : arena.getTeams()) {
                        text = text.replace("{Team" + team.getName() + "Color}", team.getColor().chat().toString())
                                .replace("{Team" + team.getName() + "Name}", team.getDisplayName(Language.getPlayerLanguage(getP())))
                                .replace("{Team" + team.getName() + "Status}", (team.isBedDestroyed() ? team.getSize() > 0 ? getMsg(getP(), Messages.FORMATTING_SCOREBOARD_BED_DESTROYED)
                                        .replace("{remainingPlayers}", String.valueOf(team.getSize())) : getMsg(getP(), Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED) : getMsg(getP(), Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE)) + (team.isMember(getP()) ? getMsg(getP(), Messages.FORMATTING_SCOREBOARD_YOUR_TEAM) : ""));
                    }
                    setContent(e.getKey(), text.replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                            .replace("{date}", date).replace("{kills}", kills).replace("{finalKills}", finalKills).replace("{beds}", beds)
                            .replace("{nextEvent}", ne[0])
                            .replace("{time}", ne[1]).replace("{deaths}", String.valueOf(getArena().getPlayerDeaths(p, false))));
                }
            }
        }
    }

    public void addHealthIcon() {
        if (getArena() == null) return;
        if (getArena().getSpectators() == null) return;
        if (getP() == null) return;
        if (getArena().getSpectators().contains(getP())) return;
        if (sb.getObjective("my") == null) {
            Objective objective = sb.registerNewObjective("my", "health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("§c ❤");
        }
    }

    public void giveTeamColorTag() {
        for (ITeam t : arena.getTeams()) {
            Team team;
            if (sb.getTeam(t.getName()) == null) {
                team = sb.registerNewTeam(t.getName());
            } else {
                team = sb.getTeam(t.getName());
            }
            team.setPrefix(t.getColor().chat() + "" + ChatColor.BOLD + t.getName().substring(0, 1).toUpperCase() + ChatColor.RESET + " " + t.getColor().chat());
            for (Player p : t.getMembers()) {
                team.addEntry(p.getName());
            }
        }
    }

    public Player getP() {
        return p;
    }

    public void remove() {
        p.setScoreboard(sbm.getNewScoreboard());
        scoreboards.remove(getP().getUniqueId());
    }

    public IArena getArena() {
        return arena;
    }

    public static ConcurrentHashMap<UUID, SBoard> getScoreboards() {
        return scoreboards;
    }

    public static SBoard getSBoard(UUID player){
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
                st = getMsg(getP(), Messages.NEXT_EVENT_EMERALD_UPGRADE_II);
                time = (arena.upgradeEmeraldsCount) * 1000L;
                break;
            case EMERALD_GENERATOR_TIER_III:
                st = getMsg(getP(), Messages.NEXT_EVENT_EMERALD_UPGRADE_III);
                time = (arena.upgradeEmeraldsCount) * 1000L;
                break;
            case DIAMOND_GENERATOR_TIER_II:
                st = getMsg(getP(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_II);
                time = (arena.upgradeDiamondsCount) * 1000L;
                break;
            case DIAMOND_GENERATOR_TIER_III:
                st = getMsg(getP(), Messages.NEXT_EVENT_DIAMOND_UPGRADE_III);
                time = (arena.upgradeDiamondsCount) * 1000L;
                break;
            case GAME_END:
                st = getMsg(getP(), Messages.NEXT_EVENT_GAME_END);
                time = (arena.getPlayingTask().getGameEndCountdown()) * 1000L;
                break;
            case BEDS_DESTROY:
                st = getMsg(getP(), Messages.NEXT_EVENT_BEDS_DESTROY);
                time = (arena.getPlayingTask().getBedsDestroyCountdown()) * 1000L;
                break;
            case ENDER_DRAGON:
                st = getMsg(getP(), Messages.NEXT_EVENT_DRAGON_SPAWN);
                time = (arena.getPlayingTask().getDragonSpawnCountdown()) * 1000L;
                break;
        }

        return new String[]{st, dateFormat.format((time))};
    }

    /**
     * Update spectators for player.
     */
    void updateSpectator(Player p, boolean value) {
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
    }

    public void invisibilityPotion(@NotNull ITeam team, Player player, boolean trueRemoveAddFalse) {
        Team t = sb.getTeam(team.getName());
        if (t != null) {
            if (trueRemoveAddFalse) t.removeEntry(player.getName());
            else t.addEntry(player.getName());
        }
    }

    /**
     * Give game scoreboard.
     *
     * @param p     target player.
     * @param arena target arena.
     */
    public static void giveGameScoreboard(@NotNull Player p, @NotNull final IArena arena) {
        if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_GAME_SCOREBOARD)) return;
        if (arena.getStatus() == GameState.waiting) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                if (p.isOnline())
                    new SBoard(p, getScoreboard(p, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING), arena);
            }, 15L);
        } else if (arena.getStatus() == GameState.starting) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                if (p.isOnline())
                    new SBoard(p, getScoreboard(p, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING), arena);
            }, 15L);
        }
    }

    /**
     * Give spectator scoreboard.
     *
     * @param player target player.
     * @param arena  target arena.
     */
    public static void giveSpectatorScoreboard(Player player, final IArena arena) {
        if (!config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_GAME_SCOREBOARD)) return;
        Bukkit.getScheduler().runTaskLater(plugin, () -> new SBoard(player, arena), 35L);
    }

    /**
     * Give lobby scoreboard if enabled in config.
     *
     * @param p target player.
     */
    public static void giveLobbyScoreboard(Player p) {
        if (config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_LOBBY_SCOREBOARD)) {
            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                if (p.isOnline())
                    new SBoard(p, getList(p, Messages.SCOREBOARD_LOBBY), null);
            }, 40L);
        }
    }
}
