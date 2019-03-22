package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.NextEvent;
import com.andrei1058.bedwars.api.events.TeamAssignEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.Main.getParty;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.language.Language.getList;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class GameStartingTask implements Runnable {

    private int countdown;
    private Arena arena;
    private BukkitTask task;

    public GameStartingTask(Arena arena) {
        this.arena = arena;
        countdown = Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR);
        task = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, 0, 20L);
    }


    /**
     * Get countdown value
     */
    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    /**
     * Get arena
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    @Override
    public void run() {
        if (countdown == 0) {
            //Check who is having parties
            List<Player> skip = new ArrayList<>(), owners = new ArrayList<>();
            for (Player p : getArena().getPlayers()) {
                if (getParty().hasParty(p) && getParty().isOwner(p)) {
                    owners.add(p);
                }
            }

            //Mix teams order
            Collections.shuffle(getArena().getTeams());

            //Team-up parties
            for (Player p : getArena().getPlayers()) {
                if (owners.contains(p)) {
                    for (BedWarsTeam t : getArena().getTeams()) {
                        if (skip.contains(p)) continue;
                        if (t.getSize() + getParty().partySize(p) <= getArena().getMaxInTeam()) {
                            skip.add(p);
                            p.closeInventory();
                            TeamAssignEvent e = new TeamAssignEvent(p, t, getArena());
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                t.addPlayers(p);
                            }
                            for (Player mem : getParty().getMembers(p)) {
                                if (mem != p) {
                                    TeamAssignEvent ee = new TeamAssignEvent(p, t, getArena());
                                    Bukkit.getPluginManager().callEvent(ee);
                                    if (!e.isCancelled()) {
                                        t.addPlayers(mem);
                                    }
                                    skip.add(mem);
                                    mem.closeInventory();
                                }
                            }
                        }
                    }
                }
            }

            //Give a team to players without a party
            for (Player p : getArena().getPlayers()) {
                if (skip.contains(p)) continue;
                BedWarsTeam addhere = getArena().getTeams().get(0);
                for (BedWarsTeam t : getArena().getTeams()) {
                    if (t.getMembers().size() < getArena().getMaxInTeam() && t.getMembers().size() < addhere.getMembers().size()) {
                        addhere = t;
                    }
                }
                TeamAssignEvent e = new TeamAssignEvent(p, addhere, getArena());
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    addhere.addPlayers(p);
                }
                p.closeInventory();
            }

            //Color bed block if possible
            //Destroy bed if team is empty
            //Spawn shops and upgrades
            //Enable team generators
            for (BedWarsTeam team : getArena().getTeams()) {
                if (team.getMembers().isEmpty()) {
                    team.setBedDestroyed(true);
                    if (!getArena().getCm().getBoolean(ConfigPath.ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS)) {
                        team.getIronGenerator().enable();
                        team.getGoldGenerator().enable();
                    }
                    continue;
                }
                nms.colorBed(team);
                if (getArena().getMaxInTeam() > 1) {
                    nms.spawnShop(getArena().getCm().getArenaLoc("Team." + team.getName() + ".Upgrade"), Messages.NPC_NAME_TEAM_UPGRADES, getArena().getPlayers(), getArena());
                    nms.spawnShop(getArena().getCm().getArenaLoc("Team." + team.getName() + ".Shop"), Messages.NPC_NAME_TEAM_SHOP, getArena().getPlayers(), getArena());
                } else {
                    nms.spawnShop(getArena().getCm().getArenaLoc("Team." + team.getName() + ".Upgrade"), Messages.NPC_NAME_SOLO_UPGRADES, getArena().getPlayers(), getArena());
                    nms.spawnShop(getArena().getCm().getArenaLoc("Team." + team.getName() + ".Shop"), Messages.NPC_NAME_SOLO_SHOP, getArena().getPlayers(), getArena());
                }
                team.getIronGenerator().enable();
                team.getGoldGenerator().enable();
            }

            //Add heart on players head
            for (SBoard sb : SBoard.getScoreboards()) {
                sb.addHealthIcon();
            }

            getArena().checkStatus(GameState.playing);
            getArena().setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);

            //Enable diamond/ emerald generators
            for (OreGenerator og : getArena().getOreGenerators()) {
                if (og.getBwt() == null) og.enable();
            }

            //Lobby removal
            arena.getMapManager().removeLobby();

            //Spawn players
            spawnPlayers();

            task.cancel();
            return;
        }

        //Send countdown
        if (getCountdown() % 10 == 0 || getCountdown() <= 5) {
            for (Player p : getArena().getPlayers()) {
                p.playSound(p.getLocation(), nms.countdownTick(), 1f, 1f);
                if (getCountdown() >= 10) {
                    nms.sendTitle(p, "§a" + getCountdown(), null, 0, 20, 0);
                    p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", String.valueOf(getCountdown())));
                } else if (getCountdown() > 3) {
                    nms.sendTitle(p, "§e" + getCountdown(), null, 0, 20, 0);
                    p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", "§c" + String.valueOf(getCountdown())));
                } else {
                    nms.sendTitle(p, "§c" + getCountdown(), null, 0, 20, 0);
                    p.sendMessage(getMsg(p, Messages.ARENA_STATUS_START_COUNTDOWN).replace("{time}", "§c" + String.valueOf(getCountdown())));
                }
            }
        }
        countdown--;
    }

    //Spawn players
    private void spawnPlayers() {
        for (BedWarsTeam bwt : getArena().getTeams()) {
            for (Player p : bwt.getMembers()) {
                bwt.firstSpawn(p);
                p.setHealth(p.getHealth() - 0.0001);
                nms.sendTitle(p, getMsg(p, Messages.ARENA_STATUS_START_PLAYER_TITLE), null, 0, 20, 0);
                for (String tut : getList(p, Messages.ARENA_STATUS_START_PLAYER_TUTORIAL)) {
                    p.sendMessage(tut);
                }
            }
        }
    }

    public void cancel() {
        task.cancel();
    }
}
