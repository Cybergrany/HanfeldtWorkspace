package com.hanfeldt.game.state;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import com.hanfeldt.game.Main;
import com.hanfeldt.game.entity.Firework;

public class GameWon extends State {
	private ArrayList<Firework> fireworks;
	private Random rand;
	private long nextFireworkTick = 0;
	private boolean hsServerAvailable = false;
	private static final String ip = "188.141.30.230";
	private static final short port = 21001;
	private String hsName = null;
	private long bestHs = 0;
	
	public GameWon(Main main) {
		super(main);
		fireworks = new ArrayList<Firework>();
		rand = new Random();
		nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(30) +30;
		String username = main.username;
		long score = main.getPlayer().getScore();
		try {
			Socket server = new Socket();
			server.connect(new InetSocketAddress(ip, port), 1000);
			hsServerAvailable = true;
			BufferedReader hsReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter sWriter = new PrintWriter(server.getOutputStream());
			
			StringTokenizer st = new StringTokenizer(hsReader.readLine(), ";");
			hsName = st.nextToken();
			bestHs = Long.parseLong(st.nextToken());
			
			sWriter.println(username + ";" + score);
			sWriter.flush();
			
			server.close();
		}catch(Exception e) {
			hsServerAvailable = false;
		}
	}
	
	public void tick() {
		if(Main.getGame().getTotalTicks() >= nextFireworkTick) {
			switch(rand.nextInt(3)) {
			case 0:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.RED));
				break;
			case 1:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.BLUE));
				break;
			case 2:
				fireworks.add(new Firework(rand.nextInt(Main.WIDTH), Main.HEIGHT, Color.ORANGE));
				break;
			}
			nextFireworkTick = Main.getGame().getTotalTicks() + rand.nextInt(16) +16;
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).tick();
		}
	}
		
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString("Congratulations!", Main.WIDTH /2 - (("Congratulation!".length() /2) *7), Main.HEIGHT /4);
		g.drawString("You win!", Main.WIDTH /2 - (("You win!".length() /2) *7), Main.HEIGHT /2);
		g.drawString("You finished with a score of:", Main.WIDTH /2 - (("You finished with a score of:".length() /2) *5), Main.HEIGHT /2 +30);
		String scoreString = Long.toString(main.getPlayer().getScore());
		g.drawString(scoreString, Main.WIDTH /2 - (scoreString.length() /2) *5, Main.HEIGHT /2 +40);
		if(!hsServerAvailable) {
			String string = "Hiscore server unavailable/waiting to transfer scores";
			g.drawString(string, Main.WIDTH /2 - (string.length() /2) *5, Main.HEIGHT /2 +50);
		}else{
			String string = "Best score: " + hsName + " with a score of " + bestHs;
			g.drawString(string, Main.WIDTH /2 - (string.length() /2) *5, Main.HEIGHT /2 +50);
		}
		for(int i=0; i<fireworks.size(); i++) {
			fireworks.get(i).render(g);
		}
	}
	
	public void removeFirework(Firework f) {
		for(int i=0; i<fireworks.size(); i++) {
			if(fireworks.get(i).equals(f)) {
				fireworks.remove(i);
			}
		}
	}
	
}
