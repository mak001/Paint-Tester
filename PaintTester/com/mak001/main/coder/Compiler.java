package com.mak001.main.coder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.CodeTab;
import com.mak001.main.coder.gui.frames.DiagnosticFrame;

public class Compiler {
	public static void compile(CodeTab t) {
		try {
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

			File bin = new File(GlobalVariables.getBinPath());
			if (!bin.exists()) {
				bin.mkdir();
			}

			List<String> optionList = new ArrayList<String>();

			optionList
					.addAll(Arrays.asList("-d", GlobalVariables.getBinPath()));

			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

			Iterable<? extends JavaFileObject> compilationUnits = Arrays
					.asList(getJavaFileObject(t));

			if (compiler.getTask(null, null, diagnostics, optionList, null,
					compilationUnits).call()) {

			} else {
				new DiagnosticFrame(diagnostics.getDiagnostics())
						.setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Loader.loadCode();
	}

	private static String readFile(CodeTab ct) {
		String c = "";
		try {

			URL item = new File(GlobalVariables.getSRCPath() + ct.getName()
					+ Boot.JAVA_EXTENSION).toURI().toURL();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					item.openStream()));

			String ins;
			while ((ins = in.readLine()) != null) {
				c = c + ins + Boot.LINE_SEPARATOR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	private static JavaFileObject getJavaFileObject(CodeTab ct) {
		JavaFileObject so = null;
		try {
			so = new JavaSourceFromString(ct.getName(), readFile(ct));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return so;
	}

	private static class JavaSourceFromString extends SimpleJavaFileObject {
		final String code;

		JavaSourceFromString(String name, String code) {
			super(URI.create("string:///" + name.replace('.', '/')
					+ Kind.SOURCE.extension), Kind.SOURCE);
			this.code = code;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return code;
		}
	}

}
