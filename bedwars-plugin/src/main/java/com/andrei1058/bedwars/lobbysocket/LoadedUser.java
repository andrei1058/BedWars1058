package com.andrei1058.bedwars.lobbysocket;

import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoadedUser {

    private UUID uuid;
    private String partyOwnerOrSpectateTarget = null;
    private long requestTime;
    private String arenaIdentifier;
    private Language language = null;

    private static ConcurrentHashMap<UUID, LoadedUser> loaded = new ConcurrentHashMap<>();

    public LoadedUser(String uuid, String arenaIdentifier, String langIso, String partyOwnerOrSpectateTarget){
        if (Bukkit.getWorld(arenaIdentifier) == null) return;
        this.arenaIdentifier = arenaIdentifier;
        this.uuid = UUID.fromString(uuid);
        if (partyOwnerOrSpectateTarget != null){
            if (!partyOwnerOrSpectateTarget.isEmpty()) {
                this.partyOwnerOrSpectateTarget = partyOwnerOrSpectateTarget;
            }
        }
        this.requestTime = System.currentTimeMillis();
        Language l = Language.getLang(langIso);
        if (l != null) language = l;

        loaded.put(this.uuid, this);
    }

    public static boolean isPreLoaded(UUID uuid){
        return loaded.containsKey(uuid);
    }

    public long getRequestTime() {
        return requestTime;
    }

    public String getArenaIdentifier() {
        return arenaIdentifier;
    }

    public void destroy(){
        loaded.remove(uuid);
    }

    public Language getLanguage() {
        return language;
    }

    public static LoadedUser getPreLoaded(UUID uuid){
        return loaded.getOrDefault(uuid, null);
    }

    // if arena is started is used as staff teleport target
    public String getPartyOwnerOrSpectateTarget() {
        return partyOwnerOrSpectateTarget;
    }
}
