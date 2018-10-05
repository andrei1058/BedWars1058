package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Reload extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Reload(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(10);
        showInList(true);
        setPermission(Permissions.PERMISSION_RELOAD);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+"       §8 - §ereload messages",
                "§fRealod messages.\n§cNot recommended!", "/"+ getParent().getName() + " "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (!MainCommand.isLobbySet(p)) return true;
        for (com.andrei1058.bedwars.configuration.Language l : Language.getLanguages()){
            l.reload();
            p.sendMessage("§6 ▪ §7"+l.getLangName()+" reloaded!");
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
