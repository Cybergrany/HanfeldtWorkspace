package com.hanfeldt.game.state;

import java.awt.Graphics;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.level.LevelArcade;

public class Arcade extends Playing {
	
	public Arcade(Main main) {
		super(main);
//		main.setLevels(new Level(new LevelArcade(main.getPlayer());
		System.err.println("This is temporarily broken. To fix, please see Arcade.java, line 14");
		Player p = main.getPlayer();
		p.setX(Main.WIDTH /2);
		p.setY(Main.HEIGHT - Main.TILE_SIZE * (1 + p.getTileSizeY()));
		p.setHealth(Player.maxHealth);
		Main.getGame().createGoreList();
	}
	
	public void tick() {
		super.tick();
		Main.getGame().getLevels().tick();
	}
	
	public void draw(Graphics g) {
		((LevelArcade) (Main.getGame().getLevels())).render(g, camera);
		super.draw(g);
		g.dispose();
	}

}
