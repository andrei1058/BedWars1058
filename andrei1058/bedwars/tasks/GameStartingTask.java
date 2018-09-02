package com.andrei1058.bedwars.tasks;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.GameState;
import com.andrei1058.bedwars.api.NextEvent;
import com.andrei1058.bedwars.api.TeamAssignEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.OreGenerator;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.configuration.Messages;
import com.boydti.fawe.jnbt.anvil.generator.OreGen;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.andrei1058.bedwars.Main.getParty;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getList;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class GameStartingTask extends BukkitRunnable {

    private int countdown;
    private Arena arena;

    public GameStartingTask(Arena arena) {
        this.arena = arena;
        countdown = Main.config.getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR);
        this.runTaskTimer(Main.plugin, 0, 20L);
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
        return this.getTaskId();
    }

    @Override
    public void run() {
        if (countdown == 0) {
            cancel();

            //Enable diamond/ emerald generators
            for (OreGenerator og : getArena().getOreGenerators()) {
                if (og.getBwt() == null) og.enable();
            }

            //Check who is having parties
            List<Player> skip = new ArrayList<>(), owners = new ArrayList<>();
            for (Player p : getArena().getPlayers()) {
                if (getParty().hasParty(p) && getParty().isOwner(p)) {
                    owners.add(p);
                }
            }

            //Mix arenas order
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
                    continue;
                }
                nms.colorBed(team);
                if (getArena().getMaxInTeam() > 1) {
                    nms.spawnShop(team.getTeamUpgrades(), Messages.UPGRADES_TEAM_NPC_NAME, getArena().getPlayers(), getArena());
                    nms.spawnShop(team.getShop(), Messages.SHOP_TEAM_NAME, getArena().getPlayers(), getArena());
                } else {
                    nms.spawnShop(team.getTeamUpgrades(), Messages.UPGRADES_SOLO_NPC_NAME, getArena().getPlayers(), getArena());
                    nms.spawnShop(team.getShop(), Messages.SHOP_SOLO_NAME, getArena().getPlayers(), getArena());
                }
                team.getIronGenerator().enable();
                team.getGoldGenerator().enable();
            }

            //Lobby removal
            if (!(getArena().getCm().getYml().get(ConfigPath.ARENA_WAITING_POS1) == null && getArena().getCm().getYml().get(ConfigPath.ARENA_WAITING_POS2) == null)) {
                Location loc1 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS1), loc2 = getArena().getCm().getArenaLoc(ConfigPath.ARENA_WAITING_POS2);
                int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
                int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                for (int x = minX; x < maxX; x++) {
                    for (int y = minY; y < maxY; y++) {
                        for (int z = minZ; z < maxZ; z++) {
                            Block b = new Location(getArena().getWorld(), x, y, z).getBlock();
                            if (b.getType() != Material.AIR) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }

            //Add heart on players head
            for (SBoard sb : SBoard.getScoreboards()) {
                sb.addHealthIcon();
            }

            //Spawn players
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

            getArena().setStatus(GameState.playing);
            getArena().setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
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
}
