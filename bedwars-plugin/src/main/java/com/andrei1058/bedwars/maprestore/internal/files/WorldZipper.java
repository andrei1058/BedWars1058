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

package com.andrei1058.bedwars.maprestore.internal.files;

import com.andrei1058.bedwars.api.util.ZipFileUtil;
import com.andrei1058.bedwars.maprestore.internal.InternalAdapter;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class WorldZipper {

    private final String worldName;
    private boolean replace;

    public WorldZipper(String worldName, boolean replace) {
        this.worldName = worldName;
        this.replace = replace;
        execute();
    }

    private void execute() {
        if (!exists() || replace) {
            try {
                zipWorldFolder();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void zipWorldFolder() throws IOException {
        File worldFolder = getWorldFolder();
        File backupFile = getBackupFile();
        ZipFileUtil.zipDirectory(worldFolder, backupFile);
    }

    private File getWorldFolder() {
        File worldContainer = Bukkit.getWorldContainer();
        return new File(worldContainer, worldName);
    }

    private File getBackupFile() {
        File backupFolder = InternalAdapter.backupFolder;
        return new File(backupFolder, worldName + ".zip");
    }

    private boolean exists() {
        File worldFolder = getWorldFolder();
        return worldFolder.isDirectory();
    }
}
