/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.IContentTier;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import com.andrei1058.bedwars.api.events.player.PlayerAfkEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.party.Party;
import com.andrei1058.bedwars.api.server.ISetupSession;
import com.andrei1058.bedwars.api.server.RestoreAdapter;
import com.andrei1058.bedwars.api.server.ServerType;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.api.sidebar.ISidebarService;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.shop.main.CategoryContent;
import com.andrei1058.bedwars.sidebar.SidebarService;
import com.andrei1058.bedwars.stats.StatsAPI;
import com.andrei1058.bedwars.upgrades.UpgradesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;

public class API implements com.andrei1058.bedwars.api.BedWars {

    private static RestoreAdapter restoreAdapter;
    private final AFKUtil afkSystem = new AFKUtil() {
        private final HashMap<UUID, Integer> afkPlayers = new HashMap<>();

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
            return afkPlayers.getOrDefault(player.getUniqueId(), 0);
        }
    };

    private final ArenaUtil arenaUtil = new ArenaUtil() {
        @Override
        public boolean canAutoScale(String arenaName) {
            return Arena.canAutoScale(arenaName);
        }

        @Override
        public void addToEnableQueue(IArena a) {
            Arena.addToEnableQueue(a);
        }

        @Override
        public void removeFromEnableQueue(IArena a) {
            Arena.removeFromEnableQueue(a);
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
        public IArena getArenaByIdentifier(String worldName) {
            return Arena.getArenaByIdentifier(worldName);
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

    private final Configs configs = new Configs() {
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
            return UpgradesManager.getConfiguration();
        }
    };

    private final ShopUtil shopUtil = new ShopUtil() {
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
        return StatsAPI.getInstance();
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

    private final TeamUpgradesUtil teamUpgradesUtil = new TeamUpgradesUtil() {
        @Override
        public boolean isWatchingGUI(Player player) {
            return UpgradesManager.isWatchingUpgrades(player.getUniqueId());
        }

        @Override
        public void setWatchingGUI(Player player) {
            UpgradesManager.setWatchingUpgrades(player.getUniqueId());
        }

        @Override
        public void removeWatchingUpgrades(UUID uuid) {
            UpgradesManager.removeWatchingUpgrades(uuid);
        }

        @Override
        public int getTotalUpgradeTiers(IArena arena) {
            return UpgradesManager.getMenuForArena(arena).countTiers();
        }
    };

    @Override
    public TeamUpgradesUtil getTeamUpgradesUtil() {
        return teamUpgradesUtil;
    }

    @Override
    public com.andrei1058.bedwars.api.levels.Level getLevelsUtil() {
        return BedWars.getLevelSupport();
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
    public void setPartyAdapter(Party partyAdapter) throws IllegalAccessError {
        if (partyAdapter == null) return;
        if (partyAdapter.equals(BedWars.getParty())) return;
        BedWars.setParty(partyAdapter);
        BedWars.plugin.getLogger().log(Level.WARNING,  "One of your plugins changed the Party adapter to: " + partyAdapter.getClass().getName());
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
    public boolean isAutoScale() {
        return BedWars.autoscale;
    }

    @Override
    public Language getLanguageByIso(String isoCode) {
        return Language.getLang(isoCode);
    }

    @Override
    public Language getPlayerLanguage(Player player) {
        return Language.getPlayerLanguage(player);
    }

    @Override
    public String getLangIso(Player p) {
        return Language.getPlayerLanguage(p).getIso();
    }

    @Override
    public File getAddonsPath() {
        return new File(BedWars.plugin.getDataFolder(), "Addons");
    }


    private static final ScoreboardUtil scoreboardUtil = new ScoreboardUtil() {

        @Override
        public void removePlayerScoreboard(Player player) {
            SidebarService.getInstance().remove(player);
        }

        @Override
        public void givePlayerScoreboard(@NotNull Player player, boolean delay) {
            SidebarService.getInstance().giveSidebar(player, Arena.getArenaByPlayer(player), delay);
        }
    };

    @Override
    public ScoreboardUtil getScoreboardUtil() {
        return scoreboardUtil;
    }

    @Override
    public boolean isShuttingDown() {
        return BedWars.isShuttingDown();
    }

    @Override
    public ISidebarService getScoreboardManager() {
        return SidebarService.getInstance();
    }
}
