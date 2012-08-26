package com.mak001.main.coder.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileFilter;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.Loader;
import com.mak001.main.coder.gui.CodeTab;
import com.mak001.main.coder.gui.frames.OpenClosedFileFrame;
import com.mak001.main.coder.gui.frames.SettingsFrame;
import com.mak001.main.coder.gui.frames.VariableManipulatorFrame;

@SuppressWarnings("serial")
public class EditMenu extends JMenu {

	private VariableManipulatorFrame manipulationFrame;

	public EditMenu() {
		super("Edit");

		JMenuItem mntmImportjava = new JMenuItem("Import...");
		add(mntmImportjava);
		mntmImportjava.addActionListener(importFile);

		JMenuItem mntmOpenAFile = new JMenuItem("Open a closed file");
		add(mntmOpenAFile);
		mntmOpenAFile.addActionListener(openClosedFile);

		JMenuItem mnCloseAll = new JMenuItem("Close all");
		add(mnCloseAll);
		mnCloseAll.addActionListener(closeAll);

		JMenuItem mntmSettings = new JMenuItem("Settings...");
		add(mntmSettings);
		mntmSettings.addActionListener(settings);

		JMenuItem mntmMinipulateVariables = new JMenuItem(
				"Manipulate variables");
		add(mntmMinipulateVariables);
		mntmMinipulateVariables.addActionListener(manipulate);

		add(new JSeparator());

		JMenuItem mntmReloadProject = new JMenuItem("Reload project");
		add(mntmReloadProject);
		mntmReloadProject.addActionListener(reload);
	}

	private final ActionListener importFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.importDialog == null) {
				initFileImporter();
			}
			if (GlobalVariables.importDialog.showOpenDialog(Boot.codeFrame) == JFileChooser.APPROVE_OPTION) {
				if (GlobalVariables.importDialog.getSelectedFile() != null) {
					Loader.loadExtenal(GlobalVariables.importDialog
							.getSelectedFile());
				} else {
				}
			}
		}
	};

	private final ActionListener reload = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(Boot.codeFrame,
					"Are you sure you wish to reload the project?"
							+ Boot.LINE_SEPARATOR
							+ "Reloading loads files from the last save.",
					"Reload", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				if (GlobalVariables.openedFile != null) {

					if (Loader.load(GlobalVariables.openedFile)) {
						JOptionPane.showMessageDialog(Boot.codeFrame,
								"Project was successfully reloaded!", "",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(Boot.codeFrame,
								"Project failed to reload!", "Error!",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(Boot.codeFrame,
							"Project was not saved!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
			}
		}
	};

	private final ActionListener settings = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.settingsFrame == null) {
				GlobalVariables.settingsFrame = new SettingsFrame();
			}
			GlobalVariables.settingsFrame.setVisible(true);
		}
	};

	private final ActionListener openClosedFile = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.openClosedFileFrame == null) {
				GlobalVariables.openClosedFileFrame = new OpenClosedFileFrame();
			}
			GlobalVariables.openClosedFileFrame.setVisible(true);
		}
	};

	private final ActionListener closeAll = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (CodeTab ct : GlobalVariables.codeTabs) {
				ct.setClosed(true);
			}
		}
	};

	private final ActionListener manipulate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (manipulationFrame == null) {
				manipulationFrame = new VariableManipulatorFrame();
			}
			manipulationFrame.setVisible(true);
		}
	};

	private void initFileImporter() {

		GlobalVariables.importDialog = new JFileChooser();
		GlobalVariables.importDialog.setAcceptAllFileFilterUsed(false);
		GlobalVariables.importDialog.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().endsWith(Boot.JAVA_EXTENSION);
			}

			@Override
			public String getDescription() {
				return "java source files";
			}
		});
	}
}
