package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;

import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;

public class SetupArena extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public SetupArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(2);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + MainCommand.getInstance().getName() + " setupArena §e<mapName>", "§fCreate or edit an arena.\n'_' and '-' will not be displayed in the arena's name.",
                "/" + MainCommand.getInstance().getName() + " setupArena ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setOpCommand(true);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " "+getSubCommandName()+" <mapName>");
            return true;
        }
        File worldServer = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0]);
        if (!worldServer.exists()) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        if (getArenaByName(args[0]) != null) {
            p.sendMessage("§c▪ §7Please disable it first!");
            return true;
        }
        World w = null;
        try {
            w = Bukkit.createWorld(new WorldCreator(args[0]));
        } catch (Exception ex) {
            File uid = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[0] + "/uid.dat");
            uid.delete();
            try {
                w = Bukkit.createWorld(new WorldCreator(args[0]));
            } catch (Exception exx) {
            }
        }
        if (w == null) {
            p.sendMessage("§c▪ §7There was an error while loading the map :(\n§c▪ §7Please delete uid.dat from " + args[0] + "'s folder.");
            return true;
        }
        Bukkit.getScheduler().runTaskLater(plugin, ()-> {
            Bukkit.getWorld(args[0]).getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove);
        }, 30L);
        w.setAutoSave(true);
        w.setGameRuleValue("doMobSpawning", "false");
        p.teleport(w.getSpawnLocation());
        p.setGameMode(GameMode.CREATIVE);
        p.setFlying(true);
        p.sendMessage("§6 ▪ §7You were teleported to the " + args[0] + "'s spawn.");
        MainCommand.sendWorldSetupCommands(p, args[0]);
        return true;
    }
}
