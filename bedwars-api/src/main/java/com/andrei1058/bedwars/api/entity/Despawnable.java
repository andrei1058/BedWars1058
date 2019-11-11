package com.andrei1058.bedwars.api.entity;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class Despawnable {

    private LivingEntity e;
    private ITeam team;
    private int despawn = 250;
    private String namePath;
    private PlayerKillEvent.PlayerKillCause deathRegularCause, deathFinalCause;

    private static BedWars api;

    public Despawnable(LivingEntity e, ITeam team, int despawn, String namePath, PlayerKillEvent.PlayerKillCause deathFinalCause, PlayerKillEvent.PlayerKillCause deathRegularCause) {
        this.e = e;
        this.team = team;
        this.deathFinalCause = deathFinalCause;
        this.deathRegularCause = deathRegularCause;
        if (despawn != 0) {
            this.despawn = despawn;
        }
        this.namePath = namePath;
        if (api == null) api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        api.getVersionSupport().getDespawnablesList().put(e.getUniqueId(), this);
        this.setName();
    }

    public void refresh() {
        if (e.isDead()) {
            api.getVersionSupport().getDespawnablesList().remove(e.getUniqueId());
            return;
        }
        setName();
        despawn--;
        if (despawn == 0) {
            e.damage(9000);
            api.getVersionSupport().getDespawnablesList().remove(e.getUniqueId());
        }
    }

    private void setName() {
        int percentuale = (int) ((e.getHealth() * 100) / e.getMaxHealth() / 10);
        e.setCustomName(api.getDefaultLang().m(namePath).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                new String(new char[percentuale]).replace("\0", api.getDefaultLang()
                        .m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)) + new String(new char[10 - percentuale]).replace("\0", "§7" + api.getDefaultLang()
                        .m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)))
                .replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getDisplayName(api.getDefaultLang())));
    }

    public Entity getEntity() {
        return e;
    }

    public ITeam getTeam() {
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
