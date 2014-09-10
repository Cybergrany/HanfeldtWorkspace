package com.hanfeldt.game.event.command;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.characters.NPCCharacter;


public class SetCommandEvent {
	
	public SetCommandEvent(){
		
	}
	
	public static void checkSetCommand(String command){
		switch(command){
			case "npc_following":
				for(int i = 0; i < Main.getGame().getNpc().size(); i++){
					Npc npc = Main.getGame().getNpc().get(i);
					if(npc instanceof NPCCharacter){
						((NPCCharacter) npc).setFollowingPlayer(true);
					}
				}
				break;
		}
	}
}
