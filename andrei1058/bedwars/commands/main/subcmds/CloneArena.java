package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.andrei1058.bedwars.Main.plugin;

public class CloneArena extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public CloneArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(7);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName> <newName>", "§fClone an existing arena.",
                "/" + getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.SUGGEST_COMMAND));
        setOpCommand(true);
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
        File mapa = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0]);
        if (!mapa.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        File yml1 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[0] + ".yml"), yml2 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[1] + ".yml");
        if (!yml1.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        File mapa2 = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
        if (mapa2.exists() && yml2.exists()) {
            p.sendMessage("§c▪ §7" + args[1] + " already exist!");
            return true;
        }
        if (Arena.getArenaByName(args[0]) != null){
            p.sendMessage("§c▪ §7Please disable " + args[0] + " first!");
            return true;
        }
        try {
            FileUtils.copyDirectory(mapa, mapa2);
            if (!new File(mapa2.getPath() + "/uid.dat").delete()) {
                p.sendMessage("§c▪ §7An error occurred. Please delete uid.dat from " + args[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            p.sendMessage("§c▪ §7An error occurred while copying the map. Check the console.");
        }
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
}
