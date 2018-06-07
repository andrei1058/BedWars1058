package com.andrei1058.bedwars.commands.main;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.subcmds.*;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Messages;
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
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class MainCommand extends BukkitCommand implements ParentCommand {

    /* SubCommands List */
    private static List<SubCommand> subCommandList = new ArrayList<>();
    /* MainCommand instance*/
    private static MainCommand instance;

    public MainCommand(String name) {
        super(name);
        instance = this;
        new SafeMode(this, "safemode"); //priority 0
        new Join(this, "join");
        new Leave(this, "leave");
        new com.andrei1058.bedwars.commands.main.subcmds.Language(this, "lang");
        if (getServerType() != ServerType.BUNGEE){
            new CmdGUI(this, "gui");
        }
        new Stats(this, "stats");
        new Forcestart(this, "forcestart");
        new SetLobby(this, "setLobby"); //priority 1
        new SetupArena(this, "setupArena"); //priority 2
        new com.andrei1058.bedwars.commands.main.subcmds.List(this, "list"); //priority 3
        new DelArena(this, "delArena"); //priority 4
        new EnableArena(this, "enableArena"); //priority 5
        new DisableArena(this, "disableArena"); //priority 6
        new CloneArena(this, "cloneArena"); //priority 7
        new ArenaGroup(this, "arenaGroup"); //priority 8
        new Build(this, "build"); //priority 9
        new Reload(this, "reload"); //priority 10
        new Cmds(this, "cmds"); //priority 20
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {

        if (args.length == 0){
            if (s.isOp() && !safeMode) {
                s.sendMessage("");
                s.sendMessage("§8§l▐ §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c General Commands");
                s.sendMessage("");
                if (s instanceof Player) {
                    sendSubCommandsToOp((Player) s);
                } else {
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                }
            } else {
                if (s instanceof ConsoleCommandSender){
                    s.sendMessage("§f   bw safemode §eenable/ disable");
                    return true;
                }
                Bukkit.dispatchCommand(s, "/"+mainCmd+" cmds");
            }
            return true;
        }
        boolean commandFound = false;
        for (SubCommand sb : getSubCommands()){
            if (sb.getSubCommandName().equalsIgnoreCase(args[0])){
                if (sb.isOpCommand() && !sb.isArenaSetupCommand()){
                    if (s.isOp()){
                        commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                    }
                } else if (sb.isArenaSetupCommand()){
                    if (s.isOp()){
                        commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                    }
                } else {
                    commandFound = sb.execute(Arrays.copyOfRange(args, 1, args.length), s);
                }
            }
        }

        if (!commandFound){
             if (s instanceof Player) s.sendMessage(getMsg((Player) s, Messages.COMMAND_NOT_FOUND_OR_REQUIRES_SAFEMODE_OFF));
             else s.sendMessage(lang.m(Messages.COMMAND_NOT_FOUND_OR_REQUIRES_SAFEMODE_OFF));
        }

        ////////////////////////

        Player p = (Player) s;
        if (args.length == 0) {
            if (p.isOp() && !safeMode) {
                if (isArenaSetup(p)) {
                    sendWorldSetupCommands(p, p.getWorld().getName());
                    return true;
                }
            } else {
                TextComponent credits = new TextComponent("§8§l▐ §b" + plugin.getName() + " §7v" + plugin.getDescription().getVersion() + " by andrei1058");
                credits.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                credits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7SafeMode: " + (safeMode ? "§aEnabled" : "§cDisabled") + "\n§7Arenas: " + (Arena.getArenas().size() == 0 ?
                        "§c0" : "§a" + Arena.getArenas().size())).create()));
                p.spigot().sendMessage(credits);
                for (String string : getList(p, Messages.COMMAND_MAIN)) {
                    p.sendMessage(string);
                }
            }
            return true;
        } else {
            if (isArenaSetup(p)) {
                ConfigManager arena = new ConfigManager(p.getWorld().getName(), "plugins/" + plugin.getName() + "/Arenas", true);
                switch (args[0].toLowerCase()) {
                    case "createteam":
                        //bw createteam name color
                        if (args.length < 3) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " createTeam §o<name> §o<color>");
                            String colors = "§7";
                            for (TeamColor t : TeamColor.values()) {
                                colors += TeamColor.getChatColor(t)+t.toString() + "§7, ";
                            }
                            colors=colors.substring(0, colors.length()-2)+"§7.";
                            p.sendMessage("§6 ▪ §7Available colors: " + colors);
                        } else {
                            boolean y = true;
                            for (TeamColor t : TeamColor.values()) {
                                if (t.toString().equalsIgnoreCase(args[2])) {
                                    y = false;
                                }
                            }
                            if (y) {
                                p.sendMessage("§c▪ §7Invalid color!");
                                String colors = "§7";
                                for (TeamColor t : TeamColor.values()) {
                                    colors += TeamColor.getChatColor(t)+t.toString() + "§7, ";
                                }
                                colors=colors.substring(0, colors.length()-2)+"§7.";
                                p.sendMessage("§6 ▪ §7Available colors: " + colors);
                            } else {
                                arena.set("Team." + args[1] + ".Color", args[2].toUpperCase());
                                p.sendMessage("§6 ▪ §7" + TeamColor.getChatColor(args[2]) +args[1] +" §7created!");
                            }
                        }
                        break;
                    case "removeteam":
                        //bw removeteam name
                        if (args.length < 2) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " removeTeam §o<teamName>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(team) + team,
                                            "/"+mainCmd+" removeTeam "+team, "§7Remove "+TeamColor.getChatColor(team) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1] + ".Color") == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                            } else {
                                arena.set("Team." + args[1], null);
                                p.sendMessage("§6 ▪ §7Team removed!");
                            }
                        }
                        break;
                    case "setmaxinteam":
                        //bw setmaxinteam int
                        if (args.length < 1) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " setMaxInTeam <int>");
                        } else {
                            try {
                                Integer.parseInt(args[1]);
                            } catch (Exception ex) {
                                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " setMaxInTeam <int>");
                                return true;
                            }
                            arena.set("maxInTeam", Integer.valueOf(args[1]));
                            p.sendMessage("§6 ▪ §7Max in team set!");
                        }
                        break;
                    case "setspawn":
                        //bw setSpawn team
                        if (args.length < 2) {
                            p.sendMessage("§6 ▪ §7Usage: /" + mainCmd + " setSpawn §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setSpawn "+team, "§7Set spawn for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§6 ▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setSpawn "+team, "§7Set spawn for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Spawn", p.getLocation());
                                p.sendMessage("§6 ▪ §7Spawn set for: " + TeamColor.getChatColor(arena.getYml().getString("Team."+args[1]+".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setbed":
                        //bw setBed team
                        if (args.length < 2) {
                            p.sendMessage("§6 ▪ §7Usage: /" + mainCmd + " setBed §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setBed "+team, "§7Set bed for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (!(p.getLocation().clone().add(0, -0.5, 0).getBlock().getType() == Material.BED_BLOCK || p.getLocation().clone().add(0, 0.5, 0).getBlock().getType() == Material.BED_BLOCK
                            || p.getLocation().clone().getBlock().getType() == Material.BED_BLOCK)){
                                p.sendMessage("§c▪ §7You must stay on a bed while using this command!");
                                return true;
                            }
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§6 ▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setBed "+team, "§7Set bed for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Bed", p.getLocation());
                                p.sendMessage("§6 ▪ §7Bed set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setshop":
                        //bw setShop team
                        if (args.length < 2) {
                            p.sendMessage("§6 ▪ §7Usage: /" + mainCmd + " setShop §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setShop "+team, "§7Set shop for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§6 ▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setShop "+team, "§7Set shop for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Shop", p.getLocation());
                                p.sendMessage("§6 ▪ §7Shop set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setupgrade":
                        if (args.length < 2) {
                            p.sendMessage("§6 ▪ §7Usage: /" + mainCmd + " setUpgrade §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setUpgrade "+team, "§7Upgrade npc set for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§6 ▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§6 ▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setUpgrade "+team, "§7Upgrade npc set for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Upgrade", p.getLocation());
                                p.sendMessage("§6 ▪ §7Upgrade npc set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
                            }
                        }
                        break;
                    case "addgenerator":
                        //bw addgenerator <type>
                        if (args.length < 2) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Diamond/Emerald>");
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
                        } else {
                            List<String> types = Arrays.asList("diamond", "emerald", "iron", "gold");
                            if (types.contains(args[1].toLowerCase())) {
                                switch (args[1].toLowerCase()) {
                                    case "diamond":
                                    case "emerald":
                                        ArrayList<String> saved;
                                        if (arena.getYml().get("generator." + args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase()) == null) {
                                            saved = new ArrayList<>();
                                        } else {
                                            saved = (ArrayList<String>) arena.getYml().getStringList("generator." + args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase());
                                        }
                                        saved.add(arena.stringLocationArenaFormat(p.getLocation()));
                                        arena.set("generator." + args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase(), saved);
                                        p.sendMessage("§6 ▪ §7"+args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase()+" generator saved!");
                                        break;
                                    case "iron":
                                    case "gold":
                                        if (args.length >= 3) {
                                            if (arena.getYml().get("Team." + args[2]) != null) {
                                                arena.set("Team." + args[2] + "." + args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase(), arena.stringLocationArenaFormat(p.getLocation()));
                                                p.sendMessage("§6 ▪ §7"+args[1]+" set for: "+TeamColor.getChatColor(arena.getYml().getString("Team." + args[2]+".Color"))+args[2]);
                                            } else {
                                                p.sendMessage("§c▪ §7Invalid team!");
                                                if (arena.getYml().get("Team") != null) {
                                                    p.sendMessage("§6 ▪ §7Available teams: ");
                                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                                        p.spigot().sendMessage(createTCExecute("§6 ▪ §fIron " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                                "/"+mainCmd+" addGenerator Iron "+team, "§7Set Iron Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                        p.spigot().sendMessage(createTCExecute("§6 ▪ §6Gold " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                                "/"+mainCmd+" addGenerator Gold  "+team, "§7Set Gold Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                    }
                                                }
                                            }
                                        } else {
                                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
                                            if (arena.getYml().get("Team") != null) {
                                                p.sendMessage("§6 ▪ §7Available teams: ");
                                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                                    p.spigot().sendMessage(createTCExecute("§6 ▪ §fIron " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                            "/"+mainCmd+" addGenerator Iron "+team, "§7Set Iron Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                    p.spigot().sendMessage(createTCExecute("§6 ▪ §6Gold " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                            "/"+mainCmd+" addGenerator Gold  "+team, "§7Set Gold Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                }
                                            }
                                        }
                                        break;
                                }
                            } else {
                                p.sendMessage("§c▪ §7Invalid type!");
                            }
                        }
                        break;
                    case "savearena":
                        Bukkit.getScheduler().runTaskLater(plugin, ()-> Bukkit.unloadWorld(Bukkit.getWorld(p.getWorld().getName()), true), 30L);
                        p.teleport(config.getConfigLoc("lobbyLoc"));
                        p.sendMessage("§6 ▪ §7Arena saved!");
                        break;
                    case "setwaitingspawn":
                        p.sendMessage("§6 ▪ §7Waiting spawn set!");
                        arena.saveArenaLoc("waiting.Loc", p.getLocation());
                        break;
                    case "waitingspawnpos":
                        //bw waitingSpawn 1/2
                        if (args.length < 2){
                            p.sendMessage("§c▪ §7Usage: /"+mainCmd+" waitingSpawnPos 1 or 2");
                        } else {
                            if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2")){
                                p.sendMessage("§6 ▪ §7Pos "+args[1]+" set!");
                                arena.saveArenaLoc("waiting.Pos"+args[1], p.getLocation());
                            } else {
                                p.sendMessage("§c▪ §7Usage: /"+mainCmd+" waitingSpawnPos 1 or 2");
                            }
                        }
                        break;
                }
            }
            return false;
        }
    }

    public static void sendWorldSetupCommands(Player p, String world) {
        if (getArenaByName(world) == null) {
            p.sendMessage("§8§l▐ §b" + world + " §7- §c Commands");
            p.sendMessage("");
            p.spigot().sendMessage(createTCExecute("§2▪ §7/" + mainCmd +" setWaitingSpawn", "/"+mainCmd+" setWaitingSpawn", "§fSet the waiting spawn location."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " createTeam <name> <color>", "/" + mainCmd + " createTeam ",
                    "§fCreate a team and assign it a color."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " removeTeam <name>", "/" + mainCmd + " removeTeam ",
                    "§fRemove a team."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " setMaxInTeam <int>", "/" + mainCmd + " setMaxInTeam ", "§fSet the max team size."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " setSpawn <teamName>", "/" + mainCmd + " setSpawn ", "§fSet the spawn point for a team."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " setBed <teamName>", "/" + mainCmd + " setBed ", "§fSet the bed location for a team."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " setShop <teamName>", "/" + mainCmd + " setShop", "§fSet the shop location for a team."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " setUpgrade <teamName>", "/" + mainCmd + " setUpgrade ", "§fSet the team's upgrade npc location."));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " addGenerator", "/" + mainCmd + " addGenerator",
                    "§fSet the generator for a team using:\n§a/" + mainCmd + " addGenerator <Iron/Gold> <teamName>\n" +
                            "§fAdd a Diamond or Emerald generator using:\n§a/" + mainCmd + " addGenerator <Emerald/Diamond>"));
            p.spigot().sendMessage(createTC("§2▪ §7/" + mainCmd + " waitingSpawnPos 1/2 §a[optional]", "/"+mainCmd+" waitingSpawnPos ", "§fSet the waiting spawn location pos 1 and 2" +
                    "\n§fso the plugin will delete it when the game starts.\n§aUse this command to set a waiting spawn §con the same map §aas the arena."));
            p.spigot().sendMessage(createTC("§2▪ §7/"+mainCmd+" saveArena", "/"+mainCmd+" saveArena", "§fFinish setup and save map."));
        }
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

    public static TextComponent createTCExecute(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    private static boolean isArenaSetup(Player p) {
        return (!config.getLobbyWorldName().isEmpty()) && (!config.getLobbyWorldName().equalsIgnoreCase(p.getWorld().getName()));
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
        for (int i = 0; i <= 20; i++){
            for (SubCommand sb : getSubCommands()){
                if (sb.getPriority() == i && sb.isShow()){
                    p.spigot().sendMessage(sb.getDisplayInfo());
                }
            }
        }
    }

    @Override
    public List<SubCommand> getSubCommands() {
        return subCommandList;
    }

    /** Get instance */
    public static MainCommand getInstance() {
        return instance;
    }

    /** Check if lobby location is set, else send a error message to the player */
    public static boolean isLobbySet(Player p){
        if (config.getLobbyWorldName().isEmpty()){
            if (p != null){
                p.sendMessage("§c▪ §7You have to set the lobby location first!");
            }
            return false;
        }
        return true;
    }
}