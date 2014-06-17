package com.hanfeldt.io;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.hanfeldt.game.Main;

/**
 * Sound! Now you can hear yourself mine!
 * To use, simply call Sound.playSound("SoundName");
 * Ensure file SoundName.wav is in res/sounds/
 * 
 * @param playSound Sound file to be played
 * @author David Ofeldt
 * 
 */
public class Sound {
	
	public static synchronized void playSound(final String sound){//For sound, call Sound.playSound("SoundName");
		if(!Main.muted){
			try {
				 URL defaultSound = Sound.class.getResource("/sounds/" + sound);
			     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			     Clip clip = AudioSystem.getClip();
			     clip.open(audioInputStream);
			     clip.start();
			}catch(Exception e) {
				System.out.println("Failed to play sound! Stacktrace:");
				e.printStackTrace();
			}
		}
	}
}
