package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Arena a;
    private Player victim, killer;
    private PlayerKillCause cause;
    private String message;

    /**
     * killer can be null
     */
    @Deprecated
    public PlayerKillEvent(Arena a, Player victim, Player killer) {
        this.a = a;
        this.victim = victim;
        this.killer = killer;
        this.message = "";
        this.cause = PlayerKillCause.UNKNOWN;
    }

    /**
     * Killer can be null
     *
     * @since API 9
     */
    public PlayerKillEvent(Arena a, Player victim, Player killer, String message, PlayerKillCause cause) {
        this.a = a;
        this.victim = victim;
        this.killer = killer;
        this.message = message;
        this.cause = cause;
    }

    public enum PlayerKillCause {
        UNKNOWN,
        UNKNOWN_FINAL_KILL,
        EXPLOSION,
        EXPLOSION_FINAL_KILL,
        VOID,
        VOID_FINAL_KILL,
        PVP,
        PVP_FINAL_KILL,
        /**
         * @since API 11
         */
        PLAYER_SHOOT,
        /**
         * @since API 11
         */
        PLAYER_SHOOT_FINAL_KILL,
        /**
         * @since API 11
         */
        SILVERFISH,
        /**
         * @since API 11
         */
        SILVERFISH_FINAL_KILL,
        /**
         * @since API 11
         */
        IRON_GOLEM,
        /**
         * @since API 11
         */
        IRON_GOLEM_FINAL_KILL
    }

    public Player getKiller() {
        return killer;
    }

    /**
     * @since API 9
     */
    public String getMessage() {
        return message;
    }

    /**
     * @since API 9
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @since API 9
     */
    public PlayerKillCause getCause() {
        return cause;
    }

    public Arena getArena() {
        return a;
    }

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
