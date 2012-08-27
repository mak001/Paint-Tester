package com.mak001.main.coder.gui.frames;

import java.util.Hashtable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mak001.main.GlobalVariables;

@SuppressWarnings("serial")
public class VariableManipulatorFrame extends JFrame {

	VariableManipulatorFrame frame;

	public VariableManipulatorFrame() {
		setTitle("Variable manipulation");
		frame = this;

		setSize(750, 210);

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		JLabel lblSkill = new JLabel("Skill");

		JScrollPane jsp = new JScrollPane();

		final JList<String> list = new JList<String>();
		list.setListData(new String[] { "Attack", "Defense", "Strngth",
				"Constitution", "Range", "Prayer", "Magic", "Cooking",
				"Woodcutting", "Fletching", "Fishing", "Firemaking",
				"Crafting", "Smithing", "Mining", "Herblore", "Agility",
				"Thieving", "Slayer", "Farming", "Runecrafting", "Hunter",
				"Construction", "Summoning", "Dungeoneering" });
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblLevel = new JLabel("Level");

		final JSlider level = new JSlider(SwingConstants.HORIZONTAL, 0, 99, 0);
		level.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				GlobalVariables.currentBot.getSkills().setLevel(
						list.getSelectedIndex(), level.getValue());
			}
		});
		level.setMajorTickSpacing(9);
		level.setPaintTicks(true);
		level.setPaintLabels(true);

		JLabel lblExperience = new JLabel(
				"Experience (Major ticks are tick * 1,000,000 xp)");

		final JSlider xp = new JSlider(SwingConstants.HORIZONTAL, 0, 200000000,
				0);
		xp.setMajorTickSpacing(10000000);
		xp.setPaintTicks(true);
		xp.setPaintLabels(true);

		Hashtable<Integer, JLabel> xpTable = new Hashtable<Integer, JLabel>();
		for (int i = 0; i < 21; i++) {
			xpTable.put(i * 10000000, new JLabel("" + i * 10));
		}
		xp.setLabelTable(xpTable);
		xp.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				GlobalVariables.currentBot.getSkills().setExperience(
						list.getSelectedIndex(), xp.getValue());
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedValue().equals("Dungeoneering")) {
					level.setMaximum(120);
				} else {
					if (level.getMaximum() != 99) {
						level.setMaximum(99);
					}
				}
			}
		});

		JLabel label = new JLabel("");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblSkill)
														.addComponent(
																jsp,
																GroupLayout.PREFERRED_SIZE,
																108,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblLevel)
														.addComponent(
																level,
																GroupLayout.DEFAULT_SIZE,
																366,
																Short.MAX_VALUE)
														.addComponent(
																lblExperience)
														.addComponent(
																xp,
																GroupLayout.DEFAULT_SIZE,
																366,
																Short.MAX_VALUE)
														.addComponent(
																label,
																GroupLayout.DEFAULT_SIZE,
																366,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblSkill)
														.addComponent(lblLevel))
										.addGap(7)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				level,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				lblExperience)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				xp,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				label,
																				GroupLayout.DEFAULT_SIZE,
																				182,
																				Short.MAX_VALUE))
														.addComponent(
																jsp,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE))
										.addContainerGap()));
		getContentPane().setLayout(groupLayout);

		jsp.setViewportView(list);
	}
}
