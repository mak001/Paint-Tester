package com.mak001.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mak001.main.coder.Bot;
import com.mak001.main.coder.Compiler;
import com.mak001.main.coder.gui.CodeTab;
import com.mak001.main.xml.XmlWriter;

public class Settings {

	private static HashMap<Types, Element> settingElements = new HashMap<Types, Element>();

	public static void init() throws ParserConfigurationException, IOException,
			SAXException {
		if (GlobalVariables.xmlWriter == null) {
			GlobalVariables.xmlWriter = new XmlWriter();
		}
		for (Types t : Types.values()) {
			Element e = GlobalVariables.xmlWriter.getDocument().createElement(
					Boot.SETTING_TAG);
			settingElements.put(t, e);
			e.setAttribute(t.getName(), t.getDefaultValue());

			DEFAULTS.put(t, t.getDefaultValue());
		}
	}

	public enum Types {
		COPY_IMPORTED_FILES("copy_imported", FALSE), USE_BIN_FOLDER(
				"bin_folder", TRUE), USE_SRC_FOLDER("src_folder", TRUE), BOT(
				"bot", Bot.POWERBOT.getName());
		private final String value;
		private final String defaultValue;

		private Types(String s, String df) {
			value = s;
			defaultValue = df;
		}

		public String getName() {
			return value;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}

	public static final String TRUE = "true";
	public static final String FALSE = "false";

	private final static HashMap<Types, String> DEFAULTS = new HashMap<Types, String>();

	private static HashMap<Types, String> settings = DEFAULTS;

	public static HashMap<Types, String> getDefaults() {
		return DEFAULTS;
	}

	public static String put(Types key, String value) {
		settingElements.get(key).setAttribute(key.getName(), value);
		switch (key) {
		case COPY_IMPORTED_FILES:
			if (value.equalsIgnoreCase(TRUE)
					&& GlobalVariables.openedFile != null) {
				if (!GlobalVariables.codeTabs.isEmpty()) {
					for (CodeTab ct : GlobalVariables.codeTabs) {
						String path = GlobalVariables.openedFile.getPath()
								.substring(
										0,
										GlobalVariables.openedFile.getPath()
												.lastIndexOf(
														Boot.FILE_SEPARATOR));
						if (!ct.getDestinationPath().equals(
								GlobalVariables.openedFile.getPath())) {
							ct.initPath(path + ct.getName()
									+ Boot.JAVA_EXTENSION, false);
						}
					}
				}
			}
			break;
		case USE_BIN_FOLDER:
			if (GlobalVariables.openedFile != null) {
				if (value.equals(TRUE)) {
					File dir = new File(GlobalVariables.openedFile.getPath());
					for (File file : dir.listFiles()) {
						if (file.getName().contains(".class")) {
							file.delete();
						}
					}
				} else {
					File dir = new File(GlobalVariables.openedFile.getPath()
							+ "bin" + Boot.FILE_SEPARATOR);
					dir.delete();

				}
				for (CodeTab ct : GlobalVariables.codeTabs) {
					Compiler.compile(ct);
				}
			}
			break;
		case BOT:
			GlobalVariables.currentBot = Bot.valueOf(value.toUpperCase());
			// TODO
			break;
		default:
			break;
		}
		return settings.put(key, value);
	}

	public static String put(Types key, boolean value) {
		return put(key, getBoolean(value));
	}

	public static Set<Entry<Types, String>> getSettings() {
		return settings.entrySet();
	}

	public static String getSetting(Types key) {
		return settings.get(key);
	}

	public static boolean getSettingBoolean(Types key) {
		return getBoolean(settings.get(key));
	}

	public static Element getElement(Types key) {
		return settingElements.get(key);
	}

	private static String getBoolean(boolean bol) {
		if (bol) {
			return TRUE;
		}
		return FALSE;
	}

	private static boolean getBoolean(String bol) {
		if (bol.equals(TRUE)) {
			return true;
		}
		return false;
	}

}
