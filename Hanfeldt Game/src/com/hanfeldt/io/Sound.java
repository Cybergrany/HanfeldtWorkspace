package com.hanfeldt.io;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

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
					playMusic();
					}
				}
			}).start();
		}
	}
	
	public static void playMusic(){
		SoundSystem soundSystem = null;
		try{
			SoundSystemConfig.addLibrary(LibraryJavaSound.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystem.libraryCompatible(LibraryJavaSound.class);
			soundSystem = new SoundSystem(LibraryJavaSound.class);
		}catch(SoundSystemException e){
			System.err.println("Lol u failed xoxo");
			e.printStackTrace();
		}
		
		soundSystem.quickPlay( false, Sound.class.getResourceAsStream("/sounds/Music.ogg").toString(), false, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 0);
		
		sleep(10);
	}
	
	public static void sleep( long seconds ) { 
	 try { 
		 Thread.sleep( 1000 * seconds ); 
	 }catch( InterruptedException e ){
		  
	  	}
	  }
	
	//This loads the class, to avoid lag spikes!
	public static void touch() {}
	
}
