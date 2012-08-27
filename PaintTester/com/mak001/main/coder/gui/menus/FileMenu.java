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
import com.mak001.main.coder.Saver;
import com.mak001.main.coder.gui.frames.SettingsFrame;

@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	public FileMenu() {
		super("File");

		JMenuItem mntmNew = new JMenuItem("New");
		this.add(mntmNew);
		mntmNew.addActionListener(newListener);

		JMenuItem mntmSave = new JMenuItem("Save");
		this.add(mntmSave);
		mntmSave.addActionListener(save);

		JMenuItem mntmSaveas = new JMenuItem("Save As...");
		this.add(mntmSaveas);
		mntmSaveas.addActionListener(saveAs);

		JMenuItem mntmLoad = new JMenuItem("Open...");
		this.add(mntmLoad);
		mntmLoad.addActionListener(open);

		this.add(new JSeparator());

		JMenuItem mntmExit = new JMenuItem("Exit");
		this.add(mntmExit);
		mntmExit.addActionListener(exit);
	}

	private final ActionListener exit = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(null,
					"Do you really want to exit?", "Confirm", 0) == 0) {
				Saver.save();
				System.exit(0);
			}
		}
	};

	private final ActionListener open = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.loadDialog == null) {
				initFileLoader();
			}

			if (GlobalVariables.loadDialog.showOpenDialog(Boot.codeFrame) == JFileChooser.APPROVE_OPTION) {
				File f = GlobalVariables.loadDialog.getSelectedFile();
				if (!f.exists()) {
					JOptionPane.showMessageDialog(Boot.codeFrame,
							"File does not exist!", "Error!",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				GlobalVariables.openedFile = f;
				if (!Loader.load(f)) {
					JOptionPane.showMessageDialog(Boot.codeFrame,
							"Could not read file!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	private final ActionListener saveAs = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.saveDialog == null) {
				initFileSaver();
			}

			Saver.saveAs();
		}

	};

	private final ActionListener save = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GlobalVariables.saveDialog == null) {
				initFileSaver();
			}

			Saver.save();
		}
	};

	private final ActionListener newListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(null,
					"Do you wish to save the current project?", "Confirm", 0) == 0) {
				Saver.save();
				if (GlobalVariables.settingsFrame == null) {
					GlobalVariables.settingsFrame = new SettingsFrame();
				}
				GlobalVariables.settingsFrame.setVisible(true);
			}
			Boot.setOpenedFile(null);
			GlobalVariables.clearClasses();
		}
	};

	private void initFileSaver() {
		GlobalVariables.saveDialog = new JFileChooser();
		GlobalVariables.saveDialog.setAcceptAllFileFilterUsed(false);
		GlobalVariables.saveDialog.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().endsWith(Boot.FILE_EXTENSION);
			}

			@Override
			public String getDescription() {
				return "code list files";
			}
		});
	}

	private void initFileLoader() {
		GlobalVariables.loadDialog = new JFileChooser();
		GlobalVariables.loadDialog.setAcceptAllFileFilterUsed(false);
		GlobalVariables.loadDialog.setFileFilter(new FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().endsWith(Boot.FILE_EXTENSION);
			}

			@Override
			public String getDescription() {
				return "code list files";
			}
		});
	}

}
