package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdUpgrades extends SubCommand {

    public CmdUpgrades(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (!(s instanceof Player)) return false;
        IArena a = Arena.getArenaByPlayer((Player) s);
        if (a == null) return false;
        if (!a.isPlayer((Player) s)) return false;
        ITeam t = a.getTeam((Player) s);
        if (t.getTeamUpgrades().distance(((Player)s).getLocation()) < 4){
            UpgradesManager.getMenuForArena(a).open((Player) s);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getTabComplete() {
        return new ArrayList<>();
    }
}
