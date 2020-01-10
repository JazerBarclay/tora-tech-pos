package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class InventoryListNode implements AbstractListNode {

	public JPanel rootPane;
	protected Inventorylist parent;
	protected String key, name;
	protected int value;
	
	public InventoryListNode(Inventorylist parent, String key, String itemName, int itemValue) {
		this.parent = parent;
		this.key = key;
		this.name = itemName;
		this.value = itemValue;
	}
	
	@Override
	public JPanel getDisplayPanel() {
		rootPane = new JPanel(new BorderLayout());
		rootPane.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		rootPane.setPreferredSize(new Dimension(100, 30));
		rootPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		
		JPanel p1 = new JPanel(new BorderLayout());
		
		JLabel label1 = new JLabel(name, SwingConstants.RIGHT);
		label1.setBorder(new EmptyBorder(2, 10, 2, 30));
		rootPane.add(label1, BorderLayout.CENTER);
		
		String txtValue = "";
		if (value < 100) txtValue = value + "p";
		else txtValue = "£" + (value/100) + "." + (value+"").substring((value+"").length()-2, (value+"").length());
		
		JLabel label2 = new JLabel(txtValue);
		label2.setBorder(new EmptyBorder(2, 10, 2, 10));
		p1.add(label2, BorderLayout.CENTER);
		
		JLabel closeLabel = new JLabel("<html><font color='red'>X</font></html>");
		closeLabel.setBackground(Color.BLACK);
		closeLabel.setBorder(new EmptyBorder(2, 10, 2, 10));
		closeLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				parent.removeNode(key);
			}
		});
		p1.add(closeLabel, BorderLayout.EAST);
		
		rootPane.add(p1, BorderLayout.EAST);
		
		return rootPane;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
	
	@Override
	public void updateDisplayPanel() {
		rootPane.revalidate();
		rootPane.repaint();
	}

}