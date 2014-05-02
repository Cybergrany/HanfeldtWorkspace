package com.hanfeldt.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;

import com.hanfeldt.game.events.PlayerEvents;
import com.hanfeldt.game.npc.Npc;
import com.hanfeldt.game.npc.Spawner;

import de.quippy.javamod.mixer.Mixer;
import de.quippy.javamod.multimedia.MultimediaContainer;
import de.quippy.javamod.multimedia.MultimediaContainerManager;
import de.quippy.javamod.multimedia.mod.ModContainer;
import de.quippy.javamod.system.Helpers;

public class Main implements Runnable {

	public static int sizeX = 256, sizeY = 144;
	public static int tileSize = 16;
	public static int tilesX = sizeX / tileSize, tilesY = sizeY / tileSize;
	public static int fps;
	public static boolean running, isPaused, debug, muted, gameOver;
	public static boolean aDown, dDown, wDown, escDown;
	public static float gravity = 0.16f;
	public static float terminalVelocity = 3;
	
	public static SpriteSheet spriteSheet;

	private int scale = 3;
	private int ticksPs = 60;
	private int frameLimit = 120;
	
	public static ArrayList<Npc> npc;
	
	private GamePanel gamePanel;
	private BufferedImage screenImage;
	private Sprite cloud, sun;
	private Player player;
	private static Level[] levels;
	private static String xmMusicPath = "res/sounds/ARPYSUNDAY.xm";
	private int level = 0;
	private Hud hud;
	private Spawner spawner;

	String name = "Craftmine - an original game about crafting. And mining! Game of the year 2014";

	public static void main(String[] args) {
		Main game = new Main();
		Thread gameThread = new Thread(game);
		game.init();
		gameThread.start();
	}

	public Main() {
		screenImage = new BufferedImage(sizeX, sizeY,
				BufferedImage.TYPE_INT_ARGB);
		gamePanel = new GamePanel(screenImage, sizeX * scale, sizeY * scale);

		gamePanel.addKeyListener(new Listener());
		gamePanel.addMouseListener(new Listener());

		initFrame();
	}

	private void initFrame() {
		BorderLayout layout = new BorderLayout();
		JFrame frame = new JFrame(name);
		frame.setLayout(layout);
		frame.addKeyListener(new Listener());
		frame.addMouseListener(new Listener());
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setVisible(true);
	}

	public void init() {
		gamePanel.requestFocus();
		spriteSheet = new SpriteSheet("res/images/spritesheet.png");
		cloud = new Sprite(spriteSheet, 1, 0, 2, 1);
		Sprite playerSprite = new Sprite(spriteSheet, 2, 1, 1, 2, 3);
		player = new Player(playerSprite, sizeX / 2, sizeY - tileSize * (1 + playerSprite.getHeight()));
		sun = new Sprite(spriteSheet, 0, 1, 2, 2);
		hud = new Hud(player);
		
		npc = new ArrayList<Npc>();
		spawner = new Spawner();
		
		levels = new Level[1];
		levels[0] = new Level("res/images/level1.png", player);
		
		//XM player
		// TODO When muted stop playing song...
		new Thread(new Runnable() {
			public void run() {
				try {
					Helpers.registerAllClasses();
					File music = new File(xmMusicPath);
					Properties props = new Properties();
					props.setProperty(ModContainer.PROPERTY_PLAYER_ISP, "3");
					props.setProperty(ModContainer.PROPERTY_PLAYER_STEREO, "2");
					props.setProperty(ModContainer.PROPERTY_PLAYER_WIDESTEREOMIX, "FALSE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_NOISEREDUCTION, "FALSE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_MEGABASS, "TRUE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_BITSPERSAMPLE, "16");
					props.setProperty(ModContainer.PROPERTY_PLAYER_FREQUENCY, "48000");
					MultimediaContainerManager.configureContainer(props);
					URL modUrl = music.toURI().toURL();
					MultimediaContainer multimediaContainer = MultimediaContainerManager.getMultimediaContainer(modUrl);
					Mixer mixer = multimediaContainer.createNewMixer();
					mixer.startPlayback();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					System.exit(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				run();
			}
		}).start();
		
		// Start "GameLoop"
		running = true;
		debug = false;
		gameOver = false;
	}

	public void run() {
		long lastTick = System.nanoTime();
		long lastFrame = System.nanoTime();
		long nsPerTick = (long) 1000000000 / ticksPs;
		long nsPerFrame = (long) 1000000000 / frameLimit;
		long lastTimer = System.currentTimeMillis();
		int frames = 0, ticks = 0;
		running = true;
		isPaused = false;

		while (running) {
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				fps = frames;
				lastTimer = System.currentTimeMillis();
				ticks = frames = 0;
			}

			if (!isPaused) {//Should game automatically pause on loss of focus? (... &&gamePanel.hasFocus()){...
				if (System.nanoTime() > lastTick + nsPerTick) {
					tick();
					ticks++;
					lastTick += nsPerTick;

					if ((lastTick + nsPerTick) - System.nanoTime() > 3000000) {
						try {
							Thread.sleep(2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}

			if (System.nanoTime() > lastFrame + nsPerFrame) {
				render();
				frames++;

				lastFrame = System.nanoTime();
			}
		}
	}

	public void tick() {
		levels[level].tick();
		hud.tick();
		
		for(int i = 0; i < npc.toArray().length; i++){
			npc.get(i).tickNpc();
		}
	}

	public void render() {
		Graphics2D g = (Graphics2D) screenImage.getGraphics();

		// Render sky
		g.setColor(new Color(0x00, 0xAA, 0xFF));
		g.fillRect(0, 0, sizeX, sizeY);

		for (int i = 0; i < 5; i++) {
			cloud.draw(g, (i * 30) + 20, ((i % 2 == 0) ? 10 : 20));
		}
		
		for(int i = 0; i < npc.toArray().length; i++){
			npc.get(i).draw(g);
		}
		
		sun.draw(g, 190, 10);
		levels[level].render(g);
		hud.draw(g);

		gamePanel.repaint();
		g.dispose();
	}
	
	public static Level[] getLevels() {
		return levels;
	}
	
}
