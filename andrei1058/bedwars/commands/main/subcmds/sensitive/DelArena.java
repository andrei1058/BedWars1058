package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;

public class DelArena extends SubCommand {

    private static HashMap<Player, Long> delArenaConfirm = new HashMap<>();

    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public DelArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(4);
        showInList(true);
        setPermission(Permissions.PERMISSION_DEL_ARENA);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + MainCommand.getInstance().getName() + " "+getSubCommandName()+" §6<worldName>", "§fDelete a map and its configuration.",
                "/" + MainCommand.getInstance().getName() + " "+getSubCommandName(), ClickEvent.Action.SUGGEST_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + MainCommand.getInstance().getName() + " delArena <mapName>");
            return true;
        }
        File ws = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0]);
        if (!ws.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist as a world folder!");
            return true;
        }
        if (getArenaByName(args[0]) != null) {
            p.sendMessage("§c▪ §7Please disable it first!");
            return true;
        }
        File ac = new File("plugins/" + plugin.getName() + "/Arenas/" + args[0]+ ".yml");
        if (!ac.exists()) {
            p.sendMessage("§c▪ §7This arena doesn't exist!");
            return true;
        }
        if (delArenaConfirm.containsKey(p)) {
            if (System.currentTimeMillis() - 2000 <= delArenaConfirm.get(p)) {
                try {
                    FileUtils.deleteDirectory(ws);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileUtils.deleteQuietly(ac);
                p.sendMessage("§c▪ §7" + args[0] + " was deleted!");
                return true;
            }
            p.sendMessage("§6 ▪ §7Type again to confirm.");
            delArenaConfirm.replace(p, System.currentTimeMillis());
        } else {
            p.sendMessage("§6 ▪ §7Type again to confirm.");
            delArenaConfirm.put(p, System.currentTimeMillis());
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tab = new ArrayList<>();
        File dir = new File("plugins/" + plugin.getName() + "/Arenas");
        if (dir.exists()) {
            File[] fls = dir.listFiles();
            for (File fl : Objects.requireNonNull(fls)) {
                if (fl.isFile()) {
                    if (fl.getName().contains(".yml")) {
                        tab.add(fl.getName().replace(".yml", ""));
                    }
                }
            }
        }
        return tab;
    }
}
