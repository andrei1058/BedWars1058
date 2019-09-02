package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.plugin;

public class EnableArena extends SubCommand {

    public EnableArena(ParentCommand parent, String name) {
        super(parent, name);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName>","§fEnable an arena.",
                "/" + getParent().getName() + " "+getSubCommandName()+ " ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setPriority(5);
        setPermission(Permissions.PERMISSION_ARENA_ENABLE);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " enableRotation <mapName>");
            return true;
        }
        if (!BedWars.getAPI().getRestoreAdapter().isWorld(args[0])) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }

        for (IArena mm : Arena.getEnableQueue()){
            if (mm.getWorldName().equalsIgnoreCase(args[0])){
                p.sendMessage("§c▪ §7This arena is already in the enable queue!");
                return true;
            }
        }

        Arena aa = Arena.getArenaByName(args[0]);
        if (aa != null) {
            p.sendMessage("§c▪ §7This arena is already enabled!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Enabling arena...");
        new Arena(args[0], p);
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

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;
        return hasPermission(s);
    }
}
