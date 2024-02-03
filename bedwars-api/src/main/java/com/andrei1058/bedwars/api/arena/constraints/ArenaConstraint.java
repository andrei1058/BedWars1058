package com.andrei1058.bedwars.api.arena.constraints;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ArenaConstraint {

    List<String> errors = new ArrayList<>();

    /**
     * @param api BedWars API
     * @param template arena configuration
     * @return optional list of error messages
     */
    public abstract Optional<List<String>> validate(BedWars api, ConfigManager template);

    public Optional<List<String>> addMessage(String errorMessage){
        this.errors.add(errorMessage);
        return getMessages();
    }

    public Optional<List<String>> getMessages() {
        return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
    }
}
