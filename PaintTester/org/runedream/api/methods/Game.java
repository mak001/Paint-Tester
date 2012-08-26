package org.runedream.api.methods;

import java.awt.Dimension;

import com.mak001.main.setters.GameBase;

public class Game extends GameBase {

	/**
	 * Gets the size of the game canvas.
	 * 
	 * @return The game's dimension.
	 */
	public static Dimension getCanvasSize() {
		return dim;
	}

}
