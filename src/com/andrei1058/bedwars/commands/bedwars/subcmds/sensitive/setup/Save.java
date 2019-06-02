package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

import static com.andrei1058.bedwars.Main.plugin;

public class Save extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Save(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p);
        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        /* Clear setup armorstands */
        for (Entity e : p.getWorld().getEntities()){
            if (e.getType() == EntityType.ARMOR_STAND){
                e.remove();
            }
        }

        Bukkit.getScheduler().runTaskLater(plugin, ()-> {
            p.getWorld().save();
            Bukkit.getScheduler().runTaskLater(plugin, () -> Bukkit.unloadWorld(Bukkit.getWorld(p.getWorld().getName()), true), 30L);
            p.sendMessage("§6 ▪ §7Arena changes saved!");
            p.sendMessage("§6 ▪ §7You can now enable it using:");
            p.spigot().sendMessage(Misc.msgHoverClick("§6/" + getParent().getName() + " enableArena " + ss.getWorldName() + "§7 (click to enable)", "§dEnable this arena.", "/" + getParent().getName() + " enableArena " + ss.getWorldName(), ClickEvent.Action.RUN_COMMAND));
            ss.done();
        }, 40L);
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
        if (!SetupSession.isInSetupSession(p)) return false;

        return hasPermission(s);
    }
}
