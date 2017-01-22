package org.schillingSchool.communications.userInterface;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import org.schillingSchool.communications.client.Client;

public class ClientInterface extends GraphicalInterface implements ActionListener{
	final private static int TEXT_HISTORY = 10;
	final private static int DEFAULT_WIDTH = 20;
	final private static String TITLE = "Schilling Comm Network Client";
	final private static String PROMPT_TEXT = "Type a message Here";
	final private static String NEW_LINE = "\n";
	
	JTextArea displayText;
	JTextField typeBox;
	
	public ClientInterface() {
		super();
		setClientLayout();
		setTitle(TITLE);
		setSize(getPreferredSize());
		startClient();
	}
	
	private void setClientLayout() {
		displayText = new JTextArea(TEXT_HISTORY, DEFAULT_WIDTH);
		JScrollPane scrollPane = new JScrollPane(displayText);
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weighty = 0.5;
		constraints.weightx = 0.5;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		add(scrollPane, constraints);
		
		typeBox = new JTextField(PROMPT_TEXT);
		typeBox.setEditable(true);
		typeBox.addActionListener(this);
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(typeBox, constraints);
	}
	
	public void displayMessage(String message) {
		displayText.append(message + NEW_LINE);
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}
	
	public void startClient() {
		Thread execute = new Thread(new Client(this));
		execute.start();
	}

	@Override
	public void actionPerformed(ActionEvent event) { //activated when a user hits enter on the text box
		if(event.getSource() == typeBox) {
			String outStr = typeBox.getText(); //set our outString to be equal to what has been typed
			displayMessage(outStr); //display the outString. debug code.
			typeBox.setText(""); //clear the text box after a message has been sent
		} else {
			startClient();
		}
	}
}
