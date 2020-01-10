package tech.tora.xen.receipt;

import tech.tora.tools.printer.PrinterService;

public abstract class CustomPrinter {

	private PrinterService service = new PrinterService();
	private String printerName = "";
	protected int printWidth = 0;
	
	private boolean leftAdjust = true;
	
	private boolean leftAlign = true;
	private boolean centerAlign = false;
	private boolean rightAlign = false;
	
	protected String printText = "";
	
	public CustomPrinter(String printerName, int printWidth) {
		this.printerName = printerName;
		this.printWidth = printWidth;
		construct();
	}
	
	private void align(boolean left, boolean center, boolean right) {
		leftAlign = left;
		centerAlign = center;
		rightAlign = right;
	}
	
	public void alignLeft() {
		align(true, false, false);
	}
	
	public void alignCenter() {
		align(false, true, false);
	}
	
	public void alignRight() {
		align(false, false, true);
	}
	
	public void addLine(String text) {
		String blank = "";
		if (leftAlign) printText += text;
		if (centerAlign) {
			// Check even
			int halfTextLength;
			if (text.length()%2 == 0) {
				halfTextLength = text.length()/2;
			} else {
				halfTextLength = (text.length() + (leftAdjust ? 1 : -1)/2);
			}
			int count = (printWidth/2) - halfTextLength;
			for (int i = 0; i < count; i++) {
				blank += " ";
			}
			printText += blank + text;
			printText += "\n";
		}
		if (rightAlign) for (int i = 0; i < (printWidth-text.length()); i++) blank += " ";
		printText += "\n";
	}
	
	public void addRule(String character) {
		String rule = "";
		for (int i = 0; i < printWidth; i++) {
			rule += character;
		}
		rule += "\n";
		addLine(rule);
	}
	
	public void print() {
		service.printString(printerName, printText);
	}
	
	public void print(String text) {
		service.printString(printerName, text);
	}
	
	public void cut() {
		byte[] cutByte = new byte[] { 0x1d, 'V', 1 };
		service.printBytes(printerName, cutByte);
	}
	
	public void kick() {
		byte[] kickByte = new byte[] { 0x1b, 0x70, 0x00, 0x19, (byte) 0xfa };
		service.printBytes(printerName, kickByte);
	}
	
	public String arrayToString(String[] array) {
		String returnString = "";
		for (String s : array) returnString += (s+"\n");
		return returnString;
	}
	
	public abstract void construct();
	
}
