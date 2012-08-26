package com.mak001.main;

import java.awt.Desktop;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Updater {

	private static double version = -1;
	private static String jarLink;
	private static String sourceLink;

	public static void check() {
		getLatest(readSite());

		if (version != -1) {
			if (JOptionPane.showConfirmDialog(Boot.codeFrame, "A new version ("
					+ version + ") is available." + Boot.LINE_SEPARATOR
					+ "Do you wish to update?", "Update?",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				JLabel label = new JLabel();
				label.getFont();

				JEditorPane ep = new JEditorPane("text/html", "<html><body>"
						+ "<a href=\"" + jarLink + "\">Jar (v " + version
						+ ")</a>" + "<br />" + "<a href=\"" + sourceLink
						+ "\">Source (v " + version + ")</a>"
						+ "</body></html>");

				ep.addHyperlinkListener(new HyperlinkListener() {
					@Override
					public void hyperlinkUpdate(HyperlinkEvent e) {
						if (e.getEventType().equals(
								HyperlinkEvent.EventType.ACTIVATED)) {
							try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				ep.setEditable(false);
				ep.setBackground(label.getBackground());
				JOptionPane.showMessageDialog(null, ep, "Update?",
						JOptionPane.QUESTION_MESSAGE);
			}
		}

	}

	private static NodeList readSite() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(
					"http://mak001.x10.mx/PaintTester/xml_storage/updates.xml")
					.openStream());
			doc.getDocumentElement().normalize();
			return doc.getElementsByTagName("update");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void getLatest(NodeList nodeList) {
		if (nodeList == null) {
			return;
		}
		for (int i = 0; i < nodeList.getLength(); i++) {
			double ver = -1;
			String jlink = null;
			String slink = null;
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				ver = Double.parseDouble(getTagValue("version", element));
				jlink = getTagValue("jar", element);
				slink = getTagValue("source", element);
			}
			if (Boot.VERSION < ver && version < ver) {
				version = ver;
				sourceLink = slink;
				jarLink = jlink;
			}
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = nlList.item(0);

		return nValue.getNodeValue();
	}

}
