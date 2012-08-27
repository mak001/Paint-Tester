package com.mak001.main.coder;

import com.mak001.main.setters.GameBase;
import com.mak001.main.setters.MainBase;
import com.mak001.main.setters.MouseBase;
import com.mak001.main.setters.PaintBase;
import com.mak001.main.setters.SkillsBase;

public enum Bot {

	POWERBOT(
			"PowerBot",
			mainbase(org.powerbot.game.api.ActiveScript.class),
			mousebase(org.powerbot.game.api.methods.input.Mouse.class),
			paintbase(org.powerbot.game.bot.event.listener.PaintListener.class),
			skillsbase(org.powerbot.game.api.methods.tab.Skills.class),
			gamebase(org.powerbot.game.api.methods.Game.class)), RUNEDREAM(
			"RuneDream", mainbase(org.runedream.api.Script.class),
			mousebase(org.runedream.api.methods.Mouse.class),
			paintbase(org.runedream.api.Script.class),
			skillsbase(org.runedream.api.methods.Skills.class),
			gamebase(org.runedream.api.methods.Game.class));

	private final String name;

	private final Class<MainBase> main;
	private final Class<PaintBase> paint;

	private final MouseBase mouse;
	private final SkillsBase skills;
	private final GameBase game;

	Bot(String name, Class<MainBase> main, MouseBase mouse,
			Class<PaintBase> paint, SkillsBase skills, GameBase game) {
		this.name = name;
		this.game = game;
		this.main = main;
		this.mouse = mouse;
		this.paint = paint;
		this.skills = skills;
	}

	public String getName() {
		return name;
	}

	public MouseBase getMouse() {
		return mouse;
	}

	public Class<MainBase> getMain() {
		return main;
	}

	public SkillsBase getSkills() {
		return skills;
	}

	public Class<PaintBase> getPainter() {
		return paint;
	}

	public GameBase getGameBase() {
		return game;
	}

	@Override
	public String toString() {
		return getName();
	}

	@SuppressWarnings("unchecked")
	private static Class<MainBase> mainbase(Class<?> clazz) {
		try {
			return (Class<MainBase>) clazz.asSubclass(MainBase.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static MouseBase mousebase(Class<?> clazz) {
		try {
			return (MouseBase) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static SkillsBase skillsbase(Class<?> clazz) {
		try {
			return (SkillsBase) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static GameBase gamebase(Class<?> clazz) {
		try {
			return (GameBase) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static Class<PaintBase> paintbase(Class<?> clazz) {
		try {
			return (Class<PaintBase>) clazz.asSubclass(PaintBase.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
