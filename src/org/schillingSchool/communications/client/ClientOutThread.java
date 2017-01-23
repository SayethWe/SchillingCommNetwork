package org.schillingSchool.communications.client;
import java.io.*;
import java.net.*;

public class ClientOutThread extends Thread{
	private Thread t;
	private String threadName;
	InetAddress addr;
	String userStr;
	Socket clientSock;
	String Username;
	private static String userString;
	private static boolean dataAvailable = false;

	ClientOutThread(String name, Socket socket, String Uname){
		threadName = name;
		clientSock = socket;
		Username = Uname;
		//		System.out.println("Initializing thread: " + threadName);
	}

	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	
	public void setUserString(String inStr) {
		userString = inStr;
		dataAvailable = true;
	}


	public void run(){

		try {
			//			System.out.println(clientSock);
			PrintWriter out = new PrintWriter(clientSock.getOutputStream());
			//			System.out.println(clientSock.getInetAddress());
			while(true){
				while (!dataAvailable) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
			System.out.println("IO Exception: " + e);
		}
	}
}