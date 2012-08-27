package com.mak001.main.setters;

import java.awt.Point;

public class MouseBase {

	protected static Point location = new Point(0, 0);
	protected static Point lastPressedLocation = new Point(-1, -1);

	protected static boolean isPressed;
	protected static boolean isPresent;

	public void setLocation(Point pos) {
		location = pos;
	}

	public void setPressed(boolean pressed) {
		isPressed = pressed;
		if (isPressed) {
			lastPressedLocation = location;
		}
	}

	public void setPresent(boolean present) {
		isPresent = present;
	}

}
