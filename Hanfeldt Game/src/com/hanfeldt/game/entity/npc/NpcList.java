package com.hanfeldt.game.entity.npc;

import java.util.ArrayList;

import com.hanfeldt.game.entity.npc.characters.Bill;
import com.hanfeldt.game.entity.npc.characters.Billy;
import com.hanfeldt.game.entity.npc.monsters.Zombie;

/**
 * 
 * @author ofeldt
 *
 */
public class NpcList {
	
	
	public static String[] characterList;/**ID's are in the order the NPC's are loaded in**/
	public static String[] monsterList;
	public static int[]  npcLayer;
	public static ArrayList<Integer> currentLevelNpcLocation;
	
	public ArrayList<Zombie> zombieCache;
	
	
	public NpcList(){
		zombieCache = new ArrayList<Zombie>();
	}
	
	public void loadCharacters(){
		int currentCharacter = 0;//TODO declare outside method
		for(int i = 0; i < characterList.length; i++){
			switch(characterList[i]){
			case "Bill":
				Spawner.spawnNpc(new Bill(currentLevelNpcLocation.get(currentCharacter), currentLevelNpcLocation.get(currentCharacter + 1)), npcLayer[i]);
				break;
			case "Billy":
				Spawner.spawnNpc(new Billy(currentLevelNpcLocation.get(currentCharacter), currentLevelNpcLocation.get(currentCharacter + 1)), npcLayer[i]);
				break;
			}
			currentCharacter+=2;
		}
	}
	
	public void loadMonsters(){
		for(Zombie z : zombieCache){
			Spawner.spawnNpc(z, 1);//TODO placeholder layer
		}
	}
}
