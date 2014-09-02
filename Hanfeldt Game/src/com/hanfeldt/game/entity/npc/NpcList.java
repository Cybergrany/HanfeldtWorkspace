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
	public static String[] monsterList;
	public static ArrayList<Integer> currentLevelNpcLocation;
	
	public NpcList(){
		
	}
	
	public static void loadCharacters(){
		int currentCharacter = 0;
		for(int i = 0; i < characterList.length; i++){
			switch(characterList[i]){
			case "Bill":
				Spawner.spawnNpc(new Bill(currentLevelNpcLocation.get(currentCharacter), currentLevelNpcLocation.get(currentCharacter + 1)));
				System.out.println("Bill placed at: ");
				System.out.println(currentLevelNpcLocation.get(currentCharacter) + "Y: " + currentLevelNpcLocation.get(currentCharacter + 1));
				break;
			case "Billy":
				Spawner.spawnNpc(new Billy(currentLevelNpcLocation.get(currentCharacter), currentLevelNpcLocation.get(currentCharacter + 1)));
				System.out.println("Billy placed at: ");
				System.out.println(currentLevelNpcLocation.get(currentCharacter) + "Y:  " + currentLevelNpcLocation.get(currentCharacter + 1));
				break;
			}
			currentCharacter+=2;
			System.out.println(currentCharacter);
		}
	}
}
