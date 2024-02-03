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

package com.andrei1058.bedwars.api.region;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Cuboid implements Region {

    private int minX, maxX;
    private int minY, maxY;
    private int minZ, maxZ;

    private boolean protect;

    public Cuboid(@NotNull Location loc, int radius, boolean protect) {
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

    public Cuboid(@NotNull Location loc1, @NotNull Location loc2, boolean protect) {
        Location l1 = loc1.clone();
        Location l2 = loc2.clone();

        minX = Math.min(l1.getBlockX(), l2.getBlockX());
        maxX = Math.max(l1.getBlockX(), l2.getBlockX());

        minY = Math.min(l1.getBlockY(), l2.getBlockY());
        maxY = Math.max(l1.getBlockY(), l2.getBlockY());

        minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());
        maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

        this.protect = protect;
    }

    @Override
    public boolean isInRegion(@NotNull Location l) {
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

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }
}
