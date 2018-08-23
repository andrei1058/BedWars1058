package com.andrei1058.bedwars.commands.main.subcmds.both;

import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.ParentCommand;
import com.andrei1058.bedwars.commands.SubCommand;
import com.andrei1058.bedwars.commands.main.MainCommand;
import com.andrei1058.bedwars.configuration.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static com.andrei1058.bedwars.Main.link;
import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.commands.main.MainCommand.getDot;
import static com.andrei1058.bedwars.configuration.Language.getList;

public class Cmds extends SubCommand {
    /**
     * Create a sub-command for a bedWars command
     * Make sure you return true or it will say command not found
     *
     * @param parent parent command
     * @param name   sub-command name
     * @since 0.6.1 api v6
     */
    public Cmds(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(11);
        showInList(true);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + MainCommand.getInstance().getName() + " " + getSubCommandName() + "         §8 - §e view player cmds", "§fView player commands.", "/" + getParent().getName() + " " + getSubCommandName(), ClickEvent.Action.RUN_COMMAND));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;
        Player p = (Player) s;
        if (SetupSession.isInSetupSession(p)) {
            SetupSession ss = SetupSession.getSession(p);
            ss.getCm().reload();

            boolean waitingSpawn = ss.getCm().getYml().get("waiting.Loc") != null,
                    pos1 = ss.getCm().getYml().get("waiting.Pos1") != null,
                    pos2 = ss.getCm().getYml().get("waiting.Pos2") != null,
                    pos = pos1 && pos2;
            String spawnNotSetNames = "";
            String bedNotSet = "", shopNotSet = "", upgradeNotSet = "", spawnNotSet = "", generatorNotSet = "";
            int teams = 0;

            if (ss.getCm().getYml().get("Team") != null) {
                for (String team : ss.getCm().getYml().getConfigurationSection("Team").getKeys(true)) {
                    if (ss.getCm().getYml().get("Team." + team + ".Color") == null) continue;
                    ChatColor color = TeamColor.getChatColor(ss.getCm().getYml().getString("Team." + team + ".Color"));
                    if (ss.getCm().getYml().get("Team." + team + ".Spawn") == null) {
                        spawnNotSet += color + "▋";
                        spawnNotSetNames += color + team + " ";
                    }
                    if (ss.getCm().getYml().get("Team." + team + ".Bed") == null) {
                        bedNotSet += color + "▋";
                    }
                    if (ss.getCm().getYml().get("Team." + team + ".Shop") == null) {
                        shopNotSet += color + "▋";
                    }
                    if (ss.getCm().getYml().get("Team." + team + ".Upgrade") == null) {
                        upgradeNotSet += color + "▋";
                    }
                    if (ss.getCm().getYml().get("Team." + team + ".Iron") == null || ss.getCm().getYml().get("Team." + team + ".Gold") == null) {
                        generatorNotSet += color + "▋";
                    }
                    teams++;
                }
            }
            int emGen = 0, dmGen = 0;
            if (ss.getCm().getYml().get("generator.Emerald") != null) {
                emGen = ss.getCm().getYml().getStringList("generator.Emerald").size();
            }
            if (ss.getCm().getYml().get("generator.Diamond") != null) {
                dmGen = ss.getCm().getYml().getStringList("generator.Diamond").size();
            }

            String setWaitingSpawn = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (waitingSpawn ? " §m" : " ") : " ") + "setWaitingSpawn§r " + (waitingSpawn ? "§a(SET)" : "§c(NOT SET)");
            String waitingPos = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (pos ? " §m" : " ") : " ") + "waitingPos 1/2§r " + (!pos ? (pos1 ? "§c(POS 2 NOT SET)" : "§c(POS 1 NOT SET)") : "§a(SET)");
            String setSpawn = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (spawnNotSet.isEmpty() ? " §m" : " ") : " ") + "setSpawn <teamName>§r " + (spawnNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + spawnNotSet + "§c)");
            String setBed = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (bedNotSet.isEmpty() ? " §m" : " ") : " ") + "setBed§r " + (bedNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + bedNotSet + "§c)");
            String setShop = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (shopNotSet.isEmpty() ? " §m" : " ") : " ") + "setShop§r " + (shopNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + shopNotSet + "§c)");
            String setUpgrade = "§9 ▪ §7/" + getParent().getName() + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (upgradeNotSet.isEmpty() ? " §m" : " ") : " ") + "setUpgrade§r " + (upgradeNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + upgradeNotSet + "§c)");
            String addGenerator = "§9 ▪ §7/" + getParent().getName() + " addGenerator" + (ss.getSetupType() == SetupSession.SetupType.ASSISTED ? (generatorNotSet.isEmpty() ? " " : "§c(Remaining: " + generatorNotSet + "§c) ") : " ") + "§e(§2E" + emGen + " §bD" + dmGen + "§e)";

            if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
                s.sendMessage("");
                s.sendMessage("§8§l" + getDot() + " §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c " + ss.getWorldName() + " Commands");
                s.sendMessage("§7Use these commands in order.");
                p.spigot().sendMessage(Misc.msgHoverClick(setWaitingSpawn, "§dSet the place where players have\n§dto wait before the game starts.", "/" + getParent().getName() + " setWaitingSpawn", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(waitingPos, "§dMake it so the waiting lobby will disappear at start.\nSelect it as a world edit region.", "/" + getParent().getName() + " waitingPos ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " autoCreateTeams §e(lazy staff)", "§dCreate teams based on islands colors.", "/" + getParent().getName() + " autoCreateTeams", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " createTeam <name> <color> §e(" + teams + " §eSET)", "§dCreate a team.", "/" + getParent().getName() + " createTeam ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setSpawn, "§dSet a team spawn.\n§cTeams without a spawn set:\n" + spawnNotSetNames, "/" + getParent().getName() + " setSpawn ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setBed, "§dSet a team's bed location.\nYou don't have to specify the team name.", "/" + getParent().getName() + " setBed", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setShop, "§dSet a team's NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setShop", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setUpgrade, "§dSet a team's upgrade NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setUpgrade", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(addGenerator, "§dAdd a generator spawn point.\n§eStay in on a team island to set a team generator\n§fStay on a diamond block to set the diamond generator.\n§fStay on a emerald block to set an emerald generator.", "/" + getParent().getName() + " addGenerator", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " setType <type>", "§dAdd the arena to a group.", "/" + getParent().getName() + " setType", ClickEvent.Action.RUN_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " save", "§dSave arena and go back to lobby", "/" + getParent().getName() + " save", ClickEvent.Action.SUGGEST_COMMAND));
            } else {
                s.sendMessage("");
                s.sendMessage("§8§l" + getDot() + " §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c " + ss.getWorldName() + " Commands");
                p.spigot().sendMessage(Misc.msgHoverClick(setWaitingSpawn, "§dSet the place where players have\n§dto wait before the game starts.", "/" + getParent().getName() + " setWaitingSpawn", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(waitingPos, "§dMake it so the waiting lobby will disappear at start.\nSelect it as a world edit region.", "/" + getParent().getName() + " waitingPos ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + mainCmd + " removeTeam <name>", "§dRemove a team.", "/" + mainCmd + " removeTeam ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + mainCmd + " setMaxInTeam <int>", "§dSet the max team size.", "/" + mainCmd + " setMaxInTeam ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " createTeam <name> <color> §e(" + teams + " §eSET)", "§dCreate a team.", "/" + getParent().getName() + " createTeam ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setSpawn, "§dSet a team spawn.\n§cTeams without a spawn set:\n" + spawnNotSetNames, "/" + getParent().getName() + " setSpawn ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setBed, "§dSet a team's bed location.\nYou don't have to specify the team name.", "/" + getParent().getName() + " setBed", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setShop, "§dSet a team's NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setShop", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setUpgrade, "§dSet a team's upgrade NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setUpgrade", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(addGenerator, "§dAdd a generator spawn point.\n§e/" + getParent().getName() + " addGenerator <Iron/ Gold/ Emerald, Diamond>", "/" + getParent().getName() + " addGenerator", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " save", "§dSave arena and go back to lobby", "/" + getParent().getName() + " save", ClickEvent.Action.SUGGEST_COMMAND));
            }
        } else {
            TextComponent credits = new TextComponent("§8§l" + getDot() + " §6" + plugin.getName() + " §7v" + plugin.getDescription().getVersion() + " by andrei1058");
            credits.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
            credits.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Arenas: " + (Arena.getArenas().size() == 0 ? "§c0" : "§a" + Arena.getArenas().size())).create()));
            ((Player) s).spigot().sendMessage(credits);
            for (String string : getList((Player) s, Messages.COMMAND_MAIN)) {
                s.sendMessage(string);
            }
        }
        return true;
    }
}
