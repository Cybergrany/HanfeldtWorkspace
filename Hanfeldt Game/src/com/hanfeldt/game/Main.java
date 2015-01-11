package com.hanfeldt.game;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.hanfeldt.game.display.Camera;
import com.hanfeldt.game.display.GamePanel;
import com.hanfeldt.game.display.Hud;
import com.hanfeldt.game.display.Sprite;
import com.hanfeldt.game.display.SpriteSheet;
import com.hanfeldt.game.entity.Entity;
import com.hanfeldt.game.entity.EntityItem;
import com.hanfeldt.game.entity.EntityManager;
import com.hanfeldt.game.entity.Player;
import com.hanfeldt.game.entity.npc.Npc;
import com.hanfeldt.game.entity.npc.NpcList;
import com.hanfeldt.game.entity.particles.Gore;
import com.hanfeldt.game.entity.particles.GoreSpawn;
import com.hanfeldt.game.entity.projectile.Bullet;
import com.hanfeldt.game.event.command.CommandEvent;
import com.hanfeldt.game.io.Debug;
import com.hanfeldt.game.io.Listener;
import com.hanfeldt.game.io.Sound;
import com.hanfeldt.game.level.Layer;
import com.hanfeldt.game.level.Level;
import com.hanfeldt.game.level.LevelLoader;
import com.hanfeldt.game.level.LevelStory;
import com.hanfeldt.game.properties.PropertiesLoader;
import com.hanfeldt.game.state.Dead;
import com.hanfeldt.game.state.GameWon;
import com.hanfeldt.game.state.Playing;
import com.hanfeldt.game.state.State;
import com.hanfeldt.game.state.menus.MainMenuState;
import com.hanfeldt.game.weapon.AmmoWeapon;
import com.hanfeldt.game.weapon.Weapon;

/**
 * Main contains all the main components, methods and constants required for the game to work.
 * 
 * @author David Ofeldt
 * @author Ronan Hanley
 * @since 1.0
 *
 */
public class Main implements Runnable {
	public static final int WIDTH = 256, HEIGHT = 144;
	public static final int TILE_SIZE = 16; //Only works properly with 16 for the moment
	public static final int SPRITE_SIZE = 16;
	public static final int SCALE = 3;
	public static final int tilesX = WIDTH / TILE_SIZE, tilesY = HEIGHT / TILE_SIZE;
	public static final float GRAVITY = 0.1f;
	public static final float TERMINAL_VELOCITY = 15;//Testing 15
	public static int fps;
	public static boolean running, debug, muted, gameOver, gameStarted, splashShowing, printFPS;
	public static int mouseX, mouseY;
	public static boolean debugCheats = true;//Preferably use command 'cheats' instead of setting here
	public static SpriteSheet spriteSheet;
	
//	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public PropertiesLoader resourceManager;
	public EntityManager entityManager;
	public NpcList npcPreCache;
	public static String username = "user";
	
	private int lives = 3;
	private int ticksPs = 60;
	private int frameLimit = 90;
	private long totalTicks = 0;
	
//	public ArrayList<Npc> npc;
//	public ArrayList<EntityItem> items;
	public ArrayList<String[]> blocks;
	
	private static Main game;
	private Robot robot;
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
		System.setProperty("java.net.preferIPv4Stack", "true");//Part of client-server relationship
		Sound.touch();//Touch on sound class to prevent clipping and lag
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
		gamePanel.addMouseMotionListener(listener);

		initFrame();
	}
	
	/**
	 * Initialize the JFrame on which the game is drawn.
	 */
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
	
	/**
	 * Initialize the core components of the game, and start the game thread.
	 */
	public void init() {
		LevelLoader.initLevel();
		spriteSheet = new SpriteSheet("/images/spritesheet.png");
		SpriteSheet playerSheet = SpriteSheet.getSheet(SpriteSheet.player);

		Sprite playerSprite = new Sprite(playerSheet, 0, 0, 1, 2, 3);
		player = new Player(playerSprite, 0, 0, listener, this);
		
		state = new MainMenuState(this);
		gamePanel.requestFocus();
		resourceManager = new PropertiesLoader();
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			System.err.println("Error creating Robot. Stacktrace in debug.");
			Debug.printErrorDebug(e.getStackTrace());
		}
		
		hud = new Hud(player);
		
		npcPreCache = new NpcList();
		entityManager = new EntityManager(this);
		
		new CommandEvent();
		printFPS = false;
		// Start "GameLoop"
		running = true;
		debug = false;
		gameOver = false;
		
		PropertiesLoader.loadBlockIDs();
	}
	
	/**
	 * Called while the main thread is running
	 */
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
				if(printFPS) {
					//Print the current fps and tps if printFPS is true
					System.out.printf("%d ticks, %d fps\n", ticks, frames);
				}
				fps = frames;
				lastTimer = System.currentTimeMillis();
				ticks = frames = 0;
			}

			if (System.nanoTime() > lastTick + nsPerTick) {
				tick();//Tick the game
				ticks++;
				totalTicks++;
				if(totalTicks >= Long.MAX_VALUE) {
					totalTicks = 0;
				}
				lastTick += nsPerTick;
			}

			if (System.nanoTime() > lastFrame + nsPerFrame && !(System.nanoTime() > lastTick + nsPerTick)) {
				render();//Render the game
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
	
	/**
	 * Ticks the game
	 */
	public void tick() {
		hud.setHasFocus(gamePanel.hasFocus());
		if(gamePanel.hasFocus()) {
			state.tick();
			listener.mouseDownLastTick = listener.mouseDown;
			listener.rightMouseDown = listener.rightMouseDown;
			listener.shopButtonDownLastTick = listener.shopButtonDown;
			listener.spaceDownLastTick = listener.spaceDown;
		}
	}
	
	/**
	 * Draws all game objects on screen
	 */
	public void render() {
		state.draw(screenImage.getGraphics());
		gamePanel.render();
	}
	
	/**
	 * If called in a tick method or similar, will return true when specified time has elapsed.
	 * @param ms time before true is returned.
	 * @return<code> true</code> if specifed time elapses.
	 */
	public static boolean timer(int ms){
		if(game.getTotalTicks() % ms == 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Provides link to important objects, constants and variables.
	 * 
	 * This is preferred to creating multiple instances of one object.
	 * @return <code>game</code> an instance of the {@link Main} class
	 * @see Main
	 */
	public static Main getGame() {
		return game;
	}
	
	/**
	 * Allows access to the {@link Player} object and its attached variables
	 * @return <code>player</code> the player object
	 * @see Player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * An {@link ArrayList} of the current block ID's,
	 * which the  game uses to match the color code in the map image with the appropriate block.
	 * 
	 * @return an ArrayList of block ID's, as parsed in by <code>loadBlockID</code> in {@link PropertiesLoader}
	 * @throws NullPointerException if blocks are not loaded or not found
	 * @see PropertiesLoader
	 * @see LevelStory
	 */
	public ArrayList<String[]>getBlockIDs()throws NullPointerException{
		if(blocks != null){
			return blocks;
		}else{
			System.err.println("Fatal error! Could not load block ID's!");
			throw new NullPointerException();
		}
	}
	
	/**
	 * Elapsed ticks
	 * @return <code>totalTicks</code> - amount of ticks elapsed.
	 */
	public long getTotalTicks() {
		return totalTicks;
	}
	
	/**
	 * Amount of Lives player has
	 * @return lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Performed on player death.
	 * Checks for remaining lives, and either displays life lost screen and respawns player, or sends player to death screen.
	 */
	public void playerDied() {
		if(!debugCheats) {
			lives--;
		}
		if(lives <= 0) {
			gameOver = true;
		}
		state = new Dead(this);
	}
	
	/**
	 * Respawns player to the spawn point as read from the level image file.
	 * @see LevelStory
	 */
	public void respawnPlayer() {
		player.alive = true;
		player.setX(getLevels().getPlayerSpawnPoint().x);
		player.setY(getLevels().getPlayerSpawnPoint().y);
		player.setHealth(100);
	}
	
	/**
	 * Returns the current {@link SpriteSheet} in use.
	 * No longer in use, as multiple SpriteSheets are now in use.
	 * @return <code>spriteSheet</code>
	 * @deprecated <b>Single SpriteSheet no longer in use</b> Use the appropriate SpriteSheet using <code>SpriteSheet.getSheet(SpriteSheet.sheetType)</code>
	 * @see SpriteSheet
	 */
	public static SpriteSheet getSpritesheet() {
		return spriteSheet;
	}
	
	/**
	 * Reload weapon equipped on Player.
	 * @see AmmoWeapon
	 * @see Player
	 * @see Weapon
	 */
	// TODO: Not needed in main, move to {@link AmmoWeapon} or similiar.
	public void reload() {
		if(state != null && state instanceof Playing) {
			if(player.getWeaponEquipped() != null && player.getWeaponEquipped() instanceof AmmoWeapon) {
				AmmoWeapon wep = (AmmoWeapon) player.getWeaponEquipped();
				wep.reload();
			}
		}
	}
	
	/**
	 * Screen overlay showing data of interest to the player.
	 * @return <code>hud</code> the overlay.
	 * @see Hud
	 */
	public Hud getHud() {
		return hud;
	}
	
	/**
	 * The listener that contains variables that indicate key events and similar.
	 * @return <code>listener</code> the mouse and key listener of the game
	 * @see Listener
	 */
	public Listener getListener() {
		return listener;
	}
	
	/**
	 * The panel on which the game is drawn
	 * @return a {@link GamePanel} object.
	 * @see GamePanel
	 */
	public GamePanel getPanel() {
		return gamePanel;
	}
	
	/**
	 * An {@link ArrayList} of all ingame NPC's
	 * @return <code>npc</code> an arraylist of npc's currently populating the level
	 * @see Npc
	 * 
	 * @deprecated - use getEntityManager.getNpcs
	 */
	public ArrayList<Npc> getNpc() {
		return entityManager.getNpcs();
	}
	
	/**
	 * An {@link ArrayList} of all ingame items
	 * @return <code>items</code> all items on current level
	 * @see EntityItem
	 * 
	 * @deprecated - use getEntityManager.getItems
	 */
	public ArrayList<EntityItem> getItems(){
		return entityManager.getItems();
	}
	
	/**
	 * {@link Bullet} entities currently ingame
	 * TODO Can't iterate over much with this in use. Optimise
	 * @return <code>bullets</code> an ArrayList of ingame bullets.
	 * @see Bullet
	 * 
	 * @deprecated - use getEntityManager.getBullets
	 */
	public ArrayList<Bullet> getBullets() {
		return entityManager.getBullets();
	}
	
	/**
	 * An {@link ArrayList} of all ingame {@link Entity} objects.
	 * Use entityManager.getEntities if you don't need the player entity
	 * @return
	 * @see Entity
	 */
	public ArrayList<Entity> getAllEntites() {
		ArrayList<Entity> e = new ArrayList<>();
		e.addAll(entityManager.getEntities());
		e.add(getPlayer());
		
		return e;
	}
	
	/**
	 * Set the current game {@link State}.
	 * @param s a game State of any kind, from the com.hanfeldt.game.state package.
	 * @see State
	 * @see Playing
	 */
	public void setState(State s) {
		state = s;
	}
	
	/**
	 * Current game {@link State}
	 * @return the State the game finds itself in currently
	 * @see State
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * Returns the game from any other state to a playing state.
	 * Eg, taking the player from a menu to the game.
	 * @see Playing
	 */
	public void returnToPlaying() {
		state = playingState;
	}
	
	/**
	 * Add gore to the level.
	 * @param x location on x-axis, with the top-left of level as the origin
	 * @param y location on y-axis, with the top-left of level as the origin
	 * @see Gore
	 * @see GoreSpawn
	 */
	public void addGore(int x, int y, int layer) {
		gore.add(new GoreSpawn(x, y, layer));
	}
	
	/**
	 * Add gore to the level, taking into account "spurt" direction
	 * @param x location on x-axis, with the top-left of level as the origin
	 * @param y location on y-axis, with the top-left of level as the origin
	 * @see Gore
	 * @see GoreSpawn
	 */
	public void addGore(int x, int y, boolean di, int layer) {
		gore.add(new GoreSpawn(x, y, di, layer));
	}
	
	/**
	 * ArrayList of all gore currently in game
	 * @return an arrayList of ingame gore.
	 * @see Gore
	 */
	public ArrayList<GoreSpawn> getGore() {
		return gore;
	}
	
	/**
	 * Create a new {@link ArrayList} to hold gore entities
	 * @see Gore
	 */
	public void createGoreList() {
		gore = new ArrayList<GoreSpawn>();
	}
	
	/**
	 * Set the current playing state to a certain state of playing type
	 * @param state a {@link State} of {@link Playing} type
	 * @see State
	 * @see Playing
	 * @see Dead
	 * @see GameWon
	 */
	public void setPlayingState(State state) {
		playingState = state;
	}
	
	/**
	 * Set {@link Level} which is currently being played.
	 * @param l A new {@link Level} object
	 * @see Level
	 * @see LevelStory
	 */
	//TODO: Rename to account for not being an array any more(Singular)
	public void setLevels(Level l) {
		levels = l;
	}
	
	/**
	 * Level which is currently being played on.
	 * @return The current level
	 * @see Level
	 */
	//TODO: Rename to account for not being an array any more(Singular)
	public Level getLevels() {
		return levels;
	}
	
	public ArrayList<Layer> getLayers(){
		return levels.layers;
	}
	
	/**
	 * Current {@link Camera} object, which is used to render Entities and other images within the game
	 * @return the game's camera
	 * @see Camera
	 */
	public Camera getCamera() {
		if(camera == null) {
			camera = new Camera(0, 0, getPlayer());
		}
		return camera;
	}
	
	/**
	 * Returns the {@link EntityManager}, which contains {@link ArrayList}s of all in-
	 * game {@link Entity} objects
	 * @return  the current {@link EntityManager} object
	 */
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
	/**
	 * Returns the game's {@link Robot} object, which can be used for setting the cursor position
	 * @return robot - the game's robot object.
	 * @see Robot
	 * TODO Not currently in use
	 */
	public Robot getRobot(){
		return robot;
	}
	
}
