package com.andrei1058.bedwars.support.version;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.language.Messages;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import static com.andrei1058.bedwars.Main.lang;

public class Despawnable {

    private LivingEntity e;
    private BedWarsTeam team;
    private int despawn = 250;
    private String namePath;
    private PlayerKillEvent.PlayerKillCause deathRegularCause, deathFinalCause;

    public Despawnable(LivingEntity e, BedWarsTeam team, int despawn, String namePath, PlayerKillEvent.PlayerKillCause deathFinalCause, PlayerKillEvent.PlayerKillCause deathRegularCause) {
        this.e = e;
        this.team = team;
        this.deathFinalCause = deathFinalCause;
        this.deathRegularCause = deathRegularCause;
        if (despawn != 0) {
            this.despawn = despawn;
        }
        this.namePath = namePath;
        Main.nms.getDespawnablesList().put(e.getUniqueId(), this);
        this.setName();
    }

    public void refresh() {
        if (e.isDead()) {
            Main.nms.getDespawnablesList().remove(e.getUniqueId());
            return;
        }
        setName();
        despawn--;
        if (despawn == 0) {
            e.damage(9000);
            Main.nms.getDespawnablesList().remove(e.getUniqueId());
        }
    }

    private void setName() {
        int percentuale = (int) ((e.getHealth() * 100) / e.getMaxHealth() / 10);
        e.setCustomName(lang.m(namePath).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                new String(new char[percentuale]).replace("\0", lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)) + new String(new char[10 - percentuale]).replace("\0", "§7" + lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)))
                .replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getName()));
    }

    public Entity getE() {
        return e;
    }

    public BedWarsTeam getTeam() {
        return team;
    }

    public int getDespawn() {
        return despawn;
    }

    public PlayerKillEvent.PlayerKillCause getDeathFinalCause() {
        return deathFinalCause;
    }

    public PlayerKillEvent.PlayerKillCause getDeathRegularCause() {
        return deathRegularCause;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LivingEntity) return ((LivingEntity) obj).getUniqueId().equals(e.getUniqueId());
        return false;
    }
}
