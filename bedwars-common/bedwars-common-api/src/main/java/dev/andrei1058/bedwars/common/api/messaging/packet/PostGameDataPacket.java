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
import dev.andrei1058.bedwars.common.api.messaging.GroupCategorisedPacket;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
@Setter
public class PostGameDataPacket extends AbstractMessagingPacket implements GroupCategorisedPacket {

    private boolean privateGame;
    private UUID gameId;
    private GameState gameState;
    private boolean full;
    private String spectatePermission;
    private String displayName;
    private int maxPlayers, minPlayers, currentPlayers, currentSpectators;
    private String templateWorld, tag;
    private int vips;
    private long startTime;
    private String gameGroup;
    private byte displayData;
    private boolean displayEnchantment;
    private String displayMaterial;
    private String replyChannel;


    public PostGameDataPacket(
            @Nullable String sender, @NotNull DisplayableArena arena
    ) {
        this(sender, null, arena, (byte) 0, null, false);
    }

    public PostGameDataPacket(
            @Nullable String sender, @Nullable String target, @NotNull DisplayableArena arena,
            byte displayData, String displayMaterial, boolean displayEnchantment
    ) {
        super(sender, target);
        this.gameId = arena.getGameId();
        this.gameState = arena.getGameState();
        this.full = arena.isFull();
        this.spectatePermission = arena.getSpectatePermission();
        this.displayName = arena.getDisplayName();
        this.maxPlayers = arena.getMaxPlayers();
        this.minPlayers = arena.getMinPlayers();
        this.currentPlayers = arena.getCurrentPlayers();
        this.currentSpectators = arena.getCurrentSpectators();
        this.templateWorld = arena.getTemplate();
        this.vips = arena.getCurrentVips();
        this.startTime = arena.getStartTime() == null ? -1 : arena.getStartTime().toEpochMilli();
        this.gameGroup = arena.getGroup();
        this.displayData = displayData;
        this.displayMaterial = displayMaterial;
        this.displayEnchantment = displayEnchantment;
    }

    public String getDisplayMaterial() {
        return displayMaterial == null ? null : displayMaterial.trim().toUpperCase();
    }

    public String getGameGroup() {
        return gameGroup;
    }
}
