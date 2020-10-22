package com.andrei1058.bedwars.stats;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class StatsListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            // Do nothing if login fails
            return;
        }
        PlayerStats stats = BedWars.getRemoteDatabase().fetchStats(event.getUniqueId());
        stats.setName(event.getName());
        BedWars.getStatsManager().put(event.getUniqueId(), stats);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            // Prevent memory leak if login fails
            BedWars.getStatsManager().remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onBedBreak(PlayerBedBreakEvent event) {
        PlayerStats stats = BedWars.getStatsManager().get(event.getPlayer().getUniqueId());
        //store beds destroyed
        stats.setBedsDestroyed(stats.getBedsDestroyed() + 1);
    }

    @EventHandler
    public void onPlayerKill(PlayerKillEvent event) {
        PlayerStats victimStats = BedWars.getStatsManager().get(event.getVictim().getUniqueId());
        // If killer is not null and not equal to victim
        PlayerStats killerStats = !event.getVictim().equals(event.getKiller()) ?
                (event.getKiller() == null ? null : BedWars.getStatsManager().getUnsafe(event.getKiller().getUniqueId())) : null;

        if (event.getCause().isFinalKill()) {
            //store final deaths
            victimStats.setFinalDeaths(victimStats.getFinalDeaths() + 1);
            //store losses
            victimStats.setLosses(victimStats.getLosses() + 1);
            //store games played
            victimStats.setGamesPlayed(victimStats.getGamesPlayed() + 1);
            //store final kills
            if (killerStats != null) killerStats.setFinalKills(killerStats.getFinalKills() + 1);
        } else {
            //store deaths
            victimStats.setDeaths(victimStats.getDeaths() + 1);
            //store kills
            if (killerStats != null) killerStats.setKills(killerStats.getKills() + 1);
        }
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        for (UUID uuid : event.getWinners()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            if (!player.isOnline()) continue;

            PlayerStats stats = BedWars.getStatsManager().get(uuid);

            // store wins even if is in another game because he assisted this team
            // the ones who abandoned are already removed from the winners list
            stats.setWins(stats.getWins() + 1);

            // store games played
            // give if he remained in this arena till the end even if was eliminated
            // for those who left games played are updated in arena leave listener
            IArena playerArena = Arena.getArenaByPlayer(player);
            if (playerArena != null && playerArena.equals(event.getArena())) {
                stats.setGamesPlayed(stats.getGamesPlayed() + 1);
            }
        }
    }

    @EventHandler
    public void onArenaLeave(PlayerLeaveArenaEvent event) {
        final Player player = event.getPlayer();

        ITeam team = event.getArena().getExTeam(player.getUniqueId());
        if (team == null) {
            return; // The player didn't play this game
        }

        if (event.getArena().getStatus() == GameState.starting || event.getArena().getStatus() == GameState.waiting) {
            return; // Game didn't start
        }

        PlayerStats playerStats = BedWars.getStatsManager().get(player.getUniqueId());

        // Update last play and first play (if required)
        Instant now = Instant.now();
        playerStats.setLastPlay(now);
        if (playerStats.getFirstPlay() == null) {
            playerStats.setFirstPlay(now);
        }

        // Check quit abuse
        if (event.getArena().getStatus() == GameState.playing) {
            // Only if the player left the arena while the game was running
            if (team.isBedDestroyed()) {
                // Only if the team had the bed destroyed

                // Punish player if bed is destroyed and he disconnects without getting killed
                // if he is not in the spectators list it means he did not pass trough player kill event and he did not receive
                // the penalty bellow.
                if (event.getArena().isPlayer(player)) {
                    playerStats.setFinalDeaths(playerStats.getFinalDeaths() + 1);
                    playerStats.setLosses(playerStats.getLosses() + 1);
                }

                // Reward attacker
                // if attacker is not null it means the victim did pvp log out
                Player damager = event.getLastDamager();
                ITeam killerTeam = event.getArena().getTeam(damager);
                if (damager != null && event.getArena().isPlayer(damager) && killerTeam != null) {
                    PlayerStats damagerStats = BedWars.getStatsManager().get(damager.getUniqueId());
                    damagerStats.setFinalKills(damagerStats.getFinalKills() + 1);
                    event.getArena().addPlayerKill(damager, true, player);

                    // Announce in arena
                    for (Player inGame : event.getArena().getPlayers()) {
                        Language lang = Language.getPlayerLanguage(inGame);
                        inGame.sendMessage(Language.getMsg(inGame, Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL)
                                .replace("{PlayerTeamName}", team.getDisplayName(lang))
                                .replace("{PlayerColor}", team.getColor().chat().toString()).replace("{PlayerName}", player.getDisplayName())
                                .replace("{KillerColor}", killerTeam.getColor().chat().toString())
                                .replace("{KillerName}", damager.getDisplayName())
                                .replace("{KillerTeamName}", killerTeam.getDisplayName(lang)));
                    }
                    for (Player inGame : event.getArena().getSpectators()) {
                        Language lang = Language.getPlayerLanguage(inGame);
                        inGame.sendMessage(Language.getMsg(inGame, Messages.PLAYER_DIE_PVP_LOG_OUT_FINAL)
                                .replace("{PlayerTeamName}", team.getDisplayName(lang))
                                .replace("{PlayerColor}", team.getColor().chat().toString()).replace("{PlayerName}", player.getDisplayName())
                                .replace("{KillerColor}", killerTeam.getColor().chat().toString())
                                .replace("{KillerName}", damager.getDisplayName())
                                .replace("{KillerTeamName}", killerTeam.getDisplayName(lang)));
                    }
                }
            } else {
                // Prevent pvp log out abuse
                Player damager = event.getLastDamager();
                ITeam killerTeam = event.getArena().getTeam(damager);

                // killer is null if if he already received kill point.
                // LastHit damager is set to null at PlayerDeathEvent so this part is not duplicated for sure.
                // damager is not null if the victim disconnected during pvp only.
                if (event.getLastDamager() != null && event.getArena().isPlayer(damager) && killerTeam != null) {
                    // Punish player
                    playerStats.setDeaths(playerStats.getDeaths() + 1);
                    event.getArena().addPlayerDeath(player);

                    // Reward attacker
                    event.getArena().addPlayerKill(damager, false, player);
                    PlayerStats damagerStats = BedWars.getStatsManager().get(damager.getUniqueId());
                    damagerStats.setKills(damagerStats.getKills() + 1);

                    // Announce in arena
                    for (Player inGame : event.getArena().getPlayers()) {
                        Language lang = Language.getPlayerLanguage(inGame);
                        inGame.sendMessage(Language.getMsg(inGame, Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR)
                                .replace("{PlayerTeamName}", team.getDisplayName(lang))
                                .replace("{PlayerColor}", team.getColor().chat().toString()).replace("{PlayerName}", player.getDisplayName())
                                .replace("{KillerColor}", killerTeam.getColor().chat().toString())
                                .replace("{KillerName}", damager.getDisplayName())
                                .replace("{KillerTeamName}", killerTeam.getDisplayName(lang)));
                    }
                    for (Player inGame : event.getArena().getSpectators()) {
                        Language lang = Language.getPlayerLanguage(inGame);
                        inGame.sendMessage(Language.getMsg(inGame, Messages.PLAYER_DIE_PVP_LOG_OUT_REGULAR)
                                .replace("{PlayerTeamName}", team.getDisplayName(lang))
                                .replace("{PlayerColor}", team.getColor().chat().toString()).replace("{PlayerName}", player.getDisplayName())
                                .replace("{KillerColor}", killerTeam.getColor().chat().toString())
                                .replace("{KillerName}", damager.getDisplayName())
                                .replace("{KillerTeamName}", killerTeam.getDisplayName(lang)));
                    }
                }
            }
        }

        //save or replace stats for player
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.plugin, () -> BedWars.getRemoteDatabase().saveStats(playerStats));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        BedWars.getStatsManager().remove(event.getPlayer().getUniqueId());
    }


    private static class DuplicationPatch {

        private static final LinkedHashMap<UUID, DuplicationPatch> duplicationPatch = new LinkedHashMap<>();

        // if already received those
        private boolean win;
        private boolean lost;
        private boolean finalDeath;
        private boolean gamePlayed;

        // last time this action was performed
        private long lastDeath;
        private long lastKill;

        // if already final killed someone
        private final List<UUID> finalKills = new LinkedList<>();

        // prevent outside instantiation
        private DuplicationPatch() {
        }

        /**
         * Set last kill performed to now.
         */
        private void updateLastKill() {
            lastKill = System.currentTimeMillis();
        }

        /**
         * Check kill delay.
         */
        private boolean passKillDelay(UUID uuid) {
            return System.currentTimeMillis() > lastKill + 3000L;
        }

        /**
         * Get cached data.
         */
        private static DuplicationPatch getPlayer(UUID uuid) {
            return duplicationPatch.get(uuid);
        }

        /**
         * Create duplication patch for player stats.
         */
        private static DuplicationPatch createPlayer(UUID uuid) {
            return duplicationPatch.put(uuid, new DuplicationPatch());
        }

        /**
         * Get cached data or create new.
         */
        private static DuplicationPatch getOrCreatePlayer(UUID uuid) {
            return duplicationPatch.getOrDefault(uuid, createPlayer(uuid));
        }
    }
}
