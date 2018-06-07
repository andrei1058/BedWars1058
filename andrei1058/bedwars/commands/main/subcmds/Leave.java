package com.andrei1058.bedwars.commands.main.subcmds;

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

import static com.andrei1058.bedwars.Main.getServerType;
import static com.andrei1058.bedwars.Main.spigot;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Leave extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Leave(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(20);
        showInList(false);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+MainCommand.getInstance().getName()+" leave", "/"+getParent().getName()+" "+getSubCommandName(), "§fLeave an arena."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null) {
            if (getServerType() == ServerType.MULTIARENA && spigot.getBoolean("settings.bungeecord")){
                Misc.moveToLobbyOrKick(p);
            } else {
                p.sendMessage(getMsg(p, Messages.COMMAND_LEAVE_DENIED_NOT_IN_ARENA));
            }
        } else {
            if (a.isPlayer(p)) {
                a.removePlayer(p);
            } else if (a.isSpectator(p)) {
                a.removeSpectator(p);
            }
        }
        return true;
    }
}
