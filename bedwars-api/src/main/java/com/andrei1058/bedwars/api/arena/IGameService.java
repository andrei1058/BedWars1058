package com.andrei1058.bedwars.api.arena;

import com.andrei1058.bedwars.api.arena.constraints.ConstraintProvider;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IGameService {

    /**
     * Load an arena.
     * It will check the load constraints and then will add the map to the enable queue.
     *
     * @param template  map template.
     * @param requester player requester. Eventually via command.
     */
    @ApiStatus.Experimental
    void loadGame(String template, @Nullable Player requester);

    /**
     * @return Arena template validators
     */
    List<ConstraintProvider> getConstraintProviders();

    /**
     * Register your own arena template validator.
     * You can have up to one validator per plugin instance.
     *
     * @param provider custom provider.
     */
    void registerProvider(ConstraintProvider provider);
}
