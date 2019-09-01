package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.BedWars;
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

import java.util.List;

import static com.andrei1058.bedwars.Main.config;

public class SetLobby extends SubCommand {

    public SetLobby(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(1);
        showInList(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/"+MainCommand.getInstance().getName()+" "+getSubCommandName()+ (config.getLobbyWorldName().isEmpty() ? " §c(not set)" : " §a(set)"),
                "§aSet the main lobby. §fThis is required but\n§fif you are going to use the server in §eBUNGEE §fmode\n§fthe lobby location will §enot §fbe used.\n§eType again to replace the old spawn location.",
                "/"+getParent().getName()+" "+getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (SetupSession.isInSetupSession(p.getUniqueId())){
            p.sendMessage("§6 ▪ §4This command can't be used in arenas. It is meant for the main lobby!");
            return true;
        }
        config.saveConfigLoc("lobbyLoc", p.getLocation());
        p.sendMessage("§6 ▪ §7Lobby location set!");
        config.reload();
        Main.setLobbyWorld(p.getLocation().getWorld().getName());
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (Arena.isInArena(p)) return false;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;

        if (!Main.getLobbyWorld().isEmpty()) return false;

        return hasPermission(s);
    }
}
