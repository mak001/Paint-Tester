package com.mak001.main.coder.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import com.mak001.main.Settings;
import com.mak001.main.Settings.Types;
import com.mak001.main.coder.Bot;

@SuppressWarnings("serial")
public class SettingsFrame extends JFrame {

	private final JFrame f;

	public SettingsFrame() {
		f = this;
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		Dimension dim = new Dimension(150, 150);
		setSize(new Dimension(200, 315));
		setMinimumSize(dim);

		final JCheckBox chckbxCopyImportedFiles = new JCheckBox(
				"Copy imported files");
		chckbxCopyImportedFiles.setSelected(Settings
				.getSettingBoolean(Types.COPY_IMPORTED_FILES));

		final JCheckBox chckbxUseSrcFolder = new JCheckBox("Use src folder");
		chckbxUseSrcFolder.setSelected(Settings
				.getSettingBoolean(Types.USE_SRC_FOLDER));

		final JCheckBox chckbxUseBinFolder = new JCheckBox("Use bin folder");
		chckbxUseBinFolder.setSelected(Settings
				.getSettingBoolean(Types.USE_BIN_FOLDER));

		JLabel lblBotApiTo = new JLabel("Bot API to use:");

		final JList<Bot> list = new JList<Bot>();
		list.setListData(Bot.values());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedValue(
				Bot.valueOf(Settings.getSetting(Types.BOT).toUpperCase()),
				false);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.put(Types.COPY_IMPORTED_FILES,
						chckbxCopyImportedFiles.isSelected());
				Settings.put(Types.USE_BIN_FOLDER,
						chckbxUseBinFolder.isSelected());
				Settings.put(Types.USE_SRC_FOLDER,
						chckbxUseSrcFolder.isSelected());
				Settings.put(Types.BOT, list.getSelectedValue().getName());
				f.setVisible(false);
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																list,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE)
														.addComponent(
																lblBotApiTo,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE)
														.addComponent(
																chckbxUseBinFolder,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE)
														.addComponent(
																chckbxUseSrcFolder,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE)
														.addComponent(
																chckbxCopyImportedFiles,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE)
														.addComponent(
																btnConfirm,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																160,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(chckbxCopyImportedFiles)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chckbxUseSrcFolder)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chckbxUseBinFolder)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblBotApiTo, GroupLayout.PREFERRED_SIZE,
								25, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 119,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE,
								23, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		getContentPane().setLayout(groupLayout);
	}
}
