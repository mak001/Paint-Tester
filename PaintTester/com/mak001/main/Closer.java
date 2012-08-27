package com.mak001.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class Closer extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent evt) {
		if (JOptionPane.showConfirmDialog(null, "Do you really want to exit?",
				"Confirm", 0) == 0) {
			System.exit(0);
		}
	}

}
