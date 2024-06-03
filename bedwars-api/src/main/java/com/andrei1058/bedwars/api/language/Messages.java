/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
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

package com.andrei1058.bedwars.api.language;

import com.andrei1058.bedwars.api.configuration.ConfigPath;

@SuppressWarnings("WeakerAccess")
public class Messages {

    public static String PREFIX = "prefix";

    /**
     * next event related
     */
    public static String NEXT_EVENT_DIAMOND_UPGRADE_II = "next-event-diamondII";
    public static String NEXT_EVENT_DIAMOND_UPGRADE_III = "next-event-diamondIII";
    public static String NEXT_EVENT_EMERALD_UPGRADE_II = "next-event-emeraldII";
    public static String NEXT_EVENT_EMERALD_UPGRADE_III = "next-event-emeraldIII";
    public static String NEXT_EVENT_BEDS_DESTROY = "next-event-beds-destroy";
    public static String NEXT_EVENT_DRAGON_SPAWN = "next-event-dragon-spawn";
    public static String NEXT_EVENT_GAME_END = "next-event-game-end";
    public static String NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED = "next-event-beds-destroy-title";
    public static String NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED = "next-event-beds-destroy-sub-title";
    public static String NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED = "next-event-beds-destroy-chat";
    public static String NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH = "next-event-sudden-death-title";
    public static String NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH = "next-event-sudden-death-sub-title";
    public static String NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH = "next-event-sudden-death-chat";

    /**
     * General commands reply
     */
    public static String COMMAND_MAIN = "cmd-main-list";
    public static String COMMAND_LANG_LIST_HEADER = "cmd-lang-list-header";
    public static String COMMAND_LANG_LIST_FORMAT = "cmd-lang-list-format";
    public static String COMMAND_LANG_USAGE = "cmd-lang-usage";
    public static String COMMAND_LANG_SELECTED_NOT_EXIST = "cmd-lang-not-exist";
    public static String COMMAND_LANG_SELECTED_SUCCESSFULLY = "cmd-lang-set";
    public static String COMMAND_LANG_USAGE_DENIED = "cmd-lang-not-set";
    public static String COMMAND_JOIN_USAGE = "cmd-join-usage";
    public static String COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND = "cmd-join-not-found";
    public static String COMMAND_JOIN_DENIED_IS_FULL = "cmd-join-arena-full";
    public static String COMMAND_JOIN_NO_EMPTY_FOUND = "cmd-join-arenas-full";
    public static String COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS = "cmd-join-arena-full-vips";
    public static String COMMAND_JOIN_DENIED_PARTY_TOO_BIG = "cmd-join-party-big";
    public static String COMMAND_JOIN_DENIED_NOT_PARTY_LEADER = "cmd-join-not-leader";
    public static String COMMAND_JOIN_PLAYER_JOIN_MSG = "cmd-join-success";
    public static String COMMAND_JOIN_SPECTATOR_MSG = "cmd-join-spectate";
    public static String COMMAND_JOIN_SPECTATOR_DENIED_MSG = "cmd-join-spectate-denied";
    public static String COMMAND_TP_PLAYER_NOT_FOUND = "cmd-tp-player-not-found";
    public static String COMMAND_TP_NOT_IN_ARENA = "cmd-tp-not-in-arena";
    public static String COMMAND_TP_NOT_STARTED = "cmd-tp-not-started";
    public static String COMMAND_TP_USAGE = "cmd-tp-usage";
    public static String REJOIN_NO_ARENA = "cmd-rejoin-no-arena";
    public static String REJOIN_DENIED = "cmd-rejoin-denied";
    public static String REJOIN_ALLOWED = "cmd-rejoin-allowed";
    public static String COMMAND_REJOIN_PLAYER_RECONNECTED = "cmd-rejoin-player-reconnected";
    public static String COMMAND_LEAVE_MSG = "cmd-leave";
    public static String COMMAND_NOT_ALLOWED_IN_GAME = "cmd-blocked-in-game";
    public static String COMMAND_LEAVE_DENIED_NOT_IN_ARENA = "cmd-not-in-arena";
    public static String COMMAND_PARTY_HELP = "cmd-party-help";
    public static String COMMAND_PARTY_INVITE_USAGE = "cmd-party-invite-usage";
    public static String COMMAND_PARTY_INVITE_SENT = "cmd-party-invite";
    public static String COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE = "cmd-party-offline";
    public static String COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG = "cmd-party-invite-received";
    public static String COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF = "cmd-party-invite-self";
    public static String COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE = "cmd-party-no-invite";
    public static String COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY = "cmd-party-already-in";
    public static String COMMAND_PARTY_INSUFFICIENT_PERMISSIONS = "cmd-party-no-perm";
    public static String COMMAND_PARTY_ACCEPT_USAGE = "cmd-party-accept-usage";
    public static String COMMAND_PARTY_ACCEPT_SUCCESS = "cmd-party-join";
    public static String COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY = "cmd-party-not-in";
    public static String COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND = "cmd-party-cant-leave";
    public static String COMMAND_PARTY_LEAVE_SUCCESS = "cmd-party-leave";
    public static String COMMAND_PARTY_DISBAND_SUCCESS = "cmd-party-disband";
    public static String COMMAND_PARTY_REMOVE_USAGE = "cmd-party-remove-usage";
    public static String COMMAND_PARTY_REMOVE_SUCCESS = "cmd-party-remove";
    public static String COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER = "cmd-party-remove-not-in";
    public static String COMMAND_PARTY_PROMOTE_SUCCESS = "cmd-party-promote-owner";
    public static String COMMAND_PARTY_PROMOTE_OWNER = "cmd-party-owner";
    public static String COMMAND_PARTY_PROMOTE_NEW_OWNER = "cmd-party-owner-changed";
    public static String COMMAND_PARTY_INFO_OWNER = "cmd-party-info-owner";
    public static String COMMAND_PARTY_INFO_PLAYERS = "cmd-party-info-players";
    public static String COMMAND_PARTY_INFO_PLAYER = "cmd-party-info-player";
    public static String COMMAND_NOT_FOUND_OR_INSUFF_PERMS = "cmd-not-found";
    public static String COMMAND_FORCESTART_NOT_IN_GAME = "cmd-start-no-game";
    public static String COMMAND_FORCESTART_SUCCESS = "cmd-start";
    public static String COMMAND_FORCESTART_NO_PERM = "cmd-start-no-perm";
    public static String COMMAND_COOLDOWN = "cmd-cooldown";

    /**
     * Arena join/ leave related
     */
    public static String ARENA_JOIN_VIP_KICK = "arena-kicked-by-vip";
    public static String ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT = "arena-countdown-stopped";
    //public static String ARENA_PLAYER_QUIT = "player.quit";
    public static String ARENA_RESTART_PLAYER_KICK = "arena-restart-kick";
    public static String ARENA_JOIN_DENIED_SELECTOR = "arena-join-denied-selector";
    public static String ARENA_JOIN_DENIED_NO_PROXY = "arena-join-denied-no-bwp";
    public static String ARENA_SPECTATE_DENIED_SELECTOR = "arena-spectate-denied-selector";
    public static String ARENA_LEAVE_PARTY_DISBANDED = "arena-leave-party-disbanded";

    /**
     * Arena status/ status change related
     */
    public static String ARENA_STATUS_WAITING_NAME = "arena-status-waiting";
    public static String ARENA_STATUS_STARTING_NAME = "arena-status-starting";
    public static String ARENA_STATUS_PLAYING_NAME = "arena-status-playing";
    public static String ARENA_STATUS_RESTARTING_NAME = "arena-status-restarting";
    public static String ARENA_STATUS_START_PLAYER_TITLE = "arena-start-title";
    public static String ARENA_STATUS_START_PLAYER_TUTORIAL = "arena-start-tutorial";
    public static String ARENA_STATUS_START_COUNTDOWN_CHAT = "arena-start-countdown";
    public static String ARENA_STATUS_START_COUNTDOWN_TITLE = "arena-start-countdown-title";
    public static String ARENA_STATUS_START_COUNTDOWN_SUB_TITLE = "arena-start-countdown-subtitle";
    public static String ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE = "arena-countdown-stopped-subtitle";
    public static String ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE = "arena-countdown-stopped-subtitle";
    public static String ARENA_DISPLAY_GROUP_PATH = "display-group-";

    /**
     * Arena GUI related
     */
    public static String ARENA_GUI_INV_NAME = "arena-selector-gui-name";
    public static String ARENA_GUI_ARENA_CONTENT_NAME = "arena-selector-content-name";
    public static String ARENA_GUI_ARENA_CONTENT_LORE = "arena-selector-content-lore";
    public static String ARENA_GUI_SKIPPED_ITEM_NAME = "arena-selector-skipped-item-name";
    public static String ARENA_GUI_SKIPPED_ITEM_LORE = "arena-selector-skipped-item-lore";

    /**
     * Spectator related
     */
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_NAME = "spectator-tp-gui-name";
    //{player} - returns display name, {prefix} - returns the player rank
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME = "spectator-tp-gui-head-name";
    //{health}, {food}
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE = "spectator-tp-gui-head-lore";

    public static String ARENA_SPECTATOR_LEAVE_ITEM_NAME = "spectator-tp-name";
    public static String ARENA_SPECTATOR_LEAVE_ITEM_LORE = "spectator-tp-lore";

    public static String ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE = "spectator-first-person-enter-title";
    public static String ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE = "spectator-first-person-enter-subtitle";
    public static String ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE = "spectator-first-person-quit-title";
    public static String ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE = "spectator-first-person-quit-subtitle";

    /**
     * Stats related
     */
    public static String PLAYER_STATS_GUI_PATH = "stats";
    public static String PLAYER_STATS_GUI_INV_NAME = PLAYER_STATS_GUI_PATH + "-inv-name";

    /**
     * Arena generators related
     */
    public static String GENERATOR_HOLOGRAM_TIER = "generator-tier";
    public static String GENERATOR_HOLOGRAM_TYPE_DIAMOND = "generator-diamond";
    public static String GENERATOR_HOLOGRAM_TYPE_EMERALD = "generator-emerald";
    public static String GENERATOR_HOLOGRAM_TIMER = "generator-timer";
    public static String GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT = "generator-upgrade-announce";

    /**
     * General formatting
     */
    public static String FORMATTING_CHAT_LOBBY = "format-chat-lobby";
    public static String FORMATTING_CHAT_WAITING = "format-chat-waiting";
    public static String FORMATTING_CHAT_SHOUT = "format-chat-global";
    public static String FORMATTING_CHAT_TEAM = "format-chat-team";
    public static String FORMATTING_CHAT_SPECTATOR = "format-chat-spectator";
    public static String FORMATTING_SCOREBOARD_DATE = "format-sb-date";
    public static String FORMATTING_SCOREBOARD_TEAM_GENERIC = "format-sb-team-generic";
    public static String FORMATTING_SCOREBOARD_HEALTH = "format-sb-health";

    // TAB
    public static String FORMATTING_SB_TAB_BASE = "format-tab";

    // TAB LOBBY
    public static String FORMATTING_SB_TAB_LOBBY_HEADER = FORMATTING_SB_TAB_BASE + ".lobby.header";
    public static String FORMATTING_SB_TAB_LOBBY_FOOTER = FORMATTING_SB_TAB_BASE + ".lobby.footer";
    public static String FORMATTING_SB_TAB_LOBBY_PREFIX = FORMATTING_SB_TAB_BASE + ".lobby.prefix";
    public static String FORMATTING_SB_TAB_LOBBY_SUFFIX = FORMATTING_SB_TAB_BASE + ".lobby.suffix";

    // TAB WAITING
    public static String FORMATTING_SB_TAB_WAITING_HEADER = FORMATTING_SB_TAB_BASE+".waiting.player.header";
    public static String FORMATTING_SB_TAB_WAITING_FOOTER = FORMATTING_SB_TAB_BASE+".waiting.player.footer";
    public static String FORMATTING_SB_TAB_WAITING_PREFIX = FORMATTING_SB_TAB_BASE+".waiting.player.prefix";
    public static String FORMATTING_SB_TAB_WAITING_SUFFIX = FORMATTING_SB_TAB_BASE+".waiting.player.suffix";
    // TAB WAITING FOR SPECTATORS
    public static String FORMATTING_SB_TAB_WAITING_HEADER_SPEC = FORMATTING_SB_TAB_BASE+".waiting.spectator.header";
    public static String FORMATTING_SB_TAB_WAITING_FOOTER_SPEC = FORMATTING_SB_TAB_BASE+".waiting.spectator.footer";
    public static String FORMATTING_SB_TAB_WAITING_PREFIX_SPEC = FORMATTING_SB_TAB_BASE+".waiting.spectator.prefix";
    public static String FORMATTING_SB_TAB_WAITING_SUFFIX_SPEC = FORMATTING_SB_TAB_BASE+".waiting.spectator.suffix";

    // TAB STARTING
    public static String FORMATTING_SB_TAB_STARTING_HEADER = FORMATTING_SB_TAB_BASE+".starting.player.header";
    public static String FORMATTING_SB_TAB_STARTING_FOOTER = FORMATTING_SB_TAB_BASE+".starting.player.footer";
    public static String FORMATTING_SB_TAB_STARTING_PREFIX = FORMATTING_SB_TAB_BASE+".starting.player.prefix";
    public static String FORMATTING_SB_TAB_STARTING_SUFFIX = FORMATTING_SB_TAB_BASE+".starting.player.suffix";
    // TAB STARTING FOR SPECTATORS
    public static String FORMATTING_SB_TAB_STARTING_HEADER_SPEC = FORMATTING_SB_TAB_BASE+".starting.spectator.header";
    public static String FORMATTING_SB_TAB_STARTING_FOOTER_SPEC = FORMATTING_SB_TAB_BASE+".starting.spectator.footer";
    public static String FORMATTING_SB_TAB_STARTING_PREFIX_SPEC = FORMATTING_SB_TAB_BASE+".starting.player.prefix";
    public static String FORMATTING_SB_TAB_STARTING_SUFFIX_SPEC = FORMATTING_SB_TAB_BASE+".starting.player.suffix";

    // TAB PLAYING
    public static String FORMATTING_SB_TAB_PLAYING_HEADER = FORMATTING_SB_TAB_BASE+".playing.alive.header";
    public static String FORMATTING_SB_TAB_PLAYING_FOOTER = FORMATTING_SB_TAB_BASE+".playing.alive.footer";
    public static String FORMATTING_SB_TAB_PLAYING_PREFIX = FORMATTING_SB_TAB_BASE+".playing.alive.prefix";
    public static String FORMATTING_SB_TAB_PLAYING_SUFFIX = FORMATTING_SB_TAB_BASE+".playing.alive.suffix";

    // TAB PLAYING-ELIMINATED
    public static String FORMATTING_SB_TAB_PLAYING_ELM_HEADER = FORMATTING_SB_TAB_BASE+".playing.eliminated.header";
    public static String FORMATTING_SB_TAB_PLAYING_ELM_FOOTER = FORMATTING_SB_TAB_BASE+".playing.eliminated.footer";
    public static String FORMATTING_SB_TAB_PLAYING_ELM_PREFIX = FORMATTING_SB_TAB_BASE+".playing.eliminated.prefix";
    public static String FORMATTING_SB_TAB_PLAYING_ELM_SUFFIX = FORMATTING_SB_TAB_BASE+".playing.eliminated.suffix";

    // TAB PLAYING FOR SPECTATORS
    public static String FORMATTING_SB_TAB_PLAYING_SPEC_HEADER = FORMATTING_SB_TAB_BASE+".playing.spectator.header";
    public static String FORMATTING_SB_TAB_PLAYING_SPEC_FOOTER = FORMATTING_SB_TAB_BASE+".playing.spectator.footer";
    public static String FORMATTING_SB_TAB_PLAYING_SPEC_PREFIX = FORMATTING_SB_TAB_BASE+".playing.spectator.prefix";
    public static String FORMATTING_SB_TAB_PLAYING_SPEC_SUFFIX = FORMATTING_SB_TAB_BASE+".playing.spectator.suffix";

    // TAB RESTARTING FOR WINNERS ALIVE
    public static String FORMATTING_SB_TAB_RESTARTING_WIN1_HEADER = FORMATTING_SB_TAB_BASE+".restarting.winner-alive.header";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN1_FOOTER = FORMATTING_SB_TAB_BASE+".restarting.winner-alive.footer";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN1_PREFIX = FORMATTING_SB_TAB_BASE+".restarting.winner-alive.prefix";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN1_SUFFIX = FORMATTING_SB_TAB_BASE+".restarting.winner-alive.suffix";

    // TAB RESTARTING FOR WINNERS DEAD
    public static String FORMATTING_SB_TAB_RESTARTING_WIN2_HEADER = FORMATTING_SB_TAB_BASE+".restarting.winner-dead.header";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN2_FOOTER = FORMATTING_SB_TAB_BASE+".restarting.winner-dead.footer";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN2_PREFIX = FORMATTING_SB_TAB_BASE+".restarting.winner-dead.prefix";
    public static String FORMATTING_SB_TAB_RESTARTING_WIN2_SUFFIX = FORMATTING_SB_TAB_BASE+".restarting.winner-dead.suffix";


    // TAB RESTARTING FOR LOSERS
    public static String FORMATTING_SB_TAB_RESTARTING_ELM_HEADER = FORMATTING_SB_TAB_BASE+".restarting.loser.header";
    public static String FORMATTING_SB_TAB_RESTARTING_ELM_FOOTER = FORMATTING_SB_TAB_BASE+".restarting.loser.footer";
    public static String FORMATTING_SB_TAB_RESTARTING_ELM_PREFIX = FORMATTING_SB_TAB_BASE+".restarting.loser.prefix";
    public static String FORMATTING_SB_TAB_RESTARTING_ELM_SUFFIX = FORMATTING_SB_TAB_BASE+".restarting.loser.suffix";

    // TAB RESTARTING FOR SPECTATORS
    public static String FORMATTING_SB_TAB_RESTARTING_SPEC_HEADER = FORMATTING_SB_TAB_BASE+".restarting.spectator.header";
    public static String FORMATTING_SB_TAB_RESTARTING_SPEC_FOOTER = FORMATTING_SB_TAB_BASE+".restarting.spectator.footer";
    public static String FORMATTING_SB_TAB_RESTARTING_SPEC_PREFIX = FORMATTING_SB_TAB_BASE+".restarting.spectator.prefix";
    public static String FORMATTING_SB_TAB_RESTARTING_SPEC_SUFFIX = FORMATTING_SB_TAB_BASE+".restarting.spectator.suffix";

    public static String FORMATTING_SCOREBOARD_TEAM_ELIMINATED = "format-sb-team-eliminated";
    public static String FORMATTING_SCOREBOARD_BED_DESTROYED = "format-sb-bed-destroyed";
    public static String FORMATTING_SCOREBOARD_TEAM_ALIVE = "format-sb-team-alive";
    public static String FORMATTING_SCOREBOARD_NEXEVENT_TIMER = "format-sb-generator-timer";
    public static String FORMATTING_SCOREBOARD_YOUR_TEAM = "format-sb-you";
    public static String FORMATTING_ACTION_BAR_TRACKING = "format-action-tracking";
    public static String FORMATTING_TEAM_WINNER_FORMAT = "format-winner-team";
    public static String FORMATTING_SOLO_WINNER_FORMAT = "format-winner-solo";
    //public static String FORMATTING_TAB_LIST = "format.tablist";
    public static String FORMATTING_GENERATOR_TIER1 = "format-tier1";
    public static String FORMATTING_GENERATOR_TIER2 = "format-tier2";
    public static String FORMATTING_GENERATOR_TIER3 = "format-tier3";
    public static String FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH = "format-despawnable-health";
    public static String FORMATTING_STATS_DATE_FORMAT = "format-stats-time";
    public static String FORMAT_PAPI_PLAYER_TEAM_TEAM = "format-papi-player-team";
    public static String FORMAT_PAPI_PLAYER_TEAM_SHOUT = "format-papi-player-shout";
    public static String FORMAT_PAPI_PLAYER_TEAM_SPECTATOR = "format-papi-player-spectator";
    public static String FORMAT_UPGRADE_TIER_LOCKED = "format-tier-color-locked";
    public static String FORMAT_UPGRADE_TIER_UNLOCKED = "format-tier-color-unlocked";
    public static String FORMAT_UPGRADE_COLOR_CANT_AFFORD = "format-upgrade-color-cant-afford";
    public static String FORMAT_UPGRADE_COLOR_CAN_AFFORD = "format-upgrade-color-can-afford";
    public static String FORMAT_UPGRADE_COLOR_UNLOCKED = "format-upgrade-color-unlocked";
    public static String FORMAT_UPGRADE_TRAP_COST = "format-upgrade-trap-cost";
    public static String FORMAT_SPECTATOR_TARGET = "format-spectator-target";

    /**
     * Meaning/ Translations
     */
    public static String MEANING_FULL = "meaning-full";
    public static String MEANING_SHOUT = "meaning-shout";
    public static String MEANING_NEVER = "meaning-never";
    public static String MEANING_NOBODY = "meaning-nobody";
    public static String MEANING_IRON_SINGULAR = "meaning-iron-singular";
    public static String MEANING_IRON_PLURAL = "meaning-iron-plural";
    public static String MEANING_GOLD_SINGULAR = "meaning-gold-singular";
    public static String MEANING_GOLD_PLURAL = "meaning-gold-plural";
    public static String MEANING_EMERALD_SINGULAR = "meaning-emerald-singular";
    public static String MEANING_EMERALD_PLURAL = "meaning-emerald-plural";
    public static String MEANING_DIAMOND_SINGULAR = "meaning-diamond-singular";
    public static String MEANING_DIAMOND_PLURAL = "meaning-diamond-plural";
    public static String MEANING_VAULT_SINGULAR = "meaning-vault-singular";
    public static String MEANING_VAULT_PLURAL = "meaning-vault-plural";
    public static String MEANING_NO_TRAP = "meaning-no-trap";

    /**
     * Scoreboard related
     */
    public static String SCOREBOARD_LOBBY = "sidebar.lobby";
    public static String SCOREBOARD_DEFAULT_WAITING = "sidebar.Default.waiting.player";
    public static String SCOREBOARD_DEFAULT_WAITING_SPEC = "sidebar.Default.waiting.spectator";
    public static String SCOREBOARD_DEFAULT_STARTING = "sidebar.Default.starting.player";
    public static String SCOREBOARD_DEFAULT_STARTING_SPEC = "sidebar.Default.starting.spectator";
    public static String SCOREBOARD_DEFAULT_PLAYING = "sidebar.Default.playing.alive";
    public static String SCOREBOARD_DEFAULT_PLAYING_SPEC = "sidebar.Default.playing.spectator";
    public static String SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED = "sidebar.Default.playing.eliminated";

    public static String SCOREBOARD_DEFAULT_RESTARTING_SPEC = "sidebar.Default.restarting.spectator";
    public static String SCOREBOARD_DEFAULT_RESTARTING_WIN1 = "sidebar.Default.restarting.winner-alive";
    public static String SCOREBOARD_DEFAULT_RESTARTING_WIN2 = "sidebar.Default.restarting.winner-eliminated";
    public static String SCOREBOARD_DEFAULT_RESTARTING_LOSER = "sidebar.Default.restarting.loser";

    /**
     * Player interact related
     */
    public static String INTERACT_CANNOT_PLACE_BLOCK = "interact-cant-place";
    public static String INTERACT_CANNOT_BREAK_BLOCK = "interact-cant-break";
    public static String INTERACT_CANNOT_BREAK_OWN_BED = "interact-cant-destroy-bed";
    public static String INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT = "interact-bed-destroy-chat";
    public static String INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT = "interact-bed-destroy-title";
    public static String INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT = "interact-bed-destroy-subtitle";
    public static String INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM = "interact-bed-destroy-team";
    public static String INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED = "interact-cant-open-chest";
    public static String INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN = "interact-invisibility-removed-damaged";

    /**
     * PvP related
     */
    public static String PLAYER_DIE_RESPAWN_TITLE = "player-respawn-title";
    public static String PLAYER_DIE_RESPAWN_SUBTITLE = "player-respawn-subtitle";
    public static String PLAYER_DIE_RESPAWN_CHAT = "player-respawn-timer-chat";
    public static String PLAYER_DIE_RESPAWNED_TITLE = "player-respawned-title";
    public static String PLAYER_DIE_ELIMINATED_CHAT = "player-eliminated-chat";

    public static String PLAYER_DIE_VOID_FALL_REGULAR_KILL = "player-die-void-regular";
    public static String PLAYER_DIE_VOID_FALL_FINAL_KILL = "player-die-void-final";
    public static String PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL = "player-die-knocked-void-regular";
    public static String PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL = "player-die-knocked-void-final";
    public static String PLAYER_DIE_KNOCKED_BY_REGULAR_KILL = "player-die-knocked-fall-regular";
    public static String PLAYER_DIE_KNOCKED_BY_FINAL_KILL = "player-die-knocked-fall-final";
    public static String PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL = "player-die-bomb-regular";
    public static String PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL = "player-die-bomb-final";
    public static String PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR = "player-die-bomb2-regular";
    public static String PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL = "player-die-bomb2-final";
    public static String PLAYER_DIE_PVP_REGULAR_KILL = "player-die-attack-regular";
    public static String PLAYER_DIE_PVP_FINAL_KILL = "player-die-attack-final";
    public static String PLAYER_DIE_UNKNOWN_REASON_REGULAR = "player-die-unknown-regular";
    public static String PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL = "player-die-unknown-final";
    public static String PLAYER_DIE_SHOOT_REGULAR = "player-die-shoot-regular";
    public static String PLAYER_DIE_SHOOT_FINAL_KILL = "player-die-shoot-final";
    public static String PLAYER_DIE_DEBUG_REGULAR = "player-die-bedbug-regular";
    public static String PLAYER_DIE_DEBUG_FINAL_KILL = "player-die-bedbug-final";
    public static String PLAYER_DIE_IRON_GOLEM_REGULAR = "player-die-golem-regular";
    public static String PLAYER_DIE_IRON_GOLEM_FINAL_KILL = "player-die-golem-final";
    public static String PLAYER_DIE_PVP_LOG_OUT_REGULAR = "player-die-pvp-log-out-regular";
    public static String PLAYER_DIE_PVP_LOG_OUT_FINAL = "player-die-pvp-log-out-final";

    public static String PLAYER_DIE_REWARD_DIAMOND = "player-loot-diamond";
    public static String PLAYER_DIE_REWARD_IRON = "player-loot-iron";
    public static String PLAYER_DIE_REWARD_GOLD = "player-loot-gold";
    public static String PLAYER_DIE_REWARD_EMERALD = "player-loot-emerald";

    public static String PLAYER_HIT_BOW = "player-hit-bow";

    /**
     * Misc
     */
    public static String GAME_END_GAME_OVER_PLAYER_TITLE = "game-end-game-over-title";
    public static String GAME_END_VICTORY_PLAYER_TITLE = "game-end-victory-title";
    public static String GAME_END_TOP_PLAYER_CHAT = "game-end-top-chat";
    public static String GAME_END_TEAM_WON_CHAT = "game-end-winner-team";
    public static String XP_REWARD_WIN = "xp-reward-game-win";
    public static String XP_REWARD_PER_TEAMMATE = "xp-reward-per-teammate";
    public static String XP_REWARD_PER_MINUTE = "xp-reward-per-minute";
    public static String XP_REWARD_BED_DESTROY = "xp-reward-bed-destroyed";
    public static String XP_REWARD_REGULAR_KILL = "xp-reward-regular-kill";
    public static String XP_REWARD_FINAL_KILL = "xp-reward-final-kill";

    public static String MONEY_REWARD_WIN = "money-reward-game-win";
    public static String MONEY_REWARD_PER_MINUTE = "money-reward-per-minute";
    public static String MONEY_REWARD_PER_TEAMMATE = "money-reward-per-teammate";
    public static String MONEY_REWARD_BED_DESTROYED = "money-reward-bed-destroyed";
    public static String MONEY_REWARD_FINAL_KILL = "money-reward-final-kill";
    public static String MONEY_REWARD_REGULAR_KILL = "money-reward-regular-kill";

    public static String BED_HOLOGRAM_DEFEND = "bed-hologram-defend";
    public static String BED_HOLOGRAM_DESTROYED = "bed-hologram-destroyed";
    public static String TEAM_ELIMINATED_CHAT = "team-eliminated";


    /**
     * Upgrades/ Shop
     */
    public static String NPC_NAME_TEAM_UPGRADES = "npc-team-upgrades";
    public static String NPC_NAME_TEAM_SHOP = "npc-team-shop";
    public static String NPC_NAME_SOLO_UPGRADES = "npc-solo-upgrades";
    public static String NPC_NAME_SOLO_SHOP = "npc-solo-shop";

    public static String UPGRADES_MENU_GUI_NAME_PATH = "upgrades-menu-gui-name-";
    public static String UPGRADES_CATEGORY_GUI_NAME_PATH = "upgrades-category-gui-name-";
    public static String UPGRADES_CATEGORY_ITEM_NAME_PATH = "upgrades-category-item-name-";
    public static String UPGRADES_CATEGORY_ITEM_LORE_PATH = "upgrades-category-item-lore-";
    public static String UPGRADES_SEPARATOR_ITEM_NAME_PATH = "upgrades-separator-item-name-";
    public static String UPGRADES_SEPARATOR_ITEM_LORE_PATH = "upgrades-separator-item-lore-";
    public static String UPGRADES_TRAP_SLOT_ITEM_NAME_PATH = "upgrades-trap-slot-item-name-";
    public static String UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH = "upgrades-trap-slot-item-lore1-";
    public static String UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH = "upgrades-trap-slot-item-lore2-";
    public static String UPGRADES_UPGRADE_TIER_ITEM_NAME = "upgrades-upgrade-name-{name}-{tier}";
    public static String UPGRADES_UPGRADE_TIER_ITEM_LORE = "upgrades-upgrade-lore-{name}";
    public static String UPGRADES_BASE_TRAP_ITEM_NAME_PATH = "upgrades-base-trap-name-";
    public static String UPGRADES_BASE_TRAP_ITEM_LORE_PATH = "upgrades-base-trap-lore-";
    public static String UPGRADES_TRAP_CUSTOM_TITLE = "upgrades-base-trap-title-";
    public static String UPGRADES_TRAP_CUSTOM_SUBTITLE = "upgrades-base-trap-subtitle-";
    public static String UPGRADES_TRAP_CUSTOM_MSG = "upgrades-base-trap-msg-";

    public static String UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY = "upgrades-lore-insuff-money";
    public static String UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY = "upgrades-lore-click-buy";
    public static String UPGRADES_LORE_REPLACEMENT_UNLOCKED = "upgrades-lore-unlocked";
    public static String UPGRADES_LORE_REPLACEMENT_LOCKED = "upgrades-lore-locked";
    public static String UPGRADES_UPGRADE_BOUGHT_CHAT = "upgrades-new-purchase";
    public static String UPGRADES_TRAP_QUEUE_LIMIT = "upgrades-trap-queue-full";
    public static String UPGRADES_TRAP_DEFAULT_TITLE = "upgrades-trap-default-title";
    public static String UPGRADES_TRAP_DEFAULT_SUBTITLE = "upgrades-trap-default-subtitle";
    public static String UPGRADES_TRAP_DEFAULT_MSG = "upgrades-trap-default-msg";

    public static String SHOP_NEW_PURCHASE = "shop-new-purchase";
    public static String SHOP_UTILITY_NPC_SILVERFISH_NAME = "shop-utility-silverfish";
    public static String SHOP_UTILITY_NPC_IRON_GOLEM_NAME = "shop-utility-iron-golem";
    public static String SHOP_INSUFFICIENT_MONEY = "shop-insuff-money";
    public static String SHOP_ALREADY_BOUGHT = "shop-already-bought";
    public static final String SHOP_PATH = "shop-items-messages";
    public static final String SHOP_LORE_STATUS_CANT_AFFORD = "shop-lore-status-cant-afford";
    public static final String SHOP_LORE_STATUS_CAN_BUY = "shop-lore-status-can-buy";
    public static final String SHOP_LORE_STATUS_MAXED = "shop-lore-status-tier-maxed";
    public static final String SHOP_LORE_STATUS_ARMOR = "shop-lore-status-armor";
    public static final String SHOP_LORE_QUICK_ADD = "shop-lore-quick-add";
    public static final String SHOP_LORE_QUICK_REMOVE = "shop-lore-quick-remove";
    public static final String SHOP_INDEX_NAME = SHOP_PATH + ".inventory-name";
    public static final String SHOP_QUICK_ADD_NAME = SHOP_PATH + ".quick-buy-add-inventory-name";
    public static final String SHOP_SEPARATOR_NAME = SHOP_PATH + ".separator-item-name";
    public static final String SHOP_SEPARATOR_LORE = SHOP_PATH + ".separator-item-lore";
    public static final String SHOP_QUICK_BUY_NAME = SHOP_PATH + ".quick-buy-item-name";
    public static final String SHOP_QUICK_BUY_LORE = SHOP_PATH + ".quick-buy-item-lore";
    public static final String SHOP_QUICK_EMPTY_NAME = SHOP_PATH + ".quick-buy-empty-item-name";
    public static final String SHOP_QUICK_EMPTY_LORE = SHOP_PATH + ".quick-buy-empty-item-lore";

    public static final String SHOP_CATEGORY_INVENTORY_NAME = SHOP_PATH + ".%category%.inventory-name";
    public static final String SHOP_CATEGORY_ITEM_NAME = SHOP_PATH + ".%category%.category-item-name";
    public static final String SHOP_CATEGORY_ITEM_LORE = SHOP_PATH + ".%category%.category-item-lore";
    public static final String SHOP_CONTENT_TIER_ITEM_NAME = SHOP_PATH + ".%category%.content-item-%content%-name";
    public static final String SHOP_CONTENT_TIER_ITEM_LORE = SHOP_PATH + ".%category%.content-item-%content%-lore";
    public static final String SHOP_CAN_BUY_COLOR = SHOP_PATH + ".can-buy-color";
    public static final String SHOP_CANT_BUY_COLOR = SHOP_PATH + ".cant-buy-color";

    /* MultiArena Lobby Item Messages */
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + "-%path%-name";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + "-%path%-lore";

    /* Spectator Items Messages */
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + "-%path%-name";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + "-%path%-lore";

    /* Arena waiting Items Messages */
    public static final String GENERAL_CONFIGURATION_WAITING_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + "-%path%-name";
    public static final String GENERAL_CONFIGURATION_WAITING_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + "-%path%-lore";



    // DEPRECATIONS FOR REMOVAL

    // LOBBY TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_LOBBY = FORMATTING_SB_TAB_LOBBY_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY = FORMATTING_SB_TAB_LOBBY_FOOTER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY = FORMATTING_SB_TAB_LOBBY_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY = FORMATTING_SB_TAB_LOBBY_SUFFIX;
    // WAITING TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_WAITING = FORMATTING_SB_TAB_WAITING_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_WAITING = FORMATTING_SB_TAB_WAITING_FOOTER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING = FORMATTING_SB_TAB_WAITING_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING = FORMATTING_SB_TAB_WAITING_SUFFIX;
    // STARTING TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_STARTING = FORMATTING_SB_TAB_STARTING_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_STARTING = FORMATTING_SB_TAB_STARTING_FOOTER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING = FORMATTING_SB_TAB_STARTING_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING = FORMATTING_SB_TAB_STARTING_SUFFIX;
    // PLAYING TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_PLAYING = FORMATTING_SB_TAB_PLAYING_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING = FORMATTING_SB_TAB_PLAYING_FOOTER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING = FORMATTING_SB_TAB_PLAYING_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING = FORMATTING_SB_TAB_PLAYING_SUFFIX;
    // SPECTATING TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR = FORMATTING_SB_TAB_PLAYING_SPEC_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR = FORMATTING_SB_TAB_PLAYING_SPEC_FOOTER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR = FORMATTING_SB_TAB_PLAYING_SPEC_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR = FORMATTING_SB_TAB_PLAYING_SPEC_SUFFIX;
    // RESTARTING TAB
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_PRESTARTING = FORMATTING_SB_TAB_RESTARTING_WIN2_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING = FORMATTING_SB_TAB_RESTARTING_WIN2_PREFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_PRESTARTING = FORMATTING_SB_TAB_RESTARTING_WIN2_SUFFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING = FORMATTING_SB_TAB_RESTARTING_WIN2_SUFFIX;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING = FORMATTING_SB_TAB_RESTARTING_ELM_HEADER;
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING = FORMATTING_SB_TAB_PLAYING_SPEC_HEADER;

    @Deprecated(forRemoval = true)
    public static String FORMATTING_SPECTATOR_TEAM = "format-spectator-team";
    @Deprecated(forRemoval = true)
    public static String FORMATTING_SPECTATOR_COLOR = FORMATTING_SPECTATOR_TEAM;
}
