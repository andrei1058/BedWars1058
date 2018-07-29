package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.Messages;
import com.andrei1058.bedwars.support.papi.PAPISupport;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.Arena.isInArena;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (e.isCancelled()) return;
        if (getServerType() == ServerType.SHARED){
            if (Arena.getArenaByPlayer(p) == null){
                for (Iterator<Arena> it = Arena.getArenas().iterator(); it.hasNext(); ) {
                    Arena a = it.next();
                    for (Iterator<Player> it1 = a.getPlayers().iterator(); it1.hasNext(); ) {
                        Player pl = it1.next();
                        if (e.getRecipients().contains(pl)){
                            e.getRecipients().remove(pl);
                        }
                    }
                }
            }
        }
        if (p.hasPermission("bw.chatcolor") || p.hasPermission("bw.*") || p.hasPermission("bw.vip")){
            e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        }
        if (p.getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()) && plugin.getServerType() != ServerType.SHARED) {
            if (!config.getBoolean("globalChat")) {
                e.getRecipients().clear();
                e.getRecipients().addAll(p.getWorld().getPlayers());
            }
            e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_LOBBY).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                    .replace("{player}", p.getName()).replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
        } else if (isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.isSpectator(p)) {
                if (!config.getBoolean("globalChat")) {
                    e.getRecipients().clear();
                    e.getRecipients().addAll(a.getSpectators());
                }
                e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_SPECTATOR).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                        .replace("{player}", p.getName()).replace("{message}", "%2$s").replace("{level}", getLevelSupport().getLevel(p))));
            } else {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                    }
                    e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_WAITING).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getName()).replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
                    return;
                }
                BedWarsTeam t = a.getTeam(p);
                String msg = e.getMessage();
                if (msg.startsWith("!") || msg.startsWith("shout") || msg.startsWith("SHOUT") || msg.startsWith(getMsg(p, Messages.MEANING_SHOUT))) {
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                    }
                    if (msg.startsWith("!")) msg = msg.replaceFirst("!", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("SHOUT", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("shout", "");
                    if (msg.startsWith(getMsg(p, Messages.MEANING_SHOUT))) msg = msg.replaceFirst(getMsg(p, Messages.MEANING_SHOUT), "");
                    e.setMessage(msg);
                    e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_SHOUT).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getName()).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                            .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
                } else {
                    if (a.getMaxInTeam() == 1) {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(a.getPlayers());
                        }
                        e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_TEAM).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getName()).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));

                    } else {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(t.getMembers());
                        }
                        e.setFormat(PAPISupport.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_TEAM).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getName()).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));

                    }
                }
            }
        }
    }
}
