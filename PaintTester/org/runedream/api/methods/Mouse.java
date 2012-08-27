package org.runedream.api.methods;

import java.awt.Point;

import com.mak001.main.setters.MouseBase;

/**
 * Mouse input methods.
 * 
 * @author Vulcan
 */
public final class Mouse extends MouseBase {

	/**
	 * Gets the location of the mouse.
	 * 
	 * @return The location of the mouse.
	 */
	public static Point getLocation() {
		return location;
	}

	/**
	 * Gets the location of the last mouse press.
	 * 
	 * @return The location of the last mouse press, or a Point(-1, -1) if none
	 *         exists.
	 */
	public static Point getPressLocation() {
		return lastPressedLocation;
	}

	/**
	 * Gets whether the mouse is on the game canvas or not.
	 * 
	 * @return <tt>true</tt> if present; otherwise <tt>false</tt>.
	 */
	public static boolean isPresent() {
		return isPresent;
	}

	/**
	 * Gets whether the mouse has a button (left, right, or wheel) pressed or
	 * not.
	 * 
	 * @return <tt>true</tt> if a button is pressed; otherwise <tt>false</tt>.
	 */
	public static boolean isPressed() {
		return isPressed;
	}
}
