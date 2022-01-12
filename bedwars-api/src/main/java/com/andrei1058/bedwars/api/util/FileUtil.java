package com.andrei1058.bedwars.api.util;

import com.andrei1058.bedwars.api.server.VersionSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {

	public static void delete(File file) {
		if(file.isDirectory()) {
			//noinspection ConstantConditions
			for(File subfile : file.listFiles()) {
				delete(subfile);
			}
		} else {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
		}
	}

	public static void setMainLevel(String worldName, VersionSupport vs){
		Properties properties = new Properties();

		try (FileInputStream in = new FileInputStream("server.properties")) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		properties.setProperty("level-name", worldName);
		properties.setProperty("generator-settings", vs.getVersion() > 5 ? "minecraft:air;minecraft:air;minecraft:air" : "1;0;1");
		properties.setProperty("allow-nether", "false");
		properties.setProperty("level-type", "flat");
		properties.setProperty("generate-structures", "false");
		properties.setProperty("spawn-monsters", "false");
		properties.setProperty("max-world-size", "1000");
		properties.setProperty("spawn-animals", "false");

		try (FileOutputStream out = new FileOutputStream("server.properties")) {
			properties.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
