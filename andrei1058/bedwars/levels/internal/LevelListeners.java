package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.events.GameEndEvent;
import com.andrei1058.bedwars.api.events.PlayerXpGainEvent;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import com.andrei1058.bedwars.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class LevelListeners implements Listener {

    public static LevelListeners instance;

    public LevelListeners() {
        instance = this;
    }

    //create new level data on player join
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            if (PlayerLevel.getLevelByPlayer(e.getPlayer().getUniqueId()) != null) return;
            int[] levelData = Main.getRemoteDatabase().getLevelData(u);
            new PlayerLevel(e.getPlayer().getUniqueId(), levelData[0], levelData[1]);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final UUID u = e.getPlayer().getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            PlayerLevel pl = PlayerLevel.getLevelByPlayer(u);
            pl.destroy();
        });
    }

    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (UUID p : e.getWinners()) {
            if (PlayerLevel.getLevelByPlayer(p) != null) {
                Player p1 = Bukkit.getPlayer(p);
                PlayerLevel.getLevelByPlayer(p).addXp(LevelsConfig.levels.getInt("xp-reward.game-win"), PlayerXpGainEvent.XpSource.GAME_WIN);
                p1.sendMessage(Language.getMsg(p1, "xp-reward-game-win").replace("{xp}", String.valueOf(LevelsConfig.levels.getInt("xp-reward.game-win"))));
                BedWarsTeam bwt = e.getArena().getTeam(p1.getName());
                if (bwt != null) {
                    if (bwt.getMembersCache().size() > 1) {
                        int tr = LevelsConfig.levels.getInt("xp-reward.per-teammate") * bwt.getMembersCache().size();
                        PlayerLevel.getLevelByPlayer(p).addXp(tr, PlayerXpGainEvent.XpSource.PER_TEAMMATE);
                        p1.sendMessage(Language.getMsg(p1, "xp-reward-per-teammate").replace("{xp}", String.valueOf(tr)));
                    }
                }
            }
        }
        for (UUID p : e.getLoosers()) {
            if (PlayerLevel.getLevelByPlayer(p) != null) {
                Player p1 = Bukkit.getPlayer(p);

                BedWarsTeam bwt = e.getArena().getTeam(p1.getName());
                if (bwt != null) {
                    if (bwt.getMembersCache().size() > 1) {
                        int tr = LevelsConfig.levels.getInt("xp-reward.per-teammate") * bwt.getMembersCache().size();
                        PlayerLevel.getLevelByPlayer(p).addXp(tr, PlayerXpGainEvent.XpSource.PER_TEAMMATE);
                        p1.sendMessage(Language.getMsg(p1, "xp-reward-per-teammate").replace("{xp}", String.valueOf(tr)));
                    }
                }
            }
        }
    }
}
