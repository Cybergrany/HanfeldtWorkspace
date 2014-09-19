package com.hanfeldt.game.scripting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.event.command.CommandEvent;
import com.hanfeldt.game.event.command.CommandNotFoundException;
import com.hanfeldt.io.Debug;

public class Dialogue {
	private static BufferedImage dialogueBox;
	private String[] text;
	
	public Dialogue(String path) throws IOException  {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(Dialogue.class.getResourceAsStream("/dialogues/" + path)));
		String line;
		while((line = br.readLine()) != null) {
			if(line.startsWith("/")){
				try {
					CommandEvent.checkCommand(line.replace("/", ""));
				} catch (CommandNotFoundException e) {
					Debug.printStackTraceDebug(e);
				}
			}else{
				lines.add(line);
			}
		}
		br.close();
		text = new String[lines.size()];
		for(int i=0; i<lines.size(); i++) {
			text[i] = lines.get(i);
		}
	}
	
	public void render(Graphics g) {
		if(dialogueBox == null) {
			return;
		}
		g.drawImage(dialogueBox, 0, 0, Main.WIDTH, Main.HEIGHT, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Kai", Font.PLAIN, 10));
		for(int i=0; i<text.length; i++) {
			g.drawString(text[i], 10, i * 10 +20);
		}
		g.drawString("<Press Space to continue>", 20, 90);
	}
	
	public static void loadImage(String path) {
		try {
			dialogueBox = ImageIO.read(Dialogue.class.getResource("/images/" + path));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
