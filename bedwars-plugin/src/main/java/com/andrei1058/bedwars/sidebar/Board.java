package com.andrei1058.bedwars.sidebar;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.sidebar.ISidebar;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import me.neznamy.tab.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class Board implements ISidebar {

    private final Player player;
    private IArena arena;
    private TabAPI handle;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat nextEventDateFormat;
    private final ScoreboardManager scoreboardManager;


    protected Board(ScoreboardManager scoreboardManager, Player player, @Nullable IArena arena) {
        this.arena = arena;
        this.scoreboardManager = scoreboardManager;
        this.player = player;
        nextEventDateFormat = new SimpleDateFormat(getMsg((Player) player.getPlayer(), Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER));
        nextEventDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat = new SimpleDateFormat(getMsg(player.getPlayer(), Messages.FORMATTING_SCOREBOARD_DATE));
    }

    public Scoreboard getScoreboard(String title, List<String> lineArray){
        String status = "default";
        if (arena != null) status = arena.getStatus().toString();
        BedWars.debug("getScoreboard() status: " + status);
        return scoreboardManager.createScoreboard(status, title, lineArray);
    }

    public void setContent(String status, String title, List<String> lineArray) {
//        handlePlayerList(); //todo
//        setHeaderFooter(); //todo
    }

    public TabPlayer getTabPlayer() {
        return null; //todo
    }

    public Player getPlayer(){
        return player;
    }



    private boolean noArena() {
        return null == arena;
    }

    private void handlePlayerList() {
//        if (null != handle) {
//            tabList.forEach((k, v) -> handle.removeTab(k));
//        }
//
//        handleHealthIcon();
//
//        if (this.isTabFormattingDisabled()) {
//            return;
//        }
//
//        if (noArena()) {
//            // if tab formatting is enabled in lobby world
//            if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
//                    !config.getLobbyWorldName().trim().isEmpty()) {
//
//                World lobby = Bukkit.getWorld(config.getLobbyWorldName());
//                if (null == lobby) {
//                    return;
//                }
//                lobby.getPlayers().forEach(inLobby -> giveUpdateTabFormat(inLobby, true));
//            }
//            return;
//        }
//
//        handleHealthIcon();
//
//        arena.getPlayers().forEach(playing -> giveUpdateTabFormat(playing, true));
//        arena.getSpectators().forEach(spectating -> giveUpdateTabFormat(spectating, true));
    }

    /**
     * Handle given player in sidebar owner tab list.
     * Will remove existing tab and give a new one based on game conditions list like spectator, team red, etc.
     * Will handle invisibility potion as well.
     */
    public void giveUpdateTabFormat(@NotNull Player player, boolean skipStateCheck) {
//        // if sidebar was not created
//        if (handle == null) {
//            return;
//        }
//
//        // unique tab list name
//        String tabListName = player.getName();
//
//        if (tabList.containsKey(tabListName)) {
//            handle.removeTab(tabListName);
//            tabList.remove(tabListName);
//            // SidebarManager.getInstance().sendHeaderFooter(player, "", "");
//        }
//
//        if (!skipStateCheck) {
//            if (this.isTabFormattingDisabled()) {
//                return;
//            }
//        }
//
//        SidebarLine prefix;
//        SidebarLine suffix;
//
//        if (noArena()) {
//            prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, player, null);
//            suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, player, null);
//
//            PlayerTab tab = handle.playerTabCreate(
//                    tabListName, player, prefix, suffix, PlayerTab.PushingRule.NEVER
//            );
//            tab.add(player);
//            tabList.put(tabListName, tab);
//            return;
//        }
//
//        // in-game tab has a special treatment
//        if (arena.isSpectator(player)) {
//            PlayerTab tab = tabList.get(SPECTATOR_TAB);
//            if (null == tab) {
//                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR, player, null);
//                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR, player, null);
//                tab = handle.playerTabCreate(SPECTATOR_TAB, null, prefix, suffix, PlayerTab.PushingRule.NEVER);
//                tabList.put(SPECTATOR_TAB, tab);
//            }
//            tab.add(player);
//
//            return;
//        }
//
//        if (arena.getStatus() != GameState.playing) {
//            if (arena.getStatus() == GameState.waiting) {
//                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, player, null);
//                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, player, null);
//            } else if (arena.getStatus() == GameState.starting) {
//                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, player, null);
//                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, player, null);
//            } else if (arena.getStatus() == GameState.restarting) {
//
//                ITeam team = arena.getTeam(player);
//                if (null == team) {
//                    team = arena.getExTeam(player.getUniqueId());
//                }
//
//                String displayName = null == team ? "" : team.getDisplayName(Language.getPlayerLanguage(this.player));
//
//                HashMap<String, String> replacements = new HashMap<>();
//                replacements.put("{team}", null == team ? "" : team.getColor().chat() + displayName);
//                replacements.put("{teamLetter}", null == team ? "" : team.getColor().chat() + (displayName.substring(0, 1)));
//                replacements.put("{teamColor}", null == team ? "" : team.getColor().chat().toString());
//
//
//                prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, player, replacements);
//                suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING, player, replacements);
//            } else {
//                throw new RuntimeException("Unhandled game status!");
//            }
//            PlayerTab t = handle.playerTabCreate(tabListName, player, prefix, suffix, PlayerTab.PushingRule.NEVER);
//            t.add(player);
//            tabList.put(tabListName, t);
//            return;
//        }
//
//        ITeam team = arena.getTeam(player);
//        if (null == team) {
//            team = arena.getExTeam(player.getUniqueId());
//        }
//        if (null == team) {
//            throw new RuntimeException("Wtf dude");
//        }
//
//        String tabName = this.getTabName(team);
//        String tabNameInvisible = tabName = tabName.substring(0, tabName.length() >= 16 ? 15 : tabName.length());
//        tabNameInvisible += "^!";
//
//        if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
//            if (!team.isMember(getPlayer())) {
//                // remove player from its tab group (if team tab group)
//                PlayerTab teamTab = tabList.getOrDefault(tabName, null);
//                if (null != teamTab) {
//                    teamTab.remove(player);
//
//                    // create or get tab group for the invisible players in that team
//                    // set tab group name visibility to false
//                    // identifier for invisibility
//                    tabName = tabNameInvisible;
//                }
//            }
//        } else {
//            PlayerTab invTab = tabList.getOrDefault(tabNameInvisible, null);
//            if (null != invTab) {
//                invTab.remove(player);
//            }
//        }
//
//        PlayerTab teamTab = tabList.get(tabName);
//        if (null == teamTab) {
//            String displayName = team.getDisplayName(Language.getPlayerLanguage(this.player));
//
//            HashMap<String, String> replacements = new HashMap<>();
//            replacements.put("{team}", team.getColor().chat() + displayName);
//            replacements.put("{teamLetter}", team.getColor().chat() + (displayName.substring(0, 1)));
//            replacements.put("{teamColor}", team.getColor().chat().toString());
//
//            prefix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, player, replacements);
//            suffix = getTabText(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, player, replacements);
//
//            teamTab = handle.playerTabCreate(tabName, null, prefix, suffix, PlayerTab.PushingRule.PUSH_OTHER_TEAMS);
//            tabList.put(tabName, teamTab);
//            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
//                teamTab.setNameTagVisibility(PlayerTab.NameTagVisibility.NEVER);
//            }
//        }
//
//        teamTab.add(player);
    }

    // Provide header and footer for current game state
//    private void setHeaderFooter() {
//        if (isTabFormattingDisabled()) {
//            return;
//        }
//        Language lang = Language.getPlayerLanguage(player);
//
//        if (noArena()) {
//            SidebarManager.getInstance().sendHeaderFooter(
//                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY),
//                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY)
//            );
//            return;
//        }
//        if (arena.isSpectator(player)) {
//            SidebarManager.getInstance().sendHeaderFooter(
//                    player, lang.m(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR),
//                    lang.m(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR)
//            );
//            return;
//        }
//
//
//        String headerPath = null;
//        String footerPath = null;
//
//        switch (arena.getStatus()) {
//            case waiting:
//                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING;
//                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING;
//                break;
//            case starting:
//                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING;
//                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING;
//                break;
//            case playing:
//                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING;
//                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING;
//                break;
//            case restarting:
//                headerPath = Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING;
//                footerPath = Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING;
//                break;
//        }
//
//        SidebarManager.getInstance().sendHeaderFooter(
//                player, lang.m(headerPath),
//                lang.m(footerPath)
//        );
//    }

//    private @NotNull String getTabName(@NotNull ITeam team) {
//        String tabName = TEAM_PREFIX + Base64.getEncoder().encodeToString((team.getName()).getBytes(StandardCharsets.UTF_8));
//        if (tabName.length() > 16) {
//            tabName = tabName.substring(0, 16);
//        }
//        return tabName;
//    }

//    @NotNull
//    private SidebarLine getTabText(String path, Player targetPlayer, @Nullable HashMap<String, String> replacements) {
//        List<String> strings = Language.getList(getPlayer(), path);
//        if (strings.isEmpty()) {
//            return new SidebarLine() {
//                @NotNull
//                @Override
//                public String getLine() {
//                    return "";
//                }
//            };
//        }
//
//        strings = new ArrayList<>();
//        for (String string : Language.getList(getPlayer(), path)) {
//            String parsed = string.replace("{vPrefix}", BedWars.getChatSupport().getPrefix(targetPlayer))
//                    .replace("{vSuffix}", BedWars.getChatSupport().getSuffix(targetPlayer));
//
//            if (null != replacements) {
//                for (Map.Entry<String, String> entry : replacements.entrySet()) {
//                    parsed = parsed.replace(entry.getKey(), entry.getValue());
//                }
//            }
//
//            strings.add(parsed);
//        }
//
//        if (strings.size() == 1) {
//            final String line = strings.get(0);
//            return new SidebarLine() {
//                @NotNull
//                @Override
//                public String getLine() {
//                    return line;
//                }
//            };
//        }
//
//        final String[] lines = new String[strings.size()];
//        for (int i = 0; i < lines.length; i++) {
//            lines[i] = strings.get(i);
//        }
//        return new SidebarLineAnimated(lines);
//    }

    /**
     * @return true if tab formatting is disabled for current sidebar/ arena stage
     */
    public boolean isTabFormattingDisabled() {
        if (noArena()) {

            if (getServerType() == ServerType.SHARED) {
                if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY) &&
                        !config.getLobbyWorldName().trim().isEmpty()) {

                    World lobby = Bukkit.getWorld(config.getLobbyWorldName());
                    return null != lobby;
                }
            }

            return !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY);
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
        return arena.getStatus() != GameState.restarting || !config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING);
    }

//    @Override
//    public boolean registerPersistentPlaceholder(PlaceholderProvider placeholderProvider) {
//        this.persistentProviders.add(placeholderProvider);
//        return true;
//    }

//    public void handleHealthIcon() {
//        if (null == handle) {
//            return;
//        }
//
//        if (noArena()) {
//            handle.hidePlayersHealth();
//            return;
//        } else if (arena.getStatus() != GameState.playing) {
//            handle.hidePlayersHealth();
//            return;
//        }
//
//        List<String> animation = Language.getList(player, Messages.FORMATTING_SCOREBOARD_HEALTH);
//        if (animation.isEmpty()) return;
//        SidebarLine line;
//        if (animation.size() > 1) {
//            String[] lines = new String[animation.size()];
//            for (int i = 0; i < animation.size(); i++) {
//                lines[i] = animation.get(i);
//            }
//            line = new SidebarLineAnimated(lines);
//        } else {
//            final String text = animation.get(0);
//            line = new SidebarLine() {
//                @NotNull
//                @Override
//                public String getLine() {
//                    return text;
//                }
//            };
//        }
//
//        if (config.getBoolean(ConfigPath.SB_CONFIG_SIDEBAR_HEALTH_IN_TAB)) {
//            handle.showPlayersHealth(line, true);
//        }
//
//        Bukkit.getScheduler().runTaskLater(plugin, () -> {
//            if (arena != null && handle != null) {
//                arena.getPlayers().forEach(player -> handle.setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
//                if (arena.isSpectator(getPlayer())) {
//                    arena.getSpectators().forEach(player -> handle.setPlayerHealth(player, (int) Math.ceil(player.getHealth())));
//                }
//            }
//        }, 20L);
//    }

    /**
     * Hide player name tag on head when he drinks an invisibility potion.
     * This is required because not all clients hide it automatically.
     *
     * @param _toggle true when applied, false when expired.
     */
    public void handleInvisibilityPotion(@NotNull Player player, boolean _toggle) {
        if (null == arena) {
            throw new RuntimeException("This can only be used when the player is in arena");
        }
        this.giveUpdateTabFormat(player, false);
    }

    public TabAPI getHandle() {
        return handle;
    }

    public IArena getArena() {
        return arena;
    }
}
