package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.getLangSupport;
import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class CmdLang extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public CmdLang(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(18);
        showInList(false);
        setDisplayInfo(com.andrei1058.bedwars.commands.bedwars.MainCommand.createTC("§6 ▪ §7/"+ MainCommand.getInstance().getName()+" "+getSubCommandName(), "/"+getParent().getName()+" "+getSubCommandName(), "§fChange your language."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (Arena.getArenaByPlayer(p) != null) return false;
        if (args.length == 0) {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_HEADER));
            for (com.andrei1058.bedwars.configuration.Language l : com.andrei1058.bedwars.configuration.Language.getLanguages()) {
                p.sendMessage(getMsg(p, Messages.COMMAND_LANG_LIST_FORMAT).replace("{iso}", l.getIso()).replace("{name}", l.getLangName()));
            }
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE));
            return true;
        } else if (com.andrei1058.bedwars.configuration.Language.isLanguageExist(args[0])) {
            if (Arena.getArenaByPlayer(p) == null) {
                getLangSupport().setLang(p, args[0]);
                Bukkit.getScheduler().runTaskLater(plugin, () -> p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_SUCCESSFULLY)), 10L);
            } else {
                p.sendMessage(getMsg(p, Messages.COMMAND_LANG_USAGE_DENIED));
            }
        } else {
            p.sendMessage(getMsg(p, Messages.COMMAND_LANG_SELECTED_NOT_EXIST));
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> tab = new ArrayList<>();
        for (Language lang : Language.getLanguages()){
            tab.add(lang.getIso());
        }
        return null;
    }
}
