package tech.tora.tools.properties;

/**
 * A generic property consisting of a key and value
 * @author jazer
 */
public class JProp {

	private String key, value;
	
	/**
	 * Create a property with the given key and string value
	 * @param key - A key to retreive the value
	 * @param stringValue - A value stored to the given key
	 */
	public JProp(String key, String stringValue) {
		this.key = key;
		this.value = stringValue;
	}

	/**
	 * Create a property with the given key and integer value
	 * @param key - A key to retreive the value
	 * @param integerValue - A value stored to the given key
	 */
	public JProp(String key, int integerValue) {
		this.key = key;
		this.value = integerValue+"";
	}
	
	/**
	 * Updates the stored key to the new key
	 * @param key - The new key
	 * @return oldKey - The old key
	 */
	public String setKey(String key) {
		String oldKey = key;
		this.key = key;
		return oldKey;
	}
	
	/**
	 * Returns the current stored key
	 * @return key - The current stored key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Updates the value to the new string value
	 * @param newStringValue - The new string value
	 * @return oldVal - The original value before update
	 */
	public String setValue(String newStringValue) {
		String oldVal = key;
		this.value = newStringValue;
		return oldVal;
	}
	
	/**
	 * Updates the value to the new integer value
	 * @param newIntegerValue - The new string value
	 * @return oldVal - The original value before update
	 */
	public String setValue(int newIntegerValue) {
		String oldVal = key;
		this.value = newIntegerValue+"";
		return oldVal;
	}
	
	/**
	 * Returns the current value
	 * @return value - the current stored value
	 */
	public String getValue() {
		return value;
	}

}
