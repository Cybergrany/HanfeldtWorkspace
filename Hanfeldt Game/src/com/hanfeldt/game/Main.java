package com.hanfeldt.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;

import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.weapon.TriggerWeapon;
import com.hanfeldt.io.Listener;

import de.quippy.javamod.mixer.Mixer;
import de.quippy.javamod.multimedia.MultimediaContainer;
import de.quippy.javamod.multimedia.MultimediaContainerManager;
import de.quippy.javamod.multimedia.mod.ModContainer;
import de.quippy.javamod.system.Helpers;

public class Main implements Runnable {

	public static int sizeX = 256, sizeY = 144;
	public static int tileSize = 16; //Only works with 16 for the moment
	public static int scale = 3;
	public static int tilesX = sizeX / tileSize, tilesY = sizeY / tileSize;
	public static int fps;
	public static boolean running, isPaused, debug, muted, gameOver;
	public static boolean aDown, dDown, wDown, escDown, mouseDown;
	private static boolean mouseDownLastTick = false;
	public static int mouseX, mouseY;
	public static float gravity = 0.1f;
	public static float terminalVelocity = 5;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static SpriteSheet spriteSheet;
	
	private int lives = 3;
	private int ticksPs = 60;
	private int frameLimit = 120;
	private long totalTicks = 0;
	
	public static ArrayList<Npc> npc;
	
	private static int deadScreenTicks = 240;
	private static long tickDied = 0;
	private static Sprite character;
	private static Main game;
	private GamePanel gamePanel;
	private BufferedImage screenImage;
	private Sprite cloud, sun;
	private Player player;
	private static Level[] levels;
	private static String xmMusicPath = "/sounds/ARPYSUNDAY.xm";
	private int level = 0;
	private Listener listener;
	private Hud hud;
	String name = "Craftmine - an original game about crafting. And mining! Game of the year 2014";

	public static void main(String[] args) {
		game = new Main();
		Thread gameThread = new Thread(game);
		game.init();
		gameThread.start();
	}

	public Main() {
		screenImage = new BufferedImage(sizeX, sizeY,
				BufferedImage.TYPE_INT_ARGB);
		gamePanel = new GamePanel(screenImage, sizeX * scale, sizeY * scale);
		
		listener = new Listener();
		gamePanel.addKeyListener(listener);
		gamePanel.addMouseListener(listener);
		gamePanel.addMouseMotionListener(listener);;

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
		spriteSheet = new SpriteSheet("/images/spritesheet.png");
		character = new Sprite(Main.spriteSheet, 1, 3, 1, 1);
		cloud = new Sprite(spriteSheet, 1, 0, 2, 1);
		Sprite playerSprite = new Sprite(spriteSheet, 2, 1, 1, 2, 3);
		player = new Player(playerSprite, sizeX / 2, sizeY - tileSize * (1 + playerSprite.getHeight()));
		sun = new Sprite(spriteSheet, 0, 1, 2, 2);
		hud = new Hud(player, character);
		
		npc = new ArrayList<Npc>();
		
		levels = new Level[1];
		levels[0] = new Level("/images/level1.png", player);
//		levels[0] = new Level("/images/level2.png", player);
		
		//XM player
		// TODO When muted stop playing song...
		new Thread(new Runnable() {
			public void run() {
				try {
					Helpers.registerAllClasses();
					URL music = Main.class.getResource(xmMusicPath);
					Properties props = new Properties();
					props.setProperty(ModContainer.PROPERTY_PLAYER_ISP, "3");
					props.setProperty(ModContainer.PROPERTY_PLAYER_STEREO, "2");
					props.setProperty(ModContainer.PROPERTY_PLAYER_WIDESTEREOMIX, "FALSE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_NOISEREDUCTION, "FALSE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_MEGABASS, "TRUE");
					props.setProperty(ModContainer.PROPERTY_PLAYER_BITSPERSAMPLE, "16");
					props.setProperty(ModContainer.PROPERTY_PLAYER_FREQUENCY, "48000");
					MultimediaContainerManager.configureContainer(props);
					URL modUrl = music;
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
					totalTicks++;
					if(totalTicks >= Long.MAX_VALUE) {
						totalTicks = 0;
					}
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
		if(player.alive) {
			levels[level].tick();
			hud.tick();
			for(int i=0; i<bullets.size(); i++) {
				bullets.get(i).tick();
			}
			player.getWeaponEquipped().tick();
			if(!mouseDownLastTick && mouseDown && player.getWeaponEquipped() instanceof TriggerWeapon) {
				((TriggerWeapon) player.getWeaponEquipped()).tryTrigger();
			}
		}else{
			if(totalTicks >= tickDied + deadScreenTicks && lives > 0) {
				respawnPlayer();
			}
		}
		// Moved NPC ticking to Level, I think it's more appropriate
		mouseDownLastTick = mouseDown;
	}

	public void render() {
		Graphics2D g = (Graphics2D) screenImage.getGraphics();

		if(player.alive || lives <= 0) {
			// Render sky
			g.setColor(new Color(0x00, 0xAA, 0xFF));
			g.fillRect(0, 0, sizeX, sizeY);

			for (int i = 0; i < 5; i++) {
				cloud.draw(g, (i * 30) + 20, ((i % 2 == 0) ? 10 : 20));
			}
			
			//Moved NPC draw to levels (Where player is also rendered)
			
			sun.draw(g, 190, 10);
			for(int i = 0; i < npc.toArray().length; i++){
				npc.get(i).draw(g);
			}
			levels[level].render(g);
			hud.draw(g);
			for(int i=0; i<bullets.size(); i++) {
				bullets.get(i).draw(g);
			}
		}else{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, sizeX, sizeY);
			character.draw(g, sizeX /2 - (tileSize *2), (sizeY /2) - 60);
			Font font = new Font("Arial", Font.PLAIN, 10);
			g.setFont(font);
			g.setColor(new Color(255, 255, 255));
			g.drawString("x " + Integer.toString(lives), (sizeX /2) - 5, (sizeY /2) - 49);
		}
		
		gamePanel.repaint();
		g.dispose();
	}
	
	public static Level[] getLevels() {
		return levels;
	}
	
	public static Main getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public long getTotalTicks() {
		return totalTicks;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void playerDied() {
		lives--;
		player.alive = false;
		if(lives <= 0) {
			gameOver = true;
		}
		tickDied = totalTicks;
	}
	
	public void respawnPlayer() {
		player.alive = true;
		player.setX(sizeX / 2);
		player.setY(sizeY - tileSize - player.getSizeY());
		player.setHealth(100);
	}
	
	public static SpriteSheet getSpritesheet() {
		return spriteSheet;
	}
	
}
