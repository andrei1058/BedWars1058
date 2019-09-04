package com.andrei1058.bedwars.maprestore.internal.files;
import com.andrei1058.bedwars.BedWars;

import java.io.File;

public class FileUtil {

	public static void delete(File file) {
		if(file.isDirectory()) {
			//noinspection ConstantConditions
			for(File subfile : file.listFiles()) {
				delete(subfile);
			}
		} else {
			if (!file.delete()){
				BedWars.plugin.getLogger().severe("Could not delete " + file.getPath());
			}
		}
	}
	
}
