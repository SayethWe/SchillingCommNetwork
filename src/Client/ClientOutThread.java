package Client;
import java.io.*;
import java.net.*;

class ClientOutThread extends Thread{
	private Thread t;
	private String threadName;
	InetAddress addr;
	String userStr;
	Socket clientSock;
	String Username;

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


	public void run(){

		try {
			//			System.out.println(clientSock);
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

			PrintWriter out = new PrintWriter(clientSock.getOutputStream());
			//			System.out.println(clientSock.getInetAddress());
			while(true){
				userStr = userIn.readLine();
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