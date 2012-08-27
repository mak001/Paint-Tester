package com.mak001.main.coder.gui;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;

@SuppressWarnings("serial")
public class TabButton extends JPanel implements ActionListener {

	ImageIcon reg = null;
	ImageIcon over = null;
	private JLabel label;
	private CodeTab ct;

	public TabButton(CodeTab ct) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.ct = ct;
		label = new JLabel(ct.getName());
		add(label);

		reg = Boot.getIcon("/images/remove_1.png");
		over = Boot.getIcon("/images/remove_2.png");

		setOpaque(false);
		final JButton button = new JButton(reg);
		button.setOpaque(false);
		button.setRolloverIcon(over);
		button.setPressedIcon(over);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		button.setMargin(new Insets(0, 5, 0, 0));
		add(button);

	}

	public void actionPerformed(ActionEvent ae) {
		JTabbedPane tp = Boot.codeFrame.tabbedPane;
		if (tp.indexOfTabComponent(this) == tp.getTabCount() - 2)
			tp.setSelectedIndex(tp.getTabCount() - 3);
		tp.remove(tp.indexOfTabComponent(this));
		if (ct.getTextContents() == null || ct.getTextContents().isEmpty()) {
			GlobalVariables.codeTabs.remove(ct);
		} else {
			ct.setClosed(true);
		}
	}

	public void setLabel(String newName) {
		label.setText(newName);
	}

	public void updateName() {
		setLabel(ct.getName());
	}
}