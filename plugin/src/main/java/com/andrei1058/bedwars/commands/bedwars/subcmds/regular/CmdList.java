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
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getList;

public class CmdList extends SubCommand {

    public CmdList(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(11);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + com.andrei1058.bedwars.commands.bedwars.MainCommand.getInstance().getName() + " " + getSubCommandName() + "         §8 - §e view player cmds", "§fView player commands.", "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (SetupSession.isInSetupSession(p.getUniqueId())) {
            SetupSession ss = SetupSession.getSession(p.getUniqueId());
            Objects.requireNonNull(ss).getConfig().reload();

            boolean waitingSpawn = ss.getConfig().getYml().get("waiting.Loc") != null,
                    pos1 = ss.getConfig().getYml().get("waiting.Pos1") != null,
                    pos2 = ss.getConfig().getYml().get("waiting.Pos2") != null,
                    pos = pos1 && pos2;
            StringBuilder spawnNotSetNames = new StringBuilder();
            StringBuilder bedNotSet = new StringBuilder();
            StringBuilder shopNotSet = new StringBuilder();
            StringBuilder killDropsNotSet = new StringBuilder();
            StringBuilder upgradeNotSet = new StringBuilder();
            StringBuilder spawnNotSet = new StringBuilder();
            StringBuilder generatorNotSet = new StringBuilder();
            int teams = 0;

            if (ss.getConfig().getYml().get("Team") != null) {
                for (String team : ss.getConfig().getYml().getConfigurationSection("Team").getKeys(true)) {
                    if (ss.getConfig().getYml().get("Team." + team + ".Color") == null) continue;
                    ChatColor color = TeamColor.getChatColor(ss.getConfig().getYml().getString("Team." + team + ".Color"));
                    if (ss.getConfig().getYml().get("Team." + team + ".Spawn") == null) {
                        spawnNotSet.append(color).append("▋");
                        spawnNotSetNames.append(color).append(team).append(" ");
                    }
                    if (ss.getConfig().getYml().get("Team." + team + ".Bed") == null) {
                        bedNotSet.append(color).append("▋");
                    }
                    if (ss.getConfig().getYml().get("Team." + team + ".Shop") == null) {
                        shopNotSet.append(color).append("▋");
                    }
                    if (ss.getConfig().getYml().get("Team." + team + "." + ConfigPath.ARENA_TEAM_KILL_DROPS_LOC) == null) {
                        killDropsNotSet.append(color).append("▋");
                    }
                    if (ss.getConfig().getYml().get("Team." + team + ".Upgrade") == null) {
                        upgradeNotSet.append(color).append("▋");
                    }
                    if (ss.getConfig().getYml().get("Team." + team + ".Iron") == null || ss.getConfig().getYml().get("Team." + team + ".Gold") == null) {
                        generatorNotSet.append(color).append("▋");
                    }
                    teams++;
                }
            }
            int emGen = 0, dmGen = 0;
            if (ss.getConfig().getYml().get("generator.Emerald") != null) {
                emGen = ss.getConfig().getYml().getStringList("generator.Emerald").size();
            }
            if (ss.getConfig().getYml().get("generator.Diamond") != null) {
                dmGen = ss.getConfig().getYml().getStringList("generator.Diamond").size();
            }

            String posMsg, group = ChatColor.RED + "(NOT SET)";
            if (pos1 && !pos2) {
                posMsg = ChatColor.RED + "(POS 2 NOT SET)";
            } else if (!pos1 && pos2) {
                posMsg = ChatColor.RED + "(POS 1 NOT SET)";
            } else if (pos1) {
                posMsg = ChatColor.GREEN + "(SET)";
            } else {
                posMsg = ChatColor.GRAY + "(NOT SET) " + ChatColor.ITALIC + "OPTIONAL";
            }

            String g2 = ss.getConfig().getYml().getString("group");
            if (g2 != null) {
                if (!g2.equalsIgnoreCase("default")) {
                    group = ChatColor.GREEN + "(" + g2 + ")";
                }
            }

            int maxInTeam = ss.getConfig().getInt("maxInTeam");

            String setWaitingSpawn = ss.dot() + (waitingSpawn ? ChatColor.STRIKETHROUGH : "") + "setWaitingSpawn" + ChatColor.RESET + " " + (waitingSpawn ? ChatColor.GREEN + "(SET)" : ChatColor.RED + "(NOT SET)");
            String waitingPos = ss.dot() + (pos ? ChatColor.STRIKETHROUGH : "") + "waitingPos 1/2" + ChatColor.RESET + " " + posMsg;
            String setSpawn = ss.dot() + ((spawnNotSet.length() == 0) ? ChatColor.STRIKETHROUGH : "") + "setSpawn <teamName>" + ChatColor.RESET + " " + ((spawnNotSet.length() == 0) ? ChatColor.GREEN + "(ALL SET)" : ChatColor.RED + "(Remaining: " + spawnNotSet + ChatColor.RED + ")");
            String setBed = ss.dot() + ((bedNotSet.toString().length() == 0) ? ChatColor.STRIKETHROUGH : "") + "setBed" + ChatColor.RESET + " " + ((bedNotSet.length() == 0) ? ChatColor.GREEN + "(ALL SET)" : ChatColor.RED + "(Remaining: " + bedNotSet + ChatColor.RED + ")");
            String setShop = ss.dot() + ((shopNotSet.toString().length() == 0) ? ChatColor.STRIKETHROUGH : "") + "setShop" + ChatColor.RESET + " " + ((shopNotSet.length() == 0) ? ChatColor.GREEN + "(ALL SET)" : ChatColor.RED + "(Remaining: " + shopNotSet + ChatColor.RED + ")");
            String setKillDrops = ss.dot() + ((killDropsNotSet.toString().length() == 0) ? ChatColor.STRIKETHROUGH : "") + "setKillDrops" + ChatColor.RESET + " " + ((shopNotSet.length() == 0) ? ChatColor.GREEN + "(ALL SET)" : ChatColor.RED + "(Remaining: " + killDropsNotSet + ChatColor.RED + ")");
            String setUpgrade = ss.dot() + ((upgradeNotSet.toString().length() == 0) ? ChatColor.STRIKETHROUGH : "") + "setUpgrade" + ChatColor.RESET + " " + ((upgradeNotSet.length() == 0) ? ChatColor.GREEN + "(ALL SET)" : ChatColor.RED + "(Remaining: " + upgradeNotSet + ChatColor.RED + ")");
            String addGenerator = ss.dot() + "addGenerator " + ((generatorNotSet.toString().length() == 0) ? "" : ChatColor.RED + "(Remaining: " + generatorNotSet + ChatColor.RED + ") ") + ChatColor.YELLOW + "(" + ChatColor.DARK_GREEN + "E" + emGen + " " + ChatColor.AQUA + "D" + dmGen + ChatColor.YELLOW + ")";
            String setSpectatorSpawn = ss.dot() + (ss.getConfig().getYml().get(ConfigPath.ARENA_SPEC_LOC) == null ? "" : ChatColor.STRIKETHROUGH) + "setSpectSpawn" + ChatColor.RESET + " " + (ss.getConfig().getYml().get(ConfigPath.ARENA_SPEC_LOC) == null ? ChatColor.RED + "(NOT SET)" : ChatColor.GRAY + "(SET)");

            s.sendMessage("");
            s.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + com.andrei1058.bedwars.commands.bedwars.MainCommand.getDot() + ChatColor.GOLD + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + ChatColor.GRAY + '-' + " " + ChatColor.GREEN + ss.getWorldName() + " commands");
            p.spigot().sendMessage(Misc.msgHoverClick(setWaitingSpawn, ChatColor.WHITE + "Set the place where players have\n" + ChatColor.WHITE + "to wait before the game starts.", "/" + getParent().getName() + " setWaitingSpawn", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(waitingPos, ChatColor.WHITE + "Make it so the waiting lobby will disappear at start.\n" + ChatColor.WHITE + "Select it as a world edit region.", "/" + getParent().getName() + " waitingPos ", ClickEvent.Action.SUGGEST_COMMAND));
            if (ss.getSetupType() == SetupType.ADVANCED) {
                p.spigot().sendMessage(Misc.msgHoverClick(setSpectatorSpawn, ChatColor.WHITE + "Set where to spawn spectators.", "/" + getParent().getName() + " setSpectSpawn", ClickEvent.Action.RUN_COMMAND));
            }
            p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "autoCreateTeams " + ChatColor.YELLOW + "(auto detect)", ChatColor.WHITE + "Create teams based on islands colors.", "/" + getParent().getName() + " autoCreateTeams", ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "createTeam <name> <color> " + ChatColor.YELLOW + "(" + teams + " CREATED)", ChatColor.WHITE + "Create a team.", "/" + getParent().getName() + " createTeam ", ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "removeTeam <name>", ChatColor.WHITE + "Remove a team by name.", "/" + mainCmd + " removeTeam ", ClickEvent.Action.SUGGEST_COMMAND));


            p.spigot().sendMessage(Misc.msgHoverClick(setSpawn, ChatColor.WHITE + "Set a team spawn.\n" + ChatColor.WHITE + "Teams without a spawn set:\n" + spawnNotSetNames.toString(), "/" + getParent().getName() + " setSpawn ", ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(setBed, ChatColor.WHITE + "Set a team's bed location.\n" + ChatColor.WHITE + "You don't have to specify the team name.", "/" + getParent().getName() + " setBed", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(setShop, ChatColor.WHITE + "Set a team's NPC.\n" + ChatColor.WHITE + "You don't have to specify the team name.\n" + ChatColor.WHITE + "It will be spawned only when the game starts.", "/" + getParent().getName() + " setShop", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(setUpgrade, ChatColor.WHITE + "Set a team's upgrade NPC.\n" + ChatColor.WHITE + "You don't have to specify the team name.\n" + ChatColor.WHITE + "It will be spawned only when the game starts.", "/" + getParent().getName() + " setUpgrade", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));
            if (ss.getSetupType() == SetupType.ADVANCED) {
                p.spigot().sendMessage(Misc.msgHoverClick(setKillDrops, ChatColor.WHITE + "Set a the location where to drop\n" + ChatColor.WHITE + "enemy items after you kill them.", "/" + getParent().getName() + " setKillDrops ", ClickEvent.Action.SUGGEST_COMMAND));
            }
            String genHover = (ss.getSetupType() == SetupType.ADVANCED ? ChatColor.WHITE + "Add a generator spawn point.\n" + ChatColor.YELLOW + "/" + getParent().getName() + " addGenerator <Iron/ Gold/ Emerald, Diamond>" :
                    ChatColor.WHITE + "Add a generator spawn point.\n" + ChatColor.YELLOW + "Stay in on a team island to set a team generator") + "\n" + ChatColor.WHITE + "Stay on a diamond block to set the diamond generator.\n" + ChatColor.WHITE + "Stay on a emerald block to set an emerald generator.";

            p.spigot().sendMessage(Misc.msgHoverClick(addGenerator, genHover, "/" + getParent().getName() + " addGenerator ", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "removeGenerator", genHover, "/" + getParent().getName() + " removeGenerator", ss.getSetupType() == SetupType.ASSISTED ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND));

            if (ss.getSetupType() == SetupType.ADVANCED) {
                p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "setMaxInTeam <int> (IS SET TO " + maxInTeam + ")", ChatColor.WHITE + "Set the max team size.", "/" + mainCmd + " setMaxInTeam ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "arenaGroup " + group, ChatColor.WHITE + "Set the arena group.", "/" + mainCmd + " arenaGroup ", ClickEvent.Action.SUGGEST_COMMAND));
            } else {
                p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "setType <type> " + group, ChatColor.WHITE + "Add the arena to a group.", "/" + getParent().getName() + " setType", ClickEvent.Action.RUN_COMMAND));
            }

            p.spigot().sendMessage(Misc.msgHoverClick(ss.dot() + "save", ChatColor.WHITE + "Save arena and go back to lobby", "/" + getParent().getName() + " save", ClickEvent.Action.SUGGEST_COMMAND));
        } else {
            TextComponent credits = new TextComponent(ChatColor.BLUE + "" + ChatColor.BOLD + com.andrei1058.bedwars.commands.bedwars.MainCommand.getDot() + " " + ChatColor.GOLD + plugin.getName() + " " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by andrei1058");
            credits.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
            credits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Arenas: " + (Arena.getArenas().size() == 0 ? ChatColor.RED + "0" : ChatColor.GREEN + "" + Arena.getArenas().size())).create()));
            ((Player) s).spigot().sendMessage(credits);
            for (String string : getList((Player) s, Messages.COMMAND_MAIN)) {
                s.sendMessage(string);
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, BedWars api) {

        if (s instanceof Player) {
            Player p = (Player) s;
            if (Arena.isInArena(p)) return false;

            if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        }

        return hasPermission(s);
    }
}
