package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.config;
import static com.andrei1058.bedwars.Main.plugin;

public class SetLobby extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public SetLobby(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(1);
        showInList(true);
        setOpCommand(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/"+MainCommand.getInstance().getName()+" "+getSubCommandName()+ (config.getLobbyWorldName().isEmpty() ? " §c(not set)" : " §a(set)"),
                "§aSet the main lobby. §fThis is required but\n§fif you are going to use the server in §eBUNGEE §fmode\n§fthe lobby location will §enot §fbe used.\n§eType again to replace the old spawn location.",
                "/"+getParent().getName()+" "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (config.getLobbyWorldName().isEmpty()){
            p.sendMessage("§6 ▪ §aAs this is the first time you set the lobby. The server needs a restart.");
            Bukkit.getScheduler().runTaskLater(plugin, ()-> plugin.getServer().spigot().restart(), 40L);
        }
        config.saveConfigLoc("lobbyLoc", p.getLocation());
        p.sendMessage("§6 ▪ §7Lobby location set!");
        return true;
    }
}
