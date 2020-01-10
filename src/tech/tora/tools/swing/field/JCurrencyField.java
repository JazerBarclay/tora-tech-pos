package tech.tora.tools.swing.field;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JCurrencyField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JCurrencyField(int columns) {
		super(columns);
		addKeyListener(new KeyListener() {

			@Override public void keyTyped(KeyEvent e) {}
			@Override public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {

				// Strip all non-numeric values
				String text = getText().replaceAll("[^\\d]", "");
				// Strip all zeros at the beginning unless its the only 0 there
				while (text.length() > 1 && text.substring(0, 1).equals("0")) {
					text = text.substring(1, text.length());
				}
				// If deleting and a "p" gets in the way, remove the p and the next value
				if (text.length() <= 2 && text.length() >0 && e.getKeyCode() == 8 && text.length() == getText().length()) {
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

				setText(text);
				if (text.replaceAll("[^\\d]", "").equals("")) {text = "-1";}
				Integer.parseInt(text.replaceAll("[^\\d-]", ""));
			}
			
		});
	}
	
	

}
