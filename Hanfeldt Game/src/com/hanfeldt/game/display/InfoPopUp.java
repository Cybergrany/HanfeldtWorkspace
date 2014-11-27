package com.hanfeldt.game.display;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.tile.Tile;

public class InfoPopUp {
	
//	private Graphics gr;
	public String message;
	public boolean triggered = false, showing = false;
	
	public InfoPopUp(String text){
		message = text;
		Main.getGame().getHud().popUpList.add(this);
	}
	
	public void tick(){
		if(Main.getGame().getListener().eDown){
			triggered = true;
		}else{
			triggered = false;//This can be removed later for auto npc usage
		}
		if(Main.timer(20)){
			showing = false;
		}
	}
	
	//TODO: This isn't the right place for this
	/**
	 * Toggles the target entity's layer
	 * @param e
	 * @param t
	 * @param up
	 * @param toggle
	 */
	public void shiftLayerConfirm(Entity e, Tile t, boolean up, boolean toggle){
		showing = true;
		if(triggered && !t.triggered){
			if(!toggle){
				if(up){
					e.moveToLayerAbove();
				}else{
					e.moveToLayerBelow();
				}
			}else if (toggle && Main.getGame().getLevels().layerAmount == 2){
				int currentLayer = e.getLayer();
				if(currentLayer == 1){
					e.moveToLayerBelow();
				}else if(currentLayer == 0){
					e.moveToLayerAbove();
				}
			}
			t.triggered = true;
			showing = false;
		}
	}
}
