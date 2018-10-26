package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive.setup;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoCreateTeams extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public AutoCreateTeams(ParentCommand parent, String name) {
        super(parent, name);
        setArenaSetupCommand(true);
        setPermission(Permissions.PERMISSION_SETUP_ARENA);
    }

    private static HashMap<Player, Long> timeOut = new HashMap<>();
    private static HashMap<Player, List<Byte>> teamsFoundOld = new HashMap<>();
    private static HashMap<Player, List<String>> teamsFound13 = new HashMap<>();

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        SetupSession ss = SetupSession.getSession(p);
        if (ss == null) {
            s.sendMessage("§c ▪ §7You're not in a setup session!");
            return true;
        }
        if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
            if (is13Higher()) {
                if (timeOut.containsKey(p) && timeOut.get(p) >= System.currentTimeMillis() && teamsFound13.containsKey(p)) {
                    for (String tf : teamsFound13.get(p)) {
                        Bukkit.dispatchCommand(s, Main.mainCmd + " createTeam " + TeamColor.enName(tf) + " " + TeamColor.enName(tf));
                    }
                    if (ss.getCm().getYml().get("waiting.Pos1") == null) {
                        s.sendMessage("");
                        s.sendMessage("§6§lWAITING LOBBY REMOVAL:");
                        s.sendMessage("§fIf you'd like the lobby to disappear when the game starts,");
                        s.sendMessage("§fplease use the following commands like a world edit selection.");
                        p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/" + Main.mainCmd + " waitingPos 1", "§dSet pos 1", "/" + getParent().getName() + " waitingPos 1", ClickEvent.Action.RUN_COMMAND));
                        p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/" + Main.mainCmd + " waitingPos 2", "§dSet pos 2", "/" + getParent().getName() + " waitingPos 2", ClickEvent.Action.RUN_COMMAND));
                        s.sendMessage("");
                        s.sendMessage("§7This step is OPTIONAL. If you wan to skip it do §6/" + Main.mainCmd);
                    }
                    return true;
                }
                List<String> found = new ArrayList<>();
                World w = p.getWorld();
                if (ss.getCm().getYml().get("Team") == null) {
                    p.sendMessage("§6 ▪ §7Searching for teams. This may cause lag.");
                    for (int x = -150; x < 150; x++) {
                        for (int y = 50; y < 130; y++) {
                            for (int z = -150; z < 150; z++) {
                                Block b = new Location(w, x, y, z).getBlock();
                                if (b.getType().toString().contains("_WOOL")) {
                                    if (!found.contains(b.getType().toString())) {
                                        int count = 0;
                                        for (int x1 = -2; x1 < 2; x1++) {
                                            for (int y1 = -2; y1 < 2; y1++) {
                                                for (int z1 = -2; z1 < 2; z1++) {
                                                    Block b2 = new Location(w, x, y, z).getBlock();
                                                    if (b2.getType() == b.getType()) {
                                                        count++;
                                                    }
                                                }
                                            }
                                        }
                                        if (count >= 5) {
                                            if (!TeamColor.enName(b.getType().toString()).isEmpty()) {
                                                if (ss.getCm().getYml().get("Team." + TeamColor.enName(b.getType().toString())) == null) {
                                                    found.add(b.getType().toString());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (found.isEmpty()) {
                    p.sendMessage("§6 ▪ §7No new teams were found.\n§6 ▪ §7Manually create teams with: §6/" + Main.mainCmd + " createTeam");
                } else {
                    if (timeOut.containsKey(p)) {
                        p.sendMessage("§c ▪ §7Time out. Type again to search for teams.");
                        timeOut.remove(p);
                        return true;
                    } else {
                        timeOut.put(p, System.currentTimeMillis() + 16000);
                    }
                    if (teamsFound13.containsKey(p)) {
                        teamsFound13.replace(p, found);
                    } else {
                        teamsFound13.put(p, found);
                    }
                    p.sendMessage("§6§lNEW TEAMS FOUND:");
                    for (String tf : found) {
                        String name = TeamColor.enName(tf);
                        p.sendMessage("§f ▪ " + TeamColor.getChatColor(name) + name);
                    }
                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7§lClick here to create found teams.", "§fClick to create found teams!", "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
                }
            } else {
                if (timeOut.containsKey(p) && timeOut.get(p) >= System.currentTimeMillis() && teamsFoundOld.containsKey(p)) {
                    for (Byte tf : teamsFoundOld.get(p)) {
                        Bukkit.dispatchCommand(s, Main.mainCmd + " createTeam " + TeamColor.enName(tf) + " " + TeamColor.enName(tf));
                    }
                    if (ss.getCm().getYml().get("waiting.Pos1") == null) {
                        s.sendMessage("");
                        s.sendMessage("§6§lWAITING LOBBY REMOVAL:");
                        s.sendMessage("§fIf you'd like the lobby to disappear when the game starts,");
                        s.sendMessage("§fplease use the following commands like a world edit selection.");
                        p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/" + Main.mainCmd + " waitingPos 1", "§dSet pos 1", "/" + getParent().getName() + " waitingPos 1", ClickEvent.Action.RUN_COMMAND));
                        p.spigot().sendMessage(Misc.msgHoverClick("§c ▪ §7/" + Main.mainCmd + " waitingPos 2", "§dSet pos 2", "/" + getParent().getName() + " waitingPos 2", ClickEvent.Action.RUN_COMMAND));
                        s.sendMessage("");
                        s.sendMessage("§7This step is OPTIONAL. If you wan to skip it do §6/" + Main.mainCmd);
                    }
                    return true;
                }
                List<Byte> found = new ArrayList<>();
                World w = p.getWorld();
                if (ss.getCm().getYml().get("Team") == null) {
                    p.sendMessage("§6 ▪ §7Searching for teams. This may cause lag.");
                    for (int x = -150; x < 150; x++) {
                        for (int y = 50; y < 130; y++) {
                            for (int z = -150; z < 150; z++) {
                                Block b = new Location(w, x, y, z).getBlock();
                                if (b.getType() == Material.WOOL) {
                                    if (!found.contains(b.getData())) {
                                        int count = 0;
                                        for (int x1 = -2; x1 < 2; x1++) {
                                            for (int y1 = -2; y1 < 2; y1++) {
                                                for (int z1 = -2; z1 < 2; z1++) {
                                                    Block b2 = new Location(w, x, y, z).getBlock();
                                                    if (b2.getType() == b.getType()) {
                                                        if (b.getData() == b2.getData()) {
                                                            count++;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (count >= 5) {
                                            if (!TeamColor.enName(b.getData()).isEmpty()) {
                                                if (ss.getCm().getYml().get("Team." + TeamColor.enName(b.getData())) == null) {
                                                    found.add(b.getData());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (found.isEmpty()) {
                    p.sendMessage("§6 ▪ §7No new teams were found.\n§6 ▪ §7Manually create teams with: §6/" + Main.mainCmd + " createTeam");
                } else {
                    if (timeOut.containsKey(p)) {
                        p.sendMessage("§c ▪ §7Time out. Type again to search for teams.");
                        timeOut.remove(p);
                        return true;
                    } else {
                        timeOut.put(p, System.currentTimeMillis() + 16000);
                    }
                    if (teamsFoundOld.containsKey(p)) {
                        teamsFoundOld.replace(p, found);
                    } else {
                        teamsFoundOld.put(p, found);
                    }
                    p.sendMessage("§6§lNEW TEAMS FOUND:");
                    for (Byte tf : found) {
                        String name = TeamColor.enName(tf);
                        p.sendMessage("§f ▪ " + TeamColor.getChatColor(name) + name);
                    }
                    p.spigot().sendMessage(Misc.msgHoverClick("§6 ▪ §7§lClick here to create found teams.", "§fClick to create found teams!", "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
                }
            }
        } else return false;
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }

    /**
     * Check if server version is 1.13 or higher
     */
    public static boolean is13Higher() {
        switch (Main.getServerVersion()) {
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1":
                return false;
        }
        return true;
    }
}
