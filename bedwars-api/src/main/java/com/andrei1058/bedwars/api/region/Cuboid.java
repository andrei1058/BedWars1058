package com.andrei1058.bedwars.api.region;

import org.bukkit.Location;

public class Cuboid implements Region {

    private int minX, maxX;
    private int minY, maxY;
    private int minZ, maxZ;

    private boolean protect;

    public Cuboid(Location loc, int radius, boolean protect) {
        Location l1 = loc.clone().subtract(radius, radius, radius);
        Location l2 = loc.clone().add(radius, radius, radius);

        minX = Math.min(l1.getBlockX(), l2.getBlockX());
        maxX = Math.max(l1.getBlockX(), l2.getBlockX());

        minY = Math.min(l1.getBlockY(), l2.getBlockY());
        maxY = Math.max(l1.getBlockY(), l2.getBlockY());

        minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
        maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

        this.protect = protect;
    }

    @Override
    public boolean isInRegion(Location l) {
        return (l.getBlockX() <= maxX && l.getBlockX() >= minX) && (l.getY() <= maxY && l.getY() >= minY) && (l.getBlockZ() <= maxZ && l.getBlockZ() >= minZ);
    }

    @Override
    public boolean isProtected() {
        return protect;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }
}
