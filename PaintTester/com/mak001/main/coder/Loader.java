package com.mak001.main.coder;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.CodeTab;
import com.mak001.main.setters.MainBase;
import com.mak001.main.setters.PaintBase;
import com.mak001.main.xml.XmlReader;

public class Loader {

	public static boolean load(File f) {
		try {

			GlobalVariables.xmlReader = new XmlReader(f);
			if (GlobalVariables.xmlReader.read()) {
				Boot.setOpenedFile(f);

				GlobalVariables.clearClasses();
				for (CodeTab t : GlobalVariables.codeTabs) {
					Compiler.compile(t);
					Object[] o = t.reload();
					if ((Boolean) o[0] == true) {
						t.setTextContents((String) o[1]);
					}
				}
				System.gc();
				return true;
			} else {
				System.gc();
				return false;
			}

		} catch (Exception e) {
			System.gc();
			e.printStackTrace();
			return false;
		}
	}

	public static void loadExtenal(File file) {
		new CodeTab(file.getPath(), true);
	}

	@SuppressWarnings("resource")
	public static void loadCode() {
		try {
			ClassLoader loader;
			File dir = new File(GlobalVariables.getBinPath());
			loader = new URLClassLoader(new URL[] { dir.toURI().toURL() });

			Class<?> c;

			for (File f : dir.listFiles()) {
				c = loader.loadClass(getName(f));
				loadClass(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getName(File f) {
		return f.getName().replace(
				f.getName().substring(f.getName().indexOf("."),
						f.getName().length()), "");
	}

	@SuppressWarnings("unchecked")
	private static void loadClass(Class<?> c) {
		GlobalVariables.classes.add(c);
		if (GlobalVariables.currentBot.getMain().isAssignableFrom(c)) {
			GlobalVariables.currentMainClass = (Class<MainBase>) c;
			try {
				switch (GlobalVariables.currentBot) {
				case RUNEDREAM:
					((org.runedream.api.Script) c.newInstance()).onStart();
					break;
				case POWERBOT:
					c.newInstance();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (MouseListener.class.isAssignableFrom(c)) {
			try {
				GlobalVariables.mouseListeners.add((MouseListener) c
						.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (MouseMotionListener.class.isAssignableFrom(c)) {
			try {
				GlobalVariables.mouseMotionListeners
						.add((MouseMotionListener) c.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (KeyListener.class.isAssignableFrom(c)) {
			try {
				GlobalVariables.keyListeners.add((KeyListener) c.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (GlobalVariables.currentBot.getPainter().isAssignableFrom(c)) {
			GlobalVariables.currentPaintClass = (Class<PaintBase>) c;
		}
	}
}
