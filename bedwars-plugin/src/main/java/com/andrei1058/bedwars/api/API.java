package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IContentTier;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.events.player.PlayerAfkEvent;
import com.andrei1058.bedwars.api.party.Party;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
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
        public void addToEnableQueue(IArena a) {
            Arena.removeFromEnableQueue(a);
        }

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

        @Override
        public void setArenaByPlayer(Player p, IArena arena) {
            Arena.setArenaByPlayer(p, arena);
        }

        @Override
        public void removeArenaByPlayer(Player p, IArena a) {
            Arena.removeArenaByPlayer(p, a);
        }

        @Override
        public IArena getArenaByName(String worldName) {
            return Arena.getArenaByName(worldName);
        }

        @Override
        public void setArenaByName(IArena arena) {
            Arena.setArenaByName(arena);
        }

        @Override
        public void removeArenaByName(String worldName) {
            Arena.removeArenaByName(worldName);
        }

        @Override
        public LinkedList<IArena> getArenas() {
            return Arena.getArenas();
        }

        @Override
        public boolean vipJoin(Player p) {
            return Arena.isVip(p);
        }

        @Override
        public int getPlayers(String group) {
            return Arena.getPlayers(group);
        }

        @Override
        public boolean joinRandomArena(Player p) {
            return Arena.joinRandomArena(p);
        }

        @Override
        public boolean joinRandomFromGroup(Player p, String group) {
            return Arena.joinRandomFromGroup(p, group);
        }

        @Override
        public LinkedList<IArena> getEnableQueue() {
            return Arena.getEnableQueue();
        }

        @Override
        public void sendLobbyCommandItems(Player p) {
            Arena.sendLobbyCommandItems(p);
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

    private ShopUtil shopUtil = new ShopUtil() {
        @Override
        public int calculateMoney(Player player, Material currency) {
            return CategoryContent.calculateMoney(player, currency);
        }

        @Override
        public Material getCurrency(String currency) {
            return CategoryContent.getCurrency(currency);
        }

        @Override
        public ChatColor getCurrencyColor(Material currency) {
            return CategoryContent.getCurrencyColor(currency);
        }

        @Override
        public String getCurrencyMsgPath(IContentTier contentTier) {
            return CategoryContent.getCurrencyMsgPath(contentTier);
        }

        @Override
        public String getRomanNumber(int n) {
            return CategoryContent.getRomanNumber(n);
        }

        @Override
        public void takeMoney(Player player, Material currency, int amount) {
            CategoryContent.takeMoney(player, currency, amount);
        }
    };

    @Override
    public IStats getStatsUtil() {
        return StatsManager.getStatsCache();
    }

    @Override
    public AFKUtil getAFKUtil() {
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
    public ShopUtil getShopUtil() {
        return shopUtil;
    }

    @Override
    public com.andrei1058.bedwars.api.levels.Level getLevelsUtil() {
        return null;
    }

    @Override
    public Party getPartyUtil() {
        return BedWars.getParty();
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
    public String getForCurrentVersion(String v18, String v12, String v13) {
        return BedWars.getForCurrentVersion(v18, v12, v13);
    }

    @Override
    public void setLevelAdapter(com.andrei1058.bedwars.api.levels.Level level) {
        BedWars.setLevelAdapter(level);
    }

    @Override
    public String getLangIso(Player p) {
        return Language.getPlayerLanguage(p).getIso();
    }
}
