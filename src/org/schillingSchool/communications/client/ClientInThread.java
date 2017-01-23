package org.schillingSchool.communications.client;
import java.io.*;
import java.net.*;
import org.schillingSchool.communications.userInterface.ClientInterface;

class ClientInThread extends Thread{
	private Thread t;
	private String threadName;
	Socket clientSock;
	ClientInterface myGUI;
	private static volatile boolean run = true;

	ClientInThread(String name, Socket socket, ClientInterface aGUI){
		threadName = name;
		clientSock = socket;
		myGUI = aGUI;
		//		System.out.println("Initializing thread: " + threadName);
	}
	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	synchronized public void end() {
		run = false;
	}
	public void run(){
		String inStr;
		
		while(run){
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

				inStr = in.readLine();
				myGUI.displayMessage(inStr);
				if(inStr.equalsIgnoreCase("/Disconnected")){
					myGUI.displayMessage("Server disconnected, terminating...");
					break;
				}
			} catch (IOException e){
				//System.out.println("There was an IOException while reading the input: " + e);
				//Sssssh, The client doesn't need to know that the project's going to hell
				break;
			}
		}
		System.exit(0);
	}
}