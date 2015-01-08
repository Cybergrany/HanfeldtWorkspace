package com.hanfeldt.game.display;

import java.awt.Graphics;
import java.awt.Point;

import com.hanfeldt.game.Main;

public class WeaponSwitcher {
	
	public Point position;

	private int radius;
	
	public WeaponSwitcher(int x, int y, int radius){
		position = new Point(x, y);
		this.radius = radius;
	}
	
	public WeaponSwitcher(Point p, int radius){
		position = p;
		this.radius = radius;
	}
	
	public void tick(){
		double angle = Math.toDegrees(Math.acos(Math.abs(position.x - Main.mouseX) / position.distance(new Point(Main.mouseX, Main.mouseY))));//Pytagoras you sound man

		if(Math.pow((position.x - Main.mouseX), 2) + Math.pow((position.y - Main.mouseY), 2) >= Math.pow((radius / 4) * 1, 2)){//Check if outside inner quarter
			//outside
			System.out.println(angle);
			if(Main.mouseY > position.y){//lower half of circle
				if(position.x < Main.mouseX){//top right
					
				}else{//top left
					
				}
			}else{//Upper half
				if(position.x < Main.mouseX){//lower right
					
				}else{//lower left
					
				}
			}
		}else{
			//cursor within inner quarter
		}
		
		if(Math.pow((position.x - Main.mouseX), 2) + Math.pow((position.y - Main.mouseY), 2) >= Math.pow(radius / 2, 2)){//Check if outside circle
		//TODO add clipping on outer bounds of wheel (Main.getRobot)
			
		}else{//cursor is within circle
			//TODO add above if statement here if confinement to circle is required
		}
	}
	
	public void render(Graphics g){
		g.drawOval(position.x - radius/2, position.y - radius/2, radius, radius);
	}
}
