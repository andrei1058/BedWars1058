package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetWaitingSpawn extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public SetWaitingSpawn(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        setArenaSetupCommand(true);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p);
        if (ss == null){
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Waiting spawn set for §e"+ss.getWorldName()+"§7!");
        ss.getCm().saveArenaLoc("waiting.Loc", p.getLocation());
        if (ss.getSetupType() == SetupSession.SetupType.ASSISTED){
            Bukkit.dispatchCommand(s, Main.mainCmd+" autocreateteams");
        } else {
            ss.sendCommands();
        }
        return true;
    }
}
