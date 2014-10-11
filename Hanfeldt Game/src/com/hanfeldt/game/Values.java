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
	
	public static int npc_out_of_map_id = 5;
	
	public static int bullet_damage_id = 4;
	public static int bullet_damage_pistol = 40;
	public static int bullet_damage_M16 = 20;//Half the damage seems legit
	public static int bullet_damage_SniperRifle = 85;
	public static int bullet_damage_RPG = 100;//kaboom
	public static int bullet_explosive_damage_RPG = 100;
	
	public static int money_from_zombie = 25;
	public static int score_from_zombie = 100;
	
	public static int bullets_per_crate = 16; // You can change this name if you wants
	
	public static int player_max_health = 100;
	public static float player_max_speed = 1.8f;
	
	public static final int zombie_max_health = 75;
	
	public static int max_npc_default = 5;
	public static int max_zombie = 3;
}
