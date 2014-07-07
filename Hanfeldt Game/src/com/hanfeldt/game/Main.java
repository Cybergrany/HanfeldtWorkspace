package com.hanfeldt.game;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.hanfeldt.game.entity.Bullet;
import com.hanfeldt.game.entity.GoreSpawn;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.state.Dead;
import com.hanfeldt.game.state.State;
import com.hanfeldt.game.state.menus.MainMenuState;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.io.Listener;
import com.hanfeldt.io.ResourceManager;
import com.hanfeldt.io.Sound;

public class Main implements Runnable {
	public static final int WIDTH = 256, HEIGHT = 144;
	public static final int TILE_SIZE = 16; //Only works properly with 16 for the moment
	public static final int SPRITE_SIZE = 16;
	public static final int SCALE = 3;
	public static final int tilesX = WIDTH / TILE_SIZE, tilesY = HEIGHT / TILE_SIZE;
	public static final float GRAVITY = 0.1f;
	public static final float TERMINAL_VELOCITY = 5;
	public static int fps;
	public static boolean running, debug, muted, gameOver, gameStarted, splashShowing;
	public static int mouseX, mouseY;
	public static boolean debugCheats = false;
	public static SpriteSheet spriteSheet;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public ResourceManager resourceManager;
	public static String username = "user";
	
	private int lives = 3;
	private int ticksPs = 60;
	private int frameLimit = 10000;
	private long totalTicks = 0;
	
	public ArrayList<Npc> npc;
	
	private static Sprite character;
	private static Main game;
	private Player player;
	private Level[] levels;
	private JFrame frame;
	private GamePanel gamePanel;
	private BufferedImage screenImage;
	private Listener listener;
	private Hud hud;
	private Camera camera;
	private State state;
	private State playingState;
	private volatile ArrayList<GoreSpawn> gore;
	String name = "Hanfeldt Zombie Shooter";

	public static void main(String[] args) {
		Sound.touch();
		game = new Main();
		Thread gameThread = new Thread(game);
		game.init();
		gameThread.start();
	}

	public Main() {
		screenImage = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		gamePanel = new GamePanel(screenImage, (WIDTH /SPRITE_SIZE *TILE_SIZE)*SCALE, (HEIGHT /SPRITE_SIZE *TILE_SIZE)*SCALE);
		
		listener = new Listener();
		gamePanel.addKeyListener(listener);
		gamePanel.addMouseListener(listener);
		gamePanel.addMouseMotionListener(listener);;

		initFrame();
	}

	private void initFrame() {
		BorderLayout layout = new BorderLayout();
		frame = new JFrame(name);
		frame.setLayout(layout);
		frame.addKeyListener(new Listener());
		frame.addMouseListener(new Listener());
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		try{
			Toolkit kit = Toolkit.getDefaultToolkit();
			Image img = kit.createImage(Main.class.getResource("/images/icon.png"));
			frame.setIconImage(img);
		}catch(Exception e){
			System.err.println("Error in setting icon");
		}

		frame.setVisible(true);
	}

	public void init() {
		spriteSheet = new SpriteSheet("/images/spritesheet.png");
		Sprite playerSprite = new Sprite(spriteSheet, 2, 1, 1, 2, 3);
		player = new Player(playerSprite, WIDTH / 2, HEIGHT - TILE_SIZE * (1 + playerSprite.getTileHeight()), listener, this);
		
		state = new MainMenuState(this);
		gamePanel.requestFocus();
		resourceManager = new ResourceManager();
		character = new Sprite(Main.spriteSheet, 1, 3, 1, 1);
		hud = new Hud(player, character);
		
		npc = new ArrayList<Npc>();
		
		// Start "GameLoop"
		running = true;
		debug = false;
		gameOver = false;
	}

	public void run() {
		long nsPerTick = (long) 1000000000 / ticksPs;
		long nsPerFrame = (long) 1000000000 / frameLimit;
		long lastTick = System.nanoTime() - nsPerTick;
		long lastFrame = System.nanoTime();
		long lastTimer = System.currentTimeMillis();
		int frames = 0, ticks = 0;
		running = true;

		while (running) {
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				if(debug) {
					System.out.printf("%d ticks, %d fps\n", ticks, frames);
				}
				fps = frames;
				lastTimer = System.currentTimeMillis();
				ticks = frames = 0;
			}

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

			if (System.nanoTime() > lastFrame + nsPerFrame) {
				render();
				frames++;

				lastFrame = lastFrame + nsPerFrame;
			}
		}
	}

	public void tick() {
		hud.setHasFocus(gamePanel.hasFocus());
		if(gamePanel.hasFocus()) {
			state.tick();
			listener.mouseDownLastTick = listener.mouseDown;
			listener.shopButtonDownLastTick = listener.shopButtonDown;
			listener.spaceDownLastTick = listener.spaceDown;
		}
	}

	public void render() {
		state.draw(screenImage.getGraphics());
		gamePanel.render();
	}
	
	//A very basic tick-based timer
	public static boolean timer(int ms){
		if(game.getTotalTicks() % ms == 0){
			return true;
		}
		return false;
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
		if(!debugCheats) {
			lives--;
		}
		if(lives <= 0) {
			gameOver = true;
		}
		state = new Dead(this);
	}
	
	public void respawnPlayer() {
		player.alive = true;
		player.setX(WIDTH / 2);
		player.setY(HEIGHT - TILE_SIZE - player.getSizeY());
		player.setHealth(100);
	}
	
	public static SpriteSheet getSpritesheet() {
		return spriteSheet;
	}
	
	public void reload() {
		if(player.getWeaponEquipped() instanceof AmmoWeapon) {
			AmmoWeapon wep = (AmmoWeapon) player.getWeaponEquipped();
			wep.reload();
		}
	}
	
	public Hud getHud() {
		return hud;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public Listener getListener() {
		return listener;
	}
	
	public GamePanel getPanel() {
		return gamePanel;
	}
	
	public ArrayList<Npc> getNpc() {
		return npc;
	}
	
	public Sprite getCharacter() {
		return character;
	}
	
	public void setState(State s) {
		state = s;
	}
	
	public State getState() {
		return state;
	}
	
	public void returnToPlaying() {
		state = playingState;
	}
	
	public void addGore(int x, int y) {
		gore.add(new GoreSpawn(x, y));
	}
	
	public ArrayList<GoreSpawn> getGore() {
		return gore;
	}
	
	public void createGoreList() {
		gore = new ArrayList<GoreSpawn>();
	}
	
	public void removeGore(GoreSpawn goreSpawn) {
		for(int i=0; i<gore.size(); i++) {
			if(goreSpawn.equals(gore.get(i))) {
				gore.remove(i);
			}
		}
	}
	
	public void setPlayingState(State state) {
		playingState = state;
	}
	
	public void setLevels(Level[] l) {
		levels = l;
	}
	
	public Level[] getLevels() {
		return levels;
	}
	
	public Camera getCamera() {
		if(camera == null) {
			camera = new Camera(0, 0, getPlayer());
		}
		return camera;
	}
	
}
