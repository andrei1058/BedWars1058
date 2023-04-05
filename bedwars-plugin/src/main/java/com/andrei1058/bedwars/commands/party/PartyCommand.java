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

package com.andrei1058.bedwars.commands.party;

import com.andrei1058.bedwars.api.language.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static com.andrei1058.bedwars.BedWars.getParty;
import static com.andrei1058.bedwars.api.language.Language.getList;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PartyCommand extends BukkitCommand {

    public PartyCommand(String name) {
        super(name);
    }

    //owner, target
    private static HashMap<UUID, UUID> partySessionRequest = new HashMap<>();

    public boolean isInvite(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INVITE_USAGE));
            return true;
        }
        if (getParty().hasParty(player) && !getParty().isOwner(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return true;
        }
        if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()) {
            if (player == Bukkit.getPlayer(args[1])) {
                player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INVITE_DENIED_CANNOT_INVITE_YOURSELF));
                return true;
            }
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INVITE_SENT).replace("{playername}", player.getName()).replace("{player}", args[1]));
            TextComponent tc = new TextComponent(getMsg(player, Messages.COMMAND_PARTY_INVITE_SENT_TARGET_RECEIVE_MSG).replace("{player}", player.getName()));
            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName()));
            Bukkit.getPlayer(args[1]).spigot().sendMessage(tc);
            if (partySessionRequest.containsKey(player.getUniqueId())) {
                partySessionRequest.replace(player.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId());
            } else {
                partySessionRequest.put(player.getUniqueId(), Bukkit.getPlayer(args[1]).getUniqueId());
            }
        } else {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("{player}", args[1]));
        }
        return false;
    }

    public boolean isAccept(Player player, String[] args) {
        if (args.length < 2) {
            return true;
        }
        if (getParty().hasParty(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_ACCEPT_DENIED_ALREADY_IN_PARTY));
            return true;
        }
        if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INVITE_DENIED_PLAYER_OFFLINE).replace("{player}", args[1]));
            return true;
        }
        if (!partySessionRequest.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
            return true;
        }
        if (partySessionRequest.get(Bukkit.getPlayer(args[1]).getUniqueId()).equals(player.getUniqueId())) {
            partySessionRequest.remove(Bukkit.getPlayer(args[1]).getUniqueId());
            if (getParty().hasParty(Bukkit.getPlayer(args[1]))) {
                getParty().addMember(Bukkit.getPlayer(args[1]), player);
                for (Player on : getParty().getMembers(Bukkit.getPlayer(args[1]))) {
                    on.sendMessage(getMsg(player, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("{playername}", player.getName()).replace("{player}", player.getDisplayName()));
                }
            } else {
                getParty().createParty(Bukkit.getPlayer(args[1]), player);
                for (Player on : getParty().getMembers(Bukkit.getPlayer(args[1]))) {
                    on.sendMessage(getMsg(player, Messages.COMMAND_PARTY_ACCEPT_SUCCESS).replace("{playername}", player.getName()).replace("{player}", player.getDisplayName()));
                }
            }
        } else {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_ACCEPT_DENIED_NO_INVITE));
        }
        return false;
    }

    public boolean isLeave(Player player) {
        if (!getParty().hasParty(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
            return true;
        }
        if (getParty().isOwner(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_LEAVE_DENIED_IS_OWNER_NEEDS_DISBAND));
            return true;
        }
        getParty().removeFromParty(player);
        return false;
    }

    public boolean isDisband(Player player) {
        if (!getParty().hasParty(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
            return true;
        }
        if (!getParty().isOwner(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return true;
        }
        getParty().disband(player);
        return false;
    }

    public  boolean isRemove(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_REMOVE_USAGE));
            return true;
        }
        if (getParty().hasParty(player) && !getParty().isOwner(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return true;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("{player}", args[1]));
            return true;
        }
        if (!getParty().isMember(player, target)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("{player}", args[1]));
            return true;
        }
        getParty().removePlayer(player, target);
        return false;
    }

    public boolean isPromote(Player player, String[] args) {
        if (!getParty().hasParty(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
            return true;
        } else if (!getParty().isOwner(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INSUFFICIENT_PERMISSIONS));
            return true;
        }
        if (args.length == 1){
            this.sendPartyCmds(player);
            return true;
        }
        Player target1 = Bukkit.getPlayer(args[1]);
        if (!getParty().isMember(player, target1)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_REMOVE_DENIED_TARGET_NOT_PARTY_MEMBER).replace("{player}", args[1]));
            return true;
        }
        getParty().promote(player, target1);
        for (Player p1 : getParty().getMembers(player)) {
            if (p1.equals(player)) {
                p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_SUCCESS).replace("{player}", args[1]));
            } else if (p1.equals(target1)) {
                p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_OWNER));
            } else {
                p1.sendMessage(getMsg(p1, Messages.COMMAND_PARTY_PROMOTE_NEW_OWNER).replace("{player}", args[1]));
            }
        }
        return false;
    }

    public boolean isInfoOrList(Player player) {
        if (!getParty().hasParty(player)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_GENERAL_DENIED_NOT_IN_PARTY));
            return true;
        }
        Player owner = getParty().getOwner(player);
        player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INFO_OWNER).replace("{owner}", owner.getName()));
        player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INFO_PLAYERS));
        for (Player player1 : getParty().getMembers(owner)) {
            player.sendMessage(getMsg(player, Messages.COMMAND_PARTY_INFO_PLAYER).replace("{player}", player1.getName()));
        }
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String c, String[] args) {
        if (sender instanceof ConsoleCommandSender) return true;
        Player player = (Player) sender;
        boolean result;
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendPartyCmds(player);
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "invite":
                result = isInvite(player, args);
                return result;
            case "accept":
                result = isAccept(player, args);
                return result;
            case "leave":
                result = isLeave(player);
                return result;
            case "disband":
                result = isDisband(player);
                return result;
            case "remove":
                result = isRemove(player, args);
                return result;
            case "promote":
                result = isPromote(player, args);
                return result;
            case "info" :
            case "list":
                result = isInfoOrList(player);
                return result;
            default:
                sendPartyCmds(player);
                break;
        }
        return false;
    }

    private void sendPartyCmds(Player player) {
        for (String s : getList(player, Messages.COMMAND_PARTY_HELP)) {
            player.sendMessage(s);
        }
    }
}
