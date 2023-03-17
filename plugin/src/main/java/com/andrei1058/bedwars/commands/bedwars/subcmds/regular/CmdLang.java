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

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.plugin;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class CmdLang extends SubCommand {

    public CmdLang(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(18);
        showInList(false);
        setDisplayInfo(com.andrei1058.bedwars.commands.bedwars.MainCommand.createTC("§6 ▪ §7/" + MainCommand.getInstance().getName() + " " + getSubCommandName(), "/" + getParent().getName() + " " + getSubCommandName(), "§fChange your language."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (Arena.getArenaByPlayer(p) != null) return false;
        if (args.length == 0) {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_HEADER));
            for (Language l : Language.getLanguages()) {
                p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_FORMAT).replace("{iso}", l.getIso()).replace("{name}", l.getLangName()));
            }
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE));
            return true;
        } else if (Language.isLanguageExist(args[0])) {
            if (Arena.getArenaByPlayer(p) == null) {
                if (Language.setPlayerLanguage(p.getUniqueId(), args[0])) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY)), 3L);
                } else {
                    p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_HEADER));
                    for (Language l : Language.getLanguages()) {
                        p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_FORMAT).replace("{iso}", l.getIso()).replace("{name}", l.getLangName()));
                    }
                    p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE));
                    return true;
                }
            } else {
                p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE_DENIED));
            }
        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_NOT_EXIST));
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tab = new ArrayList<>();
        for (Language lang : Language.getLanguages()) {
            tab.add(lang.getIso());
        }
        return tab;
    }

    @Override
    public boolean canSee(CommandSender s, BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        return hasPermission(s);
    }
}
