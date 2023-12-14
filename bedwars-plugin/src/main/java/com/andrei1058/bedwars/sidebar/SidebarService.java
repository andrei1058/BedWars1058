package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.sidebar.PlayerSidebarInitEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.sidebar.ISidebar;
import com.andrei1058.bedwars.api.sidebar.ISidebarService;
import com.andrei1058.bedwars.metrics.MetricsManager;
import com.andrei1058.bedwars.sidebar.thread.*;
import com.andrei1058.spigot.sidebar.SidebarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.api.language.Language.getScoreboard;

public class SidebarService implements ISidebarService {

    private static SidebarService instance;

    private final SidebarManager sidebarHandler;
    private final HashMap<UUID, BwSidebar> sidebars = new HashMap<>();

    public static boolean init(JavaPlugin plugin) {
        if (null == instance) {
            instance = new SidebarService();

            var log = Bukkit.getLogger();

            int playerListRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_LIST_REFRESH);
            if (playerListRefreshInterval < 1) {
                Bukkit.getLogger().info("Scoreboard names list refresh is disabled. (It is set to " + playerListRefreshInterval + ").");
            } else {
                if (playerListRefreshInterval < 20) {
                    log.warning("Scoreboard names list refresh interval is set to: " + playerListRefreshInterval);
                    log.warning("It is not recommended to use a value under 20 ticks.");
                    log.warning("If you expect performance issues please increase its timer.");
                }
                Bukkit.getScheduler().runTaskTimer(plugin, new RefreshPlayerListTask(), 1L, playerListRefreshInterval);
            }
            MetricsManager.appendPie("sb_list_refresh_interval", () -> String.valueOf(playerListRefreshInterval));

            int placeholdersRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL);
            if (placeholdersRefreshInterval < 1) {
                log.info("Scoreboard placeholders refresh is disabled. (It is set to " + placeholdersRefreshInterval + ").");
            } else {
                if (placeholdersRefreshInterval < 20) {
                    log.warning("Scoreboard placeholders refresh interval is set to: " + placeholdersRefreshInterval);
                    log.warning("It is not recommended to use a value under 20 ticks.");
                    log.warning("If you expect performance issues please increase its timer.");
                }
                Bukkit.getScheduler().runTaskTimer(plugin, new RefreshPlaceholdersTask(), 1L, placeholdersRefreshInterval);
            }
            MetricsManager.appendPie("sb_placeholder_refresh_interval", () -> String.valueOf(placeholdersRefreshInterval));

            int titleRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL);
            if (titleRefreshInterval < 1) {
                log.info("Scoreboard title refresh is disabled. (It is set to " + titleRefreshInterval + ").");
            } else {
                if (titleRefreshInterval < 4) {
                    log.warning("Scoreboard title refresh interval is set to: " + titleRefreshInterval);
                    log.warning("If you expect performance issues please increase its timer.");
                }
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new RefreshTitleTask(), 1L, titleRefreshInterval);
            }
            MetricsManager.appendPie("sb_title_refresh_interval", () -> String.valueOf(titleRefreshInterval));

            int healthAnimationInterval = config.getInt(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_REFRESH);
            if (healthAnimationInterval < 1) {
                log.info("Scoreboard health animation refresh is disabled. (It is set to " + healthAnimationInterval + ").");
            } else {
                if (healthAnimationInterval < 20) {
                    log.warning("Scoreboard health animation refresh interval is set to: " + healthAnimationInterval);
                    log.warning("It is not recommended to use a value under 20 ticks.");
                    log.warning("If you expect performance issues please increase its timer.");
                }
                Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new RefreshLifeTask(), 1L, healthAnimationInterval);
            }
            MetricsManager.appendPie("sb_health_refresh_interval", () -> String.valueOf(healthAnimationInterval));

            int tabHeaderFooterRefreshInterval = config.getInt(ConfigPath.SB_CONFIG_TAB_HEADER_FOOTER_REFRESH_INTERVAL);
            if (tabHeaderFooterRefreshInterval < 1 || !config.getBoolean(ConfigPath.SB_CONFIG_TAB_HEADER_FOOTER_ENABLE)) {
                log.info("Scoreboard Tab header-footer refresh is disabled.");
            } else {
                if (tabHeaderFooterRefreshInterval < 20) {
                    log.warning("Scoreboard tab header-footer refresh interval is set to: " + tabHeaderFooterRefreshInterval);
                    log.warning("It is not recommended to use a value under 20 ticks.");
                    log.warning("If you expect performance issues please increase its timer.");
                }
                Bukkit.getScheduler().runTaskTimer(plugin, new RefreshTabHeaderFooterTask(), 1L, tabHeaderFooterRefreshInterval);
            }
            MetricsManager.appendPie("sb_header_footer_refresh_interval", () -> String.valueOf(tabHeaderFooterRefreshInterval));

            var lobbySidebar = config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR) &&
                    BedWars.getServerType() == ServerType.MULTIARENA;
            MetricsManager.appendPie("sb_lobby_enable", () -> String.valueOf(lobbySidebar));
            var gameSidebar = config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR);
            MetricsManager.appendPie("sb_game_enable", () -> String.valueOf(gameSidebar));

            BedWars.registerEvents(new ScoreboardListener());
        }
        return instance.sidebarHandler != null;
    }

    private SidebarService() {
        sidebarHandler = SidebarManager.init();
    }

    public void giveSidebar(@NotNull Player player, @Nullable IArena arena, boolean delay) {
        BwSidebar sidebar = sidebars.getOrDefault(player.getUniqueId(), null);

        // check if we might need to remove the existing sidebar
        if (null != sidebar) {
            if (null == arena) {
                // if sidebar is disabled in lobby on shared or multi-arena mode
                if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR) ||
                        BedWars.getServerType() == ServerType.SHARED) {
                    this.remove(sidebar);
                    return;
                }
            } else {
                // if sidebar is disabled in game
                if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR)) {
                    this.remove(sidebar);
                    return;
                }
            }
        }

        // if sidebar was null but still disabled for lobbies
        if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR) && null == arena) {
            return;
        }
        // if sidebar was null but still disabled in game
        if (!config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR) && null != arena) {
            return;
        }

        // set sidebar lines based on game state or lobby
        List<String> lines = null;
        List<String> title;
        if (null == arena) {
            if (BedWars.getServerType() != ServerType.SHARED) {
                lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
            }
        } else {
            if (arena.getStatus() == GameState.waiting) {
                if (arena.isSpectator(player)) {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".waiting.spectator", Messages.SCOREBOARD_DEFAULT_WAITING_SPEC);
                } else {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".waiting.player", Messages.SCOREBOARD_DEFAULT_WAITING);
                }
            } else if (arena.getStatus() == GameState.starting) {
                if (arena.isSpectator(player)) {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".starting.spectator", Messages.SCOREBOARD_DEFAULT_STARTING_SPEC);
                } else {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".starting.player", Messages.SCOREBOARD_DEFAULT_STARTING);
                }
            } else if (arena.getStatus() == GameState.playing) {
                if (arena.isSpectator(player)) {
                    ITeam holderExTeam = arena.getExTeam(player.getUniqueId());
                    if (null == holderExTeam) {
                        lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".playing.spectator", Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC);
                    } else {
                        lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".playing.eliminated", Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED);
                    }
                } else {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".playing.alive", Messages.SCOREBOARD_DEFAULT_PLAYING);
                }
            } else if (arena.getStatus() == GameState.restarting) {

                ITeam holderTeam = arena.getTeam(player);
                ITeam holderExTeam = null == holderTeam ? arena.getExTeam(player.getUniqueId()) : null;

                if (null == holderTeam && null == holderExTeam) {
                    lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".restarting.spectator", Messages.SCOREBOARD_DEFAULT_RESTARTING_SPEC);
                } else {
                    if (null == holderTeam && holderExTeam.equals(arena.getWinner())) {
                        lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".restarting.winner-eliminated", Messages.SCOREBOARD_DEFAULT_RESTARTING_WIN2);
                    } else if (null == holderExTeam && holderTeam.equals(arena.getWinner())) {
                        lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".restarting.winner-alive", Messages.SCOREBOARD_DEFAULT_RESTARTING_WIN1);
                    } else {
                        lines = getScoreboard(player, "sidebar." + arena.getGroup() + ".restarting.loser", Messages.SCOREBOARD_DEFAULT_RESTARTING_LOSER);
                    }
                }
            }
        }

        // if we do not have lines we eventually remove the sidebar
        if (null == lines || lines.isEmpty()) {
            if (null != sidebar) {
                this.remove(sidebar);
            }
            return;
        }

        // title is the first line from array
        title = new ArrayList<>(Arrays.asList(lines.get(0).split(",")));
        if (lines.size() == 1) {
            lines = new ArrayList<>();
        }
        lines = lines.subList(1, lines.size());

        // at this point we are sure we need a sidebar instance
        boolean newlyAdded = false;
        if (null == sidebar) {
            sidebar = new BwSidebar(player);
            newlyAdded = true;

            PlayerSidebarInitEvent event = new PlayerSidebarInitEvent(player, sidebar);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        sidebar.setContent(title, lines, arena);

        if (newlyAdded) {
            sidebars.put(player.getUniqueId(), sidebar);
        }
    }

    /**
     * Kill a sidebar lifecycle.
     */
    public void remove(@NotNull BwSidebar sidebar) {
        this.sidebars.remove(sidebar.getPlayer().getUniqueId());
        sidebar.remove();
    }

    public void remove(@NotNull Player player) {
        BwSidebar sidebar = this.sidebars.remove(player.getUniqueId());
        if (null != sidebar) {
            sidebar.remove();
        }
    }

    public static SidebarService getInstance() {
        return instance;
    }

    protected SidebarManager getSidebarHandler() {
        return sidebarHandler;
    }

    public void refreshTitles() {
        this.sidebars.forEach((k, v) -> v.getHandle().refreshTitle());
    }

    public void refreshPlaceholders() {
        this.sidebars.forEach((k, v) -> v.getHandle().refreshPlaceholders());
    }

    public void refreshPlaceholders(IArena arena) {
        this.sidebars.forEach((k, v) -> {
            if (v.getArena() != null)
                if (v.getArena().equals(arena)) {
                    v.getHandle().refreshPlaceholders();
                }
        });
    }

    public void refreshTabList() {
        this.sidebars.forEach((k, v) -> v.getHandle().playerTabRefreshAnimation());
    }

    public void refreshTabHeaderFooter() {
        this.sidebars.forEach((k, v) -> {
            if (null != v && null != v.getHeaderFooter()) {
                this.sidebarHandler.sendHeaderFooter(v.getPlayer(), v.getHeaderFooter());
            }
        });
    }

    public void refreshHealth() {
        this.sidebars.forEach((k, v) -> {
            if (null != v.getArena()) {
                v.getHandle().playerHealthRefreshAnimation();
                for (Player player : v.getArena().getPlayers()) {
                    v.getHandle().setPlayerHealth(player, (int) Math.ceil(player.getHealth()));
                }
            }
        });
    }

    @Override
    public @Nullable ISidebar getSidebar(@NotNull Player player) {
        return this.sidebars.getOrDefault(player.getUniqueId(), null);
    }

    public void refreshHealth(IArena arena, Player player, int health) {
        this.sidebars.forEach((k, v) -> {
            if (null != v.getArena() && v.getArena().equals(arena)) {
                v.getHandle().setPlayerHealth(player, health);
            }
        });
    }

    public void handleReJoin(IArena arena, Player player) {
        this.sidebars.forEach((k, v) -> {
            if (null != v.getArena() && v.getArena().equals(arena)) {
                v.giveUpdateTabFormat(player, false);
            }
        });
    }

    public void handleJoin(IArena arena, Player player, @Nullable Boolean spectator) {
        this.sidebars.forEach((k, v) -> {
            if (null != v.getArena() && v.getArena().equals(arena)) {
                if (!v.getPlayer().equals(player)) {
                    v.giveUpdateTabFormat(player, false, spectator);
                }
            }
        });
    }

    public void applyLobbyTab(Player player) {
        this.sidebars.forEach((k, v) -> {
            if (null == v.getArena()) {
                if (!v.getPlayer().equals(player)) {
                    v.giveUpdateTabFormat(player, false);
                }
            }
        });
    }

    public void handleInvisibility(ITeam team, Player player, boolean toggle) {
        this.sidebars.forEach((k, v) -> {
            if (null != v.getArena() && v.getArena().equals(team.getArena())) {
                v.handleInvisibilityPotion(player, toggle);
            }
        });
    }
}
