package org.schillingSchool.communications.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.schillingSchool.communications.utils.Utils;

public class ClientOutThread extends Thread{
	private Thread t;
	private String threadName;
	String userStr;
	Socket clientSock;
	String Username;
	private static volatile String userString;
	private static volatile boolean dataAvailable = false;
	private static volatile boolean run = true;

	ClientOutThread(String name, Socket socket, String Uname){
		threadName = name;
		clientSock = socket;
		Username = Uname;
		Utils.getLogger().info("Initializing thread: " + threadName);
	}

	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	synchronized public void setUserString(String inStr) {
		userString = inStr;
		dataAvailable = true;
	}

	synchronized public void end() {
		run = false;
	}

	public void run(){
		try {
			Utils.getLogger().info(clientSock.toString());
			PrintWriter out = new PrintWriter(clientSock.getOutputStream());
			Utils.getLogger().info(clientSock.getInetAddress().toString());
			while(run){
				while (!dataAvailable) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//wait for there to be actual data.
				}
				dataAvailable = false;
				userStr = userString;
				out.println(Username + ": " + userStr);
				out.flush();
				if(userStr.equalsIgnoreCase("/Server disconnect")){
					break;
				}
			}
			out.close();
			clientSock.close();
			System.exit(0);
		}catch(IOException e){
			Utils.getLogger().severe("IO Exception: " + e);
		}
	}
}