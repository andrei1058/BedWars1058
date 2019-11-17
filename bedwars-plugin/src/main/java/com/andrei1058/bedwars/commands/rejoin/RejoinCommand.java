package com.andrei1058.bedwars.commands.rejoin;

import com.andrei1058.bedwars.arena.ReJoin;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.configuration.Sounds;
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

        ReJoin rj = ReJoin.getPlayer(p);

        if (rj == null) {
            p.sendMessage(Language.getMsg(p, Messages.REJOIN_NO_ARENA));
            Sounds.playSound("rejoin-denied", p);
            return true;
        }

        if (!rj.canReJoin()) {
            p.sendMessage(Language.getMsg(p, Messages.REJOIN_DENIED));
            Sounds.playSound("rejoin-denied", p);
            return true;
        }

        p.sendMessage(Language.getMsg(p, Messages.REJOIN_ALLOWED).replace("{arena}", rj.getArena().getDisplayName()));
        Sounds.playSound("rejoin-allowed", p);
        rj.reJoin(p);
        return true;
    }
}
