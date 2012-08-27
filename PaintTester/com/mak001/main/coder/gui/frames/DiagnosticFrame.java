package com.mak001.main.coder.gui.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Locale;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import com.mak001.main.Boot;

@SuppressWarnings("serial")
public class DiagnosticFrame extends JFrame {

	public DiagnosticFrame(
			List<Diagnostic<? extends JavaFileObject>> diagnostics) {
		Dimension d = new Dimension(500, 300);
		setSize(d);
		setTitle("Compilation Error(s) have occurred");
		JEditorPane editorPane = new JEditorPane();
		getContentPane().add(editorPane, BorderLayout.CENTER);

		editorPane.setEditable(false);
		editorPane.setFont(new Font(editorPane.getFont().getName(), Font.PLAIN,
				16));

		StringBuilder sb = new StringBuilder();

		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
			String className = diagnostic.getSource().getName()
					.replace("string:////", "")
					.replace(" from JavaSourceFromString", "");
			System.out.println("Class name: " + className);
			sb.append(String.format("Error on line %d in %s",
					diagnostic.getLineNumber(), className.replace("/", "")));
			sb.append(Boot.LINE_SEPARATOR
					+ diagnostic
							.getMessage(Locale.US)
							.replace("string:///", "")
							.replace(Kind.SOURCE.extension, "")
							.replace(diagnostic.getSource().getName(),
									className)
							.replace(
									className.replace(".java", "").replace("/",
											"")
											+ ":"
											+ diagnostic.getLineNumber()
											+ ":", ""));
			sb.append(Boot.LINE_SEPARATOR + "------------------"
					+ Boot.LINE_SEPARATOR);
		}
		editorPane.setText(sb.toString());

	}

}
