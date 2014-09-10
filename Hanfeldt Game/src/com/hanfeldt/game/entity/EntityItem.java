package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.sun.javafx.geom.Rectangle;

public class EntityItem extends Entity{
	
	private Sprite sprite;
	private int sizeX, sizeY;
	protected  boolean isCollidingWithHorizTile = false;
	
	public EntityItem(Sprite s, int x, int y){
		super(x, y);
		sprite = s;
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
	}
	
	public void tick(){
		super.tick();
		checkTileCollisions(this);
	}
	
	public int getSizeX() {
		return sizeX *Main.TILE_SIZE;
	}
	
	public int getSizeY() {
		return sizeY *Main.TILE_SIZE;
	}
	
	public int getTileSizeX(){
		return sizeX;
	}
	
	public int getTileSizeY() {
		return sizeY;
	}
	
	public boolean isCollidingWithHorizTile(){
		return isCollidingWithHorizTile;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(sizeX, sizeY, getSizeX(), getSizeY());
	}
	
	public Sprite getReverseSprite() {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(sprite.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}

}
