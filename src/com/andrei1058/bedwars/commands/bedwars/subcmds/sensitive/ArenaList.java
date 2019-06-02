package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.ConfigManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;

public class ArenaList extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public ArenaList(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(3);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + com.andrei1058.bedwars.commands.bedwars.MainCommand.getInstance().getName() + " "+getSubCommandName() + ((getArenas().size() == 0) ? " §c(0 set)" : " §a("+getArenas().size()+" set)"),
                "§fShow available arenas", "/" + MainCommand.getInstance().getName() + " "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (getArenas().size() == 0) {
            p.sendMessage("§6 ▪ §7You didn't set any arena yet!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Available arenas:");
        for (String arena : getArenas()) {
            String status = getArenaByName(arena) == null ? "§cDisabled" : "§aEnabled";
            String group = "Default";
            ConfigManager cm = new ConfigManager(arena, "plugins/"+plugin.getName()+"/Arenas", true);
            if (cm.getYml().get("group") != null){
                group = cm.getYml().getString("group");
            }
            int teams = 0;
            if (cm.getYml().get("Team") != null){
                teams = cm.getYml().getConfigurationSection("Team").getKeys(false).size();
            }
            p.sendMessage("§6 ▪    §f" + arena + " §7[" + status + "§7] [§eGroup: §d"+group+"§7] [§eTeams: §d"+teams+"§7]");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    private java.util.List<String> getArenas() {
        ArrayList<String> arene = new ArrayList<>();
        File dir = new File("plugins/" + plugin.getDescription().getName() + "/Arenas");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    if (f.getName().contains(".yml")) {
                        arene.add(f.getName().replace(".yml", ""));
                    }
                }
            }
        }
        return arene;
    }

    @Override
    public boolean canSee(CommandSender s) {

        if (s instanceof Player) {
            Player p = (Player) s;
            if (Arena.isInArena(p)) return false;

            if (SetupSession.isInSetupSession(p)) return false;
        }

        return hasPermission(s);
    }
}
