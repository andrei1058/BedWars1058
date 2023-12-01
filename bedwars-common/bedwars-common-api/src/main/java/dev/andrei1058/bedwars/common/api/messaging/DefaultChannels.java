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

package dev.andrei1058.bedwars.common.api.messaging;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * List of default messaging channel IDs.
 */
@Getter
public enum DefaultChannels {
    ARENA_FULL_DATA("arena_full_data"),
    PLAYER_COUNT_UPDATE("arena_player_count_update"),
    PLAYER_COUNT_CHANNEL("arena_player_count"),
    ARENA_STATUS_UPDATE("arena_status_update"),
    GAME_DROP("arena_game_drop"),
    PING("arena_keep_alive"),
    ARENA_QUERY("arena_query"),
    PLAYER_JOIN("arena_player_join"),
    SLAVE_WAKE_UP("slave_wake_up"),
    PRIVATE_TAKE_OVER("arena_private_game_take_over"),
    POST_REJOIN_CREATED("arena_rejoin_create"),
    POST_REJOIN_DELETED("arena_rejoin_deny"),
    QUERY_TELEPORT("teleport"),
    POST_TELEPORT_RESPONSE("teleport_response"),
    PLAY_AGAIN_REQUEST("play_again_request"),
    PLAY_AGAIN_RESPONSE("play_again_response");

    private static final String PREFIX = "bw_";
    private final String name;

    DefaultChannels(@NotNull String name) {
        if (name.length() > 8) {
            throw new RuntimeException("Channel id length exceeded!");
        }
        this.name = PREFIX+name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
