package com.mak001.main.coder.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;

import org.w3c.dom.Element;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.Settings;
import com.mak001.main.Settings.Types;
import com.mak001.main.coder.ClassGenerator;
import com.mak001.main.coder.gui.frames.CodeFrame;
import com.mak001.main.coder.gui.syntax.SyntaxDocument;

@SuppressWarnings("serial")
public class CodeTab extends JScrollPane {
	private final JEditorPane pane;
	private final TabButton closeButton = new TabButton(this);
	
	
	public final SyntaxDocument doc;

	private String path = null;
	private final Element element;

	public CodeTab(String path, boolean opened) {
		this(Boot.codeFrame, path, opened);
	}

	public CodeTab(CodeFrame cf, String path, boolean opened) {
		element = GlobalVariables.xmlWriter.getDocument().createElement(
				Boot.FILE_TAG);
		pane = new JEditorPane();
		doc = new SyntaxDocument();
		StyledEditorKit editorKit = new StyledEditorKit() {
			@Override
			public Document createDefaultDocument() {
				return doc;
			}
		};
		pane.setEditorKitForContentType("text/java", editorKit);
		pane.setContentType("text/java");

		boolean wasEmpty = GlobalVariables.codeTabs.isEmpty();
		setInitialText(wasEmpty);

		GlobalVariables.codeTabs.add(this);

		setViewportView(pane);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		if (path.contains(Boot.FILE_SEPARATOR)) {
			initPath(path);
		} else {
			setName(path);
		}

		if (opened) {
			if (cf.getAddTabIndex() < 0) {
				cf.tabbedPane.add(this, 0);
				cf.tabbedPane.setTabComponentAt(0, closeButton);
			} else {
				cf.tabbedPane.add(this, cf.getAddTabIndex() - 2);
				cf.tabbedPane.setTabComponentAt(cf.getAddTabIndex() - 2,
						closeButton);
			}
		} else {
			GlobalVariables.closedTabs.add(this);
		}
		closeButton.updateName();

		element.setAttribute(Boot.PATH_TAG, path);
		element.setAttribute(Boot.OPEN_TAG, "" + opened);
	}

	private void setInitialText(boolean wasEmpty) {
		if (wasEmpty) {
			pane.setText(ClassGenerator.getPrimaryClass(this));
		} else {
			pane.setText(ClassGenerator.getSecondaryClasses(this));
		}
	}

	public void initPath(String path) {
		if (Settings.getSettingBoolean(Types.COPY_IMPORTED_FILES)) {
			initPath(path, true);
		}
		initPath(path, false);
	}

	public void initPath(String path, boolean delete) {
		this.path = path;
		setName(path
				.replace(
						path.subSequence(0,
								path.lastIndexOf(Boot.FILE_SEPARATOR)), "")
				.replace(Boot.JAVA_EXTENSION, "")
				.replace(Boot.FILE_EXTENSION, "")
				.replace(Boot.FILE_SEPARATOR, ""));
		element.setAttribute(Boot.PATH_TAG, path);

		setTextContents(getFileContents());

		if (delete) {
			new File(path).delete();
		}

	}

	@Override
	public boolean equals(Object o) {
		if (o != null) {
			if (o instanceof CodeTab) {
				if (((CodeTab) o).getName().equals(getName())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "CodeTab:" + getName() + " [File:" + path + ", open:" + isOpen()
				+ "]";
	}

	public String getTextContents() {
		return pane.getText();
	}

	public String getFileContents() {
		try {
			if (path == null) {
				initPath(GlobalVariables.openedFile.getPath().replace(
						GlobalVariables.getOpenedFileName(), "")
						+ getName() + Boot.JAVA_EXTENSION);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(path))));
			String s;
			StringBuilder sb = new StringBuilder();
			while ((s = br.readLine()) != null) {
				sb.append(s + Boot.LINE_SEPARATOR);
			}
			br.close();
			setTextContents(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
			}
			e.printStackTrace();
			return null;
		}
	}

	public String getDestinationPath() {
		return path.replace(getName(), "").replace(Boot.JAVA_EXTENSION, "")
				.replace(Boot.FILE_EXTENSION, "");
	}

	public void setTextContents(String fileContents) {
		pane.setText(fileContents);
	}

	public void displayFileContents() {
		String s = getFileContents();
		if (s != null) {
			pane.setText(s);
		}
	}

	public Element getElement() {
		return element;
	}

	public boolean isOpen() {
		return !GlobalVariables.closedTabs.contains(this);
	}

	public void setClosed(boolean close) {
		JTabbedPane tp = Boot.codeFrame.tabbedPane;
		if (close) {
			if (isOpen()) {
				int index = tp.indexOfTabComponent(this);
				if (index != -1) {
					tp.remove(index);
				}
				GlobalVariables.closedTabs.add(this);
				if (index > 1) {
					tp.setSelectedIndex(0);
				}
			}
		} else {
			if (!isOpen()) {
				int index = Boot.codeFrame.getAddTabIndex() - 1;
				if (index > 0) {
					tp.add(this, index);
					tp.setTabComponentAt(index, closeButton);
				} else {
					tp.add(this, 0);
					tp.setTabComponentAt(0, closeButton);
				}

				GlobalVariables.closedTabs.remove(this);
			}
		}
		element.setAttribute(Boot.OPEN_TAG, "" + !close);
	}

}
