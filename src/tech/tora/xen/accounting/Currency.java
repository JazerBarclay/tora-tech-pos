package tech.tora.xen.accounting;

public class Currency {

	public Currency() {
		
	}
	
	public static String formatPounds(int valuePence) {
		String formattedCurrency = "";
		if (valuePence < 10) {
			formattedCurrency = "0.0" + valuePence;
		} else if (valuePence < 100)
			formattedCurrency = "0." + valuePence;
		else
			formattedCurrency = "" + (valuePence/100) + "." + (valuePence+"").substring((valuePence+"").length()-2, (valuePence+"").length());
		return formattedCurrency;
	}
	
	public static int getPence(String value) {
		int returnVal = -1;
		try {
			returnVal = Integer.parseInt(value.replaceAll("[^\\d-]", ""));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return returnVal;
	}
	
}
