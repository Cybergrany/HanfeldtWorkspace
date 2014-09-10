package com.hanfeldt.game.display;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;

public class SpriteSheet {
	private final BufferedImage sheet;
	
	private static SpriteSheet playerSheet, monsterSheet, characterSheet, blockSheet, itemSheet, miscSheet;
	public static final int player = 0, monster = 1, character = 2,  block = 3, item = 4, misc = 5;
	private static boolean initialized = false;
	private static String basePath = "/images/sprites/";
	
	public SpriteSheet(String filePath) {
		BufferedImage temp = null;
		
		try {
			temp = ImageIO.read(Main.class.getResource(filePath));
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			sheet = temp;
		}
	}
	
	public BufferedImage getImage(int row, int col, int width, int height) {
		return sheet.getSubimage(row * Main.SPRITE_SIZE, col * Main.SPRITE_SIZE, width * Main.SPRITE_SIZE, height * Main.SPRITE_SIZE);
	}
	
	public static void initSheets(){
		playerSheet = new SpriteSheet(basePath + "characters/player_standard.png");
		monsterSheet = new SpriteSheet(basePath + "characters/monsters.png");
		characterSheet = new SpriteSheet(basePath + "characters/NpcCharacters.png");
		blockSheet = new SpriteSheet(basePath + "blocks/blocks.png");
		itemSheet = new SpriteSheet(basePath + "items/items.png");
		miscSheet = new SpriteSheet(basePath + "misc.png");
		initialized = true;
	}
	
	public static SpriteSheet getSheet(int sheetType){
		if(initialized)
		switch(sheetType){
			case(player):
				return playerSheet;
			case(monster):
				return monsterSheet;
			case(character):
				return characterSheet;
			case(block):
				return blockSheet;
			case(item):
				return itemSheet;
			case(misc):
				return miscSheet;
		}
		return null;
	}
	
}
