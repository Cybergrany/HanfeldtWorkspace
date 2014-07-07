package com.hanfeldt.game.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server implements Runnable{
	private static final short port = 21001;
	private ServerSocket serverSock;
	private ArrayList<Socket> clients;
	
	public Server() {
		try {
			serverSock = new ServerSocket(port);
		}catch(Exception e) {
			e.printStackTrace();
		}
		clients = new ArrayList<Socket>();
	}
	
	public void run() {
		while(true) {
			try {
				System.out.println("Waiting for a client...");
				clients.add(serverSock.accept());
				System.out.println("Client connected. Starting send and receive thread...");
				Thread serverThread = new Thread(new ServerThread(clients.get(clients.size() -1)));
				serverThread.start();
				System.out.println("Thread started.");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		new Thread(server).start();
	}
	
}

class ServerThread implements Runnable {
	private Socket client;
	
	public ServerThread(Socket client) {
		this.client = client;
	}
	
	public void run() {
		try {
			PrintWriter writer = new PrintWriter(client.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			int hiscore = -Integer.MAX_VALUE;
			String hsName = null;
			
			BufferedReader hsReader = new BufferedReader(new FileReader("dev_resource/hiscores.txt"));
			String line = null;
			while((line = hsReader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ";");
				String name = st.nextToken();
				int score = Integer.parseInt(st.nextToken());
				st.nextToken();
				if(score >= hiscore) {
					hiscore = score;
					hsName = name;
				}
			}
			hsReader.close();
			writer.println(hsName + ";" + Integer.toString(hiscore));
			writer.flush();
			String packetInput = reader.readLine();
			PrintWriter scoreWriter = new PrintWriter(new BufferedWriter(new FileWriter("dev_resource/hiscores.txt", true)));
			scoreWriter.println(packetInput + ";" + client.getInetAddress());
			scoreWriter.close();
			writer.close();
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
