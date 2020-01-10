package tech.tora.xen.receipt.pos80;

import tech.tora.xen.receipt.ReceiptPrinterProperties;

public class POS80Properties extends ReceiptPrinterProperties {

	private int currentReceiptNo;
	
	public POS80Properties(int receiptNo) {
		this.currentReceiptNo = receiptNo;
	}
	
	public void setReceiptNo(int receiptNo) {
		this.currentReceiptNo = receiptNo;
	}
	
	public int getReceiptNo() {
		return currentReceiptNo;
	}
	
}
