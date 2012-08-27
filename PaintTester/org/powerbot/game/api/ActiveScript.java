package org.powerbot.game.api;

import com.mak001.main.setters.MainBase;

public abstract class ActiveScript extends MainBase {

	public ActiveScript() {
		setup();
	}

	protected abstract void setup();

	public final void stop() {
	}

	public void onStop() {
	}
}
