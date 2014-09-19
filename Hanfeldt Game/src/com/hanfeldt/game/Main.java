package com.hanfeldt.game;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JFrame;

import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.display.GamePanel;
import com.hanfeldt.game.display.Hud;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.particles.GoreSpawn;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.event.command.CommandEvent;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.level.LevelLoader;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.state.Dead;
import com.hanfeldt.game.state.Playing;
import com.hanfeldt.game.state.State;
import com.hanfeldt.game.state.menus.MainMenuState;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.io.Listener;
import com.hanfeldt.io.Sound;

public class Main implements Runnable {
	public static final int WIDTH = 256, HEIGHT = 144;
	public static final int TILE_SIZE = 16; //Only works properly with 16 for the moment
	public static final int SPRITE_SIZE = 16;
	public static final  int SCALE = 3;
	public static final int tilesX = WIDTH / TILE_SIZE, tilesY = HEIGHT / TILE_SIZE;
	public static final float GRAVITY = 0.1f;
	public static final float TERMINAL_VELOCITY = 5;
	public static int fps;
	public static boolean running, debug, muted, gameOver, gameStarted, splashShowing;
	public static int mouseX, mouseY;
	public static boolean debugCheats = true;
	public static SpriteSheet spriteSheet;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public PropertiesLoader resourceManager;
	public static String username = "user";
	
	private int lives = 3;
	private int ticksPs = 60;
	private int frameLimit = 90;
	private long totalTicks = 0;
	
	public ArrayList<Npc> npc;
	public ArrayList<EntityItem> items;
	
	private static Main game;
	private Player player;
	private Level levels;
	private JFrame frame;
	private GamePanel gamePanel;
	private BufferedImage screenImage;
	private Listener listener;
	private Hud hud;
	private Camera camera;
	private State state;
	private State playingState;
	private volatile ArrayList<GoreSpawn> gore;
	static final String name = "Hanfeldt Zombie Shooter";

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
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
		LevelLoader.initLevel();
		spriteSheet = new SpriteSheet("/images/spritesheet.png");
		SpriteSheet playerSheet = SpriteSheet.getSheet(SpriteSheet.player);
//		Sprite playerSprite = new Sprite(spriteSheet, 2, 1, 1, 2, 3);

		Sprite playerSprite = new Sprite(playerSheet, 0, 0, 1, 2, 3);
//		player = new Player(playerSprite, WIDTH / 2, HEIGHT - TILE_SIZE * (1 + playerSprite.getTileHeight()), listener, this);
		player = new Player(playerSprite, 0, 0, listener, this);
		
		state = new MainMenuState(this);
		gamePanel.requestFocus();
		resourceManager = new PropertiesLoader();
		hud = new Hud(player);
		
		npc = new ArrayList<Npc>();
		items = new ArrayList<EntityItem>();
		
		if(debugCheats)
		new CommandEvent();
		
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
			}

			if (System.nanoTime() > lastFrame + nsPerFrame && !(System.nanoTime() > lastTick + nsPerTick)) {
				render();
				frames++;

				lastFrame = lastFrame + nsPerFrame;
			}
			
			if ((lastTick + nsPerTick) - System.nanoTime() > 5 &&
				(lastFrame + nsPerFrame) - System.nanoTime() > 5) {
				try {
					Thread.sleep(0, 3);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		player.setX(getLevels().getPlayerSpawnPoint().x);
		player.setY(getLevels().getPlayerSpawnPoint().y);
		player.setHealth(100);
	}
	
	public static SpriteSheet getSpritesheet() {
		return spriteSheet;
	}
	
	public void reload() {
		if(state != null && state instanceof Playing) {
			if(player.getWeaponEquipped() != null && player.getWeaponEquipped() instanceof AmmoWeapon) {
				AmmoWeapon wep = (AmmoWeapon) player.getWeaponEquipped();
				wep.reload();
			}
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
	
	public ArrayList<EntityItem> getItems(){
		return items;
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
	
	public void setPlayingState(State state) {
		playingState = state;
	}
	
	public void setLevels(Level l) {
		levels = l;
	}
	
	public Level getLevels() {
		return levels;
	}
	
	public Camera getCamera() {
		if(camera == null) {
			camera = new Camera(0, 0, getPlayer());
		}
		return camera;
	}
}
