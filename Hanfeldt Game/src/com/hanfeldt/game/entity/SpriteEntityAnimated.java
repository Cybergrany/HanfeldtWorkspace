package com.hanfeldt.game.entity;

import com.hanfeldt.game.display.Sprite;

/**
 * Animated Sprite Entities. Not currently in use, but it's here if needed.
 * @author ofeldt
 *
 */
public class SpriteEntityAnimated extends SpriteEntity{

	public static final int ticksPerAnimChange = 4;

	public SpriteEntityAnimated(Sprite s, int x, int y) {
		super(s, x, y);
		
	}

}
