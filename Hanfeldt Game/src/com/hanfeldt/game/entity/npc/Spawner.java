package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.Main;

public class Spawner { 
	
	public Spawner(){
		
	}
	
	public static void spawnNpc(Npc npc, int layer){
		npc.setLayer(layer, false);
		Main.getGame().getEntityManager().addNpc(npc);
	}
}
