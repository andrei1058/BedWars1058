package com.andrei1058.bedwars.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static com.andrei1058.bedwars.Main.getParty;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.configuration.Language.partyPlayerOffline;

public class Party extends BukkitCommand {

    public Party(String name) {
        super(name);
    }
    //owner, target
    private static HashMap<Player, Player> partySessionRequest = new HashMap<>();

    @Override
    public boolean execute(CommandSender s, String c, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        Player p = (Player) s;
        if (args.length == 0 || args[0].equalsIgnoreCase("help")){
            sendPartyCmds(p);
            return true;
        }
        if (args.length >= 1){
            switch (args[0].toLowerCase()){
                case "invite":
                    if (args.length == 1){
                        p.sendMessage(getMsg(p, lang.partyInviteUsage));
                     return true;
                    }
                    if (getParty().hasParty(p) && !getParty().isOwner(p)){
                        p.sendMessage(getMsg(p, lang.partyInsuffPerms));
                        return true;
                    }
                    if (args.length >= 2){
                        if (Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()){
                            if (p == Bukkit.getPlayer(args[1])){
                                p.sendMessage(getMsg(p, lang.partyInviteYourself));
                                return true;
                            }
                            p.sendMessage(getMsg(p, lang.partyIniteSent).replace("{player}", args[1]));
                            TextComponent tc = new TextComponent(getMsg(p, lang.partyInviteReceived).replace("{player}", p.getName()));
                            tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept "+p.getName()));
                            Bukkit.getPlayer(args[1]).spigot().sendMessage(tc);
                            if (partySessionRequest.containsKey(p)){
                                partySessionRequest.replace(p, Bukkit.getPlayer(args[1]));
                            } else {
                                partySessionRequest.put(p, Bukkit.getPlayer(args[1]));
                            }
                        } else {
                            p.sendMessage(getMsg(p, partyPlayerOffline).replace("{player}", args[1]));
                        }
                    }
                    break;
                case "accept":
                    if (args.length < 2){
                        return true;
                    }
                    if (getParty().hasParty(p)){
                        p.sendMessage(getMsg(p, lang.partyAlreadyIn));
                        return true;
                    }
                    if (Bukkit.getPlayer(args[1]) == null || !Bukkit.getPlayer(args[1]).isOnline()){
                        p.sendMessage(getMsg(p, partyPlayerOffline).replace("{player}", args[1]));
                        return true;
                    }
                    if (partySessionRequest.get(Bukkit.getPlayer(args[1])) != null && partySessionRequest.get(Bukkit.getPlayer(args[1])).equals(p)){
                        partySessionRequest.remove(Bukkit.getPlayer(args[1]));
                        if (getParty().hasParty(Bukkit.getPlayer(args[1]))){
                            getParty().addMember(Bukkit.getPlayer(args[1]), p);
                            for (Player on : getParty().getMembers(Bukkit.getPlayer(args[1]))){
                                on.sendMessage(getMsg(p, lang.partyJoined).replace("{player}", p.getName()));
                            }
                        } else {
                            getParty().createParty(Bukkit.getPlayer(args[1]), p);
                            for (Player on : getParty().getMembers(Bukkit.getPlayer(args[1]))){
                                on.sendMessage(getMsg(p, lang.partyJoined).replace("{player}", p.getName()));
                            }
                        }
                    } else {
                        p.sendMessage(getMsg(p, lang.partyNoInvite));
                    }
                    break;
                case "leave":
                    if (!getParty().hasParty(p)){
                        p.sendMessage(getMsg(p, lang.partyNotInParty));
                        return true;
                    }
                    if (getParty().isOwner(p)){
                        p.sendMessage(getMsg(p, lang.partyCantLeaveOwner));
                        return true;
                    }
                    getParty().removeFromParty(p);
                    break;
                case "disband":
                    if (!getParty().hasParty(p)){
                        p.sendMessage(getMsg(p, lang.partyNotInParty));
                        return true;
                    }
                    if (!getParty().isOwner(p)){
                        p.sendMessage(getMsg(p, lang.partyInsuffPerms));
                        return true;
                    }
                    getParty().disband(p);
                    break;
                case "remove":
                    if (args.length == 1){
                        p.sendMessage(getMsg(p, lang.partyRemoveUsage));
                        return true;
                    }
                    if (getParty().hasParty(p) && !getParty().isOwner(p)){
                        p.sendMessage(getMsg(p, lang.partyInsuffPerms));
                        return true;
                    }
                    if (args.length >= 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null){
                            p.sendMessage(getMsg(p, lang.partyNotInPartyAtRemove).replace("{player}", args[1]));
                            return true;
                        }
                        if (!getParty().isMember(p, target)){
                            p.sendMessage(getMsg(p, lang.partyNotInPartyAtRemove).replace("{player}", args[1]));
                            return true;
                        }
                        getParty().removePlayer(p, target);
                    }
                    break;
                    default:
                        sendPartyCmds(p);
                        break;
            }
        }
        return false;
    }

    private void sendPartyCmds(Player p){
        for (String s : getList(p, lang.partyHelpCmds)){
            p.sendMessage(s);
        }
    }
}
