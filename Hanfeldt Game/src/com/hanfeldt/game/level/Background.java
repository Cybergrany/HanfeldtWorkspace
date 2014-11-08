package com.hanfeldt.game.level;



public class Background {
//	public int layerAmount;//The amount of layers in use in this level. 
	
	public String levelPath(int levelNumber, int layer, boolean staticBg){
		if(staticBg){
			return String.format("/images/maps/backgrounds/level%d/static/bg%d.png", levelNumber, layer);
		}
		return String.format("/images/maps/backgrounds/level%d/bg%d.png", levelNumber, layer);
	}

}
