package org.schillingSchool.communications.server;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.schillingSchool.communications.userInterface.ServerInterface;

class ServerInThread extends Thread{//Read from client
	private Thread t; //Thread
	private String threadName; //Thread name
	Socket clientSock; //The socket
	ServerInterface myGUI;

	ServerInThread(String name, Socket socket, ServerInterface aGUI){
		threadName = name;
		clientSock = socket;
		myGUI = aGUI;
		//		System.out.println("Initializing thread: " + threadName);
	}
	public void start(){ //Start thread
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
		myGUI.displayMessage("Thread " + threadName + " Started");
	}
	public void run(){
		String inStr;
		String[] clientName;
		boolean runThread = true;
		while(runThread){
			//Read from client program, if client says bye, close program
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream())); 

				inStr = in.readLine();
				clientName = inStr.split(":");
				if(inStr.equalsIgnoreCase(clientName[0] + ": /Server disconnect")){
					myGUI.displayMessage(clientName[0] + " disconnected");
					Server.Socks.remove(clientSock);
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
		for (Socket thisSocket : Server.Socks) {
			PrintWriter out = new PrintWriter(thisSocket.getOutputStream());
			out.println(inStr);
			out.flush();
			//out.close();
		}
	}
}