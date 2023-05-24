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
// This Language file was created by Monzu77, contact on discord for any questions regarding this language file. Dicsord: Itsmemonzu#6732
package com.tomkeuper.bedwars.language;


import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bangla extends Language {
    public Bangla() {
        super(BedWars.plugin, "bd");

        YamlConfiguration yml = getYml();
        yml.options().copyDefaults(true);
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Bangla");

        // this must stay here
        // move message to new path
        if (yml.get("player-die-knocked-regular") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL) == null) {
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, yml.getString("player-die-knocked-regular"));
            yml.set("player-die-knocked-regular", null);
        }
        if (yml.get("player-die-knocked-final") != null && yml.get(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL) == null) {
            yml.set(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, yml.getString("player-die-knocked-final"));
            yml.set("player-die-knocked-final", null);
        }

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_LOBBY, "&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_WAITING,"&a%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_STARTING, "&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_PLAYING, "&d%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_RESTARTING, "&c%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_HEADER_SPECTATOR,"&9%bw_server_ip%");

        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_LOBBY, "&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_WAITING, "&a%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_STARTING,"&6%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_PLAYING, "&d%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_RESTARTING, "&c%bw_server_ip%");
        yml.addDefault(Messages.FORMATTING_SIDEBAR_TAB_FOOTER_SPECTATOR,"&9%bw_server_ip%");

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + BedWars.mainCmd + " stats", "&2▪ &7/" + BedWars.mainCmd + " join &o<arena/group>", "&2▪ &7/" + BedWars.mainCmd + " leave", "&2▪ &7/" + BedWars.mainCmd + " lang", "&2▪ &7/" + BedWars.mainCmd + " gui", "&2▪ &7/" + BedWars.mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "%bw_lang_prefix% &2Upolopdho Bhasa:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7%bw_lang_iso% - &f%bw_name%");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "%bw_lang_prefix%&7Usage: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "%bw_lang_prefix%&cEi bhasa biddoman nei!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "%bw_lang_prefix%&aBhasa bodle gelo!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "%bw_lang_prefix%&cApni match er vitore language change korte parben na.");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Usage: /" + BedWars.mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "%bw_lang_prefix%&c%bw_name% Ei name e kono arena ba arena group nei.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "%bw_lang_prefix%&cEi arena ti full!\n&aAro features er jonno donate korar bibechona korun.");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "%bw_lang_prefix%&cEi muhurte kono arena available nei. ;(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "%bw_lang_prefix%&cDukkhito kintu ei arena ti fukk. \n&cAmra jani apni ekjon donor kintu ei arena ti Staff/and onno donor purno.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "%bw_lang_prefix%&cEi arena ti te jog dite apnar dol onek boro :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "%bw_lang_prefix%&cEkmatro Leader arena select korte parben.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "%bw_lang_prefix%&7%bw_player% &ejog diyechen! (&b%bw_on%&e/&b%bw_max%&e)");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "%bw_lang_prefix%§6Apni akhon spectate korchen §9%bw_arena%§6.\n%bw_lang_prefix%§eApni §c/leave§e kore je kono muhurte leave korte parben.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cEi arena ti te spectators allowed na!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "%bw_lang_prefix%&cPlayer paowa jai ni!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "%bw_lang_prefix%&cEi player ti ekti bedwars arena te nei!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "%bw_lang_prefix%&cEi player er arena akhono start hoini!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "%bw_lang_prefix%&cUsage: /bw tp <username>");
        yml.addDefault(Messages.REJOIN_NO_ARENA, "%bw_lang_prefix%&cKono rejoin korar moto arena nei!");
        yml.addDefault(Messages.REJOIN_DENIED, "%bw_lang_prefix%&cApni ar arena ti rejoin korte parben na. Game shes othoba apnar Bichana bhenge giyeche.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "%bw_lang_prefix%&eApni &a%bw_arena% arena join korchen&e!");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "%bw_lang_prefix%&7%bw_player% &ereconnect korechen!");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "%bw_lang_prefix%&cApni ei arena te nei!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "%bw_lang_prefix%&7%bw_player% &ebiday nilen!");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "%bw_lang_prefix%&cApni game e eti korte parben na.");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "%bw_lang_prefix%&cCommand tir ostitto nei othoba apnar command use korar permission nei!");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aParty Commands:",
                "&e/party help &7- &bPrints this help message",
                "&e/party invite <player> &7- &bPlayer tike party te invite kore",
                "&e/party leave &7- &bParty theke biday nen",
                "&e/party remove <player> &7- &bParty theke player shoran",
                "&e/party info &7- &bShow party members and owner",
                "&e/party promote <player> &7- &bTransfer party ownership",
                "&e/party accept <player> &7- &bParty accept koren",
                "&e/party disband &7- &bParty disband koren")
        );
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "%bw_lang_prefix%&eUsage: &7/party invite <player>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player% &eonline nei!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "%bw_lang_prefix%&7%bw_player% &e party invite dewa holo&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "%bw_lang_prefix%&b%bw_player% &eApnake party te invite korlen! &o&7(Accept korte click korun)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "%bw_lang_prefix%&cApni nijeke invite korte parben na!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "%bw_lang_prefix%&cAccept korar moto kono party request nei");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "%bw_lang_prefix%&eApni itimoddhe ekti party te achen!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "%bw_lang_prefix%&cSudhu matro party malik eti korte parben!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "%bw_lang_prefix%&eUsage: &7/party accept <player>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eparty te jog dilen!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "%bw_lang_prefix%&cApni ekti party te nei!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "%bw_lang_prefix%&cApni apnar party leave dite parben na!\n&b/party disband &euse korar chesta koren!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eparty theke biday nilen!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "%bw_lang_prefix%&eParty disband hoye giyeche!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "%bw_lang_prefix%&7Usage: &e/party remove <player>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &eke party theke shorano hoyeche,");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "%bw_lang_prefix%&7%bw_player% &eapnar party te nei!");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_SUCCESS, "%bw_lang_prefix%&eYou successfully promoted %bw_player% to owner");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_OWNER, "%bw_lang_prefix%&eYou have been promoted to party owner");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER, "%bw_lang_prefix%&7 &e%bw_player% has been promoted to owner");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_OWNER, "\n%bw_lang_prefix%&eParty owner: &7%bw_party_owner%");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYERS,"%bw_lang_prefix%&eParty members:");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYER, "&7%bw_player%");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Apni khelchen na!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Countdown shongkhipto kora hoyeche!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "%bw_lang_prefix%&7Apni arena ti ke force start korte parben na.\n§7VIP Features grohon korte donate korun.");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cApni akhono eti korte parben na! %bw_seconds% wait korun!");
        yml.addDefault(Messages.COMMAND_SHOUT_DISABLE_SOLO, "&cShouting is disabled in Solo!");
        yml.addDefault(Messages.COMMAND_LEAVE_STARTED, "&a&lTeleporting you to the lobby in %bw_leave_delay% seconds... Right-click again to cancel the teleport!");
        yml.addDefault(Messages.COMMAND_LEAVE_CANCELED, "&c&lTeleport cancelled!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "%bw_lang_prefix%&cDukkhito, kintu ekti donor arena ti te join korar karone apnake kick kora hoyeche.\n&aAro features er jonno donate korar bibechona korun. &7&o(click)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "%bw_lang_prefix%§cPorjapto players nei! Countdown bondho hoye gelo!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "%bw_lang_prefix%&eApnar join kora arena restart hocche.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cPlaying");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Restarting hocche");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Waiting §c%bw_full%");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Starting §c%bw_full%");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Join korte click korun!");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l%bw_name%");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: %bw_arena_status%", "&7Players: &f%bw_on%&7/&f%bw_max%", "&7Type: &a%bw_group%", "", "&aJoin korte Right-Click korun", "&eSpectate korte Right-Click korun."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r%bw_server_ip%");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "%bw_lang_prefix%&eGame ti &6%bw_time% &eseconds e start hobe!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a%bw_seconds%");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cAro players er jonno opekkha korchi..");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aGO");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "",
                "&e&l    Apnar Bichana rokkha korun abong shotruder bichana dhongsho korun",
                "&e&l                   Shokttishali upgrade access korte",
                "&e&l            Generator theke Iron, Gold, Emerald abong Diamond",
                "&e&l                    Shongroho kore nijeke abong apnar",
                "&e&l                            Dolke upgrade korun.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "%bw_lang_prefix%&cDukkhito kintu apni ei muhurte arenati te jog dite parben na. Spectate korar jonne Right-Click bebohar korun!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "%bw_lang_prefix%&cDukkhito kintu apni ei muhurte arenati ke spectate korte parben na. Jog dite Left-Click bebohar korun!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cDukkhiyo kintu apnake BedWarsProxy use kore ekti arena join korte hobe \n&eApni ekti arena setup korte chaile nijeke bw.setup permission er onumoti den jate apni shora shori server e jog dite paren!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_TIME, "&cSorry but you joined while the game was already started.");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "%bw_v_prefix%%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Health: &f%bw_player_health%%", "&7Food: &f%bw_player_food%", "", "&7Spectate korte Left-Click korun"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lReturn to lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Lobby leave dite Right-Click bebohar korun!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aSpectating &7%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&Exit korte SNEAK press korun");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eSpectator mode theke biday nicchi");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "%bw_lang_prefix%§cParty owner leave dewar karone party disband holo!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTier &c%bw_tier%");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lDiamond");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lEmerald");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eSpawns in &c%bw_seconds% &eseconds");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "%bw_lang_prefix%%bw_generator_type% Generators &c%bw_tier% &eTier e update holo");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "%bw_level%%bw_v_prefix%&6[SHOUT] %bw_team_format% &7%bw_player%&f%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "%bw_level%%bw_v_prefix%&f%bw_team_format%&7 %bw_player%%bw_v_suffix% %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "%bw_level%%bw_v_prefix%&7[SPECTATOR] %bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, "%health% &c❤");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_SPECTATOR, List.of("&7"));
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_SUFFIX_SPECTATOR, new ArrayList<>());
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TAB_PREFIX_RESTARTING, Arrays.asList("%bw_team_color%&l%bw_team_letter% &r%bw_team_color%\"", "%bw_team% ", "%bw_v_prefix% %bw_team_color%"));
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
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 YOU");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTracking: %bw_team% &f- Duratto: %bw_distance%m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "%bw_team_color%[%bw_team_name%]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[SPECTATOR]");
        yml.addDefault(Messages.MEANING_FULL, "Full");
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.MEANING_NOBODY, "Nobody");
        yml.addDefault(Messages.MEANING_NEVER, "Never");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Iron");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Iron");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Gold");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Gold");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Emerald");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Emeralds");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Diamond");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Diamonds");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "%bw_lang_prefix%&cApni ekhane block place korte parben na!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "%bw_lang_prefix%&cApni sudhu ekti player er place kora block vangte parben!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cApni apnar Bichana vangte parben na!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lBED DESTRUCTION > %bw_player_color%%bw_player% %bw_team_color%%bw_team_name% &7er Bichana destroy korlen!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cBICHANA DESTROY HOYE GIYECHE!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fApni ar respawn niben na!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lBED DESTRUCTION > %bw_player_color%%bw_player% &7Apnar Bichana destroy korlen!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cApni ei chest ti khulte parben na karon ei team ti eliminate hoini!");
        yml.addDefault(Messages.INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN, "&cApni ar invisible non karon apni damage niyechen!");
        yml.addDefault(Messages.INTERACT_MAGIC_MILK_REMOVED, "&cYour Magic Milk wore off!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "%bw_player_color%%bw_player% &7void e pore gelen.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "%bw_player_color%%bw_player% &7void e pore gelen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "%bw_killer_color%%bw_killer_name%&7 %bw_player_color%%bw_player% &7ke void e falalen.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "%bw_killer_color%%bw_killer_name%&7 %bw_player_color%%bw_player% &7ke void e falalen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7er sathe fight korar majhe disconnect korlen.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7er sathe fight korar majhe disconnect korlen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "%bw_killer_color%%bw_killer_name% %bw_player_color%%bw_player% &7ke dhakka dilen.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "%bw_killer_color%%bw_killer_name% %bw_player_color%%bw_player% &7ke dhakka dilen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7er bomb dara void e porlen.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7er bomb dara void e porlen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "%bw_player_color%%bw_player% &7ekti bomb dara void e porlen.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% &7ekti bomb dara void e porlen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7dara nihoto hon.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7dara nihoto hon. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "%bw_player_color%%bw_player% &7nihoto holen.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "%bw_player_color%%bw_player% &7nihoto holen. &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7arrow marar karone aghat hon!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_name% &7arrow marar karone aghat hon! &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_team_name% &7er BedBug er karone aghat hon!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_team_name% &7er BedBug er karone aghat hon! &b&lFINAL KILL!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_team_name% &7er Iron Golem er karone aghat hon!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "%bw_player_color%%bw_player% %bw_killer_color%%bw_killer_team_name% &7er Iron Golem er karone aghat hon!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "%bw_lang_prefix%&b+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "%bw_lang_prefix%&a+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "%bw_lang_prefix%&f+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "%bw_lang_prefix%&6+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.ARENA_MAX_BUILD_LIMIT_REACHED, "&cMax build height limit reached!");
        yml.addDefault(Messages.ARENA_MIN_BUILD_LIMIT_REACHED, "&cMin build height limit reached!");
        yml.addDefault(Messages.ARENA_FIREBALL_COOLDOWN, "&cPlease wait %bw_cooldown%s to use that again!");
        yml.addDefault(Messages.ARENA_IN_GAME_ANNOUNCEMENT, Arrays.asList("&c&lIf you get disconnected use /rejoin to join back in the game.", "&c&lCross-teaming is not allowed! Report cross-teamers using /report."));
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cAPNI MORLEN!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eApni &c%bw_time% &eseconds e respawn niben");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "%bw_lang_prefix%&eApni &c%bw_time% &eseconds e respawn niben");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aAPNI RESPAWN NILEN!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "%bw_lang_prefix%&cApni eliminate hoye gelen!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TEXT, "%bw_lang_prefix%&eYou have respawned!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "%bw_lang_prefix%%bw_player% &7er &c%bw_damage_amount% &7HP &7royeche!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lGAME SHES!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lBIJOY!");
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "%bw_lang_prefix%%bw_team_color%%bw_team_name% &ateam khela jitlen!");
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
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lApnar Bichana rokkha korun!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lApnar Bichana dhongso hoye giyeche!");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bTEAM UPGRADES,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bSOLO UPGRADES,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bTEAM SHOP,&e&lRIGHT CLICK");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bITEM SHOP,&e&lRIGHT CLICK");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTEAM ELIMINATED > %bw_team_color%%bw_team_name% &cTeam ti eliminate holen!\n");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cBeds Destruction");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fDiamond II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fDiamond III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fSudden Death");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fEmerald II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fEmerald III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Game End");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cBICHANA DESTROY HOYE GIYECHE!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fShob team er Bichana dhongso hoye giyeche!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lShob team er Bichana dhongso hoye giyeche!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cSudden Death");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cSUDDEN DEATH: &6&b%bw_dragons_amount% %bw_team_color%%bw_team_name% Dragon dekha dilo!");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Play Time).");
        yml.addDefault(Messages.XP_REWARD_WIN, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Game Win).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Team Support).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Bed Destroyed).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Regular Kill).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_xp% BedWars Experience pelen (Final Kill).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Play Time).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Game Win).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Team Support).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Bed Destroyed).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Final Kill).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_money% Coins pelen (Regular Kill).");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStats");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fApnar stats pete Right-Click korun!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eArena Selector");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fArena select korte Right-Click korun!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Hub");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fBedWars leave nite Right-Click korun!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStats");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fApnar stats pete Right-Click korun!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fArena leave nite Right-Click korun!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eBack to Lobby");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fArena leave nite Right-Click korun!"));

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8%bw_player% Stats");
        addDefaultStatsMsg(yml, "wins", "&6Wins", "&f%bw_wins%");
        addDefaultStatsMsg(yml, "losses", "&6Losses", "&f%bw_losses%");
        addDefaultStatsMsg(yml, "kills", "&6Kills", "&f%bw_kills%");
        addDefaultStatsMsg(yml, "deaths", "&6Deaths", "&f%bw_deaths%");
        addDefaultStatsMsg(yml, "final-kills", "&6Final Kills", "&f%bw_final_kills%");
        addDefaultStatsMsg(yml, "final-deaths", "&6Final Deaths", "&f%bw_final_deaths%");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Beds Destroyed", "&f%bw_beds%");
        addDefaultStatsMsg(yml, "first-play", "&6First Play", "&f%bw_play_first%");
        addDefaultStatsMsg(yml, "last-play", "&6Last Play", "&f%bw_play_last%");
        addDefaultStatsMsg(yml, "games-played", "&6Games Played", "&f%bw_games_played%");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&lBED WARS", "&7%bw_date% &8%bw_server_id%", "", "&fMap: &a%bw_map%", "", "&fPlayers: &a%bw_on%/%bw_max%", "", "&fWaiting...", "", "§fMode: &a%bw_group%", "&fVersion: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lBED WARS", "&7%bw_date% &8%bw_server_id%", "", "&fMap: &a%bw_map%", "", "&fPlayers: &a%bw_on%/%bw_max%", "", "&fStarting in &a%bw_time%s", "", "§fMode: &a%bw_group%", "&fVersion: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fKills: &a%bw_kills%", "&fFinal Kills: &a%bw_final_kills%", "&fBeds Broken: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lBED WARS", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fKills: &a%bw_kills%", "&fFinal Kills: &a%bw_final_kills%", "&fBeds Broken: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars,&4&lB&6&ledWars,&c&lB&4&le&6&ldWars,&6&lB&c&le&4&ld&6&lWars,&6&lBe&c&ld&4&lW&6&lars,&6&lBed&c&lW&4&la&6&lrs,&6&lBedW&c&la&4&lr&6&ls,&6&lBedWa&c&lr&4&ls,&6&lBedWar&c&ls,&6&lBedWars",
                "&fYour Level: %bw_level%", "", "&fProgress: &a%bw_current_xp%&7/&b%bw_required_xp%", "%bw_progress%", "", "&7%bw_player%", "", "&fCoins: &a%bw_money%", "", "&fTotal Wins: &a%bw_wins%", "&fTotal Kills: &a%bw_kills%", "", "&e%bw_server_ip%"));

        //
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Adding to Quick Buy...");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "%bw_lang_prefix%&cApnar kache porjapto %bw_currency% ney! Apnar aro %bw_amount% beshi lagbe!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "%bw_lang_prefix%&aApni &6%bw_item% &akinlen.");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "%bw_lang_prefix%&cYou've already bought that!");
        yml.addDefault(Messages.SHOP_ALREADY_HIGHER_TIER, "%bw_lang_prefix%&cYou already have a higher tier item.");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "%bw_team_color%&l%bw_team_name% &r%bw_team_color%Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "%bw_team_color%%bw_despawn_time%s &8[ %bw_team_color%%bw_health%&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Categories");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bQuick Buy");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cKhali slot!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7Eti ekti Quick Buy Slot!", "&7Kono item add korte", "&7Ekhane &bSneak Click &7korun."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&ePurchase korte click korun!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cApnar kache porjaptop %bw_currency% ney");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMAXED!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&7Quick Buy te add korte &bSneal Click &7korun");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&7Quick Buy theke remove korte &bSneak Click &7korun!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Blocks", "&aBlocks", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Wool", Arrays.asList("&7Cost: &f%bw_cost% %bw_currency%", "", "&7Great for bridging across", "&7islands. Turns into your team's",
                "&7color.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Hardened Clay", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Basic block to defend your bed.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Blast-Proof Glass", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Immune to explosions.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%End Stone", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Solid block to defend your bed.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Ladder", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Useful to save cats stuck in", "&7trees.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Obsidian", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Extreme protection for your bed.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Wood", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Solid block to defend your bed", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Melee", "&aMelee", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Stone Sword", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Iron Sword", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Diamond Sword", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Stick (KnockBack I)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Armor", "&aArmor", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Permanent Chainmail Armor", Arrays.asList("&7Cost: %bw_cost% %bw_currency%",
                "", "&7Chainmail leggings and boots", "&7which you will always spawn", "&7with.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Permanent Iron Armor", Arrays.asList("&7Cost: %bw_cost% %bw_currency%",
                "", "&7Iron leggings and boots which", "&7you will always spawn with.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Permanent Diamond Armor", Arrays.asList("&7Cost: %bw_cost% %bw_currency%",
                "", "&7Diamond leggings and boots which", "&7you will always crush with.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Tools", "&aTools", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Permanent Shears", Arrays.asList("&7Cost: %bw_cost% %bw_currency%",
                "", "&7Great to get rid of wool. You", "&7will always spawn with these shears.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Pickaxe %bw_tier%", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "&7Tier: &e%bw_tier%",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Axe %bw_tier%", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "&7Tier: &e%bw_tier%",
                "", "&7This is an upgradable item.", "&7It will lose 1 tier upon.", "&7death!", "", "&7You will permanently", "&7respawn with at least the", "&7lowest tier.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Ranged", "&aRanged", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Arrow", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Bow", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Bow (Power I)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Bow (Power I, Punch I)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Potions", "&aPotions", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Speed II Potion (45 seconds)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Jump V Potion (45 seconds)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Invisibility Potion (30 seconds)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Utility", "&aUtility", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Golden Apple", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Well-rounded healing.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%BedBug", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Spawns silverfish where the",
                "&7snowball lands to distract your", "&7enemies. Lasts 15 seconds.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Dream Defender", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Iron Golem to help defend your",
                "&7base. Lasts 4 minutes.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Fireball", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Right-click to launch! Great to",
                "&7knock back enemies walking on", "&7thin bridges", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%TNT", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Instantly ignites, appropriate",
                "&7to explode things!", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Ender Pearl", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7The quickest way to invade enemy",
                "&7bases.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Water Bucket", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Great to slow down approaching",
                "&7enemies. Can also protect", "&7against TNT.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Bridge Egg", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7This egg creates a bridge in its",
                "&7trial after being thrown.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Magic Milk", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Avoid triggering traps for 60",
                "&7seconds after consuming.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Sponge", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Great for soaking up water.",
                "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "tower", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Compact Pop-up Tower", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "&7Place a compact pop-up",
                "&7tower defense!", "", "%bw_quick_buy%", "%bw_buy_status%"));

        //
        yml.addDefault(Messages.MEANING_NO_TRAP, "No trap!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Cost: %bw_currency_color%%bw_cost% %bw_currency%");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "%bw_color%Purchase korte click korun!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "%bw_color%Apnar kache porjapto %bw_currency% ney");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_SPACE, "&eYou don't have enough inventory space to buy this item!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cLOCKED");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "%bw_color%UNLOCKED");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a%bw_player% &6%bw_upgrade_name% &akinlen.");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-1"), "%bw_color%Iron Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "forge"),
                Arrays.asList("&7Upgrade resource spawning on", "&7your island.", "", "{tier_1_color}Tier 1: +50% Resources, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tier 2: +100% Resources, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Tier 3: Spawn emeralds, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Tier 4: +200% Resources, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-2"), "%bw_color%Golden Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-3"), "%bw_color%Emerald Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-4"), "%bw_color%Molten Forge");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eBuy a trap");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Purchased traps will be", "&7queued on the right.", "", "&eClick to browse!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "swords").replace("%bw_tier%", "tier-1"), "%bw_color%Sharpened Swords");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "swords"),
                Arrays.asList("&7Your team permanently gains", "&7Sharpness I on all swords and", "&7axes!", "", "{tier_1_color}Cost: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-1"), "%bw_color%Reinforced Armor I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "armor"),
                Arrays.asList("&7Your team permanently gains", "&7Protection on all armor pieces!", "", "{tier_1_color}Tier 1: Protection I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tier 2: Protection II, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Tier 3: Protection III, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Tier 4: Protection IV, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-2"), "%bw_color%Reinforced Armor II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-3"), "%bw_color%Reinforced Armor III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-4"), "%bw_color%Reinforced Armor IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-1"), "%bw_color%Maniac Miner I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "miner"),
                Arrays.asList("&7All players on your team", "&7permanently gain Haste.", "", "{tier_1_color}Tier 1: Haste I, &b{tier_1_cost} {tier_1_currency}",
                        "&7Tier 2: Haste II, &b6 Diamonds", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-2"), "%bw_color%Maniac Miner II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "heal-pool").replace("%bw_tier%", "tier-1"), "%bw_color%Heal Pool");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "heal-pool"),
                Arrays.asList("&7Creates a Regeneration field", "&7around yor base!", "", "{tier_1_color}Cost: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "dragon").replace("%bw_tier%", "tier-1"), "%bw_color%Dragon Buff");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "dragon"),
                Arrays.asList("&7Your team will have 2 dragons", "&7instead of 1 during deathmatch!", "", "{tier_1_color}Cost: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Purchasable");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Traps Queue"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "%bw_color%Trap #1: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7The first enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "%bw_color%Trap #2: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7The second enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "%bw_color%Trap #3: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7The third enemy to walk", "&7into your base will trigger", "&7this trap!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Purchasing a trap will", "&7queue it here. Its cost", "&7will scale based on the", "&7number of traps queued.", "", "&7Next trap: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "%bw_color%It's a trap!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Inflicts Blindness and Slowness", "&7for 5 seconds.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "%bw_color%Counter-Offensive Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Grants Speed I for 15 seconds to", "&7allied players near your base.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "%bw_color%Alarm Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Reveales invisible players as", "&7well as their name and team.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "%bw_color%Miner Fatigue Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Inflict Mining Fatigue for 10", "&7seconds.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aBack");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7To Upgrades & Traps"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Queue a trap");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cTrap queue full!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l%bw_trap% was set off!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cTRAP TRIGGERED!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fYour %bw_trap% has been triggered!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lAlarm trap set off by &7&l%bw_player% &c&lfrom %bw_color%&l%bw_team% &c&lteam!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lALARM!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fAlarm trap set off by {color}{team} &fteam!");
        save();
        setPrefix(m(Messages.PREFIX));
        setPrefixStatic(m(Messages.PREFIX));
    }
}
