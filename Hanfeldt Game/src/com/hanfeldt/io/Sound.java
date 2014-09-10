package com.hanfeldt.io;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.newdawn.easyogg.OggClip;

import com.hanfeldt.game.Main;
import static com.hanfeldt.io.Debug.*;

/**
 * Sound! Now you can hear yourself mine!
 * To use, simply call Sound.playSound("SoundName");
 * Ensure file SoundName.wav is in res/sounds/
 * 
 * @author David Ofeldt
 * 
 */
public class Sound {
	
	private static OggClip oggClip;
	
	public static synchronized void playSound(final String sound){//For sound, call Sound.playSound("SoundName");
		if(!Main.muted){
			try {
				 URL defaultSound = Sound.class.getResource("/sounds/" + sound);
			     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
			     Clip clip = AudioSystem.getClip();
			     clip.open(audioInputStream);
			     clip.start();
			}catch(Exception e) {
				printErrorDebug("Failed to play sound! Stacktrace:");
				printStackTraceDebug(e);
			}
		}
	}
	
	public static void playOggLoop(){
//		oggClip.loop();
	}
	
	public static void pauseOggLoop(){
		oggClip.pause();
	}
	
	public static void resumeOggLoop(){
		oggClip.resume();
	}
	
	public static void stopCurrentOggLoop(){
		oggClip.stop();
	}
	
	public static boolean oggIsNull() {
		return oggClip == null;
	}
	
	/**
	 * Thanks to this guy: http://www.cokeandcode.com/main/code/
	 * OggClip gives us the following functions, among others;
	 * 
	 * <code>OggClip ogg = new OggClip("startup2.ogg");
ogg.loop();
ogg.setBalance(-1.0f);
ogg.pause();
ogg.resume();
ogg.stop();
ogg.setGain(1.0f);</code>
	 * @param path
	 */
	public static void setOggLoop(String path){
		try {
			oggClip = new OggClip(Sound.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.err.println("Failed to read Ogg file");
			e.printStackTrace();
		}
	}
	
	//This loads the class, to avoid lag spikes!
	public static void touch() {}
	
}
