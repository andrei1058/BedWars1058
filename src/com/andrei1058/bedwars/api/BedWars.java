package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.RestoreAdapter;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.events.player.PlayerAfkEvent;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.logging.Level;

public class BedWars implements GameAPI {


    private static RestoreAdapter restoreAdapter;

    @Override
    public ServerType getServerType() {
        return Main.getServerType();
    }

    @Override
    public int getApiVersion() {
        return 14;
    }

    @Override
    public Timestamp getPlayerFirstPlay(Player p) {
        return StatsManager.getStatsCache().getFirstPlay(p.getUniqueId());
    }

    @Override
    public Timestamp getPlayerLastPlay(Player p) {
        return StatsManager.getStatsCache().getLastPlay(p.getUniqueId());
    }

    @Override
    public int getPlayerWins(Player p) {
        return StatsManager.getStatsCache().getWins(p.getUniqueId());
    }

    @Override
    public int getPlayerKills(Player p) {
        return StatsManager.getStatsCache().getKills(p.getUniqueId());
    }

    @Override
    public int getPlayerFinalKills(Player p) {
        return StatsManager.getStatsCache().getFinalKills(p.getUniqueId());
    }

    @Override
    public int getPlayerLooses(Player p) {
        return StatsManager.getStatsCache().getLosses(p.getUniqueId());
    }

    @Override
    public int getPlayerDeaths(Player p) {
        return StatsManager.getStatsCache().getDeaths(p.getUniqueId());
    }

    @Override
    public int getPlayerFinalDeaths(Player p) {
        return StatsManager.getStatsCache().getFinalDeaths(p.getUniqueId());
    }

    @Override
    public int getPlayerBedsDestroyed(Player p) {
        return StatsManager.getStatsCache().getBedsDestroyed(p.getUniqueId());
    }

    @Override
    public int getPlayerGamesPlayed(Player p) {
        return StatsManager.getStatsCache().getGamesPlayed(p.getUniqueId());
    }

    @Override
    public boolean isPlaying(Player p) {
        if (Arena.isInArena(p)) {
            return Arena.getArenaByPlayer(p).isPlayer(p);
        }
        return false;
    }

    @Override
    public boolean isSpectating(Player p) {
        if (Arena.isInArena(p)) {
            return Arena.getArenaByPlayer(p).isSpectator(p);
        }
        return false;
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

    private static HashMap<Player, Integer> afkPlayers = new HashMap<>();

    @Override
    public boolean isPlayerAFK(Player player) {
        return afkPlayers.containsKey(player);
    }

    @Override
    public void setPlayerAFK(Player player, boolean value) {
        if (value) {
            if (!afkPlayers.containsKey(player)) {
                afkPlayers.put(player, Arena.afkCheck.get(player.getUniqueId()));
                Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(player, PlayerAfkEvent.AFKType.START));
            }
        } else {
            if (afkPlayers.containsKey(player)) {
                afkPlayers.remove(player);
                Bukkit.getPluginManager().callEvent(new PlayerAfkEvent(player, PlayerAfkEvent.AFKType.END));
            }
            Arena.afkCheck.remove(player.getUniqueId());
        }
    }

    @Override
    public Integer getPlayerTimeAFK(Player player) {
        if (afkPlayers.containsKey(player)) return afkPlayers.get(player);
        return 0;
    }


}
