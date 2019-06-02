package com.andrei1058.bedwars.arena.mapreset.internal.WorldOperations;

import java.io.File;
import java.io.IOException;

import com.andrei1058.bedwars.arena.mapreset.MapManager;
import com.andrei1058.bedwars.arena.mapreset.internal.Util.ZipFileUtil;
import org.bukkit.Bukkit;

public class WorldZipper implements WorldOperator {

    private final String worldName;
    private boolean jobDone = false;
    private boolean replace = false;

    public WorldZipper(String worldName, boolean replace) {
        this.worldName = worldName;
        this.replace = replace;
    }

    public void execute() {
        if (!exists() || replace) {
            try {
                zipWorldFolder();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jobDone = true;
        } else return;
    }

    private void zipWorldFolder() throws IOException {
        File worldFolder = getWorldFolder();
        File backupFile = getBackupFile();
        try {
            ZipFileUtil.zipDirectory(worldFolder, backupFile);
        } catch (IOException e) {
            throw e;
        }
    }

    private File getWorldFolder() {
        File worldContainer = Bukkit.getWorldContainer();
        File worldFolder = new File(worldContainer, worldName);
        return worldFolder;
    }

    private File getBackupFile() {
        File backupFolder = MapManager.backupFolder;
        File backupFile = new File(backupFolder, worldName + ".zip");
        return backupFile;
    }

    private boolean exists() {
        File worldFolder = getWorldFolder();
        return worldFolder.isDirectory();
    }

    public Boolean isJobDone() {
        return this.jobDone;
    }

    public String getResultMessage() {
        if (isJobDone()) {
            return "Successfully zipped world " + worldName;
        } else {
            return "World " + worldName + " isn't zipped yet.";
        }
    }

}
