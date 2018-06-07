package com.andrei1058.bedwars.commands.main.subcmds;

import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import com.andrei1058.bedwars.configuration.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.link;
import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.configuration.Language.getList;

public class Cmds extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Cmds(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(20);
        showInList(true);
        setDisplayInfo(MainCommand.createTC("§6 ▪ §7/"+MainCommand.getInstance().getName()+" "+getSubCommandName(), "/"+getParent().getName()+" "+getSubCommandName(), "§fView player commands."));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        TextComponent credits = new TextComponent("§8§l▐ §6" + plugin.getName() + " §7v" + plugin.getDescription().getVersion() + " by andrei1058");
        credits.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        credits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Arenas: " + (Arena.getArenas().size() == 0 ? "§c0" : "§a" + Arena.getArenas().size())).create()));
        ((Player)s).spigot().sendMessage(credits);
        for (String string : getList((Player) s, Messages.COMMAND_MAIN)) {
            s.sendMessage(string);
        }
        return true;
    }
}
