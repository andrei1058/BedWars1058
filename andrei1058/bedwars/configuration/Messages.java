package com.andrei1058.bedwars.configuration;

public class Messages {

    /** next event related */
    public static String NEXT_EVENT_DIAMOND_UPGRADE_II = "nextEvent.diamondII";//todo de mutat astea la scoreboard related
    public static String NEXT_EVENT_DIAMOND_UPGRADE_III = "nextEvent.diamondIII";
    public static String NEXT_EVENT_EMERALD_UPGRADE_II = "nextEvent.emeraldII";
    public static String NEXT_EVENT_EMERALD_UPGRADE_III = "nextEvent.emeraldIII";
    public static String NEXT_EVENT_BEDS_DESTROY = "nextEvent.bedsDestroy";
    public static String NEXT_EVENT_DRAGON_SPAWN = "nextEvent.dragonSpawn";
    public static String NEXT_EVENT_GAME_END = "nextEvent.gameEnd";
    public static String NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED = "arena.bedsDestroyed.title";
    public static String NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED = "arena.bedsDestroyed.subTitle";
    public static String NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED = "arena.bedsDestroyed.chat";
    public static String NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH = "arena.suddenDeath.title";
    public static String NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH = "arena.suddenDeath.subTitle";
    public static String NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH = "arena.suddenDeath.chat";

    /** General commands reply */
    public static String COMMAND_MAIN = "cmd.main";
    public static String COMMAND_LANG_LIST_HEADER = "lang.listHeader";
    public static String COMMAND_LANG_LIST_FORMAT = "lang.listFormat";
    public static String COMMAND_LANG_USAGE = "player.langUsage";
    public static String COMMAND_LANG_SELECTED_NOT_EXIST = "player.langNotExist";
    public static String COMMAND_LANG_SELECTED_SUCCESSFULLY = "player.langSet";
    public static String COMMAND_LANG_USAGE_DENIED = "lang.cantChange";
    public static String COMMAND_JOIN_USAGE = "cmd.joinUsage";
    public static String COMMAND_NOT_ALLOWED_IN_GAME = "player.permDenied";
    public static String COMMAND_LEAVE_DENIED_NOT_IN_ARENA = "player.notInArena";
    public static String COMMAND_PARTY_HELP = "party.help";
    public static String COMMAND_PARTY_INVITE_USAGE = "party.inviteUsage";
    public static String COMMAND_PARTY_INVITE_SENT = "party.inviteSent";
    public static String COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE = "party.playerOffline";
    public static String COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG = "party.inviteReceived";
    public static String COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF = "party.invYourself";
    public static String COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE = "party.noInvite";
    public static String COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY = "party.alreadyIn";
    public static String COMMAND_PARTY_INSUFFICIENT_PERMISSIONS = "party.noPerm";
    public static String COMMAND_PARTY_ACCEPT_USAGE = "party.acceptUsage";
    public static String COMMAND_PARTY_ACCEPT_SUCCESS = "party.joined";
    public static String COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY = "party.notIn";
    public static String COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND = "party.cantLeaveOwner";
    public static String COMMAND_PARTY_LEAVE_SUCCESS = "party.leaved";
    public static String COMMAND_PARTY_DISBAND_SUCCESS = "party.disband";
    public static String COMMAND_PARTY_REMOVE_USAGE = "party.removeUsage";
    public static String COMMAND_PARTY_REMOVE_SUCCESS = "party.removed";
    public static String COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER = "player.removeNotIn";
    public static String COMMAND_NOT_FOUND_OR_INSUFF_PERMS = "cmd.notFound";
    public static String COMMAND_FORCESTART_NOT_IN_GAME = "cmd.forcestart.notInGame";
    public static String COMMAND_FORCESTART_SUCCESS = "cmd.forcestart.success";
    public static String COMMAND_FORCESTART_NO_PERM = "cmd.forcestart.noPerm";
    public static String COMMAND_COOLDOWN = "cmd.cooldown";

    /** Arena join/ leave related */
    public static String ARENA_JOIN_VIP_KICK = "arena.kickedByVip";
    public static String ARENA_JOIN_DENIED_IS_FULL = "arena.fullArena";
    public static String ARENA_JOIN_DENIED_IS_FULL_VIP_REQUIRED = "player.vipNoJoin";
    public static String ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS = "player.countdownStopped";
    //public static String ARENA_PLAYER_QUIT = "player.quit";
    public static String ARENA_RESTART_PLAYER_KICK = "arena.restartKick";
    public static String ARENA_JOIN_DENIED_GROUP_OR_ARENA_NOT_FOUND = "player.arenaOrGroupNotFound";
    public static String ARENA_JOIN_DENIED_NO_EMPTY_FOUND = "player.noEmptyArena";
    public static String ARENA_JOIN_DENIED_PARTY_TOO_BIG = "party.tooMuch";
    public static String ARENA_JOIN_DENIED_NOT_PARTY_LEADER = "party.leaderChoose";
    public static String ARENA_JOIN_PLAYER_JOIN_MSG = "arena.join.allowed-regular";
    public static String ARENA_JOIN_DENIED_SELECTOR = "arena.join.denied-arena-selector";
    public static String ARENA_SPECTATE_DENIED_SELECTOR = "arena.spectate.denied-arena-selector";
    public static String ARENA_JOIN_SPECTATOR_MSG = "arena.spectate.spectate-join";
    public static String ARENA_JOIN_SPECTATOR_DENIED_MSG = "arena.spectate.spectate-denied";
    public static String ARENA_LEAVE_PLAYER_LEAVE_MSG = "arena.leave";
    public static String ARENA_LEAVE_PARTY_DISBANDED = "partyDisbandOnLeave";

    /** Spectator related */
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_NAME = "spectatorItems.teleporter-gui.gui-name";
    //{player} - returns display name, {prefix} - returns the player rank
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME = "spectatorItems.teleporter-gui.head-name";
    //{health}, {food}
    public static String ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE = "spectatorItems.teleporter-gui.head-lore";

    public static String ARENA_SPECTATOR_LEAVE_ITEM_NAME = "spectatorItems.leave-item.name";
    public static String ARENA_SPECTATOR_LEAVE_ITEM_LORE = "spectatorItems.leave-item.lore";

    public static String ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE = "arena.spectate.first-person.enter-title";
    public static String ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE = "arena.spectate.first-person.enter-subtitle";
    public static String ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE = "arena.spectate.first-person.quit-title";
    public static String ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE = "arena.spectate.first-person.quit-subtitle";


    /** Arena status/ status change related */
    public static String ARENA_STATUS_WAITING_NAME = "status.waiting";
    public static String ARENA_STATUS_STARTING_NAME = "status.starting";
    public static String ARENA_STATUS_PLAYING_NAME = "status.playing";
    public static String ARENA_STATUS_RESTARTING_NAME = "status.restarting";
    public static String ARENA_STATUS_START_PLAYER_TITLE = "arena.startTitle";
    public static String ARENA_STATUS_START_PLAYER_TUTORIAL = "arena.tutorial";
    public static String ARENA_STATUS_START_COUNTDOWN = "arena.startCountdown";

    /** Arena GUI related */
    public static String ARENA_GUI_INV_NAME = "arena.guiInv.Name";
    public static String ARENA_GUI_ARENA_CONTENT_NAME = "arena.guiArena.name";
    public static String ARENA_GUI_ARENA_CONTENT_LORE = "arena.guiArena.lore";

    /** Stats related */
    public static String PLAYER_STATS_GUI_PATH = "stats";
    public static String PLAYER_STATS_GUI_INV_NAME = PLAYER_STATS_GUI_PATH+".invName.name";

    /** Arena generators related */
    public static String GENERATOR_HOLOGRAM_TIER = "generator.tier";
    public static String GENERATOR_HOLOGRAM_TYPE_DIAMOND = "generator.diamond";
    public static String GENERATOR_HOLOGRAM_TYPE_EMERALD = "generator.emerald";
    public static String GENERATOR_HOLOGRAM_TIMER = "generator.timer";
    public static String GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT = "arena.generatorUpgrade";

    /** General formatting */
    public static String FORMATTING_CHAT_LOBBY = "chat.lobby";
    public static String FORMATTING_CHAT_WAITING = "chat.waiting";
    public static String FORMATTING_CHAT_SHOUT = "chat.global";
    public static String FORMATTING_CHAT_TEAM = "chat.team";
    public static String FORMATTING_CHAT_SPECTATOR = "chat.spectator";
    public static String FORMATTING_SCOREBOARD_DATE = "scoreboardFormat.date";
    public static String FORMATTING_SCOREBOARD_TEAM_ELIMINATED = "scoreboardFormat.teamEliminated";
    public static String FORMATTING_SCOREBOARD_BED_DESTROYED = "scoreboardFormat.bedDestroyed";
    public static String FORMATTING_SCOREBOARD_TEAM_ALIVE = "scoreboardFormat.teamAlive";
    public static String FORMATTING_SCOREBOARD_NEXEVENT_TIMER = "scoreboardFormat.generatorTimer";
    public static String FORMATTING_SCOREBOARD_YOUR_TEAM = "scoreboardFormat.you";
    public static String FORMATTING_ACTION_BAR_TRACKING = "action.tracking";
    public static String FORMATTING_TEAM_WINNER_FORMAT = "arena.winFormat.team";
    public static String FORMATTING_SOLO_WINNER_FORMAT = "arena.winFormat.solo";
    //public static String FORMATTING_TAB_LIST = "format.tablist";
    public static String FORMATTING_GENERATOR_TIER1 = "format.tier1";
    public static String FORMATTING_GENERATOR_TIER2 = "format.tier2";
    public static String FORMATTING_GENERATOR_TIER3 = "format.tier3";
    public static String FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH = "format.despawnableHealth";
    public static String FORMATTING_STATS_DATE_FORMAT = "format.statsTime";


    /** Meaning/ Translations */
    public static String MEANING_SHOUT = "meaning.shout";
    public static String MEANING_NEVER = "meaning.never";
    public static String MEANING_NOBODY = "meaning.nobody";

    /** Scoreboard related */
    public static String SCOREBOARD_DEFAULT_WAITING = "scoreboard.defaultWaiting";
    public static String SCOREBOARD_DEFAULT_STARTING = "scoreboard.defaultStarting";
    public static String SCOREBOARD_DEFAULT_PLAYING = "scoreboard.defaultPlaying";
    public static String SCOREBOARD_LOBBY = "scoreboard.lobby";

    /** Player interact related */
    public static String INTERACT_CANNOT_PLACE_BLOCK = "player.cantPlace";
    public static String INTERACT_CANNOT_BREAK_BLOCK = "player.cantBreak";
    public static String INTERACT_CANNOT_BREAK_OWN_BED = "player.destroyOwnBed";
    public static String INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT = "arena.bedDestructionGlobal";
    public static String INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT = "arena.bedDestructionTitle";
    public static String INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT = "arena.bedDestructionSub";
    public static String INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM = "arena.bedDestructionTeam";
    public static String INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED ="arena.cantOpenChest";

    /** PvP related */
    public static String PLAYER_DIE_VOID_FALL_REGULAR_KILL = "arena.playerDie.void";
    public static String PLAYER_DIE_VOID_FALL_FINAL_KILL =  "arena.playerDie.voidFinalKill";
    public static String PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL = "arena.playerDie.knockedInVoid";
    public static String PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL = "arena.playerDie.knockedInVoidFinalKill";
    public static String PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL = "arena.playerDie.bomb";
    public static String PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL = "arena.playerDie.bombFinalKill";
    public static String PLAYER_DIE_PVP_REGULAR_KILL = "arena.playerDie.attack";
    public static String PLAYER_DIE_PVP_FINAL_KILL = "arena.playerDie.attackFinalKill";
    public static String PLAYER_DIE_RESPAWN_TITLE = "arena.respawnTitle";
    public static String PLAYER_DIE_RESPAWN_SUBTITLE = "arena.respawnSub";
    public static String PLAYER_DIE_RESPAWN_CHAT = "arena.respawnChat";
    public static String PLAYER_DIE_RESPAWNED_TITLE = "arena.respawnedTitle";
    public static String PLAYER_DIE_ELIMINATED_CHAT = "arena.playerEliminated";
    public static String PLAYER_DIE_UNKNOWN_REASON_REGULAR = "arena.playerDie.unknown";
    public static String PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL = "arena.playerDie.unknownFinalKill";
    public static String PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR = "arena.playerDie.bomb2";
    public static String PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL = "arena.playerDie.bomb2FinalKill";
    public static String PLAYER_DIE_SHOOT_REGULAR = "arena.playerDie.shoot";
    public static String PLAYER_DIE_SHOOT_FINAL_KILL = "arena.playerDie.shootFinalKill";
    public static String PLAYER_DIE_DEBUG_REGULAR = "arena.playerDie.bedbug";
    public static String PLAYER_DIE_DEBUG_FINAL_KILL = "arena.playerDie.bedbugFinalKill";
    public static String PLAYER_DIE_IRON_GOLEM_REGULAR = "arena.playerDie.ironGolem";
    public static String PLAYER_DIE_IRON_GOLEM_FINAL_KILL = "arena.playerDie.ironGolemFinalKill";
    public static String PLAYER_DIE_REWARD_DIAMOND = "arena.playerDie.diamondReward";
    public static String PLAYER_DIE_REWARD_IRON = "arena.playerDie.ironReward";
    public static String PLAYER_DIE_REWARD_GOLD = "arena.playerDie.goldReward";
    public static String PLAYER_DIE_REWARD_EMERALD = "arena.playerDie.emeraldReward";

    /** Misc */
    public static String ARENA_GAME_OVER_PLAYER_TITLE = "arena.gameOverTitle";
    public static String ARENA_VICTORY_PLAYER_TITLE = "arena.victoryTitle";
    public static String ARENA_GAME_OVER_PLAYER_CHAT = "arena.gameOverMsg";
    public static String ARENA_TEAM_WON_CHAT = "arena.teamWonChat";
    public static String ARENA_ENEMY_BASE_ENTER_TITLE = "arena.baseEnter.title";
    public static String ARENA_ENEMY_BASE_ENTER_SUBTITLE = "arena.baseEnter.subtitle";
    public static String ARENA_ENEMY_BASE_ENTER_ACTION = "arena.baseEnter.action";
    public static String ARENA_ENEMY_BASE_ENTER_CHAT = "arena.baseEnter.chat";
    public static String PLURAL_PATH = "plural";
    public static String BED_HOLOGRAM_DEFEND = "arena.bedHologram";
    public static String BED_HOLOGRAM_DESTROYED = "arena.bedHologramDestroyed";
    public static String TEAM_ELIMINATED_CHAT = "arena.teamEliminatedChat";
    public static String PREFIX = "prefix";


    /** Upgrades/ Shop*/
    public static String UPGRADES_TEAM_NPC_NAME = "npc.teamUpgradesName";
    public static String UPGRADES_SOLO_NPC_NAME = "npc.soloUpgradesName";
    public static String UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY = "upgrades.loreFooter.insuffMoney";
    public static String UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY = "upgrades.loreFooter.clickBuy";
    public static String UPGRADES_LORE_REPLACEMENT_UNLOCKED = "upgrades.loreFooter.unlocked";
    public static String UPGRADES_LORE_REPLACEMENT_LOCKED = "upgrades.loreFooter.locked";
    public static String UPGRADES_UPGRADE_BOUGHT_CHAT = "arena.upgradeBuy";

    public static String SHOP_TEAM_NAME = "npc.shopTeamName";
    public static String SHOP_SOLO_NAME = "npc.shopSoloName";
    public static String SHOP_PATH = "shop.";
    public static String SHOP_INSUFFICIENT_MONEY = "player.insuffMoney";
    public static String SHOP_NEW_PURCHASE = "player.newPurchase";
    public static String SHOP_UTILITY_NPC_SILVERFISH_NAME = "despawnables.silverfish";
    public static String SHOP_UTILITY_NPC_IRON_GOLEM_NAME = "despawnables.ironGolem";
    public static String SHOP_ALREADY_BOUGHT = "player.alreadyBought";

    /* MultiArena Lobby Item Messages */
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.name";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.lore";

    /* Spectator Items Messages */
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.name";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.lore";

    /* Arena waiting Items Messages */
    public static final String GENERAL_CONFIGURATION_WAITING_ITEMS_NAME = ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.name";
    public static final String GENERAL_CONFIGURATION_WAITING_ITEMS_LORE = ConfigPath.GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.lore";


    public static String PLACEHOLDER_PLAYER_TEAM_TEAM = "placeholders.player-team.team";
    public static String PLACEHOLDER_PLAYER_TEAM_SHOUT = "placeholders.player-team.shout";
    public static String PLACEHOLDER_PLAYER_TEAM_SPECTATOR = "placeholders.player-team.spectator";

    public static String REJOIN_NO_ARENA = "rejoin-no-arena";
    public static String REJOIN_DENIED = "rejoin-denied";
    public static String REJOIN_ALLOWED = "rejoin-allowed";
}
