package org.schillingSchool.communications.client;
import java.io.*;
import java.net.*;
import org.schillingSchool.communications.userInterface.ClientInterface;

public class Client implements Runnable {
	static ClientInterface myGUI;
	
	public Client(ClientInterface aGUI) {
		myGUI = aGUI;
	}
	
	@Override
	public void run() {
		try {
			startUp();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startUp() throws UnknownHostException, IOException {
		InetAddress hostAddr;
		String Username = "";
		String hostPort = null;

		boolean connection = true;
		//Initiate handshake with server to get an open port to bind to
		byte[] Ping = new byte[256];

		//Get server address from client
		hostAddr = InetAddress.getByName(getInfo("Enter host address:"));

		//Try to connect 3 times before giving up
		for(int i = 0; i < 3; i++){

			connection = true; //If a connection has been established, exit loop

			DatagramSocket dataSock = new DatagramSocket(); //Create socket on first available port
			DatagramPacket dataPack = new DatagramPacket(Ping, Ping.length, hostAddr, 3333); //Create a packet to the host address (specified earlier), and port (pre-specified as the port to accept requests)

			dataSock.send(dataPack); //Send packet to server requesting connection port

			dataPack = new DatagramPacket(Ping, Ping.length); //reuse the packet used to send the request to wait for reply
			dataSock.setSoTimeout(10000);

			try { //wait 10 seconds for reply from server, if no reply, try again
				dataSock.receive(dataPack);
				hostPort = new String(dataPack.getData(), 0, dataPack.getLength());//Convert byte array into a port number
			}catch (Exception e){
				dataSock.close();
				myGUI.displayMessage("Try " + (i + 1) + " failed");
				connection = false;
			}
			if(connection == true){ //If port number returned, exit the loop and establish connection
				break;
			}
		}
		if(hostPort == null){ //assurance that we don't try to bind to a null port
			myGUI.displayMessage("Server not responding, terminating...");
			System.exit(0);
		}
		myGUI.displayMessage("Host port: " + hostPort);

		Socket clientSock = new Socket(hostAddr, Integer.parseInt(hostPort)); //Establish connection with server


		Username = getInfo("Please enter username:"); //Get username (will be implemented later)
		myGUI.displayMessage("Connected. You can now send messages, type '/Server disconnect' to disconnect");
		//Start communication Thread

		ClientOutThread out = new ClientOutThread("Client Out", clientSock, Username, myGUI);
		out.start(); //start out thread
		ClientInThread in = new ClientInThread("Client in", clientSock, myGUI);
		in.start(); //start in thread
	}

	public static String getInfo(String prompt) throws IOException{ //give user a prompt, return user input
		BufferedReader userInfo = new BufferedReader( new InputStreamReader(System.in));
		//System.out.println(prompt);
		myGUI.displayMessage(prompt);
		return userInfo.readLine();
	}

}