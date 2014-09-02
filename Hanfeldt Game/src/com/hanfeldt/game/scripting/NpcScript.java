package com.hanfeldt.game.scripting;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.state.Story;

/**
 * Scripting NPC behavior for cutscenes etc
 * @author David Ofeldt
 *
 */
public class NpcScript {
	
	private Player player;
	private Main main;
	private Dialogue dialogue;
	private int currentDialogue = 0, totalDialogues = 0;
	private int[][] dialogueTriggerX = new int[][] {{0, 400}, {Main.TILE_SIZE *35}};
	private int currentLevel;
	
	public NpcScript(Main main, Player p){
		this.main = main;
		this.player = p;
		
		currentLevel = Story.getCurrentLevel();
	}
	
	public void tick(){
		
	}
	
	public void render(){
		
	}
	
	public void addNPC(int level){
		
	}
}
