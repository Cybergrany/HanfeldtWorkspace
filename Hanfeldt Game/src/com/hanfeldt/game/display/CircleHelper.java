package com.hanfeldt.game.display;

import java.awt.Point;

public class CircleHelper {
	
	public Point position;
	public double radius;
	
	public CircleHelper(double radius){
		this.radius = radius;
		position = new Point(0, 0);
	}
	
	public CircleHelper(int x, int y, double radius){
		position = new Point(x, y);
		this.radius = radius;
	}
	
	/**
	 * Get angle between center of circle and other point, relative to 12o'clock
	 * @return
	 */
	public double getAngle(int x, int y){
		double angle1 = 90;
		double angle2 = Math.toDegrees(Math.atan2( y - position.y, x - position.x));
		
		if(angle2 < -90){
			return angle1 + Math.toDegrees(Math.atan2(position.y -  y, position.x - x)) + 180;
		}
		return angle1 + angle2;
	}
	
	/**
	 * Given angle, get co-ordinates of point on circle
	 * @param angle - in degrees
	 */
	public Point getPoint(int angle){
		double x = position.x + radius * Math.sin(angle);
		double y = position.y + radius * Math.cos(angle);
		
		return new Point((int)x, (int)y);
	}
	
	/**
	 * Check if a point is within a sector. No checks are run for being within circle.
	 * @param x
	 * @param y
	 * @param angle1- angle of first line
	 * @param angle2 - angle of second line
	 * @return
	 */
	public boolean withinSector(int x, int y, int angle1, int angle2){
		return getAngle(x, y) > angle1 && getAngle(x, y) < angle2;
	}
	
	/**
	 * Check if a point is within the circle
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean withinCircle(int x, int y){
		return !(Math.pow(position.x - x, 2) + Math.pow(position.y - y, 2) >= Math.pow(radius, 2));
	}
	
	/**
	 * Checks if point is within other circle with same centrepoint as this circle
	 * @param x
	 * @param y
	 * @param radius - radius of second circle
	 * @return
	 */
	public boolean withinCircle(int x, int y, int radius){
		return !(Math.pow(position.x - x, 2) + Math.pow(position.y - y, 2) >= Math.pow(radius, 2));
	}
}
