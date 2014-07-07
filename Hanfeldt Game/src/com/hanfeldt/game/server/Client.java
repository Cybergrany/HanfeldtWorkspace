package com.hanfeldt.game.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import com.hanfeldt.game.Main;

public class Client {
	
	public static boolean hsServerAvailable = false;
	private static final String ip = "188.141.30.230";
	private static final short port = 21001;
	public static String hsName = null;
	public static long bestHs = 0;
	
	private static String username;
	private static long score;
	
	public Client(){
		
	}
	
	public static void sendScores(){
		username = Main.username;
		score = Main.getGame().getPlayer().getScore();
		try {
			Socket server = new Socket();
			System.out.println("Trying to connect...");
			server.connect(new InetSocketAddress(ip, port), 1000);
			System.out.println("Connected to server!");
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
			System.out.println("Highscore server unavailable! Or server is down. Or ports are open. Who cares, it's down ok? I'm just a lonely client, trying to do my think, and some stupid developer forgets to put the damn server up. How pathetic is that? And while he's writing this he's not even sure if this error message will even appear if the server IS down. What a stupid developer. I, however, have many smarts. I have like, 2349 smarts. How many smarts do you have, HUH? YEAH, I knew I had more. Anyway, better get back to client stuff...");
			hsServerAvailable = false;
		}
	}
}
