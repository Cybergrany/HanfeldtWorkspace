package com.hanfeldt.game.display;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.io.Debug;
import com.hanfeldt.game.weapon.Weapon;

public class WeaponSwitcher {
	
	public Point position;
	public ArrayList<InventorySprite> wepSprites;
	public CircleHelper circle;

	private int radius;
	private int spacing = 30;
	 
	public WeaponSwitcher(int x, int y, int radius){
		position = new Point(x, y);
		this.radius = radius;
	}
	
	public WeaponSwitcher(Point p, int radius){
		position = p;
		this.radius = radius;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g){
		for(int i = 1; i < wepSprites.size()+1; i++){
			InventorySprite is = wepSprites.get(i-1);
			int x = circle.getPoint(i*spacing).x;
			int y =  circle.getPoint(i*spacing).y;
			int w =is.getWidth();
			int h = is.getHeight();
			
			is.draw(g, x, y, InventorySprite.MEDIUM);
			
			if(new Rectangle(x-w/2, y-h/2, w * is.getSize() /10, h * is.getSize() / 10).intersects(Main.mouseX, Main.mouseY, 1, 1)){
				if(Main.getGame().getListener().mouseDown && !Main.getGame().getListener().mouseDownLastTick){
					if(is.getLinkedItem() instanceof Weapon){
						Main.getGame().getPlayer().setWeaponEquipped((Weapon)is.getLinkedItem());
						if(Main.debug)
							Debug.printDebug("Weapon switched to: " + is.getName());
					}
				}
			}
		}

		if(Main.debug){
			g.drawOval(position.x - radius, position.y - radius, radius*2, radius*2);
			g.drawLine(position.x, position.y, Main.mouseX, Main.mouseY);
			g.drawString(Double.toString(circle.getAngle(Main.mouseX, Main.mouseY)), Main.mouseX, Main.mouseY);
		}
	}
}
