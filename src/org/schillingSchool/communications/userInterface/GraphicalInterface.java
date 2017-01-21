package org.schillingSchool.communications.userInterface;

import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class GraphicalInterface extends JFrame {
	GridBagConstraints constraints;
	
	public GraphicalInterface() {
		setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}


