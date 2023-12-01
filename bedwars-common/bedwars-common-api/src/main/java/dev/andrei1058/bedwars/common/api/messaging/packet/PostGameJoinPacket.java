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
public class PostGameJoinPacket extends AbstractMessagingPacket {

    private UUID player;
    private  int gameId;
    private boolean spectator;
    private String langIso;
    private String partyOrSpectateTarget;

    /**
     * Used when a player has selected a game from lobby. We are letting the slave the player is joining.
     *
     * @param target slave where the packet is going to.
     * @param sender packet sender.
     * @param player player joining the server.
     * @param gameId the game where the player must be assigned.
     * @param spectator in case is joining as spectator or admin teleport.
     * @param langIso if selected another language than default.
     * @param partyOrSpectateTarget if joining via party must specify the party over. Can be used as spectating target.
     */
    public PostGameJoinPacket(@Nullable String sender, @Nullable String target, UUID player,
                              int gameId, boolean spectator, String langIso, String partyOrSpectateTarget) {
        super(sender, target);
        this.player = player;
        this.gameId = gameId;
        this.spectator = spectator;
        this.langIso = langIso;
        this.partyOrSpectateTarget = partyOrSpectateTarget;
    }
}
