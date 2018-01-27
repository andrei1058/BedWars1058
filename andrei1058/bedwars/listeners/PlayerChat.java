package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.ServerType;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.Arena.isInArena;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class PlayerChat implements Listener {

    @EventHandler
    public void c(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p.getWorld().getName().equalsIgnoreCase(config.getLobbyWorldName()) && plugin.getServerType() != ServerType.SHARED) {
            if (!config.getBoolean("globalChat")) {
                e.getRecipients().clear();
                e.getRecipients().addAll(p.getWorld().getPlayers());
            }
            e.setFormat(getMsg(p, lang.chatLobbyFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                    .replace("{player}", p.getName()).replace("{message}", e.getMessage()).replace("{level}", getLevelSupport().getLevel(p)));
        } else if (isInArena(p)) {
            Arena a = Arena.getArenaByPlayer(p);
            if (a.isSpectator(p)) {
                if (!config.getBoolean("globalChat")) {
                    e.getRecipients().clear();
                    e.getRecipients().addAll(a.getSpectators());
                }
                e.setFormat(getMsg(p, lang.chatSpectatorFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                        .replace("{player}", p.getName()).replace("{message}", e.getMessage()).replace("{level}", getLevelSupport().getLevel(p)));
            } else {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                    }
                    e.setFormat(getMsg(p, lang.chatWaitingFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getName()).replace("{message}", e.getMessage())
                            .replace("{level}", getLevelSupport().getLevel(p)));
                    return;
                }
                BedWarsTeam t = a.getTeam(p);
                if (e.getMessage().startsWith("!") || e.getMessage().startsWith("shout") || e.getMessage().startsWith("SHOUT") || e.getMessage().startsWith(getMsg(p, lang.meaningShout))) {
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                    }
                    String msg = e.getMessage();
                    if (msg.startsWith("!")) msg = msg.replaceFirst("!", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("SHOUT", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("shout", "");
                    if (msg.startsWith(getMsg(p, lang.meaningShout)))
                        msg = msg.replaceFirst(getMsg(p, lang.meaningShout), "");
                    e.setFormat(getMsg(p, lang.chatGlobalFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getName()).replace("{message}", msg).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                            .replace("{level}", getLevelSupport().getLevel(p)));
                } else {
                    if (a.getMaxInTeam() == 1) {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(a.getPlayers());
                        }
                        e.setFormat(getMsg(p, lang.chatTeamFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getName()).replace("{message}", e.getMessage()).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p)));

                    } else {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(t.getMembers());
                        }
                        e.setFormat(getMsg(p, lang.chatTeamFormat).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getName()).replace("{message}", e.getMessage()).replace("{team}", TeamColor.getChatColor(t.getColor()) + "[" + t.getName().toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p)));

                    }
                }
            }
        }
    }
}
