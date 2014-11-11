package com.hanfeldt.game.tile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.Player;

public class Shop extends Tile{
	
	public Shop(int x, int y){
		super(x, y);
		setSprite((new Sprite(SpriteSheet.getSheet(SpriteSheet.block), 1, 3, 2, 1)));
	}
	
	public void onCollidedEntity(Entity e){
		if(e instanceof Player){
			Graphics g = Main.getGame().getPanel().getGraphics();
//			super.onCollidedEntity(e);
			Main.getGame().getPanel().getGraphics();
			g.setColor(Color.WHITE);
			g.setFont(new Font("Kai", Font.PLAIN, 10));
			g.drawString("Press Space to enter shop", 20, 70);
		}
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public String toString(){
		return "Shop";
	}

}
