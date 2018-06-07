package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;

public class List extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public List(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(3);
        showInList(true);
        setOpCommand(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + MainCommand.getInstance().getName() + " "+getSubCommandName() + ((getArenas().size() == 0) ? "§c§o(0 set)" : "§a§o("+getArenas().size()+" set)"),
                "§fShow available arenas", "/" + MainCommand.getInstance().getName() + " "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (getArenas().size() == 0) {
            p.sendMessage("§6 ▪ §7You didn't set any arena yet!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Available arenas:");
        for (String arena : getArenas()) {
            String status = getArenaByName(arena) == null ? "§cDisabled" : "§aEnabled";
            p.sendMessage("§6 ▪    §f" + arena + " §7[" + status + "§7]");
        }
        return true;
    }

    private java.util.List<String> getArenas() {
        ArrayList<String> arene = new ArrayList<>();
        File dir = new File("plugins/" + plugin.getDescription().getName() + "/Arenas");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    if (f.getName().contains(".yml")) {
                        arene.add(f.getName().replace(".yml", ""));
                    }
                }
            }
        }
        return arene;
    }
}
