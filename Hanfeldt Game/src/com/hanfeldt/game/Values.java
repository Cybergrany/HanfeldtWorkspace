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
	 */
	
	public static int fall_damage_id = 1;
	public static int fall_death_id = 3;
	
	public static int zombie_damage_id = 2;
	public static int zombie_damage_from_bullet_id = 2;
	public static int zombie_damage_to_player = 10;
	public static int zombie_max_health = 75;
	
	public static int bullet_damage_id = 4;
	public static int bullet_damage_dealt_to_zombie = 40;
	public static int bullet_damage_dealt_to_player = 60;
	
	public static int player_max_health = 100;
}
