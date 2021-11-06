package com.andrei1058.bedwars.money.internal;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.configuration.MoneyConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class MoneyListeners implements Listener {

    @EventHandler
    public void onGameEnd(GameEndEvent e) {
        for (UUID p : e.getWinners()) {
            Player player = Bukkit.getPlayer(p);
            if (player == null) continue;
            int gamewin = MoneyConfig.money.getInt("money-rewards.game-win");
            if (gamewin > 0){
                BedWars.getEconomy ().giveMoney ( player, gamewin );
                player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_WIN).replace("{money}", String.valueOf(gamewin)));
            }
            ITeam team = e.getArena().getExTeam(player.getUniqueId());
            if (team != null) {
                //noinspection deprecation
                if (team.getMembersCache().size() > 1) {
                    int moneyperteammate = MoneyConfig.money.getInt("money-rewards.per-teammate");
                    if (moneyperteammate > 0){
                        //noinspection deprecation
                        int tr = moneyperteammate * team.getMembersCache().size();
                        BedWars.getEconomy ().giveMoney ( player, tr );
                        player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_PER_TEAMMATE).replace("{money}", String.valueOf(tr)));
                    }
                }
            }
        }

        for (UUID p : e.getLosers()) {
            Player player = Bukkit.getPlayer(p);
            if (player == null) continue;
            ITeam team = e.getArena().getExTeam(player.getUniqueId());
            if (team != null) {
                //noinspection deprecation
                if (team.getMembersCache().size() > 1) {
                    int moneyperteammate = MoneyConfig.money.getInt("money-rewards.per-teammate");
                    if (moneyperteammate > 0){
                        //noinspection deprecation
                        int tr = MoneyConfig.money.getInt("money-rewards.per-teammate") * team.getMembersCache().size();
                        BedWars.getEconomy ().giveMoney ( player, tr );
                        player.sendMessage(Language.getMsg(player, Messages.MONEY_REWARD_PER_TEAMMATE).replace("{money}", String.valueOf(tr)));
                    }
                }
            }
        }
    }


    @EventHandler
    public void onBreakBed(PlayerBedBreakEvent e) {
        Player player = e.getPlayer ();
        if (player == null) return;
        int beddestroy = MoneyConfig.money.getInt ( "money-rewards.bed-destroyed" );
        if (beddestroy > 0) {
            BedWars.getEconomy ().giveMoney ( player, beddestroy );
            player.sendMessage ( Language.getMsg ( player, Messages.MONEY_REWARD_BED_DESTROYED ).replace ( "{money}", String.valueOf ( beddestroy ) ) );
        }

        for (Player p : e.getPlayerTeam().getMembers()) {
            if (p == null) return;
            ITeam team = e.getArena ().getExTeam ( p.getUniqueId () );
            if (team == null) return;
            //noinspection deprecation
            if (team.getMembersCache ().size () > 1) {
                int teammate = MoneyConfig.money.getInt ( "money-rewards.team-bed-destroyed" );
                if (teammate > 0) {
                    //noinspection deprecation
                    int tr = teammate * team.getMembersCache ().size ();
                    BedWars.getEconomy ().giveMoney ( p, tr );
                    p.sendMessage ( Language.getMsg ( p, Messages.MONEY_REWARD_TEAM_BED_DESTROYED ).replace ( "{money}", String.valueOf ( tr ) ) );
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        Player player = e.getKiller ();
        if (player == null) return;
        int finalkill = MoneyConfig.money.getInt ( "money-rewards.final-kill" );
        int regularkill = MoneyConfig.money.getInt ( "money-rewards.regular-kill" );
        if (e.getCause ().isFinalKill ()) {
            if (finalkill > 0) {
                BedWars.getEconomy ().giveMoney ( player, finalkill );
                player.sendMessage ( Language.getMsg ( player, Messages.MONEY_REWARD_FINAL_KILL ).replace ( "{money}", String.valueOf ( finalkill ) ) );
            }
        } else {
            if (regularkill > 0) {
                BedWars.getEconomy ().giveMoney ( player, regularkill );
                player.sendMessage ( Language.getMsg ( player, Messages.MONEY_REWARD_REGULAR_KILL ).replace ( "{money}", String.valueOf ( regularkill ) ) );
            }
        }
    }
}