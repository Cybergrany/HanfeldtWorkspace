package com.hanfeldt.game.scripting;

import java.util.ArrayList;

import com.hanfeldt.game.entity.EntityLiving;

/**
 * Cutscenes
 * @author David Ofeldt
 *
 */
public class Scene {
	public ArrayList<EntityLiving> entities;
	private Long totalTicks = 0L;
	
	public Scene(ArrayList<EntityLiving> e) {
		entities = e;
	}
	
	public void tick() {
		totalTicks++;
	}
	
}
