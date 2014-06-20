package com.hanfeldt.io;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.advanced.AdvancedPlayer;

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
	
	public static synchronized void playMp3(final String sound){;
		if(!Main.muted){
			new Thread(new Runnable() {
				public void run() {
					while(true) {
						try {
							AdvancedPlayer ap = new AdvancedPlayer
						            (
						                Sound.class.getResourceAsStream(sound),
						                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
						            );
						            ap.play();
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}
	
	//This loads the class, to avoid lag spikes!
	public static void touch() {}
	
}
