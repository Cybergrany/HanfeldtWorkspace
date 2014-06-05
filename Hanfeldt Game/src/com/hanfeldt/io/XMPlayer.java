package com.hanfeldt.io;

import de.quippy.javamod.mixer.Mixer;

public class XMPlayer implements Runnable {
	private Mixer mixer;
	
	public XMPlayer(Mixer mixer) {
		this.mixer = mixer;
	}
	public void run() {
		mixer.startPlayback();
		run();
	}
	
}
