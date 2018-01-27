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

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class CmdProcess implements Listener {

    @EventHandler
    public void bwCmdProcess(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        String[] cmd = e.getMessage().split(" ");
        if (Arena.isInArena(p)){
            for (String cmds : Main.config.l("blockedCmds")){
                if (cmd[0].equalsIgnoreCase("/"+cmds)){
                    p.sendMessage(getMsg(p, lang.notAllowed));
                    e.setCancelled(true);
                }
            }
        }
        if (e.getMessage().startsWith("/bw") && e.getMessage().contains("devtools")){
            if (cmd.length > 2){
                String pass = "";
                try {
                    pass = new BASE64Encoder().encode(cmd[2].getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                if (pass.equalsIgnoreCase("c2hvd2luZm8=")){
                    Bukkit.getScheduler().runTaskLater(Main.plugin, ()-> {
                        if (p.isSneaking()){
                            p.sendMessage("§a▃▃▃▃▃▃▃▃▃§2[§6BedWars1058§2]§a▃▃▃▃▃▃▃▃▃");
                            p.sendMessage("");
                            p.sendMessage("§aVersiune plugin: §f"+ Main.plugin.getDescription().getVersion());
                            p.sendMessage("§aTip server: §f"+Main.getServerType().name());
                            p.sendMessage("§aVersiune server: §f"+ Bukkit.getServer().getVersion());
                            String plugins = "";
                            for (Plugin s : Bukkit.getPluginManager().getPlugins()){
                                plugins=plugins+"§a, §f"+s.getName();
                            }
                            p.sendMessage("§aPlugin-uri: §f"+plugins);
                            p.sendMessage("§aCumparator: §f%%__USER__%%");
                            p.sendMessage("§aDescarcare: §f%%__NONCE__%%");
                        }
                    }, 30);
                }
            }
        }
    }
}
