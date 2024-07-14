package com.andrei1058.bedwars.z_myadditions;

import java.util.ArrayList;
import java.util.List;

public class Bresenham {

    ArrayList<Integer> pos_start;
    ArrayList<Integer> pos_end;

    public Bresenham (ArrayList<Integer> pos_start, ArrayList<Integer> pos_end) {
        this.pos_start = pos_start;
        this.pos_end = pos_end;
    }

    public List<int[]> algorithm() {

        List<int[]> res = new ArrayList<>();

        int dx = Math.abs(pos_start.get(0) - pos_end.get(0));
        int dz = Math.abs(pos_start.get(1) - pos_end.get(1));

        int sx = pos_start.get(0) < pos_end.get(0) ? 1 : -1;
        int sz = pos_start.get(1) < pos_end.get(1) ? 1 : -1;

        int err = dx - dz;

        while (true) {
            res.add(new int[] {pos_start.get(0), pos_start.get(1)});

            if (pos_start.get(0) == pos_end.get(0) && pos_start.get(1) == pos_end.get(1)) break;

            int e2 = 2 * err;

            if (e2 > -dz) {
                err -= dz;
                pos_start.set(0, pos_start.get(0) + sx);
            }
            if (e2 < dx) {
                err += dx;
                pos_start.set(1, pos_start.get(1) + sz);
            }
        }

        return res;

    }
}
