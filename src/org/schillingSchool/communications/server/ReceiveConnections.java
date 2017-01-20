package org.schillingSchool.communications.server;
import java.net.*;
import java.io.IOException;
 
class ReceiveConnections extends Thread { //Separate thread to check for incoming requests
	private Thread t;
	private String threadName;
	int i = 0;
	ReceiveConnections(String name){
		threadName = name;
	}

	public void start(){ //Start thread
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

	public void run(){
		InetAddress conAddr; //address of person attempting to connect
		int conPort; //port of person attempting to connect
		while(true){ //Don't stop looping
			byte[] Pong = new byte[256]; //byte array for packages

			try{
				DatagramSocket conSock = new DatagramSocket(3333); //Create a socket to wait for connection requests
				DatagramPacket conPack = new DatagramPacket(Pong, Pong.length); //Create package to wait for connection requests

				conSock.receive(conPack); //wait for connection requests

				//Package has been received
				conAddr = conPack.getAddress(); //Get address of requestee
				conPort = conPack.getPort(); //Get port of requestee

				System.out.println("Client request detected");
				System.out.println("Client addr: ");
				System.out.println(conAddr);
				System.out.println("Client port: ");
				System.out.println(conPort);

				Server.clientSocks.add(new ServerSocket(0)); //Create new server in the serversocket arraylist
				Server.Socks.add(new Socket());
				Pong = Integer.toString(Server.clientSocks.get(Server.clientSocks.size() - 1).getLocalPort()).getBytes();
				// ^^^ Take the ServerSocket we just created port number, convert to string
				//			convert said string into byte array.

				conPack = new DatagramPacket(Pong, Pong.length, conAddr, conPort); //create package with port number
				conSock.send(conPack); //send package with port number
				conSock.close(); //close DataGram Sock

				Server.clientSocks.get(Server.clientSocks.size() - 1).setSoTimeout(5000); //Set timeout of server to 5 seconds
				Server.Socks.set(Server.Socks.size() - 1, Server.clientSocks.get(Server.clientSocks.size() - 1).accept() ); //Wait 5 seconds for a connection

				//If it doesn't crash this far in, client's connected
				System.out.println("Client connected");
				System.out.println("================");
				System.out.println();

				Server.Threads.add(new ServerInThread("Thread " + i, Server.Socks.get(Server.Socks.size() - 1)));

				Server.Threads.get(Server.Threads.size() - 1).start();
				i++;
			} catch(IOException e){
				if(Server.clientSocks.get(Server.clientSocks.size() - 1) != null){ //If the client doesn't successfully connect, close socket to prevent port wasting... In theory.
					try{
						Server.clientSocks.get(Server.clientSocks.size() - 1).close();
					}catch(IOException e2){

					}
				}

				System.out.println("Error connecting: " + e);
			}
		}

	}
}