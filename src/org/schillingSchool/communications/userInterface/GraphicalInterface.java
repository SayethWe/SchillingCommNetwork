package org.schillingSchool.communications.userInterface;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GraphicalInterface extends JFrame implements WindowListener {
	ArrayList<JComponent> components = new ArrayList<JComponent>();
	
	GraphicalInterface(int columns) {
		setLayout(new GridLayout(0,columns));
		addWindowListener(this);
		setVisible(true);
	}

	void drawInterface() {
		for (JComponent thisComponent : components) {
			add(thisComponent);
		}
	}
	
	@Override
	public void windowClosing(WindowEvent event) {
		System.exit(0);
	}
	
	@Override public void windowOpened(WindowEvent evt) { }
	@Override public void windowClosed(WindowEvent evt) { }
	@Override public void windowIconified(WindowEvent evt) { }
	@Override public void windowDeiconified(WindowEvent evt) { }
	@Override public void windowActivated(WindowEvent evt) { }
	@Override public void windowDeactivated(WindowEvent evt) { }
}


