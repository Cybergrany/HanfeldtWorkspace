package com.hanfeldt.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.hanfeldt.game.Main;

/**
 * Class for any file-based operations which need to be performed.
 * @author David Ofeldt
 *
 */
public class ResourceManager {
	
	public ResourceManager(){

		
	}
	
	/**
	 * Returns amount of image files(.png) in a specified directory. To assist in background loading.
	 * @param path directory to be searched
	 * @return resources(int) - amount of image files in the specified directory
	 */
	public int getImageResourcesInDir(String path){
		String dirWithoutSlashes = path.replaceAll("/", "");
		int level = Character.digit(dirWithoutSlashes.charAt(dirWithoutSlashes.length() -1), 10);
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/level.conf")));
			line = reader.readLine();
		}catch(Exception e) {
			e.printStackTrace();
		}
		StringTokenizer st = new StringTokenizer(line, ",");
		if(Main.debug)
		System.out.println("Current Level: "  +level);
		for(int i=0; i<level -1; i++) {
			st.nextToken();
		}
		return Integer.parseInt(st.nextToken());
	}
	
}
