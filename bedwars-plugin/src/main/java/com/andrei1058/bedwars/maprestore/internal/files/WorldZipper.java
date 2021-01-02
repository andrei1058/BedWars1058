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
