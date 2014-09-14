package com.hanfeldt.game.entity.item;

import java.util.Random;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityItem;

public class ZombieGore extends EntityItem{
	
	boolean dir = false, knocked = false;
	int knockback = 30;
	float knockspeed = 1.5f;
	int origX;

	public ZombieGore(int x, int y) {//Default
		super(new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 3, 1, 1), x, y);
	}
	
	public ZombieGore(Sprite s, int x, int y) {
		super(s, x, y);
		origX = x;
		dir = Main.getGame().getPlayer().getDirection();
		setFriction(getFriction() * (knockback / 10));
		knockspeed = (knockback / 10) / 2;
	}
	
	public ZombieGore(Sprite s, int x, int y, int knockback) {
		super(s, x, y);
		origX = x;
		this.knockback = knockback;
		dir = Main.getGame().getPlayer().getDirection();
		setFriction(getFriction() * (knockback / 10));
		knockspeed = (knockback / 10) / 2;
	}
	
	public void tick(){
		super.tick();
		if(!knocked){
			if(dir){
				setVelX(knockspeed);
				if(getX() > origX + knockback){
					knocked = true;
				}
			}else{
				setVelX(-knockspeed);
				if(getX() < origX - knockback){
					knocked = true;
				}
			}
		}else{
			if(getVelX() > 0)
			changeVelX(-(getVelX() * getFriction()));
			if(getVelX() < 0)
				 changeVelX(-(getVelX() * -getFriction()));
			if(dir && (getVelX() > 0 || getVelX() < 0.15f)) {
				setVelX(0);
			}else if(getVelX() < 0 || getVelX() > -0.15f) {
				setVelX(0);
			}
		}
	}
	
	public  ZombieGore randomGore(int x, int y){
		Sprite s = null;
		switch(new Random().nextInt(6)){
			case 0:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 1, 3, 1, 1);
				break;
			case 1:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 2, 3, 1, 1);
				break;
			case 2:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 3, 3, 1, 1);
				break;
			case 3:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 4, 3, 1, 1);
				break;
			case 4:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 5, 3, 1, 1);
				break;
			case 5:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 6, 3, 1, 1);
				break;
			case 6:
				s = new Sprite(SpriteSheet.getSheet(SpriteSheet.item), 5, 3, 1, 1);
				break;
		}
		if(new Random().nextBoolean()){
			s = getReverseSprite();
		}
		return new ZombieGore(s, x, y);
	}
}
