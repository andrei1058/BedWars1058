package com.andrei1058.bedwars.support.citizens;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.skin.SkinnableEntity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.andrei1058.bedwars.commands.main.subcmds.sensitive.NPC.createArmorStand;

public class JoinNPC {
    private static boolean citizensSupport = false;

    /* Here are stored NPC holograms without colors and placeholders translated used for refresh*/
    public static HashMap<ArmorStand, List<String>> npcs_holos = new HashMap<>();
    /* Here are stored all the NPCs*/
    public static HashMap<Integer, String> npcs = new HashMap<>();


    /**
     * Check if Citizens is loaded correctly
     */
    public static boolean isCitizensSupport() {
        return citizensSupport;
    }

    /**
     * Set Citizens support
     */
    public static void setCitizensSupport(boolean citizensSupport) {
        JoinNPC.citizensSupport = citizensSupport;
        MainCommand bw = MainCommand.getInstance();
        if (bw == null) return;
        if (citizensSupport) {
            //add npc subCommand
            if (bw.isRegistered()) {
                boolean registered = false;
                for (SubCommand sc : bw.getSubCommands()) {
                    if (sc.getSubCommandName().equalsIgnoreCase("npc")) {
                        registered = true;
                    }
                }
                if (!registered) {
                    new com.andrei1058.bedwars.commands.main.subcmds.sensitive.NPC(bw, "npc");
                }
            }
        } else {
            //remove npc subCommand
            if (bw.isRegistered()) {
                for (SubCommand sc : new ArrayList<>(bw.getSubCommands())) {
                    if (sc.getSubCommandName().equalsIgnoreCase("npc")) {
                        bw.getSubCommands().remove(sc);
                    }
                }
            }
        }
    }

    /**
     * Spawn a join-NPC
     *
     * @param group Arena Group
     * @param l     Location where to be spawned
     * @param name  Display name
     * @param skin  A player name to get his skin
     */
    @Nullable
    public static NPC spawnNPC(Location l, @NotNull String name, String group, String skin) {
        if (!isCitizensSupport()) return null;
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "");
        npc.faceLocation(l);
        npc.spawn(l);
        ((SkinnableEntity) npc.getEntity()).setSkinName(skin);
        npc.faceLocation(l);
        String separator = "\\\\n";
        String[] nume = name.split(separator);
        if (nume.length >= 2) {
            ArmorStand a = createArmorStand(l.clone().add(0, 0.05, 0));
            a.setMarker(false);
            a.setCustomNameVisible(true);
            a.setCustomName(ChatColor.translateAlternateColorCodes('&', nume[0]));
            npcs.put(npc.getId(), group);
            ArmorStand a2 = createArmorStand(l.clone().subtract(0, 0.25, 0));
            a2.setMarker(false);
            a2.setCustomName(ChatColor.translateAlternateColorCodes('&', nume[1].replace("{players}", String.valueOf(Arena.getPlayers(group)))));
            a2.setCustomNameVisible(true);
            npcs_holos.put(a, Arrays.asList(group, nume[0]));
            npcs_holos.put(a2, Arrays.asList(group, nume[1]));
        } else if (nume.length == 1) {
            npcs.put(npc.getId(), group);
            ArmorStand a2 = createArmorStand(l.clone().subtract(0, 0.25, 0));
            a2.setMarker(false);
            a2.setCustomName(ChatColor.translateAlternateColorCodes('&', nume[0]).replace("{players}", String.valueOf(Arena.getPlayers(group))));
            a2.setCustomNameVisible(true);
            npcs_holos.put(a2, Arrays.asList(group, nume[0]));
        }
        npc.faceLocation(l);
        npc.setName("");
        return npc;
    }

    /**
     * Spawn all the Join-NPCs
     */
    public static void spawnNPCs() {
        if (!isCitizensSupport()) return;
        if (Main.config.getYml().get("npcLoc") != null) {
            String separator = "\\\\n";
            for (String s : Main.config.getYml().getStringList("npcLoc")) {
                String[] data = s.split(",");
                if (data.length < 10) continue;
                if (!Misc.isNumber(data[0])) continue;
                if (!Misc.isNumber(data[1])) continue;
                if (!Misc.isNumber(data[2])) continue;
                if (!Misc.isNumber(data[3])) continue;
                if (!Misc.isNumber(data[4])) continue;
                if (Misc.isNumber(data[5])) continue;
                if (Misc.isNumber(data[6])) continue;
                if (Misc.isNumber(data[7])) continue;
                if (Misc.isNumber(data[8])) continue;
                if (!Misc.isNumber(data[9])) continue;
                Location l = new Location(Bukkit.getWorld(data[5]), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]),
                        Float.valueOf(data[4]));
                String skin = data[6], name = data[7], group = data[8];
                int id = Integer.valueOf(data[9]);
                net.citizensnpcs.api.npc.NPC npc = CitizensAPI.getNPCRegistry().getById(id);
                if (CitizensAPI.getNPCRegistry().getById(id) == null) {
                    Main.plugin.getLogger().severe("Invalid npc id: " + id);
                    continue;
                } else {
                    if (!npc.isSpawned()) {
                        npc.spawn(l);
                    }
                }
                npc.faceLocation(l);
                if (npc.getEntity() instanceof SkinnableEntity) ((SkinnableEntity) npc.getEntity()).setSkinName(skin);
                npc.setProtected(true);
                npc.setName("");
                npc.faceLocation(l);
                for (Entity e : l.getWorld().getNearbyEntities(l, 1, 3, 1)) {
                    if (e.getType() == EntityType.ARMOR_STAND) e.remove();
                }
                String[] nume = name.split(separator);
                String count = String.valueOf(Arena.getPlayers(group));
                if (nume.length >= 2) {
                    String name1 = nume[0].replace("{players}", count), name2 = nume[1].replace("{players}", count);
                    ArmorStand a = createArmorStand(l.clone().add(0, 0.05, 0));
                    a.setMarker(false);
                    a.setCustomNameVisible(true);
                    a.setCustomName(ChatColor.translateAlternateColorCodes('&', name1));
                    npcs.put(npc.getId(), group);
                    npcs_holos.put(a, Arrays.asList(group, nume[0]));

                    ArmorStand a2 = createArmorStand(l.clone().subtract(0, 0.25, 0));
                    a2.setMarker(false);
                    a2.setCustomName(ChatColor.translateAlternateColorCodes('&', name2));
                    a2.setCustomNameVisible(true);
                    npcs_holos.put(a2, Arrays.asList(group, nume[1]));
                } else if (nume.length == 1) {
                    String name1 = nume[0].replace("{players}", count);
                    ArmorStand a2 = createArmorStand(l.clone().subtract(0, 0.25, 0));
                    a2.setMarker(false);
                    a2.setCustomName(ChatColor.translateAlternateColorCodes('&', name1));
                    a2.setCustomNameVisible(true);
                    npcs_holos.put(a2, Arrays.asList(group, nume[0]));
                    npcs.put(npc.getId(), group);
                }
            }

        }
    }

    /**
     * Update Join NPCs for a group
     *
     * @param group arena group
     */
    public static void updateNPCs(String group) {
        String x = String.valueOf(Arena.getPlayers(group));
        for (Map.Entry<ArmorStand, List<String>> e : npcs_holos.entrySet()) {
            if (e.getValue().get(0).equalsIgnoreCase(group)) {
                if (!(e.getKey() == null && e.getKey().isDead())) {
                    e.getKey().setCustomName(ChatColor.translateAlternateColorCodes('&', e.getValue().get(1).replace("{players}", x)));
                }
            }
        }
    }
}
