package com.andrei1058.bedwars.commands.main.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.google.common.base.Joiner;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.Main.spawnNPC;

public class NPC extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */

    //citizens support
    private static boolean citizensSupport = false;

    //main usage
    private final String MAIN_USAGE = "§c▪ §7Usage: §o/" + mainCmd + " " + getSubCommandName() + "add/ remove";
    private final String ADD_USAGE = "§c▪ §bUsage: §o/" + getParent().getName() + " " + getSubCommandName() + " add <skin> <arenaGroup> <displayName00displayName2>";
    private final String NO_GROUP = "§c▪ §bThere isn't any group called: §c%name%";
    private final String NPC_SET = "§a§c▪ §bNPC: %name% §bwas set!";

    public NPC(ParentCommand parent, String name) {
        super(parent, name);
        setOpCommand(true);
        showInList(true);
        setPriority(20);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        if (!isCitizensSupport()) return false;
        Player p = (Player) s;
        if (args.length < 1) {
            p.sendMessage(MAIN_USAGE);
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 4) {
                p.sendMessage(ADD_USAGE);
                return true;
            }
            if (Main.config.getYml().get("arenaGroups") == null) {
                if (!args[2].equalsIgnoreCase("default")) {
                    p.sendMessage(NO_GROUP.replace("%name%", args[2]));
                    return true;
                }
            } else if (!(Main.config.getYml().getStringList("arenaGroups").contains(args[2]) || args[2].equalsIgnoreCase("default"))) {
                p.sendMessage(NO_GROUP.replace("%name%", args[2]));
                return true;
            }
            List<String> npcs;
            if (Main.config.getYml().get("npcLoc") != null) {
                npcs = Main.config.getYml().getStringList("npcLoc");
            } else {
                npcs = new ArrayList<>();
            }
            String name = Joiner.on(" ").join(args).replace(args[0] + " " + args[1] + " " + args[2] + " ", "");
            net.citizensnpcs.api.npc.NPC npc = spawnNPC(p.getLocation(), name, args[3], args[2]);
            npcs.add(Main.config.getConfigLoc(p.getLocation()) + "," + args[2] + "," + name + "," + args[3] + "," + npc.getId());
            p.sendMessage(NPC_SET.replace("%name%", name.replace("00", " ")));
            Main.config.set("npcLoc", npcs);
        } else if (args[1].equalsIgnoreCase("remove")) {
            List<Entity> e = p.getNearbyEntities(1, 1, 1);
            if (e.isEmpty()) {
                p.sendMessage("§c▪ §bThere isn't any entity nearby.");
                return true;
            }
            if (Main.config.getYml().get("npcLoc") == null) {
                p.sendMessage("§c▪ §bThere isn't any NPC set yet!");
                return true;
            }
            net.citizensnpcs.api.npc.NPC entitate = null;
            List<String> locations = Main.config.getYml().getStringList("npcLoc");
            for (Entity en : e) {
                for (Integer id : Main.npcs.keySet()) {
                    net.citizensnpcs.api.npc.NPC ent = CitizensAPI.getNPCRegistry().getById(id);
                    if (en.equals(ent)) {
                        for (String loc : Main.config.getYml().getStringList("npcLoc")) {
                            String[] data = loc.split(",");
                            Location l = new Location(Bukkit.getWorld(data[5]), Double.valueOf(data[0]),
                                    Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));
                            Location l2 = ent.getEntity().getLocation();
                            if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                                entitate = ent;
                                locations.remove(loc);
                            }
                        }
                    }
                }
            }
            if (entitate != null) {
                for (Entity e2 : entitate.getEntity().getNearbyEntities(0, 3, 0)) {
                    if (e2.getType() == EntityType.ARMOR_STAND) {
                        e2.remove();
                    }
                }
                Main.config.set("npcLoc", locations);
                Main.npcs.remove(entitate);
                entitate.destroy();
                p.sendMessage("§c▪ §bNpc removed!");
            } else {
                p.sendMessage("§c▪ §bThere isn't any npc nearby.");
            }
        } else {
            p.sendMessage(MAIN_USAGE);
        }
        return true;
    }

    /**
     * Check for citizens support
     *
     * @since API v8
     */
    public static boolean isCitizensSupport() {
        return citizensSupport;
    }

    public static void setCitizensSupport(boolean citizensSupport) {
        NPC.citizensSupport = citizensSupport;
    }

    /**
     * Create an armor-stand hologram
     *
     * @since API v8
     */
    public static ArmorStand createArmorStand(Location loc) {
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setGravity(false);
        a.setVisible(false);
        a.setCustomNameVisible(false);
        a.setMarker(true);
        return a;
    }
}
