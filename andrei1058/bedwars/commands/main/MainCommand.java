package com.andrei1058.bedwars.commands.main;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.subcmds.regular.CmdList;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.*;
import com.andrei1058.bedwars.commands.main.subcmds.regular.*;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.AddGenerator;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.AutoCreateTeams;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.CreateTeam;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.RemoveTeam;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.Save;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetBed;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetMaxInTeam;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetShop;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetSpawn;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetType;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetUpgrade;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.SetWaitingSpawn;
import com.andrei1058.bedwars.commands.main.subcmds.sensitive.setup.WaitingPos;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class MainCommand extends BukkitCommand implements ParentCommand {

    /* SubCommands ArenaList */
    private static List<SubCommand> subCommandList = new ArrayList<>();
    /* MainCommand instance*/
    private static MainCommand instance;
    /* Dot char */
    public static char dot = 254;

    public MainCommand(String name) {
        super(name);
        instance = this;
        new CmdJoin(this, "join");
        new CmdLeave(this, "leave");
        new CmdLang(this, "lang");
        new CmdTeleporter(this, "teleporter");
        if (getServerType() != ServerType.BUNGEE) {
            new CmdGUI(this, "gui");
        }
        new CmdStats(this, "stats");
        new CmdStart(this, "forceStart");
        new CmdStart(this, "start");
        if (Main.getServerType() != ServerType.BUNGEE) {
            new SetLobby(this, "setLobby"); //priority 1
        }
        new SetupArena(this, "setupArena"); //priority 2
        new ArenaList(this, "arenaList"); //priority 3
        new DelArena(this, "delArena"); //priority 4
        new EnableArena(this, "enableArena"); //priority 5
        new DisableArena(this, "disableArena"); //priority 6
        new CloneArena(this, "cloneArena"); //priority 7
        new ArenaGroup(this, "arenaGroup"); //priority 8
        new Build(this, "build"); //priority 9
        new Reload(this, "reload"); //priority 10
        new CmdList(this, "cmds"); //priority 20

        /* Arena setup commands (in world) */
        new AutoCreateTeams(this, "autoCreateTeams");
        new SetWaitingSpawn(this, "setWaitingSpawn");
        new CreateTeam(this, "createTeam");
        new WaitingPos(this, "waitingPos");
        new RemoveTeam(this, "removeTeam");
        new SetMaxInTeam(this, "setMaxInTeam");
        new SetSpawn(this, "setSpawn");
        new SetBed(this, "setBed");
        new SetShop(this, "setShop");
        new SetUpgrade(this, "setUpgrade");
        new AddGenerator(this, "addGenerator");
        new SetType(this, "setType");
        new Save(this, "save");
        if (JoinNPC.isCitizensSupport() && Main.getServerType() != ServerType.BUNGEE) {
            new NPC(this, "npc");
        }
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {

        if (args.length == 0) {
            /* Set op commands*/
            if (s.isOp() && !safeMode) {
                if (s instanceof Player) {
                    if (SetupSession.isInSetupSession((Player) s)) {
                        Bukkit.dispatchCommand(s, getName() + " cmds");
                    } else {
                        s.sendMessage("");
                        s.sendMessage("§8§l" + dot + " §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c Admin Commands");
                        s.sendMessage("");
                        sendSubCommandsToOp((Player) s);
                    }
                } else {
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                }
            } else {
                if (s instanceof ConsoleCommandSender) {
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                    return true;
                }
                /* Send player commands */
                Bukkit.dispatchCommand(s, mainCmd + " cmds");
            }
            return true;
        }

        boolean commandFound = false;
        for (SubCommand sb : getSubCommands()) {
            if (sb.getSubCommandName().equalsIgnoreCase(args[0])) {
                if (sb.hasPermission(s)) {
                    commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                } else {
                    commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                }
            }
        }

        if (!commandFound) {
            if (s instanceof Player) {
                s.sendMessage(getMsg((Player) s, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            } else {
                s.sendMessage(lang.m(Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            }
        }
        return true;
    }

    public static boolean isArenaGroup(String var) {
        if (config.getYml().get("arenaGroups") != null) {
            return config.getYml().getStringList("arenaGroups").contains(var);
        }
        return var.equalsIgnoreCase("default");
    }

    public static TextComponent createTC(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    @Override
    public boolean hasSubCommand(SubCommand subCommand) {
        return subCommandList.contains(subCommand);
    }

    @Override
    public void addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
    }

    @Override
    public void sendSubCommandsToOp(Player p) {
        for (int i = 0; i <= 20; i++) {
            for (SubCommand sb : getSubCommands()) {
                if (sb.getPriority() == i && sb.isShow()) {
                    p.spigot().sendMessage(sb.getDisplayInfo());
                }
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender s, String alias, String[] args, Location location) throws IllegalArgumentException {
        if (args.length == 1) {
            List<String> sub = new ArrayList<>();
            for (SubCommand sb : getSubCommands()) {
                if (sb.canSee(s)) sub.add(sb.getSubCommandName());
            }
            return sub;
        } else if (args.length == 2) {
            if (isSubCommand(args[0])) {
                return getSubCommand(args[0]).getTabComplete();
            }
        }
        return null;
    }


    @Override
    public List<SubCommand> getSubCommands() {
        return subCommandList;
    }

    /**
     * Get instance
     */
    public static MainCommand getInstance() {
        return instance;
    }

    /**
     * Check if lobby location is set, else send a error message to the player
     */
    public static boolean isLobbySet(Player p) {
        if (Main.getServerType() == ServerType.BUNGEE) return true;
        if (config.getLobbyWorldName().isEmpty()) {
            if (p != null) {
                p.sendMessage("§c▪ §7You have to set the lobby location first!");
            }
            return false;
        }
        return true;
    }

    /**
     * Check if is a sub-cmd
     */
    public boolean isSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get sub-command by name
     */
    public SubCommand getSubCommand(String name) {
        for (SubCommand sc : getSubCommands()) {
            if (sc.getSubCommandName().equalsIgnoreCase(name)) {
                return sc;
            }
        }
        return null;
    }

    /** Get sub-command by name */


    /**
     * Get a dot symbol
     */
    public static char getDot() {
        return dot;
    }
}