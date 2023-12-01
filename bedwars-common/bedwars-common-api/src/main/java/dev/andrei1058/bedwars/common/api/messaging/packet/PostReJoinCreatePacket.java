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

import dev.andrei1058.bedwars.common.api.messaging.AbstractMessagingPacket;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public class PostReJoinCreatePacket extends AbstractMessagingPacket {

    private int gameId;
    private UUID player;

    /**
     * Slave informing masters listening that a new re-join session has been created.
     *
     * @param sender slave sending the message.
     * @param target should be null so all masters listening will receive the message.
     * @param gameId gameId where the re-join is referring to.
     * @param player player that left the game.
     */
    public PostReJoinCreatePacket(
            @Nullable String sender, @Nullable String target, int gameId, UUID player
    ) {
        super(sender, target);
        this.gameId = gameId;
        this.player = player;
    }
}
