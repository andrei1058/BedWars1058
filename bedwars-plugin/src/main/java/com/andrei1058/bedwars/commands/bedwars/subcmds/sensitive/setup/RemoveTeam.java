package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.commands.Misc.removeArmorStand;

public class RemoveTeam extends SubCommand {

    public RemoveTeam(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss == null){
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (args.length < 1) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " removeTeam §o<teamName>");
            if (ss.getConfig().getYml().get("Team") != null) {
                p.sendMessage("§6 ▪ §7Available teams: ");
                for (String team : Objects.requireNonNull(ss.getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ " + TeamColor.getChatColor(team) + team,
                             "§7Remove " + TeamColor.getChatColor(team) + team+" §7(click to remove)", "/" + mainCmd + " removeTeam " + team, ClickEvent.Action.RUN_COMMAND));
                }
            }
        } else {
            if (ss.getConfig().getYml().get("Team." + args[0] + ".Color") == null) {
                p.sendMessage("§c▪ §7This team doesn't exist!");
            } else {
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Iron") != null) {
                    removeArmorStand("Generator", ss.getConfig().getArenaLoc("Team." + args[0] + ".Iron"));
                }
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Gold") != null) {
                    removeArmorStand("Generator", ss.getConfig().getArenaLoc("Team." + args[0] + ".Gold"));
                }
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Bed") != null) {
                    removeArmorStand("Generator", ss.getConfig().getArenaLoc("Team." + args[0] + ".Bed"));
                }
                ss.getConfig().set("Team." + args[0], null);
                p.sendMessage("§6 ▪ §7Team removed!");
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
