package com.hanfeldt.game.npc;

import com.hanfeldt.game.Main;

public class Spawner { //I don't think it needs its own thread >_< We can change it back if you think otherwise
	
	public Spawner(){
		
	}
	
	public void spawnNpc(Npc npc){
		Main.npc.add(npc);
	}
	
}
