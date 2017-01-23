package org.schillingSchool.communications.userInterface;

import java.awt.GridBagConstraints;
import java.io.IOException;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.schillingSchool.communications.server.Server;

public class ServerInterface extends GraphicalInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //TODO auto-generate Serial ID
	final private static int TEXT_HISTORY = 10;
	final private static int DEFAULT_WIDTH = 20;
	final private static String NEW_LINE = "\n";
	final private static String TITLE = "Schilling Comm Network Server";
	
	JTextArea log;
	Server myServer;
	
	public ServerInterface() {
		super();
		setServerLayout();
		setTitle(TITLE);
		setSize(getPreferredSize());
		startServer();
	}
	
	private void startServer() {
		try {
			new Server(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setServerLayout() {
		log = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(log);
		log.setLineWrap(true);
		log.setWrapStyleWord(true);
		log.setEditable(false);
		log.setText("");
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		add(scrollPane, constraints);
	}
	
	public void displayMessage(String message) {
		log.append(message + NEW_LINE);
		log.setCaretPosition(log.getDocument().getLength());//jump the cursor to the end of the display to show it
	}

}