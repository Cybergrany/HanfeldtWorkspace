package com.hanfeldt.game.scripting;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Player;

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
	
	public NpcScript(Main main, Player p){
		this.main = main;
		this.player = p;
	}
	
	public void tick(){
		
	}
	
	public void render(){
		
	}
	
	public void addNPC(int level){
		
	}
}
