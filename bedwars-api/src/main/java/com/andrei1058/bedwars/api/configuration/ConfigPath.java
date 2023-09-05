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

package com.andrei1058.bedwars.api.configuration;

@SuppressWarnings("WeakerAccess")
public class ConfigPath {

    public static final String GAME_END_PATH = "game-end";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_SHOW_ELIMINATED = GAME_END_PATH+".show-eliminated";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_TELEPORT_ELIMINATED = GAME_END_PATH+".teleport-eliminated";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_CHAT_TOP_STATISTIC = GAME_END_PATH+".chat-top.order-by";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_CHAT_TOP_HIDE_MISSING = GAME_END_PATH+".chat-top.hide-missing";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_SB_TOP_STATISTIC = GAME_END_PATH+".sb-top.order-by";
    @GameMainOverridable
    public static final String GENERAL_GAME_END_SB_TOP_HIDE_MISSING = GAME_END_PATH+".sb-top.hide-missing";

    public static final String GENERATOR_STACK_ITEMS = "stack-items";

    public static final String GENERATOR_IRON_DELAY = "iron.delay";
    public static final String GENERATOR_IRON_AMOUNT = "iron.amount";
    public static final String GENERATOR_IRON_SPAWN_LIMIT = "iron.spawn-limit";

    public static final String GENERATOR_GOLD_DELAY = "gold.delay";
    public static final String GENERATOR_GOLD_AMOUNT = "gold.amount";
    public static final String GENERATOR_GOLD_SPAWN_LIMIT = "gold.spawn-limit";

    public static final String GENERATOR_DIAMOND_TIER_I_DELAY = "diamond.tierI.delay";
    public static final String GENERATOR_DIAMOND_TIER_I_AMOUNT = "diamond.tierI.amount";
    public static final String GENERATOR_DIAMOND_TIER_I_SPAWN_LIMIT = "diamond.tierI.spawn-limit";

    public static final String GENERATOR_DIAMOND_TIER_II_DELAY = "diamond.tierII.delay";
    public static final String GENERATOR_DIAMOND_TIER_II_AMOUNT = "diamond.tierII.amount";
    public static final String GENERATOR_DIAMOND_TIER_II_SPAWN_LIMIT = "diamond.tierII.spawn-limit";
    public static final String GENERATOR_DIAMOND_TIER_II_START = "diamond.tierII.start";

    public static final String GENERATOR_DIAMOND_TIER_III_DELAY = "diamond.tierIII.delay";
    public static final String GENERATOR_DIAMOND_TIER_III_AMOUNT = "diamond.tierIII.amount";
    public static final String GENERATOR_DIAMOND_TIER_III_SPAWN_LIMIT = "diamond.tierIII.spawn-limit";
    public static final String GENERATOR_DIAMOND_TIER_III_START = "diamond.tierIII.start";

    public static final String GENERATOR_EMERALD_TIER_I_DELAY = "emerald.tierI.delay";
    public static final String GENERATOR_EMERALD_TIER_I_AMOUNT = "emerald.tierI.amount";
    public static final String GENERATOR_EMERALD_TIER_I_SPAWN_LIMIT = "emerald.tierI.spawn-limit";

    public static final String GENERATOR_EMERALD_TIER_II_DELAY = "emerald.tierII.delay";
    public static final String GENERATOR_EMERALD_TIER_II_AMOUNT = "emerald.tierII.amount";
    public static final String GENERATOR_EMERALD_TIER_II_SPAWN_LIMIT = "emerald.tierII.spawn-limit";
    public static final String GENERATOR_EMERALD_TIER_II_START = "emerald.tierII.start";

    public static final String GENERATOR_EMERALD_TIER_III_DELAY = "emerald.tierIII.delay";
    public static final String GENERATOR_EMERALD_TIER_III_AMOUNT = "emerald.tierIII.amount";
    public static final String GENERATOR_EMERALD_TIER_III_SPAWN_LIMIT = "emerald.tierIII.spawn-limit";
    public static final String GENERATOR_EMERALD_TIER_III_START = "emerald.tierIII.start";


    public static final String GENERAL_CONFIGURATION_BUNGEE_MODE_GAMES_BEFORE_RESTART = "bungee-settings.games-before-restart";
    public static final String GENERAL_CONFIGURATION_AUTO_SCALE_LIMIT = "bungee-settings.auto-scale-clone-limit";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_RESTART_CMD = "bungee-settings.restart-cmd";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_LOBBY_SERVERS = "bungee-settings.lobby-sockets";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_SERVER_ID = "bungee-settings.server-id";
    public static final String GENERAL_CONFIGURATION_BUNGEE_OPTION_BWP_TIME_OUT = "bungee-settings.bwp-time-out";

    public static final String GENERAL_CONFIGURATION_ALLOW_FIRE_EXTINGUISH = "allow-fire-extinguish";
    public static final String GENERAL_CONFIGURATION_ENABLE_HALLOWEEN = "enable-halloween-feature";

    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH = "lobby-items";
    public static final String GENERAL_CONFIGURATION_EXPERIMENTAL_TEAM_ASSIGNER = "use-experimental-team-assigner";

    // Replace %path% with name
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_MATERIAL = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_DATA = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_SLOT = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.slot";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_LOBBY_ITEMS_COMMAND = GENERAL_CONFIGURATION_LOBBY_ITEMS_PATH + ".%path%.command";

    public static final String GENERAL_CONFIGURATION_SPECTATOR_ITEMS_PATH = "spectator-items";
    // Replace %path% with name
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
    // Replace %path% with name
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_MATERIAL = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_DATA = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_SLOT = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.slot";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_ENCHANTED = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_PRE_GAME_ITEMS_COMMAND = GENERAL_CONFIGURATION_PRE_GAME_ITEMS_PATH + ".%path%.command";

    public static final String GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR = "countdowns.game-start-regular";
    public static final String GENERAL_CONFIGURATION_START_COUNTDOWN_SHORTENED = "countdowns.game-start-shortened";
    public static final String GENERAL_CONFIGURATION_START_COUNTDOWN_HALF = "countdowns.game-start-half-arena";
    public static final String GENERAL_CONFIGURATION_RESTART = "countdowns.game-restart";
    public static final String GENERAL_CONFIGURATION_RE_SPAWN_COUNTDOWN = "countdowns.player-re-spawn";
    public static final String GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN = "countdowns.next-event-beds-destroy";
    public static final String GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN = "countdowns.next-event-dragon-spawn";
    public static final String GENERAL_CONFIGURATION_GAME_END_COUNTDOWN = "countdowns.next-event-game-end";

    public static final String GENERAL_CONFIGURATION_HUNGER_WAITING = "allow-hunger-depletion.waiting";
    public static final String GENERAL_CONFIGURATION_HUNGER_INGAME = "allow-hunger-depletion.ingame";

    public static final String GENERAL_CONFIGURATION_ARENA_GROUPS = "arenaGroups";
    public static final String GENERAL_CONFIGURATION_REJOIN_TIME = "rejoin-time";
    public static final String GENERAL_CONFIGURATION_RE_SPAWN_INVULNERABILITY = "re-spawn-invulnerability";

    public static final String GENERAL_CONFIGURATION_SHOUT_COOLDOWN = "shout-cmd-cooldown";

    public static final String GENERAL_CONFIGURATION_NPC_LOC_STORAGE = "join-npc-locations";
    public static final String GENERAL_CONFIGURATION_DEFAULT_ITEMS = "start-items-per-group";

    public static final String CENERAL_CONFIGURATION_ALLOWED_COMMANDS = "allowed-commands";
    public static final String SB_CONFIG_SIDEBAR_USE_LOBBY_SIDEBAR = "scoreboard-settings.sidebar.enable-lobby-sidebar";
    public static final String SB_CONFIG_SIDEBAR_USE_GAME_SIDEBAR = "scoreboard-settings.sidebar.enable-game-sidebar";
    public static final String SB_CONFIG_SIDEBAR_TITLE_REFRESH_INTERVAL = "scoreboard-settings.sidebar.title-refresh-interval";
    public static final String SB_CONFIG_SIDEBAR_PLACEHOLDERS_REFRESH_INTERVAL = "scoreboard-settings.sidebar.placeholders-refresh-interval";
    public static final String SB_CONFIG_SIDEBAR_LIST_FORMAT_LOBBY = "scoreboard-settings.player-list.format-lobby-list";
    public static final String SB_CONFIG_SIDEBAR_LIST_FORMAT_WAITING = "scoreboard-settings.player-list.format-waiting-list";
    public static final String SB_CONFIG_SIDEBAR_LIST_FORMAT_STARTING = "scoreboard-settings.player-list.format-starting-list";
    public static final String SB_CONFIG_SIDEBAR_LIST_FORMAT_PLAYING = "scoreboard-settings.player-list.format-playing-list";
    public static final String SB_CONFIG_SIDEBAR_LIST_FORMAT_RESTARTING = "scoreboard-settings.player-list.format-restarting-list";
    public static final String SB_CONFIG_SIDEBAR_LIST_REFRESH = "scoreboard-settings.player-list.names-refresh-interval";
    public static final String SB_CONFIG_SIDEBAR_HEALTH_ENABLE = "scoreboard-settings.health.enable";
    public static final String SB_CONFIG_SIDEBAR_HEALTH_IN_TAB = "scoreboard-settings.health.display-in-tab";
    public static final String SB_CONFIG_SIDEBAR_HEALTH_REFRESH = "scoreboard-settings.health.animation-refresh-interval";

    public static final String SB_CONFIG_TAB_HEADER_FOOTER_ENABLE = "scoreboard-settings.tab-header-footer.enable";
    public static final String SB_CONFIG_TAB_HEADER_FOOTER_REFRESH_INTERVAL = "scoreboard-settings.tab-header-footer.refresh-interval";

    public static final String GENERAL_CONFIGURATION_DISABLED_LANGUAGES = "disabled-languages";

    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH = "arena-gui";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SIZE = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.inv-size";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_SHOW_PLAYING = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.show-playing";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_SETTINGS_USE_SLOTS = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".settings.use-slots";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_MATERIAL = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.material";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_DATA = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.data";
    public static final String GENERAL_CONFIGURATION_ARENA_SELECTOR_STATUS_ENCHANTED = GENERAL_CONFIGURATION_ARENA_SELECTOR_PATH + ".%path%.enchanted";
    public static final String GENERAL_CONFIGURATION_DISABLE_CRAFTING = "inventories.disable-crafting-table";
    public static final String GENERAL_CONFIGURATION_DISABLE_ENCHANTING = "inventories.disable-enchanting-table";
    public static final String GENERAL_CONFIGURATION_DISABLE_FURNACE = "inventories.disable-furnace";
    public static final String GENERAL_CONFIGURATION_DISABLE_BREWING_STAND = "inventories.disable-brewing-stand";
    public static final String GENERAL_CONFIGURATION_DISABLE_ANVIL = "inventories.disable-anvil";
    public static final String GENERAL_CONFIGURATION_MARK_LEAVE_AS_ABANDON = "mark-leave-as-abandon";
    public static final String GENERAL_CONFIGURATION_ENABLE_GEN_SPLIT = "enable-gen-split";

    public static final String GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_SERVER_IP = "server-ip";
    public static final String GENERAL_CONFIG_PLACEHOLDERS_REPLACEMENTS_POWERED_BY = "powered-by";

    private static final String GENERAL_CHAT = "chat-settings.";
    public static final String GENERAL_CHAT_FORMATTING = GENERAL_CHAT+"format";
    public static final String GENERAL_CHAT_GLOBAL = GENERAL_CHAT+"global";

    public static final String ARENA_DISPLAY_NAME = "display-name";
    public static final String ARENA_CONFIGURATION_MAX_BUILD_Y = "max-build-y";
    public static final String ARENA_SPAWN_PROTECTION = "spawn-protection";
    public static final String ARENA_SHOP_PROTECTION = "shop-protection";
    public static final String ARENA_UPGRADES_PROTECTION = "upgrades-protection";
    public static final String ARENA_GENERATOR_PROTECTION = "generator-protection";
    public static final String ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS = "disable-generator-for-empty-teams";
    public static final String ARENA_DISABLE_NPCS_FOR_EMPTY_TEAMS = "disable-npcs-for-empty-teams";
    public static final String ARENA_ISLAND_RADIUS = "island-radius";
    public static final String ARENA_WAITING_POS1 = "waiting.Pos1";
    public static final String ARENA_WAITING_POS2 = "waiting.Pos2";
    public static final String ARENA_NORMAL_DEATH_DROPS = "vanilla-death-drops";
    public static final String ARENA_USE_BED_HOLO = "use-bed-hologram";
    public static final String ARENA_ALLOW_MAP_BREAK = "allow-map-break";
    public static final String ARENA_GAME_RULES = "game-rules";
    public static final String ARENA_SPEC_LOC = "spectator-loc";
    public static final String ARENA_TEAM_KILL_DROPS_LOC = "kill-drops-loc";
    public static final String ARENA_Y_LEVEL_KILL = "y-kill-height";

    public static final String SOUNDS_COUNTDOWN_TICK = "game-countdown-others";
    public static final String SOUNDS_COUNTDOWN_TICK_X = "game-countdown-s";
    public static final String SOUND_GAME_START = "game-countdown-start";
    public static final String SOUNDS_BED_DESTROY = "bed-destroy";
    public static final String SOUNDS_BED_DESTROY_OWN = "bed-destroy-own";
    public static final String SOUNDS_INSUFF_MONEY = "shop-insufficient-money";
    public static final String SOUNDS_BOUGHT = "shop-bought";
    public static final String SOUNDS_KILL = "kill";

    public static final String SIGNS_STATUS_BLOCK_WAITING_MATERIAL = "status-block.waiting.material";
    public static final String SIGNS_STATUS_BLOCK_WAITING_DATA = "status-block.waiting.data";
    public static final String SIGNS_STATUS_BLOCK_STARTING_MATERIAL = "status-block.starting.material";
    public static final String SIGNS_STATUS_BLOCK_STARTING_DATA = "status-block.starting.data";
    public static final String SIGNS_STATUS_BLOCK_PLAYING_MATERIAL = "status-block.playing.material";
    public static final String SIGNS_STATUS_BLOCK_PLAYING_DATA = "status-block.playing.data";
    public static final String SIGNS_STATUS_BLOCK_RESTARTING_MATERIAL = "status-block.restarting.material";
    public static final String SIGNS_STATUS_BLOCK_RESTARTING_DATA = "status-block.restarting.data";

    private static final String GENERAL_PARTY_SETTINGS_PATH = "party-settings";
    public static final String GENERAL_ENABLE_PARTY_CMD = GENERAL_PARTY_SETTINGS_PATH + ".enable-party-cmd";
    public static final String GENERAL_CONFIGURATION_ALLOW_PARTIES = GENERAL_PARTY_SETTINGS_PATH + ".allow-parties";
    public static final String GENERAL_ALESSIODP_PARTIES_RANK = GENERAL_PARTY_SETTINGS_PATH + ".alessioDP-choose-arena-rank";

    private static final String GENERAL_TNT_JUMP_PATH = "tnt-jump-settings";
    public static final String GENERAL_TNT_JUMP_BARYCENTER_IN_Y = GENERAL_TNT_JUMP_PATH + ".barycenter-alteration-in-y";
    public static final String GENERAL_TNT_JUMP_STRENGTH_REDUCTION = GENERAL_TNT_JUMP_PATH + ".strength-reduction-constant";
    public static final String GENERAL_TNT_JUMP_Y_REDUCTION = GENERAL_TNT_JUMP_PATH + ".y-axis-reduction-constant";
    public static final String GENERAL_TNT_JUMP_DAMAGE_SELF = GENERAL_TNT_JUMP_PATH + ".damage-self";
    public static final String GENERAL_TNT_JUMP_DAMAGE_TEAMMATES = GENERAL_TNT_JUMP_PATH + ".damage-teammates";
    public static final String GENERAL_TNT_JUMP_DAMAGE_OTHERS = GENERAL_TNT_JUMP_PATH + ".damage-others";

    public static final String GENERAL_TNT_BLAST_PROTECTION = "blast-protection";
    public static final String GENERAL_TNT_PROTECTION_END_STONE_BLAST = GENERAL_TNT_BLAST_PROTECTION+".end-stone";
    public static final String GENERAL_TNT_PROTECTION_GLASS_BLAST = GENERAL_TNT_BLAST_PROTECTION+".glass";
    public static final String GENERAL_TNT_RAY_BLOCKED_BY_GLASS = GENERAL_TNT_BLAST_PROTECTION+".ray-blocked-by-glass";

    public static final String GENERAL_TNT_PRIME = "tnt-prime-settings";
    public static final String GENERAL_TNT_AUTO_IGNITE = GENERAL_TNT_PRIME+".auto-ignite";
    public static final String GENERAL_TNT_FUSE_TICKS = GENERAL_TNT_PRIME+".fuse-ticks";

    private static final String GENERAL_FIREBALL_PATH = "fireball";
    public static final String GENERAL_FIREBALL_EXPLOSION_SIZE = GENERAL_FIREBALL_PATH + ".explosion-size";
    public static final String GENERAL_FIREBALL_SPEED_MULTIPLIER = GENERAL_FIREBALL_PATH + ".speed-multiplier";
    public static final String GENERAL_FIREBALL_MAKE_FIRE = GENERAL_FIREBALL_PATH + ".make-fire";
    private static final String GENERAL_FIREBALL_KNOCKBACK_PATH = GENERAL_FIREBALL_PATH + ".knockback";
    public static final String GENERAL_FIREBALL_KNOCKBACK_VERTICAL = GENERAL_FIREBALL_KNOCKBACK_PATH + ".vertical";
    public static final String GENERAL_FIREBALL_KNOCKBACK_HORIZONTAL = GENERAL_FIREBALL_KNOCKBACK_PATH + ".horizontal";
    public static final String GENERAL_FIREBALL_COOLDOWN = GENERAL_FIREBALL_PATH + ".cooldown";
    private static final String GENERAL_FIREBALL_DAMAGE_PATH = GENERAL_FIREBALL_PATH + ".damage";
    public static final String GENERAL_FIREBALL_DAMAGE_SELF = GENERAL_FIREBALL_DAMAGE_PATH + ".self";
    public static final String GENERAL_FIREBALL_DAMAGE_ENEMY = GENERAL_FIREBALL_DAMAGE_PATH + ".enemy";
    public static final String GENERAL_FIREBALL_DAMAGE_TEAMMATES = GENERAL_FIREBALL_DAMAGE_PATH + ".teammates";


    public static final String GENERAL_CONFIGURATION_PERFORMANCE_PATH = "performance-settings";
    public static final String GENERAL_CONFIGURATION_PERFORMANCE_ROTATE_GEN = GENERAL_CONFIGURATION_PERFORMANCE_PATH + ".rotate-generators";
    public static final String GENERAL_CONFIGURATION_PERFORMANCE_SPOIL_TNT_PLAYERS = GENERAL_CONFIGURATION_PERFORMANCE_PATH + ".spoil-tnt-players";

    public static final String GENERAL_CONFIGURATION_PERFORMANCE_PAPER_FEATURES = GENERAL_CONFIGURATION_PERFORMANCE_PATH + ".paper-features";

    private static final String GENERAL_CONFIGURATION_HEAL_POOL = GENERAL_CONFIGURATION_PERFORMANCE_PATH+".heal-pool";
    public static final String GENERAL_CONFIGURATION_HEAL_POOL_ENABLE = GENERAL_CONFIGURATION_HEAL_POOL+".enable";
    public static final String GENERAL_CONFIGURATION_HEAL_POOL_SEEN_TEAM_ONLY = GENERAL_CONFIGURATION_HEAL_POOL+".seen-by-team-only";
    public static final String SHOP_SETTINGS_PATH = "shop-settings";
    public static final String SHOP_SPECIALS_PATH = "shop-specials";
    public static final String SHOP_QUICK_DEFAULTS_PATH = "quick-buy-defaults";

    public static final String SHOP_SPECIAL_TOWER_ENABLE = SHOP_SPECIALS_PATH + ".tower.enable";
    public static final String SHOP_SPECIAL_TOWER_MATERIAL = SHOP_SPECIALS_PATH + ".tower.material";
    public static final String SHOP_SPECIAL_SILVERFISH_ENABLE = SHOP_SPECIALS_PATH + ".silverfish.enable";
    public static final String SHOP_SPECIAL_SILVERFISH_MATERIAL = SHOP_SPECIALS_PATH + ".silverfish.material";
    public static final String SHOP_SPECIAL_SILVERFISH_DATA = SHOP_SPECIALS_PATH + ".silverfish.data";
    public static final String SHOP_SPECIAL_SILVERFISH_HEALTH = SHOP_SPECIALS_PATH + ".silverfish.health";
    public static final String SHOP_SPECIAL_SILVERFISH_DAMAGE = SHOP_SPECIALS_PATH + ".silverfish.damage";
    public static final String SHOP_SPECIAL_SILVERFISH_SPEED = SHOP_SPECIALS_PATH + ".silverfish.speed";
    public static final String SHOP_SPECIAL_SILVERFISH_DESPAWN = SHOP_SPECIALS_PATH + ".silverfish.despawn";

    public static final String SHOP_SPECIAL_IRON_GOLEM_ENABLE = SHOP_SPECIALS_PATH + ".iron-golem.enable";
    public static final String SHOP_SPECIAL_IRON_GOLEM_MATERIAL = SHOP_SPECIALS_PATH + ".iron-golem.material";
    public static final String SHOP_SPECIAL_IRON_GOLEM_DATA = SHOP_SPECIALS_PATH + ".iron-golem.data";
    public static final String SHOP_SPECIAL_IRON_GOLEM_HEALTH = SHOP_SPECIALS_PATH + ".iron-golem.health";
    public static final String SHOP_SPECIAL_IRON_GOLEM_DESPAWN = SHOP_SPECIALS_PATH + ".iron-golem.despawn";
    public static final String SHOP_SPECIAL_IRON_GOLEM_SPEED = SHOP_SPECIALS_PATH + ".iron-golem.speed";

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
    public static final String SHOP_CONTENT_BUY_CMDS_PATH = "buy-cmds";
    public static final String SHOP_CATEGORY_CONTENT_CONTENT_SLOT = "content-settings.content-slot";
    public static final String SHOP_CATEGORY_CONTENT_IS_PERMANENT = "content-settings.is-permanent";
    public static final String SHOP_CATEGORY_CONTENT_IS_DOWNGRADABLE = "content-settings.is-downgradable";
    public static final String SHOP_CATEGORY_CONTENT_IS_UNBREAKABLE = "content-settings.is-unbreakable";
    public static final String SHOP_CATEGORY_CONTENT_WEIGHT = "content-settings.weight";
    public static final String SHOP_CATEGORY_CONTENT_CONTENT_TIERS = "content-tiers";
    public static final String SHOP_CATEGORY_CONTENT_PATH = ".category-content";

    public static final String SHOP_PATH_CATEGORY_BLOCKS = "blocks-category";
    public static final String SHOP_PATH_CATEGORY_MELEE = "melee-category";
    public static final String SHOP_PATH_CATEGORY_ARMOR = "armor-category";
    public static final String SHOP_PATH_CATEGORY_TOOLS = "tools-category";
    public static final String SHOP_PATH_CATEGORY_RANGED = "ranged-category";
    public static final String SHOP_PATH_CATEGORY_POTIONS = "potions-category";
    public static final String SHOP_PATH_CATEGORY_UTILITY = "utility-category";

    public static final String TEAM_NAME_PATH = "team-name-{arena}-{team}";

    public static final String LOBBY_VOID_TELEPORT_ENABLED = "lobby-settings.void-tp";
    public static final String LOBBY_VOID_TELEPORT_HEIGHT = "lobby-settings.void-height";
}
