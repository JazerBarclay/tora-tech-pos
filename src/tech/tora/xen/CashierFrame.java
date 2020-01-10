package tech.tora.xen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tech.tora.tools.swing.dialog.JCurrencyDialog;
import tech.tora.tools.swing.field.JCurrencyField;
import tech.tora.tools.swing.frame.AdvancedFrame;
import tech.tora.tools.swing.list.InventoryListNode;
import tech.tora.tools.swing.list.Inventorylist;
import tech.tora.xen.accounting.Currency;
import tech.tora.xen.list.Item;
import tech.tora.xen.receipt.POS80Printer;

public class CashierFrame extends AdvancedFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CashierFrame() {
		JPanel rootPane = new JPanel(new BorderLayout());
		rootPane.setBackground(Color.LIGHT_GRAY);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(Color.GRAY);
		topPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 60));
		topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));

		JPanel centerPanel = new JPanel(new BorderLayout());

		JPanel botPanel = new JPanel(new BorderLayout());
		botPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
		botPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
		botPanel.setBackground(Color.LIGHT_GRAY);
		botPanel.setBorder(new EmptyBorder(8, 5, 8, 5));

		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.LIGHT_GRAY);
		p1.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
		p1.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
		p1.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(Color.DARK_GRAY);

		JPanel itemPanel = new JPanel(new BorderLayout());
		itemPanel.setBackground(Color.LIGHT_GRAY);
		JPanel pricePanel = new JPanel(new BorderLayout());
		pricePanel.setBackground(Color.LIGHT_GRAY);
		JPanel totalPricePanel = new JPanel(new BorderLayout());
		totalPricePanel.setBackground(Color.LIGHT_GRAY);
		
		// Items
		JLabel itemLabel = new JLabel("Item / Job: ");
		JTextField txtItem = new JTextField();
		JLabel priceLabel = new JLabel("Price: ");
		JCurrencyField price = new JCurrencyField(9);
		Inventorylist list = new Inventorylist();
		JLabel totalCost = new JLabel("Total:  £0.00", SwingConstants.RIGHT);

		itemLabel.setBorder(new EmptyBorder(0,5,0,3));
		priceLabel.setBorder(new EmptyBorder(0,5,0,3));
		
		
		price.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					
					if (txtItem.getText().equals("") && price.getText().equals("")) {
						if (list.getLength() < 1) {
							
						} else {
							int cashReceived = -1;
							int totalInt = Currency.getPence(totalCost.getText());
							JCurrencyDialog dialog;
							
							while (cashReceived == -1 || cashReceived == 0 || cashReceived < totalInt) {
								if (cashReceived != -1 && cashReceived < totalInt) {
									JOptionPane.showMessageDialog(CashierFrame.this, "Cash Received Not Enough To Cover Cost", "Cash Received Too Low", JOptionPane.WARNING_MESSAGE);
								}
								
								dialog = new JCurrencyDialog(CashierFrame.this, "Cash Received", "Please enter total received", "Outstanding: " +  totalCost.getText());
								cashReceived = dialog.showDialog();
							}
							
							int change = cashReceived - totalInt;

							Item[] items = new Item[list.getLength()];
							int i = 0;
							for (String key : list.getKeyList()) {
								items[i] = new Item(list.getNode(key).getName(), list.getNode(key).getValue());
								i++;
							}

							
							// Connect and send details to printer
							POS80Printer printer = new POS80Printer();
							printer.constructHeader();
							printer.constructDetails(
									Launcher.cashierProp.getProperty("cashier0"),
									Launcher.receiptProp.getProperty("receiptNo"));
							printer.constructItems(items);
							printer.constructTotal(totalInt, cashReceived, change);
							printer.constructFooter();
							printer.construct();
							
							printer.testPrint();
							printer.print();
							printer.cut();
							printer.kick();
							
							
							String changeStr = Currency.formatPounds(change);
							JOptionPane.showMessageDialog(CashierFrame.this, "Please give " + changeStr + " in change", "Change: " + changeStr, JOptionPane.INFORMATION_MESSAGE);
							
							Launcher.receiptProp.updateProperty("receiptNo", 
									(Integer.parseInt(Launcher.receiptProp.getProperty("receiptNo").replaceAll("[^\\d-]", "")) + 1)+"");
							Launcher.receiptProp.managedWriteProperties();
							
							totalCost.setText("Total:  £0.00");
							list.clear();
						}
					} else {
						list.addNode(new InventoryListNode(
								list, 
								(list.getLength()) + "", 
								txtItem.getText(), 
								Currency.getPence(price.getText())));
						price.setText("");
						txtItem.setText("");
						
						int total = 0;
						for (String key : list.getKeyList()) {
							total += list.getNode(key).getValue();
						}
							totalCost.setText("Total: " + Currency.formatPounds(total));
						
					}
					totalCost.setText(totalCost.getText() + "");
					txtItem.requestFocus();
				}
			}
		});

		txtItem.setSize(new Dimension(300, 20));
		txtItem.setPreferredSize(new Dimension(300, 20));
		txtItem.setMinimumSize(new Dimension(300, 20));
		txtItem.setMaximumSize(new Dimension(300, 20));
		txtItem.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					price.requestFocus();
				}
			}
		});

		totalCost.setBorder(new EmptyBorder(2,2,2,2));
		totalCost.setPreferredSize(new Dimension(140, 20));
		totalCost.setMaximumSize(new Dimension(140, 20));

		rootPane.add(topPanel, BorderLayout.NORTH);
		rootPane.add(centerPanel, BorderLayout.CENTER);
		rootPane.add(botPanel, BorderLayout.SOUTH);

		centerPanel.add(p1, BorderLayout.NORTH);
		centerPanel.add(p2, BorderLayout.CENTER);

		p1.add(itemPanel, BorderLayout.CENTER);
		p1.add(pricePanel, BorderLayout.EAST);
		p2.add(list.getDisplayPanel());
		
		botPanel.add(totalPricePanel, BorderLayout.EAST);

		itemPanel.add(itemLabel, BorderLayout.WEST);
		itemPanel.add(txtItem, BorderLayout.CENTER);

		pricePanel.add(priceLabel, BorderLayout.WEST);
		pricePanel.add(price, BorderLayout.CENTER);
		
		totalPricePanel.add(totalCost, BorderLayout.CENTER);


		setSize(800, 600);
		setTitle("Cashier " + Launcher.cashierProp.getProperty("cashier0"));
		setContentPane(rootPane);
		setLocationRelativeTo(null);

		JMenuBar menu = new JMenuBar();
		menu.setVisible(false);
		JMenu file = new JMenu("File");

		JMenuItem test = new JMenuItem("NewNodeTest");
		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(test);
			}
		});

//		setAlwaysOnTop(true);
		setResizable(false);
		file.add(test);
		menu.add(file);
		updateMenu(menu);
	}

	@Override
	public void closeAction() {
		this.dispose();
	}

	@Override
	public void minimiseAction() {
//		setState ( NORMAL );
	}

	@Override
	public void openAction() {}

	@Override
	public void maximiseAction() {}

	@Override
	public void gainFocusAction() {}

	@Override
	public void loseFocusAction() {}

}
