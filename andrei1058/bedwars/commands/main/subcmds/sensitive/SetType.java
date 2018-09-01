package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetType extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public SetType(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        setArenaSetupCommand(true);
    }

    private static List<String> available = Arrays.asList("solo", "duals", "3v3v3v3", "4v4v4v4");

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p);
        if (ss == null){
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (args.length == 0){
           sendUsage(p);
        } else {
            if (!available.contains(args[0].toLowerCase())){
                sendUsage(p);
                return true;
            }
            List groups = Main.plugin.config.getYml().getStringList("arenaGroups");
            String input = args[0].substring(0, 1).toUpperCase()+args[0].substring(1, args[0].length()).toLowerCase();
            if (!groups.contains(input)){
                groups.add(input);
                Main.config.set("arenaGroups", groups);
                int maxInTeam = 1;
                if (input.equalsIgnoreCase("duals")){
                    maxInTeam = 2;
                } else if (input.equalsIgnoreCase("3v3v3v3")){
                    maxInTeam = 3;
                } else if (input.equalsIgnoreCase("4v4v4v4")){
                    maxInTeam = 4;
                }
                ss.getCm().set("maxInTeam", maxInTeam);
            }
            ss.getCm().set("group", input);
            p.sendMessage("§6 ▪ §7Arena group changed to: §d"+input);
            if (ss.getSetupType() == SetupSession.SetupType.ASSISTED){
                Bukkit.dispatchCommand(p, getParent().getName());
            }
        }
        return true;
    }

    private void sendUsage(Player p){
        p.sendMessage("§9 ▪ §7Usage: "+getParent().getName()+" "+getSubCommandName()+" <type>");
        p.sendMessage("§9Available types: ");
        for (String st : available){
            p.spigot().sendMessage(Misc.msgHoverClick("§1 ▪ §e"+st+" §7(click to set)", "§dClick to make the arena "+st, "/"+getParent().getName()+" "+getSubCommandName()+" "+st, ClickEvent.Action.RUN_COMMAND));
        }
    }
}
