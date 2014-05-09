package com.hanfeldt.game;

/**
 * Values.java - contains values that would be used often in the game,
 * such as bullet damage, zombie damage etc.
 * @author David Ofeldt
 *
 */
public class Values {
	
	/*
	 * Assume damage_id is damage to player, and that damage_taken_id is damage taken from other sources
	 * Ensure all Id's are unique
	 * TODO: Order all Id's correctly (It's a mess, I know.)
	 */
	
	public static int fall_damage_id = 1;
	public static int fall_death_id = 3;
	
	public static int zombie_damage_id = 2;
	public static int zombie_damage_from_bullet_id = 2;
	public static int zombie_damage_to_player = 10;
	public static int zombie_max_health = 75;
	
	public static int npc_out_of_map_id = 5;
	
	public static int bullet_damage_id = 4;
	public static int bullet_damage_dealt_to_zombie = 10;
	public static int bullet_damage_dealt_to_player = 60;
	
	public static int player_max_health = 100;
	
	public static int max_npc_default = 5;
	public static int max_zombie = 3;
}
