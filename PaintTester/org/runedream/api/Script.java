package org.runedream.api;

import com.mak001.main.setters.MainBase;
import com.mak001.main.setters.PaintBase;

/**
 * Abstract class to be extended by scripts.
 * 
 * @author Vulcan
 */
public abstract class Script extends MainBase implements PaintBase {
	/**
	 * Method for optional inheritance which is called after the initialization
	 * of a script.
	 * 
	 * @return <tt>true</tt> to start the script; <tt>false</tt> will terminate
	 *         initialization.
	 */
	public boolean onStart() {
		return true;
	}

	/**
	 * Looping method called repeatedly on a running script. Script actions
	 * should be located here.
	 * 
	 * @return The value to sleep before looping again.
	 */
	public abstract int loop();

	/**
	 * Method for optional inheritance which is called upon the termination of a
	 * script.
	 */
	public void onStop() {

	}
}