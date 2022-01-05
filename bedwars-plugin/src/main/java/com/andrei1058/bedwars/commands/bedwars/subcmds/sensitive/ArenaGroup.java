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

package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.ArenaConfig;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.BedWars.config;
import static com.andrei1058.bedwars.BedWars.plugin;

public class ArenaGroup extends SubCommand {

    public ArenaGroup(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(8);
        showInList(true);
        setPermission(Permissions.PERMISSION_ARENA_GROUP);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName()+" §8- §eclick for details", "§fManage arena groups.",
                "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;

        if (args.length == 0){
            sendArenaGroupCmdList(p);
            return true;
        }

        List<String> arenaGroups = config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS) == null ? new ArrayList<>() : config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS);

        switch (args[0]){

            case "create":

                if (args.length < 2) {
                    sendArenaGroupCmdList(p);
                    return true;
                }

                if (arenaGroups.contains(args[1])) {
                    p.sendMessage("§c▪ §7This group already exists!");
                    return true;
                }

                arenaGroups.add(args[1]);
                config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS, arenaGroups);
                p.sendMessage("§6 ▪ §7Group " + args[1] + " has been created!");

                return true;

            case "remove":

                if (args.length < 2) {
                    sendArenaGroupCmdList(p);
                    return true;
                }

                if (!arenaGroups.contains(args[1])) {
                    p.sendMessage("§c▪ §7This group doesn't exist!");
                    return true;
                }

                arenaGroups.remove(args[1]);
                config.set(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS, arenaGroups);
                p.sendMessage("§6 ▪ §7Group " +  args[1] + " has been deleted!");
                return true;

            case "list":

                p.sendMessage("§7Available arena groups:");
                p.sendMessage("§6 ▪ §fDefault");
                for (String group : arenaGroups) {
                    p.sendMessage("§6 ▪ §f" + group);
                }
                return true;

            case "set":

                if (args.length < 3) {
                    sendArenaGroupCmdList(p);
                    return true;
                }

                if (config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS) != null) {
                    if (config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS).contains(args[2])) {
                        File arena = new File(plugin.getDataFolder(), "/Arenas/" + args[1] + ".yml");
                        if (!arena.exists()) {
                            p.sendMessage("§c▪ §7Arena " + args[1] + " doesn't exist!");
                            return true;
                        }
                        ArenaConfig cm = new ArenaConfig(BedWars.plugin, args[1], plugin.getDataFolder().getPath() + "/Arenas");
                        cm.set("group", args[2]);
                        if (Arena.getArenaByName(args[1]) != null) {
                            Arena.getArenaByName(args[1]).setGroup(args[2]);
                        }
                        p.sendMessage("§6 ▪ §7Arena " + args[1] + " was added to the group: " + args[2]);
                    } else {
                        p.sendMessage("§6 ▪ §7There isn't any group called: " + args[2]);
                        p.sendMessage("§7Available arena groups:");
                        p.sendMessage("§6 ▪ §fDefault");
                        for (String group : arenaGroups) {
                            p.sendMessage("§6 ▪ §f" + group);
                        }
                    }
                } else {
                    p.sendMessage("§6 ▪ §7There isn't any group called: " + args[2]);
                    p.sendMessage("§7Available arena groups:");
                    p.sendMessage("§6 ▪ §fDefault");
                    for (String group : arenaGroups) {
                        p.sendMessage("§6 ▪ §f" + group);
                    }
                }
                return true;
            default:
                sendArenaGroupCmdList(p);
                return true;
        }
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("create", "remove", "list", "set");
    }

    private void sendArenaGroupCmdList(Player p) {
        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " create §o<groupName>",
                "Create an arena group. More details on our wiki.", "/" + getParent().getName() + " " + getSubCommandName() + " create",
                ClickEvent.Action.SUGGEST_COMMAND));
        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " list",
                "View available groups.", "/" + getParent().getName() + " " + getSubCommandName() + " list",
                ClickEvent.Action.RUN_COMMAND));
        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " remove §o<groupName>",
                "Remove an arena group. More details on our wiki.", "/" + getParent().getName() + " " + getSubCommandName() + " remove",
                ClickEvent.Action.SUGGEST_COMMAND));
        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " §r§7set §o<arenaName> <groupName>",
                "Set the arena group. More details on our wiki.", "/" + getParent().getName() + " " + getSubCommandName() + " set",
                ClickEvent.Action.SUGGEST_COMMAND));
    }

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        return hasPermission(s);
    }
}
