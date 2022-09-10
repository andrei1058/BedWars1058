package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.spigot.sidebar.SidebarManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.api.language.Language.getScoreboard;

public class SidebarService {

    private static SidebarService instance;

    private final SidebarManager sidebarHandler;
    private final HashMap<UUID, BwSidebar> sidebars = new HashMap<>();


    public static boolean init() {
        if (null == instance) {
            instance = new SidebarService();
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
                // if sidebar is disabled in lobby on shared mode
                if (BedWars.getServerType() == ServerType.SHARED &&
                        !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR)) {
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

        // set sidebar lines based on game state or lobby
        List<String> lines = null;
        // todo create new paths for title array
        List<String> title = List.of("TEST");
        if (null == arena) {
            if (BedWars.getServerType() == ServerType.SHARED) {
                lines = Language.getList(player, Messages.SCOREBOARD_LOBBY);
            }
        } else {
            if (arena.getStatus() == GameState.waiting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".waiting", Messages.SCOREBOARD_DEFAULT_WAITING);
            } else if (arena.getStatus() == GameState.starting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".starting", Messages.SCOREBOARD_DEFAULT_STARTING);
            } else if (arena.getStatus() == GameState.playing || arena.getStatus() == GameState.restarting) {
                lines = getScoreboard(player, "scoreboard." + arena.getGroup() + ".playing", Messages.SCOREBOARD_DEFAULT_PLAYING);
            }
        }

        // if we do not have lines we eventually remove the sidebar
        if (null == lines || lines.isEmpty()) {
            if (null != sidebar) {
                this.remove(sidebar);
            }
            return;
        }

        // at this point we are sure we need a sidebar instance
        if (null == sidebar) {
            sidebar = new BwSidebar(player);
        }
        sidebar.setContent(title, lines, arena);
    }

    /**
     * Kill a sidebar lifecycle.
     */
    public void remove(@NotNull BwSidebar sidebar) {
        this.sidebars.remove(sidebar.getPlayer().getUniqueId());
        sidebar.remove();
    }

    public static SidebarService getInstance() {
        return instance;
    }

    protected SidebarManager getSidebarHandler() {
        return sidebarHandler;
    }
}
