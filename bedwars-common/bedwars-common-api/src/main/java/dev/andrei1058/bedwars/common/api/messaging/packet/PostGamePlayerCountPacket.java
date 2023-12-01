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

package dev.andrei1058.bedwars.common.api.messaging.packet;

import dev.andrei1058.bedwars.common.api.arena.DisplayableArena;
import dev.andrei1058.bedwars.common.api.arena.GameState;
import dev.andrei1058.bedwars.common.api.messaging.AbstractMessagingPacket;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public class PostGamePlayerCountPacket extends AbstractMessagingPacket {

    private GameState gameState;
    private UUID gameId;
    private int maxPlayers, currentPlayers, currentSpectators, currentVips;

    /**
     * Used when a game changes player count. In case of start provide start time as well.
     *
     * @param sender slave identifier sending the update.
     * @param target if targeted to a certain master. In this case should be null.
     * @param arena arena being updated.
     */
    public PostGamePlayerCountPacket(
            @Nullable String sender, @Nullable String target,
            @NotNull DisplayableArena arena) {
        super(sender, target);
        this.gameState = arena.getGameState();
        this.gameId = arena.getGameId();
        this.maxPlayers = arena.getMaxPlayers();
        this.currentSpectators = arena.getCurrentSpectators();
        this.currentVips = arena.getCurrentVips();
    }
}
