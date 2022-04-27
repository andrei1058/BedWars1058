package com.andrei1058.bedwars.money.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.LanguageService;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.MoneyConfig;
import com.andrei1058.bedwars.language.LanguageManager;
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
        for (UUID p : e.getWinners()) {
            Player player = Bukkit.getPlayer(p);
            if (player == null) continue;
            int gamewin = MoneyConfig.money.getInt("money-rewards.game-win");
            if (gamewin > 0) {
                BedWars.getEconomy().giveMoney(player, gamewin);
                player.sendMessage(getLangService().getMsg(player, Messages.MONEY_REWARD_WIN).replace("{money}", String.valueOf(gamewin)));
            }
        }
        for (UUID p : e.getLosers()) {
            Player player = Bukkit.getPlayer(p);
            if (player == null) continue;
            int teammate = MoneyConfig.money.getInt("money-rewards.per-teammate");
            if (teammate > 0) {
                BedWars.getEconomy().giveMoney(player, teammate);
                player.sendMessage(getLangService().getMsg(player, Messages.MONEY_REWARD_PER_TEAMMATE).replace("{money}", String.valueOf(teammate)));
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
        int beddestroy = MoneyConfig.money.getInt("money-rewards.bed-destroyed");
        if (beddestroy > 0) {
            BedWars.getEconomy().giveMoney(player, beddestroy);
            player.sendMessage(getLangService().getMsg(player, Messages.MONEY_REWARD_BED_DESTROYED).replace("{money}", String.valueOf(beddestroy)));
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
        int finalKill = MoneyConfig.money.getInt("money-rewards.final-kill");
        int regularKill = MoneyConfig.money.getInt("money-rewards.regular-kill");
        if (e.getCause().isFinalKill()) {
            if (finalKill > 0) {
                BedWars.getEconomy().giveMoney(player, finalKill);
                player.sendMessage(getLangService().getMsg(player, Messages.MONEY_REWARD_FINAL_KILL).replace("{money}", String.valueOf(finalKill)));
            }
        } else {
            if (regularKill > 0) {
                BedWars.getEconomy().giveMoney(player, regularKill);
                player.sendMessage(getLangService().getMsg(player, Messages.MONEY_REWARD_REGULAR_KILL).replace("{money}", String.valueOf(regularKill)));
            }
        }
    }

    private static LanguageService getLangService() {
        return LanguageManager.getInstance();
    }
}