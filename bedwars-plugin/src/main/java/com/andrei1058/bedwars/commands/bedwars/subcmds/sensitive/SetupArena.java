package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.arena.Arena.getArenaByName;

public class SetupArena extends SubCommand {

    public SetupArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(2);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + com.andrei1058.bedwars.commands.bedwars.MainCommand.getInstance().getName() + " setupArena §6<worldName>", "§fCreate or edit an arena.\n'_' and '-' will not be displayed in the arena's name.",
                "/" + com.andrei1058.bedwars.commands.bedwars.MainCommand.getInstance().getName() + " setupArena ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        if (args.length != 1) {
            p.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " " + getSubCommandName() + " <mapName>");
            return true;
        }
        if (!args[0].equals(args[0].toLowerCase())) {
            p.sendMessage("§c▪ §c" + args[0] + ChatColor.GRAY + " mustn't contain capital letters! Rename your folder to: " + ChatColor.GREEN + args[0].toLowerCase());
            return true;
        }
        if (!BedWars.getAPI().getRestoreAdapter().isWorld(args[0])) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        if (getArenaByName(args[0]) != null) {
            p.sendMessage("§c▪ §7Please disable it first!");
            return true;
        }
        if (SetupSession.isInSetupSession(p.getUniqueId())) {
            p.sendMessage("§c ▪ §7You're already in a setup session!");
            return true;
        }
        new SetupSession(p, args[0]);
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return BedWars.getAPI().getRestoreAdapter().getWorldsList();
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
