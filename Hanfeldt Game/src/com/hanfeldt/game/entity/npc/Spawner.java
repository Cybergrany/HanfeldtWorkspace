package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.Main;

public class Spawner { 
	
	public Spawner(){
		
	}
	
	public static void spawnNpc(Npc npc){
		Main.getGame().getNpc().add(npc);
	}
	
	public void tick(){
		
	}
	
}
