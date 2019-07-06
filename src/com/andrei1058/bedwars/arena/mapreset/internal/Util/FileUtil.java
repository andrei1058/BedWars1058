package com.andrei1058.bedwars.arena.mapreset.internal.Util;
//CODE BY: Arceus02
import com.andrei1058.bedwars.Main;

import java.io.File;

public class FileUtil {
	
	@SuppressWarnings("ConstantConditions")
	public static void delete(File file) {
		if(file.isDirectory()) {
			for(File subfile : file.listFiles()) {
				delete(subfile);
			}
		} else {
			if (!file.delete()){
				Main.plugin.getLogger().severe("Could not delete " + file.getPath());
			}
		}
	}
	
}
