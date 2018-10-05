package com.andrei1058.bedwars.commands.bedwars.subcmds.sensitive;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import com.google.common.base.Joiner;
import net.citizensnpcs.api.CitizensAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.andrei1058.bedwars.Main.mainCmd;

public class NPC extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */

    //main usage
    private final List<BaseComponent> MAIN_USAGE = Arrays.asList(Misc.msgHoverClick("§f\n§c▪ §7Usage: §e/" + mainCmd + " " + getSubCommandName() + " add", "§fUse this command to create a join NPC.\n§fClick to see the syntax.", "/"+getParent().getName()+" "+getSubCommandName()+" add", ClickEvent.Action.RUN_COMMAND),
            Misc.msgHoverClick("§c▪ §7Usage: §e/" + mainCmd + " " + getSubCommandName() + " remove", "§fStay in front of a NPC in order to remove it.", "/"+getParent().getName()+" "+getSubCommandName()+" remove", ClickEvent.Action.SUGGEST_COMMAND));
    private final List<BaseComponent> ADD_USAGE = Arrays.asList(Misc.msgHoverClick("f\n§c▪ §7Usage: §e§o/" + getParent().getName() + " " + getSubCommandName() + " add <skin> <arenaGroup> <§7line1§9\\n§7line2§e>\n§7You can use §e{players} §7for the players count in this arena §7group.", "Click to use.", "/"+getParent().getName()+" "+getSubCommandName()+" add", ClickEvent.Action.SUGGEST_COMMAND));
    private final String NO_GROUP = "§c▪ §bThere isn't any group called: §c%name%";
    private final String NPC_SET = "§a§c▪ §bNPC: %name% §bwas set!";
    private final String NO_NPCS = "§c▪ §bThere isn't any NPC nearby.";
    private final String NO_SET = "§c▪ §bThere isn't any NPC set yet!";
    private final String NPC_REMOVED = "§c▪ §bThe target NPC was removed!";

    public NPC(ParentCommand parent, String name) {
        super(parent, name);
        showInList(true);
        setPriority(12);
        setPermission(Permissions.PERMISSION_NPC);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + "         §8   - §ecreate a join NPC", "§fCreate a join NPC  \n§fClick for more details.",
                "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        if (!JoinNPC.isCitizensSupport()) return false;
        Player p = (Player) s;
        if (args.length < 1) {
            for (BaseComponent bc : MAIN_USAGE){
                p.spigot().sendMessage(bc);
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 4) {
                for (BaseComponent bc : ADD_USAGE){
                    p.spigot().sendMessage(bc);
                }
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
            if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE) != null) {
                npcs = Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE);
            } else {
                npcs = new ArrayList<>();
            }
            String name = Joiner.on(" ").join(args).replace(args[0] + " " + args[1] + " " + args[2] + " ", "");
            net.citizensnpcs.api.npc.NPC npc = JoinNPC.spawnNPC(p.getLocation(), name, args[2], args[1], null);
            npcs.add(Main.config.getConfigLoc(p.getLocation()) + "," + args[1] + "," + name + "," + args[2] + "," + npc.getId());
            p.sendMessage(NPC_SET.replace("%name%", name.replace("&", "§").replace("\\\\n", " ")));
            Main.config.set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE, npcs);

        } else if (args[0].equalsIgnoreCase("remove")) {

            List<Entity> e = p.getNearbyEntities(4, 4, 4);
            if (e.isEmpty()) {
                p.sendMessage(NO_NPCS);
                return true;
            }
            if (Main.config.getYml().get(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE) == null) {
                p.sendMessage(NO_SET);
                return true;
            }
            net.citizensnpcs.api.npc.NPC npc = getTarget(p);
            if (npc == null) {
                p.sendMessage(NO_NPCS);
                return true;
            }
            List<String> locations = Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE);
            for (Integer id : JoinNPC.npcs.keySet()) {
                if (id == npc.getId()) {
                    for (String loc : Main.config.getYml().getStringList(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE)) {
                        locations.remove(loc);
                        break;
                    }
                }
            }
            JoinNPC.npcs.remove(npc.getId());
            for (Entity e2 : npc.getEntity().getNearbyEntities(0, 3, 0)) {
                if (e2.getType() == EntityType.ARMOR_STAND) {
                    e2.remove();
                }
            }
            Main.config.set(ConfigPath.GENERAL_CONFIGURATION_NPC_LOC_STORAGE, locations);
            npc.destroy();
            p.sendMessage(NPC_REMOVED);
        } else {
            for (BaseComponent bc : MAIN_USAGE){
                p.spigot().sendMessage(bc);
            }
        }
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return Arrays.asList("remove", "add");
    }


    /**
     * Create an armor-stand hologram
     *
     * @since API v8
     */
    public static ArmorStand createArmorStand(@NotNull Location loc) {
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setGravity(false);
        a.setVisible(false);
        a.setCustomNameVisible(false);
        a.setMarker(true);
        return a;
    }

    /**
     * Get target NPC
     */
    @Nullable
    public static net.citizensnpcs.api.npc.NPC getTarget(@NotNull final Player player) {

        BlockIterator iterator = new BlockIterator(player.getWorld(), player.getLocation().toVector(), player.getEyeLocation().getDirection(), 0, 100);
        while (iterator.hasNext()) {
            Block item = iterator.next();
            for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
                int acc = 2;
                for (int x = -acc; x < acc; x++) {
                    for (int z = -acc; z < acc; z++) {
                        for (int y = -acc; y < acc; y++) {
                            if (entity.getLocation().getBlock().getRelative(x, y, z).equals(item)) {
                                if (entity.hasMetadata("NPC")) {
                                    net.citizensnpcs.api.npc.NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
                                    if (npc != null) return npc;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
