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
import static com.andrei1058.bedwars.Main.plugin;
import static com.andrei1058.bedwars.Main.shop;
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
        setPriority(20);
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
            String bedNotSet = "", shopNotSet = "", upgradeNotSet = "", spawnNotSet = "";

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
            }

            String setWaitingSpawn = "§9 ▪ §7/" + getParent().getName() + (waitingSpawn ? " §m" : " ") + "setWaitingSpawn§r " + (waitingSpawn ? "§a(SET)" : "§c(NOT SET)");
            String waitingPos = "§9 ▪ §7/" + getParent().getName() + (pos ? " §m" : " ") + "waitingPos 1/2§r " + (!pos ? (pos1 ? "§c(POS 2 NOT SET)" : "§c(POS 1 NOT SET)") : "§a(SET)");
            String setSpawn = "§9 ▪ §7/" + getParent().getName() + (spawnNotSet.isEmpty() ? " §m" : " ") + "setSpawn <teamName>§r " + (spawnNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + spawnNotSet + "§c)");
            String setBed = "§9 ▪ §7/" + getParent().getName() + (bedNotSet.isEmpty() ? " §m" : " ") + "setBed§r " + (bedNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + bedNotSet + "§c)");
            String setShop = "§9 ▪ §7/" + getParent().getName() + (shopNotSet.isEmpty() ? " §m" : " ") + "setShop§r " + (shopNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + shopNotSet + "§c)");
            String setUpgrade = "§9 ▪ §7/" + getParent().getName() + (upgradeNotSet.isEmpty() ? " §m" : " ") + "setUpgrade§r " + (upgradeNotSet.isEmpty() ? "§a(ALL SET)" : "§c(Remaining: " + upgradeNotSet + "§c)");


            if (ss.getSetupType() == SetupSession.SetupType.ASSISTED) {
                s.sendMessage("");
                s.sendMessage("§8§l▐ §6" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion() + " §7- §c " + ss.getWorldName() + " Commands");
                s.sendMessage("§7Use these commands in order.");
                p.spigot().sendMessage(Misc.msgHoverClick(setWaitingSpawn, "§dSet the place where players have\nto wait before the game starts.", "/" + getParent().getName() + " waitingPos 1/2 §e(optional)", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(waitingPos, "§dMake it so the waiting lobby will disappear at start.\nSelect it as a world edit region.", "/" + getParent().getName() + " setWaitingSpawn", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " autoCreateTeams §e(lazy staff)", "§dCreate teams based on islands colors.", "/" + getParent().getName() + " autoCreateTeams", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setSpawn, "§dSet a team spawn.\n§cTeams without a spawn set:\n" + spawnNotSetNames, "/" + getParent().getName() + " setSpawn ", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setBed, "§dSet a team's bed location.\nYou don't have to specify the team name.", "/" + getParent().getName() + " setBed", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setShop, "§dSet a team's NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setShop", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick(setUpgrade, "§dSet a team's upgrade NPC.\nYou don't have to specify the team name.\nIt will be spawned only when the game starts.", "/" + getParent().getName() + " setUpgrade", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " addGenerator", "§dAdd a generator spawn point.\n§e/" + getParent().getName() + " addGenerator <Iron/ Gold/ Emerald, Diamond>", "/" + getParent().getName() + " addGenerator", ClickEvent.Action.SUGGEST_COMMAND));
                p.spigot().sendMessage(Misc.msgHoverClick("§9 ▪ §7/" + getParent().getName() + " save", "§dSave arena and go back to lobby", "/" + getParent().getName() + " save", ClickEvent.Action.SUGGEST_COMMAND));
            } else {

            }
        } else {
            TextComponent credits = new TextComponent("§8§l▐ §6" + plugin.getName() + " §7v" + plugin.getDescription().getVersion() + " by andrei1058");
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
