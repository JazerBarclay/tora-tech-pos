package tech.tora.xen.list;

import tech.tora.tools.swing.list.CustomList;
import tech.tora.tools.swing.list.exceptions.CustomListException;

public class List extends CustomList {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List(int listWidth, int listHeight, int nodeWidth, int nodeHeight) {
		super();
	}
	
	public void addNode(String id, Item item) throws CustomListException {
		addNode(new ListItem(this, Integer.MAX_VALUE, minNodeHeight, id, item));
	}
	

}
