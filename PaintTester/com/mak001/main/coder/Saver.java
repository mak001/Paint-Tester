package com.mak001.main.coder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.w3c.dom.Element;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.Settings;
import com.mak001.main.Settings.Types;
import com.mak001.main.coder.gui.CodeTab;

public class Saver {

	private static ArrayList<CodeTab> savedCodetabs = new ArrayList<CodeTab>();

	public static void save() {
		if (GlobalVariables.openedFile != null) {
			if (!writeXML()) {
				JOptionPane.showMessageDialog(Boot.codeFrame,
						"Could not write file!", "Error!",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			saveAs();
		}
	}

	public static void saveAs() {
		if (GlobalVariables.saveDialog.showSaveDialog(Boot.codeFrame) == JFileChooser.APPROVE_OPTION) {
			File f = GlobalVariables.saveDialog.getSelectedFile();
			if (!f.getName().endsWith(Boot.FILE_EXTENSION)) {
				f = new File((new StringBuilder()).append(f.getPath())
						.append(Boot.FILE_EXTENSION).toString());
			}
			if (f.exists()
					&& JOptionPane
							.showConfirmDialog(
									Boot.codeFrame,
									(new StringBuilder())
											.append("Do you really want to overwrite ")
											.append(f.getName()).append("?")
											.toString(), "Confirm", 0) != 0) {
				return;
			} else {
				f.delete();
			}
			if (GlobalVariables.xmlWriter.getDocument().getChildNodes() == null
					|| GlobalVariables.xmlWriter.getDocument().getChildNodes()
							.getLength() == 0) {
				if (ceateFirstCommit(f)) {
					Boot.setOpenedFile(f);
					for (CodeTab ct : GlobalVariables.codeTabs) {
						File file = new File(GlobalVariables.getSRCPath());
						if (!file.exists()) {
							file.mkdir();
						}

						File binDir = new File(GlobalVariables.getBinPath());
						if (!binDir.exists()) {
							binDir.mkdir();
						} else {
							for (File fi : binDir.listFiles()) {
								fi.delete();
							}
						}

						GlobalVariables.currentMainClass = null;
						GlobalVariables.currentPaintClass = null;
						GlobalVariables.currentPaintBase = null;

						if (!write(ct.getName() + Boot.JAVA_EXTENSION,
								ct.getTextContents())) {
							return;
						}
					}

					for (CodeTab ct : GlobalVariables.codeTabs) {
						ct.reload();
						Compiler.compile(ct);
					}
				} else {
					JOptionPane.showMessageDialog(Boot.codeFrame,
							"Could not write file!", "Error!",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {

				try {
					GlobalVariables.xmlWriter.setFile(f);
					GlobalVariables.xmlWriter.commit();
					Boot.setOpenedFile(f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean write(String name, String data) {
		File file = new File(GlobalVariables.getSRCPath() + name);
		if (!file.getAbsolutePath().contains("null")) {
			try {
				BufferedWriter wr = new BufferedWriter(new FileWriter(file));
				wr.write(data);
				wr.flush();
				wr.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return true;
		}
	}

	private static boolean writeXML() {
		try {
			for (CodeTab ct : GlobalVariables.codeTabs) {
				String path = null;
				if (Settings.getSetting(Types.USE_SRC_FOLDER).equals(
						Settings.FALSE)) {
					path = GlobalVariables.openedFile.getPath().replace(
							GlobalVariables.openedFile.getName(), "")
							+ ct.getName() + Boot.JAVA_EXTENSION;
				} else {
					path = GlobalVariables.openedFile.getPath().replace(
							GlobalVariables.openedFile.getName(), "")
							+ Boot.FILE_SEPARATOR
							+ "src"
							+ Boot.FILE_SEPARATOR
							+ ct.getName() + Boot.JAVA_EXTENSION;
				}

				if (!savedCodetabs.contains(ct)) {
					Element root = GlobalVariables.xmlWriter.getDocument()
							.getDocumentElement();

					GlobalVariables.xmlWriter
							.appendChild(root, ct.getElement());

					ct.initPath(path);
				}
				write(ct.getName() + Boot.JAVA_EXTENSION, ct.getTextContents());
			}

			GlobalVariables.clearClasses();

			File binDir = new File(GlobalVariables.getBinPath());
			if (!binDir.exists()) {
				binDir.mkdir();
			} else {
				for (File fi : binDir.listFiles()) {
					fi.delete();
				}
			}

			GlobalVariables.currentMainClass = null;
			GlobalVariables.currentPaintClass = null;
			GlobalVariables.currentPaintBase = null;

			for (CodeTab ct : GlobalVariables.codeTabs) {
				ct.reload();
				Compiler.compile(ct);
			}

			GlobalVariables.xmlWriter.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean ceateFirstCommit(File file) {
		try {
			GlobalVariables.xmlWriter.setFile(file);
			Element root = GlobalVariables.xmlWriter
					.getElement(Boot.TOP_LEVEL_TAG);
			GlobalVariables.xmlWriter.appendRoot(root);

			for (Types t : Types.values()) {
				root.appendChild(Settings.getElement(t));
			}

			for (CodeTab ct : GlobalVariables.codeTabs) {
				GlobalVariables.xmlWriter.appendChild(root, ct.getElement());

				savedCodetabs.add(ct);
			}

			GlobalVariables.xmlWriter.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void reset() {
		savedCodetabs.clear();
	}

	public static boolean add(CodeTab ct) {
		return savedCodetabs.add(ct);
	}

}
