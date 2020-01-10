package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import tech.tora.tools.swing.list.exceptions.CustomListException;
import tech.tora.tools.swing.list.exceptions.CustomListNoNodeFoundException;

public class CustomList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LinkedHashMap<String, CustomListNode> nodes;
	
	private JPanel containerPane;
	protected int prefWidth = 0;
	protected int prefHeight = 0;
	protected int minNodeWidth = -1;
	protected int minNodeHeight = 28;
	
	public CustomList(int width, int height) {
		nodes = new LinkedHashMap<String, CustomListNode>();
		
		// Generate the list
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		prefWidth = width;
		prefHeight = height;
		if (width < 0) prefWidth = Integer.MAX_VALUE;
		if (height < 0) prefHeight = Integer.MAX_VALUE;
		setPreferredSize(new Dimension(prefWidth, prefHeight));
		
		containerPane = new JPanel();
		containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(containerPane);
//		scrollPane = new JScrollPane(containerPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		this.add(scrollPane);
	}
	
	public CustomList() {
		this(-1, -1);
	}
	
	private void buildNodes() {
		containerPane.removeAll();
		for (String k : nodes.keySet()) {
			containerPane.add(nodes.get(k).updateNode());
		}
		containerPane.revalidate();
		containerPane.repaint();
	}
	
	public void addNode(String id) throws CustomListException {
		nodes.put(id, new CustomListNode(this, minNodeWidth, minNodeHeight, id));
		buildNodes();
	}
	
	public void addNode(String id, String displayValue) throws CustomListException {
		nodes.put(id, new CustomListNode(this, minNodeWidth, minNodeHeight, id, displayValue));
		buildNodes();
	}
	
	public void addNode(CustomListNode node) throws CustomListException {
		nodes.put(node.getID(), node);
		buildNodes();
	}
	
	public void removeNode(String id) throws CustomListNoNodeFoundException {
		if (nodes.remove(id) == null) {
			throw new CustomListNoNodeFoundException("Key: " + id + " not found in list");
		}
		buildNodes();
	}

	public int getLength() {
		return nodes.size();
	}
}
