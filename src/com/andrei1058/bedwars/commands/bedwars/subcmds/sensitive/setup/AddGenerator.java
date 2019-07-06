package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.commands.Misc.createArmorStand;
import static com.andrei1058.bedwars.commands.Misc.removeArmorStand;

public class AddGenerator extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public AddGenerator(ParentCommand parent, String name) {
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
        ConfigManager arena = ss.getCm();
        if (args.length < 1) {
            String foundTeam = "";
            double distance = 100;
            if (ss.getCm().getYml().getConfigurationSection("Team") == null) {
                p.sendMessage("§c ▪ §7Please create teams first!");
                return true;
            }
            for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                if (ss.getCm().getYml().get("Team." + team + ".Spawn") == null) continue;
                double dis = ss.getCm().getArenaLoc("Team." + team + ".Spawn").distance(p.getLocation());
                if (dis <= ss.getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS)) {
                    if (dis < distance) {
                        distance = dis;
                        foundTeam = team;
                    }
                }
            }
            if (!foundTeam.isEmpty()) {
                if (ss.getCm().getYml().get("Team." + foundTeam + ".Iron") != null) {
                    removeArmorStand("Generator", ss.getCm().getArenaLoc("Team." + foundTeam + ".Iron"));
                }
                if (ss.getCm().getYml().get("Team." + foundTeam + ".Gold") != null) {
                    removeArmorStand("Generator", ss.getCm().getArenaLoc("Team." + foundTeam + ".Gold"));
                }
                arena.set("Team." + foundTeam + ".Iron", arena.stringLocationArenaFormat(p.getLocation()));
                arena.set("Team." + foundTeam + ".Gold", arena.stringLocationArenaFormat(p.getLocation()));
                String team = TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + foundTeam + ".Color")) + foundTeam;
                p.sendMessage("§6▪ §7Generator set for team: " + team);
                createArmorStand("§7Generator set for " + team, p.getLocation());
                if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
                    Bukkit.dispatchCommand(p, getParent().getName());
                }
                return true;
            }
            if (p.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                Bukkit.dispatchCommand(p, getParent().getName() + " " + getSubCommandName() + " diamond");
                return true;
            } else if (p.getLocation().add(0, -1, 0).getBlock().getType() == Material.EMERALD_BLOCK) {
                Bukkit.dispatchCommand(p, getParent().getName() + " " + getSubCommandName() + " emerald");
                return true;
            }
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Diamond/Emerald>");
            if (ss.getSetupType() == SetupSession.SetupType.ADVANCED) {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
            } else {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold>");
            }
            return true;
        }
        List<String> types = Arrays.asList("diamond", "emerald", "iron", "gold");
        if (types.contains(args[0].toLowerCase())) {
            switch (args[0].toLowerCase()) {
                case "diamond":
                case "emerald":
                    List<Location> locations = ss.getCm().getLocations("generator." + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase());
                    for (Location l : locations){
                        if (ss.getCm().compareArenaLoc(l, p.getLocation())){
                            p.sendMessage("§6 ▪ §cThis generator was already set!");
                            return true;
                        }
                    }
                    ArrayList<String> saved;
                    if (arena.getYml().get("generator." + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase()) == null) {
                        saved = new ArrayList<>();
                    } else {
                        saved = (ArrayList<String>) arena.getYml().getStringList("generator." + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase());
                    }
                    saved.add(arena.stringLocationArenaFormat(p.getLocation()));
                    arena.set("generator." + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase(), saved);
                    p.sendMessage("§6 ▪ §7" + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase() + " generator saved!");
                    createArmorStand("§6" + args[0] + " SET", p.getLocation());
                    if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
                        //autoSetGen(p, getParent().getName()+" "+getSubCommandName()+" ", ss, args[0].equalsIgnoreCase("diamond") ?
                        //        Material.DIAMOND_BLOCK : Material.EMERALD_BLOCK);
                        Bukkit.dispatchCommand(p, getParent().getName());
                    }
                    break;
                case "iron":
                case "gold":
                    String foundTeam = "";
                    double distance = 100;
                    if (args.length == 1) {
                        for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                            if (ss.getCm().getYml().get("Team." + team + ".Spawn") == null) continue;
                            double dis = ss.getCm().getArenaLoc("Team." + team + ".Spawn").distance(p.getLocation());
                            if (dis <= ss.getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS)) {
                                if (dis < distance) {
                                    distance = dis;
                                    foundTeam = team;
                                }
                            }
                        }
                        if (foundTeam.isEmpty()) {
                            p.sendMessage("");
                            p.sendMessage("§6§lTEAM GENERATORS SETUP:");
                            p.sendMessage("§7There isn't any team nearby :(");
                            p.sendMessage("§dMake sure you set the team's spawn first!");
                            p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " setSpawn <teamName> ",
                                    "§dSet a team spawn.", "/" + getParent().getName() + " " + getSubCommandName() + " ", ClickEvent.Action.SUGGEST_COMMAND));
                            p.sendMessage("§9Or if you set the spawn and the team wasn't found automatically");
                            p.spigot().sendMessage(Misc.msgHoverClick("§9Use §e/" + getParent().getName() + " " + getSubCommandName() + " " + args[0] + " <teamName>", "§dSet a team " + args[0] + " generator.", "/" + getParent().getName() + " " + getSubCommandName() + " " + args[0], ClickEvent.Action.SUGGEST_COMMAND));
                            return true;
                        }
                    } else if (args.length == 2) {
                        if (arena.getYml().get("Team." + args[1]) != null) {
                            foundTeam = args[1];
                        } else {
                            p.sendMessage("§c▪ §7Invalid team!");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§6 ▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §fIron generator " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team + "§7 (click to set)",
                                            "§7Set Iron Generator for " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " addGenerator Iron " + team, ClickEvent.Action.RUN_COMMAND));
                                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §6Gold generator " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team + "§7 (click to set)",
                                            "§7Set Gold Generator for " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " addGenerator Gold  " + team, ClickEvent.Action.RUN_COMMAND));
                                }
                            }
                            return true;
                        }
                    } else {
                        if (ss.getSetupType() == SetupSession.SetupType.ADVANCED) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
                        } else {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold>");
                        }
                        if (arena.getYml().get("Team") != null) {
                            p.sendMessage("§6 ▪ §7Available teams: ");
                            for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §fIron generator " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team + "§7 (click to set)",
                                        "§7Set Iron Generator for " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " addGenerator Iron " + team, ClickEvent.Action.RUN_COMMAND));
                                p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §6Gold generator " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team + "§7 (click to set)",
                                        "§7Set Gold Generator for " + TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " addGenerator Gold  " + team, ClickEvent.Action.RUN_COMMAND));
                            }
                        }
                        return true;
                    }
                    arena.set("Team." + foundTeam + "." + args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase(), arena.stringLocationArenaFormat(p.getLocation()));
                    p.sendMessage("§6 ▪ §7" + args[0] + " set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + foundTeam + ".Color")) + foundTeam);
                    if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
                        Bukkit.dispatchCommand(p, getParent().getName());
                    }
            }
        } else {
            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Diamond/Emerald>");
            if (ss.getSetupType() == SetupSession.SetupType.ADVANCED) {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
            } else {
                p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold>");
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("Diamond", "Emerald", "Iron", "Gold");
    }

    @Override
    public boolean canSee(CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (!SetupSession.isInSetupSession(p)) return false;

        return hasPermission(s);
    }
}
