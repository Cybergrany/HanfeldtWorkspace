package com.hanfeldt.game.scripting;

import java.awt.Graphics;
import java.io.IOException;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.event.ItemGiveEvent;
import com.hanfeldt.game.level.LevelLoader;
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
	private static int currentDialogue = 0;
	private static int[] dialogueTriggerX;
	private static int currentLevel;
	private static String[] actions;
	
	public NpcScript(Main main, Player p){
		this.main = main;
		this.player = p;
	}
	
	public void tick(){
		if(dialogue != null && main.getListener().spaceDown && !main.getListener().spaceDownLastTick) {
			dialogue = null;
		}
		try {
			if(dialogue == null && player.getX() > dialogueTriggerX[currentDialogue]) {
				dialogue = new Dialogue((currentLevel + 1) + "." + currentDialogue + ".txt");
//				checkTriggerAction();
				currentDialogue++;
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
		currentLevel = Story.getCurrentLevel();//May as well put this here as this is called on level init
	}
	
	public void draw(Graphics g){
		if(!(dialogue == null)) {
			dialogue.render(g);
		}
	}
	
	public static void setNpcTriggersAndActions(int[] triggers, String[] action){
		dialogueTriggerX = new int[triggers.length];
		dialogueTriggerX = triggers;
		actions = action;
	}
	
	private void checkTriggerAction(){
		int i = currentDialogue;
		switch(actions[i]){
			case "give" :
				ItemGiveEvent.checkGiveCommand(actions[i + 1]);
				break;
		}
	}
}
