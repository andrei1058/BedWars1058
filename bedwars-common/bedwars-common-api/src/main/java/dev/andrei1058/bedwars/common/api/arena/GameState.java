/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2023 Andrei Dascălu
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
 */

package dev.andrei1058.bedwars.common.api.arena;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum GameState {

    LOADING(0),
    WAITING(1),
    STARTING(2),
    IN_GAME(3),
    CELEBRATING(4),
    STOPPING(5);

    /**
     * State code.
     * Used for game state comparison.
     * When adding new game states make sure to respect the sequence.
     */
    private final int weight;

    GameState(int stateCode) {
        this.weight = stateCode;
    }

    /**
     * Get status by code.
     * @return Optional game state.
     */
    public static @NotNull Optional<GameState> getByWeight(int weight) {
        return Arrays.stream(values()).filter(status -> status.weight == weight).findFirst();
    }

    /**
     * Get game state by slug.
     * Can be used to parse configuration into objects.
     */
    public static Optional<GameState> getBySlug(@NotNull String slug) {

        return switch (slug.toLowerCase()) {
            case "loading", "l", "enabling" -> Optional.of(LOADING);
            case "waiting", "w", "wait", "lobby" -> Optional.of(WAITING);
            case "starting", "s", "start" -> Optional.of(STARTING);
            case "in_game", "in game", "started", "ig", "g", "playing", "p" -> Optional.of(IN_GAME);
            case "celebrating", "celebrate", "c", "finishing", "ending", "closing" -> Optional.of(CELEBRATING);
            case "saving", "done", "stopping" -> Optional.of(STOPPING);
            default -> Optional.empty();
        };
    }
 }
