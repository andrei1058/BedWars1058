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
public class PostQueryPlayerPacket extends AbstractMessagingPacket {

    private UUID playerRequester;
    private String playerQueried;

    /**
     * Used to search a player.
     *
     * @param sender server sending the message.
     * @param target target server where to search for this player or null for all listener.
     * @param playerRequester player requester.
     * @param playerQueried player looking for.
     */
    public PostQueryPlayerPacket(
            @Nullable String sender, @Nullable String target, UUID playerRequester, String playerQueried
    ) {
        super(sender, target);
        this.playerQueried =  playerQueried;
        this.playerRequester = playerRequester;
    }

}
