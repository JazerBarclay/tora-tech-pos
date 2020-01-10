package tech.tora.xen.receipt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import tech.tora.xen.accounting.Currency;
import tech.tora.xen.list.Item;

public class POS80Printer extends CustomPrinter {

	// Override Name + Width
	public POS80Printer(String printerName, int printerWidth) {
		super(printerName, printerWidth);
	}
	// Override Name
	public POS80Printer(String printerName) {
		super(printerName, 48);
	}
	
	// Default
	public POS80Printer() {
		super("POS-80", 48);
	}

	@Override
	public void construct() {
		addLine("");
	}
	
	public void constructHeader() {
		for (String s : header()) addLine(s);
	}
	
	public void constructDetails(String cashierName, String issueNo) {
		for (String s : details(cashierName, issueNo, 
				DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()),
				DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()))) addLine(s); 
	}
	
	public void constructItems(Item[] itemArray) {
		addLine(items(itemArray));
	}
	
	public void constructTotal(int total, int received, int change) {
		addLine(totals(total, received, change));
	}
	
	public void constructFooter() {
		for (String s : footer()) addLine(s);
	}
	
	private String[] header() {
		return new String[] {
				"\n",
				"  _______                _______        _    ",
				" |__   __|              |__   __|      | |   ",
				"    | | ___  _ __ __ _     | | ___  ___| |__  ",
				"    | |/ _ \\| '__/ _` |    | |/ _ \\/ __| '_ \\ ",
				"    | | (_) | | | (_| |    | |  __/ (__| | | |",
				"    |_|\\___/|_|  \\__,_|    |_|\\___|\\___|_| |_|",
				"",
				"              Code . Repair . Support           ",
				"",
				"",
				"    67 Ninfield Road           01424 257 406    ",
				"    Bexhill, TN39 5BA          0800 024 8086    "
		};
	}
	
	private String[] details(String cashierName, String issueNo, String date, String time) {
		String cashierGap = "";
		int c = 21-cashierName.length();
		for (int i = 0; i < c; i++) cashierGap+=" "; 
		return new String[] {
				" ---------------------------------------------- ",
				" Date: "+date+cashierGap+"Cashier: "+cashierName+" ",
				" Time: "+time+"                 Issue: 00000"+issueNo+" ",
				" ---------------------------------------------- " //48
		};
	}

	private String items(Item[] items) {
		String s = "";
		int itemCount = items.length;
		s += "| Item                             |   Price   |\n";
		for (int i = 0; i < itemCount; i++) {
			String itemName = items[i].getItemName();
			while (itemName.length() > 32 ) {
				s+=("| " + itemName.substring(0, 32) + " |           |\n");
				itemName = (" " + itemName.substring(32));
			}
			s+=("| " + itemName);
			int z = itemName.length() + 2;
			int gap = 35-z;
			for (int j = 0; j < gap; j++) s+=" ";
			s+="| £";
			String str = Currency.formatPounds(items[i].getTotalPrice());
			int xgap = 8-str.length();
			for (int l = 0; l < xgap; l++) s+= " ";
			s+= str;
			s+=" |\n";
		}
		return s;
	}
	
	private String totals(int total, int received, int change) {
		String s = "";
		int startGap = printWidth-14;
		for (int i = 0; i < (startGap-5); i++) s+=" ";
		s+="Total | £";
		
		String str = Currency.formatPounds(total);
		int xgap = 8-str.length();
		for (int l = 0; l < xgap; l++) s+= " ";
		s+= str;
		s+=" |\n";
		
		
		for (int i = 0; i < (startGap-4); i++) s+=" ";
		s+="Cash | £";
		
		str = Currency.formatPounds(received);
		xgap = 8-str.length();
		for (int l = 0; l < xgap; l++) s+= " ";
		s+= str;
		s+=" |\n";
		
		
		for (int i = 0; i < (startGap-6); i++) s+=" ";
		s+="Change | £";
		
		str = Currency.formatPounds(change);
		xgap = 8-str.length();
		for (int l = 0; l < xgap; l++) s+= " ";
		s+= str;
		s+=" |\n";
		
		return s;
	}
	
	private String[] footer() {
		return new String[] {
				" ---------------------------------------------- ",
				"",
				"              Thank you for choosing            ",
				"                    Tora Tech                   ",
		};
	}
	
	public void testPrint() {
		System.out.println(printText);
	}
	
}
