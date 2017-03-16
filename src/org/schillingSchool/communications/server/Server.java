package org.schillingSchool.communications.server;
import java.net.*;
import java.util.ArrayList;

import org.schillingSchool.communications.userInterface.ServerInterface;
import org.schillingSchool.communications.utils.Utils;
/**
 * 
 * @author DMWCincy
 * This class contains the code to start and end the server
 * Written pi day 2017 (14/3/17)
 */
public class Server {
	static ArrayList<ServerSocket> clientSocks = new ArrayList<ServerSocket>(); //Array of serversockets
	static ArrayList<Socket> socks = new ArrayList<Socket>();
	static ArrayList<ServerInThread> threads = new ArrayList<ServerInThread>();
	ReceiveConnections connections;
	/**
	 * 
	 * @param aGUI
	 * @throws UnknownHostException
	 * Prints a message to the console saying that the server has started
	 * Displays within the program the IP on which the server was started
	 * Starts the thread to wait for connection requests
	 */
	public Server(ServerInterface aGUI) throws UnknownHostException{
		Utils.getLogger().info("Server Started");
		aGUI.displayMessage("Server started on IP: " + InetAddress.getLocalHost());
		connections = new ReceiveConnections("getRequests", aGUI);//Start thread to wait for requests
		connections.start();
	}
	/**
	 * Ends all connections and prints a message to the console 
	 * saying that the server is shutting down
	 */
	synchronized public void end() { //shutdown command
		connections.end();
		Utils.getLogger().info("server " + this + " shutting down.");
	}
}