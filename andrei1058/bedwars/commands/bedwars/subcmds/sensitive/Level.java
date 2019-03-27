package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.levels.internal.PlayerLevel;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Level extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Level(ParentCommand parent, String name) {
        super(parent, name);
        setPermission(Permissions.PERMISSION_LEVEL);
        setPriority(10);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " §8      - §eclick for details", "§fManage a player level.",
                "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (args.length == 0) {
            sendSubCommands(s);
            return true;
        }
        if (args[0].equalsIgnoreCase("setlevel")) {
            if (args.length != 3) {
                s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + "Usage: /bw level setLevel §o<player> <level>");
                return true;
            }
            Player pl = Bukkit.getPlayer(args[1]);
            if (pl == null) {
                s.sendMessage(ChatColor.RED + " ▪ " + ChatColor.GRAY + "Player not found!");
                return true;
            }

            int level;

            try {
                level = Integer.parseInt(args[2]);
            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Level must be an integer!");
                return true;
            }

            PlayerLevel pv = PlayerLevel.getLevelByPlayer(pl.getUniqueId());
            if (pv != null) pv.setLevel(level);

            Main.getRemoteDatabase().setLevelData(pl.getUniqueId(), level, 0, null);
            s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + pl.getName() + " level was set to: " + args[2]);
            s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + "The player may need to rejoin to see it updated.");
        } else if (args[0].equalsIgnoreCase("givexp")) {
            if (args.length != 3) {
                s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + "Usage: /bw level giveXp §o<player> <amount>");
                return true;
            }
            Player pl = Bukkit.getPlayer(args[1]);
            if (pl == null) {
                s.sendMessage(ChatColor.RED + " ▪ " + ChatColor.GRAY + "Player not found!");
                return true;
            }

            int amount;

            try {
                amount = Integer.parseInt(args[2]);
            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Amount must be an integer!");
                return true;
            }

            PlayerLevel pv = PlayerLevel.getLevelByPlayer(pl.getUniqueId());
            if (pv != null) pv.setXp(amount);

            Object[] data = Main.getRemoteDatabase().getLevelData(pl.getUniqueId());
            Main.getRemoteDatabase().setLevelData(pl.getUniqueId(), (Integer) data[0], ((Integer)data[1]) + amount, (String) data[2]);
            s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + args[2] + " xp was given to: " + pl.getName());
            s.sendMessage(ChatColor.GOLD + " ▪ " + ChatColor.GRAY + "The player may need to rejoin to see it updated.");
        } else {
            sendSubCommands(s);
        }
        return true;
    }

    private void sendSubCommands(CommandSender s) {
        if (s instanceof Player) {
            Player p = (Player) s;
            p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " setLevel §o<player> <level>",
                    "Set a player level.", "/" + getParent().getName() + " " + getSubCommandName() + " setLevel",
                    ClickEvent.Action.SUGGEST_COMMAND));
            p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " giveXp §o<player> <amount>",
                    "Give Xp to a player.", "/" + getParent().getName() + " " + getSubCommandName() + " giveXp",
                    ClickEvent.Action.SUGGEST_COMMAND));
        } else {
            s.sendMessage(ChatColor.GOLD + "bw level setLevel <player> <level>");
            s.sendMessage(ChatColor.GOLD + "bw level giveXp <player> <amount>");
        }
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
