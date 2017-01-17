package Client;
import java.io.*;
import java.net.*;

class ClientInThread extends Thread{
	private Thread t;
	private String threadName;
	Socket clientSock;

	ClientInThread(String name, Socket socket){
		threadName = name;
		clientSock = socket;
		//		System.out.println("Initializing thread: " + threadName);
	}
	public void start(){
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
	public void run(){
		String inStr;


		while(true){
			try{
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

				inStr = in.readLine();
				System.out.println(inStr);
				if(inStr.equalsIgnoreCase("/Disconnected")){
					System.out.println("Server disconnected, terminating...");
					break;
				}
			} catch (IOException e){
				System.out.println("There was an IOException while reading the input: " + e);
				break;
			}
		}
		System.exit(0);
	}
}