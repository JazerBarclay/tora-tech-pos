package tech.tora.xen.list;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import tech.tora.tools.swing.list.CustomList;
import tech.tora.tools.swing.list.CustomListNode;

public class ListItem extends CustomListNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Item item = new Item("", 0);
	
	public ListItem(CustomList parent, int width, int height, String id, Item item) {
		super(parent, width, height, id, item.getItemName());
		this.item = item;
	}
	
	@Override
	public JPanel generateNode() {
		setLayout(new BorderLayout());
		label = new JLabel(displayValue);
		setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		add(label, BorderLayout.CENTER);
		JLabel label2 = new JLabel();
		label2.setText("Testing");
		add(label2, BorderLayout.EAST);
		return this;
	}
	

}
