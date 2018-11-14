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

    /**
     * Replace %r% with receive and %g% with generator
     */
    public static final String UPGRADES_TEAM_UPGRADE_RECEIVE_TEAM_GENERATOR_SPAWN_LIMIT = "receive.%r%.%g%.spawn-limit";


    public static final String GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART = "bungee-settings.games-before-restart";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD = "bungee-settings.restart-cmd";
    public static final String GENERAL_CONFIGURATION_ALLOW_PARTIES = "allow-parties";

    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH = "lobby-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.slot";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.command";

    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH = "spectator-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_MATERIAL = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_DATA = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_SLOT = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".%path%.slot";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_COMMAND = GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH + ".%path%.command";

    public static final String GENERAL_CONFIGURATION_STATS_PATH = "stats-gui";
    public static final String GENERAL_CONFIGURATION_STATS_GUI_SIZE = GENERAL_CONFIGURATION_STATS_PATH + ".inv-size";
    public static final String GENERAL_CONFIGURATION_STATS_ITEMS_MATERIAL = GENERAL_CONFIGURATION_STATS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_STATS_ITEMS_DATA = GENERAL_CONFIGURATION_STATS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_STATS_ITEMS_SLOT = GENERAL_CONFIGURATION_STATS_PATH + ".%path%.slot";

    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH = "pre-game-items";
    /* Replace %path% with name */
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.slot";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.command";

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

    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH = "arena-gui";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.inv-size";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.show-playing";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.use-slots";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.enchanted";


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

    public static final String GENERAL_CONFIGURATION_PERFORMANCE_PATH = "performance-settings";
    public static final String GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN = GENERAL_CONFIGURATION_PERFORMANCE_PATH + ".rotate-generators";

    public static final String SHOP_SETTINGS_PATH = "shop-settings";
    public static final String SHOP_SPECIALS_PATH = "shop-specials";

    public static final String SHOP_SPECIAL_SILVERFISH_ENABLE = SHOP_SPECIALS_PATH + ".silverfish.enable";
    public static final String SHOP_SPECIAL_SILVERFISH_MATERIAL = SHOP_SPECIALS_PATH + ".silverfish.material";
    public static final String SHOP_SPECIAL_SILVERFISH_DATA = SHOP_SPECIALS_PATH + ".silverfish.data";

    public static final String SHOP_SPECIAL_IRON_GOLEM_ENABLE = SHOP_SPECIALS_PATH + ".iron-golem.enable";
    public static final String SHOP_SPECIAL_IRON_GOLEM_MATERIAL = SHOP_SPECIALS_PATH + ".iron-golem.material";
    public static final String SHOP_SPECIAL_IRON_GOLEM_DATA = SHOP_SPECIALS_PATH + ".iron-golem.data";

    public static final String SHOP_SETTINGS_QUICK_BUY_CATEGORY_PATH = SHOP_SETTINGS_PATH + ".quick-buy-category";
    public static final String SHOP_SETTINGS_QUICK_BUY_BUTTON_MATERIAL = SHOP_SETTINGS_QUICK_BUY_CATEGORY_PATH + ".material";
    public static final String SHOP_SETTINGS_QUICK_BUY_BUTTON_AMOUNT = SHOP_SETTINGS_QUICK_BUY_CATEGORY_PATH + ".amount";
    public static final String SHOP_SETTINGS_QUICK_BUY_BUTTON_DATA = SHOP_SETTINGS_QUICK_BUY_CATEGORY_PATH + ".data";
    public static final String SHOP_SETTINGS_QUICK_BUY_BUTTON_ENCHANTED = SHOP_SETTINGS_QUICK_BUY_CATEGORY_PATH + ".enchanted";

    public static final String SHOP_SETTINGS_SEPARATOR_REGULAR_PATH = SHOP_SETTINGS_PATH + ".regular-separator-item";
    public static final String SHOP_SETTINGS_SEPARATOR_REGULAR_MATERIAL = SHOP_SETTINGS_SEPARATOR_REGULAR_PATH + ".material";
    public static final String SHOP_SETTINGS_SEPARATOR_REGULAR_AMOUNT = SHOP_SETTINGS_SEPARATOR_REGULAR_PATH + ".amount";
    public static final String SHOP_SETTINGS_SEPARATOR_REGULAR_DATA = SHOP_SETTINGS_SEPARATOR_REGULAR_PATH + ".data";
    public static final String SHOP_SETTINGS_SEPARATOR_REGULAR_ENCHANTED = SHOP_SETTINGS_SEPARATOR_REGULAR_PATH + ".enchanted";

    public static final String SHOP_SETTINGS_SEPARATOR_SELECTED_PATH = SHOP_SETTINGS_PATH + ".selected-separator-item";
    public static final String SHOP_SETTINGS_SEPARATOR_SELECTED_MATERIAL = SHOP_SETTINGS_SEPARATOR_SELECTED_PATH + ".material";
    public static final String SHOP_SETTINGS_SEPARATOR_SELECTED_AMOUNT = SHOP_SETTINGS_SEPARATOR_SELECTED_PATH + ".amount";
    public static final String SHOP_SETTINGS_SEPARATOR_SELECTED_DATA = SHOP_SETTINGS_SEPARATOR_SELECTED_PATH + ".data";
    public static final String SHOP_SETTINGS_SEPARATOR_SELECTED_ENCHANTED = SHOP_SETTINGS_SEPARATOR_SELECTED_PATH + ".enchanted";

    public static final String SHOP_SETTINGS_QUICK_BUY_EMPTY_PATH = SHOP_SETTINGS_PATH + ".quick-buy-empty-item";
    public static final String SHOP_SETTINGS_QUICK_BUY_EMPTY_MATERIAL = SHOP_SETTINGS_QUICK_BUY_EMPTY_PATH + ".material";
    public static final String SHOP_SETTINGS_QUICK_BUY_EMPTY_AMOUNT = SHOP_SETTINGS_QUICK_BUY_EMPTY_PATH + ".amount";
    public static final String SHOP_SETTINGS_QUICK_BUY_EMPTY_DATA = SHOP_SETTINGS_QUICK_BUY_EMPTY_PATH + ".data";
    public static final String SHOP_SETTINGS_QUICK_BUY_EMPTY_ENCHANTED = SHOP_SETTINGS_QUICK_BUY_EMPTY_PATH + ".enchanted";

    public static final String SHOP_CATEGORY_ITEM_MATERIAL = ".category-item.material";
    public static final String SHOP_CATEGORY_ITEM_DATA = ".category-item.data";
    public static final String SHOP_CATEGORY_ITEM_AMOUNT = ".category-item.amount";
    public static final String SHOP_CATEGORY_ITEM_ENCHANTED = ".category-item.enchanted";

    public static final String SHOP_CONTENT_TIER_SETTINGS_COST = ".tier-settings.cost";
    public static final String SHOP_CONTENT_TIER_SETTINGS_CURRENCY = ".tier-settings.currency";

    public static final String SHOP_CONTENT_TIER_ITEM_MATERIAL = ".tier-item.material";
    public static final String SHOP_CONTENT_TIER_ITEM_DATA = ".tier-item.data";
    public static final String SHOP_CONTENT_TIER_ITEM_AMOUNT = ".tier-item.amount";
    public static final String SHOP_CONTENT_TIER_ITEM_ENCHANTED = ".tier-item.enchanted";

    public static final String SHOP_CATEGORY_SLOT = ".category-slot";
    public static final String SHOP_CONTENT_BUY_ITEMS_PATH = "buy-items";
    public static final String SHOP_CATEGORY_CONTENT_CONTENT_SLOT = "content-settings.content-slot";
    public static final String SHOP_CATEGORY_CONTENT_IS_PERMANENT = "content-settings.is-permanent";
    public static final String SHOP_CATEGORY_CONTENT_IS_DOWNGRADABLE = "content-settings.is-downgradable";
    public static final String SHOP_CATEGORY_CONTENT_CONTENT_TIERS = "content-tiers";
    public static final String SHOP_CATEGORY_CONTENT_PATH = ".category-content";

    public static final String SHOP_PATH_CATEGORY_BLOCKS = "blocks-category";
    public static final String SHOP_PATH_CATEGORY_MELEE = "melee-category";
    public static final String SHOP_PATH_CATEGORY_ARMOR = "armor-category";
    public static final String SHOP_PATH_CATEGORY_TOOLS = "tools-category";
    public static final String SHOP_PATH_CATEGORY_RANGED = "ranged-category";
    public static final String SHOP_PATH_CATEGORY_POTIONS = "potions-category";
}
