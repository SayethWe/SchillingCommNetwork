package org.schillingSchool.communications.userInterface;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.schillingSchool.communications.server.Server;

public class MainMenuInterface extends GraphicalInterface implements ActionListener {
	final private static String TITLE = "Schilling Comm Network";
	final private static String CLIENT_BUTTON = "Start your Client";
	final private static String SERVER_BUTTON = "Start a Server";
	
	public MainMenuInterface() {
		super();
		setOpeningLayout();
		setTitle(TITLE);
		setResizable(false);
		//setSize(X_SIZE, Y_SIZE);
		setSize(getPreferredSize());
	}

	private void setOpeningLayout() {
		JButton startClient = new JButton(CLIENT_BUTTON);
		startClient.addActionListener(this);
		startClient.setActionCommand(CLIENT_BUTTON);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(startClient, constraints);
		
		JLabel orLabel = new JLabel("or");
		orLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orLabel.setVerticalAlignment(SwingConstants.CENTER);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(orLabel, constraints);
		
		JButton startServer = new JButton(SERVER_BUTTON);
		startServer.addActionListener(this);
		startServer.setActionCommand(SERVER_BUTTON);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(startServer, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == CLIENT_BUTTON)	runClient();
		if (event.getActionCommand() == SERVER_BUTTON)	runServer();
	}

	private void runClient() {
			new ClientInterface();
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