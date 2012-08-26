package com.mak001.main.coder.gui.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.mak001.main.Boot;
import com.mak001.main.Closer;
import com.mak001.main.coder.gui.AddTab;
import com.mak001.main.coder.gui.menus.EditMenu;
import com.mak001.main.coder.gui.menus.FileMenu;
import com.mak001.main.coder.gui.menus.HelpMenu;

@SuppressWarnings("serial")
public class CodeFrame extends JFrame {

	public final JTabbedPane tabbedPane;
	public final AddTab addTab;

	private final JTextArea area;

	public CodeFrame() {
		// DefaultSyntaxKit.initKit();
		addWindowListener(new Closer());
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		setTitle("Code Frame");

		addTab = new AddTab();
		area = new JTextArea("");
		area.setEditable(false);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		area.setText(Boot.LINE_SEPARATOR
				+ Boot.LINE_SEPARATOR
				+ "If you have no clue what you are doing, open the Help menu and click the Help option.");
		tabbedPane.add(area, 0);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(area), addTab);
		tabbedPane.setEnabledAt(tabbedPane.indexOfComponent(area), false);

		setSize(new Dimension(300, 600));

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new FileMenu();
		menuBar.add(mnFile);

		JMenu mnEdit = new EditMenu();
		menuBar.add(mnEdit);

		JMenu mnHelp = new HelpMenu();
		menuBar.add(mnHelp);

	}

	public int getAddTabIndex() {
		return tabbedPane.indexOfComponent(addTab);
	}
}