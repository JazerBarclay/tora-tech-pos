package tech.tora.xen;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tech.tora.tools.properties.JProp;
import tech.tora.tools.properties.JProperties;
import tech.tora.tools.properties.exceptions.PropertyFileNotFoundException;
import tech.tora.tools.properties.exceptions.PropertyIOException;
import tech.tora.tools.properties.exceptions.PropertyReadException;
import tech.tora.tools.properties.exceptions.PropertyWriteException;

public class Launcher {
	public static boolean devmode = false;
	
	public static JProperties receiptProp;
	public static JProperties cashierProp;
	
	private String receiptFile = "receipt.properties", cashierFile = "cashier.properties";

	public Launcher(boolean devmode) {

		System.out.println("Welcome to XEN EPOS");
		
		Launcher.devmode = devmode;

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "XEN-Client");
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		// Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		receiptProp = readReceipt();
		cashierProp = readCashier();
		
		System.out.println("Receipt No.: " + receiptProp.getProperty("receiptNo"));
		System.out.println("Cashier Default: " + cashierProp.getProperty("cashier0"));

		CashierFrame frame = new CashierFrame();
		frame.setVisible(true);
		
	}
	
	private JProperties readReceipt() {
		receiptProp = new JProperties(receiptFile);
		try {
			receiptProp.readProperties();
		} catch (PropertyFileNotFoundException e) {
			// create new property file
			receiptProp = new JProperties(receiptFile, new JProp("receiptNo", 0));
			receiptProp.managedWriteProperties();
		} catch (PropertyReadException e1) {
			System.err.println("Failed to read property file due to ReadException");
			if (Launcher.devmode) e1.printStackTrace();
			System.exit(0);
		} catch (PropertyIOException e3) {
			System.err.println("Failed to read property file due to IOException");
			if (Launcher.devmode) e3.printStackTrace();
			System.exit(0);
		}
		return receiptProp;
	}
	
	private JProperties readCashier() {
		cashierProp = new JProperties(cashierFile);
		
		try {
			cashierProp.readProperties();
		} catch (PropertyFileNotFoundException e) {
			cashierProp = new JProperties(cashierFile, new JProp("cashier0", "Default"));
			try {
				cashierProp.writeProperties();
			} catch (PropertyWriteException pwe) {
				pwe.printStackTrace();
				System.exit(0);
			} catch (PropertyIOException pioe) {
				pioe.printStackTrace();
				System.exit(0);
			}
		} catch (PropertyReadException e1) {
			System.exit(0);
		} catch (PropertyIOException e3) {
			System.exit(0);
		}
		return cashierProp;
	}


	public static void main(String[] args) {
		if (args.length < 1) {
			args = new String[] {"0"};
		}
		new Launcher(Boolean.getBoolean(args[0]));
	}

}
