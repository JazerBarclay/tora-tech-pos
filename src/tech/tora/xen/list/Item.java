package tech.tora.xen.list;

public class Item {
	
	private String itemName = "";
	private int itemPrice = 0;
	private int itemQuantity = 1;
	
	public Item(String name, int price) {
		this.itemName = name;
		this.itemPrice = price;
	}
	
	public void addQuantity() {
		addQuantity(1);
	}
	
	public void addQuantity(int quantity) {
		this.itemQuantity += quantity;
	}
	
	public void setQuantity(int quantity) {
		this.itemQuantity = quantity;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getQuantity() {
		return itemQuantity;
	}
	
	public int getIndividualPrice() {
		return itemPrice;
	}
	
	public int getTotalPrice() {
		return (itemPrice * itemQuantity);
	}
	
}
