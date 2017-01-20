package org.schillingSchool.communications.userInterface;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.util.ArrayList;

public class GraphicalInterface extends JFrame {
	ArrayList<JComponent> components = new ArrayList<JComponent>();
	
	GraphicalInterface(int columns) {
		setLayout(new GridLayout(0,columns));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	void drawInterface() {
		for (JComponent thisComponent : components) {
			add(thisComponent);
		}
	}
}


