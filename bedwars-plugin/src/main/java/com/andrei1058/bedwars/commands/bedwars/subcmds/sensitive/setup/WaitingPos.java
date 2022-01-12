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

package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.mainCmd;

public class WaitingPos extends SubCommand {

    public WaitingPos(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss == null){
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " "+getSubCommandName()+" 1 or 2");
        } else {
            if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2")) {
                p.sendMessage("§6 ▪ §7Pos " + args[0] + " set!");
                ss.getConfig().saveArenaLoc("waiting.Pos" + args[0], p.getLocation());
                ss.getConfig().reload();
                if (ss.getConfig().getYml().get("waiting.Pos1") == null){
                    p.sendMessage("§c ▪ §7Set the remaining position:");
                    p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/"+ BedWars.mainCmd+" waitingPos 1", "§dSet pos 1", "/"+getParent().getName()+" waitingPos 1", ClickEvent.Action.RUN_COMMAND));
                } else if (ss.getConfig().getYml().get("waiting.Pos2") == null){
                    p.sendMessage("§c ▪ §7Set the remaining position:");
                    p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/"+ BedWars.mainCmd+" waitingPos 2", "§dSet pos 2", "/"+getParent().getName()+" waitingPos 2", ClickEvent.Action.RUN_COMMAND));
                }
            } else {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " "+getSubCommandName()+" 1 or 2");
            }
        }
        if (!((ss.getConfig().getYml().get("waiting.Pos1") == null || ss.getConfig().getYml().get("waiting.Pos2") == null))){
            Bukkit.dispatchCommand(p, BedWars.mainCmd+" cmds");
            s.sendMessage("§6 ▪ §7Set teams spawn if you didn't!");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("1", "2");
    }

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (!SetupSession.isInSetupSession(p.getUniqueId())) return false;

        return hasPermission(s);
    }
}
