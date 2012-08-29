package com.mak001.main.coder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.CodeTab;

public class ClassGenerator {

	public static String getPrimaryClass(CodeTab ct) {
		return getPrimaryClass(ct, GlobalVariables.currentBot);
	}

	public static String getPrimaryClass(CodeTab ct, Bot bot) {
		String[] paintInfo = getInfo(ct, bot.getPainter());
		String[] mainInfo = getInfo(ct, bot.getMain());
		boolean implement = true;

		StringBuilder sb = new StringBuilder();
		sb.append("import " + bot.getMain().getCanonicalName() + ";"
				+ Boot.LINE_SEPARATOR);
		if (!bot.getMain().getCanonicalName()
				.equals(bot.getPainter().getCanonicalName())) {
			sb.append("import " + bot.getPainter().getCanonicalName() + ";"
					+ Boot.LINE_SEPARATOR);
		} else {
			implement = false;
		}

		sb.append(paintInfo[0] + mainInfo[0] + Boot.LINE_SEPARATOR
				+ "public class " + ct.getName() + " extends "
				+ bot.getMain().getSimpleName());
		if (implement) {
			sb.append(" implements " + bot.getPainter().getSimpleName());
			sb.append(" {" + Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR
					+ paintInfo[1] + mainInfo[1] + Boot.LINE_SEPARATOR + "}");
		} else {
			sb.append(" {" + Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR
					+ paintInfo[1] + Boot.LINE_SEPARATOR + "}");
		}
		return sb.toString();
	}

	public static String getSecondaryClasses(CodeTab ct) {
		return "public class " + ct.getName() + " {" + Boot.LINE_SEPARATOR
				+ Boot.LINE_SEPARATOR + Boot.LINE_SEPARATOR + "}";
	}

	public static String formatToBot(Bot bot, String text) {
		for (Bot b : Bot.values()) {
			text.replaceAll(b.getMain().getCanonicalName(), bot.getMain()
					.getCanonicalName());
			text.replace(b.getPainter().getCanonicalName(), bot.getPainter().getCanonicalName());
			text.replace(b.getMain().getSimpleName(), bot.getMain().getSimpleName());
			text.replace(b.getPainter().getSimpleName(), bot.getPainter().getSimpleName());
		}
		return text;
	}

	private static String[] getInfo(CodeTab ct, Class<?> clazz) {
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
					&& !ct.doc.isAKeyword(m.getReturnType().getName())) {
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
						&& !ct.doc.isAKeyword(param.getName())) {
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

	private static Method[] throwOutDupes(Method[] combine) {
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

	private static Method[] combine(Method[] m1, Method[] m2) {
		Method[] ret = new Method[m1.length + m2.length];
		for (int i = 0; i < m1.length; i++) {
			ret[i] = m1[i];
		}

		for (int i = 0; i < m2.length; i++) {
			ret[i + m1.length] = m2[i];
		}

		return ret;
	}

}
