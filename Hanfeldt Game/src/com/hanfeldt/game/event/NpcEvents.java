package com.hanfeldt.game.event;

import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.Zombie;

public class NpcEvents {
	
	public static int zombie_Id = 1;
	
	private Npc npc;
	private Zombie zombie;
	
	public NpcEvents(Npc npc){
		this.npc = npc;
	}
	
	public void tick(){//Don't think this needs to tick
		
	}
	
	public void damageNpc(int damage, int id){
		if(id == zombie_Id){
			zombie.changeHealth(-damage);
		}
	}
}
