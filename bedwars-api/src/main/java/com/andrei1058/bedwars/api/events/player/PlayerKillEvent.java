package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.api.arena.IArena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private IArena a;
    private final Player victim;
    private final Player killer;
    private final PlayerKillCause cause;
    private String message;

    /**
     * Called when a Player got killed during the game.
     *
     * @param killer can be NULL.
     */
    public PlayerKillEvent(IArena a, Player victim, Player killer, String message, PlayerKillCause cause) {
        this.a = a;
        this.victim = victim;
        this.killer = killer;
        this.message = message;
        this.cause = cause;
    }

    public enum PlayerKillCause {
        UNKNOWN(false),
        UNKNOWN_FINAL_KILL(true),
        EXPLOSION(false),
        EXPLOSION_FINAL_KILL(true),
        VOID(false),
        VOID_FINAL_KILL(true),
        PVP(false),
        PVP_FINAL_KILL(true),
        PLAYER_SHOOT(false),
        PLAYER_SHOOT_FINAL_KILL(true),
        SILVERFISH(false),
        SILVERFISH_FINAL_KILL(true),
        IRON_GOLEM(false),
        IRON_GOLEM_FINAL_KILL(true),
        PLAYER_PUSH(false),
        PLAYER_PUSH_FINAL(true);

        private final boolean finalKill;

        PlayerKillCause(boolean finalKill) {
            this.finalKill = finalKill;
        }

        public boolean isFinalKill() {
            return finalKill;
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
    public String getMessage() {
        return message;
    }

    /**
     * Set chat message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get kill cause
     */
    public PlayerKillCause getCause() {
        return cause;
    }

    public IArena getArena() {
        return a;
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
