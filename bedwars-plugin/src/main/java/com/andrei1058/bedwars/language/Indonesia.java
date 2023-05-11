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

public class Indonesia extends Language {

    public Indonesia() {
        super(BedWars.plugin, "id");

        YamlConfiguration yml = getYml();
        yml.options().copyDefaults(true);
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Indonesia");

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

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<arena/grup>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3(vip)"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "%bw_lang_prefix% &2Bahasa Tersedia:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7%bw_lang_iso% - &f%bw_name%");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "%bw_lang_prefix%&7Gunakan: /lang &f&o<bahasa>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "%bw_lang_prefix%&cBahasa ini tidak tersedia!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "%bw_lang_prefix%&aBahasa terganti!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "%bw_lang_prefix%&cAnda tidak bisa ganti bahasa saat dalam permainan.");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Gunakan: /" + mainCmd + " join §o<arena/grup>");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "%bw_lang_prefix%&cTidak ada arena atau group yang bernama: %bw_name%");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "%bw_lang_prefix%&cArena tersebut penuh!\n&aSilakan pertimbangkan untuk menyumbang untuk lebih banyak fitur. &7&o(click)");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "%bw_lang_prefix%&cTidak ada arena yang tersedia untuk sekarang ;(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "%bw_lang_prefix%&cKami meminta maaf tapi arena sedang penuh.\n&cKami tahu Anda seorang donatur tetapi sebenarnya arena ini penuh dengan staf atau/dan donatur.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "%bw_lang_prefix%&cParty anda terlalu ramai untuk memasuki arena sebagai team :(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "%bw_lang_prefix%&cHanya pemimpin yang dapat memilih arena.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "%bw_lang_prefix%&7%bw_player% &etelah masuk (&b%bw_on%&e/&b%bw_max%&e)!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "%bw_lang_prefix%§6Kamu sekarang menonton §9%bw_arena%§6.\n%bw_lang_prefix%§eKamu bisa keluar arena kapan pun dengan cara §c/leave§e.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cPenonton tidak diperbolehkan di arena tersebut!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "%bw_lang_prefix%&cPemain tidak ditemukan!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "%bw_lang_prefix%&cPlayer tersebut tidak ada di arena!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "%bw_lang_prefix%&cArena dimana player tersebut berada belum dimulai!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "%bw_lang_prefix%&cGunakan: /bw tp <nama pengguna>");
        yml.addDefault(Messages.REJOIN_NO_ARENA, "%bw_lang_prefix%&cTidak ada arena untuk bergabung kembali!");
        yml.addDefault(Messages.REJOIN_DENIED, "%bw_lang_prefix%&cKamu tidak dapat bergabung ke arena kembali. Pertandingan selesai atau bed kamu telah dihancurkan.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "%bw_lang_prefix%&eMemasuki arena &a%bw_arena%&e!");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "%bw_lang_prefix%&7%bw_player% &etelah terhubung kembali!");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "%bw_lang_prefix%&cKamu sedang tidak di arena!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "%bw_lang_prefix%&7%bw_player% &etelah keluar!");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "%bw_lang_prefix%&cAnda tidak dapat melakukan ini selama pertandingan.");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "%bw_lang_prefix%&cPerintah tidak ditemukan atau Anda tidak memiliki izin!");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aPerintah Party:",
                "&e/party help &7- &bCetak pesan bantuan ini",
                "&e/party invite <player> &7- &bMengundang pemain ke party Anda",
                "&e/party leave &7- &bMeninggalkan party yang anda masuki",
                "&e/party info &7- &bShow party members and owner",
                "&e/party promote <player> &7- &bTransfer party ownership",
                "&e/party remove <player> &7- &bKeluarkan pemain dari party",
                "&e/party accept <player> &7- &bTerima undangan party",
                "&e/party disband &7- &bBubarkan party")
        );
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "%bw_lang_prefix%&eGunakan: &7/party invite <pemain>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "%bw_lang_prefix%&eInvite sent to &7%bw_player%&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "%bw_lang_prefix%&b%bw_player% &ehas invited you to a party! &o&7(Click to accept)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "%bw_lang_prefix%&cKamu tidak dapat mengundang diri anda sendiri!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player% &esedang offline!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "%bw_lang_prefix%&eTidak ada undangan party yang perlu kamu terima!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "%bw_lang_prefix%&eKamu sedang berada di party!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "%bw_lang_prefix%&cHanya pemilik party yang dapat melakukan ini!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "%bw_lang_prefix%&eGunakan: &7/party accept <pemain>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "%bw_lang_prefix%&7%bw_player% &etelah memasuki party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "%bw_lang_prefix%&cKamu tidak berada di party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "%bw_lang_prefix%&cKamu tidak dapat keluar dari party kamu sendiri!\n&eCoba gunakan: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &etelah keluar party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "%bw_lang_prefix%&eParty dibubarkan!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "%bw_lang_prefix%&7Gunakan: &e/party remove <pemain>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "%bw_lang_prefix%&7%bw_player% &etelah dikeluarkan dari party,");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "%bw_lang_prefix%&7%bw_player% &etidak ada di party!");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_SUCCESS, "%bw_lang_prefix%&eYou successfully promoted %bw_player% to owner");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_OWNER, "%bw_lang_prefix%&eYou have been promoted to party owner");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER, "%bw_lang_prefix%&7 &e%bw_player% has been promoted to owner");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_OWNER, "\n%bw_lang_prefix%&eOwner of the party is: &7%bw_party_owner%");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYERS,"%bw_lang_prefix%&eParty members:");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYER, "&7%bw_player%");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7Kamu sedang tidak bermain!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Hitung mundur dipersingkat!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "%bw_lang_prefix%&7Anda tidak dapat memaksa memulai arena.\n§7Harap pertimbangkan untuk menyumbang untuk fitur VIP.");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cAnda belum bisa melakukannya! Tunggu %bw_seconds% detik lagi!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "%bw_lang_prefix%&cMaaf, tapi Anda dikeluarkan karena donatur bergabung ke arena.\n&aHarap pertimbangkan untuk menyumbang untuk lebih banyak fitur. &7&o(click)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "%bw_lang_prefix%§cTidak ada cukup pemain! Hitung mundur dihentikan!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "%bw_lang_prefix%&eArena tempat Anda berada sedang dimulai ulang.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cBermain");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Memulai Kembali");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Menunggu §c%bw_full%");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Mulai §c%bw_full%");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Klik untuk bergabung!");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l%bw_name%");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Status: %bw_arena_status%", "&7Pemain: &f%bw_on%&7/&f%bw_max%", "&7Tipe: &a%bw_group%", "", "&aKlik Kiri untuk bergabung.", "&eKlik Kanan untuk menonton."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r%bw_server_ip%");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "%bw_lang_prefix%&ePermainan dimulai dalam &6%bw_time% &edetik!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a%bw_seconds%");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cMenunggu lebih banyak pemain..");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aMULAI");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lPerang Kasur", "",
                "&e&l    Lindungi kasur Anda dan hancurkan kasur musuh.",
                "&e&l  Tingkatkan diri Anda dan tim Anda dengan mengumpulkan",
                "&e&l   Besi, Emas, Zamrud, dan Berlian dari generator",
                "&e&l   untuk mendapatkan akses peningkatan yang kuat.", "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "%bw_lang_prefix%&cMaaf, tetapi sementara Anda tidak dapat bergabung ke arena tersebut. Gunakan Klik Kanan untuk menonton!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "%bw_lang_prefix%&cMaaf tapi sementara kamu tidak bisa menonton arena ini. Gunakan Klik Kiri untuk bergabung!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cMaaf, tetapi Anda harus bergabung ke arena menggunakan BedWarsProxy. \n&eJika Anda ingin menyiapkan arena, pastikan untuk memberi diri Anda izin bw.setup sehingga Anda dapat bergabung dengan server secara langsung!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Teleporter");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "%bw_lang_prefix%%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Hati: &f%bw_player_health%%", "&7Bar Kelaparan: &f%bw_player_food%", "", "&7Klik kiri untuk menonton!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lKembali ke lobi");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Klik kanan untuk keluar ke lobi!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aMenonton &7%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK untuk keluar");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eKeluar dari mode Penonton");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "%bw_lang_prefix%§cPemilik party telah pergi dan party itu dibubarkan!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eTingkat &c%bw_tier%");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lBerlian");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lZamrud");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eMuncul dalam &c%bw_seconds% &edetik");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "%bw_lang_prefix%%bw_generator_type% Generator &etelah ditingkatkan ke Tingkat &c%bw_tier%");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "%bw_level%%bw_v_prefix%&6[SHOUT] %bw_team_format% &7%bw_player%&f%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "%bw_level%%bw_v_prefix%&f%bw_team_format%&7 %bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "&7[PENONTON] %bw_level%%bw_v_prefix%%bw_player%%bw_v_suffix%: %bw_message%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, "%health% &c❤");
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "PENONTON");
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
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 KAMU");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTracking: %bw_team% &f- Distance: %bw_distance%m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "%bw_team_color%[%bw_team_name%]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[PENONTON]");
        yml.addDefault(Messages.MEANING_FULL, "Penuh");
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.MEANING_NOBODY, "Bukan Siapa Siapa");
        yml.addDefault(Messages.MEANING_NEVER, "Tidak Pernah");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Besi");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Besi");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Emas");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Emas");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Zamrud");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Zamrud");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Zamrud");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Zamrud");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "%bw_lang_prefix%&cAnda tidak dapat menempatkan blok di sini!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "%bw_lang_prefix%&cAnda hanya dapat memecahkan blok yang ditempatkan oleh pemain!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cAnda tidak dapat menghancurkan kasur Anda sendiri!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lPENGHANCURAN KASUR > %bw_team_color%%bw_team_name% Kasur &7telah dihancurkan oleh %bw_player_color%%bw_player%&7!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cKASUR HANCUR!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fAnda tidak akan respawn kembali!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&lPENGHANCURKAN KASUR N > &7Kasur Anda telah dihancurkan oleh %bw_player_color%%bw_player%&7!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cAnda tidak dapat membuka peti ini karena tim ini belum tereliminasi!");
        yml.addDefault(Messages.INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN, "&cYou are no longer invisible because you have taken damage!");
        yml.addDefault(Messages.INTERACT_MAGIC_MILK_REMOVED, "&cYour Magic Milk wore off!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "%bw_player_color%%bw_player% &7jatuh ke void.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "%bw_player_color%%bw_player% &7jatuh ke void. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "%bw_player_color%%bw_player% &7terlempar ke dalam void oleh %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "%bw_player_color%%bw_player% &7terlempar ke dalam void oleh %bw_killer_color%%bw_killer_name%&7. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "%bw_player_color%%bw_player% &7terputus saat bertarung dengan %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "%bw_player_color%%bw_player% &7terputus saat bertarung dengan %bw_killer_color%%bw_killer_name%&7. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "%bw_player_color%%bw_player% &7didorong oleh %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "%bw_player_color%%bw_player% &7didorong oleh %bw_killer_color%%bw_killer_name%&7. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "%bw_player_color%%bw_player% &7terkena bom cinta dari %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% &7terkena bom cinta dari %bw_killer_color%%bw_killer_name%&7. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "%bw_player_color%%bw_player% &7terkena bom.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player% &7terkena bom. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%%bw_killer_name%&7. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "%bw_player_color%%bw_player% &7mati.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "%bw_player_color%%bw_player% &7mati. &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "%bw_player_color%%bw_player% &7ditembak oleh %bw_killer_color%%bw_killer_name%&7.");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "%bw_player_color%%bw_player% &7ditembak oleh %bw_killer_color%%bw_killer_name%&7! &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%BedBug si %bw_killer_team_name%!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%%bw_killer_team_name%'s &7BedBug! &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%Iron Golem si %bw_killer_team_name%!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "%bw_player_color%%bw_player% &7dibunuh oleh %bw_killer_color%Iron Golem si %bw_killer_team_name%! &b&lPEMBUNUHAN TERAKHIR!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "%bw_lang_prefix%&b+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "%bw_lang_prefix%&a+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "%bw_lang_prefix%&f+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "%bw_lang_prefix%&6+%bw_amount% %bw_meaning%");
        yml.addDefault(Messages.ARENA_MAX_BUILD_LIMIT_REACHED, "&cMax build height limit reached!");
        yml.addDefault(Messages.ARENA_MIN_BUILD_LIMIT_REACHED, "&cMin build height limit reached!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cKAMU MATI!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eAnda akan muncul kembali dalam &c%bw_time% &edetik!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "%bw_lang_prefix%&eAnda akan muncul kembali dalam &c%bw_time% &edetik!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aMUNCUL KEMBALI!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "%bw_lang_prefix%&cKamu telah dieliminasi!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TEXT, "%bw_lang_prefix%&eYou have respawned!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "%bw_lang_prefix%%bw_player% &7berada di &c%bw_damage_amount% &7HP!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lPERMAINAN SELESAI!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lKEMENANGAN!");
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "%bw_lang_prefix%%bw_team_color%%bw_team_name% &atelah memenangkan permainan!");
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
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lPertahankan Kasur Anda!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lKasur anda telah dihancurkan!");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&bPENINGKATAN TIM,&e&LKLIK KANAN");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&bPENINGKATAN INDIVIDU,&e&lKLIK KANAN");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&bTOKO TIM,&e&lKLIK KANAN");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&bTOKO BARANG,&e&lKLIK KANAN");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&lTIM ELIMINASI > %bw_team_color%Tim %bw_team_name% &ctelah dieliminasi!\n");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cPenghancuran Kasur");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fBerlian II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fBerlian III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fKematian Mendadak");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fZamrud II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fZamrud III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Permainan Berakhir");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cKASUR HANCUR!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fSemua kasur telah dihancurkan!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lSemua kasur telah dihancurkan!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cKematian Mendadak");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cKEMATIAN MENDADAK: &6&b%bw_dragons_amount% %bw_team_color%Naga %bw_team_name%!");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Waktu Bermain).");
        yml.addDefault(Messages.XP_REWARD_WIN, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Memenangkan Permainan).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Dukungan Tim).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Menghancurkan Kasur).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Pembunuhan Biasa).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_xp% Experience Bed Wars Diterima (Pembunuhan Terakhir).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_money% Koin (Waktu Bermain).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "%bw_lang_prefix%&6+%bw_money% Koin (Memenangkan Permainan).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_money% Koin (Dukungan Tim).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "%bw_lang_prefix%&6+%bw_money% Koin (Menghancurkan Kasur).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_money% Koin (Pembunuhan Terakhir).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_money% Koin (Pembunuhan Biasa).");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eStatistik");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKlik Kanan untuk melihat statistik!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&ePemilih Arena");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fKlik Kanan untuk memilih Arena!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eKembali ke Pusat");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fKlik Kanan untuk keluar dari Bed Wars!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eStatistik");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fKlik Kanan untuk melihat statistik!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eKembali ke Lobi");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fKlik kanan untuk keluar Arena!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eTeleporter");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eKembali ke Lobi");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fKlik kanan untuk keluar Arena!"));

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8Statistik %bw_player%");
        addDefaultStatsMsg(yml, "wins", "&6Kememenangan", "&f%bw_wins%");
        addDefaultStatsMsg(yml, "losses", "&6Kekalahan", "&f%bw_losses%");
        addDefaultStatsMsg(yml, "kills", "&6Membunuh", "&f%bw_kills%");
        addDefaultStatsMsg(yml, "deaths", "&6Mati", "&f%bw_deaths%");
        addDefaultStatsMsg(yml, "final-kills", "&6Pembunuhan Terakhir", "&f%bw_final_kills%");
        addDefaultStatsMsg(yml, "final-deaths", "&6Kematian Terakhir", "&f%bw_final_deaths%");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Menghancurkan Kasur", "&f%bw_beds%");
        addDefaultStatsMsg(yml, "first-play", "&6Pertama Kali Bermain", "&f%bw_play_first%");
        addDefaultStatsMsg(yml, "last-play", "&6Terakhir Kali Bermain", "&f%bw_play_last%");
        addDefaultStatsMsg(yml, "games-played", "&6Pertandingan Dimainkan", "&f%bw_games_played%");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&fPERANG KASUR", "&7%bw_date% &8%bw_server_id%", "", "&fMap: &a%bw_map%", "", "&fPlayers: &a%bw_on%/%bw_max%", "", "&fWaiting...", "", "§fMode: &a%bw_group%", "&fVersion: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&lPERANG KASUR", "&7%bw_date% &8%bw_server_id%", "", "&fMap: &a%bw_map%", "", "&fPlayers: &a%bw_on%/%bw_max%", "", "&fStarting in &a%bw_time%s", "", "§fMode: &a%bw_group%", "&fVersion: &7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&lPERANG KASUR", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&lPERANG KASUR", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&lPERANG KASUR", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fKills: &a%bw_kills%", "&fFinal Kills: &a%bw_final_kills%", "&fBeds Broken: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&lPERANG KASUR", "&7%bw_date%", "", "&f%bw_next_event% in &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&fKills: &a%bw_kills%", "&fPembunuhan Terakhir: &a%bw_final_kills%", "&fBed Dihancurkan: &a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&lBedWars,&4&lB&6&ledWars,&c&lB&4&le&6&ldWars,&6&lB&c&le&4&ld&6&lWars,&6&lBe&c&ld&4&lW&6&lars,&6&lBed&c&lW&4&la&6&lrs,&6&lBedW&c&la&4&lr&6&ls,&6&lBedWa&c&lr&4&ls,&6&lBedWar&c&ls,&6&lBedWars",
                "&fLevel Anda: %bw_level%", "", "&fProgres: &a%bw_current_xp%&7/&b%bw_required_xp%", "%bw_progress%", "", "&7%bw_player%", "", "&fKoin: &a%bw_money%", "", "&fTotal Kemenangan: &a%bw_wins%", "&fTotal Membunuh: &a%bw_kills%", "", "&e%bw_server_ip%"));

        //
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Quick Buy");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Adding to Quick Buy...");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "%bw_lang_prefix%&cKamu tidak mempunyai %bw_currency% yang cukup! Butuh %bw_amount% lagi!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "%bw_lang_prefix%&aKamu membeli &6%bw_item%");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "%bw_lang_prefix%&cKamu telah membeli itu!!");
        yml.addDefault(Messages.SHOP_ALREADY_HIGHER_TIER, "%bw_lang_prefix%&cYou already have a higher tier item.");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "%bw_team_color%&l%bw_team_name% &r%bw_team_color%Silverfish");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "%bw_team_color%%bw_despawn_time%s &8[ %bw_team_color%%bw_health%&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Categories");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Items"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bQuick Buy");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cEmpty slot!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7This is a Quick Buy Slot!", "&bSneak Click &7any item in", "&7the shop to add it here."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&eKlik untuk membeli!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cKamu tidak mempunyai %bw_currency% yang cukup!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aMAKSIMAL!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bJongkok Klik untuk menambahkan ke Beli Cepat!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bJongkok Klik untuk menhapus dari Beli Cepat!");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Blok", "&aBlok", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "wol", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Wol", Arrays.asList("&7Biaya: &f%bw_cost% %bw_currency%", "", "&7Bagus untuk menyebrang", "&7pulau. Berubah menjadi tim Anda",
                "&7color.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Hardened Clay", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Blok dasar untuk mempertahankan kasur Anda", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Kaca Anti Ledakan", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Kebal terhadap ledakan", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%End Stone", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Blok keras untuk mempertahankan kasur Anda", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Tangga", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Berguna untuk menyelamatkan kucing yang terjebak", "&7di pohon.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Obsidian", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Perlindungan ekstrim untuk kasur Anda", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%Kayu", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Blok keras untuk mempertahankan kasur Anda", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Senjata Jarak Dekat", "&aSenjata Jarak Dekat", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Stone Sword", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Iron Sword", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Diamond Sword", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%Stik (Knockback I)", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Armor", "&aArmor", Collections.singletonList("&eClick to view!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Armor Chainmail Permanenr", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%",
                "", "&7Celana dan sepatu chainmail yang dapat membuat", "&7kamu akan selalu muncul dengan ini", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Armor Besi Permanen", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%",
                "", "&7Celana dan sepatu besi yang dapat membuat", "&7kamu akan selalu muncul dengan ini.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%Armor Berlian Permanen", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%",
                "", "&7Celana dan sepatu berlian yang dapat membuat", "&7kamu akan selalu naksir dengan ini.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Tools", "&aTools", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Gunting Permanen", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%",
                "", "&7Bagus untuk menyingkirkan wol. Kamu", "&7akan selalu muncul dengan gunting ini.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Pickaxe %bw_tier%", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "&7Tingkat: &e%bw_tier%",
                "", "&7Ini adalah item yang dapat diupgrade.", "&7Itu akan kehilangan 1 tingkat setelah.", "&7mati!", "", "&7Anda akan secara permanen", "&7respawn dengan setidaknya", "&7tingkat terendah.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%Kapak %bw_tier%", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "&7Tingkat: &e%bw_tier%",
                "", "&7Ini adalah item yang dapat diupgrade.", "&7Itu akan kehilangan 1 tingkat setelah.", "&7mati!", "", "&7Anda akan secara permanen", "&7respawn dengan setidaknya", "&7tingkat terendah.", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Senjata Jarak Jauh", "&aSenjata Jarak Jauh", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Anak Panah", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Panah", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Panah (Power I)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%Panah (Power I, Punch I)", Arrays.asList("&7Cost: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Ramuan", "&aRamuan", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Ramuan Kecepatan II (45 detik)", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Ramuan Lompat V (45 detik)", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%Ramuan Tak Kasat Mata (30 detik)", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Utilitas", "&aUtilitas", Collections.singletonList("&eKlik untuk melihat!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Apel Emas", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Penyembuhan menyeluruh.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Kutu Kasur", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Memunculkan ikan gabus di mana",
                "&7bola salju mendarat untuk mengalihkan perhatian", "&7musuh. Berlangsung 15 detik.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Pertahanan Impian", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Iron Golem untuk membantu mempertahankan",
                "&7base. Berlangsung 4 menit.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Bola Api", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Klik kanan untuk meluncurkan! Bagus untuk",
                "&7memukul mundur musuh yang berjalan", "&7di jembatan tipis", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%TNT", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Langsung menyala, cocok",
                "&7untuk meledakkan sesuatu!", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Ender Pearl", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Cara tercepat untuk menyerang base",
                "&7musuh.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Water Bucket", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Bagus untuk memperlambat musuh.",
                "&7Bisa juga melindungi", "&7dari serangan TNT.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Telur Jembatan", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Telur ini menciptakan jembatan di",
                "&7percobaan setelah dilempar.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Susu Ajaib", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Hindari memicu jebakan untuk 60",
                "&7detik setelah diminum.", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%Spons", Arrays.asList("&7Biaya: %bw_cost% %bw_currency%", "", "&7Bagus untuk menyerap air.",
                "", "%bw_quick_buy%", "%bw_buy_status%"));

        //
        yml.addDefault(Messages.MEANING_NO_TRAP, "Tidak ada Perangkap!");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Biaya: %bw_currency_color%%bw_cost% %bw_currency%");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "%bw_color%Klik untuk membeli!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "%bw_color%Anda tidak punya %bw_currency% yang cukup!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_SPACE, "&eYou don't have enough inventory space to buy this item!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cTERKUNCI");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "%bw_color%TERBUKA");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a%bw_player% membeli &6%bw_item%");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-1"), "%bw_color%Iron Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "forge"),
                Arrays.asList("&7Tingkatkan pemijahan sumber daya aktif", "&7di pulau mu.", "", "{tier_1_color}Tingkat 1: +50% Sumber Daya, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tingkat 2: +100% Sumber Daya, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Tingkat 3: Spawn zamrud, &b{tier_3_cost} B{tier_3_currency}",
                        "{tier_4_color}Tingkat 4: +200% Sumber Daya, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-2"), "%bw_color%Golden Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-3"), "%bw_color%Emerald Forge");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-4"), "%bw_color%Molten Forge");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&aBeli perangkap");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7Perangkap yang dibeli akan menjadi", "&7antri di sebelah kanan.", "", "&eKlik untuk menelusuri!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "swords").replace("%bw_tier%", "tier-1"), "%bw_color%Pedang yang Diasah");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "swords"),
                Arrays.asList("&7Tim Anda memperoleh keuntungan secara permanen", "&7Ketajaman I pada semua pedang dan", "&7kapak!", "", "{tier_1_color}Biaya: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-1"), "%bw_color%Armor Bertulang I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "armor"),
                Arrays.asList("&7Tim Anda memperoleh keuntungan secara permanen", "&7Perlindungan pada semua bagian armor!", "", "{tier_1_color}Tingkat 1: Perlindungan I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tingkat 2: Perlindungan II, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Tingkat 3: Perlindungan III, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Tingkat 4: Perlindungan IV, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-2"), "%bw_color%Armor Bertulang II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-3"), "%bw_color%Armor Bertulang III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-4"), "%bw_color%Armor Bertulang IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-1"), "%bw_color%Penambang Maniak I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "miner"),
                Arrays.asList("&7Semua pemain di tim Anda", "&7mendapatkan Haste secara permanen.", "", "{tier_1_color}Tingkat 1: Haste I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Tingkat 2: Haste II, &b{tier_2_cost} {tier_2_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-2"), "%bw_color%Penambang Maniak II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "heal-pool").replace("%bw_tier%", "tier-1"), "%bw_color%Kolam Penyembuhan");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "heal-pool"),
                Arrays.asList("&7Membuat bidang Regenerasi", "&7di sekitar base Anda!", "", "{tier_1_color}Biaya: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "dragon").replace("%bw_tier%", "tier-1"), "%bw_color%Dragon Buff");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "dragon"),
                Arrays.asList("&7Tim Anda akan memiliki 2 naga", "&7bukannya 1 selama deathmatch!", "", "{tier_1_color}Biaya: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Dapat Dibeli");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Antrian Perangkap"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "%bw_color%Perangkap #1: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7Musuh pertama yang berjalan", "&7ke base Anda akan memicu", "&7perangkap ini!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Membeli perangkap akan", "&7antri disini. biayanya", "&7akan skala berdasarkan", "&7jumlah jebakan yang diantrekan.", "", "&7Perangkap Selanjutnya: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "%bw_color%Perangkap #2: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7Musuh kedua yang berjalan", "&7ke base Anda akan memicu", "&7perangkap ini!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Membeli perangkap akan", "&7antri disini. biayanya", "&7akan skala berdasarkan", "&7jumlah jebakan yang diantrekan.", "", "&7Perangkap Selanjutnya: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "%bw_color%Perangkap #3: %bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7Musuh ketiga yang berjalan", "&7ke base Anda akan memicu", "&7perangkap ini!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Membeli perangkap akan", "&7antri disini. biayanya", "&7akan skala berdasarkan", "&7jumlah jebakan yang diantrekan.", "", "&7Perangkap Selanjutnya: &b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "%bw_color%It's a trap!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Menimbulkan Kebutaan dan Kelambatan", "&7selama 5 detik.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "%bw_color%Counter-Offensive Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Memberikan Kecepatan I selama 15 detik untuk", "&7pemain sekutu di dekat base Anda.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "%bw_color%Alarm Trap");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Mengungkapkan pemain tak terlihat serta", "&7nama dan tim mereka.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "%bw_color%Perangkap Kelelahan Penambang");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Menimbulkan Kelelahan Penambangan selama 10", "&7detik.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aKembali");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7Ke Upgrades & Perangkap"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Antrian Jebakan");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cAntrian jebakan penuh!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l%bw_trap% telah berangkat!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cPERANGKAP TERPICU!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fPerangkap %bw_trap% telah terpicu!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&lPerangkap alarm telah dipicu oleh &7&l%bw_player% &c&ldari tim %bw_color%&l%bw_team%!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lALARM!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&fPerangkap alarm telah dipicu oleh tim %bw_color%%bw_team%&f!");
        yml.addDefault(Messages.UPGRADES_UPGRADE_ALREADY_CHAT, "&cYou already unlocked this upgrade!");
        save();
        setPrefix(m(Messages.PREFIX));
        setPrefixStatic(m(Messages.PREFIX));
    }
}
