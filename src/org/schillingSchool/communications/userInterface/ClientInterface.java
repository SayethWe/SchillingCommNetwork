package org.schillingSchool.communications.userInterface;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

public class ClientInterface extends GraphicalInterface implements ActionListener{
	final private static int TEXT_HISTORY = 10;
	final private static int DEFAULT_WIDTH = 200;
	final private static String TITLE = "Schilling Comm Network Client";
	final private static String PROMPT_TEXT = "Type a message Here";
	final private static String NEW_LINE = "\n";
	
	JTextArea displayText;
	JTextField typeBox;
	
	public ClientInterface() {
		super();
		setClientLayout();
		setTitle(TITLE);
		//setSize(getPreferredSize());
		setSize(getPreferredSize());
	}
	
	private void setClientLayout() {
		displayText = new JTextArea(DEFAULT_WIDTH,TEXT_HISTORY);
		JScrollPane scrollPane = new JScrollPane(displayText);
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridheight = TEXT_HISTORY;
		constraints.fill = GridBagConstraints.BOTH;
		add(scrollPane, constraints);
		
		typeBox = new JTextField(PROMPT_TEXT, DEFAULT_WIDTH);
		typeBox.setEditable(true);
		typeBox.addActionListener(this);
		constraints.gridheight = 1;
		constraints.gridx = 0;
		constraints.gridy = TEXT_HISTORY;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(typeBox, constraints);
		
		displayMessage("Enter a host IP"); //until we can get the server to ask, this is here
	}
	
	void displayMessage(String message) {
		displayText.append(message + NEW_LINE);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String outStr = typeBox.getText();
		displayMessage(outStr);
		typeBox.setText(""); //clear the text box after a message has been sent
		displayText.setCaretPosition(displayText.getDocument().getLength());//jump the cursor to the end of the display to show it
	}
}
