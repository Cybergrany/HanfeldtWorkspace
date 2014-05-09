package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.Main;

public class Spawner { 
	
	public Spawner(){
		
	}
	
	public void spawnNpc(Npc npc){
		Main.npc.add(npc);
	}
	
	public void tick(){
		
	}
	
}
