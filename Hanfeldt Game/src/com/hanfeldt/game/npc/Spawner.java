package com.hanfeldt.game.npc;

import java.util.Random;

import com.hanfeldt.game.Main;

public class Spawner implements Runnable{//Give it it's own thread, why not?
	public boolean isRunning = true;
	
	public Spawner(){
		new Thread(this).start();
	}
	
	public void spawnNpc(Npc npc){
		Main.npc.add(npc);
	}
	
	public void run(){
		while(isRunning){
			if(Main.npc.toArray().length < Npc.getMaxNpc()){
				spawnNpc(new Zombie(50, 96));
			}
			
			try{
				Thread.sleep(new Random().nextInt(500) + 500);
			}catch (Exception e){}
		}
	}
}
