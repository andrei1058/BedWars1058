package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.commands.Misc.removeArmorStand;

public class RemoveTeam extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public RemoveTeam(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setOpCommand(true);
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
        if (args.length < 1) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " removeTeam §o<teamName>");
            if (ss.getCm().getYml().get("Team") != null) {
                p.sendMessage("§6 ▪ §7Available teams: ");
                for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ " + TeamColor.getChatColor(team) + team,
                             "§7Remove " + TeamColor.getChatColor(team) + team+" §7(click to remove)", "/" + mainCmd + " removeTeam " + team, ClickEvent.Action.RUN_COMMAND));
                }
            }
        } else {
            if (ss.getCm().getYml().get("Team." + args[0] + ".Color") == null) {
                p.sendMessage("§c▪ §7This team doesn't exist!");
            } else {
                if (ss.getCm().getArenaLoc("Team." + args[0] + ".Iron") != null) {
                    removeArmorStand("Generator", ss.getCm().getArenaLoc("Team." + args[0] + ".Iron"));
                }
                if (ss.getCm().getArenaLoc("Team." + args[0] + ".Gold") != null) {
                    removeArmorStand("Generator", ss.getCm().getArenaLoc("Team." + args[0] + ".Gold"));
                }
                if (ss.getCm().getArenaLoc("Team." + args[0] + ".Bed") != null) {
                    removeArmorStand("Generator", ss.getCm().getArenaLoc("Team." + args[0] + ".Bed"));
                }
                ss.getCm().set("Team." + args[0], null);
                p.sendMessage("§6 ▪ §7Team removed!");
            }
        }
        return true;
    }
}
