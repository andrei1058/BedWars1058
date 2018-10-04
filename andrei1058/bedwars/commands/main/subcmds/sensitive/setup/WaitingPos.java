package com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.mainCmd;

public class WaitingPos extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public WaitingPos(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
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
        if (args.length == 0) {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " "+getSubCommandName()+" 1 or 2");
        } else {
            if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2")) {
                p.sendMessage("§6 ▪ §7Pos " + args[0] + " set!");
                ss.getCm().saveArenaLoc("waiting.Pos" + args[0], p.getLocation());
                ss.getCm().reload();
                if (ss.getCm().getYml().get("waiting.Pos1") == null){
                    p.sendMessage("§c ▪ §7Set the remaining position:");
                    p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/"+Main.mainCmd+" waitingPos 1", "§dSet pos 1", "/"+getParent().getName()+" waitingPos 1", ClickEvent.Action.RUN_COMMAND));
                } else if (ss.getCm().getYml().get("waiting.Pos2") == null){
                    p.sendMessage("§c ▪ §7Set the remaining position:");
                    p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/"+Main.mainCmd+" waitingPos 2", "§dSet pos 2", "/"+getParent().getName()+" waitingPos 2", ClickEvent.Action.RUN_COMMAND));
                }
            } else {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " "+getSubCommandName()+" 1 or 2");
            }
        }
        if (!((ss.getCm().getYml().get("waiting.Pos1") == null || ss.getCm().getYml().get("waiting.Pos2") == null))){
            Bukkit.dispatchCommand(p, Main.mainCmd+" cmds");
            s.sendMessage("§6 ▪ §7Set teams spawn if you didn't!");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("1", "2");
    }
}
