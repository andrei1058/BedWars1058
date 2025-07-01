package com.andrei1058.mc.bedwars.despawnable;

/**
 * Customization of entity.
 *
 * @param type
 * @param speed
 * @param health
 * @param damage
 * @param removalTime entity un-spawn in seconds.
 */
public record DespawnableAttributes(
        DespawnableType type,
        double speed,
        double health,
        double damage,
        int removalTime
) {
}
