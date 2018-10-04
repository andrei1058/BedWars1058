package com.andrei1058.bedwars.commands.main.subcmds.regular;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.Main.getServerType;
import static com.andrei1058.bedwars.Main.spigot;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class CmdLeave extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public CmdLeave(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(20);
        showInList(false);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+MainCommand.getInstance().getName()+" leave", "/"+getParent().getName()+" "+getSubCommandName(), "§fLeave an arena."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (Main.getServerType() == ServerType.BUNGEE){
            Misc.forceKick(p);
            return true;
        }
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) {
            Misc.forceKick(p);
        } else {
            if (a.isPlayer(p)) {
                a.removePlayer(p, false);
            } else if (a.isSpectator(p)) {
                a.removeSpectator(p, false);
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
