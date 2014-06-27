package com.hanfeldt.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Dialogue {
	private static BufferedImage dialogueBox;
	private String[] text;
	
	public Dialogue(String path) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Dialogue.class.getResourceAsStream("/dialogues/" + path)));
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		text = new String[lines.size()];
		for(int i=0; i<lines.size(); i++) {
			text[i] = lines.get(i);
		}
	}
	
	public void render(Graphics g) {
		if(dialogueBox == null) {
			return;
		}
		g.drawImage(dialogueBox, 0, 0, Main.sizeX, Main.sizeY, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Kai", Font.PLAIN, 10));
		for(int i=0; i<text.length; i++) {
			g.drawString(text[i], 10, i * 10 +20);
		}
		g.drawString("<Press Space to continue>", 20, 90);
	}
	
	public static void loagImage(String path) {
		try {
			dialogueBox = ImageIO.read(Dialogue.class.getResource("/images/" + path));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
