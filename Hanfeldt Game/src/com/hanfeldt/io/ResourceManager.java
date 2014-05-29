package com.hanfeldt.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceManager {
	
	public ResourceManager(){

		
	}
	
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
	}
	
}
