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
public class PlayAgainResponsePacket extends AbstractMessagingPacket {

    private String slaveGameHolder;
    private Integer gameId;
    private UUID player;
    private boolean privateGame;

    /**
     * In response for {@link PlayAgainRequestPacket}.
     *
     * @param sender          the lobby as master responding to the request.
     * @param target          slave that made the request. Must not be null in this case.
     * @param slaveGameHolder the slave holding the new game, null if not found.
     * @param gameId          null if no game was found.
     * @param player          requester.
     * @param privateGame     if it is a private game.
     */
    public PlayAgainResponsePacket(
            @Nullable String sender, @Nullable String target, @Nullable String slaveGameHolder,
            @Nullable Integer gameId, UUID player, boolean privateGame
    ) {
        super(sender, target);
        this.slaveGameHolder = slaveGameHolder;
        this.gameId = gameId;
        this.player = player;
        this.privateGame = privateGame;
    }
}
