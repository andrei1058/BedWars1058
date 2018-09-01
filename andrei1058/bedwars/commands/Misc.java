package com.andrei1058.bedwars.commands;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Misc {

    /**
     * This is used to spawn armorStands during the setup
     * so the player knows what he set
     *
     * @since api v6
     */
    public static void createArmorStand(String name, Location location) {
        ArmorStand a = (ArmorStand) location.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.ARMOR_STAND);
        a.setVisible(false);
        a.setMarker(true);
        a.setGravity(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
    }
}
