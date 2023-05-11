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

package com.tomkeuper.bedwars.commands.party;

import com.tomkeuper.bedwars.BedWars;
import com.tomkeuper.bedwars.api.language.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static com.tomkeuper.bedwars.api.language.Language.getList;
import static com.tomkeuper.bedwars.api.language.Language.getMsg;

public class PartyCommand extends BukkitCommand {

    public PartyCommand(String name) {
        super(name);
    }

    //owner, target
    private static HashMap<UUID, UUID> partySessionRequest = new HashMap<>();

    @Override
    public boolean execute(CommandSender s, String c, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        Player p = (Player) s;
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendPartyCmds(p);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "invite":
                if (args.length == 1) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_USAGE));
                    return true;
                }
                if (BedWars.getParty().hasParty(p) && !BedWars.getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
                    return true;
                }
                if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
                    if (p == Bukkit.getPlayer(args[1])) {
                        p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF));
                        return true;
                    }
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_SENT).replace("%bw_playername%", p.getName()).replace("%bw_player%", args[1]));
                    TextComponent tc = new TextComponent(getMsg(p, Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG).replace("%bw_player%", p.getName()));
                    tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + p.getName()));
                    Bukkit.getPlayer(args[1]).spigot().sendMessage(tc);
                    if (partySessionRequest.containsKey(p.getUniqueId())) {
                        partySessionRequest.replace(p.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId());
                    } else {
                        partySessionRequest.put(p.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId());
                    }
                } else {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("%bw_player%", args[1]));
                }
                break;
            case "accept":
                if (args.length < 2) {
                    return true;
                }
                if (BedWars.getParty().hasParty(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY));
                    return true;
                }
                if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("%bw_player%", args[1]));
                    return true;
                }
                if (!partySessionRequest.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
                    return true;
                }
                if (partySessionRequest.get(Bukkit.getPlayer(args[1]).getUniqueId()).equals(p.getUniqueId())) {
                    partySessionRequest.remove(Bukkit.getPlayer(args[1]).getUniqueId());
                    if (BedWars.getParty().hasParty(Bukkit.getPlayer(args[1]))) {
                        BedWars.getParty().addMember(Bukkit.getPlayer(args[1]), p);
                        for (Player on : BedWars.getParty().getMembers(Bukkit.getPlayer(args[1]))) {
                            on.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("%bw_playername%", p.getName()).replace("%bw_player%", p.getDisplayName()));
                        }
                    } else {
                        BedWars.getParty().createParty(Bukkit.getPlayer(args[1]), p);
                        for (Player on : BedWars.getParty().getMembers(Bukkit.getPlayer(args[1]))) {
                            on.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("%bw_playername%", p.getName()).replace("%bw_player%", p.getDisplayName()));
                        }
                    }
                } else {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
                }
                break;
            case "leave":
                if (!BedWars.getParty().hasParty(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
                    return true;
                }
                if (BedWars.getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND));
                    return true;
                }
                BedWars.getParty().removeFromParty(p);
                break;
            case "disband":
                if (!BedWars.getParty().hasParty(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
                    return true;
                }
                if (!BedWars.getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
                    return true;
                }
                BedWars.getParty().disband(p);
                break;
            case "remove":
                if (args.length == 1) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_USAGE));
                    return true;
                }
                if (BedWars.getParty().hasParty(p) && !BedWars.getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("%bw_player%", args[1]));
                    return true;
                }
                if (!BedWars.getParty().isMember(p, target)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("%bw_player%", args[1]));
                    return true;
                }
                BedWars.getParty().removePlayer(p, target);
                break;
            case "promote":
                if (!BedWars.getParty().hasParty(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
                    return true;
                } else if (!BedWars.getParty().isOwner(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
                    return true;
                }
                if (args.length == 1){
                    this.sendPartyCmds(p);
                    return true;
                }
                Player target1 = Bukkit.getPlayer(args[1]);
                if (!BedWars.getParty().isMember(p, target1)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("%bw_player%", args[1]));
                    return true;
                }
                BedWars.getParty().promote(p, target1);
                for (Player p1 : BedWars.getParty().getMembers(p)) {
                    if (p1.equals(p)) {
                        p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_SUCCESS).replace("%bw_player%", args[1]));
                    } else if (p1.equals(target1)) {
                        p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_OWNER));
                    } else {
                        p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER).replace("%bw_player%", args[1]));
                    }
                }
                break;
            case "info" :
            case "list":
                if (!BedWars.getParty().hasParty(p)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
                    return true;
                }
                Player owner = BedWars.getParty().getOwner(p);
                p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INFO_OWNER).replace("%bw_party_owner%", owner.getName()));
                p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INFO_PLAYERS));
                for (Player p1 : BedWars.getParty().getMembers(owner)) {
                    p.sendMessage(getMsg(p, Messages.COMMAND_PARTY_INFO_PLAYER).replace("%bw_player%", p1.getName()));
                }
                break;
            default:
                sendPartyCmds(p);
                break;
        }
        return false;
    }

    private void sendPartyCmds(Player p) {
        for (String s : getList(p, Messages.COMMAND_PARTY_HELP)) {
            p.sendMessage(s);
        }
    }
}
