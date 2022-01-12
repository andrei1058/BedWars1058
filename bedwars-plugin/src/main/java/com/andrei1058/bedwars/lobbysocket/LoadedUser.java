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

package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoadedUser {

    private static final long waitSeconds = BedWars.config.getYml().getLong(ConfigPath.GENERAL_CONFIGURATION_BUNGEE_OPTION_BWP_TIME_OUT);

    private UUID uuid;
    private String partyOwnerOrSpectateTarget = null;
    private long toleranceTime;
    private String arenaIdentifier;
    private Language language = null;

    private static final ConcurrentHashMap<UUID, LoadedUser> loaded = new ConcurrentHashMap<>();

    public LoadedUser(String uuid, String arenaIdentifier, String langIso, String partyOwnerOrSpectateTarget){
        if (Bukkit.getWorld(arenaIdentifier) == null) return;
        this.arenaIdentifier = arenaIdentifier;
        this.uuid = UUID.fromString(uuid);
        if (partyOwnerOrSpectateTarget != null){
            if (!partyOwnerOrSpectateTarget.isEmpty()) {
                this.partyOwnerOrSpectateTarget = partyOwnerOrSpectateTarget;
            }
        }
        this.toleranceTime = System.currentTimeMillis() + waitSeconds;
        Language l = Language.getLang(langIso);
        if (l != null) language = l;

        loaded.put(this.uuid, this);
    }

    public static boolean isPreLoaded(UUID uuid){
        return loaded.containsKey(uuid);
    }

    public boolean isTimedOut() {
        return System.currentTimeMillis() > this.toleranceTime;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getArenaIdentifier() {
        return arenaIdentifier;
    }

    public void destroy(String reason){
        BedWars.debug("Destroyed PreLoaded User: " + uuid + " Reason: " + reason + ". Tolerance: " + waitSeconds);
        loaded.remove(uuid);
    }

    public Language getLanguage() {
        return language;
    }

    public static LoadedUser getPreLoaded(UUID uuid){
        return loaded.get(uuid);
    }

    // if arena is started is used as staff teleport target
    public String getPartyOwnerOrSpectateTarget() {
        return partyOwnerOrSpectateTarget;
    }

    public static ConcurrentHashMap<UUID, LoadedUser> getLoaded() {
        return loaded;
    }
}
