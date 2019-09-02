package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class CmdJoin extends SubCommand {

    public CmdJoin(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(19);
        showInList(false);
        setDisplayInfo(com.andrei1058.bedwars.commands.bedwars.MainCommand.createTC("§6 ▪ §7/"+ com.andrei1058.bedwars.commands.bedwars.MainCommand.getInstance().getName()+" join §e<random/ arena/ groupName>",
                "/"+getParent().getName()+" "+getSubCommandName(), "§fJoin an arena by name or by group.\n§f/bw join random - join random arena."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (args.length < 1) {
            s.sendMessage(getMsg(p, Messages.COMMAND_JOIN_USAGE));
            return true;
        }
        if (args[0].equalsIgnoreCase("random")){
            if (!Arena.joinRandomArena(p)){
                s.sendMessage(getMsg(p, Messages.COMMAND_JOIN_NO_EMPTY_FOUND));
            }
            return true;
        }
        if (com.andrei1058.bedwars.commands.bedwars.MainCommand.isArenaGroup(args[0])) {
            if (!Arena.joinRandomFromGroup(p, args[0])) {
                s.sendMessage(getMsg(p, Messages.COMMAND_JOIN_NO_EMPTY_FOUND));
            }
            return true;
        } else if (Arena.getArenaByName(args[0]) != null) {
            Arena.getArenaByName(args[0]).addPlayer(p, false);
            return true;
        }
        s.sendMessage(getMsg(p, Messages.COMMAND_JOIN_GROUP_OR_ARENA_NOT_FOUND).replace("{name}", args[0]));
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tab = new ArrayList<>(BedWars.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_ARENA_GROUPS));
        for (Arena arena : Arena.getArenas()){
            tab.add(arena.getWorldName());
        }
        return tab;
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
