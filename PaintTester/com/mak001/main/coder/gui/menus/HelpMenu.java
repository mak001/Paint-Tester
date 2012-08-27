package com.mak001.main.coder.gui.menus;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.frames.CreditFrame;
import com.mak001.main.coder.gui.frames.HelpFrame;

@SuppressWarnings("serial")
public class HelpMenu extends JMenu {

	private CreditFrame creditFrame;

	public HelpMenu() {
		super("Help");
		JMenuItem mntmHelp = new JMenuItem("Help");
		add(mntmHelp);
		mntmHelp.addActionListener(help);

		JMenuItem mntmAbout = new JMenuItem("About");
		add(mntmAbout);
		mntmAbout.addActionListener(about);

		JMenuItem mntmSite = new JMenuItem("Site");
		add(mntmSite);
		mntmSite.addActionListener(site);

		JMenuItem mntmCredits = new JMenuItem("Credits");
		add(mntmCredits);
		mntmCredits.addActionListener(credits);
	}

	private ActionListener credits = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (creditFrame == null)
				creditFrame = new CreditFrame();
			JOptionPane.showMessageDialog(Boot.codeFrame, creditFrame,
					"Credits", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	private ActionListener site = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Desktop.getDesktop().browse(new URI(Boot.SITE));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	};

	private ActionListener about = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane
					.showMessageDialog(
							Boot.codeFrame,
							"This was made to test powerbot paints without actually loading up the rsbot client",
							"About", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	private ActionListener help = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.helpFrame == null)
				GlobalVariables.helpFrame = new HelpFrame();
			GlobalVariables.helpFrame.setVisible(true);
		}
	};

}
