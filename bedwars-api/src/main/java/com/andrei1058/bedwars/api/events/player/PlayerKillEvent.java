package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.function.Function;

public class PlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final IArena arena;
    private final Player victim;
    private final Player killer;
    private final PlayerKillCause cause;
    private Function<Player, String> message;

    /**
     * Called when a Player got killed during the game.
     *
     * @param killer can be NULL.
     */
    public PlayerKillEvent(IArena arena, Player victim, Player killer, Function<Player, String> message, PlayerKillCause cause) {
        this.arena = arena;
        this.victim = victim;
        this.killer = killer;
        this.message = message;
        this.cause = cause;
    }

    public enum PlayerKillCause {
        UNKNOWN(false, false, false),
        UNKNOWN_FINAL_KILL(true, false, false),
        EXPLOSION(false, false, false),
        EXPLOSION_FINAL_KILL(true, false, false),
        VOID(false, false, false),
        VOID_FINAL_KILL(true, false, false),
        PVP(false, false, false),
        PVP_FINAL_KILL(true, false, false),
        PLAYER_SHOOT(false, false, false),
        PLAYER_SHOOT_FINAL_KILL(true, false, false),
        SILVERFISH(false, true, false),
        SILVERFISH_FINAL_KILL(true, true, false),
        IRON_GOLEM(false, true, false),
        IRON_GOLEM_FINAL_KILL(true, true, false),
        PLAYER_PUSH(false, false, false),
        /**
         * Corresponds to FALL on ground.
         */
        PLAYER_PUSH_FINAL(true, false, false),
        PLAYER_DISCONNECT(false, false, true),
        PLAYER_DISCONNECT_FINAL(true, false, true);

        private final boolean finalKill;
        private final boolean despawnable;
        private final boolean pvpLogOut;

        PlayerKillCause(boolean finalKill, boolean despawnable, boolean pvpLogOut) {
            this.finalKill = finalKill;
            this.despawnable = despawnable;
            this.pvpLogOut = pvpLogOut;
        }

        public boolean isFinalKill() {
            return finalKill;
        }

        /**
         * @return true if killed by a player's ironGolem, silverfish etc.
         */
        public boolean isDespawnable() {
            return despawnable;
        }

        public boolean isPvpLogOut() {
            return pvpLogOut;
        }
    }

    /**
     * Killer can be NULL (void etc.)
     */
    public Player getKiller() {
        return killer;
    }

    /**
     * Get kill chat message.
     */
    public Function<Player, String> getMessage() {
        return message;
    }

    /**
     * Set chat message.
     */
    public void setMessage(Function<Player, String> message) {
        this.message = message;
    }

    /**
     * Get kill cause
     */
    public PlayerKillCause getCause() {
        return cause;
    }

    public IArena getArena() {
        return arena;
    }

    /**
     * Get the Player who died.
     */
    public Player getVictim() {
        return victim;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
