package com.andrei1058.bedwars.commands;

import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.ArenaGUI;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.ConfigManager;
import com.andrei1058.bedwars.configuration.Language;
import com.google.common.base.Joiner;
import net.md_5.bungee.api.chat.*;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.Arena.getArenaByName;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;
import static com.andrei1058.bedwars.listeners.BreakPlace.*;

public class MainCommand extends BukkitCommand {

    public MainCommand(String name) {
        super(name);
    }

    private static HashMap<Player, Long> delArenaConfirm = new HashMap<>(), statsCooldown = new HashMap<>();

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {
        if (s instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                s.sendMessage("");
                s.sendMessage(plugin.getName() + " v" + plugin.getDescription().getVersion() + " by " + plugin.getDescription().getAuthors().get(0));
                s.sendMessage("Console commands: ");
                s.sendMessage("    " + getName() + " enableSafeMode");
                s.sendMessage("    " + getName() + " disableSafeMode");
                s.sendMessage("");
                return true;
            }
            if (args[0].equalsIgnoreCase("enablesafemode")) {
                if (safeMode) {
                    s.sendMessage("SafeMode is already turned on!");
                    return true;
                }
                safeMode = true;
                getBuildSession().clear();
                config.set("safeMode", true);
                s.sendMessage("SafeMode activated!");
            } else if (args[0].equalsIgnoreCase("disablesafemode")) {
                if (!safeMode) {
                    s.sendMessage("SafeMode is already disabled!");
                    return true;
                }
                safeMode = false;
                config.set("safeMode", false);
                getBuildSession().clear();
                s.sendMessage("SafeMode is now disabled!");
            }
            return true;
        }
        Player p = (Player) s;
        if (args.length == 0) {
            if (p.isOp() && !safeMode) {
                if (isArenaSetup(p)) {
                    sendWorldSetupCommands(p, p.getWorld().getName());
                    return true;
                }
                s.sendMessage("§8§l▐ §b" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c Commands");
                s.sendMessage("");

                /** bw setLobby */
                p.spigot().sendMessage(Misc.msgHoverClick(isConfigSet("lobbyLoc") ? "§a▪ §7/" + getName() + " setLobby §a§o(set)" : "§a▪ §7/" + getName() + " setLobby)",
                        "§fSet the lobby location :) \n§fType again to replace an old spawn location.", "/" + getName() + " setLobby", ClickEvent.Action.RUN_COMMAND));


                /** bw list */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " list " + ((getArenas().size() == 0) ? "§c§o(0 set)" : "§c§o("+getArenas().size()+" set)"),
                        "§fShow available arenas", "/" + getName() + " list", ClickEvent.Action.RUN_COMMAND));


                /** bw setupArena <mapName> */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " setupArena §o<mapName>", "§fCreate or edit an arena.\n_ and - will not be displayed in the arena's name.",
                        "/" + getName() + " setupArena ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw delArena <mapName> */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " delArena §o<mapName>", "§fDelete a map and its configuration.",
                        "/" + getName() + " delArena ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw enableArena <mapName> */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " enableArena §o<mapName>","§7Enable an arena.",
                        "/" + getName() + " enableArena ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw disableArena <mapName> */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " disableArena §o<mapName>", "§fDisable an arena.\nThis will remove the players from the arena before disabling.",
                        "/" + getName() + " disableArena ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw cloneArena mapName */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " cloneArena §o<mapName> <newArena>", "§fClone an existing arena.",
                        "/" + getName() + " cloneArena ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw arenaGroup */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " arenaGroup", "§fCreate/ remove an arenaGroup\n§fAdd/ remove an arena from a group.",
                        "/" + getName() + " arenaGroup ", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw npc */
                p.spigot().sendMessage(Misc.msgHoverClick(getNPCs() == 0 ? "§a▪ §7/" + getName() + " npc add/remove" : "§a▪ §7/" + getName() + " npc add/remove §a§o(" + getNPCs() + " added)",
                        "§fAdd a NPC mob so you can join a free arena by clicking it.\n If you want to remove a NPC you must be in front of it\nand then type /" + getName() + " NPC",
                        "/" + getName() + " NPC", ClickEvent.Action.SUGGEST_COMMAND));

                /** bw build */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " build", "§fEnable or disable build session so you can break or place blocks.",
                        "/" + getName() + " build", ClickEvent.Action.RUN_COMMAND));

                /** bw reload */
                p.spigot().sendMessage(Misc.msgHoverClick("§a▪ §7/" + getName() + " reload", "§fRealod messages.", "/"+ getName() + " reload", ClickEvent.Action.RUN_COMMAND));

                p.sendMessage("");
            } else {
                TextComponent credits = new TextComponent("§8§l▐ §b" + plugin.getName() + " §7v" + plugin.getDescription().getVersion() + " by andrei1058");
                credits.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                credits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7SafeMode: " + (safeMode ? "§aEnabled" : "§cDisabled") + "\n§7Arenas: " + (Arena.getArenas().size() == 0 ?
                        "§c0" : "§a" + Arena.getArenas().size())).create()));
                p.spigot().sendMessage(credits);
                for (String string : getList(p, Language.cmdMain)) {
                    p.sendMessage(string);
                }
            }
            return true;
        } else {
            boolean x = false;
            switch (args[0].toLowerCase()) {
                case "join":
                    if (args.length < 2) {
                        p.sendMessage(getMsg(p, lang.joinCmdUsage));
                        return true;
                    }
                    if (isArenaGroup(args[1])) {
                        if (!Arena.joinRandomFromGroup(p, args[1])) {
                            p.sendMessage(getMsg(p, Language.noEmptyArena));
                        }
                        return true;
                    } else if (Arena.getArenaByName(args[1]) != null) {
                        Arena.getArenaByName(args[1]).addPlayer(p, false);
                        return true;
                    }
                    p.sendMessage(getMsg(p, Language.arenaOrGroupNotFound).replace("{name}", args[1]));
                    x = true;
                    break;
                case "leave":
                    Arena a = Arena.getArenaByPlayer(p);
                    if (a == null) {
                        if (getServerType() == ServerType.MULTIARENA && spigot.getBoolean("settings.bungeecord")){
                            Misc.moveToLobbyOrKick(p);
                        } else {
                            p.sendMessage(getMsg(p, Language.notInArena));
                        }
                    } else {
                        if (a.isPlayer(p)) {
                            a.removePlayer(p);
                        } else if (a.isSpectator(p)) {
                            a.removeSpectator(p);
                        }
                    }
                    x = true;
                    break;
                case "lang":
                case "language":
                    if (args.length == 1) {
                        p.sendMessage(getMsg(p, Language.langListHeader));
                        for (Language l : Language.getLanguages()) {
                            p.sendMessage(getMsg(p, Language.langListFormat).replace("{iso}", l.getIso()).replace("{name}", l.getLangName()));
                        }
                        p.sendMessage(getMsg(p, Language.langCmdUsage));
                        return true;
                    } else if (Language.isLanguageExist(args[1])) {
                        if (Arena.getArenaByPlayer(p) == null) {
                            getLangSupport().setLang(p, args[1]);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                p.sendMessage(getMsg(p, Language.langSet));
                            }, 10L);
                        } else {
                            p.sendMessage(getMsg(p, Language.cantLangPlaying));
                        }
                    } else {
                        p.sendMessage(getMsg(p, Language.langNotExist));
                    }
                    x = true;
                    break;
                case "gui":
                    if (Arena.getArenaByPlayer(p) != null) return true;
                    ArenaGUI.openGui(p);
                    break;
                case "stats":
                    if (statsCooldown.containsKey(p)){
                        if (System.currentTimeMillis() - 3000 >= statsCooldown.get(p)) {
                            statsCooldown.replace(p, System.currentTimeMillis());
                        } else {
                            //wait 3 seconds
                            return true;
                        }
                    } else {
                        statsCooldown.put(p, System.currentTimeMillis());
                    }
                    Misc.openStatsGUI(p);
                    break;
            }
            if (x) return true;
            if (!p.isOp()){
                return true;
            }
            if (safeMode && p.isOp()){
                p.sendMessage("§c▪ §7You should disable the safemode in order to use setup commands.");
                p.sendMessage("§a▪ §7Just type in the console: §o/"+getName()+" disablesafemode");
                return true;
            }
            if (args[0].equalsIgnoreCase("setlobby")){
                if (config.getLobbyWorldName().isEmpty()){
                    p.sendMessage("§a▪ §aAs this is the first time you set the lobby. The server needs a restart.");
                    Bukkit.getScheduler().runTaskLater(plugin, ()-> plugin.getServer().spigot().restart(), 40L);
                }
                config.saveConfigLoc("lobbyLoc", p.getLocation());
                p.sendMessage("§a▪ §7Lobby location set!");
                return true;
            }
            if (config.getLobbyWorldName().isEmpty()){
                p.sendMessage("§c▪ §7You have to set the lobby location first!");
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "list":
                    if (getArenas().size() == 0) {
                        p.sendMessage("§a▪ §7You didn't set any arena yet!");
                        return true;
                    }
                    p.sendMessage("§a▪ §7Available arenas:");
                    for (String arena : getArenas()) {
                        String status = getArenaByName(arena) == null ? "§cDisabled" : "§aEnabled";
                        p.sendMessage("§a▪    §f" + arena + " §7[" + status + "§7]");
                    }
                    break;
                case "setuparena":
                    if (args.length != 2) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " setupArena <mapName>");
                        return true;
                    }
                    File worldServer = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
                    if (!worldServer.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    if (getArenaByName(args[1]) != null) {
                        p.sendMessage("§c▪ §7Please disable it first!");
                        return true;
                    }
                    World w = null;
                    try {
                        w = Bukkit.createWorld(new WorldCreator(args[1]));
                    } catch (Exception ex) {
                        File uid = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1] + "/uid.dat");
                        uid.delete();
                        try {
                            w = Bukkit.createWorld(new WorldCreator(args[1]));
                        } catch (Exception exx) {
                        }
                    }
                    if (w == null) {
                        p.sendMessage("§c▪ §7There was an error while loading the map :(\n§c▪ §7Please delete uid.dat from " + args[1] + "'s folder.");
                        return true;
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, ()-> {
                        Bukkit.getWorld(args[1]).getEntities().stream().filter(e -> e.getType() != EntityType.PLAYER).filter(e -> e.getType() != EntityType.PAINTING).filter(e -> e.getType() != EntityType.ITEM_FRAME).forEach(Entity::remove);
                    }, 30L);
                    w.setAutoSave(true);
                    w.setGameRuleValue("doMobSpawning", "false");
                    p.teleport(w.getSpawnLocation());
                    p.setGameMode(GameMode.CREATIVE);
                    p.setFlying(true);
                    p.sendMessage("§a▪ §7You were teleported to the " + args[1] + "'s spawn.");
                    sendWorldSetupCommands(p, args[1]);
                    break;
                case "delarena":
                    if (args.length != 2) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " delArena <mapName>");
                        return true;
                    }
                    File ws = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
                    if (!ws.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    if (getArenaByName(args[1]) != null) {
                        p.sendMessage("§c▪ §7Please disable it first!");
                        return true;
                    }
                    File ac = new File("plugins/" + plugin.getName() + "/Arenas/" + args[1]);
                    if (!ac.exists()) {
                        p.sendMessage("§c▪ §7This arena doesn't exist!");
                        return true;
                    }
                    if (delArenaConfirm.containsKey(p)) {
                        if (System.currentTimeMillis() - 2000 <= delArenaConfirm.get(p)) {
                            try {
                                FileUtils.deleteDirectory(ws);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            FileUtils.deleteQuietly(ac);
                            p.sendMessage("§c▪ §7" + args[1] + " was deleted!");
                            return true;
                        }
                        p.sendMessage("§a▪ §7Type again to confirm.");
                        delArenaConfirm.replace(p, System.currentTimeMillis());
                    } else {
                        p.sendMessage("§a▪ §7Type again to confirm.");
                        delArenaConfirm.put(p, System.currentTimeMillis());
                    }
                    break;
                case "disablearena":
                    if (args.length != 2) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " disableArena <mapName>");
                        return true;
                    }
                    File wss = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
                    if (!wss.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    Arena a = Arena.getArenaByName(args[1]);
                    if (a == null) {
                        p.sendMessage("§c▪ §7This arena is disabled yet!");
                        return true;
                    }
                    p.sendMessage("§a▪ §7Disabling arena...");
                    a.disable();
                    break;
                case "enablearena":
                    if (args.length != 2) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " enable <mapName>");
                        return true;
                    }
                    File wss2 = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
                    if (!wss2.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    Arena aa = Arena.getArenaByName(args[1]);
                    if (aa != null) {
                        p.sendMessage("§c▪ §7This arena is already enabled!");
                        return true;
                    }
                    p.sendMessage("§a▪ §7Enabling arena...");
                    new Arena(args[1]);
                    break;
                case "clonearena":
                    if (args.length != 3) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " cloneArena <mapName> <newArena>");
                        return true;
                    }
                    File mapa = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[1]);
                    if (!mapa.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    File yml1 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[1] + ".yml"), yml2 = new File("plugins/" + plugin.getName() + "/Arenas/" + args[2] + ".yml");
                    if (!yml1.exists()) {
                        p.sendMessage("§c▪ §7" + args[1] + " doesn't exist!");
                        return true;
                    }
                    File mapa2 = new File(Bukkit.getServer().getWorldContainer().getPath() + "/" + args[2]);
                    if (mapa2.exists() && yml2.exists()) {
                        p.sendMessage("§c▪ §7" + args[2] + " already exist!");
                        return true;
                    }
                    if (Arena.getArenaByName(args[1]) != null){
                        p.sendMessage("§c▪ §7Please disable " + args[1] + " first!");
                        return true;
                    }
                    try {
                        FileUtils.copyDirectory(mapa, mapa2);
                        if (!new File(mapa2.getPath() + "/uid.dat").delete()) {
                            p.sendMessage("§c▪ §7An error occurred. Please delete uid.dat from " + args[2]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage("§c▪ §7An error occurred while copying the map. Check the console.");
                    }
                    if (yml1.exists()) {
                        try {
                            FileUtils.copyFile(yml1, yml2, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            p.sendMessage("§c▪ §7An error occurred while copying the map's config. Check the console.");
                        }
                    }
                    p.sendMessage("§a▪ §7Done :D.");
                    break;
                case "npc":
                    if (args.length < 2) {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " npc add/remove");
                        return true;
                    }
                    if (args[1].equalsIgnoreCase("add")) {
                        if (args.length < 5) {
                            p.sendMessage("§c▪ §7Usage: §o/" + getName() + " npc add <entityType> <arenaGroup> <displayName>");
                            return true;
                        }
                        try {
                            EntityType.valueOf(args[2]);
                        } catch (Exception ex) {
                            p.sendMessage("§c▪ §7Invalid entity type!");
                            return true;
                        }
                        if (config.getYml().get("arenaGroups") == null) {
                            if (!args[3].equalsIgnoreCase("default")) {
                                p.sendMessage("§c▪ §7There isn't any group called: " + args[3]);
                                return true;
                            }
                        } else if (!(config.getYml().getStringList("arenaGroups").contains(args[3]) || args[3].equalsIgnoreCase("default"))) {
                            p.sendMessage("§c▪ §7There isn't any group called: " + args[3]);
                            return true;
                        }
                        List<String> npcs;
                        if (config.getYml().get("npcLoc") != null) {
                            npcs = config.getYml().getStringList("npcLoc");
                        } else {
                            npcs = new ArrayList<>();
                        }
                        String name = Joiner.on(" ").join(args).replace(args[0] + " " + args[1] + " " + args[2] + " " + args[3] + " ", "");
                        npcs.add(config.getConfigLoc(p.getLocation()) + "," + args[2] + "," + name + "," + args[3]);
                        nms.spawnNPC(EntityType.valueOf(args[2]), p.getLocation(), name.replace("&", "§"), args[3]);
                        p.sendMessage("§c▪ §7NPC set =D");
                        config.set("npcLoc", npcs);
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        List<Entity> e = p.getNearbyEntities(1, 1, 1);
                        if (e.isEmpty()) {
                            p.sendMessage("§c▪ §7There isn't any entity nearby.");
                            return true;
                        }
                        if (config.getYml().get("npcLoc") == null) {
                            p.sendMessage("§c▪ §7There isn't any NPC set yet!");
                            return true;
                        }
                        Entity entitate = null;
                        List<String> locations = config.getYml().getStringList("npcLoc");
                        for (Entity en : e) {
                            for (Entity ent : npcs.keySet()) {
                                if (en.equals(ent)) {
                                    for (String loc : config.getYml().getStringList("npcLoc")) {
                                        String[] data = loc.split(",");
                                        Location l = new Location(Bukkit.getWorld(data[5]), Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2]), Float.valueOf(data[3]), Float.valueOf(data[4]));
                                        Location l2 = ent.getLocation();
                                        if (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2.getBlockZ()) {
                                            entitate = ent;
                                            locations.remove(loc);
                                        }
                                    }
                                }
                            }
                        }
                        if (entitate != null) {
                            for (Entity e2 : entitate.getNearbyEntities(0, 2, 0)){
                                if (e2.getType() == EntityType.ARMOR_STAND){
                                    e2.remove();
                                }
                            }
                            config.set("npcLoc", locations);
                            npcs.remove(entitate);
                            entitate.remove();
                            p.sendMessage("§a▪ §7Npc removed!");
                        } else {
                            p.sendMessage("§a▪ §7There isn't any npc nearby.");
                        }
                    } else {
                        p.sendMessage("§c▪ §7Usage: §o/" + getName() + " npc add/remove");
                    }
                    break;
                case "build":
                    if (isBuildSession(p)) {
                        p.sendMessage("§a▪ §7You can't place and break blocks anymore;");
                        removeBuildSession(p);
                    } else {
                        p.sendMessage("§a▪ §7You can place and break blocks now.");
                        addBuildSession(p);
                    }
                    break;
                case "arenagroup":
                    if (args.length < 2) {
                        sendArenaGroupCmdList(p);
                    } else if (args[1].equalsIgnoreCase("create")) {
                        List<String> groups;
                        if (config.getYml().getStringList("arenaGroups") == null) {
                            groups = new ArrayList<>();
                        } else {
                            groups = config.getYml().getStringList("arenaGroups");
                        }
                        if (groups.contains(args[2])) {
                            p.sendMessage("§c▪ §7This group already exists!");
                            return true;
                        }
                        groups.add(args[2]);
                        config.set("arenaGroups", groups);
                        p.sendMessage("§a▪ §7Group created!");
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        List<String> groups;
                        if (config.getYml().getStringList("arenaGroups") == null) {
                            groups = new ArrayList<>();
                        } else {
                            groups = config.getYml().getStringList("arenaGroups");
                        }
                        if (!groups.contains(args[2])) {
                            p.sendMessage("§c▪ §7This group doesn't exist!");
                            return true;
                        }
                        groups.remove(args[2]);
                        config.set("arenaGroups", groups);
                        p.sendMessage("§a▪ §7Group deleted!");
                    } else if (args.length > 2){
                        if (config.getYml().get("arenaGroups") != null) {
                            if (config.getYml().getStringList("arenaGroups").contains(args[1])) {
                                if (args.length < 3) {
                                    p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o" + args[1] + " §r§7add §o<arenaName>");
                                    p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o" + args[1] + " §r§7remove §o<arenaName>");
                                    return true;
                                }
                                File arena = new File("plugins/" + plugin.getName() + "/Arenas/" + args[3] + ".yml");
                                if (!arena.exists()) {
                                    p.sendMessage("§c▪ §7" + args[3] + " doesn't exist!");
                                    return true;
                                }
                                ConfigManager cm = new ConfigManager(args[3], "plugins/" + plugin.getName() + "/Arenas", true);
                                if (args[2].equalsIgnoreCase("add")) {
                                    cm.set("group", args[1]);
                                    if (Arena.getArenaByName(args[3]) != null) {
                                        Arena.getArenaByName(args[3]).setGroup(args[1]);
                                    }
                                    p.sendMessage("§a▪ §7" + args[3] + " was added to the group: " + args[1]);
                                } else if (args[2].equalsIgnoreCase("remove")) {
                                    p.sendMessage("§a▪ §7" + args[3] + " was removed from the group: " + cm.getYml().getString("group"));
                                    cm.set("group", "DEFAULT");
                                } else {
                                    p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o" + args[1] + " §r§7add §o<arenaName>");
                                    p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o" + args[1] + " §r§7remove §o<arenaName>");
                                }
                            } else {
                                p.sendMessage("§a▪ §7There isn't any group called: " + args[1]);
                                p.sendMessage("§a▪ §7Available groups: " + config.getYml().getStringList("arenaGroups"));
                            }
                        } else {
                            p.sendMessage("§a▪ §7There isn't any group called: " + args[1]);
                        }
                    } else {
                        sendArenaGroupCmdList(p);
                    }
                    break;
                case "reload":
                    for (Language l : Language.getLanguages()){
                        l.reload();
                        p.sendMessage("§a▪ §7"+l.getLangName()+" reloaded!");
                    }
                    break;
            }
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
                            p.sendMessage("§a▪ §7Available colors: " + colors);
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
                                p.sendMessage("§a▪ §7Available colors: " + colors);
                            } else {
                                arena.set("Team." + args[1] + ".Color", args[2].toUpperCase());
                                p.sendMessage("§a▪ §7" + TeamColor.getChatColor(args[2]) +args[1] +" §7created!");
                            }
                        }
                        break;
                    case "removeteam":
                        //bw removeteam name
                        if (args.length < 2) {
                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " removeTeam §o<teamName>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§a▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(team) + team,
                                            "/"+mainCmd+" removeTeam "+team, "§7Remove "+TeamColor.getChatColor(team) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1] + ".Color") == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                            } else {
                                arena.set("Team." + args[1], null);
                                p.sendMessage("§a▪ §7Team removed!");
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
                            p.sendMessage("§a▪ §7Max in team set!");
                        }
                        break;
                    case "setspawn":
                        //bw setSpawn team
                        if (args.length < 2) {
                            p.sendMessage("§a▪ §7Usage: /" + mainCmd + " setSpawn §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§a▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setSpawn "+team, "§7Set spawn for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§a▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setSpawn "+team, "§7Set spawn for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Spawn", p.getLocation());
                                p.sendMessage("§a▪ §7Spawn set for: " + TeamColor.getChatColor(arena.getYml().getString("Team."+args[1]+".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setbed":
                        //bw setBed team
                        if (args.length < 2) {
                            p.sendMessage("§a▪ §7Usage: /" + mainCmd + " setBed §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§a▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
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
                                    p.sendMessage("§a▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setBed "+team, "§7Set bed for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Bed", p.getLocation());
                                p.sendMessage("§a▪ §7Bed set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setshop":
                        //bw setShop team
                        if (args.length < 2) {
                            p.sendMessage("§a▪ §7Usage: /" + mainCmd + " setShop §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§a▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setShop "+team, "§7Set shop for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§a▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setShop "+team, "§7Set shop for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Shop", p.getLocation());
                                p.sendMessage("§a▪ §7Shop set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
                            }
                        }
                        break;
                    case "setupgrade":
                        if (args.length < 2) {
                            p.sendMessage("§a▪ §7Usage: /" + mainCmd + " setUpgrade §o<team>");
                            if (arena.getYml().get("Team") != null) {
                                p.sendMessage("§a▪ §7Available teams: ");
                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                    p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                            "/"+mainCmd+" setUpgrade "+team, "§7Upgrade npc set for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                }
                            }
                        } else {
                            if (arena.getYml().get("Team." + args[1]) == null) {
                                p.sendMessage("§c▪ §7This team doesn't exist!");
                                if (arena.getYml().get("Team") != null) {
                                    p.sendMessage("§a▪ §7Available teams: ");
                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                        p.spigot().sendMessage(createTCExecute("§a▪ " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                "/"+mainCmd+" setUpgrade "+team, "§7Upgrade npc set for "+TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team));
                                    }
                                }
                            } else {
                                arena.saveArenaLoc("Team." + args[1] + ".Upgrade", p.getLocation());
                                p.sendMessage("§a▪ §7Upgrade npc set for: " + TeamColor.getChatColor(arena.getYml().getString("Team." + args[1] + ".Color")) + args[1]);
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
                                        p.sendMessage("§a▪ §7"+args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase()+" generator saved!");
                                        break;
                                    case "iron":
                                    case "gold":
                                        if (args.length >= 3) {
                                            if (arena.getYml().get("Team." + args[2]) != null) {
                                                arena.set("Team." + args[2] + "." + args[1].substring(0, 1).toUpperCase()+args[1].substring(1).toLowerCase(), arena.stringLocationArenaFormat(p.getLocation()));
                                                p.sendMessage("§a▪ §7"+args[1]+" set for: "+TeamColor.getChatColor(arena.getYml().getString("Team." + args[2]+".Color"))+args[2]);
                                            } else {
                                                p.sendMessage("§c▪ §7Invalid team!");
                                                if (arena.getYml().get("Team") != null) {
                                                    p.sendMessage("§a▪ §7Available teams: ");
                                                    for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                                        p.spigot().sendMessage(createTCExecute("§a▪ §fIron " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                                "/"+mainCmd+" addGenerator Iron "+team, "§7Set Iron Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                        p.spigot().sendMessage(createTCExecute("§a▪ §6Gold " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                                "/"+mainCmd+" addGenerator Gold  "+team, "§7Set Gold Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                    }
                                                }
                                            }
                                        } else {
                                            p.sendMessage("§c▪ §7Usage: /" + mainCmd + " addGenerator <Iron/Gold> <teamName>");
                                            if (arena.getYml().get("Team") != null) {
                                                p.sendMessage("§a▪ §7Available teams: ");
                                                for (String team : arena.getYml().getConfigurationSection("Team").getKeys(false)) {
                                                    p.spigot().sendMessage(createTCExecute("§a▪ §fIron " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
                                                            "/"+mainCmd+" addGenerator Iron "+team, "§7Set Iron Generator for "+TeamColor.getChatColor(arena.getYml().getString("Team." + team + ".Color")) + team));
                                                    p.spigot().sendMessage(createTCExecute("§a▪ §6Gold " + TeamColor.getChatColor(arena.getYml().getString("Team."+team+".Color")) + team,
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
                        p.sendMessage("§a▪ §7Arena saved!");
                        break;
                    case "setwaitingspawn":
                        p.sendMessage("§a▪ §7Waiting spawn set!");
                        arena.saveArenaLoc("waiting.Loc", p.getLocation());
                        break;
                    case "waitingspawnpos":
                        //bw waitingSpawn 1/2
                        if (args.length < 2){
                            p.sendMessage("§c▪ §7Usage: /"+mainCmd+" waitingSpawnPos 1 or 2");
                        } else {
                            if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2")){
                                p.sendMessage("§a▪ §7Pos "+args[1]+" set!");
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


    private boolean isConfigSet(String path) {
        return config.getYml().get(path) != null;
    }

    private List<String> getArenas() {
        ArrayList<String> arene = new ArrayList<>();
        File dir = new File("plugins/" + plugin.getDescription().getName() + "/Arenas");
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    if (f.getName().contains(".yml")) {
                        arene.add(f.getName().replace(".yml", ""));
                    }
                }
            }
        }
        return arene;
    }

    private int getNPCs() {
        int x = 0;
        if (config.getYml().get("joinNPC") != null) {
            x = config.getYml().getShortList("joinNPC").size();
        }
        return x;
    }

    private static void sendWorldSetupCommands(Player p, String world) {
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

    private static boolean isArenaGroup(String var) {
        if (config.getYml().get("arenaGroups") != null) {
            return config.getYml().getStringList("arenaGroups").contains(var);
        }
        return var.equalsIgnoreCase("default");
    }

    private static TextComponent createTC(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    private static TextComponent createTCExecute(String text, String suggest, String shot_text) {
        TextComponent tx = new TextComponent(text);
        tx.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, suggest));
        tx.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(shot_text).create()));
        return tx;
    }

    private static boolean isArenaSetup(Player p) {
        return (!config.getLobbyWorldName().isEmpty()) && (!config.getLobbyWorldName().equalsIgnoreCase(p.getWorld().getName()));
    }

    private void sendArenaGroupCmdList(Player p){
        p.sendMessage("§a▪ §7/" + getName() + " arenaGroup create §o<groupName>");
        p.sendMessage("§a▪ §7/" + getName() + " arenaGroup remove §o<groupName>");
        p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o<groupName> §r§7add §o<arenaName>");
        p.sendMessage("§a▪ §7/" + getName() + " arenaGroup §o<groupName> §r§7remove §o<arenaName>");
    }
}