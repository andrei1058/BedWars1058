/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2023 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.spigot.sidebar.PlayerTab;
import com.andrei1058.spigot.sidebar.Sidebar;
import com.andrei1058.spigot.sidebar.SidebarLine;
import com.andrei1058.spigot.sidebar.SidebarLineAnimated;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;

public class BwTabList {

    private static final char SPECTATOR_PREFIX = 'z';
    private static final char ELIMINATED_FROM_TEAM_PREFIX = 'z';

    // Player list container. Used to manipulate deployed player tab: lines ecc.
    // Key is player uuid.
    private final HashMap<UUID, PlayerTab> deployedPerPlayerTabList = new HashMap<>();
    // playing-restarting team order prefix for tab
    // this is concatenated to player identifier to keep tab-list ordered
    // and still let players have individual placeholders
    private final HashMap<UUID, String> teamOrderPrefix = new HashMap<>();
    private int teamOrderIndex = 0;
    // unique string used for tab ordering. Does not track team here.
    private final HashMap<UUID, String> playerTabIdentifier = new HashMap<>();
    // used to prevent tab identifier duplication. Keeps an index of concurrent identifiers
    // concatenated later to playerTabIdentifier
    private final HashMap<String, Integer> playerTabIdentifierDuplication = new HashMap<>();

    private final BwSidebar sidebar;

    public BwTabList(BwSidebar sidebar) {
        this.sidebar = sidebar;
    }

    /**
     * Triggered when sidebar context changes.
     * Arena/ game state change.
     */
    void handlePlayerList() {

        // clear existing formatted player tab-lists
        if (null != sidebar.getHandle()) {
            deployedPerPlayerTabList.clear();
            sidebar.getHandle().removeTabs();
        }

        handleHealthIcon();

        if (this.isTabFormattingDisabled()) {
            return;
        }

        if (null == sidebar.getArena()) {
            // if tab formatting is enabled in lobby world
            if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                    !config.getLobbyWorldName().trim().isEmpty()) {

                World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                if (null == lobby) {
                    return;
                }
                lobby.getPlayers().forEach(inLobby -> giveUpdateTabFormat(inLobby, true, null));
            }
            // sometimes due to timing issues player is not listed yet in lobby players
            giveUpdateTabFormat(sidebar.getPlayer(), true, null);
            return;
        }

        handleHealthIcon();

        sidebar.getArena().getPlayers().forEach(playing -> giveUpdateTabFormat(playing, true, null));
        sidebar.getArena().getSpectators().forEach(spectating -> giveUpdateTabFormat(spectating, true, null));
    }

    public void handleHealthIcon() {
        if (null == sidebar.getHandle()) {
            return;
        }

        if (null == sidebar.getArena()) {
            sidebar.getHandle().hidePlayersHealth();
            return;
        } else if (sidebar.getArena().getStatus() != GameState.playing) {
            sidebar.getHandle().hidePlayersHealth();
            return;
        }

        List<String> animation = Language.getList(sidebar.getPlayer(), Messages.FORMATTING_SCOREBOARD_HEALTH);
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

        if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_ENABLE)) {
            sidebar.getHandle().showPlayersHealth(line, config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_IN_TAB));
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (null != sidebar.getArena() && null != sidebar.getHandle()) {
                sidebar.getArena().getPlayers().forEach(player -> sidebar.getHandle().setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
                if (sidebar.getArena().isSpectator(sidebar.getPlayer())) {
                    sidebar.getArena().getSpectators().forEach(player -> sidebar.getHandle().setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
                }
            }
        }, 10L);
    }

    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage
     */
    public boolean isTabFormattingDisabled() {
        if (null == sidebar.getArena()) {

            if (getServerType() == ServerType.SHARED) {
                if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                    return null == lobby || !sidebar.getPlayer().getWorld().getName().equals(lobby.getName());
                }
            }

            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
        }
        GameState status = sidebar.getArena().getStatus();

        // if tab formatting is disabled in game
        if (status == GameState.playing) {
            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING);
        }

        // if tab formatting is disabled in starting
        if (status == GameState.starting) {
            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING);
        }

        // if tab formatting is disabled in waiting
        if (status == GameState.waiting) {
            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING);
        }

        // if tab formatting is disabled in restarting
        return status != GameState.restarting || !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING);
    }

    /**
     * Handle given player in sidebar owner tab list.
     * Will remove existing tab and give a new one based on game conditions list like spectator, team red, etc.
     * Will handle invisibility potion as well.
     */
    public void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck, @Nullable Boolean spectator) {
        // if sidebar was not created
        if (sidebar.getHandle() == null) {
            return;
        }

        // unique tab list name
        String playerTabId = getCreatePlayerTabIdentifier(player);

        // clear existing tab formatting for given player
        PlayerTab playerTab = deployedPerPlayerTabList.getOrDefault(player.getUniqueId(), null);
        if (null != playerTab) {
            sidebar.getHandle().removeTab(playerTab.getIdentifier());
            deployedPerPlayerTabList.remove(player.getUniqueId());
        }

        if (!skipStateCheck) {
            if (this.isTabFormattingDisabled()) {
                return;
            }
        }

        SidebarLine prefix;
        SidebarLine suffix;
        IArena arena = sidebar.getArena();
        Sidebar handle = sidebar.getHandle();

        if (null == arena) {
            prefix = getTabText(Messages.FORMATTING_SB_TAB_LOBBY_PREFIX, player, null);
            suffix = getTabText(Messages.FORMATTING_SB_TAB_LOBBY_SUFFIX, player, null);

            PlayerTab tab = handle.playerTabCreate(
                    playerTabId, player, prefix, suffix, PlayerTab.PushingRule.NEVER,
                    this.sidebar.getPlaceholders(player)
            );
            deployedPerPlayerTabList.put(player.getUniqueId(), tab);
            return;
        }

        // in-game tab has a special treatment
        if (arena.isSpectator(player) || (spectator != null && spectator)) {

            // if has been eliminated from a team
            ITeam exTeam = arena.getExTeam(player.getUniqueId());

            // when player leaves but decides to join to spectate later
            if (null != exTeam) {

                HashMap<String, String> replacements = getTeamReplacements(exTeam);

                if (arena.getStatus() == GameState.restarting && null != arena.getWinner()) {
                    if (arena.getWinner().equals(exTeam)) {
                        prefix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_PREFIX, player, replacements);
                        suffix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_SUFFIX, player, replacements);
                    } else {
                        prefix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_PREFIX, player, replacements);
                        suffix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_SUFFIX, player, replacements);
                    }
                } else {
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_ELM_PREFIX, player, replacements);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_ELM_SUFFIX, player, replacements);
                }

                PlayerTab tab = handle.playerTabCreate(
                        getPlayerTabIdentifierEliminatedInTeam(exTeam, playerTabId),
                        player, prefix, suffix, PlayerTab.PushingRule.NEVER,
                        this.sidebar.getPlaceholders(player)
                );
                deployedPerPlayerTabList.put(player.getUniqueId(), tab);
                return;
            }

            switch (arena.getStatus()) {
                case waiting:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_WAITING_PREFIX_SPEC, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_WAITING_SUFFIX_SPEC, player, null);
                    break;
                case starting:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_STARTING_PREFIX_SPEC, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_STARTING_SUFFIX_SPEC, player, null);
                    break;
                case playing:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_PREFIX, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_SUFFIX, player, null);
                    break;
                case restarting:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_PREFIX, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_SUFFIX, player, null);
                    break;
                default:
                    throw new RuntimeException("Unhandled game state..");
            }

            PlayerTab tab = handle.playerTabCreate(
                    getPlayerTabIdentifierSpectator(null, playerTabId),
                    player, prefix, suffix, PlayerTab.PushingRule.NEVER,
                    this.sidebar.getPlaceholders(player)
            );
            deployedPerPlayerTabList.put(player.getUniqueId(), tab);
            return;
        }

        // this is reached only by alive players
        GameState status = arena.getStatus();
        if (status != GameState.playing) {

            String currentTabId = playerTabId;

            switch (status) {
                case waiting:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_WAITING_PREFIX, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_WAITING_SUFFIX, player, null);
                    break;
                case starting:
                    prefix = getTabText(Messages.FORMATTING_SB_TAB_STARTING_PREFIX, player, null);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_STARTING_SUFFIX, player, null);
                    break;
                case restarting:
                    ITeam team = arena.getTeam(player);
                    currentTabId = getPlayerTabIdentifierAliveInTeam(team, playerTabId);

                    HashMap<String, String> replacements = getTeamReplacements(team);

                    prefix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_PREFIX, player, replacements);
                    suffix = getTabText(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_SUFFIX, player, replacements);
                    break;
                default:
                    throw new IllegalStateException("Unhandled game status!");
            }
            PlayerTab t = handle.playerTabCreate(
                    currentTabId, player, prefix, suffix, PlayerTab.PushingRule.NEVER, this.sidebar.getPlaceholders(player)
            );
            deployedPerPlayerTabList.put(player.getUniqueId(), t);
            return;
        }

        // if status is playing and player is alive

        ITeam team = arena.getTeam(player);
        // tab list of playing state
        HashMap<String, String> replacements = getTeamReplacements(team);

        prefix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_PREFIX, player, replacements);
        suffix = getTabText(Messages.FORMATTING_SB_TAB_PLAYING_SUFFIX, player, replacements);

        PlayerTab teamTab = handle.playerTabCreate(
                getPlayerTabIdentifierAliveInTeam(team, playerTabId),
                player, prefix, suffix, PlayerTab.PushingRule.PUSH_OTHER_TEAMS,
                this.sidebar.getPlaceholders(player)
        );
        deployedPerPlayerTabList.put(player.getUniqueId(), teamTab);
        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            teamTab.setNameTagVisibility(PlayerTab.NameTagVisibility.NEVER);
        }
    }

    @NotNull
    private SidebarLine getTabText(String path, Player targetPlayer, @Nullable HashMap<String, String> replacements) {
        List<String> strings = Language.getList(sidebar.getPlayer(), path);
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
        for (String string : Language.getList(sidebar.getPlayer(), path)) {
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
     * Gets/generates a prefix string to be concatenated to the player tab-list identifier, it keeps tab-list ordered by team.
     *
     * @param team target.
     * @return prefix string.
     */
    private String getCreateTeamTabOrderPrefix(@NotNull ITeam team) {
        String prefix = teamOrderPrefix.getOrDefault(team.getIdentity(), null);
        if (null == prefix) {
            teamOrderIndex++;
            prefix = teamOrderIndex + "";
            teamOrderPrefix.put(team.getIdentity(), prefix);
            if (prefix.length() > 3) {
                throw new RuntimeException("Could not generate new order prefixes. Char limit exceeded. Max value is 999.");
            }

            // todo how do we clean up index? when arena became null?
        }
        return prefix;
    }

    /**
     * Get existing or create player unique identifier for tab ordering
     *
     * @param player target.
     * @return unique tab identifier for given player.
     */
    private String getCreatePlayerTabIdentifier(@NotNull Player player) {
        String id = playerTabIdentifier.getOrDefault(player.getUniqueId(), null);
        if (null == id) {
            id = player.getName().substring(0, Math.min(player.getName().length(), 9));

            if (hasPlayerIdentifier(id)) {
                Integer lastDuplicationIndex = playerTabIdentifierDuplication.getOrDefault(id, 0);

                lastDuplicationIndex++;
                id += lastDuplicationIndex.toString();
                playerTabIdentifierDuplication.put(id, lastDuplicationIndex);
            }

            playerTabIdentifier.put(player.getUniqueId(), id);
        }
        return id;
    }


    private boolean hasPlayerIdentifier(@NotNull String id) {
        for (String string : playerTabIdentifier.values()) {
            if (string.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    private @NotNull String getPlayerTabIdentifierAliveInTeam(ITeam team, Player player) {
        return getPlayerTabIdentifierAliveInTeam(team, getCreatePlayerTabIdentifier(player));
    }

    private @NotNull String getPlayerTabIdentifierAliveInTeam(ITeam team, String playerId) {
        return getCreateTeamTabOrderPrefix(team) + playerId;
    }

    @SuppressWarnings("unused")
    private @NotNull String getPlayerTabIdentifierEliminatedInTeam(ITeam team, Player player) {
        return getPlayerTabIdentifierEliminatedInTeam(team, getCreatePlayerTabIdentifier(player));
    }

    private @NotNull String getPlayerTabIdentifierEliminatedInTeam(ITeam team, String playerId) {
        return ELIMINATED_FROM_TEAM_PREFIX + getCreateTeamTabOrderPrefix(team) + playerId;
    }

    @SuppressWarnings("unused")
    private @NotNull String getPlayerTabIdentifierSpectator(@Nullable ITeam team, Player player) {
        return getPlayerTabIdentifierSpectator(team, getCreatePlayerTabIdentifier(player));
    }

    private @NotNull String getPlayerTabIdentifierSpectator(@Nullable ITeam team, String playerId) {
        if (null == team) {
            return SPECTATOR_PREFIX + playerId;
        }
        return getPlayerTabIdentifierEliminatedInTeam(team, playerId);
    }

    @NotNull HashMap<String, String> getTeamReplacements(@Nullable ITeam team) {
        HashMap<String, String> replacements = new HashMap<>();
        String displayName = null == team ? "" : team.getDisplayName(Language.getPlayerLanguage(sidebar.getPlayer()));
        replacements.put("{teamName}", displayName);
        replacements.put("{teamLetter}", null == team ? "" : team.getColor().chat() + (displayName.substring(0, 1)));
        replacements.put("{teamColor}", null == team ? "" : team.getColor().chat().toString());

        return replacements;
    }

    /**
     * Clear tab lines from instance.
     */
    public void onSidebarRemoval() {
        sidebar.getHandle().clearLines();
        deployedPerPlayerTabList.clear();
        playerTabIdentifier.clear();
        playerTabIdentifierDuplication.clear();
        teamOrderPrefix.clear();
        teamOrderIndex = 0;
    }
}
