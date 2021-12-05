package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CmdSpect extends SubCommand {

    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     */
    public CmdSpect(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(21);
        showInList(true);
    }

    /**
     * Add your sub-command code under this method
     *
     * @param args command arguments
     * @param s command executor
     */
    @Override
    public boolean execute(String[] args, CommandSender s) {

        if (!(s instanceof Player)) return true;

        Player player = (Player) s;

        if (args.length != 1) {
            s.sendMessage(Language.getMsg(player, Messages.COMMAND_SPECT_USAGE));
            return true;
        }

        if (Arena.isInArena(player)) return true;

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            s.sendMessage(Language.getMsg(player, Messages.COMMAND_TP_PLAYER_NOT_FOUND));
            return true;
        }

        if (target == player){
            s.sendMessage(Language.getMsg(player, Messages.COMMAND_SPECT_CANNOT_SPECTATE_YOURSELF));
            return true;
        }

        IArena arena = Arena.getArenaByPlayer(target);

        if (arena == null){
            s.sendMessage(Language.getMsg(player, Messages.COMMAND_TP_NOT_IN_ARENA));
            return true;
        }

        if (arena.getStatus() == GameState.playing && arena.addSpectator(player, false, null)){
            Sounds.playSound("spectate-allowed", player);
        } else {
            player.sendMessage(Language.getMsg(player, Messages.ARENA_SPECTATE_DENIED_COMMAND).replace("{arena}", arena.getArenaName()));
            Sounds.playSound("spectate-denied", player);
        }

        return true;
    }

    /**
     * Manage sub-command tab complete
     */
    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
