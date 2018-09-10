package com.andrei1058.bedwars.commands;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Misc {

    /**
     * This is used to spawn armorStands during the setup
     * so the player knows what he set
     *
     * @since api v6
     */
    public static void createArmorStand(String name, Location location) {
        ArmorStand a = (ArmorStand) location.getWorld().spawnEntity(location.getBlock().getLocation().add(0.5, 2, 0.5), EntityType.ARMOR_STAND);
        a.setVisible(false);
        a.setMarker(true);
        a.setGravity(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
    }

    /**
     * Remove an armor stand
     *
     * @since api v10
     */
    public static void removeArmorStand(String contains, Location location) {
        for (Entity e : location.getWorld().getNearbyEntities(location, 1, 3, 1)){
            if (e.getType() == EntityType.ARMOR_STAND){
                if (!((ArmorStand)e).isVisible()){
                    if (e.getCustomName().contains(contains)){
                        e.remove();
                    }
                }
            }
        }
    }
}
