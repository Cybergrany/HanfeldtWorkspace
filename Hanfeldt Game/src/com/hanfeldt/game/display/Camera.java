package com.hanfeldt.game.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.EntityLiving;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.particles.Gore;
import com.hanfeldt.game.entity.particles.GoreSpawn;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.entity.projectile.GrenadeRPG;
import com.hanfeldt.game.tile.Tile;

/**
 * Camera is the class which allows entities and other objects to be rendered to the game in relation to one single origin point.
 * This is useful for instances when objects need to be aware of their location in relation to other objects of different type.
 * The camera can easily be manipulated by moving, zooming and more. (To be implemented)
 * @author David Ofeldt
 * @author Ronan Hanley
 *
 */
public class Camera {
	private int x, y;
	private Player player;
	private Entity entity, previousEntity;
	private int shakingTicks, zoom, playerMoveWeightX = 50, playerPanToSpeed = 4;
	private boolean scrolled = false, scrolledleft = false, scrolledright = false, 
									followingEntity = false, dir;
	private Random rand = new Random();
	
	public Camera(int x, int y, Player p) {
		this.x = x;
		this.y = y;
		this.player = p;
		dir = player.isMovingRight();
		zoom = 100;
		setFollowingEntity(Main.getGame().getPlayer());
	}
	
	public void tick() {
		//Follow player/entity with dynamic movements
		if(followingEntity && entity != null){
			int px = entity.getX() - (Main.WIDTH / 2) + (Main.TILE_SIZE/2);
			if(entity.isMovingRight()){//Moving right
				if(x < px + playerMoveWeightX && !scrolledleft){
					//Bias camera to show action on right hand side of entity sprite
					changeX((int)entity.getVelX() * playerPanToSpeed); 
				}else{
					scrolledleft = true;
					x= px + playerMoveWeightX;
				}
			}else if(entity.isMovingLeft()){
				if(x > px - playerMoveWeightX && !scrolledright){
					//Bias camera to show action on left hand side of entity sprite
					changeX((int)entity.getVelX() * playerPanToSpeed); 
				}else{
					scrolledright = true;
					x= px - playerMoveWeightX;
				}
			}
			
			if(dir != entity.isMovingRight() && (scrolledleft || scrolledright)){
				scrolledleft = false;
				scrolledright = false;
				dir = entity.isMovingRight();
			}
			
			// Stop the camera going offscreen
			
			if(x < 0) {
				x = 0;//Holy shit this code blew my mind
			}
			
			y = entity.getY() - (Main.HEIGHT / 2 + (Main.TILE_SIZE/2) -10);
	}
		
		if(shakingTicks > 0) {
			x += rand.nextInt(5) -2;
			y += rand.nextInt(3) -1;
			shakingTicks--;
		}
	}
	
	/**
	 * Render a {@link Sprite} object on the level.
	 * <p>
	 * <b>Please note that if the Sprite is larger than
	 * the window size, then it will not be rendered if
	 * the leftmost corner of that sprite is off-window.
	 * Use {@link #renderImageNoOff(Graphics, BufferedImage, int, int)} if you
	 * wish to avoid this.</b>
	 * @param g a {@link Graphics} object
	 * @param s the {@link Sprite} object that is to be drawn
	 * @param xOff x-coordinate of the sprite to be drawn
	 * @param yOff y-coordinate of the sprite to be drawn
	 * @see Sprite
	 */
	public void renderSprite(Graphics g, Sprite s, int xOff, int yOff) {
		int screenX = xOff - x;
		int screenY = yOff - y;
		if(screenX + s.getWidth() >= 0 &&
			screenX <= Main.WIDTH &&
			screenY + s.getHeight() >= 0 &&
			screenY <= Main.HEIGHT) {
				s.draw(g, screenX, screenY, zoom);
		}
	}
	
	/**
	 * Render an enlarged Sprite object on the screen
	 * <p>
	 * <b>Please note that if the Sprite is larger than
	 * the window size, then it will not be rendered if
	 * the leftmost corner of that sprite is off-window.
	 * Use {@link #renderImageNoOff(Graphics, BufferedImage, int, int)} if you
	 * wish to avoid this.</b>
	 * @param g a {@link Graphics} object
	 * @param s the {@link Sprite} object that is to be drawn
	 * @param xOff x-coordinate of the sprite to be drawn
	 * @param yOff y-coordinate of the sprite to be drawn
	 * @param enlargement factor to enlarge Sprite object by.
	 */
	public void renderSprite(Graphics g, Sprite s, int xOff, int yOff, int enlargement) {
		int screenX = xOff - x;
		int screenY = yOff - y;
		if(screenX + s.getWidth() >= 0 &&
			screenX <= Main.WIDTH &&
			screenY + s.getHeight() >= 0 &&
			screenY <= Main.HEIGHT) {
				s.draw(g, screenX, screenY, enlargement);
		}
	}
	
	/**
	 * Renders an {@link EntityItem}
	 * @param g a graphics object.
	 * @param e the item to be rendered
	 * @see EntityItem
	 */
	public void renderEntityItem(Graphics g, EntityItem e){
		if(e.getDirection()){
			renderSprite(g, e.getReverseSprite(), e.getX(), e.getY());
		}else{
			renderSprite(g, e.getSprite(), e.getX(), e.getY());
		}
	}
	
	/**
	 * Renders an {@link EntityLiving}
	 * @param g a graphics object
	 * @param e the entity to be rendered
	 * @see EntityLiving
	 */
	public void renderEntityLiving(Graphics g, EntityLiving e) {
		if(e.getSprite().getWalkingAnimsLength() > 1){
			renderImage(g, e.getWalkingImage(), e.getX(), e.getY());
		}else if(e.getDirection()) {
			renderSprite(g, e.getSprite(), e.getX(), e.getY());
			}else{
				renderSprite(g, e.getReverseSprite(), e.getX(), e.getY());
			}

//		camera.renderImage(g, npc.get(i).getWalkingImage(), npc.get(i).getX(),  npc.get(i).getY());
	}
	
	/**
	 * Render a level tile
	 * @param g a graphics object
	 * @param t the tile to be rendered
	 * @see Tile
	 * @see Level
	 */
	public void renderTile(Graphics g, Tile t) {
		renderSprite(g, t.getSprite(), t.getX(), t.getY());
	}
	
	/**
	 * Render a bullet
	 * @param g a graphics object
	 * @param b the bullet to be rendered
	 * @see Bullet
	 */
	public void renderBullet(Graphics g, Bullet b) {
		int screenX = b.getX() - x;
		int screenY = b.getY() - y;
		if(screenX +1 >= 0 &&
			screenX <= Main.WIDTH &&
			screenY +1 >= 0 &&
			screenY <= Main.HEIGHT) {
			g.setColor(Bullet.COLOR);
			g.fillRect(screenX, screenY, 1, 1);
		}
	}
	
	/**
	 * Render a group of gore
	 * @param g a graphics object
	 * @param gs the {@link GoreSpawn} object to be rendered
	 * @see Gore
	 * @see GoreSpawn
	 */
	public void renderGore(Graphics g, GoreSpawn gs){
			for(Gore gore : gs.gore){
				int screenX = gore.getX() - x;
				int screenY = gore.getY() - y;
				if(screenX + 1 >= 0 &&
					screenX <= Main.WIDTH &&
					screenY + 1 >= 0 &&
					screenY <= Main.HEIGHT){
					g.setColor(Color.RED);
					g.fillRect(screenX, screenY, 1, 1);
			}else{gs.removeGore();}
		}
	}
	
	/**
	 *Render a bullet which is represented by a sprite image.
	 *For example; {@link GrenadeRPG}
	 * @param g a graphics object
	 * @param b the bullet
	 * @param s the associated {@link Sprite}
	 * @see Bullet
	 * @see Sprite
	 */
	public void renderBullet(Graphics g, Bullet b, Sprite s){
		renderSprite(g, s, b.getX(), b.getY());
	}
	
	/**
	 * Render an image (BufferedImage) to the level.
	 * This is done by converting the image into a {@link Sprite}
	 * <p>
	 * <b>Please note that if the Sprite is larger than
	 * the window size, then it will not be rendered if
	 * the leftmost corner of that sprite is off-window.
	 * Use {@link #renderImageNoOff(Graphics, BufferedImage, int, int)} if you
	 * wish to avoid this.</b>
	 * @param g a Graphics object
	 * @param image the {@link BufferedImage} to be rendered
	 * @param x x-coordinate of the image to be drawn
	 * @param y y-coordinate of the image to be drawn
	 * @see BufferedImage
	 * @see Sprite
	 */
	public void renderImage(Graphics g, BufferedImage image, int x, int y) {
		renderSprite(g, new Sprite(image), x, y);
	}
	
	/**
	 * Render an image (BufferedImage) to the level, with added enlargement
	 * This is done by converting the image into a {@link Sprite}
	 * <p>
	 * <b>Please note that if the Sprite is larger than
	 * the window size, then it will not be rendered if
	 * the leftmost corner of that sprite is off-window.
	 * Use {@link #renderImageNoOff(Graphics, BufferedImage, int, int)} if you
	 * wish to avoid this.</b>
	 * @param g a Graphics object
	 * @param image the {@link BufferedImage} to be rendered
	 * @param x x-coordinate of the image to be drawn
	 * @param y y-coordinate of the image to be drawn
	 * @param enlargement the amount of enlargement required
	 * @see BufferedImage
	 * @see Sprite
	 */
	public void renderImage(Graphics g, BufferedImage image, int x, int y, int enlargement) {
		renderSprite(g, new Sprite(image), x, y, enlargement);
	}
	
	/**
	 * Renders a Sprite without removing it if the origin is outside the screen.
	 * @param g
	 * @param image
	 * @param x
	 * @param y
	 */
	public void renderImageNoOff(Graphics g, BufferedImage image, int x, int y) {
		Sprite s = new Sprite(image);
		s.draw(g, x, y);
	}
	
	/***
	 * Renders a Sprite without removing it if the origin is outside the screen.
	 * @param g
	 * @param image
	 * @param x
	 * @param y
	 * @param enlargement
	 */
	public void renderImageNoOff(Graphics g, BufferedImage image, int x, int y, int enlargement) {
		Sprite s = new Sprite(image);
		s.draw(g, x, y, enlargement * zoom);
	}
	
	/**
	 * Draws a rectangular border around the screen for a cinematic effect
	 * @param g
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @param c
	 */
	//TODO: Implement
	public void drawRectBorder(Graphics g, int x1, int x2, int y1, int y2, Color c, boolean dynamic){
		g.setColor(c);
		for(int dx = 0; dx < Main.WIDTH; dx++){
			for(int dy = 0; dy < Main.HEIGHT; dy++){
				if(dynamic){
					if((dx > x1||dx <  x2) &&
						(dy < y1 + (entity.getY() / Main.TILE_SIZE)
								|| dy > y2 - (entity.getY() / Main.TILE_SIZE))){
							g.drawLine(dx, dy, dx, dy);
						}
				}else{
					if((dx > x1 ||dx < x2) &&
						(dy < y1|| dy > y2)){
							g.drawLine(dx, dy, dx, dy);
					}
				}
			}
		}
		
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void changeX(int c) {
		x += c;
	}
	
	public void changeY(int c) {
		x += c;
	}
	
	public void changeZoom(int z){
		zoom += z;
	}
	
	public void setZoom(int z){
		zoom = z;
	}
	
	/**
	 * Scroll a certain distance along the x-axis
	 * @param distance
	 * @param speed
	 */
	public void scrollX(int start, int distance, int speed){
		int dis = start + distance;
		disableFollow();
		if(dis > 0){
			if(x < dis){
				changeX(speed);
			}else if(x > dis)
				changeX(-speed);
		}else{
			if (x > 0)//Won't scroll lower than 0
			changeX(-speed);
		}
	}
	
	/**
	 * Scrolls to an entity along the x-axis
	 * @param e
	 */
	//TODO:Finish
	public void scrollXToEntity(Entity e, int speed){
//		scrollX(x, x + (e.getX() - x), speed);
	}
	
	/**
	 * Follow the specified entity
	 * @param e - the entity to follow.
	 */
	public void setFollowingEntity(Entity e){
		previousEntity = entity;
		entity = e;
		scrollXToEntity(entity, 2);
		followingEntity = true;
	}
	
	/**
	 * Follow human-controlled player
	 */
	public void setFollowingPlayer(){
		setFollowingEntity(Main.getGame().getPlayer());
	}
	
	/**
	 * Stop Following entity
	 */
	public void disableFollow(){
		followingEntity = false;
	}
	
	/**
	 * Add amount of ticks left before shaking stops
	 * @param a
	 */
	public void addShakingTicks(int a) {
		shakingTicks += a;
	}
	
	/**
	 * Shakes the camera to suggest injury/explosions
	 */
	public void shake() {
		addShakingTicks(rand.nextInt(10) +20);
	}
	
}
