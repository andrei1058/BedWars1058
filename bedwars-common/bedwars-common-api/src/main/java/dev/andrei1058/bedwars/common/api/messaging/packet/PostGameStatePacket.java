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
public class PostGameStatePacket extends AbstractMessagingPacket {

    private UUID gameId;
    private GameState gameState;
    private long startTime;
    private byte displayData;
    private boolean displayEnchanted;
    private String displayMaterial;

    public PostGameStatePacket(
            @Nullable String sender, @NotNull DisplayableArena arena
    ) {
        this(sender, null, arena, (byte) 0, null, false);
    }

    /**
     * Used when a game changes its state. In case of start provide start time as well.
     *
     * @param sender slave identifier sending the update.
     * @param target if targeted to a certain master. In this case should be null.
     * @param arena  arena being updated.
     */
    public PostGameStatePacket(
            @Nullable String sender, @Nullable String target, @NotNull DisplayableArena arena,
            byte displayData, @Nullable String displayMaterial, boolean displayEnchanted
    ) {
        super(sender, target);
        this.gameId = arena.getGameId();
        this.gameState = arena.getGameState();
        this.startTime = arena.getStartTime() == null ? -1 : arena.getStartTime().toEpochMilli();
        this.displayMaterial = displayMaterial;
        this.displayEnchanted = displayEnchanted;
        this.displayData = displayData;
    }

    public String getDisplayMaterial() {
        return displayMaterial == null ? null : displayMaterial.trim().toUpperCase();
    }
}
