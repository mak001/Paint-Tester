package com.mak001.main.coder.gui;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mak001.main.Boot;
import com.mak001.main.GlobalVariables;
import com.mak001.main.coder.gui.syntax.SyntaxDocument;

@SuppressWarnings("serial")
public class AddTab extends JPanel implements ActionListener {

	private final SyntaxDocument doc;

	private ImageIcon reg = null;
	private ImageIcon over = null;

	public AddTab() {
		super((LayoutManager) new FlowLayout(FlowLayout.LEFT, 0, 0));

		doc = new SyntaxDocument();

		reg = Boot.getIcon("/images/add_1.png");
		over = Boot.getIcon("/images/add_2.png");

		setOpaque(false);
		final JButton button = new JButton(reg);
		button.setOpaque(false);
		button.setRolloverIcon(over);
		button.setPressedIcon(over);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		button.setMargin(new Insets(1, 1, 1, 1));
		add(button);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = JOptionPane
				.showInputDialog("The name of the new class: ");
		if (name != null && !name.isEmpty()) {
			if (isDeclared(name)) {
				JOptionPane.showMessageDialog(this,
						"Cannot creat a class with the name " + name + "!",
						"Error!", JOptionPane.ERROR_MESSAGE);
			} else {

				if (GlobalVariables.codeTabs.contains(name)) {
					JOptionPane.showMessageDialog(this,
							"Tab already exists with the name " + name + "!",
							"Error!", JOptionPane.ERROR_MESSAGE);
				} else {
					new CodeTab(name, true);
					Boot.codeFrame.tabbedPane
							.setSelectedIndex(Boot.codeFrame.tabbedPane
									.getTabCount() - 2);
				}
			}
		}
	}

	private boolean isDeclared(String name) {
		for (String keyword : doc.getKeywords()) {
			if (name.equals(keyword))
				return true;
		}
		return false;
	}

}