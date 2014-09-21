package com.hanfeldt.game.entity;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

public class EntityItem extends SpriteEntity{
	protected  boolean isCollidingWithHorizTile = false;
	
	float friction = .93f;
	
	public EntityItem(Sprite s, int x, int y){
		super(s, x, y);
	}
	
	public void tick(){
		super.tick();
		checkTileCollisions();
//		if(Main.timer(60)){
//			System.out.println("\n\n");
//			System.out.println(getTransparentBounds());
//		}
	}
	
	public void removeItem(EntityItem e){
		Main.getGame().getItems().remove(e);
	}
	
	public boolean isCollidingWithHorizTile(){
		return isCollidingWithHorizTile;
	}
	
	public Rectangle getTransparentBounds(){
		if(getSprite() != null){
			BufferedImage image = getSprite().getImage();
			int /**firstX = 0, firstY = 0,*/ lastX = image.getWidth(), lastY = image.getHeight();
			
			for(int y = 0; y < image.getHeight(); y++){
				for(int x = 0; x < image.getWidth(); x++){
					Color c = new Color(image.getRGB(x, y), true);
					if(c.getAlpha() != 0){
						if(y < image.getHeight() / 2){
//							firstY = y;
//							System.out.println("firstY set to: " + firstY);
						}else if(y > image.getHeight() / 2){
							lastY = y;
//							System.out.println("lastY set to: " + lastY);
						}
						if(x < image.getWidth() / 2){
//							firstX = x;
//							System.out.println("firstX set to: " + firstX);
						}else if(x > image.getWidth() / 2){
							lastX = x;
//							System.out.println("lastX set to: " + lastX);
						}
					}
				}
			}
			Rectangle r = new Rectangle(lastX, lastY);
			return r;
		}
		return null;
	}
	
	public float getFriction(){
		return friction;
	}
	
	public void setFriction(float f){
		friction = f;
	}
	
	public String getType(){
		return "Item";
	}
	
	public Object getLinkedItem(){
		return this;
	}
	
	public Object getLinkedItem(EntityLiving e){
		return this;
	}

}
