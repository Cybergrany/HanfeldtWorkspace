package com.hanfeldt.game.display;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.hanfeldt.game.Main;

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
		for(int i = 1; i< wepSprites.size()+1;i++){
			
		}
	}
	
	public void render(Graphics g){
		for(int i = 1; i < wepSprites.size()+1; i++){
			wepSprites.get(i-1).draw(g, circle.getPoint(i*spacing).x, circle.getPoint(i*spacing).y, InventorySprite.MEDIUM);
			
			if(circle.withinSector(Main.mouseX, Main.mouseY, 
						(int)circle.getAngle(circle.getPoint(i*spacing-spacing/2).x, circle.getPoint(i*spacing-spacing/2).y), 
						(int)circle.getAngle(circle.getPoint(i*spacing+spacing/2).x, circle.getPoint(i*spacing+spacing/2).y))){
				g.drawOval(Main.mouseX-5, Main.mouseY-5, 10, 10);
				g.drawLine(Main.mouseX, Main.mouseY, position.x, position.y);
			}
		}

		g.drawOval(position.x - radius, position.y - radius, radius*2, radius*2);
		if(Main.debug){
			g.drawOval(position.x - radius, position.y - radius, radius*2, radius*2);
			g.drawLine(circle.getPoint(40).x, circle.getPoint(40).y, position.x, position.y);
			g.drawString(Double.toString(circle.getAngle(Main.mouseX, Main.mouseY)), Main.mouseX, Main.mouseY);
		}
	}
}
