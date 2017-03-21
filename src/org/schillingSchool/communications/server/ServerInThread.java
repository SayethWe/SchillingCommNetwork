package org.schillingSchool.communications.server;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.schillingSchool.communications.userInterface.ServerInterface;
import org.schillingSchool.communications.utils.Utils;

/**
 * A thread that listens to a specified port. The server creates one for ach client it thinks it has
 * @author DMWCincy
 * Written pi day 2017 (14/3/17)
 */
class ServerInThread extends Thread{
	private final static String CLOSING_MESSAGE = "Server Shutting down";
	private Thread t; //Thread we run on
	private String threadName; //Thread name to identify it
	private Socket clientSock; //The socket we talk over
	private ServerInterface myGUI; //a gui to talk to
	private volatile boolean runThread = true;

	/**
	 * Construct the In Thread with specified name, socket, and gui. 
	 * @param name A name to help identify the thread
	 * @param socket the socket we're listening for data on
	 * @param aGUI the gui we send our messages to
	 */
	ServerInThread(String name, Socket socket, ServerInterface aGUI){
		threadName = name;
		clientSock = socket;
		myGUI = aGUI;
		Utils.getLogger().info("Initializing thread: " + threadName); //log that we've started
	}
	
	/**
	 * Start this thread on a separate entity
	 */
	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
		myGUI.displayMessage("Thread " + threadName + " Started");
	}
	
	/**
	 * get the socket we talk over
	 * @return the socket this Thread uses to listen
	 */
	public Socket getSocket() {
		return clientSock;
	}
	
	/**
	 * tell this method it's time to end
	 */
	synchronized public void end() {
		runThread = false; //it's no longer time to work
		try {
			printMsg(CLOSING_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method that runs once the thread is created
	 */
	public void run(){
		String inStr;
		String[] clientName;
		while(runThread){ //run, unless we've been told not to
			//Read from client program, if client says bye, close program
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); 

				inStr = in.readLine();
				clientName = inStr.split(":");
				if(inStr.equalsIgnoreCase(clientName[0] + ": /Server disconnect")){
					myGUI.displayMessage(clientName[0] + " disconnected");
					Server.socks.remove(clientSock);
					runThread = false;
					clientSock.close();
					clientName = inStr.split(":");
					inStr = clientName[0] + " has disconnected";
					printMsg(inStr);
					break;
				}
				printMsg(inStr);
			} catch (IOException e){
				myGUI.displayMessage("There was an IOException while reading the input: " + e);
			}
		}
	}
	
	private void printMsg(String inStr) throws IOException {
		for (Socket thisSocket : Server.socks) {
			PrintWriter out = new PrintWriter(thisSocket.getOutputStream());
			out.println(inStr);
			out.flush();
			//out.close();
		}
	}
}