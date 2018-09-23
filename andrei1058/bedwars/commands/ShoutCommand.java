package com.andrei1058.bedwars.commands;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Language;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
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
        if (p.hasPermission(Permissions.PERMISSION_SHOUT_COMMAND) || p.hasPermission(Permissions.PERMISSION_ALL)) {
            if (isShoutCooldown(p)) {
                p.sendMessage(Language.getMsg(p, Messages.COMMAND_COOLDOWN).replace("{seconds}", String.valueOf(getShoutCooldown(p))));
                return true;
            }
            updateShout(p);
            p.chat(st.replace("//", "!"));
        } else {
            p.sendMessage(Language.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
        }
        return false;
    }

    public static void updateShout(Player player) {
        if (shoutCooldown.containsKey(player.getUniqueId())) {
            shoutCooldown.replace(player.getUniqueId(), System.currentTimeMillis());
        } else {
            shoutCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }

    public static boolean isShoutCooldown(Player player) {
        if (!shoutCooldown.containsKey(player.getUniqueId())) return false;
        return shoutCooldown.get(player.getUniqueId()) + Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN) * 1000 > System.currentTimeMillis();
    }

    public static double getShoutCooldown(Player p) {
        return shoutCooldown.get(p.getUniqueId()) - System.currentTimeMillis() + Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_SHOUT_COOLDOWN) * 1000;
    }

    public static boolean isShout(Player p) {
        if (!shoutCooldown.containsKey(p.getUniqueId())) return false;
        return shoutCooldown.get(p.getUniqueId()) + 300 > System.currentTimeMillis();
    }
}
