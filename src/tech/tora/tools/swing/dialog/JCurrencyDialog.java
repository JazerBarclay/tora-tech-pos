package tech.tora.tools.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class JCurrencyDialog extends JDialog {
	
	/**  **/
	private static final long serialVersionUID = 1L;
	
	private int returnValue = -1;
	private JTextField textField = null;
	public JCurrencyDialog(JFrame parent, String title, String message, String message2) {
		super(parent, title);
		
		addWindowListener(new WindowListener() {
			
			@Override public void windowOpened(WindowEvent e) { }
			@Override public void windowIconified(WindowEvent e) { }
			@Override public void windowDeiconified(WindowEvent e) { }
			@Override public void windowDeactivated(WindowEvent e) { }
			@Override public void windowActivated(WindowEvent e) { }
			@Override public void windowClosed(WindowEvent e) { }
			
			@Override
			public void windowClosing(WindowEvent e) {
				returnValue = -1;
			}
			
		});
		
		JPanel panel = new JPanel();
		panel.add(new JLabel(message));
		panel.add(new JLabel(message2));
		
		textField = new JTextField(10);
		textField.addKeyListener(new KeyListener() {

			@Override public void keyTyped(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {

				// Strip all non-numeric values
				String text = textField.getText().replaceAll("[^\\d]", "");
				// Strip all zeros at the beginning unless its the only 0 there
				while (text.length() > 1 && text.substring(0, 1).equals("0")) {
					text = text.substring(1, text.length());
				}
				// If deleting and a "p" gets in the way, remove the p and the next value
				if (text.length() <= 2 && text.length() >0 && e.getKeyCode() == 8 && text.length() == textField.getText().length()) {
					text = text.substring(0, text.length()-1);
				}

				// If less than 2 then its pence format, else its pounds format
				if (text.length() <= 2) {
					text = (text+"p");
				} else {
					text = ("£" + text.substring(0, text.length()-2) + "." + text.substring(text.length()-2, text.length()));
				}

				// If you are left with a "p" after all this, remove the "p"
				if (text.equals("p")) {
					text = ("");
				}

				textField.setText(text);
				if (text.replaceAll("[^\\d]", "").equals("")) {text = "-1";}
				returnValue = Integer.parseInt(text.replaceAll("[^\\d-]", ""));
			}
			
		});
		
		panel.add(textField);
		
		JPanel messagePane = new JPanel();
		messagePane.add(new JLabel(message));
		getContentPane().add(panel);

		JPanel buttonPane = new JPanel();
		JButton closeButton = new JButton("Cancel");
		buttonPane.add(closeButton);
		
		JButton submitButton = new JButton("OK");
		buttonPane.add(submitButton);
		
		submitButton.addActionListener(new enterActionListener());
		closeButton.addActionListener(new closeActionListener());
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pack();
		setSize(new Dimension(200, 150));
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
	
	}
	
	public JCurrencyDialog(JFrame parent, String title, String message) {
		this(parent, title, message, "");
	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke escapeStroke = KeyStroke.getKeyStroke("ESCAPE");
		KeyStroke enterStroke = KeyStroke.getKeyStroke("ENTER");
		Action enterAction = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				new enterActionListener().actionPerformed(e);
			}
		};
		Action closeAction = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				new closeActionListener().actionPerformed(e);
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(escapeStroke, "ESCAPE");
		inputMap.put(enterStroke, "ENTER");
		rootPane.getActionMap().put("ESCAPE", closeAction);
		rootPane.getActionMap().put("ENTER", enterAction);
		return rootPane;
	}

	public int showDialog() {
		   setVisible(true);
		   return returnValue;
	}
	
	class enterActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!textField.getText().equals("") && textField.getText()!=null) {
				setVisible(false);
				dispose();
			} else {
				new closeActionListener().actionPerformed(e);
			}
		}
	}
	
	class closeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			returnValue = -1;
			setVisible(false);
			dispose();
		}
	}
	
	
}
