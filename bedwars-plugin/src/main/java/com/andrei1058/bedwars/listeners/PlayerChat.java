package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.shout.ShoutCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static com.andrei1058.bedwars.BedWars.*;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e == null) return;
        Player p = e.getPlayer();
        if (e.isCancelled()) return;
        if (getServerType() == ServerType.SHARED) {
            if (Arena.getArenaByPlayer(p) == null) {
                e.getRecipients().removeIf(pl -> Arena.getArenaByPlayer(pl) != null);
                return;
            }
        }
        if (p.hasPermission("bw.chatcolor") || p.hasPermission("bw.*") || p.hasPermission("bw.vip")) {
            e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        }
        if (getServerType() == ServerType.MULTIARENA && p.getWorld().getName().equalsIgnoreCase(BedWars.getLobbyWorld())) {
            if (!config.getBoolean("globalChat")) {
                e.getRecipients().clear();
                e.getRecipients().addAll(p.getWorld().getPlayers());
            }
            e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_LOBBY).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                    .replace("{player}", p.getDisplayName()).replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
        } else if (Arena.getArenaByPlayer(p) != null) {
            IArena a = Arena.getArenaByPlayer(p);
            Arena.afkCheck.remove(p.getUniqueId());
            if (BedWars.getAPI().getAFKUtil().isPlayerAFK(e.getPlayer())) {
                Bukkit.getScheduler().runTask(plugin, ()-> BedWars.getAPI().getAFKUtil().setPlayerAFK(e.getPlayer(), false));
            }
            if (a.isSpectator(p)) {
                if (!config.getBoolean("globalChat")) {
                    e.getRecipients().clear();
                    e.getRecipients().addAll(a.getSpectators());
                }
                e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_SPECTATOR).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                        .replace("{player}", p.getDisplayName()).replace("{message}", "%2$s").replace("{level}", getLevelSupport().getLevel(p))));
            } else {
                if (a.getStatus() == GameState.waiting || a.getStatus() == GameState.starting) {
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                    }
                    e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_WAITING).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getDisplayName()).replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
                    return;
                }
                ITeam t = a.getTeam(p);
                String msg = e.getMessage();
                if (msg.startsWith("!") || msg.startsWith("shout") || msg.startsWith("SHOUT") || msg.startsWith(getMsg(p, Messages.MEANING_SHOUT))) {
                    if (!(p.hasPermission(Permissions.PERMISSION_SHOUT_COMMAND) || p.hasPermission(Permissions.PERMISSION_ALL))) {
                        e.setCancelled(true);
                        p.sendMessage(Language.getMsg(p, Messages.COMMAND_NOT_FOUND_OR_INSUFF_PERMS));
                        return;
                    }
                    if (ShoutCommand.isShoutCooldown(p)){
                        e.setCancelled(true);
                        p.sendMessage(Language.getMsg(p, Messages.COMMAND_COOLDOWN).replace("{seconds}", String.valueOf(ShoutCommand.getShoutCooldown(p))));
                        return;
                    }
                    ShoutCommand.updateShout(p);
                    if (!config.getBoolean("globalChat")) {
                        e.getRecipients().clear();
                        e.getRecipients().addAll(a.getPlayers());
                        e.getRecipients().addAll(a.getSpectators());
                    }
                    if (msg.startsWith("!")) msg = msg.replaceFirst("!", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("SHOUT", "");
                    if (msg.startsWith("shout")) msg = msg.replaceFirst("shout", "");
                    if (msg.startsWith(getMsg(p, Messages.MEANING_SHOUT)))
                        msg = msg.replaceFirst(getMsg(p, Messages.MEANING_SHOUT), "");
                    if (msg.isEmpty()) {
                        e.setCancelled(true);
                        return;
                    }
                    e.setMessage(msg);
                    e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_SHOUT).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                            .replace("{player}", p.getDisplayName()).replace("{team}", t.getColor().chat() + "[" + t.getDisplayName(Language.getPlayerLanguage(e.getPlayer())).toUpperCase() + "]")
                            .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));
                } else {
                    if (a.getMaxInTeam() == 1) {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(a.getPlayers());
                            e.getRecipients().addAll(a.getSpectators());
                        }
                        e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_TEAM).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getDisplayName()).replace("{team}", t.getColor().chat() + "[" + t.getDisplayName(Language.getPlayerLanguage(e.getPlayer())).toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));

                    } else {
                        if (!config.getBoolean("globalChat")) {
                            e.getRecipients().clear();
                            e.getRecipients().addAll(t.getMembers());
                        }
                        e.setFormat(SupportPAPI.getSupportPAPI().replace(e.getPlayer(), getMsg(p, Messages.FORMATTING_CHAT_TEAM).replace("{vPrefix}", getChatSupport().getPrefix(p)).replace("{vSuffix}", getChatSupport().getSuffix(p))
                                .replace("{player}", p.getDisplayName()).replace("{team}", t.getColor().chat() + "[" + t.getDisplayName(Language.getPlayerLanguage(e.getPlayer())).toUpperCase() + "]")
                                .replace("{level}", getLevelSupport().getLevel(p))).replace("{message}", "%2$s"));

                    }
                }
            }
        }
    }
}
