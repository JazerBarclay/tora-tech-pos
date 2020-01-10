package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class CustomListNode extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected CustomList parent;
	protected String id;
	protected String displayValue;
	protected JLabel label;
	
	public CustomListNode(CustomList parent, int width, int height, String id, String displayValue) {
		this.parent = parent;
		this.id = id;
		this.displayValue = displayValue;
		
		generateNode();
		
		Dimension dim = new Dimension(
				(width > 0) ? width : Integer.MAX_VALUE, 
				(height > 0) ? height : Integer.MAX_VALUE);
		setPreferredSize(dim);
		setMaximumSize(dim);
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	public CustomListNode(CustomList parent, int width, int height, String id) {
		this(parent, width, height, id, null);
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public void setValue(String value) {
		this.displayValue = value;
	}
	
	public String getValue() {
		return displayValue;
	}
	
	public JPanel generateNode() {
		setLayout(new BorderLayout());
		label = new JLabel(displayValue);
		label.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
		add(label, BorderLayout.CENTER);
		return this;
	}
	
	public JPanel updateNode(String... values) {
		if (values.length > 0) {
			label.setText(values[0]);
			revalidate();
			repaint();
		}
		return this;
	}

}
