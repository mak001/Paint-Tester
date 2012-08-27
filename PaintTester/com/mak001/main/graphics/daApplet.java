package com.mak001.main.graphics;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class daApplet extends Applet {
	Image offScreenBuffer;

	@Override
	public void update(Graphics g) {
		Graphics gr;
		if (offScreenBuffer == null
				|| (!(offScreenBuffer.getWidth(this) == getWidth() && offScreenBuffer
						.getHeight(this) == getHeight()))) {
			offScreenBuffer = this.createImage(getWidth(), getHeight());
		}
		gr = offScreenBuffer.getGraphics();
		paint(gr);
		g.drawImage(offScreenBuffer, 0, 0, this);
	}
}
