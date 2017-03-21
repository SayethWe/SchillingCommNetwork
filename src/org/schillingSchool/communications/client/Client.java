package org.schillingSchool.communications.client;

import java.io.IOException; //An exception to the input or output
import java.net.DatagramPacket; //
import java.net.DatagramSocket; //
import java.net.InetAddress; //
import java.net.Socket; //
import java.net.UnknownHostException; //

import org.schillingSchool.communications.userInterface.ClientInterface;
/**
 * 
 * @author William Black
 * @Param aGUI, A Client Interface GUI
 *
 */
public class Client implements Runnable {
	final private static int DEFAULT_PORT = 3333;

	private static ClientInterface myGUI;
	private static volatile String userString;
	private static volatile boolean dataAvailable = false;

	private ClientInThread in;
	private ClientOutThread out;

	/**
	 * The constructor
	 * @param aGUI, another GUI
	 */
	public Client(ClientInterface aGUI) {
		myGUI = aGUI;
	}

	@Override
	/**
	 * The startup code for the runnable
	 */
	public void run() {
		try {
			startUp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the User String, and declares data is available
	 * @param inStr
	 */
	synchronized public void setUserString(String inStr) {
		userString = inStr;
		dataAvailable = true;
	}

	/**
	 * 	Attempts connection to server, if connection is true:
	 * starts the two client - server communication threads
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void startUp() throws UnknownHostException, IOException {
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
			DatagramPacket dataPack = new DatagramPacket(Ping, Ping.length, hostAddr, DEFAULT_PORT); //Create a packet to the host address (specified earlier), and port (pre-specified as the port to accept requests)

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


		Username = getInfo("Please enter username:"); //Get user name (will be implemented later)
		myGUI.displayMessage("Connected. You can now send messages, type '/Server disconnect' to disconnect");
		//Start communication Thread

		out = new ClientOutThread("Client Out", clientSock, Username);
		out.start(); //start out thread
		in = new ClientInThread("Client in", clientSock, myGUI);
		in.start(); //start in thread

		myGUI.clientFinishedStarting(out);
	}

	/**
	 * A badly named class that sends data to the client GUI
	 * @param prompt, the message to display to the client
	 * @return userString, the message displayed
	 * @throws IOException
	 */
	public static String getInfo(String prompt) throws IOException{ //give user a prompt, return user input
		//		BufferedReader userInfo = new BufferedReader( new InputStreamReader(System.in));
		//System.out.println(prompt);
		myGUI.displayMessage(prompt);
		while (!dataAvailable) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//wait for there to be actual data.
		}
		dataAvailable = false;
		return userString;
	}

	/**
	 * Ends the two communication threads
	 */
	synchronized public void end() {
		out.end();
		in.end();
	}
}