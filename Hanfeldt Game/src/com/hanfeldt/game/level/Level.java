package com.hanfeldt.game.level;

import java.awt.Graphics;

import com.hanfeldt.game.Camera;
import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Spawner;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.game.tile.Tile;

public class Level {
	/*
	 * NOTE: 0 = air, 1 = block
	 */
	
	protected Player player;
	public Tile[][] tiles;//Making this public static just to test things
	public static int level = 0;
	protected int sizeX, sizeY;
	protected Spawner spawner;
	protected Background bg;
	
	public void tick(){
		Main.getGame().getPlayer();
		if(player.levelFinished){
			if(level +1 >= Main.getGame().getLevels().length) {
				//Win code
				Main.getGame().setState(new GameWon(Main.getGame()));
			}else{
				level++;
				Story.setLevel(level);
				setBg(level);
				player.setX(0);
				player.setY(Main.HEIGHT - Main.tileSize - player.getSizeY());
				player.levelFinished = false;
			}
		}
		player.tick();
		
		for(int i=0; i<Main.npc.size(); i++) {
			Main.npc.get(i).tick();
		}
		bg.tick();
	}
	
	public void render(Graphics g, Camera c) {
		bg.draw(g);
		draw(g, c);
	}
	
	public void draw(Graphics g, Camera c) {
		bg.draw(g);
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles.length; j++) {
				c.renderTile(g, tiles[j][i]);
			}
		}
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, Tile t) {
		tiles[x][y] = t;
	}
	
	public void setBg(int currentLevel){
		bg = new Background(currentLevel);
	}
	
}
