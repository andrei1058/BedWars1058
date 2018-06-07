package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class DisableArena extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public DisableArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(6);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName>", "§fDisable an arena.\nThis will remove the players from the arena before disabling.",
                "/" + getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.SUGGEST_COMMAND));
        setOpCommand(true);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " "+getSubCommandName()+" <mapName>");
            return true;
        }
        File wss = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0]);
        if (!wss.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        Arena a = Arena.getArenaByName(args[0]);
        if (a == null) {
            p.sendMessage("§c▪ §7This arena is disabled yet!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Disabling arena...");
        a.disable();
        return true;
    }
}
