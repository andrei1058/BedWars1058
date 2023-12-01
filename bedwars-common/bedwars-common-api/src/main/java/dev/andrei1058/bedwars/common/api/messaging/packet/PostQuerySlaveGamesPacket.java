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

@Getter
@Setter
public class PostQuerySlaveGamesPacket extends AbstractMessagingPacket {

    private String replyChannel;
    /**
     * Query a slave for its active games.
     *
     * @param sender server requester.
     * @param target slave queried.
     * @param replyChannel asking for response via this channel.
     */
    public PostQuerySlaveGamesPacket(
            @Nullable String sender, @Nullable String target, String replyChannel
    ) {
        super(sender, target);
        this.replyChannel = replyChannel;
    }
}
