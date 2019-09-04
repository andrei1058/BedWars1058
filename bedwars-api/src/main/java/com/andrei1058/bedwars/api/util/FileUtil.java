package com.andrei1058.bedwars.api.util;

import java.io.File;

public class FileUtil {

	public static boolean delete(File file) {
		if(file.isDirectory()) {
			//noinspection ConstantConditions
			for(File subfile : file.listFiles()) {
				delete(subfile);
			}
		} else {
			return file.delete();
		}
		return true;
	}
	
}
