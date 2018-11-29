package com.andrei1058.bedwars.commands.shout;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ShoutCommand extends BukkitCommand {

    private static HashMap<UUID, Long> shoutCooldown = new HashMap<>();

    public ShoutCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender s, String st, String[] args) {
        if (s instanceof ConsoleCommandSender) return true;
        Player p = (Player) s;
        Arena a = Arena.getArenaByPlayer(p);
        if (a == null || a.isSpectator(p)) {
            p.sendMessage(Language.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
            return true;
        }
        StringBuilder sb = new StringBuilder();
        for (String ar : args) {
            sb.append(ar + " ");
        }

        p.chat("!" + sb.toString());
        return false;
    }

    public static void updateShout(Player player) {
        if (shoutCooldown.containsKey(player.getUniqueId())) {
            shoutCooldown.replace(player.getUniqueId(), System.currentTimeMillis() + (Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN) * 1000));
        } else {
            shoutCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN) * 1000));
        }
    }

    public static boolean isShoutCooldown(Player player) {
        if (!shoutCooldown.containsKey(player.getUniqueId())) return false;
        return shoutCooldown.get(player.getUniqueId()) > System.currentTimeMillis();
    }

    public static double getShoutCooldown(Player p) {
        return (shoutCooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
    }

    public static boolean isShout(Player p) {
        if (!shoutCooldown.containsKey(p.getUniqueId())) return false;
        return shoutCooldown.get(p.getUniqueId()) + 1000 > System.currentTimeMillis();
    }
}
