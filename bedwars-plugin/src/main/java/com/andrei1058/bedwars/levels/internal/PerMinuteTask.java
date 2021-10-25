package com.andrei1058.bedwars.levels.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.player.PlayerXpGainEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.LevelsConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class PerMinuteTask {

    private final int xp = LevelsConfig.levels.getInt("xp-rewards.per-minute");
    private BukkitTask task;

    /**
     * Create a new per minute xp reward.
     */
    public PerMinuteTask(Arena arena) {
        if (xp < 1){
            return;
        }
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, () -> {
            for (Player p : arena.getPlayers()) {
                PlayerLevel.getLevelByPlayer(p.getUniqueId()).addXp(xp, PlayerXpGainEvent.XpSource.PER_MINUTE);
                p.sendMessage(Language.getMsg(p, Messages.XP_REWARD_PER_MINUTE).replace("{xp}", String.valueOf(xp)));
            }
        }, 60 * 20, 60 * 20);
    }

    /**
     * Cancel task.
     */
    public void cancel() {
        if (task != null) {
            task.cancel();
        }
    }
}
