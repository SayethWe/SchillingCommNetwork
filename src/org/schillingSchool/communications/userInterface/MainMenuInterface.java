package org.schillingSchool.communications.userInterface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.schillingSchool.communications.client.Client;
import org.schillingSchool.communications.server.Server;

public class MainMenuInterface extends GraphicalInterface implements ActionListener {
	final private static int X_SIZE = 250;
	final private static int Y_SIZE = 150;
	final private static int COLUMNS = 1;
	final private static String TITLE = "Schilling Comm Network";
	final private static String CLIENT_BUTTON = "Start your Client";
	final private static String SERVER_BUTTON = "Start a Server";

	public MainMenuInterface() {
		super(COLUMNS);
		setOpeningLayout();
		drawInterface();
		setTitle(TITLE);
		//setSize(X_SIZE, Y_SIZE);
		setSize(getPreferredSize());
	}

	private void setOpeningLayout() {
		JButton startClient = new JButton(CLIENT_BUTTON);
		startClient.addActionListener(this);
		startClient.setActionCommand(CLIENT_BUTTON);
		components.add(startClient);
		
		JLabel orLabel = new JLabel("or");
		orLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orLabel.setVerticalAlignment(SwingConstants.CENTER);
		components.add(orLabel);
		
		JButton startServer = new JButton(SERVER_BUTTON);
		startServer.addActionListener(this);
		startServer.setActionCommand(SERVER_BUTTON);
		components.add(startServer);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == CLIENT_BUTTON)	runClient();
		if (event.getActionCommand() == SERVER_BUTTON)	runServer();
	}

	private void runClient() {
//		try {
			new ClientInterface();
//			new Client();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	private void runServer() {
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}