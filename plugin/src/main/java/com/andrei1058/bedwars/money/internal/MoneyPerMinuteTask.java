package com.andrei1058.bedwars.money.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.configuration.MoneyConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class MoneyPerMinuteTask {

    private final int money = MoneyConfig.money.getInt("money-rewards.per-minute");

    private BukkitTask task;

    /**
     * Create a new per minute money reward.
     */
    public MoneyPerMinuteTask(Arena arena) {
        if (money < 1) {
            return;
        }
        task = Bukkit.getScheduler().runTaskTimer(BedWars.plugin, () -> {
            if (null == arena){
                this.cancel();
                return;
            }
            for (Player p : arena.getPlayers()) {
                BedWars.getEconomy().giveMoney(p, money);
                p.sendMessage(Language.getMsg(p, Messages.MONEY_REWARD_PER_MINUTE).replace("{money}", String.valueOf(money)));
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
