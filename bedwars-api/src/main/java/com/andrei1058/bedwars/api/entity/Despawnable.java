/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.entity;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

public class Despawnable {

    private LivingEntity e;
    private ITeam team;
    private int despawn = 250;
    private String namePath;
    private PlayerKillEvent.PlayerKillCause deathRegularCause, deathFinalCause;
    private UUID uuid;

    private static BedWars api;

    public Despawnable(LivingEntity e, ITeam team, int despawn, String namePath, PlayerKillEvent.PlayerKillCause deathFinalCause, PlayerKillEvent.PlayerKillCause deathRegularCause) {
        this.e = e;
        if (e == null) return;
        this.uuid = e.getUniqueId();
        this.team = team;
        this.deathFinalCause = deathFinalCause;
        this.deathRegularCause = deathRegularCause;
        if (despawn != 0) {
            this.despawn = despawn;
        }
        this.namePath = namePath;
        if (api == null) api = Bukkit.getServer().getServicesManager().getRegistration(BedWars.class).getProvider();
        api.getVersionSupport().getDespawnablesList().put(uuid, this);
        this.setName();
    }

    public void refresh() {
        if (e.isDead() || e == null || team == null || team.getArena() == null) {
            api.getVersionSupport().getDespawnablesList().remove(uuid);
            if (team.getArena() == null){
                e.damage(e.getHealth()+100);
            }
            return;
        }
        setName();
        despawn--;
        if (despawn == 0) {
            e.damage(e.getHealth()+100);
            api.getVersionSupport().getDespawnablesList().remove(e.getUniqueId());
        }
    }

    private void setName() {
        int percentuale = (int) ((e.getHealth() * 100) / e.getMaxHealth() / 10);
        String name = api.getDefaultLang().m(namePath).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                new String(new char[percentuale]).replace("\0", api.getDefaultLang()
                        .m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)) + new String(new char[10 - percentuale]).replace("\0", "§7" + api.getDefaultLang()
                        .m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)));
        if (team != null) {
            name = name.replace("{TeamColor}", team.getColor().chat().toString()).replace("{TeamName}", team.getDisplayName(api.getDefaultLang()));
        }
        e.setCustomName(name);
    }

    public LivingEntity getEntity() {
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

    public void destroy(){
        if (getEntity() != null){
            getEntity().damage(Integer.MAX_VALUE);
        }
        team = null;
        api.getVersionSupport().getDespawnablesList().remove(uuid);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LivingEntity) return ((LivingEntity) obj).getUniqueId().equals(e.getUniqueId());
        return false;
    }
}
