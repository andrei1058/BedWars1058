package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

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

public class EnableArena extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public EnableArena(ParentCommand parent, String name) {
        super(parent, name);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName>","§fEnable an arena.",
                "/" + getParent().getName() + " "+getSubCommandName()+ " ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setOpCommand(true);
        setPriority(5);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " enable <mapName>");
            return true;
        }
        File wss2 = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0]);
        if (!wss2.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        Arena aa = Arena.getArenaByName(args[0]);
        if (aa != null) {
            p.sendMessage("§c▪ §7This arena is already enabled!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Enabling arena...");
        new Arena(args[0], p);
        return true;
    }
}
