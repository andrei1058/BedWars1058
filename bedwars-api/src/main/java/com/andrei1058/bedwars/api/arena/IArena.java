package com.andrei1058.bedwars.api.arena;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IArena {

    /**
     * Check if a player is spectating on this arena.
     */
    boolean isSpectator(Player player);

    /**
     * Get used world name.
     */
    String getWorldName();

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
}
