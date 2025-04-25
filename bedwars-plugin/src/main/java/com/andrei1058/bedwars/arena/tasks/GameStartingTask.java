/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.arena.tasks;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.NextEvent;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.region.Cuboid;
import com.andrei1058.bedwars.api.tasks.StartingTask;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.team.BedWarsTeam;
import com.andrei1058.bedwars.arena.team.LegacyTeamAssigner;
import com.andrei1058.bedwars.configuration.ArenaConfig;
import com.andrei1058.bedwars.configuration.Sounds;
import com.andrei1058.bedwars.support.papi.SupportPAPI;
import com.andrei1058.bedwars.z_listeners.WaterSource;
import com.andrei1058.bedwars.z_myadditions.Bresenham;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.andrei1058.bedwars.BedWars.nms;
import static com.andrei1058.bedwars.BedWars.plugin;
import static com.andrei1058.bedwars.api.language.Language.getList;
import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class GameStartingTask implements Runnable, StartingTask {

    private int countdown;
    private final IArena arena;
    private final BukkitTask task;

    public GameStartingTask(Arena arena) {
        this.arena = arena;
        countdown = BedWars.config.getInt(ConfigPath.GENERAL_CONFIGURATION_START_COUNTDOWN_REGULAR);
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, this, 0, 20L);

        ArenaConfig cm = new ArenaConfig(BedWars.plugin, getArena().getArenaName(), plugin.getDataFolder().getPath() + "/Arenas");
        YamlConfiguration yml = cm.getYml();

        this.temporaryWallMod = yml.getBoolean(ConfigPath.ARENA_TEMPORARY_WALL_MOD);
        this.temporaryWallMaterial = yml.getString(ConfigPath.ARENA_TEMPORARY_WALL_MATERIAL);
        this.temporaryWallWallProtect = yml.getInt(ConfigPath.ARENA_TEMPORARY_WALL_WALL_PROTECTION);
        this.maxBuildY = yml.getInt(ConfigPath.ARENA_CONFIGURATION_MAX_BUILD_Y);

        this.temporaryWallPos = (List<ArrayList<ArrayList<Integer>>>) yml.getList(ConfigPath.ARENA_TEMPORARY_WALL_POS);
    }

    private final boolean temporaryWallMod;
    private final List<ArrayList<ArrayList<Integer>>> temporaryWallPos;
    private final String temporaryWallMaterial;
    private final int temporaryWallWallProtect;

    private final HashSet<int[]> wallBlocksPosXZ = new HashSet<>();

    public List<ArrayList<Integer>> wallBlocksPos = new ArrayList<>();

    private final int maxBuildY;

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
    public IArena getArena() {
        return arena;
    }

    public List<ArrayList<Integer>> getWallBlocksPos() {
        return wallBlocksPos;
    }

    /**
     * Get task ID
     */
    public int getTask() {
        return task.getTaskId();
    }

    @Override
    public BukkitTask getBukkitTask() {
        return task;
    }

    @Override
    public void run() {
        if (countdown == 0) {

            if (BedWars.config.getBoolean(ConfigPath.GENERAL_CONFIGURATION_EXPERIMENTAL_TEAM_ASSIGNER)) {
                getArena().getTeamAssigner().assignTeams(getArena());
            } else {
                LegacyTeamAssigner.assignTeams(getArena());
            }

            //Color bed block if possible
            //Destroy bed if team is empty
            //Spawn shops and upgrades
            //Disable generators for empty teams if required
            for (ITeam team : getArena().getTeams()) {
                nms.colorBed(team);
                if (team.getMembers().isEmpty()) {
                    team.setBedDestroyed(true);
                    if (getArena().getConfig().getBoolean(ConfigPath.ARENA_DISABLE_GENERATOR_FOR_EMPTY_TEAMS)) {
                        for (IGenerator gen : team.getGenerators()) {
                            gen.disable();
                        }
                    }
                }
            }

            Bukkit.getScheduler().runTaskLater(BedWars.plugin, () -> {
                //Enable diamond/ emerald generators
                for (IGenerator og : getArena().getOreGenerators()) {
                    if (og.getType() == GeneratorType.EMERALD || og.getType() == GeneratorType.DIAMOND)
                        og.enableRotation();
                }
            }, 60L);

            //Spawn players
            spawnPlayers();

            //Lobby removal
            BedWars.getAPI().getRestoreAdapter().onLobbyRemoval(arena);


            task.cancel();
            getArena().changeStatus(GameState.playing);

            // Check if emerald should be first based on time
            if (getArena().getUpgradeDiamondsCount() < getArena().getUpgradeEmeraldsCount()) {
                getArena().setNextEvent(NextEvent.DIAMOND_GENERATOR_TIER_II);
            } else {
                getArena().setNextEvent(NextEvent.EMERALD_GENERATOR_TIER_II);
            }

            //Spawn shopkeepers
            for (ITeam bwt : getArena().getTeams()) {
                bwt.spawnNPCs();
            }

            if (temporaryWallMod) {
                System.out.println("Temporary wall mode enabled");
                System.out.println(temporaryWallPos);
                this.setWalls(temporaryWallPos);
            }

            new BukkitRunnable() {
                Map<Block, Integer> addWaterSource = null;
                Set<Block> removeWaterSource = null;
                Map<Block, Integer> updateWaterSource = null;
                @Override
                public void run() {
                    addWaterSource = new HashMap<>();
                    removeWaterSource = new HashSet<>();
                    updateWaterSource = new HashMap<>();
                    for (Map.Entry<Block, Integer> pair : arena.getWaterSources().entrySet()) {
                        Integer newTimer = WaterSource.processWaterSource(pair, addWaterSource, removeWaterSource);
                        updateWaterSource.put(pair.getKey(), newTimer);
                    }
                    for (Map.Entry<Block, Integer> pair : updateWaterSource.entrySet()) {
                        arena.getWaterSources().put(pair.getKey(), pair.getValue());
                    }
                    for (Map.Entry<Block, Integer> pair : addWaterSource.entrySet()) {
                        arena.getWaterSources().putIfAbsent(pair.getKey(), pair.getValue());
                    }
                    for (Block block : removeWaterSource) {
                        arena.getWaterSources().remove(block);
                    }
                }
            }.runTaskTimer(plugin, 0, 8);

            return;
        }

        //Send countdown
        if (getCountdown() % 10 == 0 || getCountdown() <= 5) {
            if (getCountdown() < 5) {
                Sounds.playSound(ConfigPath.SOUNDS_COUNTDOWN_TICK_X + getCountdown(), getArena().getPlayers());
            } else {
                Sounds.playSound(ConfigPath.SOUNDS_COUNTDOWN_TICK, getArena().getPlayers());
            }
            for (Player player : getArena().getPlayers()) {
                Language playerLang = Language.getPlayerLanguage(player);
                String[] titleSubtitle = Language.getCountDownTitle(playerLang, getCountdown());
                nms.sendTitle(player, titleSubtitle[0], titleSubtitle[1], 0, 20, 10);
                player.sendMessage(getMsg(player, Messages.ARENA_STATUS_START_COUNTDOWN_CHAT).replace("{time}", String.valueOf(getCountdown())));
            }
        }
        countdown--;
    }

    private void setWalls(@NotNull List<ArrayList<ArrayList<Integer>>> wallsPos) {

        World world = getArena().getWorld();
        Material wallMaterial = Material.valueOf(temporaryWallMaterial);

        int[] regionWallStartEnd = new int[2];
        regionWallStartEnd[0] = arena.getRegionsList().size();

        for (ArrayList<ArrayList<Integer>> wallPos : wallsPos) {
            List<int[]> blocksPosXZ = new Bresenham(wallPos.get(0), wallPos.get(1)).algorithm();
            for (int[] blockPos : blocksPosXZ) {
                wallBlocksPosXZ.add(blockPos);
                for (int y = 0; y <= maxBuildY + 30; y++) {
                    Block block = world.getBlockAt(blockPos[0], y, blockPos[1]);
                    arena.getRegionsList().add(new Cuboid(new Location(world, blockPos[0], y, blockPos[1]), arena.getConfig().getInt(ConfigPath.ARENA_TEMPORARY_WALL_WALL_PROTECTION), true));
                    if (block.getType() == Material.AIR) {
                        block.setType(wallMaterial);
                        wallBlocksPos.add(new ArrayList<>(Arrays.asList(blockPos[0], y, blockPos[1])));
                    }
                }
            }
        }
//        arena.setWallBlocksPosXZ(wallBlocksPosXZ);
        regionWallStartEnd[1] = arena.getRegionsList().size() - 1;
        if (regionWallStartEnd[1] >= regionWallStartEnd[0]) {
            arena.setRegionWallStartEnd(regionWallStartEnd);
        };
        arena.setWallBlocksPos(wallBlocksPos);
    }

    //Spawn players
    private void spawnPlayers() {
        for (ITeam bwt : getArena().getTeams()) {
            for (Player p : new ArrayList<>(bwt.getMembers())) {
                BedWarsTeam.reSpawnInvulnerability.put(p.getUniqueId(), System.currentTimeMillis() + 2000L);
                bwt.firstSpawn(p);
                Sounds.playSound(ConfigPath.SOUND_GAME_START, p);
                nms.sendTitle(p, getMsg(p, Messages.ARENA_STATUS_START_PLAYER_TITLE), null, 0, 30, 10);
                for (String tut : getList(p, Messages.ARENA_STATUS_START_PLAYER_TUTORIAL)) {
                    p.sendMessage(SupportPAPI.getSupportPAPI().replace(p, tut));
                }
            }
        }
    }

    public void cancel() {
        task.cancel();
    }
}
