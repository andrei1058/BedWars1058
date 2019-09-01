package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.Main.mainCmd;

public class CreateTeam extends SubCommand {

    public CreateTeam(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (args.length < 2) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " createTeam §o<name> §o<color>");
            StringBuilder colors = new StringBuilder("§7");
            for (TeamColor t : TeamColor.values()) {
                colors.append(TeamColor.getChatColor(t)).append(t.toString()).append("§7, ");
            }
            colors = new StringBuilder(colors.toString().substring(0, colors.toString().length() - 2) + "§7.");
            p.sendMessage("§6 ▪ §7Available colors: " + colors);
        } else {
            boolean y = true;
            for (TeamColor t : TeamColor.values()) {
                if (t.toString().equalsIgnoreCase(args[1])) {
                    y = false;
                }
            }
            if (y) {
                p.sendMessage("§c▪ §7Invalid color!");
                StringBuilder colors = new StringBuilder("§7");
                for (TeamColor t : TeamColor.values()) {
                    colors.append(TeamColor.getChatColor(t)).append(t.toString()).append("§7, ");
                }
                colors = new StringBuilder(colors.toString().substring(0, colors.toString().length() - 2) + "§7.");
                p.sendMessage("§6 ▪ §7Available colors: " + colors);
            } else {
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Color") != null) {
                    p.sendMessage("§c▪ §7" + args[0] + " team already exists!");
                    return true;
                }
                ss.getConfig().set("Team." + args[0] + ".Color", args[1].toUpperCase());
                p.sendMessage("§6 ▪ §7" + TeamColor.getChatColor(args[1]) + args[0] + " §7created!");
                if (ss.getSetupType() == SetupType.ASSISTED) {
                    ss.getConfig().reload();
                    int teams = ss.getConfig().getYml().getConfigurationSection("Team").getKeys(false).size();
                    int max = 1;
                    if (teams == 4) {
                        max = 2;
                    }
                    ss.getConfig().set("maxInTeam", max);
                }
            }
        }
        return true;
    }

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
