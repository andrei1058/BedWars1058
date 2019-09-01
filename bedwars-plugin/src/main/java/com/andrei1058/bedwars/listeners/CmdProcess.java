package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.configuration.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class CmdProcess implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {

        Player p = e.getPlayer();

        if (e.getMessage().equals("/party sethome")){
            p.sendMessage(getMsg(p, Messages.COMMAND_NOT_ALLOWED_IN_GAME));
            e.setCancelled(true);
        }

        if (e.getMessage().equals("/party home")){
            p.sendMessage(getMsg(p, Messages.COMMAND_NOT_ALLOWED_IN_GAME));
            e.setCancelled(true);
        }

        if (p.hasPermission(Permissions.PERMISSION_COMMAND_BYPASS)) return;
        String[] cmd = e.getMessage().replaceFirst("/", "").split(" ");
        if (cmd.length == 0) return;
        if (Arena.isInArena(p)) {
            if (!Main.config.getList(ConfigPath.CENERAL_CONFIGURATION_ALLOWED_COMMANDS).contains(cmd[0])) {
                p.sendMessage(getMsg(p, Messages.COMMAND_NOT_ALLOWED_IN_GAME));
                e.setCancelled(true);
            }
        }
    }
}
