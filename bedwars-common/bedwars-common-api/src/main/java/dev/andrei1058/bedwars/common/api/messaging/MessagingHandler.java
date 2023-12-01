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

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Responsible for slave-lobby messaging.
 */
public interface MessagingHandler {

    /**
     * Register incoming packet listener.
     * Incoming packet channel from lobby servers.
     * You should check first if a channel is already registered with {@link #isChannelRegistered(String)}.
     * You cannot listen to someone else's packet id.
     * This is used only when server type is bungee.
     *
     * @param channel incoming packets from remote lobbies' listener.
     * @return true if registered successfully.
     */
    boolean registerIncomingPacketChannel(MessagingChannel<?> channel);

    /**
     * Check if there is a registered packet listener with the given name.
     *
     * @param channel channel name.
     */
    boolean isChannelRegistered(String channel);

    /**
     * Send a packet to remote sockets.
     * Json based. Packets should be sent async.
     *
     * @param channel remote channel that will handle this packet.
     * @param message your custom Json data.
     * @param async   true if you want to send it async or false if you're already sending it from an async task or u know if you know what you're doing.
     */
    void sendPacket(String channel, MessagingPacket message, boolean async);

    /**
     * Send a packet to remote sockets.
     * Json based. Packets should be sent async.
     *
     * @param channel remote channel that will handle this packet.
     * @param message your custom Json data.
     * @param async   true if you want to send it async or false if you're already sending it from an async task or u know if you know what you're doing.
     */
    void sendPackets(String channel, List<MessagingPacket> message, boolean async);

    /**
     * Retrieve a channel by id.
     *
     * @param name channel id.
     * @return null if not found.
     */
    @Nullable
    MessagingChannel<?> getChannelByName(String name);
}
