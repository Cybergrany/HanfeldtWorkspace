package com.hanfeldt.game.entity;

import java.awt.Point;
import java.util.ArrayList;

import com.hanfeldt.game.Main;

public class Entity {
	protected long totalTicks = 0;
	private float x, y;
	protected int layer = 1;
	protected float velX = 0f;
	protected float velY = 0f;
	float velXMax = 10f, velYMax = 3f;
	protected boolean direction = true; //Right = true, Left = false
	protected boolean falling = false;
	boolean isMovingLeft = false, isMovingRight = false;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		if(falling) {
			velY += Main.GRAVITY;
		}else{
			if(velY > 0.0f) {
				velY = 0;
			}
		}
		if(velX < -velXMax) {
			velX = -velXMax;
		}else if(velX > velXMax) {
			velX = velXMax;
		}
		
		if(velY < -Main.TERMINAL_VELOCITY) {
			velY = -Main.TERMINAL_VELOCITY;
		}else if(velY > Main.TERMINAL_VELOCITY) {
			velY = Main.TERMINAL_VELOCITY;
		}
		
		changeX(velX);
		changeY(velY);
		isMovingLeft = velX < 0;
		isMovingRight = velX > 0;
		totalTicks++;
	}
	
	//java.awt.Point can be more convenient to use sometimes
	public void setLocation(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getX() {
		return (int) x;
	}
	
	public int getY() {
		return (int) y;
	}
	
	public void changeX(float change) {
		x += change;
	}
	
	public void changeY(float change) {
		y += change;
	}
	
	public int getTileX() {
		return ((int) x) /Main.TILE_SIZE;
	}
	
	public int getTileY() {
		return ((int) y) /Main.TILE_SIZE;
	}
	
	public void setTileX(int x) {
		setX(x *Main.TILE_SIZE);
	}
	
	public void setTileY(int y) {
		setY(y *Main.TILE_SIZE);
	}
	
	public void changeVelX(float change){
		velX += change;
	}
	
	public void changeVelY(float change){
		velY += change;
	}
	
	public void setVelX(float vx) {
		velX = vx;
	}
	
	public void setVelY(float vy) {
		velY = vy;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public void setVelXMax(float vxm) {
		velXMax = vxm;
	}
	
	public boolean isMovingRight() {
		return isMovingRight;
	}
	
	public boolean isMovingLeft() { 
		return isMovingLeft;
	}
	/**
	 * Right = true, Left = false
	 */
	public void setDirection(boolean dir) {//JavaDoc shows up in autocomplete! // (Ronan) Oh right//Hey Ronan//How you doin'?
		direction = dir;
	}
	
	/**
	 * 
	 * @return boolean direction: Right = true, Left = false
	 */
	public boolean getDirection() {
		return direction;
	}
	
	public int getSizeX() {
		return getX() *Main.TILE_SIZE;
	}
	
	public int getSizeY() {
		return getY() *Main.TILE_SIZE;
	}
	
	/**
	 * set the layer in which the entity is in.
	 */
	public void setLayer(int l, boolean moving){
		if(moving){
//			Main.getGame().getLayers().get(layer).removeEntity(this);
//			Main.getGame().getLayers().get(l).addEntity(this);TODO See if this works
			Main.getGame().getEntityManager().removeEntity(this);
			layer = l;
			Main.getGame().getEntityManager().addEntity(this);
		}else{
			layer = l;
		}
	}
	
	/**
	 * Move up a layer
	 */
	public void moveToLayerAbove(){
		int current = layer;
		if(current < Main.getGame().getLevels().layerAmount){
//			Main.getGame().getLayers().get(current).removeEntity(this);
			Main.getGame().getEntityManager().removeEntity(this);
			layer = current + 1;
			Main.getGame().getEntityManager().addEntity(this);
		}else{
			return;
		}
	}
	
	/**
	 * Move down a layer
	 */
	public void moveToLayerBelow(){
		int current = layer;
		if(current > 0){
//			Main.getGame().getLayers().get(current).removeEntity(this);
			Main.getGame().getEntityManager().removeEntity(this);
			layer = current - 1;
			Main.getGame().getEntityManager().addEntity(this);
		}else{
			return;
		}
	}
	
	/**
	 * The layer in which the entity is in. 
	 */
	public int getLayer(){
		return layer;
	}
	
	/**
	 * Returns the closest entity to this entity in Point form
	 */
	public Point getClosestEntity(){
		ArrayList<Entity> allEnts = Main.getGame().getEntityManager().getEntities();
		
		double shortestDist = Integer.MAX_VALUE;
		int shortestDistIndex = 0;
		
		for(int i=0; i<allEnts.size(); i++) {
			Entity compareTo = allEnts.get(i);
			if(this == compareTo) {
				continue;
			}
			int distX = Math.abs(compareTo.getX() - getX());
			int distY = Math.abs(compareTo.getY() - getY());
			
			double distance = Math.sqrt(distX * distX + distY * distY);
			
			if(distance < shortestDist) {
				shortestDist = distance;
				shortestDistIndex = i;
			}
		}
		
		Entity closestEntity = allEnts.get(shortestDistIndex);
		return new Point(closestEntity.getX() + (closestEntity.getSizeX() /2), closestEntity.getY() + (closestEntity.getSizeY() /2));
	}
	//TODO: Finish these methods and use in BulletNPCFired to assit in aiming
	/**
	 * Returns the closest entity to a given x and y co-ord in Point form
	 * @param x - The x co-ord of the entity being measured from
	 * @return y - The y co-ord of the entity being measured from
	 */
//	public static Point getClosestEntity(int x, int y){
//		
//	}
	
}
