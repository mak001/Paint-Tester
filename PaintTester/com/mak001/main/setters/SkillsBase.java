package com.mak001.main.setters;

public class SkillsBase {

	public void setLevels(int[] levelsArray) {
		levels = levelsArray;
	}

	public void setLevel(int skill, int level) {
		levels[skill] = level;
	}

	public void setExperiences(int[] exp) {
		experiences = exp;
	}

	public void setExperience(int skill, int experience) {
		experiences[skill] = experience;
	}

	protected static int[] levels = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	protected static int[] experiences = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };;

}
