package com.hanfeldt.game.entity.npc;

import com.hanfeldt.game.entity.npc.characters.Bill;
import com.hanfeldt.game.entity.npc.characters.Billy;

/**
 * 
 * @author ofeldt
 *
 */
public class NpcList {
	
	public static String[] characterList;
	public static String[] monsterList;
	
	public NpcList(){
		
	}
	
	public void loadCharacter(String character, int x, int y){
		for(int i = 0; i < characterList.length; i++){
			if(character == characterList[i]){
				switch(characterList[i]){
				case "Bill":
					Spawner.spawnNpc(new Bill(x, y));
				case "Billy":
					Spawner.spawnNpc(new Billy(x, y));
				}
			}
		}
	}
}
