package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.events.player.PlayerAfkEvent;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;


public class API implements BedWars {

    private static RestoreAdapter restoreAdapter;
    private IAFK afkSystem = new AFKUtil();

    @Override
    public IStats getStatsCache() {
        return StatsManager.getStatsCache();
    }

    @Override
    public IAFK getAFKSystem() {
        return afkSystem;
    }

    private static class AFKUtil implements BedWars.IAFK {

        private static HashMap<UUID, Integer> afkPlayers = new HashMap<>();

        @Override
        public boolean isPlayerAFK(Player player) {
            return afkPlayers.containsKey(player.getUniqueId());
        }

        @Override
        public void setPlayerAFK(Player player, boolean value) {
            if (value) {
                if (!afkPlayers.containsKey(player.getUniqueId())) {
                    afkPlayers.put(player.getUniqueId(), Arena.afkCheck.get(player.getUniqueId()));
                    Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(player, PlayerAfkEvent.AFKType.START));
                }
            } else {
                if (afkPlayers.containsKey(player.getUniqueId())) {
                    afkPlayers.remove(player.getUniqueId());
                    Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(player, PlayerAfkEvent.AFKType.END));
                }
                Arena.afkCheck.remove(player.getUniqueId());
            }
        }

        @Override
        public int getPlayerTimeAFK(Player player) {
            if (afkPlayers.containsKey(player.getUniqueId())) return afkPlayers.get(player.getUniqueId());
            return 0;
        }
    }

    @Override
    public ISetupSession getSetupSession(UUID player) {
        return SetupSession.getSession(player);
    }

    @Override
    public boolean isInSetupSession(UUID player) {
        return SetupSession.isInSetupSession(player);
    }

    @Override
    public ServerType getServerType() {
        return Main.getServerType();
    }

    @Override
    public boolean isPlaying(Player p) {
        return Arena.isInArena(p) && Arena.getArenaByPlayer(p).isPlayer(p);
    }

    @Override
    public boolean isSpectating(Player p) {
        return Arena.isInArena(p) && Arena.getArenaByPlayer(p).isSpectator(p);
    }

    @Override
    public ParentCommand getBedWarsCommand() {
        return MainCommand.getInstance();
    }

    @Override
    public RestoreAdapter getRestoreAdapter() {
        return restoreAdapter;
    }

    @Override
    public void setRestoreAdapter(RestoreAdapter adapter) throws IllegalAccessError {
        if (!Arena.getArenas().isEmpty()) {
            throw new IllegalAccessError("Arenas must be unloaded when changing the adapter");
        }
        restoreAdapter = adapter;
        if (adapter.getOwner() != null) {
            if (adapter.getOwner() != Main.plugin) {
                Main.plugin.getLogger().log(Level.WARNING, adapter.getOwner().getName() + " changed the restore system to its own adapter.");
            }
        }
    }

    @Override
    public String getLangIso(Player p) {
        return Language.getPlayerLanguage(p).getIso();
    }
}
