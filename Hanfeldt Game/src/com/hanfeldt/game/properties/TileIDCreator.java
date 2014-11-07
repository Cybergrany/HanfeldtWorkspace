package com.hanfeldt.game.properties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

/**
 * Used to create initial tile ID list
 * @author ofeldt
 *@deprecated edit the file manualy
 */
public class TileIDCreator {
	private static String filename = "block.conf";
	
	public static String[] tileName = new String[]{"color code", "isSolid", "isVisible", "name"};
	public static String[] air = new String[]{"0xffffffff", "false", "false", "Air"};
	
	public static void main(String[] args){
		
		Properties prop = new Properties();
		OutputStream output = null;
		try{
			setIds(String.format("/config/id/%s", filename), output, prop);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(output!= null)
			try{
				output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	static void setIds(String path, OutputStream output, Properties p)throws IOException, URISyntaxException{
		URL resourceUrl = TileIDCreator.class.getResource(path);
		
		if(resourceUrl == null){
			System.err.println("File not found D:");
			System.exit(0);
		}
		File file = new File(resourceUrl.toURI());
		output = new FileOutputStream(file);
		
//		p.setProperty("tileName", Arrays.toString(tileName));
		p.setProperty("air", Arrays.toString(air));
		
		p.store(output, "tileName=[color code, isSolid, isVisible, name]");
		
		output.close();
		
	}
}
