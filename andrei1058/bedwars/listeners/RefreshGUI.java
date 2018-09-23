package com.andrei1058.bedwars.listeners;

import com.andrei1058.bedwars.api.*;
import com.andrei1058.bedwars.arena.ArenaGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RefreshGUI implements Listener {

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent e){
        for (Player p : ArenaGUI.getRefresh().keySet()){
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onPlayerJoinArena(PlayerJoinArenaEvent e){
        for (Player p : ArenaGUI.getRefresh().keySet()){
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent e){
        for (Player p : ArenaGUI.getRefresh().keySet()){
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onArenaEnable(ArenaEnableEvent e){
        for (Player p : ArenaGUI.getRefresh().keySet()){
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }

    @EventHandler
    public void onArenaDisable(ArenaDisableEvent e){
        for (Player p : ArenaGUI.getRefresh().keySet()){
            ArenaGUI.refreshInv(p, ArenaGUI.getRefresh().get(p));
        }
    }
}
