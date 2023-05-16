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

package com.tomkeuper.bedwars.commands.bedwars.subcmds.regular;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.command.ParentCommand;
import com.tomkeuper.bedwars.api.command.SubCommand;
import com.tomkeuper.bedwars.api.configuration.ConfigPath;
import com.tomkeuper.bedwars.api.language.Language;
import com.tomkeuper.bedwars.api.language.Messages;
import com.tomkeuper.bedwars.api.server.ServerType;
import com.tomkeuper.bedwars.arena.Arena;
import com.tomkeuper.bedwars.arena.Misc;
import com.tomkeuper.bedwars.arena.SetupSession;
import com.tomkeuper.bedwars.commands.bedwars.MainCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.tomkeuper.bedwars.BedWars.config;

public class CmdLeave extends SubCommand {

    private static HashMap<UUID, Long> delay = new HashMap<>();
    private static HashMap<UUID, BukkitTask> leaving = new HashMap<>();

    public CmdLeave(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(20);
        showInList(false);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+ MainCommand.getInstance().getName()+" leave", "/"+getParent().getName()+" "+getSubCommandName(), "§fLeave an arena."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;

        if (cancel(p.getUniqueId())) return true;

        IArena a = Arena.getArenaByPlayer(p);
        if (p.getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
            update(p.getUniqueId());
            Misc.moveToLobbyOrKick(p, a, a != null && a.isSpectator(p.getUniqueId()));
            return true;
        } else {
            if (a == null) {
                update(p.getUniqueId());
                p.sendMessage(Language.getMsg(p, Messages.COMMAND_FORCESTART_NOT_IN_GAME));
                return true;
            }
            if (config.getInt(ConfigPath.GENERAL_CONFIGURATION_LEAVE_DELAY) == 0) {
                Misc.moveToLobbyOrKick(p, a, a != null && a.isSpectator(p.getUniqueId()));
            } else {
                BukkitTask qt = leaving.get(p.getUniqueId());
                if (qt != null) {
                    update(p.getUniqueId());
                    qt.cancel();
                    leaving.remove(p.getUniqueId());
                    p.sendMessage(Language.getMsg(p, Messages.COMMAND_LEAVE_CANCELED));
                    return true;
                }
                p.sendMessage(Language.getMsg(p, Messages.COMMAND_LEAVE_STARTED).replace("%bw_leave_delay%", String.valueOf(config.getInt(ConfigPath.GENERAL_CONFIGURATION_LEAVE_DELAY))));
                BukkitTask bukkitTask = new BukkitRunnable() {
                    public void run() {
                        Misc.moveToLobbyOrKick(p, a, a != null && a.isSpectator(p.getUniqueId()));
                        leaving.remove(p.getUniqueId());
                    }
                }.runTaskLater(BedWars.plugin, config.getInt(ConfigPath.GENERAL_CONFIGURATION_LEAVE_DELAY) * 20L);
                leaving.put(p.getUniqueId(),bukkitTask);
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, com.tomkeuper.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;

        if (BedWars.getServerType() == ServerType.SHARED && !Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        return hasPermission(s);
    }

    private static boolean cancel(UUID player){
        return delay.getOrDefault(player, 0L) > System.currentTimeMillis();
    }

    private static void update(UUID player){
        if (delay.containsKey(player)){
            delay.replace(player, System.currentTimeMillis() + 2500L);
            return;
        }
        delay.put(player, System.currentTimeMillis() + 2500L);
    }
}
