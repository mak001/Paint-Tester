package com.mak001.main.xml;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.Settings;
import com.mak001.main.Settings.Types;
import com.mak001.main.coder.Saver;
import com.mak001.main.coder.gui.CodeTab;

public class XmlReader {

	private final File file;

	public XmlReader(String path) {
		this(new File(path));
	}

	public XmlReader(File file) {
		this.file = file;
	}

	public boolean read() {
		try {
			XmlWriter writer = GlobalVariables.xmlWriter;
			GlobalVariables.xmlWriter.setFile(file);

			Document doc = writer.getDocument();
			doc.getDocumentElement().normalize();
			NodeList fileList = doc.getElementsByTagName(Boot.FILE_TAG);
			for (int i = 0; i < fileList.getLength(); i++) {
				Node node = fileList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String path = element.getAttribute(Boot.PATH_TAG);
					String open = element.getAttribute(Boot.OPEN_TAG);
					CodeTab ct = new CodeTab(path, fromString(open));
					// ct.displayFileContents();
					Saver.add(ct);
				}
			}

			NodeList settingsList = doc.getElementsByTagName(Boot.SETTING_TAG);
			for (int i = 0; i < settingsList.getLength(); i++) {
				Node node = settingsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String name = element.getAttribute(Boot.SETTING_NAME);
					String value = element.getAttribute(Boot.SETTING_VALUE);
					for (Types t : Types.values()) {
						if (name.equals(t.getName())) {
							Settings.put(t, value);
						}
					}
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean fromString(String s) {
		if (s.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

}
