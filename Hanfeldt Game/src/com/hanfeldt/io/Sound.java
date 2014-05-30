package com.hanfeldt.io;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

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
				try{
					URL url = new Sound().getClass().getResource("/sounds/" + sound);
					String urls=url.toString(); 
					urls=urls.replaceFirst("file:/", "file:///");
					AudioClip ac=Applet.newAudioClip(new URL(urls));
					ac.play();
				}catch(Exception e){
					System.err.println("Error while playing sound");
					e.printStackTrace();
			}
		}
	}

}
