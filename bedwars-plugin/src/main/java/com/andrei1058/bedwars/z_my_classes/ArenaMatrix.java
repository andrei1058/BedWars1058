package com.andrei1058.bedwars.z_my_classes;

import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.ArrayList;
import java.util.List;

public class ArenaMatrix {
    private List<List<List<MyBlockState>>> matrix;
    private final int xMin, zMin;
    private final int sideLength;
    private final World world;

    public ArenaMatrix(World world) {
        WorldBorder border = world.getWorldBorder();

//        world.setC

        this.world = world;

        this.sideLength = (int) border.getSize();

        this.xMin = (int) (border.getCenter().getBlockX() - (this.sideLength) / 2);
        this.zMin = (int) (border.getCenter().getBlockY() - (this.sideLength) / 2);

        this.matrix = createMatrix();
    }

    private List<List<List<MyBlockState>>> createMatrix() {
        matrix = new ArrayList<>();

        for (int x = 0; x < sideLength; x++) {
            List<List<MyBlockState>> ZList = new ArrayList<>();
            for (int z = 0; z < sideLength; z++) {
                List<MyBlockState> YList = new ArrayList<>();
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    YList.add(new MyBlockState(world.getBlockAt(xMin + x, y, zMin + z)));
                }
                ZList.add(YList);
            }
            matrix.add(ZList);
        }

        return matrix;
    }

    public int x(int x) {
        return x - this.xMin;
    };

    public int z(int z) {
        return z - this.zMin;
    }

    public int y(int y) {
        return y;
    }

    public MyBlockState getBlock(int x, int z, int y) {
        x = this.x(x);
        z = this.z(z);
        y = this.y(y);

        return this.matrix.get(x).get(z).get(y);
    }
}
