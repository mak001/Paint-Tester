package com.mak001.main.coder.gui.frames;

import java.awt.Desktop;
import java.awt.Dimension;
import java.net.URI;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import com.mak001.main.Boot;

@SuppressWarnings("serial")
public class HelpFrame extends JFrame {

	private final JList<Help> list;
	private final JEditorPane editorPane;
	private final Document doc;

	public HelpFrame() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setSize(new Dimension(650, 420));
		setTitle("Help");

		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setBorder(BorderFactory.createEmptyBorder());
		HTMLEditorKit kit = new HTMLEditorKit();
		kit.getStyleSheet().importStyleSheet(
				Boot.class.getResource("/com/mak001/main/html/style.css"));
		editorPane.setEditorKit(kit);
		doc = kit.createDefaultDocument();
		editorPane.setDocument(doc);
		editorPane.addHyperlinkListener(linkListener);

		list = new JList<Help>();
		list.addListSelectionListener(listListener);
		list.setListData(Help.values());
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(editorPane);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		springLayout.putConstraint(SpringLayout.NORTH, list, 10,
				SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, list, 10,
				SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, list, -10,
				SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, list, 100,
				SpringLayout.WEST, getContentPane());
		getContentPane().add(list);

		springLayout.putConstraint(SpringLayout.NORTH, jsp, 10,
				SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, jsp, 10,
				SpringLayout.EAST, list);
		springLayout.putConstraint(SpringLayout.SOUTH, jsp, -10,
				SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, jsp, -10,
				SpringLayout.EAST, getContentPane());
		getContentPane().add(jsp);

	}

	private enum Help {
		GENERAL("General", "/com/mak001/main/html/General.html"), FILE_MENU(
				"File menu", "/com/mak001/main/html/File.html"), EDIT_MENU(
				"Edit menu", "/com/mak001/main/html/Edit.html"), HELP_MENU(
				"Help menu", "/com/mak001/main/html/Help.html"), CODING(
				"Coding", "/com/mak001/main/html/Coding.html");

		private final String name;
		private final String path;

		Help(String name, String path) {
			this.name = name;
			this.path = Boot.class.getResource(path).toString();
		}

		public String getName() {
			return name;
		}

		public String getRealName() {
			return name.split(" ")[0];
		}

		public String getPage() {
			return path;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	private final ListSelectionListener listListener = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Help help = list.getSelectedValue();
			for (Help h : Help.values()) {
				if (h.equals(help)) {
					try {
						editorPane.setPage(new URL(help.getPage() + "#top"));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	};

	private final HyperlinkListener linkListener = new HyperlinkListener() {

		@Override
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				String url = e.getDescription();
				if (url.startsWith("#")) {
					String name = list.getSelectedValue().getName();
					for (Help help : Help.values()) {
						if (help.getName().equals(name)) {
							url = help.getPage() + url;
						}
					}
					try {
						editorPane.setPage(new URL(url));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (url.startsWith("local/")) {
					String name = url.replace("local/", "");
					if (name.contains("#")) {
						String[] args = name.split("#");

						for (Help h : Help.values()) {
							if (h.getName().equals(args[0])
									|| h.getRealName().equals(args[0])) {
								list.setSelectedValue(h, true);
								try {
									editorPane.setPage(new URL(h.getPage()
											+ "#" + args[1]));
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}

					} else {
						for (Help h : Help.values()) {
							if (h.getName().equals(name)
									|| h.getRealName().equals(name)) {
								list.setSelectedValue(h, true);
							}
						}
					}
				} else {
					try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		}
	};
}
