package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class CmdStats extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public CmdStats(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(16);
        showInList(false);
        setDisplayInfo(com.andrei1058.bedwars.commands.bedwars.MainCommand.createTC("§6 ▪ §7/"+ MainCommand.getInstance().getName()+" "+getSubCommandName(), "/"+getParent().getName()+" "+getSubCommandName(), "§fOpens the stats GUI."));
    }

    private static HashMap<Player, Long> statsCoolDown = new HashMap<>();

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        Arena a = Arena.getArenaByPlayer(p);
        if (a != null){
            if (!(a.getStatus() == GameState.starting || a.getStatus() == GameState.waiting)){
                if (!a.isSpectator(p)){
                    return false;
                }
            }
        }
        if (statsCoolDown.containsKey(p)){
            if (System.currentTimeMillis() - 3000 >= statsCoolDown.get(p)) {
                statsCoolDown.replace(p, System.currentTimeMillis());
            } else {
                //wait 3 seconds
                return true;
            }
        } else {
            statsCoolDown.put(p, System.currentTimeMillis());
        }
        Misc.openStatsGUI(p);
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }


    @Override
    public boolean canSee(CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p)) return false;
        return hasPermission(s);
    }
}
