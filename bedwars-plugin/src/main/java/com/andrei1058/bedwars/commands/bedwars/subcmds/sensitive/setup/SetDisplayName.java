package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.BedWars.mainCmd;

public class SetDisplayName extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     */
    public SetDisplayName(ParentCommand parent, String name) {
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

        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }

        if (args.length == 0) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " setDisplayName <name>");
            return true;
        }

        // Combine arguments into a single string with spaces
        String displayName = String.join(" ", args[0]);

        ss.getConfig().set("display-name", displayName);
        p.sendMessage("§6 ▪ §7Display name set to §e" + displayName + "§7!");
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