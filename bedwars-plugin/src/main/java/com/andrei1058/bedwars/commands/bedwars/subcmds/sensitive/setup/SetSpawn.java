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
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.support.paper.TeleportManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.mainCmd;
import static com.andrei1058.bedwars.commands.Misc.removeArmorStand;

public class SetSpawn extends SubCommand {

    public SetSpawn(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss == null) {
            //s.sendMessage("§c ▪ §7You're not in a setup session!");
            return false;
        }
        if (args.length < 1) {
            p.sendMessage(ss.getPrefix() + ChatColor.RED + "Usage: /" + mainCmd + " setSpawn <team>");
            if (ss.getConfig().getYml().get("Team") != null) {
                for (String team : Objects.requireNonNull(ss.getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                    if (ss.getConfig().getYml().get("Team." + team + ".Spawn") == null) {
                        p.spigot().sendMessage(Misc.msgHoverClick(ss.getPrefix() + "Set spawn for: " + ss.getTeamColor(team) + team + " " + ChatColor.getLastColors(ss.getPrefix()) + "(click to set)", ChatColor.WHITE + "Set spawn for " + ss.getTeamColor(team) + team, "/" + mainCmd + " setSpawn " + team, ClickEvent.Action.RUN_COMMAND));
                    }
                }
            }
        } else {
            if (ss.getConfig().getYml().get("Team." + args[0]) == null) {
                p.sendMessage(ss.getPrefix() + ChatColor.RED + "Could not find target team: " + ChatColor.RED + args[0]);
                if (ss.getConfig().getYml().get("Team") != null) {
                    p.sendMessage(ss.getPrefix() + "Teams list: ");
                    for (String team : Objects.requireNonNull(ss.getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                        p.spigot().sendMessage(Misc.msgHoverClick(ChatColor.GOLD + " " + '▪' + " " + ss.getTeamColor(team) + team + " " + ChatColor.getLastColors(ss.getPrefix()) + "(click to set)", ChatColor.WHITE + "Set spawn for " + ss.getTeamColor(team) + team, "/" + mainCmd + " setSpawn " + team, ClickEvent.Action.RUN_COMMAND));
                    }
                }
            } else {
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Spawn") != null) {
                    removeArmorStand("spawn", ss.getConfig().getArenaLoc("Team." + args[0] + ".Spawn"), ss.getConfig().getString("Team." + args[0] + ".Spawn"));
                }
                ss.getConfig().saveArenaLoc("Team." + args[0] + ".Spawn", p.getLocation());
                String teamm = ss.getTeamColor(args[0]) + args[0];
                p.sendMessage(ChatColor.GOLD + " " + '▪' + " " + "Spawn set for: " + teamm);
                com.andrei1058.bedwars.commands.Misc.createArmorStand(teamm + " " + ChatColor.GOLD + "SPAWN SET", p.getLocation(), ss.getConfig().stringLocationArenaFormat(p.getLocation()));
                int radius = ss.getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
                Location l = p.getLocation();
                for (int x = -radius; x < radius; x++) {
                    for (int y = -radius; y < radius; y++) {
                        for (int z = -radius; z < radius; z++) {
                            Block b = l.clone().add(x, y, z).getBlock();
                            if (BedWars.nms.isBed(b.getType())) {
                                TeleportManager.teleport(p, b.getLocation());
                                Bukkit.dispatchCommand(p, getParent().getName() + " setBed " + args[0]);
                                return true;
                            }
                        }
                    }
                }
                if (ss.getConfig().getYml().get("Team") != null) {
                    StringBuilder remainging = new StringBuilder();
                    for (String team : Objects.requireNonNull(ss.getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                        if (ss.getConfig().getYml().get("Team." + team + ".Spawn") == null) {
                            remainging.append(ss.getTeamColor(team)).append(team).append(" ");
                        }
                    }
                    if (remainging.toString().length() > 0) {
                        p.sendMessage(ss.getPrefix() + "Remaining: " + remainging.toString());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return new ArrayList<>();
    }

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (!SetupSession.isInSetupSession(p.getUniqueId())) return false;

        return hasPermission(s);
    }
}
