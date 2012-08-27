package org.powerbot.game.api.methods.input;

import java.awt.Point;

import com.mak001.main.setters.MouseBase;

public class Mouse extends MouseBase {

	public Mouse() {
	}

	/*
	 * Normal methods found in the bot
	 */
	public static Point getLocation() {
		return location;
	}

	public static int getX() {
		return (int) location.getX();
	}

	public static int getY() {
		return (int) location.getY();
	}

	public static boolean isPressed() {
		return isPressed;
	}

	public static boolean isPresent() {
		return isPresent;
	}

}
