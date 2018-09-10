package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.configuration.ConfigPath;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.mainCmd;

public class SetSpawn extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public SetSpawn(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        setArenaSetupCommand(true);
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
        if (args.length < 1) {
            p.sendMessage("§6 ▪ §7Usage: /" + mainCmd + " setSpawn §o<team>");
            if (ss.getCm().getYml().get("Team") != null) {
                for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                    if (ss.getCm().getYml().get("Team." + team + ".Spawn") == null) {
                        p.spigot().sendMessage(Misc.msgHoverClick("§6Set spawn for: " + TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color")) + team + "§7 (click to set)",
                                "§7Set spawn for " + TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " setSpawn " + team, ClickEvent.Action.RUN_COMMAND));
                    }
                }
            }
        } else {
            if (ss.getCm().getYml().get("Team." + args[0]) == null) {
                p.sendMessage("§c▪ §7This team doesn't exist!");
                if (ss.getCm().getYml().get("Team") != null) {
                    p.sendMessage("§a§lTeams list: ");
                    for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                        p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ " + TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color")) + team + " §7(click to set)",
                                "§7Set spawn for " + TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color")) + team, "/" + mainCmd + " setSpawn " + team, ClickEvent.Action.RUN_COMMAND));
                    }
                }
            } else {
                ss.getCm().saveArenaLoc("Team." + args[0] + ".Spawn", p.getLocation());
                String teamm = TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + args[0] + ".Color")) + args[0];
                p.sendMessage("§6 ▪ §7Spawn set for: " + teamm);
                com.andrei1058.bedwars.commands.Misc.createArmorStand(teamm+" §6SPAWN SET", p.getLocation());
                int radius = ss.getCm().getInt(ConfigPath.ARENA_ISLAND_RADIUS);
                Location l = p.getLocation();
                for (int x = -radius; x < radius; x++){
                    for (int y = -radius; y < radius; y++){
                        for (int z = -radius; z < radius; z++){
                            Block b = l.clone().add(x, y, z).getBlock();
                            if (b.getType() == Material.BED_BLOCK){
                                p.teleport(b.getLocation());
                                Bukkit.dispatchCommand(p, getParent().getName()+" setBed "+args[0]);
                                return true;
                            }
                        }
                    }
                }
                if (ss.getCm().getYml().get("Team") != null) {
                    String remainging = "";
                    for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(false)) {
                        if (ss.getCm().getYml().get("Team." + team + ".Spawn") == null) {
                            remainging+= TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color"))+team+" ";
                        }
                    }
                    if (!remainging.isEmpty()){
                        p.sendMessage("§6Remaining: "+remainging);
                    }
                }
            }
        }
        return true;
    }
}
