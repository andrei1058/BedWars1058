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

package com.andrei1058.bedwars.api.arena;

import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.arena.stats.GameStatsHolder;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.ITeamAssigner;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.region.Region;
import com.andrei1058.bedwars.api.tasks.PlayingTask;
import com.andrei1058.bedwars.api.tasks.RestartingTask;
import com.andrei1058.bedwars.api.tasks.StartingTask;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public interface IArena {

    /**
     * Check if a player is spectating on this arena.
     */
    boolean isSpectator(Player player);

    /**
     * Check if a player is spectating on this arena.
     */
    boolean isSpectator(UUID player);

    /**
     * Check if a player is spectating on this arena.
     */
    boolean isReSpawning(UUID player);

    /**
     * Get used world name.
     */
    String getArenaName();

    /**
     * Initialize the arena after loading the world.
     * This needs to be called in order to allow players to join.
     */
    void init(World world);

    /**
     * Get arena config.
     */
    ConfigManager getConfig();

    /**
     * Check if user is playing.
     */
    boolean isPlayer(Player player);

    /**
     * Get a list of spectators.
     */
    List<Player> getSpectators();

    /**
     * Get the player's team.
     * This will work if the player is alive only.
     * <p>
     * Use {@link #getExTeam(UUID)} to get the team where the player has played in current match.
     */
    ITeam getTeam(Player player);

    /**
     * Get the team where the player has played in current match.
     * To be used if the player was eliminated.
     */
    ITeam getExTeam(UUID player);

    /**
     * Get the arena name as a message that can be used on signs etc.
     *
     * @return A string with - and _ replaced by a space.
     */
    String getDisplayName();

    /**
     * Change world name for auto-scaling.
     *
     * @param name new name.
     */
    void setWorldName(String name);

    /**
     * Get arena status.
     */
    GameState getStatus();

    /**
     * Get players in arena.
     */
    List<Player> getPlayers();

    /**
     * Get maximum allowed players amount.
     */
    int getMaxPlayers();

    /**
     * Get arena group.
     */
    String getGroup();

    /**
     * Get maximum players allowed in a team.
     */
    int getMaxInTeam();

    /**
     * Get list of players in respawn screen.
     * Player is the actual player in re-spawn screen.
     * Integer is the remaining time.
     */
    ConcurrentHashMap<Player, Integer> getRespawnSessions();


    /**
     * Disable spectator collisions.
     *
     * @param p - spectator.
     *          Use false when the spectator got removed from the arena.
     */
    void updateSpectatorCollideRule(Player p, boolean collide);

    /**
     * This will attempt to upgrade the next event if it is the case.
     */
    void updateNextEvent();

    /**
     * Add a player to the arena
     *
     * @param p              - Player to add.
     * @param skipOwnerCheck - True if you want to skip the party checking for this player. This
     * @return true if was added.
     */
    boolean addPlayer(Player p, boolean skipOwnerCheck);

    /**
     * Add a player as Spectator
     *
     * @param p            Player to be added
     * @param playerBefore True if the player has played in this arena before and he died so now should be a spectator.
     */
    boolean addSpectator(Player p, boolean playerBefore, Location staffTeleport);

    /**
     * Remove a player from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     */
    void removePlayer(Player p, boolean disconnect);

    /**
     * Remove a spectator from the arena
     *
     * @param p          Player to be removed
     * @param disconnect True if the player was disconnected
     */
    void removeSpectator(Player p, boolean disconnect);

    /**
     * Rejoin an arena
     *
     * @return true if can rejoin
     */
    boolean reJoin(Player p);

    /**
     * Disable the arena.
     * This will automatically kick/ remove the people from the arena.
     */
    void disable();

    /**
     * Restart the arena.
     */
    void restart();

    /**
     * Get the arena world
     */
    World getWorld();

    /**
     * Get the display status for an arena.
     * A message that can be used on signs etc.
     */
    String getDisplayStatus(Language lang);

    /**
     * Get arena display group for given player.
     *
     * @return translated group.
     */
    String getDisplayGroup(Player player);

    /**
     * Get arena display group for given language.
     *
     * @return translated group.
     */
    @SuppressWarnings("unused")
    String getDisplayGroup(Language language);

    List<ITeam> getTeams();

    /**
     * Add placed block to cache.
     * So players will be able to remove blocks placed by players only.
     */
    void addPlacedBlock(Block block);

    /**
     * Gets the cooldowns for fireballs
     * @return The cooldowns for fireballs
     */
    Map<UUID, Long> getFireballCooldowns();

    /**
     * Remove placed block.
     */
    void removePlacedBlock(Block block);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isBlockPlaced(Block block);

    /**
     * Get a player kills count.
     *
     * @param p          Target player
     * @param finalKills True if you want to get the Final Kills. False for regular kills.
     */
    @Deprecated
    int getPlayerKills(Player p, boolean finalKills);

    /**
     * Session stats.
     * @return stats container for this game.
     */
    @Nullable
    GameStatsHolder getStatsHolder();

    /**
     * Get the player beds destroyed count
     *
     * @param p Target player
     */
    @Deprecated
    int getPlayerBedsDestroyed(Player p);

    /**
     * Get the join signs for this arena
     *
     * @return signs.
     */
    List<Block> getSigns();

    /**
     * Get the island radius
     */
    int getIslandRadius();

    void setGroup(String group);

    /**
     * Set game status without starting stats.
     */
    void setStatus(GameState status);

    /**
     * Change game status starting tasks.
     */
    void changeStatus(GameState status);

    /**
     * Check if target player is in re-spawning screen.
     */
    @Deprecated
    default boolean isRespawning(Player p) {
        return isReSpawning(p);
    }

    /**
     * Add a join sign for the arena.
     */
    void addSign(Location loc);

    /**
     * Refresh signs.
     */
    void refreshSigns();

    /**
     * Add a kill point to the game stats.
     */
    @Deprecated
    void addPlayerKill(Player p, boolean finalKill, Player victim);

    /**
     * Add a destroyed bed point to the player temp stats.
     */
    @Deprecated
    void addPlayerBedDestroyed(Player p);


    /**
     * Get arena by player name.
     * Used to get the team for a player that has left the arena.
     * Make sure the player is in this arena first.
     */
    @Deprecated
    ITeam getPlayerTeam(String playerName);

    /**
     * Check winner. Will check if the game has a winner in certain conditions. Manage your win conditions.
     * Call the arena restart and the needed stuff.
     */
    void checkWinner();

    /**
     * Add a kill to the player temp stats.
     */
    @Deprecated
    void addPlayerDeath(Player p);

    /**
     * Set next event for the arena.
     */
    void setNextEvent(NextEvent nextEvent);

    /**
     * Get next event.
     */
    NextEvent getNextEvent();

    /**
     * This will give the pre-game command Items.
     * This will clear the inventory first.
     */
    void sendPreGameCommandItems(Player p);

    /**
     * This will give the spectator command Items.
     * This will clear the inventory first.
     */
    void sendSpectatorCommandItems(Player p);

    /**
     * Get a team by name
     */
    ITeam getTeam(String name);

    StartingTask getStartingTask();

    PlayingTask getPlayingTask();

    RestartingTask getRestartingTask();

    /**
     * Get Ore Generators.
     */
    List<IGenerator> getOreGenerators();

    /**
     * Get the list of next events to come.
     * Not ordered.
     */
    List<String> getNextEvents();

    /**
     * Get player deaths.
     */
    @Deprecated
    int getPlayerDeaths(Player p, boolean finalDeaths);

    /**
     * Show upgrade announcement to players.
     * Change diamondTier value first.
     */
    void sendDiamondsUpgradeMessages();

    /**
     * Show upgrade announcement to players.
     * Change emeraldTier value first.
     */
    void sendEmeraldsUpgradeMessages();

    /**
     * List of placed blocks.
     */
    LinkedList<Vector> getPlaced();

    /**
     * This is used to destroy arena data when it restarts.
     */
    void destroyData();

    int getUpgradeDiamondsCount();

    int getUpgradeEmeraldsCount();

    List<Region> getRegionsList();

    /**
     * Get invisibility for armor
     */
    ConcurrentHashMap<Player, Integer> getShowTime();

    void setAllowSpectate(boolean allowSpectate);

    boolean isAllowSpectate();

    String getWorldName();

    /**
     * Get player render distance in blocks.
     */
    int getRenderDistance();

    /**
     * Put a player in re-spawning countdown.
     *
     * @param player  target player.
     * @param seconds countdown in seconds. 0 for instant re-spawn.
     * @return false if the player is not actually in game or if is in another re-spawn session.
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean startReSpawnSession(Player player, int seconds);

    /**
     * Check if a player is in re-spawning screen/ countdown.
     */
    boolean isReSpawning(Player player);

    /**
     * Get re-spawning screen location.
     */
    Location getReSpawnLocation();

    /**
     * Where spectators will spawn.
     */
    Location getSpectatorLocation();

    /**
     * Location where to spawn at join (waiting/ starting).
     */
    Location getWaitingLocation();

    /**
     * Check if the given location is protected.
     * Border checks, regions, island spawn protection, npc protections, generator protections.
     */
    boolean isProtected(Location location);

    /**
     * This is triggered when a player has abandoned a game.
     * This should remove its assist from existing team and re-join session.
     * This does not replace {@link #removePlayer(Player, boolean)}.
     */
    void abandonGame(Player player);

    /**
     * -1 won't handle void kill.
     * Instant kill when player y is under this number.
     */
    int getYKillHeight();

    Instant getStartTime();

    ITeamAssigner getTeamAssigner();

    void setTeamAssigner(ITeamAssigner teamAssigner);

    List<Player> getLeavingPlayers();


    /**
     * Check if breaking map is allowed, otherwise only placed blocks are allowed.
     * Some blocks like have a special protections, like blocks under shopkeepers, bed, ecc.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isAllowMapBreak();

    /**
     * Toggle map block break rule.
     */
    void setAllowMapBreak(boolean allowMapBreak);

    /**
     * Check if there is a player bed at given location.
     */
    boolean isTeamBed(Location location);

    /**
     * Get owner team of a bed based on location.
     */
    @Nullable ITeam getBedsTeam(Location location);

    /**
     * Provides the winner team.
     * This is populated on restarting phase.
     */
    @Nullable ITeam getWinner();
}
