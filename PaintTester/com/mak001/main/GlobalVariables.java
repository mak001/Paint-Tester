package com.mak001.main;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import com.mak001.main.Settings.Types;
import com.mak001.main.coder.Bot;
import com.mak001.main.coder.gui.CodeTab;
import com.mak001.main.coder.gui.CustomList;
import com.mak001.main.coder.gui.frames.HelpFrame;
import com.mak001.main.coder.gui.frames.OpenClosedFileFrame;
import com.mak001.main.coder.gui.frames.SettingsFrame;
import com.mak001.main.setters.MainBase;
import com.mak001.main.setters.PaintBase;
import com.mak001.main.xml.XmlReader;
import com.mak001.main.xml.XmlWriter;

public class GlobalVariables {

	public static JFileChooser saveDialog;
	public static JFileChooser loadDialog;
	public static JFileChooser importDialog;

	public static OpenClosedFileFrame openClosedFileFrame;
	public static HelpFrame helpFrame;
	public static SettingsFrame settingsFrame;

	public static XmlWriter xmlWriter;
	public static XmlReader xmlReader;

	public static File openedFile = null;

	public static ArrayList<CodeTab> codeTabs = new ArrayList<CodeTab>();
	public static CustomList<CodeTab> closedTabs = new CustomList<CodeTab>();

	public static ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	public static Class<MainBase> currentMainClass;
	public static Class<PaintBase> currentPaintClass;
	public static PaintBase currentPaintBase;
	public static Bot currentBot;

	public static ArrayList<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	public static ArrayList<MouseMotionListener> mouseMotionListeners = new ArrayList<MouseMotionListener>();
	public static ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();

	public static String getBinPath() {
		if (Settings.getSettingBoolean(Types.USE_BIN_FOLDER)) {
			return getPath() + "bin" + Boot.FILE_SEPARATOR;
		}
		return getPath();
	}

	public static String getSRCPath() {
		if (Settings.getSettingBoolean(Types.USE_SRC_FOLDER)) {
			return getPath() + "src" + Boot.FILE_SEPARATOR;
		}
		return getPath();
	}

	public static String getOpenedFileName() {
		return openedFile
				.getPath()
				.replace(
						openedFile.getPath().subSequence(
								0,
								openedFile.getPath().lastIndexOf(
										Boot.FILE_SEPARATOR)), "")
				.replace(Boot.FILE_SEPARATOR, "");
	}

	private static String getPath() {
		return openedFile.getPath().replace(getOpenedFileName(), "");
	}

	public static void clearClasses() {
		classes.clear();
		mouseListeners.clear();
		mouseMotionListeners.clear();
		keyListeners.clear();
	}

}
