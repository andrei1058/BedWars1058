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

public class SimplifiedChinese extends Language {

    public SimplifiedChinese() {
        super(BedWars.plugin, "zh_cn");

        YamlConfiguration yml = getYml();
        yml.options().copyDefaults(true);
        yml.addDefault(Messages.PREFIX, "");
        yml.addDefault("name", "简体中文");

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

        yml.addDefault(Messages.COMMAND_MAIN, Arrays.asList("", "&2▪ &7/" + mainCmd + " stats", "&2▪ &7/" + mainCmd + " join &o<游戏/模式>", "&2▪ &7/" + mainCmd + " leave", "&2▪ &7/" + mainCmd + " lang", "&2▪ &7/" + mainCmd + " gui", "&2▪ &7/" + mainCmd + " start &3（赞助者）"));
        yml.addDefault(Messages.COMMAND_LANG_LIST_HEADER, "%bw_lang_prefix% &2可用的语言：");
        yml.addDefault(Messages.COMMAND_LANG_LIST_FORMAT, "&a▪  &7%bw_lang_iso% - &f%bw_name%");
        yml.addDefault(Messages.COMMAND_LANG_USAGE, "%bw_lang_prefix%&用法：/lang &f&o<iso>");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_NOT_EXIST, "%bw_lang_prefix%&c该语言不存在！");
        yml.addDefault(Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY, "%bw_lang_prefix%&a语言已设置！");
        yml.addDefault(Messages.COMMAND_LANG_USAGE_DENIED, "%bw_lang_prefix%&c你不能在游戏进行时修改语言。");
        yml.addDefault(Messages.COMMAND_JOIN_USAGE, "&a▪ &7用法：/" + BedWars.mainCmd + " join &o<游戏/模式>");
        yml.addDefault(Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND, "%bw_lang_prefix%&c游戏%bw_name%不存在！");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL, "%bw_lang_prefix%&c游戏已满！\n&a请考虑赞助以支持我们！ &7&o(点击查看)");
        yml.addDefault(Messages.COMMAND_JOIN_NO_EMPTY_FOUND, "%bw_lang_prefix%&c现在没有可用的游戏:(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_IS_FULL_OF_VIPS, "%bw_lang_prefix%&c很抱歉，虽然我们已知道你已赞助，但该游戏已满。\n&c此游戏中全是赞助者或管理员。");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_PARTY_TOO_BIG, "%bw_lang_prefix%&c你的队伍人数太多了，不能作为一个队伍加入该游戏:(");
        yml.addDefault(Messages.COMMAND_JOIN_DENIED_NOT_PARTY_LEADER, "%bw_lang_prefix%&c只有队长才能选择游戏。");
        yml.addDefault(Messages.COMMAND_JOIN_PLAYER_JOIN_MSG, "%bw_lang_prefix%&7%bw_player%&e加入了游戏(&b%bw_on%&e/&b%bw_max%&e)！");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_MSG, "%bw_lang_prefix%&6你正在观战&9%bw_arena%&6。\n%bw_lang_prefix%&e输入 &c/leave &e离开。");
        yml.addDefault(Messages.COMMAND_JOIN_SPECTATOR_DENIED_MSG, "&c该游戏不允许旁观！");
        yml.addDefault(Messages.COMMAND_TP_PLAYER_NOT_FOUND, "%bw_lang_prefix%&c无法找到这位玩家！");
        yml.addDefault(Messages.COMMAND_TP_NOT_IN_ARENA, "%bw_lang_prefix%&c该玩家不在任何一场起床战争游戏中！");
        yml.addDefault(Messages.COMMAND_TP_NOT_STARTED, "%bw_lang_prefix%&c该玩家所在的游戏还没开始！");
        yml.addDefault(Messages.COMMAND_TP_USAGE, "%bw_lang_prefix%&c用法：/bw tp <玩家名>");
        yml.addDefault(Messages.REJOIN_NO_ARENA, "%bw_lang_prefix%&c没有可以重新加入的游戏！");
        yml.addDefault(Messages.REJOIN_DENIED, "%bw_lang_prefix%&c由于你所属队伍的床被破坏或游戏已经结束，你不能重新加入。");
        yml.addDefault(Messages.REJOIN_ALLOWED, "%bw_lang_prefix%&e正在重新加入&a%bw_arena%&e！");
        yml.addDefault(Messages.COMMAND_REJOIN_PLAYER_RECONNECTED, "%bw_lang_prefix%&7%bw_player%&e重新连接。");
        yml.addDefault(Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA, "%bw_lang_prefix%&c你不在一场起床战争游戏中！");
        yml.addDefault(Messages.COMMAND_LEAVE_MSG, "%bw_lang_prefix%&7%bw_player%&e离开了！");
        yml.addDefault(Messages.COMMAND_NOT_ALLOWED_IN_GAME, "%bw_lang_prefix%&c你在游戏中不可以这么做。");
        yml.addDefault(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS, "%bw_lang_prefix%&c指令无效或你没有权限！");
        yml.addDefault(Messages.COMMAND_PARTY_HELP, Arrays.asList("&6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&a队伍指令：",
                "&e/party help &7- &b查看该提示",
                "&e/party invite <玩家> &7- &b邀请一位玩家到你的队伍",
                "&e/party leave &7- &b离开当前队伍",
                "&e/party info &7- &bShow party members and owner",
                "&e/party promote <player> &7- &bTransfer party ownership",
                "&e/party remove <玩家> &7- &b将玩家移出队伍",
                "&e/party accept <玩家> &7- &b接受队伍邀请",
                "&e/party disband &7- &bD解散队伍")
        );
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_USAGE, "%bw_lang_prefix%&e用法：&7/party invite <玩家>");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player%&e不在线！");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT, "%bw_lang_prefix%&e已向&7%bw_player%&e发送邀请&6。");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG, "%bw_lang_prefix%&b%bw_player%&e邀请你加入队伍！ &o&7(点击接受)");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF, "%bw_lang_prefix%&c你不可以邀请你自己！");
        yml.addDefault(Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE, "%bw_lang_prefix%&7%bw_player%&e不在线！");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE, "%bw_lang_prefix%&c没有可以接受的队伍邀请！");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY, "%bw_lang_prefix%&e你已经在队伍中了！");
        yml.addDefault(Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS, "%bw_lang_prefix%&c只有队长才可以这么做！");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_USAGE, "%bw_lang_prefix%&e用法：&7/party accept <玩家>");
        yml.addDefault(Messages.COMMAND_PARTY_ACCEPT_SUCCESS, "%bw_lang_prefix%&7%bw_player%&e加入了队伍！");
        yml.addDefault(Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY, "%bw_lang_prefix%&c你不在队伍中！");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND, "%bw_lang_prefix%&c你不可以离开由你带领的队伍！\n&e使用：&b/party disband &e来解散队伍。");
        yml.addDefault(Messages.COMMAND_PARTY_LEAVE_SUCCESS, "%bw_lang_prefix%&7%bw_player%&e离开了队伍！");
        yml.addDefault(Messages.COMMAND_PARTY_DISBAND_SUCCESS, "%bw_lang_prefix%&e队伍已解散！");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_USAGE, "%bw_lang_prefix%&7用法：&e/party remove <玩家>");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_SUCCESS, "%bw_lang_prefix%&7%bw_player%&e被移出了队伍。");
        yml.addDefault(Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER, "%bw_lang_prefix%&7%bw_player%&e不在你的队伍中！");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_SUCCESS, "%bw_lang_prefix%&e你成功将 %bw_player% 提升为群主");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_OWNER, "%bw_lang_prefix%&e你已被提升为群主");
        yml.addDefault(Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER, "%bw_lang_prefix%&7 &e%bw_player% 已被提升为群主");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_OWNER, "\n%bw_lang_prefix%&e群主为: &7%bw_party_owner%");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYERS,"%bw_lang_prefix%&e群成员有：");
        yml.addDefault(Messages.COMMAND_PARTY_INFO_PLAYER, "&7%bw_player%");
        yml.addDefault(Messages.COMMAND_FORCESTART_NOT_IN_GAME, "&c▪ &7你不在游戏中！");
        yml.addDefault(Messages.COMMAND_FORCESTART_SUCCESS, "&c▪ &7游戏开始倒计时缩短！");
        yml.addDefault(Messages.COMMAND_FORCESTART_NO_PERM, "%bw_lang_prefix%&7你不可以强制开始游戏！\n&7请考虑赞助以得到对应权限！");
        yml.addDefault(Messages.COMMAND_COOLDOWN, "&c你不能这么做！ 请等待 %bw_seconds% 秒！");
        yml.addDefault(Messages.ARENA_JOIN_VIP_KICK, "%bw_lang_prefix%&c抱歉，由于有一位赞助者加入该游戏，因此你被移出了该游戏。\n&a请考虑赞助以支持我们！ &7&o(点击查看)");
        yml.addDefault(Messages.ARENA_START_COUNTDOWN_STOPPED_INSUFF_PLAYERS_CHAT, "%bw_lang_prefix%&c玩家不足！ 倒计时取消！");
        yml.addDefault(Messages.ARENA_RESTART_PLAYER_KICK, "%bw_lang_prefix%&e当前游戏正在重启。");
        yml.addDefault(Messages.ARENA_STATUS_PLAYING_NAME, "&c游戏中");
        yml.addDefault(Messages.ARENA_STATUS_RESTARTING_NAME, "&4重启中");
        yml.addDefault(Messages.ARENA_STATUS_WAITING_NAME, "&3等待中 &c%bw_full%");
        yml.addDefault(Messages.ARENA_STATUS_STARTING_NAME, "&6即将开始 &c%bw_full%");
        yml.addDefault(Messages.ARENA_GUI_INV_NAME, "&8点击加入");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_NAME, "&a&l%bw_name%");
        yml.addDefault(Messages.ARENA_GUI_ARENA_CONTENT_LORE, Arrays.asList("", "&7状态：%bw_arena_status%", "&7玩家数：&f%bw_on%&7/&f%bw_max%", "&7模式：&a%bw_group%", "", "&a点击进入", "&e右击观赛"));
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_NAME, "&r%bw_server_ip%");
        yml.addDefault(Messages.ARENA_GUI_SKIPPED_ITEM_LORE, Collections.emptyList());
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CHAT, "%bw_lang_prefix%&e游戏将在 &6%bw_time%&e 秒后开始！");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE, "&a%bw_seconds%");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-5", "&e❺");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-4", "&e❹");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-3", "&c❸");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-2", "&c❷");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_SUB_TITLE + "-1", "&c❶");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_TITLE, " ");
        yml.addDefault(Messages.ARENA_STATUS_START_COUNTDOWN_CANCELLED_SUB_TITLE, "&c等待更多玩家……");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TITLE, "&a游戏开始");
        yml.addDefault(Messages.ARENA_STATUS_START_PLAYER_TUTORIAL, Arrays.asList(
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬",
                "&f                                  &l起床战争", "",
                "&e&l                            保护你的床并破坏敌人的床，",
                "&e&l                        从资源点收集铁锭、金锭、绿宝石和钻石，",
                "&e&l                        来购买强力装备和进行升级来使自己变强！",
                "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        yml.addDefault(Messages.ARENA_JOIN_DENIED_SELECTOR, "%bw_lang_prefix%&c抱歉，你现在不能加入该游戏。右键来观赛！");
        yml.addDefault(Messages.ARENA_SPECTATE_DENIED_SELECTOR, "%bw_lang_prefix%&c抱歉，你现在不能观赛。右键来加入游戏！");
        yml.addDefault(Messages.ARENA_JOIN_DENIED_NO_PROXY, "&c抱歉，你必须通过 BedWarsProxy 来加入游戏。 \n&e如果你想设置游戏，你可以给予你自己 bw.setup 权限来直接进入服务器！");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_NAME, "&8传送");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_NAME, "%bw_v_prefix%%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_TELEPORTER_GUI_HEAD_LORE, Arrays.asList("&7生命值：&f%bw_player_health%%", "&7饱食度：&f%bw_player_food%", "", "&7左键传送"));
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_NAME, "&c&l回到大厅");
        yml.addDefault(Messages.ARENA_SPECTATOR_LEAVE_ITEM_LORE, Collections.singletonList("&7右键离开起床战争大厅！"));
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_TITLE, "&a正在旁观&7%bw_player%");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_ENTER_SUBTITLE, "&c潜行以退出！");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_TITLE, "&e退出旁观者模式");
        yml.addDefault(Messages.ARENA_SPECTATOR_FIRST_PERSON_LEAVE_SUBTITLE, "");
        yml.addDefault(Messages.ARENA_LEAVE_PARTY_DISBANDED, "%bw_lang_prefix%&c由于队长离开了，队伍解散！");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIER, "&e等级&c%bw_tier%");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_DIAMOND, "&b&l钻石");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TYPE_EMERALD, "&a&l绿宝石");
        yml.addDefault(Messages.GENERATOR_HOLOGRAM_TIMER, "&c%bw_seconds%&e 秒后生成");
        yml.addDefault(Messages.GENERATOR_UPGRADE_CHAT_ANNOUNCEMENT, "%bw_lang_prefix%%bw_generator_type%资源点&e升级到&c%bw_tier%级。");
        yml.addDefault(Messages.FORMATTING_CHAT_LOBBY, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%：%bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_WAITING, "%bw_level%%bw_v_prefix%&7%bw_player%%bw_v_suffix%：%bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SHOUT, "%bw_level%%bw_v_prefix%&6[公屏] %bw_team% &7%bw_player%&f%bw_v_suffix%：%bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_TEAM, "%bw_level%%bw_v_prefix%&f%bw_team%&7 %bw_player%%bw_v_suffix% %bw_message%");
        yml.addDefault(Messages.FORMATTING_CHAT_SPECTATOR, "%bw_level%%bw_v_prefix%&7[旁观者] %bw_player%%bw_v_suffix%：%bw_message%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_HEALTH, "%health% &c❤");
        yml.addDefault(Messages.FORMATTING_SPECTATOR_TEAM, "旁观者");
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

        yml.addDefault(Messages.FORMATTING_SCOREBOARD_DATE, "yy/MM/dd");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_GENERIC, "%bw_team_color%%bw_team_letter%&f %bw_team_name%：%bw_team_status%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ELIMINATED, "&c&l✘");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_BED_DESTROYED, "&a%bw_players_remaining%");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_TEAM_ALIVE, "&a&l✓");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_NEXEVENT_TIMER, "mm:ss");
        yml.addDefault(Messages.FORMATTING_SCOREBOARD_YOUR_TEAM, "&7 你");
        yml.addDefault(Messages.FORMATTING_ACTION_BAR_TRACKING, "&fTracking: %bw_team% &f- Distance: %bw_distance%m");
        yml.addDefault(Messages.FORMATTING_TEAM_WINNER_FORMAT, "      %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_SOLO_WINNER_FORMAT, "                 %bw_team_color%%bw_team_name% &7- %bw_winner_members%");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER1, "I");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER2, "II");
        yml.addDefault(Messages.FORMATTING_GENERATOR_TIER3, "III");
        yml.addDefault(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH, "▮ ");
        yml.addDefault(Messages.FORMATTING_STATS_DATE_FORMAT, "yyyy/MM/dd HH:mm");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_TEAM, "%bw_team_color%[%bw_team_name%]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SHOUT, "&6[公屏]");
        yml.addDefault(Messages.FORMAT_PAPI_PLAYER_TEAM_SPECTATOR, "&7[旁观者]");
        yml.addDefault(Messages.MEANING_FULL, "已满");
        yml.addDefault(Messages.MEANING_SHOUT, "公屏");
        yml.addDefault(Messages.MEANING_NOBODY, "无玩家");
        yml.addDefault(Messages.MEANING_NEVER, "从不");
        yml.addDefault(Messages.MEANING_IRON_SINGULAR, "铁锭");
        yml.addDefault(Messages.MEANING_IRON_PLURAL, "铁锭");
        yml.addDefault(Messages.MEANING_GOLD_SINGULAR, "金锭");
        yml.addDefault(Messages.MEANING_GOLD_PLURAL, "金锭");
        yml.addDefault(Messages.MEANING_EMERALD_SINGULAR, "绿宝石");
        yml.addDefault(Messages.MEANING_EMERALD_PLURAL, "绿宝石");
        yml.addDefault(Messages.MEANING_DIAMOND_SINGULAR, "钻石");
        yml.addDefault(Messages.MEANING_DIAMOND_PLURAL, "钻石");
        yml.addDefault(Messages.MEANING_VAULT_SINGULAR, "$");
        yml.addDefault(Messages.MEANING_VAULT_PLURAL, "$");
        yml.addDefault(Messages.INTERACT_CANNOT_PLACE_BLOCK, "%bw_lang_prefix%&c你不能在这里放置方块！");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_BLOCK, "%bw_lang_prefix%&c你只能破坏由玩家放置的方块！");
        yml.addDefault(Messages.INTERACT_CANNOT_BREAK_OWN_BED, "&c你不能破坏自己的床！");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT, "\n&f&l床被破坏 > %bw_team_color%%bw_team_name%的床&7被%bw_player_color%%bw_player%&7破坏了！\n");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_TITLE_ANNOUNCEMENT, "&c床被破坏！");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_SUBTITLE_ANNOUNCEMENT, "&f你不能再重生！");
        yml.addDefault(Messages.INTERACT_BED_DESTROY_CHAT_ANNOUNCEMENT_TO_VICTIM, "&f&l床被破坏 > &7你的床被%bw_player_color%%bw_player%&7破坏了！\n");
        yml.addDefault(Messages.INTERACT_CHEST_CANT_OPEN_TEAM_ELIMINATED, "&c此队伍还未被团灭，因此你不能打开该团队箱子！");
        yml.addDefault(Messages.INTERACT_INVISIBILITY_REMOVED_DAMGE_TAKEN, "&c你因受到伤害而被迫退出隐身！");
        yml.addDefault(Messages.INTERACT_MAGIC_MILK_REMOVED, "&cYour Magic Milk wore off!");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_REGULAR_KILL, "%bw_player_color%%bw_player%&7掉进了虚空。");
        yml.addDefault(Messages.PLAYER_DIE_VOID_FALL_FINAL_KILL, "%bw_player_color%%bw_player%&7掉进了虚空。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_REGULAR_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7丢进了虚空。");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_IN_VOID_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7丢进了虚空。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR, "%bw_player_color%%bw_player%&7在与%bw_killer_color%%bw_killer_name%&7战斗时断开连接。");
        yml.addDefault(Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL, "%bw_player_color%%bw_player%&7在与%bw_killer_color%%bw_killer_name%&7战斗时断开连接。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_REGULAR_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7推下了悬崖。");
        yml.addDefault(Messages.PLAYER_DIE_KNOCKED_BY_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7推下了悬崖。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_REGULAR_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7炸死了。");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITH_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7炸死了。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_REGULAR, "%bw_player_color%%bw_player%&7爆炸了。");
        yml.addDefault(Messages.PLAYER_DIE_EXPLOSION_WITHOUT_SOURCE_FINAL_KILL, "%bw_player_color%%bw_player%&7爆炸了。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_PVP_REGULAR_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7击杀。");
        yml.addDefault(Messages.PLAYER_DIE_PVP_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7击杀。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_REGULAR, "%bw_player_color%%bw_player%&7死了。");
        yml.addDefault(Messages.PLAYER_DIE_UNKNOWN_REASON_FINAL_KILL, "%bw_player_color%%bw_player%&7死了。 &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_REGULAR, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7射死了！");
        yml.addDefault(Messages.PLAYER_DIE_SHOOT_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_name%&7射死了！ &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_REGULAR, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_team_name%&7的蠹虫杀死了！");
        yml.addDefault(Messages.PLAYER_DIE_DEBUG_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_team_name%&7的蠹虫杀死了！ &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_REGULAR, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_team_name%&7的铁傀儡杀死了！");
        yml.addDefault(Messages.PLAYER_DIE_IRON_GOLEM_FINAL_KILL, "%bw_player_color%%bw_player%&7被%bw_killer_color%%bw_killer_team_name%&7的铁傀儡杀死了！ &b&l最终击杀！");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_DIAMOND, "%bw_lang_prefix%&b+%bw_amount%%bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_EMERALD, "%bw_lang_prefix%&a+%bw_amount%%bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_IRON, "%bw_lang_prefix%&f+%bw_amount%%bw_meaning%");
        yml.addDefault(Messages.PLAYER_DIE_REWARD_GOLD, "%bw_lang_prefix%&6+%bw_amount%%bw_meaning%");
        yml.addDefault(Messages.ARENA_MAX_BUILD_LIMIT_REACHED, "&cMax build height limit reached!");
        yml.addDefault(Messages.ARENA_MIN_BUILD_LIMIT_REACHED, "&cMin build height limit reached!");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_TITLE, "&c你死了！");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_SUBTITLE, "&e你将在 &a%bw_time% &e秒后重生！");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWN_CHAT, "%bw_lang_prefix%&e你将在 &a%bw_time% &e秒后重生！");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TITLE, "&a已重生！");
        yml.addDefault(Messages.PLAYER_DIE_ELIMINATED_CHAT, "%bw_lang_prefix%&c你已被淘汰！");
        yml.addDefault(Messages.PLAYER_DIE_RESPAWNED_TEXT, "%bw_lang_prefix%&eYou have respawned!");
        yml.addDefault(Messages.PLAYER_HIT_BOW, "%bw_lang_prefix%%bw_player%&7还有 &e%bw_amount% &c生命值！");
        yml.addDefault(Messages.GAME_END_GAME_OVER_PLAYER_TITLE, "&c&l游戏结束！");
        yml.addDefault(Messages.GAME_END_VICTORY_PLAYER_TITLE, "&6&l胜利！");
        yml.addDefault(Messages.GAME_END_TEAM_WON_CHAT, "%bw_lang_prefix%%bw_team_color%%bw_team_name%&a赢得了这场游戏！");
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
        yml.addDefault(Messages.BED_HOLOGRAM_DEFEND, "&c&l保护你的床！");
        yml.addDefault(Messages.BED_HOLOGRAM_DESTROYED, "&c&l你的床被破坏了！");
        yml.addDefault(Messages.NPC_NAME_TEAM_UPGRADES, "&b队伍升级,&e&l右键点击");
        yml.addDefault(Messages.NPC_NAME_SOLO_UPGRADES, "&b单挑升级,&e&l右键点击");
        yml.addDefault(Messages.NPC_NAME_TEAM_SHOP, "&b队伍商店,&e&l右键点击");
        yml.addDefault(Messages.NPC_NAME_SOLO_SHOP, "&b单挑商店,&e&l右键点击");
        yml.addDefault(Messages.TEAM_ELIMINATED_CHAT, "\n&f&l队伍团灭 > %bw_team_color%%bw_team_name%&c已被团灭！\n");
        yml.addDefault(Messages.NEXT_EVENT_BEDS_DESTROY, "&c床被破坏");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_II, "&f钻石II级");
        yml.addDefault(Messages.NEXT_EVENT_DIAMOND_UPGRADE_III, "&f钻石III级");
        yml.addDefault(Messages.NEXT_EVENT_DRAGON_SPAWN, "&f绝杀模式");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_II, "&f绿宝石II级");
        yml.addDefault(Messages.NEXT_EVENT_EMERALD_UPGRADE_III, "&f绿宝石III级");
        yml.addDefault(Messages.NEXT_EVENT_GAME_END, "&4游戏结束！");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED, "&c床被破坏！");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED, "&f所有床已被破坏！");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED, "&c&l所有床已被破坏！");
        yml.addDefault(Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH, "&c绝杀模式");
        yml.addDefault(Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH, "");
        yml.addDefault(Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH, "&c绝杀模式：&6&b%bw_dragons_amount% %bw_team_color%%bw_team_name%的龙！");
        yml.addDefault(Messages.XP_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(游戏时间)");
        yml.addDefault(Messages.XP_REWARD_WIN, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(游戏胜利)");
        yml.addDefault(Messages.XP_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(团队协作)");
        yml.addDefault(Messages.XP_REWARD_BED_DESTROY, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(破坏床)");
        yml.addDefault(Messages.XP_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(击杀)");
        yml.addDefault(Messages.XP_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_xp%起床战争经验(最终击杀)");

        yml.addDefault(Messages.MONEY_REWARD_PER_MINUTE, "%bw_lang_prefix%&6+%bw_money%金币(游戏时间)");
        yml.addDefault(Messages.MONEY_REWARD_WIN, "%bw_lang_prefix%&6+%bw_money%金币(游戏胜利)");
        yml.addDefault(Messages.MONEY_REWARD_PER_TEAMMATE, "%bw_lang_prefix%&6+%bw_money%金币(团队协作)");
        yml.addDefault(Messages.MONEY_REWARD_BED_DESTROYED, "%bw_lang_prefix%&6+%bw_money%金币(破坏床)");
        yml.addDefault(Messages.MONEY_REWARD_FINAL_KILL, "%bw_lang_prefix%&6+%bw_money%金币(最终击杀)");
        yml.addDefault(Messages.MONEY_REWARD_REGULAR_KILL, "%bw_lang_prefix%&6+%bw_money%金币(击杀)");

        /* Lobby Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "stats"), "&e战绩");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&f右键显示你的战绩！"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "arena-selector"), "&e选择游戏");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "arena-selector"), Collections.singletonList("&f右键选择游戏！"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_NAME.replace("%path%", "leave"), "&e回到主大厅");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_LOBBY_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&f右键离开起床战争！"));
        /* Pre Game Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "stats"), "&e战绩");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "stats"), Collections.singletonList("&f右键显示你的战绩！"));
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_NAME.replace("%path%", "leave"), "&e返回大厅");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_WAITING_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&f右键离开游戏！"));
        /* Spectator Command Items */
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "teleporter"), "&e传送");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_NAME.replace("%path%", "leave"), "&e回到大厅");
        yml.addDefault(Messages.GENERAL_CONFIGURATION_SPECTATOR_ITEMS_LORE.replace("%path%", "leave"), Collections.singletonList("&f右键离开游戏！"));

        /* save default items messages for stats gui */
        yml.addDefault(Messages.PLAYER_STATS_GUI_INV_NAME, "&8%bw_player%的战绩");
        addDefaultStatsMsg(yml, "wins", "&6胜场数", "&f%bw_wins%");
        addDefaultStatsMsg(yml, "losses", "&6失败场数", "&f%bw_losses%");
        addDefaultStatsMsg(yml, "kills", "&6击杀数", "&f%bw_kills%");
        addDefaultStatsMsg(yml, "deaths", "&6死亡数", "&f%bw_deaths%");
        addDefaultStatsMsg(yml, "final-kills", "&6最终击杀数", "&f%bw_final_kills%");
        addDefaultStatsMsg(yml, "final-deaths", "&6最终死亡数", "&f%bw_final_deaths%");
        addDefaultStatsMsg(yml, "beds-destroyed", "&6破坏床数", "&f%bw_beds%");
        addDefaultStatsMsg(yml, "first-play", "&6首次游玩", "&f%bw_play_first%");
        addDefaultStatsMsg(yml, "last-play", "&6上次游玩", "&f%bw_play_last%");
        addDefaultStatsMsg(yml, "games-played", "&6总游玩场数", "&f%bw_games_played%");

        yml.addDefault(Messages.SCOREBOARD_DEFAULT_WAITING, Arrays.asList("&f&l起床战争", "&7%bw_date% &8%bw_server_id%", "", "&f地图：&a%bw_map%", "", "&f玩家数：&a%bw_on%/%bw_max%", "", "&f等待中...", "", "&f模式：&a%bw_group%", "&f版本：&7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_STARTING, Arrays.asList("&f&l起床战争", "&7%bw_date% &8%bw_server_id%", "", "&f地图：&a%bw_map%", "", "&f玩家数：&a%bw_on%/%bw_max%", "", "&f &a%bw_time% &f秒后开始", "", "&f模式：&a%bw_group%", "&f模式：&7%bw_version%", "", "&e%bw_server_ip%"));
        yml.addDefault(Messages.SCOREBOARD_DEFAULT_PLAYING, Arrays.asList("&e&l起床战争", "&7%bw_date%", "", "&f%bw_next_event% - &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5%", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.Doubles.playing", Arrays.asList("&e&l起床战争", "&7%bw_date%", "", "&f%bw_next_event% - &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "%bw_team_5%", "%bw_team_6%", "%bw_team_7%", "%bw_team_8%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.3v3v3v3.playing", Arrays.asList("&e&l起床战争", "&7%bw_date%", "", "&f%bw_next_event% - &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&f击杀：&a%bw_kills%", "&f最终击杀：&a%bw_final_kills%", "&f破坏床：&a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault("scoreboard.4v4v4v4.playing", Arrays.asList("&e&l起床战争", "&7%bw_date%", "", "&f%bw_next_event% - &a%bw_time%", "",
                "%bw_team_1%", "%bw_team_2%", "%bw_team_3%", "%bw_team_4%", "", "&f击杀：&a%bw_kills%", "&f最终击杀：&a%bw_final_kills%", "&f破坏床：&a%bw_beds%", "", "&e%bw_server_ip%"));

        yml.addDefault(Messages.SCOREBOARD_LOBBY, Arrays.asList("&6&l起床战争,&4&l起&6&l床战争,&6&l起&4&l床&6&l战争,&6&l起床&4&l战&6&l争,&6&l起床战&4&l争,&6&l起床战争",
                "&f等级：%bw_level%", "", "&f进度：&a%bw_current_xp%&7/&b%bw_required_xp%", "%bw_progress%", "", "&7%bw_player%", "", "&f金币：&a%bw_money%", "", "&f总胜场：&a%bw_wins%", "&f总击杀：&a%bw_kills%", "", "&e%bw_server_ip%"));

        yml.addDefault(Messages.SHOP_INDEX_NAME, "&8快速购买");
        yml.addDefault(Messages.SHOP_QUICK_ADD_NAME, "&8添加到快速购买...");
        yml.addDefault(Messages.SHOP_INSUFFICIENT_MONEY, "%bw_lang_prefix%&c你没有足够的%bw_currency%！ 还需要 %bw_amount% 个%bw_currency%！");
        yml.addDefault(Messages.SHOP_NEW_PURCHASE, "%bw_lang_prefix%&a购买&6%bw_item%");
        yml.addDefault(Messages.SHOP_ALREADY_BOUGHT, "%bw_lang_prefix%&c你已经购买过了！");
        yml.addDefault(Messages.SHOP_ALREADY_HIGHER_TIER, "%bw_lang_prefix%&cYou already have a higher tier item.");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, "%bw_team_color%&l%bw_team_name% &r%bw_team_color%蠹虫");
        yml.addDefault(Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME, "%bw_team_color%%bw_despawn_time%秒 &8[ %bw_team_color%%bw_health%&8]");
        yml.addDefault(Messages.SHOP_SEPARATOR_NAME, "&8⇧ 分类");
        yml.addDefault(Messages.SHOP_SEPARATOR_LORE, Collections.singletonList("&8⇩ 物品"));
        yml.addDefault(Messages.SHOP_QUICK_BUY_NAME, "&b快速购买");
        yml.addDefault(Messages.SHOP_QUICK_BUY_LORE, new ArrayList<>());
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_NAME, "&c空槽位！");
        yml.addDefault(Messages.SHOP_QUICK_EMPTY_LORE, Arrays.asList("&7这是快速购买槽位！", "&bShift+点击 &7商店中的物品", "&7来加到这里。"));
        yml.addDefault(Messages.SHOP_CAN_BUY_COLOR, "&a");
        yml.addDefault(Messages.SHOP_CANT_BUY_COLOR, "&c");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CAN_BUY, "&e点击购买！");
        yml.addDefault(Messages.SHOP_LORE_STATUS_CANT_AFFORD, "&c你没有足够的%bw_currency%！");
        yml.addDefault(Messages.SHOP_LORE_STATUS_MAXED, "&a满级！");
        yml.addDefault(Messages.SHOP_LORE_STATUS_ARMOR, "&a已装备！");
        yml.addDefault(Messages.SHOP_LORE_QUICK_ADD, "&bShift+点击 来添加快速购买");
        yml.addDefault(Messages.SHOP_LORE_QUICK_REMOVE, "&bShift+点击 来从快速购买中移除！");


        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "&8方块", "&a方块", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "wool", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%羊毛", Arrays.asList("&7花费：&f%bw_cost% %bw_currency%", "", "&7很好的搭路工具",
                "&7购买后将变为你队伍的颜色", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "clay", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%硬化粘土", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7保护床的基本方块。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "glass", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%防爆玻璃", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7免疫爆炸。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stone", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%末地石", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7保护床的坚实方块。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ladder", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%梯子", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7爬树时很有用。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "obsidian", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%黑曜石", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7对床的终极保护。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "wood", ConfigPath.SHOP_PATH_CATEGORY_BLOCKS, "%bw_color%原木", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7保护床的坚实方块。", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_MELEE, "&8战斗", "&a战斗", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "stone-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%石剑", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%铁剑", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-sword", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%钻石剑", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "stick", ConfigPath.SHOP_PATH_CATEGORY_MELEE, "%bw_color%木棍(击退 I)", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "&8盔甲", "&a盔甲", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "chainmail", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%锁链护甲", Arrays.asList("&7花费：%bw_cost% %bw_currency%",
                "", "&7锁链靴子和护腿", "&7死亡不掉落", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "iron-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%铁护甲", Arrays.asList("&7花费：%bw_cost% %bw_currency%",
                "", "&7铁靴子和护腿", "&7死亡不掉落", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "diamond-armor", ConfigPath.SHOP_PATH_CATEGORY_ARMOR, "%bw_color%钻石护甲", Arrays.asList("&7花费：%bw_cost% %bw_currency%",
                "", "&7钻石靴子和护腿", "&7死亡不掉落", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "&8工具", "&a工具", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "shears", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%剪刀", Arrays.asList("&7花费：%bw_cost% %bw_currency%",
                "", "&7拆羊毛的利器", "&7死亡不掉落。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "pickaxe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%镐%bw_tier%", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "&7等级：&e%bw_tier%",
                "", "&7该工具可升级。", "&7每次死亡都会降一级。", "", "&7降到最低级为止", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "axe", ConfigPath.SHOP_PATH_CATEGORY_TOOLS, "%bw_color%斧%bw_tier%", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "&7等级：&e%bw_tier%",
                "", "&7该工具可升级。", "&7每次死亡都会降一级。", "", "&7降到最低级为止", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_RANGED, "&8远程武器", "&a远程武器", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "arrow", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%箭", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow1", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%弓", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow2", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%弓（力量 I）", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bow3", ConfigPath.SHOP_PATH_CATEGORY_RANGED, "%bw_color%弓（力量 I, 冲击 I）", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "&8药水", "&a药水", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "speed-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%速度 II 药水（45 秒）", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "jump-potion", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%跳跃提升 V 药水（45 秒）", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "invisibility", ConfigPath.SHOP_PATH_CATEGORY_POTIONS, "%bw_color%隐身药水（30 秒）", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "%bw_quick_buy%", "%bw_buy_status%"));

        addCategoryMessages(yml, ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "&8实用工具", "&a实用工具", Collections.singletonList("&e点击查看！"));

        addContentMessages(yml, "golden-apple", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%金苹果", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7均衡治疗", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bedbug", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%蠹虫", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7在雪球落下的地方召唤蠹虫", "&7来干扰敌人", "&7持续15秒。", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "dream-defender", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%梦境守卫者", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7召唤铁傀儡来保护基地", "&7持续 4 分钟", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "fireball", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%火球", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "", "&7右键发射！",
                "&7把在窄桥上的敌人打下去！", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "tnt", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%TNT", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7立即点燃, 适合炸点东西", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "ender-pearl", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%末影珍珠", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7偷家最快的方式", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "water-bucket", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%水桶", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7减缓敌人的速度", "&7也可以防止 TNT 破坏方块", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "bridge-egg", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%搭桥蛋", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7丢出后在其轨迹形成一座桥", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "magic-milk", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%魔法牛奶", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7喝下后 60 秒内不会触发陷阱", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "sponge", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%海绵", Arrays.asList("&7花费：%bw_cost% %bw_currency%", "",
                "&7用来吸水不错", "", "%bw_quick_buy%", "%bw_buy_status%"));
        addContentMessages(yml, "Compact Pop-up Tower", ConfigPath.SHOP_PATH_CATEGORY_UTILITY, "%bw_color%袖珍弹出塔", Arrays.asList("&花费: %bw_cost% %bw_currency%", "",
                "&7放置一个袖珍弹出塔", "&7塔防！", "", "%bw_quick_buy%", "%bw_buy_status%"));

        yml.addDefault(Messages.MEANING_NO_TRAP, "无陷阱！");
        yml.addDefault(Messages.FORMAT_UPGRADE_TRAP_COST, "&7花费：%bw_currency_color%%bw_cost% %bw_currency%");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CAN_AFFORD, "&e");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_CANT_AFFORD, "&c");
        yml.addDefault(Messages.FORMAT_UPGRADE_COLOR_UNLOCKED, "&a");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_LOCKED, "&7");
        yml.addDefault(Messages.FORMAT_UPGRADE_TIER_UNLOCKED, "&a");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_CLICK_TO_BUY, "%bw_color%点击购买！");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_MONEY, "%bw_color%你没有足够的%bw_currency%！");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_INSUFFICIENT_SPACE, "&eYou don't have enough inventory space to buy this item!");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_LOCKED, "&c已锁定");
        yml.addDefault(Messages.UPGRADES_LORE_REPLACEMENT_UNLOCKED, "%bw_color%已解锁");
        yml.addDefault(Messages.UPGRADES_UPGRADE_BOUGHT_CHAT, "&a%bw_player%购买了&6%bw_upgrade_name%");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-1"), "%bw_color%铁锭熔炉");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "forge"),
                Arrays.asList("&7增加岛上资源的生成速度", "", "{tier_1_color}等级 1：+50% 生成速率, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}等级 2：+100% 生成速率，&b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}等级 3：生成绿宝石，&b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}等级 4：+200% 生成速率，&b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-2"), "%bw_color%金锭熔炉");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-3"), "%bw_color%绿宝石熔炉");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "forge").replace("%bw_tier%", "tier-4"), "%bw_color%无尽熔炉");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_NAME_PATH + "traps", "&e购买陷阱");
        yml.addDefault(Messages.UPGRADES_CATEGORY_ITEM_LORE_PATH + "traps", Arrays.asList("&7已购买的陷阱将从右边进入队列", "", "&e点击查看！"));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "swords").replace("%bw_tier%", "tier-1"), "%bw_color%锋利附魔");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "swords"),
                Arrays.asList("&7队伍的所有剑和斧获得锋利 I！", "", "{tier_1_color}花费：&b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-1"), "%bw_color%护甲强化 I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "armor"),
                Arrays.asList("&7队伍的所有护甲获得保护附魔！", "", "{tier_1_color}等级 1：保护 I， &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}等级 2：保护 II，&b{tier_2_cost} {tier_2_currency}",
                        "{tier_3_color}等级 3：保护 III，&b{tier_3_cost} {tier_3_currency}",
                        "{tier_4_color}等级 4：保护 IV，&b{tier_4_cost} {tier_4_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-2"), "%bw_color%护甲强化 II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-3"), "%bw_color%护甲强化 III");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "armor").replace("%bw_tier%", "tier-4"), "%bw_color%护甲强化 IV");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-1"), "%bw_color%疯狂矿工 I");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "miner"),
                Arrays.asList("&7队伍获得急迫效果。", "", "{tier_1_color}等级 1：急迫 I, &b{tier_1_cost} {tier_1_currency}",
                        "{tier_2_color}等级 2：急迫 II, &b{tier_2_cost} {tier_2_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "miner").replace("%bw_tier%", "tier-2"), "%bw_color%疯狂矿工 II");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "heal-pool").replace("%bw_tier%", "tier-1"), "%bw_color%治愈池");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "heal-pool"),
                Arrays.asList("&7在基地附近生成治愈池！", "", "{tier_1_color}花费：&b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_NAME.replace("%bw_name%", "dragon").replace("%bw_tier%", "tier-1"), "%bw_color%末影龙升级");
        yml.addDefault(Messages.UPGRADES_UPGRADE_TIER_ITEM_LORE.replace("%bw_name%", "dragon").replace("%bw_tier%", "tier-1"),
                Arrays.asList("&7在死斗时你的队伍会有 2 条而不是 1 条龙！", "", "{tier_1_color}花费：&b{tier_1_cost} {tier_1_currency}", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "glass", "&8⬆&7可购买");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "glass", Collections.singletonList("&8⬇&7陷阱队列"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "first", "%bw_color%陷阱 #1：%bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "first", Collections.singletonList("&7第一个进入你基地的敌人将触发该陷阱！"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "first",
                Arrays.asList("", "&7下个购买的陷阱将在此进入队列", "&7陷阱的花费将会随着队列长度增加", "", "&7下个陷阱花费：&b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "second", "%bw_color%陷阱 #2：%bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "second", Collections.singletonList("&7第二个进入你基地的敌人将触发该陷阱！"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "second",
                Arrays.asList("", "&7下个购买的陷阱将在此进入队列", "&7陷阱的花费将会随着队列长度增加", "", "&7下个陷阱花费：&b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_NAME_PATH + "third", "%bw_color%陷阱 #3：%bw_name%");
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE1_PATH + "third", Collections.singletonList("&7第三个进入你基地的敌人将触发该陷阱！"));
        yml.addDefault(Messages.UPGRADES_TRAP_SLOT_ITEM_LORE2_PATH + "third",
                Arrays.asList("", "&7下个购买的陷阱将在此进入队列", "&7陷阱的花费将会随着队列长度增加", "", "&7下个陷阱花费：&b%bw_cost% %bw_currency%"));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "1", "%bw_color%这是个陷阱！");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "1", Arrays.asList("&7造成5秒失明和缓慢", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "2", "%bw_color%反击陷阱");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "2", Arrays.asList("&7给予基地附近的队友 15 秒速度 I。", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "3", "%bw_color%报警陷阱");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "3", Arrays.asList("&7显示隐身的敌人及其名字和队伍。", ""));
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_NAME_PATH + "4", "%bw_color%挖掘疲劳陷阱");
        yml.addDefault(Messages.UPGRADES_BASE_TRAP_ITEM_LORE_PATH + "4", Arrays.asList("&7造成 10 秒挖掘疲劳。", ""));
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_NAME_PATH + "back", "&a返回");
        yml.addDefault(Messages.UPGRADES_SEPARATOR_ITEM_LORE_PATH + "back", Collections.singletonList("&7回到升级和陷阱菜单"));
        yml.addDefault(Messages.UPGRADES_CATEGORY_GUI_NAME_PATH + "traps", "&8将陷阱加入队列");
        yml.addDefault(Messages.UPGRADES_TRAP_QUEUE_LIMIT, "&c陷阱队列已满！");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_MSG, "&c&l%bw_trap%被触发了！");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_TITLE, "&c陷阱触发！");
        yml.addDefault(Messages.UPGRADES_TRAP_DEFAULT_SUBTITLE, "&f你队伍的%bw_trap%被触发了！");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_MSG + "3", "&c&l报警陷阱被%bw_color%&l%bw_team%的&7&l%bw_player%&c&l触发了！");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_TITLE + "3", "&c&l警报！！！");
        yml.addDefault(Messages.UPGRADES_TRAP_CUSTOM_SUBTITLE + "3", "%bw_color%%bw_team%&f触发了陷阱！");
        yml.addDefault(Messages.UPGRADES_UPGRADE_ALREADY_CHAT, "&cYou already unlocked this upgrade!");
        save();
        setPrefix(m(Messages.PREFIX));
        setPrefixStatic(m(Messages.PREFIX));
    }
}
