package org.schillingSchool.communications.server;
import java.net.*;
import java.util.ArrayList;

import org.schillingSchool.communications.userInterface.ServerInterface;
import org.schillingSchool.communications.utils.Utils;

public class Server {
	static ArrayList<ServerSocket> clientSocks = new ArrayList<ServerSocket>(); //Array of serversockets
	static ArrayList<Socket> socks = new ArrayList<Socket>();
	static ArrayList<ServerInThread> threads = new ArrayList<ServerInThread>();
	ReceiveConnections connections;

	public Server(ServerInterface aGUI) throws UnknownHostException{
		Utils.getLogger().info("Server Started");
		aGUI.displayMessage("Server started on IP: " + InetAddress.getLocalHost());
		connections = new ReceiveConnections("getRequests", aGUI);//Start thread to wait for requests
		connections.start();
	}
	
	synchronized public void end() { //shutdown command
		connections.end();
		Utils.getLogger().info("server " + this + " shutting down.");
	}
}