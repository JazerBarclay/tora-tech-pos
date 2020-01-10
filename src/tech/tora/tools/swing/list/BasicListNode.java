package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class BasicListNode implements AbstractListNode {

	protected JPanel rootPane = new JPanel(new BorderLayout());
	protected String key, value;
	
	public BasicListNode(String key, String value) {
		this.key = key;
		this.value = value;
		
		rootPane = buildDisplayPanel();
	}
	
	@Override
	public JPanel getDisplayPanel() {
		return rootPane;
	}

	@Override
	public void updateDisplayPanel() {
		rootPane.revalidate();
		rootPane.repaint();
	}

	protected JPanel buildDisplayPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		panel.setPreferredSize(new Dimension(100, 30));
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		JLabel label1 = new JLabel(value);
		label1.setBorder(new EmptyBorder(2, 10, 2, 10));
		panel.add(label1, BorderLayout.CENTER);
		return panel;
	}

}
