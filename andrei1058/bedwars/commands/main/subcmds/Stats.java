package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Stats extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Stats(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(16);
        showInList(true);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+MainCommand.getInstance().getName()+" "+getSubCommandName(), "/"+getParent().getName()+" "+getSubCommandName(), "§fOpens the stats GUI."));
    }

    private static HashMap<Player, Long> statsCoolDown = new HashMap<>();

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (statsCoolDown.containsKey(p)){
            if (System.currentTimeMillis() - 3000 >= statsCoolDown.get(p)) {
                statsCoolDown.replace(p, System.currentTimeMillis());
            } else {
                //wait 3 seconds
                return true;
            }
        } else {
            statsCoolDown.put(p, System.currentTimeMillis());
        }
        Misc.openStatsGUI(p);
        return true;
    }
}
