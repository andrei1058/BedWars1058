package com.andrei1058.bedwars.metrics;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.support.citizens.JoinNPC;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

import java.util.concurrent.Callable;

public class MetricsManager {

    private static MetricsManager instance;

    private final Metrics metrics;

    private MetricsManager(BedWars plugin) {
        metrics = new Metrics(plugin, 1885);

        // base metrics
        metrics.addCustomChart(new SimplePie("server_type", () -> BedWars.getServerType().toString()));
        metrics.addCustomChart(new SimplePie("default_language", () -> Language.getDefaultLanguage().getIso()));
        metrics.addCustomChart(new SimplePie("auto_scale", () -> String.valueOf(BedWars.autoscale)));
        metrics.addCustomChart(new SimplePie("party_adapter", () -> BedWars.getParty().getClass().getName()));
        metrics.addCustomChart(new SimplePie("chat_adapter", () -> BedWars.getChatSupport().getClass().getName()));
        metrics.addCustomChart(new SimplePie("level_adapter", () -> BedWars.getLevelSupport().getClass().getName()));
        metrics.addCustomChart(new SimplePie("db_adapter", () -> BedWars.getRemoteDatabase().getClass().getName()));
        metrics.addCustomChart(new SimplePie("map_adapter", () -> BedWars.getAPI().getRestoreAdapter().getClass().getName()));
        metrics.addCustomChart(new SimplePie("citizens_support", () -> String.valueOf(JoinNPC.isCitizensSupport())));
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public static void appendPie(String id, Callable<String> callable) {
        if (null == instance) {
            throw new RuntimeException("Metrics manager is not initialized!");
        }
        instance.getMetrics().addCustomChart(new SimplePie(id, callable));
    }

    public static void initService(BedWars plugin) {
        if (null != instance) {
            return;
        }
        instance = new MetricsManager(plugin);
    }
}
