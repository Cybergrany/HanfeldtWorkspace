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
	private static final String ip = "127.0.0.1";
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
			System.out.println("Unable to connect to server!");
			hsServerAvailable = false;
		}
	}
}
