package dev.andrei1058.bedwars.common.api.arena;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

public interface DisplayableArena {

    UUID getGameId();

    GameState getGameState();

    boolean isFull();

    String getSpectatePermission();

    // todo language based where null is default server language
    String getDisplayName();

    int getMaxPlayers();

    int getMinPlayers();

    int getCurrentPlayers();

    int getCurrentSpectators();

    int getCurrentVips();

    /**
     * World template used for this game instance.
     *
     * @return
     */
    String getTemplate();

    @Nullable Instant getStartTime();

    boolean isPrivateGame();

    void setPrivateGame();

    void setHost(UUID playerHost);

    @Nullable UUID getPlayerHost();

    String getGroup();

    void setGroup();

    /**
     * Add a player to the game as a Player.
     * This must be used only on WAITING or STARTING states.
     * Will return false if player is already in a game.
     *
     * @param player      user to be added.
     * @param ignoreParty if it does not matter if he is the party owner.
     * @return true if added to the game session.
     */
    boolean joinPlayer(Player player, boolean ignoreParty);

    /**
     * Add a player to the game as a Spectator.
     * @param player user to be added.
     * @param target spectating target (UUID). Null for no spectating target.
     * @param byPass to be defined.
     * @return true if added as spectator successfully.
     */
    boolean joinSpectator(Player player, @Nullable String target, boolean byPass);

    /**
     * Add a player to the game as a Spectator.
     * @param player user to be added.
     * @param target spectating target (UUID). Null for no spectating target.
     * @return true if added as spectator successfully.
     */
    default boolean joinSpectator(Player player, @Nullable String target) {
        return joinSpectator(player, target, false);
    }

    /**
     * Add a player to the game as a Spectator.
     * @param player user to be added.
     * @return true if added as spectator successfully.
     */
    default boolean joinSpectator(Player player) {
        return joinSpectator(player, null, false);
    }

    default int compareTo(@NotNull DisplayableArena other) {
        if (other.getGameState() == GameState.STARTING && getGameState() == GameState.STOPPING) {
            return Integer.compare(other.getCurrentPlayers(), getCurrentPlayers());
        }

        return Integer.compare(other.getGameState().getWeight(), getGameState().getWeight());
    }

    // todo provide language, null for server default
    ItemStack getDisplayItem();

    /**
     * If arena is hosted on this server instance.
     */
    boolean isLocal();
}
