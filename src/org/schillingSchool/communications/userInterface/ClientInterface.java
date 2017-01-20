package org.schillingSchool.communications.userInterface;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientInterface extends GraphicalInterface implements ActionListener{
	final private static int TEXT_HISTORY = 10;
	final private static int COLUMNS = 1;
	final private static String TITLE = "Schilling Comm Network Client";
	final private static String PROMPT_TEXT = "Type a message Here";
	final private static String NEW_LINE = "\n";
	
	JTextArea displayText;
	JTextField typeBox;
	
	public ClientInterface() {
		super(COLUMNS);
		setClientLayout();
		drawInterface();
		setTitle(TITLE);
		setSize(getPreferredSize());
	}
	
	private void setClientLayout() {
		displayText = new JTextArea(COLUMNS,TEXT_HISTORY);
		JScrollPane scrollPane = new JScrollPane(displayText);
		displayText.setLineWrap(true);
		displayText.setWrapStyleWord(true);
		displayText.setEditable(false);
		displayText.setText("");
		components.add(scrollPane);
		
		typeBox = new JTextField(PROMPT_TEXT);
		typeBox.setEditable(true);
		typeBox.addActionListener(this);
		components.add(typeBox);
		
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
	}
}
