package org.runedream.api.methods;

import java.awt.Rectangle;

import com.mak001.main.setters.SkillsBase;

/**
 * Stats Interface convenience methods.
 * 
 * @author Aidden
 */
public final class Skills extends SkillsBase {

	/**
	 * Enumeration of the game skills.
	 */
	public enum Skill {
		ATTACK("Attack", new Rectangle(550, 210, 59, 26), 0), DEFENCE(
				"Defence", new Rectangle(550, 266, 59, 26), 1), STRENGTH(
				"Strength", new Rectangle(550, 238, 59, 26), 2), CONSTITUTION(
				"Constitution", new Rectangle(612, 210, 59, 26), 3), RANGED(
				"Ranged", new Rectangle(550, 294, 59, 26), 4), PRAYER("Prayer",
				new Rectangle(550, 322, 59, 26), 5), MAGIC("Magic",
				new Rectangle(550, 350, 59, 26), 6), COOKING("Cooking",
				new Rectangle(674, 294, 59, 26), 7), WOODCUTTING("Woodcutting",
				new Rectangle(674, 350, 59, 26), 8), FLETCHING("Fletching",
				new Rectangle(612, 350, 59, 26), 9), FISHING("Fishing",
				new Rectangle(674, 266, 59, 26), 10), FIREMAKING("Firemaking",
				new Rectangle(674, 322, 59, 26), 11), CRAFTING("Crafting",
				new Rectangle(612, 322, 59, 26), 12), SMITHING("Smithing",
				new Rectangle(674, 238, 59, 26), 13), MINING("Mining",
				new Rectangle(674, 210, 59, 26), 14), HERBLORE("Herblore",
				new Rectangle(612, 266, 59, 26), 15), AGILITY("Agility",
				new Rectangle(612, 238, 59, 26), 16), THIEVING("Thieving",
				new Rectangle(612, 294, 59, 26), 17), SLAYER("Slayer",
				new Rectangle(612, 378, 59, 26), 18), FARMING("Farming",
				new Rectangle(674, 378, 59, 26), 19), RUNECRAFTING(
				"Runecrafting", new Rectangle(550, 378, 59, 26), 20), HUNTER(
				"Hunter", new Rectangle(612, 406, 59, 26), 21), SUMMONING(
				"Summoning", new Rectangle(674, 406, 59, 26), 22), CONSTRUCTION(
				"Construction", new Rectangle(550, 406, 59, 26), 23), DUNGEONEERING(
				"Dungeoneering", new Rectangle(550, 434, 59, 26), 24);

		private final String skill;
		private final int id;
		private final Rectangle bounds;
		public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);

		private Skill(final String skill, final Rectangle bounds, final int id) {
			this.skill = skill;
			this.bounds = bounds;
			this.id = id;
		}

		/**
		 * Gets the skill's name.
		 * 
		 * @return The skill's name.
		 */
		public String getSkill() {
			return skill;
		}

		/**
		 * Gets the skill's bounding rectangle.
		 * 
		 * @return The skill's bounding rectangle.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the skill's current level.
		 * 
		 * @return Current level
		 */
		public int getLevel() {
			return levels[id];
		}

		/**
		 * Get current level from experience
		 * 
		 * @param exp
		 *            ; Current experience
		 * @return Current level
		 */
		public static int getLevel(final int exp) {
			double xp = 0.00;
			for (int i = 0; i < 120; i += 1) {
				xp += (i / 4.00) + 75.00 * Math.pow(2, i / 7.00);
				if (xp > exp) {
					return i - 1;
				}
			}
			return 0;
		}

		/**
		 * Get the current experience of the skill
		 * 
		 * @return Current experience
		 */
		public int getExperience() {
			return experiences[id];
		}

		/**
		 * Get the experience required to level up
		 * 
		 * @return Remaining experience
		 */
		public int getExperienceToLevel() {
			return XP_TABLE[getLevel() + 1] - getExperience();
		}

		private static final int[] XP_TABLE = { 0, 0, 83, 174, 276, 388, 512,
				650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115,
				3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730,
				10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815,
				27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512,
				67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594,
				150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288,
				333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051,
				737627, 814445, 899257, 992895, 1096278, 1210421, 1336443,
				1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114,
				2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332,
				5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
				11805606, 13034431, 14391160, 15889109, 17542976, 19368992,
				21385073, 23611006, 26068632, 28782069, 31777943, 35085654,
				38737661, 42769801, 47221641, 52136869, 57563718, 63555443,
				70170840, 77474828, 85539082, 94442737, 104273167 };
	}
}