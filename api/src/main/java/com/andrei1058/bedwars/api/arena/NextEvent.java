/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
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
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.api.arena;

public enum NextEvent {

    DIAMOND_GENERATOR_TIER_II("next-event.diamond-II"),
    DIAMOND_GENERATOR_TIER_III("next-event.diamond-III"),
    EMERALD_GENERATOR_TIER_II("next-event.emerald-II"),
    EMERALD_GENERATOR_TIER_III("next-event.emerald-III"),
    BEDS_DESTROY("next-event.beds-destroy"),
    ENDER_DRAGON("next-event.dragons-spawn"),
    GAME_END("next-event.game-end");

    private final String soundPath;

    NextEvent(String soundPath){
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }
}
