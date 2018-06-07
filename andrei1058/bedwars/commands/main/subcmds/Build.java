package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.listeners.BreakPlace.addBuildSession;
import static com.andrei1058.bedwars.listeners.BreakPlace.isBuildSession;
import static com.andrei1058.bedwars.listeners.BreakPlace.removeBuildSession;

public class Build extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Build(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        setPriority(9);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName(), "§fEnable or disable build session so you can break or place blocks.",
                "/" + getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (isBuildSession(p)) {
            p.sendMessage("§6 ▪ §7You can't place and break blocks anymore;");
            removeBuildSession(p);
        } else {
            p.sendMessage("§6 ▪ §7You can place and break blocks now.");
            addBuildSession(p);
        }
        return true;
    }
}
