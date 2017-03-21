package org.schillingSchool.communications.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.schillingSchool.communications.userInterface.ClientInterface;
import org.schillingSchool.communications.utils.Utils;

/**
 * An in thread for the client to read message from the server off of
 * @author DMWCincy
 * Written pi day 2017 (14/3/17)
 */
class ClientInThread extends Thread{
	private Thread t; //the thread we run on
	private String threadName; //a name to identify the thread
	Socket clientSock; //the socket we talk over
	ClientInterface myGUI; //the gui we talk to
	private static volatile boolean run = true; //if we're still supposed to run. Should always be initialized to true.

	/**
	 * Construct a client inThread to work with
	 * @param name the name this thread will associate with
	 * @param socket the socket to talk over
	 * @param aGUI the interface to send messages to
	 */
	ClientInThread(String name, Socket socket, ClientInterface aGUI){
		threadName = name;
		clientSock = socket;
		myGUI = aGUI;
		//		System.out.println("Initializing thread: " + threadName);
	}
	
	/**
	 * start this thread up. Calls a new thread to surround this object and uses its start() method.
	 */
	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	/**
	 * Tell this thread it's time to end
	 */
	synchronized public void end() {
		run = false;
	}
	
	/**
	 * Run the thread. invoked by the Thread.start() method of the superclass
	 */
	@Override
	public void run(){
		String inStr;
		
		while(run){ //as long as we're not told to stop
			try{ //try this....
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); //create a reader on the socket

				inStr = in.readLine(); //read the line (When it terminates with a \n or \r)
				myGUI.displayMessage(inStr); //write the message to the gui
				if(inStr.equalsIgnoreCase("/Disconnected")){ //if the server tells it it cut us off
					myGUI.displayMessage("Server disconnected, terminating...");
					break; //exit the loop
				}
			} catch (IOException e){ //...unless something goes wrong with the input/output
				Utils.getLogger().severe("There was an IOException while reading the input: " + e); //dump out data to the console
				break;
			}
		}
		System.exit(0); //exit the system. bad code to be finished off
	}
}