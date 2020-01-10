package tech.tora.tools.swing.list;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

import tech.tora.tools.swing.list.exceptions.ListDuplicateException;
import tech.tora.tools.swing.list.exceptions.ListKeyNotFoundException;

// An abstract list contains a list of nodes within its list
// displayes these nodes as they want to be shown
// the node returns a render and the list returns a render of itself
public abstract class AbstractList {
	
	protected LinkedHashMap<String, Object> nodes = new LinkedHashMap<>();
	protected JPanel rootPane = new JPanel(new BorderLayout());
	
	public AbstractList() {
		rootPane = generateDisplay();
	}

	protected void addNode(String id, Object obj) throws ListDuplicateException {
		if (containsNode(id)) throw new ListDuplicateException();
		nodes.put(id, obj);
	}
	
	protected void removeNode(String id) throws ListKeyNotFoundException {
		if (!containsNode(id)) throw new ListKeyNotFoundException();
		nodes.remove(id);
	}
	
	protected boolean containsNode(String id) {
		return nodes.containsKey(id);
	}
	
	protected Object getNode(String id) throws ListKeyNotFoundException {
		if (!containsNode(id)) throw new ListKeyNotFoundException();
		return nodes.get(id);
	}
	
	public abstract JPanel generateDisplay();
	
	public JPanel getDisplayPanel() {
		return rootPane;
	}

}
