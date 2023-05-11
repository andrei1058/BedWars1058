package com.tomkeuper.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.tomkeuper.bedwars.api.BedWars;
import com.tomkeuper.bedwars.api.command.ParentCommand;
import com.tomkeuper.bedwars.api.command.SubCommand;
import com.tomkeuper.bedwars.arena.SetupSession;
import com.tomkeuper.bedwars.configuration.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetMinBuildHeight extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     */
    public SetMinBuildHeight(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    /**
     * Add your sub-command code under this method
     *
     * @param args
     * @param s
     */
    @Override
    public boolean execute(String[] args, CommandSender s) {

        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());

        if (ss == null){
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage("§c▪ §7Usage: /" + com.tomkeuper.bedwars.BedWars.mainCmd + " setMinBuildHeight <int>");
        } else {
            try {
                Integer.parseInt(args[0]);
            } catch (Exception ex) {
                p.sendMessage("§c▪ §7Usage: /" + com.tomkeuper.bedwars.BedWars.mainCmd + " setMinBuildHeight <int>");
                return true;
            }
            ss.getConfig().set("min-build-y", Integer.valueOf(args[0]));
            p.sendMessage("§6 ▪ §7Min build height Y set to §e" + args[0] + "§7!");
        }
        return true;
    }

    /**
     * Manage sub-command tab complete
     */
    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (!SetupSession.isInSetupSession(p.getUniqueId())) return false;

        return hasPermission(s);
    }
}
