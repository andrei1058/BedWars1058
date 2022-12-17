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

package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.mainCmd;

public class Russian extends Language{

    public Russian() {
        super(BedWars.plugin, "ru");
        YamlConfiguration yml = getYml();
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Pусский");

        // this must stay here
        // move message to new path
        if (yml.get("player-die-knocked-regular") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL) == null){
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, yml.getString("player-die-knocked-regular"));
            yml.set("player-die-knocked-regular", null);
        }
        if (yml.get("player-die-knocked-final") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL) == null){
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, yml.getString("player-die-knocked-final"));
            yml.set("player-die-knocked-final", null);
        }

        yml.options().copyDefaults(true);
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/group>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cИзвините, но вас выгнали, потому что к арене присоединился донатер.\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cЭта арена полная!\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cПриносим извинения, но эта арена полная.\n&cМы знаем, что вы являетесь донатером, но на самом деле эта арена полна сотрудников и/или донатеров.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "{prefix}§cНедостаточно игроков! Обратный отсчет остановился!");
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
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Статус: {status}", "&7Игроков: &f{on}&7/&f{max}", "&7Тип: &a{group}", "", "&aЛКМ для входа.", "&eПКМ для слежки."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r{serverIp}");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Доступные языки:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Используйте: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cЭтот язык не существует!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aЯзык сменен!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cВы не можете изменить язык во время игры.");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cИгрок не найден!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cЭтот игрок не на bedwars арене!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cИгра на арене, где находится игрок, еще не началась!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cUsage: /bw tp <ник>");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cВаша партия слишком велика для того, чтобы присоединиться к этой арене как команде :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cТолько лидер может выбрать арену.");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &eпереподключился!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eУровень &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lАлмаз");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lЭмеральд");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eСпавн через &c{seconds} &eсекунд");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &eвошел в игру (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eвышел из игры!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "{prefix}&eИгра начнется через &6{time} &eсекунд!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a{second}");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cОжидание игроков..");
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
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}",
                "", "&fУбийств: &a{kills}", "&fФинальных убийств: &a{finalKills}", "&fКроватей уничтожено: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7{date}", "", "&f{nextEvent} in &a{time}", "", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}", "{team}",
                "", "&fУбийств: &a{kills}", "&fФинальных убийств: &a{finalKills}", "&fКроватей уничтожено: &a{beds}", "", "&e{server_ip}"));

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, Arrays.asList("&c❤", "&aЗдоровье"));
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "SPECT");
        yml.addDefault(Messages.FORMATTING_SPECTATOR_COLOR, "&7");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PRESTARTING, Arrays.asList("{teamColor}&l{teamLetter} &r{teamColor}", "{team} ", "{vPrefix} {teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PRESTARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, Arrays.asList("{teamColor}&l{teamLetter} &r{teamColor}", "{team} ", "{vPrefix} {teamColor}&l{teamLetter} &r{teamColor}"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "{TeamColor}{TeamLetter}&f {TeamName}: {TeamStatus}");
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
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "{PlayerColor}{PlayerName} &7was pushed by {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "{PlayerColor}{PlayerName} &7was pushed by {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7был поражен любимой бомбой игрока {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7был поражен любимой бомбой игрока {KillerColor}{KillerName}&7. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7был убит игроком {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7был убит игроком {KillerColor}{KillerName}&7. &b&lЗАКЛЮЧИТЕЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "{PlayerColor}{PlayerName} &7disconnected while fighting with {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "{PlayerColor}{PlayerName} &7disconnected while fighting with {KillerColor}{KillerName}&7. &b&lFINAL KILL!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lВаша кровать разрушена!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cВЫ УМЕРЛИ!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eВы возродитесь через &c{time} &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eВы возродитесь через &c{time} &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aВОЗРОЖДЕН!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cВы были устранены!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "&7У {prefix}{TeamColor}{PlayerName} &7теперь &c{amount} &7HP!");
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
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bКОМАНДНЫЕ УЛУЧШЕНИЯ, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bСОЛО УЛУЧШЕНИЯ, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bМАГАЗИН, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bМАГАЗИН, &e&lПКМ");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} BedWars Experience получено (Игровое время).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} BedWars Experience получено (Победа в игре).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} BedWars Experience получено (Поддержка команды).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "{prefix}&6+{xp} BedWars Experience получено (Разрушение кровати).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "{prefix}&6+{xp} BedWars Experience получено (Убийство).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "{prefix}&6+{xp} BedWars Experience получено (Финальное убийство).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "{prefix}&6+{money} Монет (Игровое время).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "{prefix}&6+{money} Монет (Победа в игре).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "{prefix}&6+{money} Монет (Поддержка команды).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "{prefix}&6+{money} Монет (Разрушение кровати).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "{prefix}&6+{money} Монет (Финальное убийство).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "{prefix}&6+{money} Монет (Убийство).");

        //SHOP
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Быстрая покупка");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cТебе недостаточно {currency}! Нужно еще {amount}!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aТы купил &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cТы уже купил это!");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Добавление в быструю покупку...");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "{TeamColor}&l{TeamName} &r{TeamColor}Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Категории");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Предметы"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bБыстрая покупка");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cПустой слот!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7Это слот быстрой покупки!", "&bШифт-клик &7на любой предмет", "&7для добавления его сюда."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eНажми для покупки!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cТебе не достаточно {currency}!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aМАКС!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bШифт-клик для добавления в быструю покупку");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bШифт-клик для удаления из быстрой покупки!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Блоки", "&aБлоки", Collections.singletonList("&eНажми для просмотра!"));

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
                "&7enemies. Can also protect", "&7against TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Bridge Egg", Arrays.asList("&7Cost: {cost} {currency}", "", "&7This egg creates a bridge in its",
                "&7trial after being thrown.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Magic Milk", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Avoid triggering traps for 60",
                "&7seconds after consuming.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Sponge", Arrays.asList("&7Cost: {cost} {currency}", "", "&7Great for soaking up water.",
                "", "{quick_buy}", "{buy_status}"));


        yml.addDefault(Messages.MEANING_FULL, "Полный");
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
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eне онлайн!");
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
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Ты теперь наблюдаешь за §9{arena}§6.\n{prefix}§eДля выхода с арены используй §c/leave§e.");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cТы не можешь открыть этот сундук, так как команда уничтожена!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Телепортер"); // Да-да :D
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{vPrefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Здоровье: &f{health}%", "&7Насыщенность: &f{food}", "", "&7ЛКМ для слежки"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lВернутья в лобби");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Щелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aСлежка за &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cШИФТ для выхода");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eПокидание режима слежки");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eоффлайн!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cНа этой арене отключена слежка!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cВладелец пати покинут сервер, пати распущена!"); // Можно сменить на команду в целом, сленговое слово пати тоже норм

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

        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cТы не можешь еще делать это! Подожди еще {seconds} секунд!");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[ГЛОБ]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[СПЕК]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cSorry but you can't join this arena at this moment. Use Right-Click to spectate!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cSorry but you can't spectate this arena at this moment. Use Left-Click to join!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cSorry but you must join an arena using BedWarsProxy. \n&eIf you want to setup an arena make sure to give yourself the bw.setup permission so you can join the server directly!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cАрен для перезахода не найдено!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cТы больше не можешь перезайти. Игра окончена или кровать разрушена.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eВход на арену &a{arena}&e!");


        yml.addDefault(Messages.MEANING_NO_TRAP, "Нет ловушки!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Цена: {currencyColor}{cost} {currency}");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "{color}Нажми для покупки!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "{color}Тебе не достаточно {currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cЗАБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "{color}РАЗБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} купил &6{upgradeName}");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-1"), "{color}Iron Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-1"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "{tierColor}Tier 1: +50% Resources, &b{cost} {currency}",
                        "&7Tier 2: +100% Resources, &b8 Diamonds",
                        "&7Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-2"), "{color}Golden Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-2"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "{tierColor}Tier 2: +100% Resources, &b8 Diamonds",
                        "&7Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-3"), "{color}Emerald Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-3"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "&aTier 2: +100% Resources, &b8 Diamonds",
                        "{tierColor}Tier 3: Spawn emeralds, &b12 Diamonds",
                        "&7Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-4"), "{color}Molten Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge").replace("{tier}", "tier-4"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "&aTier 1: +50% Resources, &b{cost} {currency}",
                        "&aTier 2: +100% Resources, &b8 Diamonds",
                        "&aTier 3: Spawn emeralds, &b12 Diamonds",
                        "{tierColor}Tier 4: +200% Resources, &b16 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eBuy a trap");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Purchased traps will be", "&7queued on the right.", "", "&eClick to browse!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "swords").replace("{tier}", "tier-1"), "{color}Sharpened Swords");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "swords").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team permanently gains", "&7Sharpness I on all swords and", "&7axes!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-1"), "{color}Reinforced Armor I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "{tierColor}Tier 1: Protection I, &b{cost} {currency}",
                        "&7Tier 2: Protection II, &b10 Diamonds",
                        "&7Tier 3: Protection III, &b20 Diamonds",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-2"), "{color}Reinforced Armor II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-2"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "{tierColor}Tier 2: Protection II, &b{cost} {currency}",
                        "&7Tier 3: Protection III, &b20 Diamonds",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-3"), "{color}Reinforced Armor III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-3"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 Diamonds",
                        "{tierColor}Tier 3: Protection III, &b{cost} {currency}",
                        "&7Tier 4: Protection IV, &b30 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-4"), "{color}Reinforced Armor IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor").replace("{tier}", "tier-4"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "&aTier 1: Protection I, &b5 Diamonds",
                        "&aTier 2: Protection II, &b10 Diamonds",
                        "&aTier 3: Protection III, &b20 Diamonds",
                        "{tierColor}Tier 4: Protection IV, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-1"), "{color}Maniac Miner I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-1"),
                Arrays.asList("&7All players on your team", "&7permanently gain Haste.", "", "{tierColor}Tier 1: Haste I, &b{cost} {currency}",
                        "&7Tier 2: Haste II, &b6 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-2"), "{color}Maniac Miner II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner").replace("{tier}", "tier-2"),
                Arrays.asList("&7All players on your team", "&7permanently gain Haste.", "", "&aTier 1: Haste I, &b4 Diamonds",
                        "{tierColor}Tier 2: Haste II, &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "heal-pool").replace("{tier}", "tier-1"), "{color}Heal Pool");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "heal-pool").replace("{tier}", "tier-1"),
                Arrays.asList("&7Creates a Regeneration field", "&7around yor base!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "dragon").replace("{tier}", "tier-1"), "{color}Dragon Buff");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "dragon").replace("{tier}", "tier-1"),
                Arrays.asList("&7Your team will have 2 dragons", "&7instead of 1 during deathmatch!", "", "&7Cost: &b{cost} {currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Purchasable");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Traps Queue"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "{color}Trap #1: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7The first enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "{color}Trap #2: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7The second enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "{color}Trap #3: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7The third enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "{color}It's a trap!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Inflicts Blindness and Slowness", "&7for 5 seconds.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "{color}Counter-Offensive Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Grants Speed I for 15 seconds to", "&7allied players near your base.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "{color}Alarm Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Reveales invisible players as", "&7well as their name and team.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "{color}Miner Fatigue Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Inflict Mining Fatigue for 10", "&7seconds.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aBack");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7To Upgrades & Traps"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Queue a trap");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cОчередь ловушек переполнена!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l{trap} был(а) отключена!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cВ ЛОВУШКУ ПОПАЛИСЬ!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fВаша {trap} был(а) активирован(а)!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lЛовушка была активирована игроком &7&l{player} &c&lиз {color}&l{team} &c&lкоманды!"); // set off = активирована?
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lВНИМАНИЕ!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fЛовушка была активирована командой {color}{team} &f!");
        save();
        setPrefix(m(Messages.PREFIX));
    }
}
