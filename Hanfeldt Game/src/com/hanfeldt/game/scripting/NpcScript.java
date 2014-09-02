package com.hanfeldt.game.scripting;

import java.awt.Graphics;
import java.io.IOException;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.state.Story;
import com.hanfeldt.io.Debug;

/**
 * Scripting NPC behavior
 * @author David Ofeldt
 *
 */
public class NpcScript {
	
	private Player player;
	private Main main;
	private static Dialogue dialogue;
	private static int currentDialogue = 0, totalDialogues = 0;
	private static int[] dialogueTriggerX;
	private int currentLevel;
	
	public NpcScript(Main main, Player p){
		this.main = main;
		this.player = p;
		currentLevel = Story.getCurrentLevel();
	}
	
	public void tick(){
		if(dialogue != null && main.getListener().spaceDown && !main.getListener().spaceDownLastTick) {
			dialogue = null;
		}
		try {
			if(dialogue == null && player.getX() > dialogueTriggerX[currentDialogue]) {
				dialogue = new Dialogue((currentLevel + 1) + "." + totalDialogues + ".txt");
//				switch(totalDialogues) {
//				case 1:
//					player.setWeaponEquipped(new Pistol(player));
//					break;
//				case 2:
//					player.setWeaponEquipped(new M16(player));
//				}
				currentDialogue++;
				totalDialogues++;
			}
		}catch(IOException e) {
			System.err.println("Dialogue file not found for level " + currentLevel);
			Debug.printStackTraceDebug(e);
		}catch(Exception e){
//			Debug.printErrorDebug("Error while loading script.");
//			Debug.printStackTraceDebug(e);
		}
	}
	
	public static Dialogue getDialogue(){
		return dialogue;
	}
	
	public static void resetDialogue(){
		currentDialogue = 0;
	}
	
	public void draw(Graphics g){
		if(!(dialogue == null)) {
			dialogue.render(g);
		}
	}
	
	public static void setNpcTriggers(int[] triggers){
		dialogueTriggerX = new int[triggers.length];
		dialogueTriggerX = triggers;
	}
}
