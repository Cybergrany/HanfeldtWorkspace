package com.hanfeldt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		int resources = 0;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
			String name;
			while((name = reader.readLine()) != null){
				if(name.endsWith("png"))
				resources++;
			}
		} catch (IOException e) {
			System.err.println("IOException in ResourceManager");
		}finally{
			try{
				reader.close();
			}catch(Exception e){
				System.err.println("Exception in ResourceManager");
			}
		}
		return resources;

//		 Have to have this to work in jar, will fix later TODO
//		if(path.endsWith("1/")) {
//			return 4;
//		}else if(path.endsWith("2/")) {
//			return 1;
//		}
//		return 0;
	}
	
}
