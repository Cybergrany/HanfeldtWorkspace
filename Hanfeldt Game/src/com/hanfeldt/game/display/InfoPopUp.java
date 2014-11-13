package com.hanfeldt.game.display;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;

public class InfoPopUp {
	
//	private Graphics gr;
	public String message;
	public boolean triggered = false, showing = false;
	
	public Entity en;
	
	public InfoPopUp(String text, Entity e){
		message = text;
		en = e;
		Main.getGame().getHud().popUpList.add(this);
	}
	
	public void tick(){
		if(Main.getGame().getListener().eDown){
			triggered = true;
		}
		if(Main.timer(60)){
			showing = false;
		}
	}
}
