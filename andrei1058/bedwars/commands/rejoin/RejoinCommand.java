package com.andrei1058.bedwars.commands.rejoin;

import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class RejoinCommand extends BukkitCommand {

    public RejoinCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {
        if (s instanceof ConsoleCommandSender) {
            s.sendMessage("This command is for players!");
            return true;
        }

        Player p = (Player) s;

        if (!p.hasPermission(Permissions.PERMISSION_REJOIN)) {
            p.sendMessage(Language.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            return true;
        }

        if (!ReJoin.exists(p)) {
            p.sendMessage(Language.getMsg(p, Messages.REJOIN_NO_ARENA));
            return true;
        }

        if (!ReJoin.getPlayer(p).canReJoin()) {
            p.sendMessage(Language.getMsg(p, Messages.REJOIN_DENIED));
            return true;
        }

        p.sendMessage(Language.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", ReJoin.getPlayer(p).getArena().getDisplayName()));
        ReJoin.getPlayer(p).reJoin(p);
        return true;
    }
}
