package com.andrei1058.bedwars.z_my_classes;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.arena.Arena;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class BlockUtils {

    /**
     * Направления для соседних блоков.
     */
    public static enum Direction {
        EAST(1, 0, 0),   // +X
        WEST(-1, 0, 0),  // -X
        UP(0, 1, 0),     // +Y
        DOWN(0, -1, 0),  // -Y
        SOUTH(0, 0, 1),  // +Z
        NORTH(0, 0, -1); // -Z

        private final int xOffset;
        private final int yOffset;
        private final int zOffset;

        Direction(int xOffset, int yOffset, int zOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.zOffset = zOffset;
        }

        public int getXOffset() {
            return xOffset;
        }

        public int getYOffset() {
            return yOffset;
        }

        public int getZOffset() {
            return zOffset;
        }
    }

    /**
     * Возвращает список всех соседних блоков для указанного блока.
     * Все направления включены.
     *
     * @param block Блок, для которого ищутся соседи.
     * @return Список соседних блоков.
     * @throws IllegalArgumentException Если block равен null.
     */
    public static ArrayList<Block> getNeighborsBlocks(Block block) {
        return getNeighborsBlocks(block, EnumSet.allOf(Direction.class), false);
    }

    /**
     * Возвращает список соседних блоков для указанного блока, фильтруя направления.
     * По умолчанию включает только указанные направления.
     *
     * @param block      Блок, для которого ищутся соседи.
     * @param directions Множество направлений для фильтрации.
     * @return Список соседних блоков.
     * @throws IllegalArgumentException Если block или directions равны null.
     */
    public static ArrayList<Block> getNeighborsBlocks(Block block, EnumSet<Direction> directions) {
        return getNeighborsBlocks(block, directions, false);
    }

    /**
     * Возвращает список соседних блоков для указанного блока, фильтруя направления.
     * Параметр invertFilter определяет, как интерпретировать directions:
     * - false: включаются только указанные направления.
     * - true: исключаются указанные направления, включаются остальные.
     *
     * @param block        Блок, для которого ищутся соседи.
     * @param directions   Множество направлений для фильтрации.
     * @param invertFilter Если true, исключает указанные направления; если false, включает только их.
     * @return Список соседних блоков.
     * @throws IllegalArgumentException Если block или directions равны null.
     */
    public static ArrayList<Block> getNeighborsBlocks(Block block, EnumSet<Direction> directions, boolean invertFilter) {
        if (directions == null) {
            throw new IllegalArgumentException("Directions cannot be null");
        }

        ArrayList<Block> blocks = new ArrayList<>();
        Location loc = block.getLocation();

        // Перебираем все возможные направления
        for (Direction dir : Direction.values()) {
            // Проверяем, нужно ли включить направление
            boolean include = invertFilter != directions.contains(dir);
            if (include) {
                blocks.add(block.getWorld().getBlockAt(
                        loc.getBlockX() + dir.getXOffset(),
                        loc.getBlockY() + dir.getYOffset(),
                        loc.getBlockZ() + dir.getZOffset()
                ));
            }
        }

        return blocks;
    }

    public static ArrayList<Location> getNeighborsBlocks(Location loc) {
        ArrayList<Block> blocks = getNeighborsBlocks(loc.getBlock());
        ArrayList<Location> locations = new ArrayList<>();

        for (Block block : blocks) {
            locations.add(block.getLocation());
        }

        return locations;
    }

    public static boolean checkBlockPlacedByPlayer(Block block) {
        Location loc = block.getLocation();
        World world = block.getWorld();

        IArena arena = Arena.getArenaByName(world.getName());

        for (Vector pos : arena.getPlaced()) {
            if (pos.getBlockX() == loc.getBlockX()
                    && pos.getBlockZ() == loc.getBlockZ()
                    && pos.getBlockY() == loc.getBlockY()
            ) { return true; }
        }

        return false;
    }
}
