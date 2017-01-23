package org.schillingSchool.communications.server;
import java.net.*;
import java.util.ArrayList;

import org.schillingSchool.communications.userInterface.ServerInterface;

public class Server {
	static ArrayList<ServerSocket> clientSocks = new ArrayList<ServerSocket>(); //Array of serversockets
	static ArrayList<Socket> Socks = new ArrayList<Socket>();
	static ArrayList<ServerInThread> Threads = new ArrayList<ServerInThread>();

	public Server(ServerInterface aGUI) throws UnknownHostException{
		aGUI.displayMessage("Server started on IP: " + InetAddress.getLocalHost());
		ReceiveConnections connections = new ReceiveConnections("getRequests", aGUI);//Start thread to wait for requests
		connections.start();
	}
}