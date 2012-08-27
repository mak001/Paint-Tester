package com.mak001.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.mak001.main.coder.Bot;
import com.mak001.main.coder.gui.CustomListListener;
import com.mak001.main.coder.gui.frames.CodeFrame;
import com.mak001.main.coder.gui.frames.OpenClosedFileFrame;
import com.mak001.main.coder.gui.frames.SettingsFrame;
import com.mak001.main.graphics.CanvasFrame;

public class Boot {

	public static final String FILE_EXTENSION = ".xml",
			JAVA_EXTENSION = ".java", DEFAULT_TAG = "default",
			OPEN_TAG = "open", PATH_TAG = "path", FILE_TAG = "File",
			TOP_LEVEL_TAG = "Paint_Tester_Save", SETTING_TAG = "Setting",
			SETTING_NAME = "name", SETTING_VALUE = "value",
			SITE = "http://mak001.x10.mx/PaintTester/", LINE_SEPARATOR = System
					.getProperty("line.separator"), FILE_SEPARATOR = System
					.getProperty("file.separator");

	public static final double VERSION = 0.3; // TODO

	public static Boot boot;
	public static CodeFrame codeFrame;
	public static CanvasFrame canvasFrame;

	public static void main(String[] args) {
		GlobalVariables.currentBot = Bot.POWERBOT;

		Updater.check();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Settings.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		canvasFrame = new CanvasFrame();
		canvasFrame.setVisible(true);

		codeFrame = new CodeFrame();
		codeFrame.setVisible(true);

		if (GlobalVariables.settingsFrame == null) {
			GlobalVariables.settingsFrame = new SettingsFrame();
		}
		GlobalVariables.settingsFrame.setVisible(true);

		GlobalVariables.closedTabs.addListDataListener(closedTabListener);
	}

	public static ImageIcon getIcon(String path) {
		try {
			return new ImageIcon(ImageIO.read(Boot.class.getClass()
					.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage getImage(String path) {
		try {
			return ImageIO
					.read(Boot.class.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setOpenedFile(File f) {
		GlobalVariables.openedFile = f;
		if (f != null) {
			canvasFrame.setTitle("Preview of "
					+ f.getName().replace(JAVA_EXTENSION, "")
							.replace(FILE_EXTENSION, ""));
		} else {
			canvasFrame.setTitle("Preview");
		}
	}

	private static CustomListListener closedTabListener = new CustomListListener() {
		@Override
		public void intervalAdded() {
			String s = "";
			for (int i = 0; i < GlobalVariables.closedTabs.getSize(); i++) {
				s = s + GlobalVariables.closedTabs.getElementAt(i).getName()
						+ ", ";
			}
			if (GlobalVariables.openClosedFileFrame == null) {
				GlobalVariables.openClosedFileFrame = new OpenClosedFileFrame();
			}
			GlobalVariables.openClosedFileFrame
					.setListData(GlobalVariables.closedTabs.toArray());
		}

		@Override
		public void intervalRemoved() {
			String s = "";
			for (int i = 0; i < GlobalVariables.closedTabs.getSize(); i++) {
				s = s + GlobalVariables.closedTabs.getElementAt(i).getName()
						+ ", ";
			}
			if (GlobalVariables.openClosedFileFrame == null) {
				GlobalVariables.openClosedFileFrame = new OpenClosedFileFrame();
			}
			GlobalVariables.openClosedFileFrame
					.setListData(GlobalVariables.closedTabs.toArray());
		}

		@Override
		public void contentsChanged() {
			String s = "";
			for (int i = 0; i < GlobalVariables.closedTabs.getSize(); i++) {
				s = s + GlobalVariables.closedTabs.getElementAt(i).getName()
						+ ", ";
			}
			if (GlobalVariables.openClosedFileFrame == null) {
				GlobalVariables.openClosedFileFrame = new OpenClosedFileFrame();
			}
			GlobalVariables.openClosedFileFrame
					.setListData(GlobalVariables.closedTabs.toArray());
		}
	};

}
