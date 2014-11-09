package com.hanfeldt.game.entity.npc;

import java.util.ArrayList;

import com.hanfeldt.game.entity.npc.characters.Bill;
import com.hanfeldt.game.entity.npc.characters.Billy;

/**
 * 
 * @author ofeldt
 *
 */
public class NpcList {
	
	
	public static String[] characterList;
	public static String[] monsterList;//Unused
	public static int[]  npcLayer;
	public static ArrayList<Integer> currentLevelNpcLocation;
	
	
	public NpcList(){
		
	}
	
	public static void loadCharacters(){
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
}
