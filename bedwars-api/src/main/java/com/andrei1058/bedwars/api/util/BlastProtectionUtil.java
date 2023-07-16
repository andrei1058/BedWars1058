package com.andrei1058.bedwars.api.util;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class BlastProtectionUtil {

    private final VersionSupport versionSupport;
    private final BedWars api;

    public BlastProtectionUtil(VersionSupport versionSupport, BedWars api) {
        this.versionSupport = versionSupport;
        this.api = api;
    }


    /**
     * Check if block is protected by blast-proof glass or an unbreakable block from a point of view
     * <p>
     * if pov is null, block is checked by {@link VersionSupport#isGlass(Material)} and {@link IArena#isBlockPlaced(Block)} instead.
     * Otherwise, a ray tracing is performed to check whether there is any glass block or an unbreakable block.
     * <p>
     * If arena is null, then glass only be checked.
     *
     * @param arena Arena instance.
     * @param pov   the point of view.
     * @param block the block instance.
     * @param step  how frequent to check the ray (0.25 - 0.5 recommended).
     * @return whether there's unbreakable block between the pov and the block
     */
    public boolean isProtected(@NotNull IArena arena, Location pov, @NotNull Block block, double step) {

        if (arena.isProtected(block.getLocation()) || arena.isTeamBed(block.getLocation())) {
            return true;
        }

        boolean rayBlockedByGlass = api.getConfigs().getMainConfig().getBoolean(ConfigPath.GENERAL_TNT_RAY_BLOCKED_BY_GLASS);

        // Trace blocks from pov to the block location
        final Location target = block.getLocation();

        LinkedList<Vector> targetVectors = new LinkedList<>();

        double alteredRayStep = 0.73;
        // x
        for (double XrayRadius = alteredRayStep * -1; XrayRadius <= alteredRayStep; XrayRadius+= alteredRayStep) {
            // y
            for (double YrayRadius = alteredRayStep * -1; YrayRadius <= alteredRayStep; YrayRadius+= alteredRayStep) {
                // z
                for (double ZrayRadius = alteredRayStep * -1; ZrayRadius <= alteredRayStep; ZrayRadius+= alteredRayStep) {
                    targetVectors.add(pov.clone().toVector().toBlockVector().add(new Vector(XrayRadius, YrayRadius, ZrayRadius)));
                }
            }
        }

        AtomicInteger protectedTimes = new AtomicInteger();
        int totalRays = targetVectors.size();

        targetVectors.forEach(targetVector -> {
            BlockRay ray;
            try {
                ray = new BlockRay(block.getWorld(), targetVector, target.toVector(), step);
            } catch (IllegalArgumentException ignored) {
                return;
            }

            while (ray.hasNext()) {
                Block nextBlock = ray.next();

                if (nextBlock.getType() == Material.AIR) {
                    continue;
                }

                if (rayBlockedByGlass && versionSupport.isGlass(nextBlock.getType())) {
                    // If a block is a glass
                    protectedTimes.getAndIncrement();
                    return;
                }

                if (!arena.isBlockPlaced(nextBlock) && !arena.isAllowMapBreak()) {
                    protectedTimes.getAndIncrement();
                    return;
                }
            }
        });

        return totalRays - protectedTimes.get() < 6;
    }
}
