package com.hanfeldt.game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server implements Runnable{
	private static final short port = 21001;
	private ServerSocket serverSock;
	private Socket client;
	
	public Server() {
		try {
			if(serverSock != null)
			serverSock = new ServerSocket(port);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
				if(serverSock!=null)
				client = serverSock.accept();
			}catch(Exception e) {
				e.printStackTrace();
			}
			new Thread(new Runnable() {
				public void run() {
					try {
						PrintWriter writer = new PrintWriter(client.getOutputStream());
						BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
						int hiscore = 0;
						String hsName = null;
						
						BufferedReader hsReader = new BufferedReader(new FileReader("/hiscores.txt"));
						String line = null;
						while((line = hsReader.readLine()) != null) {
							StringTokenizer st = new StringTokenizer(line, ";");
							String name = st.nextToken();
							int score = Integer.parseInt(st.nextToken());
							st.nextToken();
							if(score > hiscore) {
								hiscore = score;
								hsName = name;
							}
						}
						hsReader.close();
						writer.println(hsName + ":" + Integer.toString(hiscore));
						writer.close();
						String packetInput = reader.readLine();
						PrintWriter scoreWriter = new PrintWriter(new BufferedWriter(new FileWriter("/hiscores.txt")), true);
						scoreWriter.println(packetInput);
						scoreWriter.close();
						reader.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		new Thread(server).start();
	}
	
	
}
