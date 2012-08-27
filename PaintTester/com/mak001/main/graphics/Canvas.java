package com.mak001.main.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import com.mak001.main.GlobalVariables;

@SuppressWarnings("serial")
public class Canvas extends daApplet implements Runnable, MouseMotionListener,
		MouseListener, KeyListener {

	private final BufferedImage background;

	public Canvas(BufferedImage background) {
		this.background = background;
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		(new Thread(this)).start();

	}

	@Override
	public void mouseDragged(MouseEvent me) {
		GlobalVariables.currentBot.getMouse().setLocation(me.getPoint());
		for (MouseMotionListener m : GlobalVariables.mouseMotionListeners) {
			m.mouseDragged(me);
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		GlobalVariables.currentBot.getMouse().setLocation(me.getPoint());
		for (MouseMotionListener m : GlobalVariables.mouseMotionListeners) {
			m.mouseMoved(me);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (MouseListener m : GlobalVariables.mouseListeners) {
			m.mouseClicked(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GlobalVariables.currentBot.getMouse().setPressed(true);
		for (MouseListener m : GlobalVariables.mouseListeners) {
			m.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GlobalVariables.currentBot.getMouse().setPressed(false);
		for (MouseListener m : GlobalVariables.mouseListeners) {
			m.mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		GlobalVariables.currentBot.getMouse().setPresent(true);
		for (MouseListener m : GlobalVariables.mouseListeners) {
			m.mouseEntered(e);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		GlobalVariables.currentBot.getMouse().setPresent(false);
		for (MouseListener m : GlobalVariables.mouseListeners) {
			m.mouseExited(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for (KeyListener k : GlobalVariables.keyListeners) {
			k.keyTyped(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (KeyListener k : GlobalVariables.keyListeners) {
			k.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (KeyListener k : GlobalVariables.keyListeners) {
			k.keyReleased(e);
		}
	}

	@Override
	public void run() {
		do {
			repaint();
			try {
				Thread.sleep(25L);
			} catch (InterruptedException e) {
			}
		} while (true);
	}

	@Override
	public void paint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(background, 0, 0, null);

		if (GlobalVariables.currentPaintClass != null) {
			if (GlobalVariables.currentPaintBase == null) {
				try {
					GlobalVariables.currentPaintBase = GlobalVariables.currentPaintClass
							.newInstance();
					System.gc();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			GlobalVariables.currentPaintBase.onRepaint(g);
		}
	}

}
