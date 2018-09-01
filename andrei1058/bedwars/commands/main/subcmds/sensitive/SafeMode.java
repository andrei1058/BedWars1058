package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.safeMode;
import static com.andrei1058.bedwars.listeners.BreakPlace.getBuildSession;

public class SafeMode extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true
     *
     * @param parent
     * @param name
     * @since 0.6.1 api v6
     */
    public SafeMode(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(0);
        setOpCommand(true);
        showInList(true);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+MainCommand.getInstance().getName()+" safeMode §6<enable/ disable>",
                "/"+getParent().getName()+" "+getSubCommandName(), "§fIf enabled the plugin will disable sensitive commands.\n" +
                "§fSo your staff won't be able to modify arenas.\n§fEnabled: §c"+safeMode));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (args.length == 0){
            sendUsage(s);
            return true;
        }
        if (args[0].equalsIgnoreCase("enable")){
            if (safeMode) {
                s.sendMessage("§6 ▪ §7Safe mode is already turned on!");
                return true;
            }
            safeMode = true;
            getBuildSession().clear();
            config.set("safeMode", true);
            s.sendMessage("§6 ▪ §aSafeMode activated!");
            s.sendMessage("§6 ▪ §7Build sessions were cleared.");
            s.sendMessage("§6 ▪ §7Remember: you'll need to disable the safe mode when you'll add a new arena.");
        } else if (args[0].equalsIgnoreCase("disable")){
            if (!safeMode) {
                s.sendMessage("§6 ▪ §7Safe mode is already disabled!");
                return true;
            }
            if (s instanceof Player){
                s.sendMessage("§6 ▪ §cYou can disable the safe mode from §lconsole§r§c only!");
                return true;
            }
            safeMode = false;
            config.set("safeMode", false);
            getBuildSession().clear();
            s.sendMessage("§6 ▪ §7Safe mode is now disabled!");
            s.sendMessage("§6 ▪ §7Build sessions were cleared.");
            s.sendMessage("§6 ▪ §7OP players are now able to use sensitive commands.");
        } else {
            sendUsage(s);
        }
        return true;
    }

    private static void sendUsage(CommandSender s){
        s.sendMessage(" ");
        s.sendMessage("§e§lCommand usage: §fbw enabled§7/§f disable");
        s.sendMessage("§a    enable §f- if you want to secure bedwars commands.");
        s.sendMessage("§c    disable §f- if you want your staff to do the setup.");
    }
}
