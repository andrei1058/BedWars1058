package com.andrei1058.bedwars.api.util;

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

	public static void setLevelName(String worldName){
		Properties properties = new Properties();

		try (FileInputStream in = new FileInputStream("server.properties")) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		properties.setProperty("level-name", worldName);

		try (FileOutputStream out = new FileOutputStream("app.properties")) {
			properties.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
