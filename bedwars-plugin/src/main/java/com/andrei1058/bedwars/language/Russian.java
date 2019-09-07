package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.andrei1058.bedwars.BedWars.mainCmd;

public class Russian extends Language{

    public Russian() {
        super(BedWars.plugin, "ru");
        YamlConfiguration yml = getYml();
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Pусский");
        yml.options().copyDefaults(true);
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cИзвините, но вас выгнали, потому что к арене присоединился донатер.\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cЭта арена полная!\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cПриносим извинения, но эта арена полная.\n&cМы знаем, что вы являетесь донатером, но на самом деле эта арена полна сотрудников и/или донатеров.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS, "{prefix}§cНедостаточно игроков! Обратный отсчет остановился!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&e{player} вышел из игры!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&eАрена, в которой вы были, перезапускается.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cИдет Игра");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Перезапуск");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Ожидание §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Начало §c{full}");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cНе существует какой-либо арены или арены: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cСейчас нет какой-либо арены ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cТы не на арене!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Доступные арены");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Статус: {status}", "&7Игроков: &f{on}&7/&f{max}", "&7Тип: &a{group}", "", "&aLeft-Click to join.", "&eRight-Click to spectate."));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Доступные языки:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Используйте: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cЭтот язык не существует!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aЯзык сменен!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cВы не можете изменить язык во время игры.");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cPlayer not found!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cThis player is not in a bedwars arena!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cThe arena where the player is didn't start yet!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUsage: /bw tp <username>");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cВаша партия слишком велика для того, чтобы присоединиться к этой арене как команде :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cТолько лидер может выбрать арену.");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &ehas reconnected!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eУровень &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lАлмаз");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lЭмеральд");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eСпавн через &c{seconds} &eсекунд");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &eвошел в игру (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eвышел из игры!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN, "{prefix}&eИгра начнется через &6{time} &eсекунд!");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "{level}{vPrefix}&7{player}{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "{level}{vPrefix}&6[ВСЕМ] {team} &7{player}&f{vSuffix}: {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "{level}{vPrefix}&f{team}&7 {player}{vSuffix} {message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "{level}{vPrefix}&7[ЗРИТЕЛЬ] {player}{vSuffix}: {message}");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l Защищайте свою кровать и уничтожайте кровати врагов.",
                "&e&l      Улучшайте себя и свою команду, собирая",
                "&e&l   Железо, Золото, Эмеральды и Алмазы из генераторов",
                "&e&l     чтобы получить доступ к мощным улучшениям.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cВы не можете ставить блоки здесь!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cВы можете ломать блоки только игроков!");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aПОГНАЛИ");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fКарта: &a{map}", "", "&fИгроков: &a{on}/{max}", "", "&fОжидание...", "", "§fMode: &a{group}", "&fVersion: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7{date} &8{server}", "", "&fКарта: &a{map}", "", "&fИгроков: &a{on}/{max}", "", "&fСтарт через &a{time}s", "", "§fMode: &a{group}", "&fVersion: &7{version}", "", "&e{server_ip}"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamPinkStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "{TeamAquaColor}A &f{TeamAquaName}&f: {TeamAquaStatus}", "{TeamWhiteColor}W &f{TeamWhiteName}&f: {TeamPinkStatus}", "{TeamPinkColor}P &f{TeamPinkName}&f: {TeamPinkStatus}",
                "{TeamGrayColor}S &f{TeamGrayName}&f: {TeamGrayStatus}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fУбийств: &a{kills}", "&fФинальных убийств: &a{finalKills}", "&fКроватей уничтожено: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{TeamRedColor}R&f {TeamRedName}&f: {TeamRedStatus}",
                "{TeamBlueColor}B&f {TeamBlueName}&f: {TeamBlueStatus}", "{TeamGreenColor}G&f {TeamGreenName}&f: {TeamGreenStatus}", "{TeamYellowColor}Y &f{TeamYellowName}&f: {TeamYellowStatus}",
                "", "&fУбийств: &a{kills}", "&fФинальных убийств: &a{finalKills}", "&fКроватей уничтожено: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 Вы");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fОтслеживание: {team} &f- Дистанция: {distance}m");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lЗащищайте свою кровать!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cВы не можете разрушить свою кровать!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lКОМАНДНАЯ ЛИКВИДАЦИЯ > {TeamColor}{TeamName} команда &cбыла уничтожена!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lУНИЧТОЖЕНИЕ КРОВАТИ > {TeamColor}{TeamName} Кровать &7разушена игроком {PlayerColor}{PlayerName}&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cКРОВАТЬ УНИЧТОЖЕНА!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fВы больше не будете возрождаться!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lУНИЧТОЖЕНИЕ КРОВАТИ > &7Ваша кровать разрушена игроком {PlayerColor}{PlayerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7упал.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7упал. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7был сбит игроком {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "{PlayerColor}{PlayerName} &7был сбит игроком {KillerColor}{KillerName}&7. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7был поражен любимой бомбой игрока {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7был поражен любимой бомбой игрока {KillerColor}{KillerName}&7. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7был убит игроком {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7был убит игроком {KillerColor}{KillerName}&7. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lВаша кровать разрушена!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cВЫ УМЕРЛИ!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eВы возродитесь через &c{time} &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eВы возродитесь через &c{time} &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aВОЗРОЖДЕН!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cВы были устранены!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lИГРА ОКОНЧЕНА!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lПОБЕДА!");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&e                          &l1-1 Убийца &7- {firstName} - {firstKills}",
                "&6                          &l2-й Убийца &7- {secondName} - {secondKills}",
                "&c                          &l3-й Убийца &7- {thirdName} - {thirdKills}", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        //yml.addDefault(gameOverReward, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
        //        "&f                                   &lReward Summary", "", "",
        //        "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &aвыиграл игру!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        //yml.addDefault(tablistFormat, "{TeamColor}&l{TeamLetter}&r {TeamColor}{PlayerName} &e{PlayerHealth}");//{TeamColor}{TeamName}{TeamHealth}{PlayerName}{PlayerHealth}
        yml.addDefault(Messages.MEANING_NOBODY, "Никто");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}{generatorType} Генератор &eбыл обновлен до уровня &c{tier}");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bКОМАНДНЫЕ УЛУЧШЕНИЯ,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bСОЛО УЛУЧШЕНИЯ,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bМАГАЗИН,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bМАГАЗИН,&e&lRIGHT CLICK");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} BedWars Experience Received (Play Time).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} BedWars Experience Received (Game Win).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} BedWars Experience Received (Team Support).");

        //SHOP
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cYou don't have enough {currency}! Need {amount} more!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aYou purchased &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cYou've already bought that!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Categories");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bQuick Buy");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cEmpty slot!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7This is a Quick Buy Slot!", "&bSneak Click &7any item in", "&7the shop to add it here."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eClick to purchase!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cYou don't have enough {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMAXED!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bSneak Click to add to Quick Buy");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bSneak Click to remove from Quick Buy!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Blocks", "&aBlocks", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Wool", Arrays.asList("&7Cost: &f{cost} {currency}", "", "&7Great for bridging across", "&7islands. Turns into your team's",
                "&7color.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Hardened Clay", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Basic block to defend your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Blast-Proof Glass", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Immune to explosions.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}End Stone", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Solid block to defend your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Ladder", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Useful to save cats stuck in", "&7trees.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Obsidian", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Extreme protection for your bed.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Wood", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Solid block to defend your bed", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Melee", "&aMelee", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Stone Sword", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Iron Sword", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Diamond Sword", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Stick (KnockBack I)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Armor", "&aArmor", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanent Chainmail Armor", Arrays.asList("&7Cost: {cost} {currency}",
                "", "&7Chainmail leggings and boots", "&7which you will always spawn", "&7with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanent Iron Armor", Arrays.asList("&7Cost: {cost} {currency}",
                "", "&7Iron leggings and boots which", "&7you will always spawn with.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Permanent Diamond Armor", Arrays.asList("&7Cost: {cost} {currency}",
                "", "&7Diamond leggings and boots which", "&7you will always crush with.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Tools", "&aTools", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Permanent Shears", Arrays.asList("&7Cost: {cost} {currency}",
                "", "&7Great to get rid of wool. You", "&7will always spawn with these shears.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Pickaxe {tier}", Arrays.asList("&7Cost: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Axe {tier}", Arrays.asList("&7Cost: {cost} {currency}", "&7Tier: &e{tier}",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Ranged", "&aRanged", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Arrow", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Bow", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Bow (Power I)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Bow (Power I, Punch I)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Potions", "&aPotions", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Speed II Potion (45 seconds)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Jump V Potion (45 seconds)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Invisibility Potion (30 seconds)", Arrays.asList("&7Cost: {cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Utility", "&aUtility", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Golden Apple", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Well-rounded healing.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}BedBug", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Spawns silverfish where the",
                "&7snowball lands to distract your", "&7enemies. Lasts 15 seconds.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Dream Defender", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Iron Golem to help defend your",
                "&7base. Lasts 4 minutes.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Fireball", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Right-click to launch! Great to",
                "&7knock back enemies walking on", "&7thin bridges", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Instantly ignites, appropriate",
                "&7to explode things!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ender Pearl", Arrays.asList("&7Cost: {cost} {currency}", "", "&7The quickest way to invade enemy",
                "&7bases.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Water Bucket", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Great to slow down approaching",
                "&7enemies. Can also protect", "against TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Bridge Egg", Arrays.asList("&7Cost: {cost} {currency}", "", "&7This egg creates a bridge in its",
                "&7trial after being thrown.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Magic Milk", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Avoid triggering traps for 60",
                "&7seconds after consuming.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Sponge", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Great for soaking up water.",
                "", "{quick_buy}", "{buy_status}"));


        yml.addDefault(Messages.MEANING_FULL, "Full");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Железо");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Железо");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Золото");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Золото");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Эмеральд");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Эмеральд");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Алмаз");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Алмаз");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");

        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Используйте: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cВы не можете это сделать.");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aНажмите, чтобы купить!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cУ вас недостаточно {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cЗАБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aРАЗБЛОКИРОВАНО");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eЖелезная кузница");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Увеличивает скорость спавна", "&7Железа и золота на 50%..", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eЗолотая кузница");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Увеличивает скорость спавна", "&7железа и золота на 100%..", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eИзумрудная кузница");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Активирует заклинателя «Изумруд»", "&7В кузнице вашей команды.", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eМаньяк-майнер");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Все игроки вашей команды", "&7Навсегда получат ускорение I", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eЗаостренные мечи");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Ваша команда получит Резкость I", "&7На всех мечах!", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eУкрепленная броня");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Ваша команда поолучит Защиту I", "&7На всей броне!", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eЭто ловушка!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7Враг, пытающийся зайти на вашу базу", "&7Получит слепоту", "&7и медлительность!", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eШахтерская усталостная ловушка");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Враг, пытающийся зайти на вашу базу", "&7В течении 10 секунд получит", "&7усталость!", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eИсцеляющий бассейн");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Создает поле регенирации", "&7вокруг вашей базы!", "", "&7Цена:&b {cost} {currency}", "", "{loreFooter}"));
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_ACTION, "&cВ ЛОВУШКУ ПОПАЛИСЬ!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_CHAT, "{prefix}&cВ ЛОВУШКУ ПОПАЛИСЬ!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_SUBTITLE, "&fВаша ловушка была выключена!");
        yml.addDefault(Messages.TRAP_ENEMY_BASE_ENTER_TITLE, "&cВ ЛОВУШКУ ПОПАЛИСЬ!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} купил улучшение &6{upgradeName}");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7умер.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7умер. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7was shoot by {KillerColor}{KillerName}&7! &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7BedBug!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7BedBug! &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerTeamName}'s &7Iron Golem! &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7был поражен бомбой.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7был поражен бомбой. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8{player} Статистика");

        /* save default items messages for stats gui */
        addDefaultStatsMsg(yml, "wins", "&6Побед", "&f{wins}");
        addDefaultStatsMsg(yml, "losses", "&6Проигрышей", "&f{losses}");
        addDefaultStatsMsg(yml, "kills", "&6Убийств", "&f{kills}");
        addDefaultStatsMsg(yml, "deaths", "&6Смертей", "&f{deaths}");
        addDefaultStatsMsg(yml, "final-kills", "&6Финальных убийств", "&f{finalKills}");
        addDefaultStatsMsg(yml, "final-deaths", "&6Финальных смертей", "&f{finalDeaths}");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Кроватей уничтожено", "&f{bedsDestroyed}");
        addDefaultStatsMsg(yml, "first-play", "&6Первая игра", "&f{firstPlay}");
        addDefaultStatsMsg(yml, "last-play", "&6Последняя игра", "&f{lastPlay}");
        addDefaultStatsMsg(yml, "games-played", "&6Игр сыграно", "&f{gamesPlayed}");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Никогда");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&fYour Level: {level}", "", "&fProgress: &a{currentXp}&7/&b{requiredXp}", "{progress}", "", "&7{player}", "", "&fCoins: &a{money}"
                , "", "&fTotal Wins: &a{wins}", "&fTotal Kills: &a{kills}", "", "&e{server_ip}"));

        /* party commands */
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aКоманды группы:", "&e/party help &7- &bПоказывает все доступные команды", "&e/party invite <player> &7- &bПригласить игрока в вашу группу",
                "&e/party leave &7- &bВыйти из группы",
                "&e/party remove <player> &7- &bВыгнать игрока из группы",
                "&e/party accept <player> &7- &bПринять приглашение в группу", "&e/party disband &7- &bРасфирмировать текующую группу"));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eИспользуйте: &7/party invite <player>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eis not online!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eПриглашение отправлено &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &eпригласил в группу! &o&7(Нажмите, чтобы принять)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cВы не можете пригласить себя!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cНет приглашений в группу.");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eВы уже в группе!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cТолько владелец партии может это сделать!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eИспользуйте: &7/party accept <player>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &eприсоединился к группе!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cВы не в группе!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cВы не можете покинуть свою собственную группу!\n&eПопробуйте: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &eвышел из группы!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eГруппа расформированна!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Используйте: &e/party remove <player>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &eбыл удален из группы.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &eis not in your party!");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cУничтожение кроватей");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fАлмаз II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fАлмаз III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fВнезапная смерть");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fЭмеральд II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fЭмеральд III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Игра закончена");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cКРОВАТЬ УНИЧТОЖЕНА!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fВсе кровати были разрушены!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lВсе кровати были разрушены!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cВнезапная смерть");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cВНЕЗАПНАЯ СМЕРТЬ: &6&b{TeamDragons} {TeamColor}{TeamName} Dragon!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cКоманда не найдена или у вас нет прав!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Вы не играете!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Обратный отсчет сокращен!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Вы не можете сократить время старта.\n§7Подумайте о том, чтобы купить донат.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6You are now spectating §9{arena}§6.\n{prefix}§eYou can leave the arena at any time doing §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cYou can't open this chest because this team was eliminated!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{prefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Health: &f{health}%", "&7Food: &f{food}", "", "&7Left-click to spectate"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lВернутья в лобби");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Щелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK to exit");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eExiting Spectator mode");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eis offline!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cSpectators are not allowed in this arena!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cThe party owner has left and the party was disbanded!");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eСтатистика");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fРик-клик, чтобы увидеть свою статистику!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eАрены");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fВыберите арену!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eСтатистика");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fРик-клик, чтобы увидеть свою статистику!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fRight-click to spectate a player!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));

        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cYou can't do that yet! Wait {seconds} more seconds!");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cSorry but you can't join this arena at this moment. Use Right-Click to spectate!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cSorry but you can't spectate this arena at this moment. Use Left-Click to join!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cThere is no arena to rejoin!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cYou can't rejoin the arena anymore. Game ended or bed destroyed.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eJoining arena &a{arena}&e!");

        save();
        setPrefix(m(Messages.PREFIX));
    }
}
