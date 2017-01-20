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
	final private int X_SIZE = 250;
	final private int Y_SIZE = 150;
	final private String CLIENT_BUTTON = "Start your Client";
	final private String SERVER_BUTTON = "Start a Server";
	
	JButton startServer;
	JButton startClient;

	public MainMenuInterface() {
		super(1);
		setOpeningLayout();
		drawInterface();
		setTitle("Schilling Comm Network");
		setSize(X_SIZE, Y_SIZE);
	}

	private void setOpeningLayout() {
		startClient = new JButton(CLIENT_BUTTON);
		startClient.addActionListener(this);
		components.add(startClient);
		
		JLabel orLabel = new JLabel("or");
		orLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orLabel.setVerticalAlignment(SwingConstants.CENTER);
		components.add(orLabel);
		
		startServer = new JButton(SERVER_BUTTON);
		startServer.addActionListener(this);
		components.add(startServer);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == startClient)	runClient();
		if (event.getSource() == startServer)	runServer();
	}

	private void runClient() {
		try {
			new Client();
			setVisible(false);
			this.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void runServer() {
		try {
			new Server();
			setVisible(false);
			this.dispose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
