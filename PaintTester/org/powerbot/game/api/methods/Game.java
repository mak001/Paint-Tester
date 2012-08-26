package org.powerbot.game.api.methods;

import java.awt.Dimension;

import com.mak001.main.setters.GameBase;

/**
 * A utility for the manipulation of the game.
 * 
 * @author Timer
 */
public class Game extends GameBase {

	/**
	 * @return <tt>true</tt> if this client instance is logged in; otherwise
	 *         <tt>false</tt>.
	 */
	public static boolean isLoggedIn() {
		return true;
	}

	public static Dimension getDimensions() {
		return dim;
	}

}