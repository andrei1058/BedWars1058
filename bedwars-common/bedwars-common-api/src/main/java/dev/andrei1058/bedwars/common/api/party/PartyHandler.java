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

package dev.andrei1058.bedwars.common.api.party;

public interface PartyHandler {
    /**
     * Get party support interface.
     *
     * @return current party adapter.
     */
    PartyAdapter getPartyAdapter();

    /**
     * Change server party adapter.
     * This is only possible if there aren't any created parties on the existing adapter.
     *
     * @param partyAdapter new adapter. Null to revert to default.
     * @return true if changed successfully.
     */
    boolean setPartyAdapter(PartyAdapter partyAdapter);
}
