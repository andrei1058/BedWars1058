package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.TeamColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.Main.database;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.arena.Misc.replaceStatsPlaceholders;
import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.configuration.Language.getScoreboard;

public class SBoard {

    public static ScoreboardManager sbm = Bukkit.getScoreboardManager();
    private List<String> placeholders = Arrays.asList("{on}", "{max}", "{time}", "{generatorName}", "{generatorTimer}", "{date}");
    private static List<SBoard> scoreboards = new ArrayList<>();
    private HashMap<Team, String> toRefresh = new HashMap<>();
    private Scoreboard sb = sbm.getNewScoreboard();
    private Player p;
    private Objective o;
    private Arena arena;
    private SimpleDateFormat dateFormat;

    public SBoard(Player p, List<String> content, Arena arena) {
        this.p = p;
        o = sb.registerNewObjective("Sb", "or");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.arena = arena;
        if (this.arena != null) {
            placeholders = new ArrayList<>(placeholders);
            this.placeholders.add("{kills}");
            this.placeholders.add("{finalKills}");
            this.placeholders.add("{beds}");
            for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
                if (sb.getP() == p) {
                    sb.remove();
                }
            }
        }
        this.setStrings(content);
        p.setScoreboard(sb);
        dateFormat = new SimpleDateFormat(getMsg(p, lang.generatorTimerFormat));
        scoreboards.add(this);
    }

    /**
     * Used for spectators
     */
    public SBoard(Player p, Arena arena) {
        this.p = p;
        o = sb.registerNewObjective("Sb", "or");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.arena = arena;
        dateFormat = new SimpleDateFormat(getMsg(p, lang.generatorTimerFormat));
        this.setStrings(getScoreboard(p, "scoreboard." + arena.getGroup() + "Playing", lang.scoreboardDefaultPlaying));
        p.setScoreboard(sb);
        scoreboards.add(this);
        Team t = sb.registerNewTeam("spect");
        t.addEntry(p.getName());
        nms.teamCollideRule(t);
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
            for (String ph : placeholders) {
                if (temp.contains(ph)) {
                    if (!toRefresh.containsKey(t)) {
                        toRefresh.put(t, temp);
                    }
                }
            }
            if (arena != null) {
                for (BedWarsTeam teams : arena.getTeams()) {
                    if (temp.contains("{Team" + teams.getName() + "Status}")) {
                        if (!toRefresh.containsKey(t)) {
                            toRefresh.put(t, temp);
                        }
                    }
                }
            }
            if (arena == null) {
                /** stats */
                int kills = database.getKills(p), deaths = database.getDeaths(p), looses = database.getLooses(p), wins = database.getWins(p),
                        finalKills = database.getFinalKills(p), finalDeaths = database.getFinalDeaths(p), bedsDestroyed = database.getBedsDestroyed(p), gamesPlayed = database.getGamesPlayed(p);
                Timestamp firstPlay = database.getFirstPlay(p), lastPlay = database.getLastPlay(p);
                /** cache time format */
                String timeFormat = getMsg(p, lang.statsDateTimeFormat), never = getMsg(p, lang.never);

                temp = temp.replace("{server}", Bukkit.getServer().getMotd()).replace("{on}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getServer().getMaxPlayers())).replace("{date}",
                                new SimpleDateFormat(getMsg(getP(), lang.dateFormat)).format(new Date(System.currentTimeMillis())));

                setContent(t, replaceStatsPlaceholders(temp,
                        kills, deaths, looses, wins, finalKills, finalDeaths, bedsDestroyed, gamesPlayed, firstPlay, lastPlay, timeFormat, p.getName(), never));

            } else if (arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) {
                setContent(t, temp.replace("{map}", arena.getDisplayName()).replace("{server}", Bukkit.getServer().getMotd())
                        .replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                        .replace("{time}", String.valueOf(arena.getCountdownS())).replace("{player}", p.getName())
                        .replace("{date}", new SimpleDateFormat(getMsg(getP(), lang.dateFormat)).format(new Date(System.currentTimeMillis()))));
            } else if (arena.getStatus() == GameState.playing) {
                String generatorTime = dateFormat.format((arena.showDiamondSb ?
                        arena.upgradeDiamondsCount : arena.upgradeEmeraldsCount) * 1000);
                for (BedWarsTeam team : arena.getTeams()) {
                    temp = temp.replace("{Team" + team.getName() + "Color}", TeamColor.getChatColor(team.getColor()).toString()).replace("{Team" + team.getName() + "Name}",
                            team.getName()).replace("{Team" + team.getName() + "Status}", String.valueOf(team.isBedDestroyed() ? team.getSize() > 0 ? getMsg(getP(), lang.bedDestroyedFormat).replace("{remainingPlayers}",
                            String.valueOf(team.getSize())) : getMsg(getP(), lang.teamEliminatedFormat) : getMsg(getP(), lang.teamAliveFormat)) + (team.isMember(getP()) ? getMsg(getP(), lang.youScoreboardFormat) : ""));
                }
                setContent(t, temp.replace("{map}", arena.getDisplayName()).replace("{server}", Bukkit.getServer().getMotd())
                        .replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                        .replace("{time}", String.valueOf(arena.getCountdownS())).replace("{player}", p.getName())
                        .replace("{date}", new SimpleDateFormat(getMsg(getP(), lang.dateFormat)).format(new Date(System.currentTimeMillis())))
                        .replace("{kills}", String.valueOf(arena.getPlayerKills(getP(), false))).replace("{finalKills}", String.valueOf(arena.getPlayerKills(getP(), true)))
                        .replace("{beds}", String.valueOf(arena.getPlayerBedsDestroyed(getP()))).replace("{generatorName}", getMsg(getP(), arena.showDiamondSb ? lang.diamondGeneratorName : lang.emeraldGeneratorName))
                        .replace("{generatorTimer}", generatorTime));
            }
        }
    }

    private void setContent(Team t, String s) {
        if (s.length() >= 16) {
            String prefix = s.substring(0, 16);
            String suffix;
            if (prefix.endsWith("&") || prefix.endsWith("§")) {
                prefix = prefix.substring(0, prefix.length() - 1);
                suffix = s.substring(prefix.length(), s.length());
            } else if (prefix.substring(0, 15).endsWith("&") || prefix.substring(0, 15).endsWith("§")) {
                prefix = prefix.substring(0, prefix.length() - 2);
                suffix = s.substring(prefix.length(), s.length());
            } else {
                suffix = ChatColor.getLastColors(prefix) + s.substring(prefix.length(), s.length());
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
        String date = new SimpleDateFormat(getMsg(getP(), lang.dateFormat)).format(new Date(System.currentTimeMillis()));
        if (arena == null) {
            for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                setContent(e.getKey(), e.getValue().replace("{on}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("{max}", String.valueOf(Bukkit.getServer().getMaxPlayers())).replace("{date}", date));
            }
        } else {
            if (arena.getStatus() == GameState.waiting || arena.getStatus() == GameState.starting) {
                for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                    setContent(e.getKey(), e.getValue().replace("{on}", String.valueOf(arena.getPlayers().size()))
                            .replace("{max}", String.valueOf(arena.getMaxPlayers()))
                            .replace("{time}", String.valueOf(arena.getCountdownS())).replace("{date}", date));
                }
            } else if (arena.getStatus() == GameState.playing) {
                String kills = String.valueOf(arena.getPlayerKills(getP(), false)), finalKills = String.valueOf(arena.getPlayerKills(getP(), true)),
                        beds = String.valueOf(arena.getPlayerBedsDestroyed(getP())),
                        generatorTime = dateFormat.format((arena.showDiamondSb ?
                                arena.upgradeDiamondsCount : arena.upgradeEmeraldsCount) * 1000);
                for (Map.Entry<Team, String> e : toRefresh.entrySet()) {
                    String text = e.getValue();
                    for (BedWarsTeam team : arena.getTeams()) {
                        text = text.replace("{Team" + team.getName() + "Color}", TeamColor.getChatColor(team.getColor()).toString()).replace("{Team" + team.getName() + "Name}",
                                team.getName()).replace("{Team" + team.getName() + "Status}", String.valueOf(team.isBedDestroyed() ? team.getSize() > 0 ? getMsg(getP(), lang.bedDestroyedFormat).replace("{remainingPlayers}",
                                String.valueOf(team.getSize())) : getMsg(getP(), lang.teamEliminatedFormat) : getMsg(getP(), lang.teamAliveFormat)) + (team.isMember(getP()) ? getMsg(getP(), lang.youScoreboardFormat) : ""));
                    }
                    setContent(e.getKey(), text.replace("{on}", String.valueOf(arena.getPlayers().size())).replace("{max}", String.valueOf(arena.getMaxPlayers()))
                            .replace("{date}", date).replace("{kills}", kills).replace("{finalKills}", finalKills).replace("{beds}", beds)
                            .replace("{generatorName}", getMsg(getP(), arena.showDiamondSb ? lang.diamondGeneratorName : lang.emeraldGeneratorName))
                            .replace("{generatorTimer}", generatorTime));
                }
            }
        }
    }

    public void addHealthIcon() {
        if (sb.getObjective("my") == null) {
            Objective objective = sb.registerNewObjective("my", "health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("§c❤");
        }
    }

    public void giveTeamColorTag() {
        for (BedWarsTeam t : arena.getTeams()) {
            Team team = sb.registerNewTeam(t.getName());
            team.setPrefix(TeamColor.getChatColor(t.getColor()) + "§l" +
                    t.getName().substring(0, 1).toUpperCase() + " §r" + TeamColor.getChatColor(t.getColor()));
            nms.teamCollideRule(team);
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
        scoreboards.remove(this);
    }

    public Arena getArena() {
        return arena;
    }

    public static List<SBoard> getScoreboards() {
        return scoreboards;
    }

    public static String formatGenTimer(int duration) {
        long seconds = duration;
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
