package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.SetupType;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

import static com.andrei1058.bedwars.BedWars.mainCmd;
import static com.andrei1058.bedwars.commands.Misc.createArmorStand;
import static com.andrei1058.bedwars.commands.Misc.removeArmorStand;

public class SetBed extends SubCommand {

    public SetBed(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p.getUniqueId());
        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (args.length == 0) {
            String foundTeam = "";
            double distance = 100;
            for (String team : ss.getConfig().getYml().getConfigurationSection("Team").getKeys(false)) {
                if (ss.getConfig().getYml().get("Team." + team + ".Spawn") == null) continue;
                double dis = ss.getConfig().getArenaLoc("Team." + team + ".Spawn").distance(p.getLocation());
                if (dis <= ss.getConfig().getInt(ConfigPath.ARENA_ISLAND_RADIUS)) {
                    if (dis < distance) {
                        distance = dis;
                        foundTeam = team;
                    }
                }
            }
            if (foundTeam.isEmpty()) {
                p.sendMessage("");
                p.sendMessage("§6§lBED SETUP:");
                p.sendMessage("§7There isn't any team nearby :(");
                p.sendMessage("§dMake sure you set the team's spawn first!");
                p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " setSpawn <teamName> ",
                        "§dSet a team spawn.", "/" + getParent().getName() + " setSpawn ", ClickEvent.Action.SUGGEST_COMMAND));
                p.sendMessage("§9Or if you set the spawn and the team wasn't found automatically");
                p.spigot().sendMessage(Misc.msgHoverClick("§9Use §e/" + getParent().getName() + " " + getSubCommandName() + " <teamName>", "§dSet a team bed.", "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.SUGGEST_COMMAND));
            } else Bukkit.dispatchCommand(s, getParent().getName() + " " + getSubCommandName() + " " + foundTeam);
        } else {
            if (!(BedWars.nms.isBed(p.getLocation().clone().add(0, -0.5, 0).getBlock().getType()) ||  BedWars.nms.isBed(p.getLocation().clone().add(0, 0.5, 0).getBlock().getType())
                    || BedWars.nms.isBed(p.getLocation().clone().getBlock().getType()))) {
                p.sendMessage("§c▪ §7You must stay on a bed while using this command!");
                return true;
            }
            if (ss.getConfig().getYml().get("Team." + args[0]) == null) {
                p.sendMessage("§c▪ §7This team doesn't exist!");
                if (ss.getConfig().getYml().get("Team") != null) {
                    p.sendMessage("§6 ▪ §7Available teams: ");
                    for (String team : Objects.requireNonNull(ss.getConfig().getYml().getConfigurationSection("Team")).getKeys(false)) {
                        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ " + TeamColor.getChatColor(Objects.requireNonNull(ss.getConfig().getYml().getString("Team." + team + ".Color"))) + team,
                                "§7Set bed for " + TeamColor.getChatColor(Objects.requireNonNull(ss.getConfig().getYml().getString("Team." + team + ".Color"))) + team, "/" + mainCmd + " setBed " + team, ClickEvent.Action.RUN_COMMAND));
                    }
                }
            } else {
                String team = TeamColor.getChatColor(Objects.requireNonNull(ss.getConfig().getYml().getString("Team." + args[0] + ".Color"))) + args[0];
                if (ss.getConfig().getYml().get("Team." + args[0] + ".Bed") != null) {
                    removeArmorStand("BED SET", ss.getConfig().getArenaLoc("Team." + args[0] + ".Bed"));
                }
                createArmorStand(team + " §6BED SET", p.getLocation().add(0.5, 0, 0.5));
                ss.getConfig().saveArenaLoc("Team." + args[0] + ".Bed", p.getLocation());
                p.sendMessage("§6 ▪ §7Bed set for: " + team);
                if (ss.getSetupType() == SetupType.ASSISTED) {
                    Bukkit.dispatchCommand(p, getParent().getName());
                }
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        if (s instanceof ConsoleCommandSender) return false;

        Player p = (Player) s;
        if (!SetupSession.isInSetupSession(p.getUniqueId())) return false;

        return hasPermission(s);
    }
}
