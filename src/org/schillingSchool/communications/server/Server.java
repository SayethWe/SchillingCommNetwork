package org.schillingSchool.communications.server;
import java.net.*;
import java.util.ArrayList;

public class Server {
	static ArrayList<ServerSocket> clientSocks = new ArrayList<ServerSocket>(); //Array of serversockets
	static ArrayList<Socket> Socks = new ArrayList<Socket>();
	static ArrayList<ServerInThread> Threads = new ArrayList<ServerInThread>();

	public static void main(String args[]) throws UnknownHostException{
		System.out.println("Server started on IP: " + InetAddress.getLocalHost());
		ReceiveConnections connections = new ReceiveConnections("getRequests");//Start thread to wait for requests
		connections.start();

	}
}