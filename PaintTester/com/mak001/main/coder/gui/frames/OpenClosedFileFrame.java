package com.mak001.main.coder.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;

import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.CodeTab;

@SuppressWarnings("serial")
public class OpenClosedFileFrame extends JFrame {

	private final JList<String> list;

	public OpenClosedFileFrame() {

		Dimension dim = new Dimension(150, 200);

		setMinimumSize(dim);
		setSize(dim);

		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(open);

		JScrollPane sp = new JScrollPane();
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
																sp,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																225,
																Short.MAX_VALUE)
														.addComponent(
																btnOpen,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																225,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addGap(12)
						.addComponent(sp, GroupLayout.DEFAULT_SIZE, 284,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnOpen).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		list = new JList<String>(getNames(GlobalVariables.closedTabs.toArray()));

		sp.setViewportView(list);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}

	public void setListData(Object[] os) {
		list.setListData(getNames(os));
	}

	public String[] getNames(Object[] objects) {
		String[] ss = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof CodeTab) {
				ss[i] = ((CodeTab) objects[i]).getName();
			}
		}
		return ss;
	}

	private final ActionListener open = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (Object s : list.getSelectedValuesList()) {
				if (s instanceof String) {
					for (Object ct : GlobalVariables.closedTabs.toArray()) {
						if (((CodeTab) ct).getName().equals(s)) {
							((CodeTab) ct).setClosed(false);
						}
					}
				} else {
					return;
				}
			}
		}
	};
}
