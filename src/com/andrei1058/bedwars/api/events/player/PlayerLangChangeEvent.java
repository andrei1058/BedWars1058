package com.andrei1058.bedwars.api.events.player;

import com.andrei1058.bedwars.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.Contract;

public class PlayerLangChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private boolean cancelled = false;
    private Player player;
    private Language oldLang, newLang;

    /**
     * Called when a Player changes his language
     */
    public PlayerLangChangeEvent(Player p, Language oldLang, Language newLang) {
        this.player = p;
        this.oldLang = oldLang;
        this.newLang = newLang;
    }

    /**
     * Check if event is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Change value
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Get Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get old Language
     */
    public Language getOldLang() {
        return oldLang;
    }


    /**
     * Get new Language
     */
    public Language getNewLang() {
        return newLang;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Contract(pure = true)
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
