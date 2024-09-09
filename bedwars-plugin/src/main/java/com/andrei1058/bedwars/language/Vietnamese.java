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

public class Vietnamese extends Language {

    public Vietnamese() {
        super(BedWars.plugin, "vi");

        YamlConfiguration yml = getYml();
        yml.options().copyDefaults(true);
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "Tiếng Việt");

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

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " thống kê", "&2▪ &7/" + mainCmd + " tham gia &o<đấu trường/nhóm>", "&2▪ &7/" + mainCmd + " thoát", "&2▪ &7/" + mainCmd + " ngôn ngữ", "&2▪ &7/" + mainCmd + " giao diện", "&2▪ &7/" + mainCmd + " bắt đầu &3(vip)"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "{prefix} &2Ngôn ngữ có sẵn:");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7{iso} - &f{name}");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "{prefix}&7Cách dùng: /lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "{prefix}&cNgôn ngữ này không tồn tại!");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "{prefix}&aNgôn ngữ đã được thay đổi!");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "{prefix}&cBạn không thể thay đổi ngôn ngữ trong trận.");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "§a▪ §7Cách dùng: /" + mainCmd + " join §o<arena/group>");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "{prefix}&cKhông có đầu trường nào có tên là: {name}");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "{prefix}&cTrận đã đầy!\n&aXin hãy quyên góp để nhận thêm tính năng. &7&o(nhấn vào đây)");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "{prefix}&cHiện tại không có đấu trường nào trống ;(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "{prefix}&cChúng tôi xin lỗi, nhưng đấu trường này đã đầy.\n&cChúng tôi biết bạn là nhà tài trợ nhưng thực tế đấu trường này đã đầy với staff hoặc nhà tài trợ khác.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "{prefix}&cNhóm của bạn quá đông để tham gia đấu trường này.");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "{prefix}&cChỉ có trưởng nhóm mới có thể chọn đấu trường.");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "{prefix}&7{player} &eđã tham gia (&b{on}&e/&b{max}&e)!");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "{prefix}§6Bạn hiện đang quan sát §9{arena}§6.\n{prefix}§eBạn có thể rời khỏi trận bất cứ lúc nào bằng lệnh §c/leave§e.");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&cKhông cho phép khán giả tham gia trận này!");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "{prefix}&cKhông tìm thấy người chơi!");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "{prefix}&cNgười chơi này không có trong đấu trường bedwars!");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "{prefix}&cĐấu trường mà người chơi đang ở chưa bắt đầu!");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "{prefix}&cCách dùng: /bw tp <tên người chơi>");
        yml.addDefault(Messages.REJOIN_NO_ARENA, "{prefix}&cKhông có đấu trường nào để tham gia lại!");
        yml.addDefault(Messages.REJOIN_DENIED, "{prefix}&cBạn không thể tham gia lại đấu trường. Trò chơi đã kết thúc hoặc giường đã bị phá hủy.");
        yml.addDefault(Messages.REJOIN_ALLOWED, "{prefix}&eĐang tham gia đấu trường &a{arena}&e!");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "{prefix}&7{player} &eđã kết nối lại!");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "{prefix}&cBạn không ở trong đấu trường!");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "{prefix}&7{player} &eđã rời đi!");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "{prefix}&cBạn không thể làm điều này trong trò chơi.");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "{prefix}&cKhông tìm thấy lệnh hoặc bạn không có quyền thực hiện lệnh!");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&aCác lệnh sử dụng tính năng đội:",
                "&e/party help &7- &bMở phần giúp đỡ này.",
                "&e/party invite <người chơi> &7- &bMời người chơi vào đội của bạn.",
                "&e/party leave &7- &bThoát đội hiện tại.",
                "&e/party remove <người chơi> &7- &bĐá người chơi khỏi đội ;)",
                "&e/party info &7- &bHiển thị thành viên đội và chủ sở hữu",
                "&e/party promote <người chơi> &7- &bChuyển chủ đội cho người chơi khác.",
                "&e/party accept <người chơi> &7- &bCTham gia đội của người mời.",
                "&e/party disband &7- &bGiải tán đội hiện tại ;("
        ));
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "{prefix}&eCách dùng: &7/party invite <người chơi>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "{prefix}&7{player} &eđang offline!");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "{prefix}&eĐã gửi lời mời tới &7{player}&6.");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "{prefix}&b{player} &evừa mời bạn vào đội! &o&7(Click để tham gia)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "{prefix}&cBạn không thể tự mời chính mình!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "{prefix}&cHiện không có lời mời vào đội nào!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "{prefix}&eBạn hiện đang ở trong một đội!");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "{prefix}&cChỉ chủ đội mới có thể sử dụng!");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "{prefix}&eCách dùng: &7/party accept <người chơi>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "{prefix}&7{player} &ehas joined the party!");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "{prefix}&cYou're not in a party!");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "{prefix}&cYou can't leave your own party!\n&eTry using: &b/party disband");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "{prefix}&7{player} &ehas left the party!");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "{prefix}&eĐội đã giải tán!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "{prefix}&7Cách dùng: &e/party remove <người chơi>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "{prefix}&7{player} &eđã bị đá khỏi đội!");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "{prefix}&7{player} &ekhông có trong đội của bạn!");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_SUCCESS, "{prefix}&eBạn đã thăng chức {player} lên làm chủ đội!");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_OWNER, "{prefix}&eBạn đã được thăng chức làm chủ đội");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER, "{prefix}&7 &e{player} đã được thăng chức làm chủ đội");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_OWNER, "\n{prefix}&eChủ sở hữu của đội là: &7{owner}");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYERS, "{prefix}&eThành viên của đội:");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYER, "&7{player}");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "§c▪ §7You're not playing!");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "§c▪ §7Đã cắt ngắn đếm ngược!");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "{prefix}&7Bạn không thể bắt đầu sớm trận đấu này.");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&cVui lòng chờ thêm {seconds} giây!");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "{prefix}&cXin lỗi, nhưng bạn đã bị loại khỏi đấu trường vì một nhà tài trợ đã tham gia.\n&aHãy cân nhắc quyên góp để nhận thêm tính năng. &7&o(Nhấn vaò đây)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "{prefix}§cKhông đủ người chơi! Đã dừng đếm ngược!");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "{prefix}&ePhòng đang khởi động lại.");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&cĐang chơi");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4Đang khởi động lại");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&2Đang chờ §c{full}");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6Đang bắt đầu §c{full}");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8Nhấn để tham gia");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l{name}");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7Trạng thái: {status}", "&7Người chơi: &f{on}&7/&f{max}", "&7Loại: &a{group}", "", "&aNhấn chuột trái để tham gia.", "&eNhấn chuột phải để quan sát."));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r{serverIp}");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "{prefix}&eGame sẽ bắt đầu trong &6{time} &egiây!");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a{second}");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e5");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e4");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c3");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c2");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c1");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&cChờ thêm người...");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&aGO");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBed Wars", "",
                "&e   Bảo vệ giường của bạn và phá hủy giường của kẻ địch.",
                "&e   Nâng cấp cho bản thân và đồng đội bằng cách thu thập",
                "&e   tài nguyên từ máy tạo để có sức mạnh bất khả chiến bại!",
                "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "{prefix}&cBạn không thể vào trận lúc này. Bấm chuột phải để xem trận!");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "{prefix}&cBạn không thể xem trận lúc này. Bấm chuột trái để vào trận!");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&cXin lỗi, nhưng bạn phải tham gia đấu trường thông qua BedWarsProxy. \n&eNếu bạn muốn thiết lập một đấu trường, hãy đảm bảo cấp cho mình quyền bw.setup để có thể tham gia máy chủ trực tiếp!");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8Dịch chuyển");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "{vPrefix}{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7Máu: &f{health}%", "&7Thức ăn: &f{food}", "", "&7Chuột trái để theo dõi"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&lTrở về lobby");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7Nhấn chuột phải để quay lại sảnh!"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&aĐang theo dõi &7{player}");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&cSNEAK để thoát");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&eĐang thoát chế độ Khán giả");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "{prefix}§cChủ đội đã rời, đội giải tán!");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&eCấp &c{tier}");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&lKim cương");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&lNgọc lục bảo");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&eTạo ra trong &c{seconds} &egiây");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "{prefix}{generatorType} &eđã được nâng lên cấp &c{tier}");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "%minigames_level_icon%&7{vPrefix}&7{player}{vSuffix}&f: %minigames_chatcolor%{message}");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "%minigames_level_icon%&7{vPrefix}&7{player}{vSuffix}&f: %minigames_chatcolor%{message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "%minigames_level_icon%&7{vPrefix}&6[SHOUT] {team} &7{player}&f{vSuffix}&f: %minigames_chatcolor%{message}");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "%minigames_level_icon%&7{vPrefix}&f{team}&7 {player}{vSuffix}&f: %minigames_chatcolor%{message}");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "%minigames_level_icon%&7{vPrefix}&7[SPECTATOR] {player}{vSuffix}&f: %minigames_chatcolor%{message}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, Arrays.asList("&c❤", "&aHealth"));

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "dd/MM/yy");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "{TeamColor}{TeamLetter}&f {TeamName}: {TeamStatus}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a{remainingPlayers}");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 Bạn");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTheo dõi: {team} &f- Khoảng cách: {distance}m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 {TeamColor}{TeamName} &7- {members}");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "{TeamColor}[{TeamName}]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[SHOUT]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[KHÁN GIẢ]");
        yml.addDefault(Messages.MEANING_FULL, "Đầy");
        yml.addDefault(Messages.MEANING_SHOUT, "shout");
        yml.addDefault(Messages.MEANING_NOBODY, "Không ai");
        yml.addDefault(Messages.MEANING_NEVER, "Không bao giờ");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "Sắt");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "Sắt");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "Vàng");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "Vàng");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "Ngọc lục bảo");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "Ngọc lục bảo");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "Kim cương");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "Kim cương");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "{prefix}&cBạn không thể đặt khối ở đây!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "{prefix}&cBạn chỉ được phá khối mà người chơi đặt!");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&cBạn không thể tự phá giường của bạn!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&lPHÁ GIƯỜNG > {TeamColor}Giường {TeamColor}{TeamName} &7đã bay màu bởi {PlayerColor}{PlayerName}!\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&cGIƯỜNG BỊ PHÁ HỦY!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&fBạn sẽ không thể hồi sinh nữa!");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&c&lPHÁ GIƯỜNG > &cGiường của bạn đã bị phá bởi {PlayerColor}{PlayerName}!");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&cBạn chưa thể mở rương này!");
        yml.addDefault(Messages.INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN, "&cBạn không còn tàng hình nữa vì bạn đã bị tấn công!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "{PlayerColor}{PlayerName} &7đã rơi xuống vực.");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã rơi xuống vực. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "{PlayerColor}{PlayerName} &7đã bị đẩy xuống vực bởi {KillerColor}{KillerName}&7. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "{PlayerColor}{PlayerName} &7đã bị ngắt kết nối trong khi đánh với {KillerColor}{KillerName}&7. Đen thật đấy.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "{PlayerColor}{PlayerName} &7đã bị ngắt kết nối trong khi đánh với {KillerColor}{KillerName}&7. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "{PlayerColor}{PlayerName} &7đã bị đẩy đến chết bởi {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã bị đẩy đến chết bởi {KillerColor}{KillerName}&7. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "{PlayerColor}{PlayerName} &7bị hạ bởi một quả bom tình yêu từ {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &bị hạ bởi một quả bom tình yêu t {KillerColor}{KillerName}&7. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "{PlayerColor}{PlayerName} &7đã bị hạ bởi một quả bom. Đau đấy.");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã bị hạ bởi một quả bom. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7.");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "{PlayerColor}{PlayerName} &7was killed by {KillerColor}{KillerName}&7. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "{PlayerColor}{PlayerName} &7died.");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "{PlayerColor}{PlayerName} &7died. &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "{PlayerColor}{PlayerName} &7đã bị bắn hạ bởi {KillerColor}{KillerName}&7!");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã bị bắn hạ bởi {KillerColor}{KillerName}&7! &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "{PlayerColor}{PlayerName} &7đã bị hạ bởi bọ của {KillerColor}{KillerTeamName} &7!");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã bị hạ bởi bọ của {KillerColor}{KillerTeamName} &7! &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "{PlayerColor}{PlayerName} &7đã bị hạ bởi Người Sắt của {KillerColor}{KillerTeamName} &7!");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "{PlayerColor}{PlayerName} &7đã bị hạ bởi Người Sắt của {KillerColor}{KillerTeamName} &7! &b&lKẾT LIỄU!");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "{prefix}&b+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "{prefix}&a+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "{prefix}&f+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "{prefix}&6+{amount} {meaning}");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&cBẠN ĐÃ HẺO!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&eBạn sẽ hồi sinh trong &c{time} &egiây!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "{prefix}&eBạn sẽ hồi sinh trong &c{time} &egiây!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&aĐÃ HỒI SINH!");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "{prefix}&cBạn đã bị loại");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "{prefix}{TeamColor}{PlayerName} &7còn &c{amount} &7HP!");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&lTRÒ CHƠI KẾT THÚC!");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&lCHIẾN THẮNG!");
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "{prefix}{TeamColor}{TeamName} &ađã thắng cuộc!");
        //TODO: Kiwi
        yml.addDefault(Messages.GAME_END_TOP_PLAYER_CHAT, Arrays.asList("",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                   &lBedWars", "", "{winnerFormat}", "", "",
                "&6                       &6⭐ &l1st Killer &7- {topTeamColor}{topPlayerDisplayName} &7- &l{topValue}",
                "&e                          &l2nd Killer &7- {topTeamColor}{topPlayerDisplayName} &7- &l{topValue}",
                "&c                          &l3rd Killer &7- {topTeamColor}{topPlayerDisplayName} &7- &l{topValue}",
                "",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&lBảo vệ giường của bạn!");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&lGiường đã bị phá hủy!");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&b&lNÂNG CẤP,&eCHUỘT PHẢI");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&b&lNÂNG CẤP,&eCHUỘT PHẢI");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&b&lCỬA HÀNG,&eCHUỘT PHẢI");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&b&lCỬA HÀNG,&eCHUỘT PHẢI");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&c&lĐỘI BỊ LOẠI > {TeamColor}Đội {TeamColor}{TeamName} &7đã bị loại khỏi cuộc chơi!\n");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&cPhá giường");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&fKim cương II");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&fKim cương III");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&fSinh tử");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&fLục bảo II");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&fLục bảo III");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4Trò chơi kết thúc");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&cGIƯỜNG BỊ PHÁ HỦY!");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&fTất cả giường đã bị phá hủy!");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&lTất cả giường đã bị phá hủy!");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&cSinh tử...");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "Hãy coi chừng");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&cSINH TỬ: &6&bRồng của {TeamDragons} {TeamColor}{TeamName}!");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "{prefix}&6+{xp} BedWars Exp (Thời gian chơi).");
        yml.addDefault(Messages.XP_REWARD_WIN, "{prefix}&6+{xp} BedWars Exp (Chiến thắng).");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "{prefix}&6+{xp} BedWars Exp (Hỗ trợ đội).");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "{prefix}&6+{xp} BedWars Exp (Phá giường).");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "{prefix}&6+{xp} BedWars Exp (Giết đối thủ).");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "{prefix}&6+{xp} BedWars Exp (Kết liễu).");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "{prefix}&6+{money} MG (Thời gian chơi).");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "{prefix}&6+{money} MG (Chiến thắng).");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "{prefix}&6+{money} MG (Hỗ trợ đội).");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "{prefix}&6+{money} MG (Phá giường).");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "{prefix}&6+{money} MG (Kết liễu).");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "{prefix}&6+{money} MG (Giết đối thủ)..");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&eThống kê");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fNhấn chuột phải để xem thống kê!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&eChọn phòng chơi");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&fNhấn chuột phải để chọn đấu trường!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&eQuay về Sảnh");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fNhấn chuột phải để rời khỏi BedWars!"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&eThống kê");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&fNhấn chuột phải để xem thống kê của bạn!"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&eQuay về Sảnh");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fNhấn chuột phải để rời khỏi phòng!"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&eDịch Chuyển");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&eQuay về Sảnh");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&fNhấn chuột phải để rời khỏi phòng!"));

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8{player} Stats");
        addDefaultStatsMsg(yml, "wins", "&6Thắng", "&f{wins}");
        addDefaultStatsMsg(yml, "losses", "&6Thua", "&f{losses}");
        addDefaultStatsMsg(yml, "kills", "&6Hạ gục", "&f{kills}");
        addDefaultStatsMsg(yml, "deaths", "&6Hẻo", "&f{deaths}");
        addDefaultStatsMsg(yml, "final-kills", "&6Kết liễu", "&f{finalKills}");
        addDefaultStatsMsg(yml, "final-deaths", "&6Hẻo hoàn toàn", "&f{finalDeaths}");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6Phá giường", "&f{bedsDestroyed}");
        addDefaultStatsMsg(yml, "first-play", "&6Trận đầu tiên", "&f{firstPlay}");
        addDefaultStatsMsg(yml, "last-play", "&6Trận gần nhất", "&f{lastPlay}");
        addDefaultStatsMsg(yml, "games-played", "&6Tổng số trận", "&f{gamesPlayed}");

        // Start of Sidebar
        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList(
                "&e&lBED WARS",
                "&fCấp độ của bạn: {level}",
                "",
                "&fTiến độ: &a{currentXp}&7/&b{requiredXp}",
                "{progress}",
                "",
                "&7{player}",
                "",
                "&fXu: &a{money}",
                "",
                "&fTổng số trận thắng: &a{wins}",
                "&fTổng số mạng hạ gục: &a{kills}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&fBản đồ: &a{map}",
                "&fNgười chơi: &a{on}/{max}",
                "",
                "&fChế độ: &a{group}",
                "",
                "Đang chờ,&fĐang chờ.,&fĐang chờ..,&fĐang chờ...",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING_SPEC, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Khán giả",
                "",
                "&fBản đồ: &a{map}",
                "&fNgười chơi: &a{on}/{max}",
                "",
                "&fChế độ: &a{group}",
                "",
                "Đang chờ,&fĐang chờ.,&fĐang chờ..,&fĐang chờ...",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&fBản đồ: &a{map}",
                "&fNgười chơi: &a{on}/{max}",
                "",
                "&fChế độ: &a{group}",
                "",
                "&fBắt đầu sau &a{time}s",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING_SPEC, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Khán giả",
                "",
                "&fBản đồ: &a{map}",
                "&fNgười chơi: &a{on}/{max}",
                "",
                "&fChế độ: &a{group}",
                "",
                "&fBắt đầu sau &a{time}s",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_RESTARTING_SPEC, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&6Đội chiến thắng: {winnerTeamColor}{winnerTeamName} &6⭐",
                "",
                "&7&lTop Hạ Gục:",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_RESTARTING_WIN1, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&6Đội chiến thắng: {winnerTeamColor}{winnerTeamName} &6⭐",
                "",
                "&7&lTop Hạ Gục:",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_RESTARTING_WIN2, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&6Đội chiến thắng: {winnerTeamColor}{winnerTeamName} &6⭐",
                "",
                "&7&lTop Hạ Gục:",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_RESTARTING_LOSER, Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&6Đội chiến thắng: {winnerTeamColor}{winnerTeamName} &6⭐",
                "",
                "&7&lTop Hạ Gục:",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "&f{topTeamColor}{topPlayerDisplayName}&7 - &l{topValue}",
                "",
                "&e{serverIp}")
        );


        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING.replaceFirst("Default", "Doubles"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC.replaceFirst("Default", "Doubles"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED.replaceFirst("Default", "Doubles"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING.replaceFirst("Default", "3v3v3v3"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fHạ: &a{kills}",
                "&fKết liễu: &a{finalKills}",
                "&fGiường đã phá: &a{beds}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC.replaceFirst("Default", "3v3v3v3"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED.replaceFirst("Default", "3v3v3v3"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fHạ: &a{kills}",
                "&fKết liễu: &a{finalKills}",
                "&fGiường đã phá: &a{beds}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING.replaceFirst("Default", "4v4v4v4"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fHạ: &a{kills}",
                "&fKết liễu: &a{finalKills}",
                "&fGiường đã phá: &a{beds}",
                "",
                "&e{serverIp}")
        );

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC.replaceFirst("Default", "4v4v4v4"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "",
                "&e{serverIp}")
        );
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING_SPEC_ELIMINATED.replaceFirst("Default", "4v4v4v4"), Arrays.asList(
                "&e&lBED WARS",
                "&7{date} &8mini%minevn_room_id%",
                "&o&7Đang theo dõi {spectatorTarget}",
                "&f{nextEvent}: &6{time}",
                "",
                "{team}",
                "{team}",
                "{team}",
                "{team}",
                "",
                "&fHạ: &a{kills}",
                "&fKết liễu: &a{finalKills}",
                "&fGiường đã phá: &a{beds}",
                "",
                "&e{serverIp}")
        );

        // End of Sidebar

        // start of TAB
        // main lobby tab format
        yml.addDefault(Messages.FORMATTING_SB_TAB_LOBBY_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_LOBBY_FOOTER, List.of(
                "",
                "&fCó {on} người chơi trong sảnh này",
                "Được phát triển bởi {poweredBy},&a{serverIp}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_LOBBY_PREFIX, List.of("{vPrefix}"));
        yml.addDefault(Messages.FORMATTING_SB_TAB_LOBBY_SUFFIX, List.of(""));
        // player waiting lobby
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_FOOTER, List.of(
                "",
                "Đang chờ thêm người chơi,Đang chờ thêm người chơi.,Đang chờ thêm người chơi.., Đang chờ thêm người chơi...",
                "&f{on}&a/&f{max}",
                "",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));

        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_PREFIX, List.of("{teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_SUFFIX, List.of(""));
        // spectator waiting lobby
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_HEADER_SPEC, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_FOOTER_SPEC, List.of(
                "",
                "&7&oBạn đang quan sát",
                "Đang chờ thêm người chơi,Đang chờ thêm người chơi.,Đang chờ thêm người chơi.., Đang chờ thêm người chơi...",
                "&f{on}&a/&f{max}",
                "",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_PREFIX_SPEC, List.of("{teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_WAITING_SUFFIX_SPEC, List.of(""));
        // player starting lobby
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_FOOTER, List.of(
                "",
                "&fBắt đầu sau &a{time} &fgiây,&fBắt đầu sau &a{time} &fgiây.,&fBắt đầu sau &a{time} &fgiây..,&fBắt đầu sau &a{time} &fgiây...",
                "&f{on}&a/&f{max}",
                "",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_PREFIX, List.of("{teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_SUFFIX, List.of(""));
        // spectator starting lobby
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_HEADER_SPEC, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                "",
                "&7&oBạn đang quan sát"
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_FOOTER_SPEC, List.of(
                "",
                "&fBắt đầu sau &a{time} &fgiây,&fBắt đầu sau &a{time} &fgiây.,&fBắt đầu sau &a{time} &fgiây..,&fBắt đầu sau &a{time} &fgiây...",
                "&f{on}&a/&f{max}",
                "",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_PREFIX_SPEC, List.of("{vPrefix} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_STARTING_SUFFIX_SPEC, List.of(""));
// player playing
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                "",
                "{nextEvent} trong {time}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_FOOTER, List.of(
                "",
                "&fBạn đang chơi cho đội {teamColor}{teamName}",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_PREFIX, List.of("{teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_SUFFIX, List.of(""));
        // player eliminated - playing state
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_ELM_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                "",
                "{nextEvent} trong {time}",
                "",
                "&7&oBạn đã bị loại"
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_ELM_FOOTER, List.of(
                "",
                "&fBạn đã chơi cho đội {teamColor}{teamName}",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_ELM_PREFIX, List.of("&f&oKhán giả "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_ELM_SUFFIX, List.of(""));
        //spectator - playing state
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                "",
                "{nextEvent} trong {time}",
                "",
                "&7&oBạn đang quan sát"
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_FOOTER, List.of(
                "",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_PREFIX, List.of("&f&oKhán giả "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_PLAYING_SPEC_SUFFIX, List.of(""));
        // winner alive - restarting state
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_HEADER, List.of(
                "                                                                                                        ",
                "&6⭐ {winnerTeamColor}&lĐội của bạn đã thắng! &6⭐",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_FOOTER, List.of(
                "",
                "&6&lBạn đã thắng trong đội {teamColor}&l{teamName}&6&l!,&6&lBạn đã thắng trong đội {teamColor}&l{teamName}&6&l!,&f&lBạn đã thắng trong đội {teamColor}&l{teamName}&f&l!",
                "&7Thống kê từ trận này",
                "&eHạ gục: &f{kills} &8| &eHạ gục cuối cùng: &f{finalKills} &8| &ePhá giường: &f{beds} &8| &eChết: &f{deaths},&eHạ gục: &7{kills} &8| &eHạ gục cuối cùng: &7{finalKills} &8| &ePhá giường: &7{beds} &8| &eChết: &7{deaths}",
                "",
                "&fCảm ơn đã chơi, {player}!",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_PREFIX, List.of("&6&l⭐ {teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN1_SUFFIX, List.of(""));
        // winner dead - restarting state
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_HEADER, List.of(
                "                                                                                                        ",
                "&6⭐ {winnerTeamColor}&lĐội {winnerTeamName} đã thắng! &6⭐",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_FOOTER, List.of(
                "",
                "&6&lBạn đã thắng trong đội {teamColor}&l{teamName}&6&l!,&6&lBạn đã thắng trong đội {teamColor}&l{teamName}&6&l!,&f&lBạn đã thắng trong đội {teamColor}&l{teamName}&f&l!",
                "&7Thống kê từ trận này",
                "&eHạ gục: &f{kills} &8| &eHạ gục cuối cùng: &f{finalKills} &8| &ePhá giường: &f{beds} &8| &eChết: &f{deaths},&eHạ gục: &7{kills} &8| &eHạ gục cuối cùng: &7{finalKills} &8| &ePhá giường: &7{beds} &8| &eChết: &7{deaths}",
                "",
                "&fCảm ơn đã chơi, {player}!",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_PREFIX, List.of("&6&l⭐ {teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_WIN2_SUFFIX, List.of(""));
        // loser - restarting state
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_HEADER, List.of(
                "                                                                                                        ",
                "&6⭐ {winnerTeamColor}&lĐội {winnerTeamName} đã thắng! &6⭐",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_FOOTER, List.of(
                "",
                "&fBạn đã thua trong đội {teamColor}{teamName}",
                "&7Thống kê từ trận này",
                "&eHạ gục: &f{kills} &8| &eHạ gục cuối cùng: &f{finalKills} &8| &ePhá giường: &f{beds} &8| &eChết: &f{deaths},&eHạ gục: &7{kills} &8| &eHạ gục cuối cùng: &7{finalKills} &8| &ePhá giường: &7{beds} &8| &eChết: &7{deaths}",
                "",
                "&fCảm ơn đã chơi, {player}!",
                "&a{serverIp}",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_PREFIX, List.of("{teamLetter} "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_ELM_SUFFIX, List.of(""));
        // spectator - restarting state
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_HEADER, List.of(
                "                                                                                                        ",
                "&a{serverIp}",
                "&6⭐ {winnerTeamColor}&lĐội {winnerTeamName} đã thắng! &6⭐",
                "&7{date}",
                "&7Bản đồ: &f{map} &7Chế độ: &f{group}",
                "",
                "&fCảm ơn đã chơi, {player}!",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_FOOTER, List.of(
                "",
                "&fĐược phát triển bởi {poweredBy}",
                ""
        ));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_PREFIX, List.of("&f&oKhán giả "));
        yml.addDefault(Messages.FORMATTING_SB_TAB_RESTARTING_SPEC_SUFFIX, List.of(""));
        // end of tab

        //
        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8Mua nhanh");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8Thêm vào mua nhanh");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "{prefix}&cCần thêm {amount} {currency}!");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "{prefix}&aBạn đã mua &6{item}");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "{prefix}&cBạn đã mua cái này rồi!");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "Silver Fish của {TeamColor}&l{TeamName} &r{TeamColor}");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "{TeamColor}{despawn}s &8[ {TeamColor}{health}&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ Loại hàng");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ Vật phẩm"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&bMua nhanh");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&cÔ trống!");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7Đây là thanh mua nhanh!", "&bShift-Click &7vật phẩm", "&7trong shop để thêm vào đây."));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&e▸ [Nhấn] &7để mua!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&cBạn không đủ&c &f{currency}!&f");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&aĐÃ TỐI ĐA!");
        yml.addDefault(Messages.SHOP_LORE_STATUS_ARMOR, "&aĐÃ TRANG BỊ!");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&b[Shift-Click] &7để thêm vào mua nhanh");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&b[Shift-Click] &7để xóa mua nhanh");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8Blocks", "&aBlocks", Collections.singletonList("&eNhấn vào để xem!"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Len", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Xây cầu qua các đảo", "&7Màu len theo màu đội", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Đất sét cứng", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Khối cơ bản", "&7để bảo vệ giường", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Kính chống nổ", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Chống được TNT và Fireball", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Đá TheEnd", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Khối cơ bản", "&7để bảo vệ giường", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Thang", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Ladder-clutch cực ngầu", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Hắc diện thạch", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Khối cứng nhất", "&7để bảo vệ giường", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "{color}Gỗ", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Khối cơ bản", "&7để bảo vệ giường", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8Cận chiến", "&aCận chiến", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Kiếm đá", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Kiếm sắt", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Kiếm kim cương", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "{color}Gậy (Đánh bật lùi I)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8Phòng thủ", "&aGiáp", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Giáp xích", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Giáp xích (quần và giày)", "&7Sẽ luôn xuất hiện khi bạn hồi sinh.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Giáp sắt", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Quần và giày giáp sắt", "&7Sẽ luôn xuất hiện khi bạn hồi sinh.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "{color}Giáp kim cương", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Quần và giày giáp kim cương", "&7Sẽ luôn giúp bạn chiến thắng.", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8Công cụ", "&aCông cụ", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Kéo tỉa", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Phá len nhanh hơn.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Cúp {tier}", Arrays.asList("&fGiá: &d{cost} {currency}", "&7Bậc: &e{tier}", "", "&7Vật phẩm có thể nâng cấp.", "&7(Hạ 1 bậc khi bạn hẻo)", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "{color}Rìu {tier}", Arrays.asList("&fGiá: &d{cost} {currency}", "&7Bậc: &e{tier}", "", "&7Vật phẩm có thể nâng cấp.", "&7(Hạ 1 bậc khi bạn hẻo)", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8Tầm xa", "&aTầm xa", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Mũi tên", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Cung", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Cung (Sức mạnh I)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "{color}Cung (Sức mạnh I, Bật lùi I)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8Thuốc", "&aThuốc", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Thuốc Tốc độ II (45 giây)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Thuốc Nhảy cao V (45 giây)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "{color}Thuốc Tàng hình (30 giây)", Arrays.asList("&fGiá: &d{cost} {currency}", "", "{quick_buy}", "{buy_status}"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8Tiện ích", "&aTiện ích", Collections.singletonList("&eNhấn để vào xem!"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Táo dai", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Hồi máu cho bản thân.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Silver Fish", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Tạo ra Silver Fish", "&7bảo vệ căn cứ.", "&7Tồn tại trong 15 giây.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Hậu vệ người sắt", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Tạo ra người sắt", "&7bảo vệ căn cứ.", "&7Tồn tại trong 4 phút.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Cầu lửa", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Dễ dàng triệt hạ kẻ thù", "&7đang đi trên cầu.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}TNT", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Được kích hoạt sẵn", "&7sẵn sàng phát nổ!", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Ngọc Ender", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Cách nhanh nhất để đột nhập", "&7vào căn cứ kẻ thù.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Xô nước", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Dùng để làm chậm kẻ thù", "&7hoặc chống lại TNT.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Cầu trứng", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Tạo ra cây cầu khi ném.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Sữa ma thuật", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Không kích hoạt bẫy trong 60 giây.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Bọt biển", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Hấp thụ nước nhanh chóng.", "", "{quick_buy}", "{buy_status}"));
        addContentMessages(yml, "tower", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "{color}Lâu đài tình ái", Arrays.asList("&fGiá: &d{cost} {currency}", "", "&7Tạo ra lâu đài tình ái", "&7(Dam Vinh Hung approved)", "", "{quick_buy}", "{buy_status}"));

        //
        yml.addDefault(Messages.MEANING_NO_TRAP, "Không có!");
        yml.addDefault(Messages.FORMAT_SPECTATOR_TARGET, "{targetTeamColor}{targetDisplayName}");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7Cost: {currencyColor}{cost} {currency}");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "{color}Nhấn để nâng cấp!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "{color}Cần thêm &f{currency}");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&cĐANG BỊ KHÓA");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "{color}ĐÃ MỞ KHÓA TOÀN BỘ");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a{player} đã mua &6{upgradeName}");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-1"), "{color}Lò luyện Sắt");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "forge"),
                Arrays.asList("&7Nâng cấp nguồn tài nguyên cho", "&7đảo của bạn.", "", "{tier_1_color}Bậc 1: +50% Tài nguyên, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Bậc 2: +100% Tài nguyên, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Bậc 3: Tạo ra Lục bảo, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Bậc 4: +200% Tài nguyên, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-2"), "{color}Lò luyện Vàng");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-3"), "{color}Lò luyện Ngọc lục bảo");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "forge").replace("{tier}", "tier-4"), "{color}Lò luyện nóng chảy");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&eMua một cái bẫy");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("7Các bẫy đã mua sẽ được", "&7đặt bên dưới.", "", "&eNhấn vào để xem!"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "swords").replace("{tier}", "tier-1"), "{color}Kiếm sắc bén");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "swords"),
                Arrays.asList("&7Đội của bạn sẽ nhận được", "&aSắc bén I &7lên toàn bộ vũ khí!", "", "{tier_1_color}Giá: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-1"), "{color}Giáp bảo vệ I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "armor"),
                Arrays.asList("&7Đội của bạn sẽ được nhận hiệu ứng", "&aBảo vệ &7lên toàn bộ giáp đang mặc!", "", "{tier_1_color}Bậc 1: Bảo vệ I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Bậc 2: Bảo vệ II, &b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}Bậc 3: Bảo vệ III, &b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}Bậc 4: Bảo vệ IV, &b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-2"), "{color}Giáp bảo vệ II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-3"), "{color}Giáp bảo vệ III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "armor").replace("{tier}", "tier-4"), "{color}Giáp bảo vệ IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-1"), "{color}Thợ mỏ I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "miner"),
                Arrays.asList("&7Tất cả người chơi trong đội", "&7nhận &aĐào nhanh &7vĩnh viễn.", "", "{tier_1_color}Bậc 1: Đào nhanh I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}Bậc 2: Đào nhanh II, &b{tier_2_cost} {tier_2_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "miner").replace("{tier}", "tier-2"), "{color}Thợ đào mỏ II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "heal-pool").replace("{tier}", "tier-1"), "{color}Hồi phục");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "heal-pool"),
                Arrays.asList("&7Tạo một vùng &aHồi phục", "&7xung quanh căn cứ!", "", "{tier_1_color}Cost: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("{name}", "dragon").replace("{tier}", "tier-1"), "{color}Buff Rồng");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("{name}", "dragon"),
                Arrays.asList("&7Đội của bạn sẽ nhận 2", "&7con rồng thay vì 1", "", "{tier_1_color}Cost: &b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7Có thể mua được");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7Bẫy"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "{color}Bẫy #1: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Arrays.asList("&7Kẻ thù đầu tiên đi vào", "&7căn cứ của bạn sẽ kích hoạt", "&7cái bẫy này!!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7Mua một cái bẫy sẽ đặt nó", "&7vào hàng chờ ở đây. Chi phí", "&7sẽ thay đổi dựa vào số lượng", "&7bẫy đã đặt.", "", "&7Bẫy tiếp theo: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "{color}Bẫy #2: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Arrays.asList("&7Kẻ thù thứ hai đi vào", "&7căn cứ của bạn sẽ kích hoạt", "&7cái bẫy này!!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7Mua một cái bẫy sẽ đặt nó", "&7vào hàng chờ ở đây. Chi phí", "&7sẽ thay đổi dựa vào số lượng", "&7bẫy đã đặt.", "", "&7Bẫy tiếp theo: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "{color}Bẫy #3: {name}");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Arrays.asList("&7Kẻ thù thứ ba đi vào", "&7căn cứ của bạn sẽ kích hoạt", "&7cái bẫy này!!"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7Mua một cái bẫy sẽ đặt nó", "&7vào hàng chờ ở đây. Chi phí", "&7sẽ thay đổi dựa vào số lượng", "&7bẫy đã đặt.", "", "&7Bẫy tiếp theo: &b{cost} {currency}"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "{color}It's a trap!");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7Kẻ địch sẽ nhận &aMù quáng", "&7và &aChậm rãi &7trong 5 giây.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "{color}Phản công");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7Cho đồng minh gần căn cứ hiệu ứng", "&7Tốc độ I &7trong 15 giây", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "{color}Báo động");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7Làm lộ người chơi tàng hình", "&7cùng với tên và đội của họ.", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "{color}Mỏ Mỏi");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7Kẻ địch sẽ nhận hiệu ứng.", "Đào chậm &7trong 10 giây.", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&aTrở lại");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7Nâng cấp & Bẫy"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8Bẫy");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&cBẫy đã đầy!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l{trap} đã được kích hoạt!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&cBẪY KÍCH HOẠT!");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&fBẫy {trap} đã được kích hoạt!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&cBẫy &ekích hoạt bởi {color}{player} &etừ đội {color}{team}!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&lBÁO ĐỘNG!!!");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "&f{trap} &eđã kích hoạt bởi đội {color}{team}");
        save();
        setPrefix(m(Messages.PREFIX));
        setPrefixStatic(m(Messages.PREFIX));
    }
}
