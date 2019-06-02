package com.andrei1058.bedwars.api;

import com.andrei1058.bedwars.arena.Arena;
import org.jetbrains.annotations.Contract;

public enum NextEvent {
    DIAMOND_GENERATOR_TIER_II, DIAMOND_GENERATOR_TIER_III, EMERALD_GENERATOR_TIER_II, EMERALD_GENERATOR_TIER_III, BEDS_DESTROY, ENDER_DRAGON, GAME_END;

    @Contract(pure = true)
    public int getValue(Arena arena) {

        int value = -1;
        switch (super.name()) {
            case "DIAMOND_GENERATOR_TIER_II":
                if (arena.getNextEvent() == DIAMOND_GENERATOR_TIER_II)
                    value = arena.upgradeDiamondsCount;
                break;
            case "DIAMOND_GENERATOR_TIER_III":
                if (!arena.getNextEvents().contains("DIAMOND_GENERATOR_TIER_II"))
                    value = arena.upgradeDiamondsCount;
                break;
            case "EMERALD_GENERATOR_TIER_II":
                if (!arena.getNextEvents().contains("EMERALD_GENERATOR_TIER_III"))
                    value = arena.upgradeDiamondsCount;
                break;
            case "EMERALD_GENERATOR_TIER_III":
                if (!arena.getNextEvents().contains("EMERALD_GENERATOR_TIER_II"))
                    value = arena.upgradeDiamondsCount;
                break;
            case "BEDS_DESTROY":
                //if (arena.getPlayingTask() != null) {
                if (arena.getNextEvents().contains(ENDER_DRAGON) && arena.getNextEvents().contains(GAME_END))
                value = arena.getPlayingTask().getBedsDestroyCountdown();
                //}
                break;
            case "ENDER_DRAGON":
                //if (arena.getPlayingTask() != null){
                if (!arena.getNextEvents().contains(BEDS_DESTROY))
                value = arena.getPlayingTask().getDragonSpawnCountdown();
                //}
                break;
            case "GAME_END":
                //if (arena.getPlayingTask() != null){
                if (arena.getNextEvents().size() == 1)
                    value = arena.getPlayingTask().getGameEndCountdown();

                //}
                break;
        }

        return value;
    }
}
