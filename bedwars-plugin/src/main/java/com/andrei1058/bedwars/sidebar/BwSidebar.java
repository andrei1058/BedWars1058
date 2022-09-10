package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import com.andrei1058.bedwars.stats.PlayerStats;
import com.andrei1058.spigot.sidebar.PlaceholderProvider;
import com.andrei1058.spigot.sidebar.Sidebar;
import com.andrei1058.spigot.sidebar.SidebarLine;
import com.andrei1058.spigot.sidebar.SidebarLineAnimated;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class BwSidebar {

    private static final SidebarLine EMPTY_TITLE = new SidebarLine() {
        @Override
        public @NotNull String getLine() {
            return "";
        }
    };

    private final Player player;
    private IArena arena;
    private Sidebar handle;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat nextEventDateFormat;


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
        handle.remove(player);
    }

    public void setContent(List<String> titleArray, List<String> lineArray, @Nullable IArena arena) {
        SidebarLine title = this.normalizeTitle(titleArray);
        List<SidebarLine> lines = this.normalizeLines(lineArray);
        this.arena = arena;

        List<PlaceholderProvider> placeholders = this.getPlaceholders();

        // if it is the first time setting content we create the handle
        if (null == handle) {
            handle = SidebarService.getInstance().getSidebarHandler().createSidebar(title, lines, placeholders);
            handle.add(player);
        } else {
            new ArrayList<>(handle.getPlaceholders()).forEach(p -> handle.removePlaceholder(p.getPlaceholder()));
            placeholders.forEach(p -> handle.addPlaceholder(p));
            handle.setTitle(title);
            lines.forEach(l -> handle.addLine(l));
        }
    }

    public Player getPlayer() {
        return player;
    }

    @SuppressWarnings("ConstantConditions")
    private SidebarLine normalizeTitle(@Nullable List<String> titleArray) {
        return null == titleArray || titleArray.isEmpty() ?
                EMPTY_TITLE :
                new SidebarLineAnimated((String[]) titleArray.toArray());
    }

    @Contract(pure = true)
    private @NotNull List<SidebarLine> normalizeLines(@NotNull List<String> lineArray) {
        List<SidebarLine> lines = new ArrayList<>();
        for (String line : lineArray) {
            lines.add(new SidebarLine() {
                @Override
                public @NotNull String getLine() {
                    return line;
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

        PlayerLevel level = BedWars.getLevelSupport().getPlayerLevel(getPlayer());
        if (null != level) {
            providers.add(new PlaceholderProvider("{progress}", level::getProgress));
            providers.add(new PlaceholderProvider("{level}", ()-> String.valueOf(level.getLevel())));
            providers.add(new PlaceholderProvider("{currentXp}", level::getFormattedCurrentXp));
            providers.add(new PlaceholderProvider("{requiredXp}", level::getFormattedRequiredXp));
        }

        if (null == arena) {
            providers.add(new PlaceholderProvider("{on}", () ->
                    String.valueOf(null == arena ? Bukkit.getOnlinePlayers().size()))
            );
            PlayerStats stats = BedWars.getStatsManager().get(getPlayer().getUniqueId());
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
}
