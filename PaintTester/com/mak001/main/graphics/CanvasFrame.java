package com.mak001.main.graphics;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mak001.main.Boot;
import com.mak001.main.Closer;
import com.mak001.main.GlobalVariables;

@SuppressWarnings("serial")
public class CanvasFrame extends JFrame implements ComponentListener {
	private final Canvas canvas;

	private final BufferedImage SCREEN_IMAGE;
	private final Dimension SCREEN_DIMENSION;

	public CanvasFrame() {

		addComponentListener(this);
		GlobalVariables.currentBot.getGameBase().setDimension(getSize());

		SCREEN_IMAGE = Boot.getImage("/images/screen.png");
		SCREEN_DIMENSION = new Dimension(SCREEN_IMAGE.getWidth(null),
				SCREEN_IMAGE.getHeight(null));

		setTitle("Preview");
		addWindowListener(new Closer());

		canvas = new Canvas(SCREEN_IMAGE);
		canvas.setPreferredSize(SCREEN_DIMENSION);
		canvas.init();
		add(canvas);

		Dimension x = new Dimension(SCREEN_IMAGE.getWidth(null),
				SCREEN_IMAGE.getHeight(null) + 25);

		setSize(x);
		setMinimumSize(x);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		GlobalVariables.currentBot.getGameBase().setDimension(getSize());
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}
