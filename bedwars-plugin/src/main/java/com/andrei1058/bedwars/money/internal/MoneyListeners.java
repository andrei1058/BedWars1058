package com.andrei1058.bedwars.money.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.events.player.PlayerMoneyGainEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.MoneyConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MoneyListeners implements Listener {

    /**
     * Create a new winner / loser money reward.
     */
    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (UUID uuid : e.getWinners()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            int moneyPerWin = MoneyConfig.money.getInt("money-rewards.game-win");
            if (moneyPerWin > 0) {
                BedWars.getEconomy().giveMoney(player, moneyPerWin);
                player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_WIN).replace("{money}", String.valueOf(moneyPerWin)));
                Bukkit.getPluginManager().callEvent(new PlayerMoneyGainEvent(player, moneyPerWin));
            }
        }
        for (UUID uuid : e.getLosers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            int moneyPerTeammate = MoneyConfig.money.getInt("money-rewards.per-teammate");
            if (moneyPerTeammate > 0) {
                BedWars.getEconomy().giveMoney(player, moneyPerTeammate);
                player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_PER_TEAMMATE).replace("{money}", String.valueOf(moneyPerTeammate)));
                Bukkit.getPluginManager().callEvent(new PlayerMoneyGainEvent(player, moneyPerTeammate));
            }
        }
    }

    /**
     * Create a new bed destroyed money reward.
     */
    @EventHandler
    public void onBreakBed(PlayerBedBreakEvent e) {
        Player player = e.getPlayer();
        if (player == null) return;
        int moneyPerBedDestroy = MoneyConfig.money.getInt("money-rewards.bed-destroyed");
        if (moneyPerBedDestroy > 0) {
            BedWars.getEconomy().giveMoney(player, moneyPerBedDestroy);
            player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_BED_DESTROYED).replace("{money}", String.valueOf(moneyPerBedDestroy)));
            Bukkit.getPluginManager().callEvent(new PlayerMoneyGainEvent(player, moneyPerBedDestroy));
        }
    }

    /**
     * Create a kill money reward.
     */
    @EventHandler
    public void onKill(PlayerKillEvent e) {
        Player player = e.getKiller();
        Player victim = e.getVictim();
        if (player == null || victim.equals(player)) return;
        int moneyPerFinalKill = MoneyConfig.money.getInt("money-rewards.final-kill");
        int moneyPerRegularKill = MoneyConfig.money.getInt("money-rewards.regular-kill");
        if (e.getCause().isFinalKill()) {
            if (moneyPerFinalKill > 0) {
                BedWars.getEconomy().giveMoney(player, moneyPerFinalKill);
                player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_FINAL_KILL).replace("{money}", String.valueOf(moneyPerFinalKill)));
                Bukkit.getPluginManager().callEvent(new PlayerMoneyGainEvent(player, moneyPerFinalKill));
            }
        } else {
            if (moneyPerRegularKill > 0) {
                BedWars.getEconomy().giveMoney(player, moneyPerRegularKill);
                player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_REGULAR_KILL).replace("{money}", String.valueOf(moneyPerRegularKill)));
                Bukkit.getPluginManager().callEvent(new PlayerMoneyGainEvent(player, moneyPerRegularKill));
            }
        }
    }
}