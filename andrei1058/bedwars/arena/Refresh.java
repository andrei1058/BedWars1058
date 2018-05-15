package com.andrei1058.bedwars.arena;

import com.andrei1058.bedwars.configuration.Messages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Refresh extends BukkitRunnable {

    public static HashMap<Player, Integer> showTime = new HashMap<>();

    @Override
    public void run() {
        for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
            sb.refresh();
        }

        for (Arena a : new ArrayList<>(Arena.getArenas())) {
            a.refresh();
        }

        for (OreGenerator o : new ArrayList<>(OreGenerator.getGenerators())) {
            o.spawn();
        }

        for (Map.Entry<Player, Integer> e : new HashMap<>(Arena.respawn).entrySet()) {
            if (e.getValue() != 0) {
                nms.sendTitle(e.getKey(), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_TITLE).replace("{time}",
                        String.valueOf(e.getValue())), getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_SUBTITLE).replace("{time}",
                        String.valueOf(e.getValue())), 0, 30, 0);
                e.getKey().sendMessage(getMsg(e.getKey(), Messages.PLAYER_DIE_RESPAWN_CHAT).replace("{time}", String.valueOf(e.getValue())));
            }
            if (e.getValue() == 0) {
                Arena a = Arena.getArenaByPlayer(e.getKey());
                BedWarsTeam t = a.getTeam(e.getKey());
                t.respawnMember(e.getKey());
                Arena.respawn.remove(e.getKey());
            }
            Arena.respawn.replace(e.getKey(), e.getValue() - 1);
        }
        for (Map.Entry<Player, Integer> e : new HashMap<>(showTime).entrySet()){
            if (e.getValue() <= 0){
                showTime.remove(e.getKey());
                for (Player p : e.getKey().getWorld().getPlayers()){
                   nms.showArmor(e.getKey(), p);
                }
            } else {
                showTime.replace(e.getKey(), e.getValue()-1);
            }
        }
        nms.refreshDespawnables();
    }
}
