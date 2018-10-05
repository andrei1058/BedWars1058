package com.andrei1058.bedwars.configuration;

public class ConfigPath {

    public static final String GENERATOR_STACK_ITEMS = "stack-items";

    public static final String GENERATOR_IRON_DELAY = "iron.delay";
    public static final String GENERATOR_IRON_AMOUNT = "iron.amount";
    public static final String GENERATOR_IRON_SPAWN_LIMIT = "iron.spawn-limit";

    public static final String GENERATOR_GOLD_DELAY = "gold.delay";
    public static final String GENERATOR_GOLD_AMOUNT = "gold.amount";
    public static final String GENERATOR_GOLD_SPAWN_LIMIT = "gold.spawn-limit";

    public static final String GENERATOR_DIAMOND_TIER_I_DELAY = "diamond.tierI.delay";
    public static final String GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT = "diamond.tierI.spawn-limit";

    public static final String GENERATOR_DIAMOND_TIER_II_DELAY = "diamond.tierII.delay";
    public static final String GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT = "diamond.tierII.spawn-limit";
    public static final String GENERATOR_DIAMOND_TIER_II_START = "diamond.tierII.start";

    public static final String GENERATOR_DIAMOND_TIER_III_DELAY = "diamond.tierIII.delay";
    public static final String GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT = "diamond.tierIII.spawn-limit";
    public static final String GENERATOR_DIAMOND_TIER_III_START = "diamond.tierIII.start";

    public static final String GENERATOR_EMERALD_TIER_I_DELAY = "emerald.tierI.delay";
    public static final String GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT = "emerald.tierI.spawn-limit";

    public static final String GENERATOR_EMERALD_TIER_II_DELAY = "emerald.tierII.delay";
    public static final String GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT = "emerald.tierII.spawn-limit";
    public static final String GENERATOR_EMERALD_TIER_II_START = "emerald.tierII.start";

    public static final String GENERATOR_EMERALD_TIER_III_DELAY = "emerald.tierIII.delay";
    public static final String GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT = "emerald.tierIII.spawn-limit";
    public static final String GENERATOR_EMERALD_TIER_III_START = "emerald.tierIII.start";

    /** Replace %r% with receive and %g% with generator */
    public static final String UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT = "receive.%r%.%g%.spawn-limit";


    public static final String GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART = "bungee-settings.games-before-restart";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD = "bungee-settings.restart-cmd";
    public static final String GENERAL_CONFIGURATION_ALLOW_PARTIES = "allow-parties";

    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH = "lobby-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.material";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.data";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.slot";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH+".%path%.command";

    //todo
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH = "spectator-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.material";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.data";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.slot";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH+".%path%.command";

    //todo
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH = "pre-game-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.material";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.data";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.slot";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH+".%path%.command";

    public static final String GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR = "countdowns.game-start-regular";
    public static final String GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED = "countdowns.game-start-shortened";
    public static final String GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN = "countdowns.next-event-beds-destroy";
    public static final String GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN = "countdowns.next-event-dragon-spawn";
    public static final String GENERAL_CONFIGURATION_GAME_END_COUNTDOWN = "countdowns.next-event-game-end";

    public static final String GENERAL_CONFIGURATION_ARENA_GROUPS = "arenaGroups";
    public static final String GENERAL_CONFIGURATION_REJOIN_TIME = "rejoin-time";

    public static final String GENERAL_CONFIGURATION_SHOUT_COOLDOWN = "shout-cmd-cooldown";

    public static final String GENERAL_CONFIGURATION_NPC_LOC_STORAGE = "join-npc-locations";
    public static final String GENERAL_CONFIGURATION_DEFAULT_ITEMS = "start-items-per-group";

    public static final String CENERAL_CONFIGURATION_ALLOWED_COMMANDS = "allowed-commands";
    public static final String GENERAL_CONFIGURATION_LOBBY_SCOREBOARD = "lobby-scoreboard";

    public static final String ARENA_CONFIGURATION_MAX_BUILD_Y = "max-build-y";
    public static final String ARENA_SPAWN_PROTECTION = "spawn-protection";
    public static final String ARENA_SHOP_PROTECTION = "shop-protection";
    public static final String ARENA_UPGRADES_PROTECTION = "upgrades-protection";
    public static final String ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS = "disable-generator-for-empty-teams";
    public static final String ARENA_ISLAND_RADIUS = "island-radius";
    public static final String ARENA_WAITING_POS1 = "waiting.Pos1";
    public static final String ARENA_WAITING_POS2 = "waiting.Pos2";
    public static final String ARENA_NORMAL_DEATH_DROPS = "vanilla-death-drops";
    public static final String ARENA_USE_BED_HOLO = "use-bed-hologram";


    public static final String SOUNDS_BED_DESTROY = "bed-destroy";
    public static final String SOUNDS_PLAYER_KILL = "player-kill";
    public static final String SOUNDS_INSUFF_MONEY = "insufficient-money";
    public static final String SOUNDS_BOUGHT = "bought";
    public static final String SOUNDS_COUNTDOWN_TICK = "countdown";

    public static final String SIGNS_STATUS_BLOCK_WAITING_MATERIAL = "status-block.waiting.material";
    public static final String SIGNS_STATUS_BLOCK_WAITING_DATA = "status-block.waiting.data";
    public static final String SIGNS_STATUS_BLOCK_STARTING_MATERIAL = "status-block.starting.material";
    public static final String SIGNS_STATUS_BLOCK_STARTING_DATA = "status-block.starting.data";
    public static final String SIGNS_STATUS_BLOCK_PLAYING_MATERIAL = "status-block.playing.material";
    public static final String SIGNS_STATUS_BLOCK_PLAYING_DATA = "status-block.playing.data";
}
