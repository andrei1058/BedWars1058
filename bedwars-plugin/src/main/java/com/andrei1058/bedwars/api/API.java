package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.events.player.PlayerAfkEvent;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;


public class API implements com.andrei1058.bedwars.api.BedWars {

    private static RestoreAdapter restoreAdapter;
    private AFKUtil afkSystem = new AFKUtil() {
        private HashMap<UUID, Integer> afkPlayers = new HashMap<>();

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
    };

    private ArenaUtil arenaUtil = new ArenaUtil() {
        @Override
        public void removeFromEnableQueue(IArena a) {
            Arena.removeFromEnableQueue(a);
        }

        @Override
        public boolean isPlaying(Player p) {
            return Arena.isInArena(p);
        }

        @Override
        public boolean isSpectating(Player p) {
            return Arena.isInArena(p) && Arena.getArenaByPlayer(p).isSpectator(p);
        }

        @Override
        public void loadArena(String worldName, Player sender) {
            new Arena(worldName, sender);
        }

        @Override
        public void setGamesBeforeRestart(int games) {
            Arena.setGamesBeforeRestart(games);
        }

        @Override
        public int getGamesBeforeRestart() {
            return Arena.getGamesBeforeRestart();
        }

        @Override
        public IArena getArenaByPlayer(Player player) {
            return Arena.getArenaByPlayer(player);
        }
    };

    private Configs configs = new Configs() {
        @Override
        public ConfigManager getMainConfig() {
            return BedWars.config;
        }

        @Override
        public ConfigManager getSignsConfig() {
            return BedWars.signs;
        }

        @Override
        public ConfigManager getGeneratorsConfig() {
            return BedWars.generators;
        }

        @Override
        public ConfigManager getShopConfig() {
            return BedWars.shop;
        }

        @Override
        public ConfigManager getUpgradesConfig() {
            return BedWars.upgrades;
        }
    };

    @Override
    public IStats getStatsCache() {
        return StatsManager.getStatsCache();
    }

    @Override
    public AFKUtil getAFKSystem() {
        return afkSystem;
    }

    @Override
    public ArenaUtil getArenaUtil() {
        return arenaUtil;
    }

    @Override
    public Configs getConfigs() {
        return configs;
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
        return BedWars.getServerType();
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
            if (adapter.getOwner() != BedWars.plugin) {
                BedWars.plugin.getLogger().log(Level.WARNING, adapter.getOwner().getName() + " changed the restore system to its own adapter.");
            }
        }
    }

    @Override
    public VersionSupport getVersionSupport() {
        return BedWars.nms;
    }

    @Override
    public Language getDefaultLang() {
        return Language.getDefaultLanguage();
    }

    @Override
    public String getLobbyWorld() {
        return BedWars.getLobbyWorld();
    }

    @Override
    public String getLangIso(Player p) {
        return Language.getPlayerLanguage(p).getIso();
    }
}
