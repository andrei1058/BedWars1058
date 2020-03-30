package com.andrei1058.bedwars.language;

import com.andrei1058.bedwars.BedWars;
import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

public class PreLoadedCleaner implements Runnable {

    private static boolean started = false;

    private LinkedList<UUID> toRemove = new LinkedList<>();

    private PreLoadedCleaner(){
        started = true;
    }

    public static void init(){
        if (!started){
            Bukkit.getScheduler().runTaskTimerAsynchronously(BedWars.plugin, new PreLoadedCleaner(), 20L, 60L);
        }
    }

    @Override
    public void run() {
        if (!PreLoadedLanguage.getPreLoadedLanguage().isEmpty()) {
            long time = System.currentTimeMillis();
            for (Map.Entry<UUID, PreLoadedLanguage> m : PreLoadedLanguage.getPreLoadedLanguage().entrySet()) {
                if (m.getValue().getTimeout() <= time){
                    toRemove.add(m.getKey());
                }
            }
            toRemove.forEach(PreLoadedLanguage::clear);
            toRemove.clear();
        }
    }
}
