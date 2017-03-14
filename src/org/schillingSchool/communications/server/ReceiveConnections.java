package org.schillingSchool.communications.server;
import java.net.*;
import java.io.IOException;

import org.schillingSchool.communications.userInterface.ServerInterface;
import org.schillingSchool.communications.utils.Utils;


/**
 * @author William Black
 * 		Wait for a connection, when a connection is received open a new thread for
 * 	client.
 * @Param name
 * @Param aGUI
 */
class ReceiveConnections extends Thread { //Separate thread to check for incoming requests
	private Thread t;
	private String threadName;
	int i = 0;
	ServerInterface myGUI;
	private volatile boolean run = true;
	
	/**
	 * Constructor for the ReceiveConnections class
	 * @param name
	 * @param aGUI
	 */
	ReceiveConnections(String name, ServerInterface aGUI){
		threadName = name;
		myGUI = aGUI;
		Utils.getLogger().info("Connections started");
	}
	
	/**
	 * Closes all the clients before coming to an end
	 */
	synchronized public void end() { //shutdown command
		run = false;
		for (ServerInThread thisThread : Server.threads) { //close each thread
			try {
				thisThread.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			thisThread.end();
			Utils.getLogger().info(thisThread + " closed.");
		}
		Utils.getLogger().info("No Longer Receiving Connections");
	}

	/**
	 * Starts the receive connections thread
	 */
	public void start(){ //Start thread
		if (t == null) { //if no thread exists
			t = new Thread(this, threadName); //create a thread
			t.start(); //start the thread
		}
	}
	/**
	 * Wait for a client, once one is received give them instructions to connect
	 * Open a new thread for them and connect with them
	 */
	public void run(){
		Utils.getLogger().info("Running Connector");
		InetAddress conAddr; //address of person attempting to connect
		int conPort; //port of person attempting to connect
		while(run){ //Don't stop looping, unless we've been sent a close message
			byte[] Pong = new byte[256]; //byte array for packages
			Utils.getLogger().info(this.toString() + " started");

			try{
				DatagramSocket conSock = new DatagramSocket(3333); //Create a socket to wait for connection requests
				DatagramPacket conPack = new DatagramPacket(Pong, Pong.length); //Create package to wait for connection requests

				conSock.receive(conPack); //wait for connection requests

				//Package has been received
				conAddr = conPack.getAddress(); //Get address of requestee
				conPort = conPack.getPort(); //Get port of requestee

				myGUI.displayMessage("Client request detected");
				myGUI.displayMessage("Client addr: ");
				myGUI.displayMessage(conAddr + "");
				myGUI.displayMessage("Client port: ");
				myGUI.displayMessage(conPort + "");

				Server.clientSocks.add(new ServerSocket(0)); //Create new server in the serversocket arraylist
				Server.socks.add(new Socket());
				Pong = Integer.toString(Server.clientSocks.get(Server.clientSocks.size() - 1).getLocalPort()).getBytes();
				// ^^^ Take the ServerSocket we just created port number, convert to string
				//			convert said string into byte array.

				conPack = new DatagramPacket(Pong, Pong.length, conAddr, conPort); //create package with port number
				conSock.send(conPack); //send package with port number
				conSock.close(); //close DataGram Sock

				Server.clientSocks.get(Server.clientSocks.size() - 1).setSoTimeout(5000); //Set timeout of server to 5 seconds
				Server.socks.set(Server.socks.size() - 1, Server.clientSocks.get(Server.clientSocks.size() - 1).accept() ); //Wait 5 seconds for a connection

				//If it doesn't crash this far in, client's connected
				myGUI.displayMessage("Client connected");
				myGUI.displayMessage("================");
				myGUI.displayMessage("");

				Server.threads.add(new ServerInThread("Thread " + i, Server.socks.get(Server.socks.size() - 1), myGUI));

				Server.threads.get(Server.threads.size() - 1).start();
				i++;
			} catch(IOException e){
				if(Server.clientSocks.get(Server.clientSocks.size() - 1) != null){ //If the client doesn't successfully connect, close socket to prevent port wasting... In theory.
					try{
						Server.clientSocks.get(Server.clientSocks.size() - 1).close();
					}catch(IOException e2){
						Utils.getLogger().warning(e.getStackTrace().toString());
						Utils.getLogger().warning(e2.getStackTrace().toString());
					}
				}

				myGUI.displayMessage("Error connecting: " + e); //toss an error
			}
		}
	}
}