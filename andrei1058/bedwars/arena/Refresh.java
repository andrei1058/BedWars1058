package com.andrei1058.bedwars.arena;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

import static com.andrei1058.bedwars.Main.lang;
import static com.andrei1058.bedwars.Main.nms;
import static com.andrei1058.bedwars.configuration.Language.getMsg;

public class Refresh extends BukkitRunnable {

    //private static List<OreGenerator> temp;
    @Override
    public void run() {
        for (SBoard sb: SBoard.getScoreboards()){
            sb.refresh();
        }
        for (Arena a: Arena.getArenas()){
            a.refresh();
        }
        //temp = new ArrayList<>(OreGenerator.getGenerators());
        for (OreGenerator o : OreGenerator.getGenerators()){
            o.spawn();
        }
        for (SBoard sb : SBoard.getScoreboards()){
            sb.refresh();
        }
        for (Map.Entry<Player, Integer> e : Arena.respawn.entrySet()){
            if (e.getValue() != 0) {
                nms.sendTitle(e.getKey(), getMsg(e.getKey(), lang.youDiedTitle).replace("{time}", String.valueOf(e.getValue())), getMsg(e.getKey(), lang.youDiedSubTitle).replace("{time}", String.valueOf(e.getValue())), 0, 30, 0);
                e.getKey().sendMessage(getMsg(e.getKey(), lang.respawnChat).replace("{time}", String.valueOf(e.getValue())));
            }
            Arena a = Arena.getArenaByPlayer(e.getKey());
            BedWarsTeam t = a.getTeam(e.getKey());
            if (e.getValue() == 0){
                e.getKey().teleport(t.getSpawn());
                t.respawnMember(e.getKey());
                Arena.respawn.remove(e.getKey());
                e.getKey().removePotionEffect(PotionEffectType.INVISIBILITY);
                e.getKey().spigot().setCollidesWithEntities(true);
                e.getKey().setAllowFlight(false);
                e.getKey().setFlying(false);
                e.getKey().setHealth(20);
            }
            Arena.respawn.replace(e.getKey(), e.getValue()-1);
        }
    }
}
