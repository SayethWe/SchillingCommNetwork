package org.schillingSchool.communications.userInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.schillingSchool.communications.server.Server;
import org.schillingSchool.communications.utils.Utils;

public class ServerInterface extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final private static int TEXT_HISTORY = 11; //how many lines to show by default
	final private static int DEFAULT_WIDTH = 20; //how wide to be by default
	final private static String NEW_LINE = "\n"; //java's new line character
	final private static String TITLE = "Schilling Comm Network Server";
	
	JTextArea log;
	Server myServer;
	GridBagConstraints constraints;
	
	public ServerInterface() { //the constructor
		Utils.getLogger().info("Server interface opened");
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		setServerLayout();
		setTitle(TITLE);
		setSize(getPreferredSize());
		setVisible(true);
		startServer();
	}
	
	private void startServer() {
		try {
			myServer = new Server(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setServerLayout() {
		log = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH); //define a log element as a text area
		JScrollPane scrollPane = new JScrollPane(log); //create a scroll pane containing this element
		log.setLineWrap(true); //allow things to wrap to a new line instead of going sideways
		log.setWrapStyleWord(true); //wrap at the break of a word
		log.setEditable(false); //make it so this can't be changed, only viewed.
		log.setText(""); //set a default blank text
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH; //set this element to resize to fill both directions
		add(scrollPane, constraints); //add this element
		
		addWindowListener(this); //give this frame a window listener
	}
	
	public void displayMessage(String message) { //show a message we've been sent
		log.append(message + NEW_LINE); //add the text, and a new line, to the log.
		log.setCaretPosition(log.getDocument().getLength());//jump the cursor to the end of the display to show it
	}

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosing(WindowEvent e) { //when the window is being closed
		myServer.end(); //send the server a shutdown command
	}
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}

}