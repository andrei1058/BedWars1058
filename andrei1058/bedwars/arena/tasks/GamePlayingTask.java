package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class GamePlayingTask implements Runnable {

    private Arena arena;
    private BukkitTask task;
    private int beds_destroy_countdown, dragon_spawn_countdown, game_end_countdown;

    public GamePlayingTask(Arena arena) {
        this.arena = arena;
        this.beds_destroy_countdown = Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_BEDS_DESTROY_COUNTDOWN);
        this.dragon_spawn_countdown = Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_DRAGON_SPAWN_COUNTDOWN);
        this.game_end_countdown = Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_GAME_END_COUNTDOWN);
        this.task = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, 0, 20L);
    }

    public Arena getArena() {
        return arena;
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    public int getBedsDestroyCountdown() {
        return beds_destroy_countdown;
    }

    public int getDragonSpawnCountdown() {
        return dragon_spawn_countdown;
    }

    public int getGameEndCountdown() {
        return game_end_countdown;
    }

    @Override
    public void run() {
        switch (getArena().getNextEvent()) {
            case EMERALD_GENERATOR_TIER_II:
            case EMERALD_GENERATOR_TIER_III:
            case DIAMOND_GENERATOR_TIER_II:
            case DIAMOND_GENERATOR_TIER_III:
                if (getArena().upgradeDiamondsCount > 0) {
                    getArena().upgradeDiamondsCount--;
                    if (getArena().upgradeDiamondsCount == 0) {
                        getArena().updateNextEvent();
                        for (OreGenerator o : arena.getOreGenerators()) {
                            if (o.getOre().getType() == Material.DIAMOND) {
                                o.upgrade();
                            }
                        }
                    }
                }
                if (getArena().upgradeEmeraldsCount > 0) {
                    getArena().upgradeEmeraldsCount--;
                    if (getArena().upgradeEmeraldsCount == 0) {
                        getArena().updateNextEvent();
                        for (OreGenerator o : arena.getOreGenerators()) {
                            if (o.getOre().getType() == Material.EMERALD) {
                                o.upgrade();
                            }
                        }
                    }
                }
                break;
            case BEDS_DESTROY:
                beds_destroy_countdown--;
                if (getBedsDestroyCountdown() == 0) {
                    for (Player p : getArena().getPlayers()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 30, 0);
                        p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                    }
                    for (Player p : getArena().getSpectators()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_BEDS_DESTROYED), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_BEDS_DESTROYED), 0, 30, 0);
                        p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_BEDS_DESTROYED));
                    }
                    for (BedWarsTeam t : getArena().getTeams()) {
                        t.setBedDestroyed(true);
                    }
                    getArena().updateNextEvent();
                }
                break;
            case ENDER_DRAGON:
                dragon_spawn_countdown--;
                if (getDragonSpawnCountdown() == 0) {
                    for (Player p : getArena().getPlayers()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 30, 0);
                        for (BedWarsTeam t : getArena().getTeams()) {
                            if (t.getMembers().isEmpty()) continue;
                            p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                    .replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                        }
                    }
                    for (Player p : getArena().getSpectators()) {
                        nms.sendTitle(p, getMsg(p, Messages.NEXT_EVENT_TITLE_ANNOUNCE_SUDDEN_DEATH), getMsg(p, Messages.NEXT_EVENT_SUBTITLE_ANNOUNCE_SUDDEN_DEATH), 0, 30, 0);
                        for (BedWarsTeam t : getArena().getTeams()) {
                            if (t.getMembers().isEmpty()) continue;
                            p.sendMessage(getMsg(p, Messages.NEXT_EVENT_CHAT_ANNOUNCE_SUDDEN_DEATH).replace("{TeamDragons}", String.valueOf(t.getDragons()))
                                    .replace("{TeamColor}", TeamColor.getChatColor(t.getColor()).toString()).replace("{TeamName}", t.getName()));
                        }
                    }
                    getArena().updateNextEvent();
                    for (OreGenerator o : arena.getOreGenerators()) {
                        Location l = o.getLocation();
                        for (int y = 0; y < 20; y++) {
                            l.clone().subtract(0, y, 0).getBlock().setType(Material.AIR);
                        }
                    }
                    for (BedWarsTeam t : getArena().getTeams()) {
                        if (t.getMembers().isEmpty()) continue;
                        for (int x = 0; x < t.getDragons(); x++) {
                            nms.spawnDragon(getArena().getCm().getArenaLoc("waiting.Loc").add(0, 10, 0), t);
                        }
                    }
                }
                break;
            case GAME_END:
                game_end_countdown--;
                if (getGameEndCountdown() == 0) {
                    getArena().checkWinner();
                    getArena().changeStatus(GameState.restarting);
                }
                break;
        }
        int distance = 0;
        for (BedWarsTeam t : getArena().getTeams()) {
            if (t.getSize() > 1) {
                for (Player p : t.getMembers()) {
                    for (Player p2 : t.getMembers()) {
                        if (p2 == p) continue;
                        if (distance == 0) {
                            distance = (int) p.getLocation().distance(p2.getLocation());
                        } else if ((int) p.getLocation().distance(p2.getLocation()) < distance) {
                            distance = (int) p.getLocation().distance(p2.getLocation());
                        }
                    }
                    nms.playAction(p, getMsg(p, Messages.FORMATTING_ACTION_BAR_TRACKING).replace("{team}", TeamColor.getChatColor(t.getColor()) + t.getName())
                            .replace("{distance}", TeamColor.getChatColor(t.getColor()).toString() + distance).replace("&", "§"));
                }
            }
        }

        /* AFK SYSTEM FOR PLAYERS */
        int current = 0;
        for (Player p : getArena().getPlayers()) {
            if (Arena.afkCheck.get(p.getUniqueId()) == null) {
                Arena.afkCheck.put(p.getUniqueId(), current);
            } else {
                current = Arena.afkCheck.get(p.getUniqueId());
                current++;
                Arena.afkCheck.replace(p.getUniqueId(), current);
                if (current == 45) {
                    Main.api.setPlayerAFK(p, true);
                }
            }
        }

        /* RESPAWN SESSION */
        if (!getArena().getRespawn().isEmpty()) {
            for (Map.Entry<Player, Integer> e : getArena().getRespawn().entrySet()) {
                if (e.getValue() == 0) {
                    Arena a = Arena.getArenaByPlayer(e.getKey());
                    BedWarsTeam t = a.getTeam(e.getKey());
                    t.respawnMember(e.getKey());
                    getArena().getRespawn().remove(e.getKey());
                } else {
                    nms.sendTitle(e.getKey(), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_TITLE).replace("{time}",
                            String.valueOf(e.getValue())), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_SUBTITLE).replace("{time}",
                            String.valueOf(e.getValue())), 0, 30, 0);
                    e.getKey().sendMessage(getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_CHAT).replace("{time}", String.valueOf(e.getValue())));
                    getArena().getRespawn().replace(e.getKey(), e.getValue() - 1);
                }
            }
        }

        /* INVISIBILITY FOR ARMOR */
        if (!getArena().getShowTime().isEmpty()) {
            for (Map.Entry<Player, Integer> e : getArena().getShowTime().entrySet()) {
                if (e.getValue() <= 0) {
                    getArena().getShowTime().remove(e.getKey());
                    for (Player p : e.getKey().getWorld().getPlayers()) {
                        nms.showArmor(e.getKey(), p);
                    }
                } else {
                    getArena().getShowTime().replace(e.getKey(), e.getValue() - 1);
                }
            }
        }

        /* SPAWN ITEMS */
        for (OreGenerator o : getArena().getOreGenerators()) {
            o.spawn();
        }
    }

    public void cancel() {
        task.cancel();
    }
}


