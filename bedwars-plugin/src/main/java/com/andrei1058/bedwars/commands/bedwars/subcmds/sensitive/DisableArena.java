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
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DisableArena extends SubCommand {

    public DisableArena(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(6);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName>", "§fDisable an arena.\nThis will remove the players \n§ffrom the arena before disabling.",
                "/" + getParent().getName() + " "+getSubCommandName()+" ", ClickEvent.Action.SUGGEST_COMMAND));
        setPermission(Permissions.PERMISSION_ARENA_DISABLE);
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
        if (!BedWars.getAPI().getRestoreAdapter().isWorld(args[0])) {
            p.sendMessage("§c▪ §7" + args[0] + " doesn't exist!");
            return true;
        }
        Arena a = Arena.getArenaByName(args[0]);
        if (a == null) {
            p.sendMessage("§c▪ §7This arena is disabled yet!");
            return true;
        }
        p.sendMessage("§6 ▪ §7Disabling arena...");
        a.disable();
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tab = new ArrayList<>();
        for (Arena a : Arena.getArenas()){
            tab.add(a.getWorldName());
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
