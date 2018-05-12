package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

import static com.andrei1058.bedwars.Main.getParty;
import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.mainCmd;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class CmdProcess implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String[] cmd = e.getMessage().split(" ");
        if (Arena.isInArena(p)){
            if (cmd[0].equalsIgnoreCase("/party")){
                p.sendMessage(getMsg(p, lang.notAllowed));
                e.setCancelled(true);
                return;
            }
            for (String cmds : Main.config.l("blockedCmds")){
                if (cmd[0].equalsIgnoreCase("/"+cmds)){
                    p.sendMessage(getMsg(p, lang.notAllowed));
                    e.setCancelled(true);
                }
            }
        }
    }
}
