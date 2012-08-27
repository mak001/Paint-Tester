package com.mak001.main.coder.gui.frames;

import java.awt.Desktop;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class CreditFrame extends JEditorPane {

	public CreditFrame() {
		super("text/html", "");

		JLabel label = new JLabel();

		setText("<table>"
				+ "<tr><td>mak001</td><td>-</td><td>the base code</td></tr>"
				+ "<tr><td>import</td><td>-</td><td>moral support</td></tr>"
				+ "<tr><td><a href=\"http://codewall.blogspot.com/2011/01/syntax-highlighting-in-java.html\">codewall</a></td><td>-</td><td>Java highlighting</td></tr>"
				+ "</table>");
		// TODO
		addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					try {
						Desktop.getDesktop().browse(e.getURL().toURI());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
		setEditable(false);
		setBackground(label.getBackground());
	}

}
