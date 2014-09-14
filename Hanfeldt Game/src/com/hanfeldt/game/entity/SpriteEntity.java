package com.hanfeldt.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;

/***
 * OOP FTW
 * This is going to save a load of typing, and make the code cleaner what with the amount of Entities that need sprites.
 * @author ofeldt
 *
 */
public class SpriteEntity extends Entity{
	
	public Sprite sprite;
	public int sizeX, sizeY;
	
	/**
	 * Needed for entities that do not have a sprite in some cases.
	 * For example, Bullets
	 * @param x
	 * @param y
	 */
	public SpriteEntity(int x, int y) {
		super(x, y);
	}

	public SpriteEntity(Sprite s, int x, int y){
		super(x, y);
		sprite = s;
		sizeX = sprite.getTileWidth();
		sizeY = sprite.getTileHeight();
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
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public Rectangle getBounds(){
		Rectangle r = new Rectangle(sizeX, sizeY, getSizeX(), getSizeY());
		r.setLocation(getX(), getY());
		return r;
	}
	
	public Sprite getReverseSprite() {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(sprite.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}
	
	public Sprite getReverseSprite(Sprite s) {
		BufferedImage image = new BufferedImage(getSizeX(), getSizeY(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		g.drawImage(s.getImage(), getSizeX(), 0, 0, getSizeY(), 0, 0, getSizeX(), getSizeY(), null);
		g.dispose();
		return new Sprite(image);
	}

}
