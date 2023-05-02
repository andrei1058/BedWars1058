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

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY, "&6%bw_server_ip%\n");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING, "&a%bw_server_ip%\n");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING, "&6%bw_server_ip%\n");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING, "&d%bw_server_ip%\n");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING, "&c%bw_server_ip%\n");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR, "&9%bw_server_ip%\n");

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY, "\n&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING, "\n&a%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING, "\n&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING, "\n&d%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING, "\n&c%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR, "\n&9%bw_server_ip%");

        yml.options().copyDefaults(true);
        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<арена/тип>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "%bw_lang_prefix%&cИзвините, но вас выгнали, потому что к арене присоединился донатер.\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "%bw_lang_prefix%&cЭта арена полная!\n&aЧтобы иметь больше возможностей - купите донат. &7&o(жми)");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "%bw_lang_prefix%&cПриносим извинения, но эта арена полная.\n&cМы знаем, что вы являетесь донатером, но на самом деле эта арена полна сотрудников и/или донатеров.");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "%bw_lang_prefix%§cНедостаточно игроков! Обратный отсчет остановился!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "%bw_lang_prefix%&e%bw_player% вышел из игры!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "%bw_lang_prefix%&eАрена, в которой вы были, перезапускается.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cИдет Игра");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Перезапуск");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Ожидание §c%bw_full%");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Начало §c%bw_full%");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "%bw_lang_prefix%&cНе существует какой-либо арены или арены: %bw_name%");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "%bw_lang_prefix%&cСейчас нет какой-либо арены ;(");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "%bw_lang_prefix%&cТы не на арене!");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Доступные арены");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l%bw_name%");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Статус: %bw_team_status%", "&7Игроков: &f%bw_on%&7/&f%bw_max%", "&7Тип: &a%bw_group%", "", "&aЛКМ для входа.", "&eПКМ для слежки."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r%bw_server_ip%");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "%bw_lang_prefix% &2Доступные языки:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7%bw_lang_iso% - &f%bw_name%");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "%bw_lang_prefix%&7Используйте: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "%bw_lang_prefix%&cЭтот язык не существует!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "%bw_lang_prefix%&aЯзык сменен!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "%bw_lang_prefix%&cВы не можете изменить язык во время игры.");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "%bw_lang_prefix%&cИгрок не найден!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "%bw_lang_prefix%&cЭтот игрок не на bedwars арене!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "%bw_lang_prefix%&cИгра на арене, где находится игрок, еще не началась!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "%bw_lang_prefix%&cUsage: /bw tp <ник>");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "%bw_lang_prefix%&cВаша партия слишком велика для того, чтобы присоединиться к этой арене как команде :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "%bw_lang_prefix%&cТолько лидер может выбрать арену.");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "%bw_lang_prefix%&7%bw_player% &eпереподключился!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eУровень &c%bw_tier%");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lАлмаз");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lЭмеральд");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eСпавн через &c%bw_seconds% &eсекунд");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "%bw_lang_prefix%&7%bw_player% &eвошел в игру (&b%bw_on%&e/&b%bw_max%&e)!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "%bw_lang_prefix%&7%bw_player% &eвышел из игры!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "%bw_lang_prefix%&eИгра начнется через &6%bw_time% &eсекунд!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a%bw_seconds%");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cОжидание игроков..");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "%bw_level%%bw_v_prefix%&6[ВСЕМ] %bw_team% &7%bw_player%&f%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "%bw_level%%bw_v_prefix%&f%bw_team%&7 %bw_player%%bw_v_suffix% %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "%bw_level%%bw_v_prefix%&7[НАБЛЮДАТЕЛЬ] %bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l Защищайте свою кровать и уничтожайте кровати врагов.",
                "&e&l      Улучшайте себя и свою команду, собирая",
                "&e&l   Железо, Золото, Эмеральды и Алмазы из генераторов",
                "&e&l     чтобы получить доступ к мощным улучшениям.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aПОГНАЛИ");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7%bw_date% &8%bw_server_id%", "", "&fКарта: &a%bw_map%", "", "&fИгроков: &a%bw_on%/%bw_max%", "", "&fОжидание...", "", "§fТип: &a%bw_group%", "&fВерсия: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7%bw_date% &8%bw_server_id%", "", "&fКарта: &a%bw_map%", "", "&fИгроков: &a%bw_on%/%bw_max%", "", "&fСтарт через &a%bw_time%s", "", "§fТип: &a%bw_group%", "&fВерсия: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5%", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5%", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fУбийств: &a%bw_kills%", "&fФинальных убийств: &a%bw_final_kills%", "&fКроватей уничтожено: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fУбийств: &a%bw_kills%", "&fФинальных убийств: &a%bw_final_kills%", "&fКроватей уничтожено: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, "%health% &c❤");
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "SPECT");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR, "&7");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR, "");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, Arrays.asList("%bw_team_color%&l%bw_team_letter% &r%bw_team_color%", "%bw_team% ", "%bw_v_prefix% %bw_team_color%"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_RESTARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_PLAYING, Arrays.asList("%bw_team_color%&l%bw_team_letter% &r%bw_team_color%", "%bw_team% ", "%bw_v_prefix% %bw_team_color%&l%bw_team_letter% &r%bw_team_color%"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_PLAYING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_STARTING, List.of("%bw_v_prefix% "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_STARTING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_WAITING, List.of("%bw_v_prefix% "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_WAITING, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_LOBBY, List.of("%bw_v_prefix% "));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_LOBBY, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "%bw_team_color%%bw_team_letter%&f %bw_team_name%: %bw_team_status%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a%bw_players_remaining%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 Вы");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTracking: %bw_team% &f- Distance: %bw_distance%m");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lКОМАНДНАЯ ЛИКВИДАЦИЯ > %bw_team_color%%bw_team_name% команда &cбыла уничтожена!\n");
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lЗащищайте свою кровать!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cТы не можешь открыть этот сундук, так как команда уничтожена!");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "%bw_lang_prefix%&cВы не можете ставить блоки здесь!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "%bw_lang_prefix%&cВы можете ломать блоки только игроков!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cВы не можете разрушить свою кровать!");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lУНИЧТОЖЕНИЕ КОММАНДЫ > %bw_team_color%%bw_team_name% команда &cбыла уничтожена!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lУНИЧТОЖЕНИЕ КРОВАТИ > %bw_team_color%%bw_team_name% Кровать &7разушена игроком %bw_player_color%%bw_player%&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cВАША КРОВАТЬ УНИЧТОЖЕНА!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fВы больше не сможете возродиться!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lУНИЧТОЖЕНИЕ КРОВАТИ > &7Ваша кровать разрушена игроком %bw_player_color%%bw_player%&7!");
        yml.addDefault(Messages.INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN, "&cYou are no longer invisible because you have taken damage!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "%bw_player_color%%bw_player% &7упал.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "%bw_player_color%%bw_player% &7упал. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "%bw_player_color%%bw_player% &7был скинут в бездну %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "%bw_player_color%%bw_player% &7был скинут в бездну игроком %bw_killer_color%%bw_killer_name%&7. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "%bw_player_color%%bw_player% &7был сбит игроком %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "%bw_player_color%%bw_player% &7был сбит игроком %bw_killer_color%%bw_killer_name%&7. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "%bw_player_color%%bw_player% &7был поражен любимой бомбой игрока %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% &7был поражен любимой бомбой игрока %bw_killer_color%%bw_killer_name%&7. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "%bw_player_color%%bw_player% &7был убит игроком %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "%bw_player_color%%bw_player% &7был убит игроком %bw_killer_color%%bw_killer_name%&7. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "%bw_player_color%%bw_player% &7откючился пока сражался с %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "%bw_player_color%%bw_player% &7откючился пока сражался с %bw_killer_color%%bw_killer_name%&7. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lВаша кровать сломана!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cВЫ ПОГИБЛИ!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eВы возродитесь через &c%bw_time% &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "%bw_lang_prefix%&eВы возродитесь через &c%bw_time% &eсекунд!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aВОЗРОЖДЕН!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "%bw_lang_prefix%&cВы были устранены!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TEXT, "%bw_lang_prefix%&eYou have respawned!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "&7У %bw_lang_prefix%%bw_player% &7теперь &c%bw_amount% &7HP!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lИГРА ОКОНЧЕНА!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lПОБЕДА!");
        yml.addDefault(Messages.FORMATTING_EACH_WINNER, "%bw_player%");
        yml.addDefault(Messages.GAME_END_FIRST_KILLER, "%bw_player%");
        yml.addDefault(Messages.GAME_END_SECOND_KILLER, "%bw_player%");
        yml.addDefault(Messages.GAME_END_THIRD_KILLER, "%bw_player%");
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "%bw_winner_format%", "", "",
                "&e                          &l1st Killer &7- %bw_first_format% - %bw_first_kills%",
                "&6                          &l2nd Killer &7- %bw_second_format% - %bw_second_kills%",
                "&c                          &l3rd Killer &7- %bw_third_format% - %bw_third_kills%", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "%bw_lang_prefix%%bw_team_color%%bw_team_name% &aвыиграл игру!");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.MEANING_NOBODY, "Никто");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "%bw_lang_prefix%%bw_generator_type% Генератор &eбыл улучшен до уровня &c%bw_tier%");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bКОМАНДНЫЕ УЛУЧШЕНИЯ, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bСОЛО УЛУЧШЕНИЯ, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bМАГАЗИН, &e&lПКМ");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bМАГАЗИН, &e&lПКМ");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Игровое время).");
        yml.addDefault(Messages.XP_REWARD_WIN, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Победа в игре).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Поддержка команды).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Разрушение кровати).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Убийство).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_xp% BedWars Опыта получено (Финальное убийство).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_money% Монет (Игровое время).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "%bw_lang_prefix%&6+%bw_money% Монет (Победа в игре).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_money% Монет (Поддержка команды).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "%bw_lang_prefix%&6+%bw_money% Монет (Разрушение кровати).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_money% Монет (Финальное убийство).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_money% Монет (Убийство).");

        //SHOP
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Быстрая покупка");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "%bw_lang_prefix%&cТебе нехватает %bw_currency%! Нужно еще %bw_amount%!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "%bw_lang_prefix%&aТы купил &6%bw_item%");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "%bw_lang_prefix%&cТы уже купил это!");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Добавление в быструю покупку...");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "%bw_team_color%&l%bw_team_name% &r%bw_team_color%Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "%bw_team_color%%bw_despawn_time%s &8[ %bw_team_color%%bw_health%&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Категории");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Предметы"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bБыстрая покупка");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cПустой слот!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7Это слот быстрой покупки!", "&bШифт-клик &7на любой предмет", "&7для добавления его сюда."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eНажми для покупки!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cТебе нехватает %bw_currency%!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aМАКСИМАЛЬНО!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bШифт-клик для добавления в быструю покупку");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bШифт-клик для удаления из быстрой покупки!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Блоки", "&aБлоки", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Шерсть", Arrays.asList("&7Цена: &f%bw_cost% %bw_currency%", "", "&7Great for bridging across", "&7islands. Turns into your team's",
                "&7color.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Глина", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Стандартный блок для защиты кровати.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Взрывоустойчивое стекло", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Имеет имунитет к взрывам.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Эндерняк", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Солидный блок для защиты кровати.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Лестницы", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Полезно когда кошки застревают", "&7на деревьях.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Обсидиан", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Лучший блок для защиты кровати.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Дерево", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Хороший блок для защиты кровати.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Оружие", "&aОружие", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Каменный меч", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Железный меч", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Алмазный меч", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Палка (Отдача I)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Броня", "&aБроня", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Кольчужная броня", Arrays.asList("&7Цена: %bw_cost% %bw_currency%",
                "", "&7Кольчужные штаны и сапоги", "&7Вы всегда будете появляться", "&7с ними при смерти.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Железная броня", Arrays.asList("&7Цена: %bw_cost% %bw_currency%",
                "", "&Железные штаны и сапоги", "&7Вы всегда будете появляться", "&7с ними при смерти.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Permanent Diamond Armor", Arrays.asList("&7Цена: %bw_cost% %bw_currency%",
                "", "&7Алмазные штаны и сапоги", "&7Вы всегда будете появляться", "&7с ними при смерти.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Инструменты", "&aИнструменты", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Ножницы", Arrays.asList("&7Цена: %bw_cost% %bw_currency%",
                "", "&7Отличный инструмент для ломания шерсти.", "&7Вы всегда будете появляться с ножницами.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Кирка %bw_tier%", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "&7Фаза: &e%bw_tier%",
                "", "&7Это улучшаемый предмет.", "&7Если вы умрёте, то он.", "&7потеряет одну фазу!", "", "&7Если вы ухудшите свой", "&7предметы до последней фазы", "&7то вы всегда будете появляться с самой нижней фазой.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Топор %bw_tier%", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "&7Фаза: &e%bw_tier%",
                "", "&7Это улучшаемый предмет.", "&7Если вы умрёте, то он.", "&7потеряет одну фазу!", "", "&7Если вы ухудшите свой", "&7предметы до последней фазы", "&7то вы всегда будете появляться с самой нижней фазой.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Луки", "&aЛуки", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Стрела", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Лук", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Лук (Сила I)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Лук (Сила  I, Отдача I)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Зелья", "&aЗелья", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Зелье на Скорость II (45 секунд)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Зелье на Прыжок V (45 секунд)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Зелье невидимости (30 секунд)", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Разное", "&aРазное", Collections.singletonList("&eНажмите для просмотра!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Золотое Яблоко", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Хорошо восстанавливает здоровье.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Чешуйница", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Спавнит чешуйниц при",
                "&7бросания снежка.", "&7Может скинуть врагов, действует 15 секунд.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Железный Голем", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Железный голем для защиты вашей",
                "&7базы. Действует 4 минуты.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Фаербол", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Нажми ПКМ чтобы запустить!",
                "&7Огненный заряд хорошенько поджарит", "&7мосты ваших противников!", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Динамит", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Автоматически поджигаеться, при",
                "&7размещении!", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Эндер-пёрл", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Самый быстрый способ добраться",
                "&7до базы вашего врага.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Ведро Воды", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Отличный метод замедлить ваших",
                "&7противников. Также может", "&7защитить от взрыва динамита.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Яйцо", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Создаёт мост, по заданной",
                "&7траектории после броска.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Магическое Молоко", Arrays.asList("&7Цена: %bw_cost% %bw_currency%", "", "&7Снимает эффекты всех ловушек,",
                "&7поставленных на базу противника, в течении 60 секунд.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Губка", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Вжимает в себя воду.",
                "", "%bw_quick_buy%", "%bw_buy_status%"));


        yml.addDefault(Messages.MEANING_FULL, "Полный");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Железо");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Железа");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Золото");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Золота");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Изумруд");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Изумрудов");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Алмаз");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Алмазов");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");

        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Используйте: /" + mainCmd + " join §o<арена/тип>");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "%bw_lang_prefix%&cВы не можете этого сделать.");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "&aНажмите, чтобы купить!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "&cУ вас недостаточно %bw_currency%!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_SPACE, "&eYou don't have enough inventory space to buy this item!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cЗАБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "&aРАЗБЛОКИРОВАНО");
        yml.addDefault("upgrades.Default.generators.tier1.name", "&eЖелезная кузница");
        yml.addDefault("upgrades.Default.generators.tier1.lore", Arrays.asList("&7Увеличивает скорость спавна", "&7Железа и золота на 50%..", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.generators.tier2.name", "&eЗолотая кузница");
        yml.addDefault("upgrades.Default.generators.tier2.lore", Arrays.asList("&7Увеличивает скорость спавна", "&7железа и золота на 100%..", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.generators.tier3.name", "&eИзумрудная кузница");
        yml.addDefault("upgrades.Default.generators.tier3.lore", Arrays.asList("&7Активирует заклинателя «Изумруд»", "&7В кузнице вашей команды.", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.maniacMiner.tier1.name", "&eМаньяк-майнер");
        yml.addDefault("upgrades.Default.maniacMiner.tier1.lore", Arrays.asList("&7Все игроки вашей команды", "&7Навсегда получат ускорение I", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.sharpSword.tier1.name", "&eЗаостренные мечи");
        yml.addDefault("upgrades.Default.sharpSword.tier1.lore", Arrays.asList("&7Ваша команда получит Резкость I", "&7На всех мечах!", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.reinforced.tier1.name", "&eУкрепленная броня");
        yml.addDefault("upgrades.Default.reinforced.tier1.lore", Arrays.asList("&7Ваша команда поолучит Защиту I", "&7На всей броне!", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.trap.tier1.name", "&eЭто ловушка!");
        yml.addDefault("upgrades.Default.trap.tier1.lore", Arrays.asList("&7Враг, пытающийся зайти на вашу базу", "&7Получит слепоту", "&7и медлительность!", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.miningFatigue.tier1.name", "&eШахтерская усталостная ловушка");
        yml.addDefault("upgrades.Default.miningFatigue.tier1.lore", Arrays.asList("&7Враг, пытающийся зайти на вашу базу", "&7В течении 10 секунд получит", "&7усталость!", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault("upgrades.Default.healPool.tier1.name", "&eИсцеляющее поле");
        yml.addDefault("upgrades.Default.healPool.tier1.lore", Arrays.asList("&7Создает поле регенирации", "&7вокруг вашей базы!", "", "&7Цена:&b %bw_cost% %bw_currency%", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a%bw_player% купил улучшение &6%bw_upgrade_name%");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "%bw_player_color%%bw_player% &7умер.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "%bw_player_color%%bw_player% &7умер. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "%bw_player_color%%bw_player% &7был убит выстрелом игрока %bw_killer_color%%bw_killer_name%&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "%bw_player_color%%bw_player% &7был убит выстрелом игрока %bw_killer_color%%bw_killer_name%&7! &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "%bw_player_color%%bw_player% &7был убит чешуйницей игрока %bw_killer_color%%bw_killer_team_name%!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "%bw_player_color%%bw_player% &7был убит Чешуйницей игрока %bw_killer_color%%bw_killer_team_name%! &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "%bw_player_color%%bw_player% &7был убит Железным Големом игрока %bw_killer_color%%bw_killer_team_name%!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "%bw_player_color%%bw_player% &7был убит Железным Големом игрока %bw_killer_color%%bw_killer_team_name%! &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "%bw_lang_prefix%&b+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "%bw_lang_prefix%&a+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "%bw_lang_prefix%&f+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "%bw_lang_prefix%&6+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.ARENA_MAX_BUILD_LIMIT_REACHED, "&cMax build height limit reached!");
        yml.addDefault(Messages.ARENA_MIN_BUILD_LIMIT_REACHED, "&cMin build height limit reached!");

        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "%bw_player_color%%bw_player% &7был взорван бомбой.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% &7был взорван бомбой. &b&lФИНАЛЬНОЕ УБИЙСТВО!");
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8Статистика игрока %bw_player%");

        /* save default items messages for stats gui */
        addDefaultStatsMsg(yml, "wins", "&6Побед", "&f%bw_wins%");
        addDefaultStatsMsg(yml, "losses", "&6Проигрышей", "&f%bw_losses%");
        addDefaultStatsMsg(yml, "kills", "&6Убийств", "&f%bw_kills%");
        addDefaultStatsMsg(yml, "deaths", "&6Смертей", "&f%bw_deaths%");
        addDefaultStatsMsg(yml, "final-kills", "&6Финальных убийств", "&f%bw_final_kills%");
        addDefaultStatsMsg(yml, "final-deaths", "&6Финальных смертей", "&f%bw_final_deaths%");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Кроватей уничтожено", "&f%bw_beds%");
        addDefaultStatsMsg(yml, "first-play", "&6Первая игра", "&f%bw_play_first%");
        addDefaultStatsMsg(yml, "last-play", "&6Последняя игра", "&f%bw_play_last%");
        addDefaultStatsMsg(yml, "games-played", "&6Игр сыграно", "&f%bw_games_played%");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");

        yml.addDefault(Messages.MEANING_NEVER, "Никогда");
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars", "&fВаш уровень: %bw_level%", "", "&fПрогресс: &a%bw_current_xp%&7/&b%bw_required_xp%", "%bw_progress%", "", "&7%bw_player%", "", "&fМонет: &a%bw_money%"
                , "", "&fВсего побед: &a%bw_wins%", "&fВсего убйиств: &a%bw_kills%", "", "&e%bw_server_ip%"));

        /* party commands */
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aКоманды пати:",
                "&e/party help &7- &bПоказывает все доступные команды",
                "&e/party invite <игрок> &7- &bПригласить игрока в ваше пати",
                "&e/party leave &7- &bВыйти из пати",
                "&e/party info &7- &bShow party members and owner",
                "&e/party promote <player> &7- &bTransfer party ownership",
                "&e/party remove <игрок> &7- &bВыгнать игрока из пати",
                "&e/party accept <игрок> &7- &bПринять приглашение в пати",
                "&e/party disband &7- &bРасфирмировать текующую группу")
        );
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "%bw_lang_prefix%&eИспользуйте: &7/party invite <игрок>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player% &eне онлайн!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "%bw_lang_prefix%&eПриглашение отправлено &7%bw_player%&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "%bw_lang_prefix%&b%bw_player% &eпригласил вас в пати! &o&7(Нажмите, чтобы принять)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "%bw_lang_prefix%&cВы не можете пригласить самого себя!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "%bw_lang_prefix%&cНет приглашений в пати.");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "%bw_lang_prefix%&eВы уже в пати!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "%bw_lang_prefix%&cТолько владелец партии может это сделать!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "%bw_lang_prefix%&eИспользуйте: &7/party accept <игрок>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eприсоединился к группе!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "%bw_lang_prefix%&cВы не в группе!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "%bw_lang_prefix%&cВы не можете покинуть свою собственное пати!\n&eПопробуйте: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eвышел из пати!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "%bw_lang_prefix%&eПати расфармированно!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "%bw_lang_prefix%&7Используйте: &e/party remove <игрок>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eбыл удален из пати.");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "%bw_lang_prefix%&7%bw_player% &eне состоит в вашем пати!");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_SUCCESS, "%bw_lang_prefix%&eВы успешно повысили %bw_player% до владельца");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_OWNER, "%bw_lang_prefix%&eВы были повышены до владельца группы");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER, "%bw_lang_prefix%&7 &e%bw_player% был повышен до владельца группы");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_OWNER, "\n%bw_lang_prefix%&eВладелец группы: &7%bw_party_owner%");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYERS,"%bw_lang_prefix%&eУчастники группы:");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYER, "&7%bw_player%");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cУничтожение кроватей");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fАлмазы II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fАлмазы III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fВнезапная смерть");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fИзумруды II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fИзумруды III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Игра закончиться");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cВАША КРОВАТЬ УНИЧТОЖЕНА!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fВсе кровати были уничтожены!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lВсе кровати были уничтожены!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cСмерть");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cСМЕРТЬ: &6&b%bw_dragons_amount% %bw_team_color%%bw_team_name% Dragon!");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "%bw_lang_prefix%&cКоманда не найдена или у вас нет прав!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Вы не играете!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Обратный отсчет сокращен!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "%bw_lang_prefix%&7Вы не можете сократить время старта.\n§7Подумайте о том, чтобы купить донат.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "%bw_lang_prefix%§6Ты теперь наблюдаешь за §9%bw_arena%§6.\n%bw_lang_prefix%§eДля выхода с арены используй §c/leave§e.");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Телепортер"); // Да-да :D
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "%bw_v_prefix%%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Здоровье: &f%bw_player_health%%", "&7Насыщенность: &f%bw_player_food%", "", "&7ЛКМ для слежки"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lВернутья в лобби");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Щелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aСлежка за &7%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cШИФТ для выхода");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eПокидание режима слежки");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player% &eоффлайн!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cНа этой арене отключена слежка!");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "%bw_lang_prefix%§cВладелец пати покинут сервер, пати распущена!"); // Можно сменить на команду в целом, сленговое слово пати тоже норм.
                                                                                                                         // Добавлено Matveev_: согл, сленговое лучше, харош

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eСтатистика");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fПКМ, чтобы увидеть свою статистику!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eАрены");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fВыберите арену!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eСтатистика");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fПКМ, чтобы увидеть свою статистику!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eТелепортер");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "teleporter"), Collections.singletonList("&fПКМ, чтобы наблюдать за игроками!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eВернутья в лобби");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fЩелкните правой кнопкой мыши, чтобы вернуться в лобби!"));

        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cВы не можете сделать этого! Подождите еще %bw_seconds% секунд!");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "%bw_team_color%[%bw_team_name%]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[ВСЕМ]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[НАБЛЮДАТЕЛЬ]");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "%bw_lang_prefix%&cИзвините, но вы не можете пресоедениться к этой арене прямо сейчас. Используйте ПКМ для режима наблюдателей!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "%bw_lang_prefix%&cИзвините, но вы не можете наблюдать за этой ареной прямо сейчас. Используйте ЛКМ для входа в игру!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cИзвините, но вы должны подключиться к арене, используя BedWarsProxy!");

        yml.addDefault(Messages.REJOIN_NO_ARENA, "%bw_lang_prefix%&cАрен для перезахода не найдено!");
        yml.addDefault(Messages.REJOIN_DENIED, "%bw_lang_prefix%&cВы больше не можете перезайти. Игра окончена или ваша кровать была разрушена.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "%bw_lang_prefix%&eПереносим вас в игру, на карту &a%bw_arena%&e!");


        yml.addDefault(Messages.MEANING_NO_TRAP, "Ловушки отсутствуют!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Цена: %bw_currency_color%%bw_cost% %bw_currency%");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "%bw_color%Нажми для покупки!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cЗАБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "%bw_color%РАЗБЛОКИРОВАНО");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a%bw_player% купил &6%bw_upgrade_name%");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-1"), "%bw_color%Железная Печь");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "forge"),
                Arrays.asList("&7Делает быстрее спавнер", "&7ресурсов вашего острова.", "", "{tier_1_color}Фаза 1: +50% К ресурсам, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Фаза 2: +100% К ресурсам, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Фаза 3: Спавнит изумруды, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Фаза 4: +200% К ресурсам, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-2"), "%bw_color%Золотая Печь");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-3"), "%bw_color%Изумрудная Печь");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-4"), "%bw_color%Алмазная Печь");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eКупить ловушку");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Купленные ловушки будут", "&7размещенны в очереди.", "", "&eНажмите чтобы посмотреть!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "swords").replace("%bw_tier%", "tier-1"), "%bw_color%Заострённые Мечи");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "swords"),
                Arrays.asList("&7Ваша команда получит", "&7Остроту I на все мечи,", "&7а также топоры!", "", "{tier_1_color}Цена: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-1"), "%bw_color%Защищёная Броня I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "armor"),
                Arrays.asList("&7Ваша команда получит", "&7Защиту I на всю броню!", "", "{tier_1_color}Фаза 1: Защита I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Фаза 2: Защита II, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Фаза 3: Защита III, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Фаза 4: Защита IV, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-2"), "%bw_color%Защищёная Броня II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-3"), "%bw_color%Защищёная Броня III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-4"), "%bw_color%Защищёная Броня IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-1"), "%bw_color%Быстрый Шахтёр I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "miner"),
                Arrays.asList("&7Ваша команда получит", "&7Спешку I.", "", "{tier_1_color}Фаза 1: Спешка I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Фаза 2: Спешка II, &b{tier_2_cost} {tier_2_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-2"), "%bw_color%Быстрый Шахтёр II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "heal-pool").replace("%bw_tier%", "tier-1"), "%bw_color%Исцеляющее Поле");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "heal-pool"),
                Arrays.asList("&7Создаёт исцеляющее поле", "&7вокруг вашей базы!", "", "{tier_1_color}Цена: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "dragon").replace("%bw_tier%", "tier-1"), "%bw_color%Драконы");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "dragon"),
                Arrays.asList("&7Ваша команда будет иметь 2 драконов,", "&7вместо 1 во время конца игры!", "", "{tier_1_color}Цена: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Покупаемое");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Очередь Ловушек"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "%bw_color%Ловушка #1: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7Первый человек который зайдёт", "&7на базу, активирует", "&7эту ловушку!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Покупка ловушки,", "&7добавит её в очередь. Её цена", "&7будет увеличиваться по мере", "&7числа купленных ловушек.", "", "&7Следущяя ловушка: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "%bw_color%Ловушка #2: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7Второй человек который зайдёт", "&7на базу, активирует", "&7эту ловушку!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Покупка ловушки,", "&7добавит её в очередь. Её цена", "&7будет увеличиваться по мере", "&7числа купленных ловушек.", "", "&7Следущяя ловушка: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "%bw_color%Ловушка #3: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7Третий человек который зайдёт", "&7на базу, активирует", "&7эту ловушку!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Покупка ловушки,", "&7добавит её в очередь. Её цена", "&7будет увеличиваться по мере", "&7числа купленных ловушек.", "", "&7Следущяя ловушка: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "%bw_color%Это ловушка!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Накладывает Слепоту и Медлительность", "&7на 5 секунд.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "%bw_color%Ловушка-Помощник");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Даёт вам и вашим союзникам", "&7скорость I на 15 секунд.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "%bw_color%Ловушка с Тревогой");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Показывает игроков, которые были", "&7с зельем невидимости.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "%bw_color%Ловушка Медленный Шахтёр");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Замедляет копание на", "&710 секунд.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aНазад");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7К Улучшениям и Ловушкам"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Очередь ловушек");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cОчередь ловушек переполнена!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&lВаша %bw_trap% был(а) отключена!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cЛОВУШКА БЫЛА АКТИВИРОВАНА!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fВаша %bw_trap% был(а) активирован(а)!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lЛовушка была активирована игроком &7&l%bw_player% &c&lиз %bw_color%&l%bw_team% &c&lкоманды!"); // set off = активирована? // Добавлено Matveev_: не ебу
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lВНИМАНИЕ!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fЛовушка была активирована командой %bw_color%%bw_team% &f!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_ALREADY_CHAT, "&cYou already unlocked this upgrade!");
        save();
        setPrefix(m(Messages.PREFIX));
        setPrefixStatic(m(Messages.PREFIX));
    }
}
