package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import tech.tora.tools.swing.list.exceptions.ListDuplicateException;
import tech.tora.tools.swing.list.exceptions.ListKeyNotFoundException;

public class BasicList extends AbstractList {
	
	protected JPanel containerPane;
	
	public BasicList() {

	}

	public void addNode(BasicListNode node) {
		try {
			addNode(nodes.size() + "", node);

			containerPane.add(node.getDisplayPanel());
			containerPane.revalidate();
			containerPane.repaint();
		} catch (ListDuplicateException e) {
			System.err.println("Duplicate key found. Key set to " + nodes.size() + "_1");
			try {
				addNode(nodes.size() + "_1", node);
			} catch (ListDuplicateException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void removeNode(String id) {
		try {
			super.removeNode(id);
		} catch (ListKeyNotFoundException e) {
			System.err.println("'" + id + "' key not found. Skipping");
		}
	}
	
	public boolean containsNode(String id) {
		return super.containsNode(id);
	}
	
	public BasicListNode getNode(String id) {
		try {
			return (BasicListNode) super.getNode(id);
		} catch (ListKeyNotFoundException e) {
			System.err.println("'" + id + "' key not found. Return null");
			return null;
		}
	}
	
	@Override
	public JPanel generateDisplay() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.GRAY);
		panel.setPreferredSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
		
		containerPane = new JPanel();
		containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(containerPane);
//		scrollPane = new JScrollPane(containerPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		panel.add(scrollPane);
		
		return panel;
	}

}
