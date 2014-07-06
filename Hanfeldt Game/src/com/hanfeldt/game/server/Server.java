package com.hanfeldt.game.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server implements Runnable{
	private static final short port = 21001;
	private ServerSocket serverSock;
	
	public Server() {
		try {
			serverSock = new ServerSocket(port);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			Thread infoThread = new Thread(new Runnable() {
				public void run() {
					try {
						Socket client = serverSock.accept();
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
							String ip = st.nextToken();
							if(score > hiscore) {
								hiscore = score;
								hsName = name;
							}
						}
						writer.println(hsName + ":" + Integer.toString(hiscore));
						
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	
	
}
