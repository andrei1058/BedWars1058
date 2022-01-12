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

package com.andrei1058.bedwars.upgrades.upgradeaction;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.upgrades.UpgradeAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class DispatchCommand implements UpgradeAction {

    public enum CommandType {
        ONCE_AS_CONSOLE, FOREACH_MEMBER_AS_CONSOLE, FOREACH_MEMBER_AS_PLAYER;

        private void dispatch(ITeam team, String command) {
            switch (this) {
                case ONCE_AS_CONSOLE:
                    if (command.startsWith("/")) {
                        command = command.replaceFirst("/", "");
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    break;
                case FOREACH_MEMBER_AS_CONSOLE:
                    if (command.startsWith("/")) {
                        command = command.replaceFirst("/", "");
                    }
                    for (Player player : team.getMembers()) {
                        String playerName = player.getName();
                        String playerUUID = player.getUniqueId().toString();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command
                                .replace("{player}", playerName).replace("{player_uuid}", playerUUID));
                    }
                    break;
                case FOREACH_MEMBER_AS_PLAYER:
                    if (!command.startsWith("/")) {
                        command = "/" + command;
                    }
                    for (Player player : team.getMembers()) {
                        String playerName = player.getName();
                        String playerUUID = player.getUniqueId().toString();
                        player.chat(command.replace("{player}", playerName).replace("{player_uuid}", playerUUID));
                    }
                    break;
            }
        }
    }

    private final CommandType commandType;

    private final String command;

    public DispatchCommand(CommandType commandType, String command) {
        this.commandType = commandType;
        this.command = command;
    }


    @Override
    public void onBuy(@Nullable Player player, ITeam team) {
        String buyerName = player == null ? "null" : player.getName();
        String buyerUUID = player == null ? "null" : player.getUniqueId().toString();
        String teamName = team.getName();
        String teamDisplay = team.getDisplayName(Language.getDefaultLanguage());
        String teamColor = team.getColor().chat().toString();
        String arenaIdentifier = team.getArena().getArenaName();
        String arenaWorld = team.getArena().getWorldName();
        String arenaDisplay = team.getArena().getDisplayName();
        String arenaGroup = team.getArena().getGroup();
        commandType.dispatch(team, command
                .replace("{buyer}", buyerName)
                .replace("{buyer_uuid}", buyerUUID)
                .replace("{team}", teamName).replace("{team_display}", teamDisplay)
                .replace("{team_color}", teamColor).replace("{arena}", arenaIdentifier)
                .replace("{arena_world}", arenaWorld).replace("{arena_display}", arenaDisplay)
                .replace("{arena_group}", arenaGroup));
    }
}
