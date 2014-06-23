package com.hanfeldt.game.shop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.Sprite;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.game.weapon.Pistol;
import com.hanfeldt.game.weapon.Weapon;
import com.hanfeldt.io.Listener;
import com.hanfeldt.io.Sound;

public class Label {
	private static Listener listener;
	private final int x, y;
	private final int width = 30, height = 15;
	private Color color;
	private Sprite icon;
	private boolean selected;
	private String name;
	private int cost;
	
	public Label(String name, int cost, int x, int y, Color color, int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
		icon = new Sprite(Main.getSpritesheet(), spriteX, spriteY, spriteWidth, spriteWidth);
		this.x = x;
		this.y = y;
		this.color = color;
		this.name = name;
		this.cost = cost;
	}
	
	public void tick() {
		selected = new Rectangle(Main.mouseX, Main.mouseY, 1, 1).intersects(getBounds());
		if(selected && listener.mouseDown && !listener.mouseDownLastTick) {
			//User clicked the label. Whipee!
			Weapon wep = Main.getGame().getPlayer().getWeaponEquipped();
			AmmoWeapon ammoWep = null;
			if(wep instanceof AmmoWeapon) {
				ammoWep = (AmmoWeapon) wep;
			}
			switch(name) {
			case "pistol":
				if(Main.getGame().getPlayer().getMoney() >= cost) {
					if(wep instanceof Pistol) {
						ammoWep.changeTotalAmmo(16);
					}else{
						wep = new Pistol(Main.getGame().getPlayer(), ammoWep.getAmmoInClip(), ammoWep.getTotalAmmo() +8);
					}
					Main.getGame().getPlayer().changeMoney(-cost);
					Sound.playSound("wep_bought.wav");
				}else{
					Sound.playSound("not_enough_money.wav");
				}
				break;
			default:
				System.out.println("Weapon not found: " + name);
				break;
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fill3DRect(x, y, width, height, true);
		if(selected) {
			g.setColor(Color.RED);
			g.drawRect(x -1, y -1, width +1, height +1);
		}
		g.setColor(Color.WHITE);
		icon.draw(g, x, y);
		g.drawString("$" + cost, x +3, y -2);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public static void setListener(Listener l) {
		listener = l;
	}
	
}
