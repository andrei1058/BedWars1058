package com.andrei1058.bedwars.language;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PreLoadedLanguage {

    private static ConcurrentHashMap<UUID, PreLoadedLanguage> preLoadedLanguageConcurrentHashMap = new ConcurrentHashMap<>();

    private String iso;
    private long timeout;

    public PreLoadedLanguage(UUID player, String iso){
        preLoadedLanguageConcurrentHashMap.remove(player);
        this.iso = iso;
        this.timeout = System.currentTimeMillis() + 3000;
        preLoadedLanguageConcurrentHashMap.put(player, this);
    }

    public long getTimeout() {
        return timeout;
    }

    public String getIso() {
        return iso;
    }

    public static ConcurrentHashMap<UUID, PreLoadedLanguage> getPreLoadedLanguage() {
        return preLoadedLanguageConcurrentHashMap;
    }

    public static PreLoadedLanguage getByUUID(UUID player){
        return preLoadedLanguageConcurrentHashMap.getOrDefault(player, null);
    }

    public static void clear(UUID player){
        preLoadedLanguageConcurrentHashMap.remove(player);
    }
}
