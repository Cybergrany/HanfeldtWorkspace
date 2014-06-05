package com.hanfeldt.io;

public class ResourceManager {
	
	public ResourceManager(){

		
	}
	
	public int getImageResourcesInDir(String path){
		/*
		int resources = 0;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
			String name;
			while((name = reader.readLine()) != null){
				if(name.endsWith("png"))
				resources++;
			}
			System.out.println(resources);
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
		*/
		// Have to have this to work in jar, will fix later TODO
		if(path.endsWith("1/")) {
			return 4;
		}else if(path.endsWith("2/")) {
			return 1;
		}
		return 0;
	}
	
}
