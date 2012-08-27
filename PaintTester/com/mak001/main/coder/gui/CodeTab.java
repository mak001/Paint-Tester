package com.mak001.main.coder.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

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
import com.mak001.main.coder.gui.frames.CodeFrame;
import com.mak001.main.coder.gui.syntax.SyntaxDocument;

@SuppressWarnings("serial")
public class CodeTab extends JScrollPane {
	private final JEditorPane pane;
	private final TabButton closeButton = new TabButton(this);
	private final SyntaxDocument doc;

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
			String[] paintInfo = getInfo(GlobalVariables.currentBot
					.getPainter());
			String[] mainInfo = getInfo(GlobalVariables.currentBot.getMain());
			boolean implement = true;

			StringBuilder sb = new StringBuilder();
			sb.append("import "
					+ GlobalVariables.currentBot.getMain().getCanonicalName()
					+ ";" + Boot.LINE_SEPARATOR);
			if (!GlobalVariables.currentBot
					.getMain()
					.getCanonicalName()
					.equals(GlobalVariables.currentBot.getPainter()
							.getCanonicalName())) {
				sb.append("import "
						+ GlobalVariables.currentBot.getPainter()
								.getCanonicalName() + ";" + Boot.LINE_SEPARATOR);
			} else {
				implement = false;
			}

			sb.append(paintInfo[0] + mainInfo[0] + Boot.LINE_SEPARATOR
					+ "public class " + getName() + " extends "
					+ GlobalVariables.currentBot.getMain().getSimpleName());
			if (implement) {
				sb.append(" implements "
						+ GlobalVariables.currentBot.getPainter()
								.getSimpleName());
				sb.append(" {" + Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR
						+ paintInfo[1] + mainInfo[1] + Boot.LINE_SEPARATOR
						+ "}");
			} else {
				sb.append(" {" + Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR
						+ paintInfo[1] + Boot.LINE_SEPARATOR + "}");
			}

			pane.setText(sb.toString());
		} else {
			pane.setText("public class " + getName() + " {"
					+ Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR
					+ Boot.LINE_SEPARATOR + "}");
		}
	}

	private String[] getInfo(Class<?> clazz) {
		Method[] regmethods = clazz.getMethods();
		Method[] decmethods = clazz.getDeclaredMethods();
		Method[] methods = throwOutDupes(combine(regmethods, decmethods));

		ArrayList<String> importArray = new ArrayList<String>();

		StringBuilder imports = new StringBuilder();
		StringBuilder main = new StringBuilder();

		for (Method m : methods) {

			if (m.isSynthetic() || m.getDeclaringClass().equals(Object.class)) {
				continue;
			}

			int mod = m.getModifiers();
			int classMods = clazz.getModifiers();

			if (Modifier.isAbstract(classMods)) {
				if (!Modifier.isAbstract(mod)) {
					continue;
				}
			}
			if (Modifier.isPrivate(mod)) { // connot inherite this method
				continue;
			}

			main.append("\t@Override" + Boot.LINE_SEPARATOR + "\t");

			if (Modifier.isPublic(mod)) {
				main.append("public ");
			}
			if (Modifier.isStatic(mod)) {
				main.append("static ");
			}
			if (Modifier.isProtected(mod)) {
				main.append("protected ");
			}
			if (Modifier.isSynchronized(mod)) {
				main.append("synchronized ");
			}
			if (Modifier.isTransient(mod)) {
				main.append("transient ");
			}
			if (Modifier.isStrict(mod)) {
				main.append("strictfp ");
			}
			if (Modifier.isVolatile(mod)) {
				main.append("volatile ");
			}

			main.append(m.getReturnType().getSimpleName() + " " + m.getName()
					+ "(");
			if (!importArray.contains(m.getReturnType().getName())
					&& !doc.isAKeyword(m.getReturnType().getName())) {
				imports.append("import " + m.getReturnType().getName() + ";"
						+ Boot.LINE_SEPARATOR);

				importArray.add(m.getReturnType().getName());
			}

			if (m.getGenericParameterTypes().length == 0) {
				main.append(") {" + Boot.LINE_SEPARATOR);
			}

			for (int i = 0; i < m.getGenericParameterTypes().length; i++) {

				Class<?> param = m.getParameterTypes()[i];

				String gen = param.getSimpleName().substring(0, 1)
						.toLowerCase();
				String camma = ") {" + Boot.LINE_SEPARATOR;
				if (i < m.getGenericParameterTypes().length - 1) {
					camma = ", ";
				}
				main.append(param.getSimpleName() + " " + gen + camma);
				if (!importArray.contains(param.getName())
						&& !doc.isAKeyword(param.getName())) {
					imports.append("import " + param.getName() + ";"
							+ Boot.LINE_SEPARATOR);

					importArray.add(param.getName());
				}
			}
			main.append(Boot.LINE_SEPARATOR + "\t}" + Boot.LINE_SEPARATOR
					+ Boot.LINE_SEPARATOR);
		}
		return new String[] { imports.toString(), main.toString() };
	}

	private Method[] throwOutDupes(Method[] combine) {
		ArrayList<Method> ms = new ArrayList<Method>();
		for (Method m : combine) {
			if (ms.isEmpty()) {
				ms.add(m);
			} else {
				boolean skip = false;
				for (Method meth : ms) {
					if (meth.getModifiers() == m.getModifiers()
							&& meth.getName().equals(m.getName())) {
						skip = true;
						continue;
					}
				}
				if (!skip) {
					ms.add(m);
				}
			}
		}
		return ms.toArray(new Method[ms.size()]);
	}

	private Method[] combine(Method[] m1, Method[] m2) {
		Method[] ret = new Method[m1.length + m2.length];
		for (int i = 0; i < m1.length; i++) {
			ret[i] = m1[i];
		}

		for (int i = 0; i < m2.length; i++) {
			ret[i + m1.length] = m2[i];
		}

		return ret;
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

	public String getFileContents() {
		return (String) reload()[1];
	}

	public String getTextContents() {
		return pane.getText();
	}

	public Object[] reload() {
		Object[] val = new Object[2];

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
			val[0] = true;
			val[1] = sb.toString();
			return val;
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
			}
			e.printStackTrace();
			val[0] = false;
			val[1] = "";
			return val;
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
		Object[] obj = reload();
		if ((Boolean) obj[0]) {
			pane.setText((String) obj[1]);
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
