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

package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class CmdTpStaff extends SubCommand {

    public CmdTpStaff(ParentCommand parent, String name) {
        super(parent, name);
        setPermission("bw.tp");
        showInList(false);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (!(s instanceof Player)) return true;
        Player p2 = (Player) s;
        if (args.length != 1) {
            s.sendMessage(Language.getMsg(p2, Messages.COMMAND_TP_USAGE));
            return true;
        }

        if (!hasPermission(p2)) {
            p2.sendMessage(getMsg(p2, Messages.COMMAND_FORCESTART_NO_PERM));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
            s.sendMessage(Language.getMsg(p2, Messages.COMMAND_TP_PLAYER_NOT_FOUND));
            return true;
        }
        IArena a = Arena.getArenaByPlayer(p);
        IArena a2 = Arena.getArenaByPlayer(p2);
        if (a == null) {
            s.sendMessage(Language.getMsg(p2, Messages.COMMAND_TP_NOT_IN_ARENA));
            return true;
        }

        if (a.getStatus() == GameState.playing) {
            if (a2 != null) {
                if (a2.isPlayer(p2)) a2.removePlayer(p2, false);
                if (a2.isSpectator(p2)) {
                    if (a2.getArenaName().equals(a.getArenaName())) {
                        TeleportManager.teleport(p2, p.getLocation());
                        return true;
                    } else a2.removeSpectator(p2, false);
                }
            }
            a.addSpectator(p2, false, p.getLocation());
        } else {
            s.sendMessage(Language.getMsg(((Player) s), Messages.COMMAND_TP_NOT_STARTED));
        }

        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> players = new ArrayList<>();
        for (IArena a : Arena.getArenas()) {
            for (Player p : a.getPlayers()) {
                players.add(p.getName());
            }
            for (Player p : a.getSpectators()) {
                players.add(p.getName());
            }
        }
        return players;
    }
}
