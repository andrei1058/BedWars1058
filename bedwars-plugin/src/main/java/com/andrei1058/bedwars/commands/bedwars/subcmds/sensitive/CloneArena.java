package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.io.FileUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.plugin;

public class CloneArena extends SubCommand {
    public CloneArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(7);
        showInList(true);
        setPermission(Permissions.PERMISSION_CLONE);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName> <newName>", "§fClone an existing arena.",
                "/" + getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.SUGGEST_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 2) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " "+getSubCommandName()+" <mapName> <newArena>");
            return true;
        }
        if (!BedWars.getAPI().getRestoreAdapter().isWorld(args[0])) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        File yml1 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[0] + ".yml"), yml2 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[1] + ".yml");
        if (!yml1.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        if (BedWars.getAPI().getRestoreAdapter().isWorld(args[1]) && yml2.exists()) {
            p.sendMessage("§c▪ §7" + args[1] + " already exist!");
            return true;
        }
        if (Arena.getArenaByName(args[0]) != null){
            p.sendMessage("§c▪ §7Please disable " + args[0] + " first!");
            return true;
        }
        BedWars.getAPI().getRestoreAdapter().cloneArena(args[0], args[1]);
        if (yml1.exists()) {
            try {
                FileUtils.copyFile(yml1, yml2, true);
            } catch (IOException e) {
                e.printStackTrace();
                p.sendMessage("§c▪ §7An error occurred while copying the map's config. Check the console.");
            }
        }
        p.sendMessage("§6 ▪ §7Done :D.");
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
