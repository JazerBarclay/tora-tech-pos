package tech.tora.tools.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import tech.tora.tools.properties.exceptions.PropertyFileNotFoundException;
import tech.tora.tools.properties.exceptions.PropertyIOException;
import tech.tora.tools.properties.exceptions.PropertyReadException;
import tech.tora.tools.properties.exceptions.PropertyWriteException;
import tech.tora.xen.Launcher;

/**
 * A generic properties container
 * @author jazer
 */
public class JProperties {
	
	private String location;
	private JProp[] propertyList;
	
	/**
	 * 
	 * @param fileName - File name
	 */
	public JProperties(String fileName) {
		this.location = fileName;
	}

	/**
	 * 
	 * @param fileName - File name
	 * @param myProps - Properties array
	 */
	public JProperties(String fileName, JProp... myProps) {
		this.location = fileName;
		this.propertyList = myProps;
	}
	
	/**
	 * 
	 * @param fileName - File name
	 * @param propKeys - Property name array
	 */
	public JProperties(String fileName, String... propKeys) {
		this.location = fileName;
		for (String k : propKeys) {
			addProperty(k, null);
		}
	}

	/**
	 * 
	 * @param prop - New property
	 */
	public void addProperty(JProp prop) {
		JProp[] tmp = new JProp[(propertyList.length+1)];
		for (int i = 0; i < propertyList.length; i++) tmp[i] = propertyList[i];
		tmp[tmp.length] = prop;
		propertyList = tmp;
	}

	/**
	 * 
	 * @param key - Key
	 * @param value - Value
	 */
	public void addProperty(String key, String value) {
		addProperty(new JProp(key, value));
	}

	/**
	 * 
	 * @param key - Key
	 * @param value - Value
	 */
	public void addProperty(String key, int value) {
		addProperty(new JProp(key, value));
	}

	/**
	 * 
	 * @return Property list
	 */
	public JProp[] getProperties() {
		return propertyList;
	}
	
	public String updateProperty(String key, String newValue) {
		String oldValue = "";
		for (JProp prop : propertyList) {
			if (prop.getKey().equals(key)) {
				oldValue = prop.getValue();
				prop.setValue(newValue);
				return oldValue;
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param key - Key
	 * @return Value associated with key.<p>
	 * Returns null if no key match found
	 */
	public String getProperty(String key) {
		for (JProp p : propertyList) {
			if (p.getKey().equals(key)) {
				return p.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return Properties - properties from file
	 * @throws PropertyFileNotFoundException No file found
	 * @throws PropertyReadException Failed to read file
	 * @throws PropertyIOException Failed to close connection
	 */
	public Properties readProperties() throws PropertyFileNotFoundException, PropertyReadException, PropertyIOException {
		Properties prop = new Properties();
		InputStream input = null;
		
		File f = new File(location);
		if(!f.exists() || f.isDirectory()) throw new PropertyFileNotFoundException("Property File Not Found");
		
		try {
			input = Launcher.class.getResourceAsStream(location);
			if (input == null) {
				input = new FileInputStream(location);
			}
			prop.load(input);
		} catch (Exception e) {
			throw new PropertyReadException("Unable to read properties value");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new PropertyIOException("Unable to close stream for read properties value");
				}
			}
		}
		propertyList = getPropsFromProperties(prop);
		return prop;
	}
	
	/**
	 * 
	 * @param keys
	 * @return
	 * @throws PropertyFileNotFoundException
	 * @throws PropertyReadException
	 * @throws PropertyIOException
	 */
	public String[] readProperties(String... keys) throws PropertyFileNotFoundException, PropertyReadException, PropertyIOException {
		Properties prop = readProperties();
		
		String[] values = new String[keys.length];
		int c = 0;
		for (String k : keys) {
			values[c] = prop.getProperty(k);
			c++;
		}
		return values;

	} // END readProperties()
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public static JProp[] getPropsFromProperties(Properties p) {
		JProp[] pr = new JProp[p.keySet().size()];
		int z = 0;
		for (Object k : p.keySet()) {
			pr[z] = new JProp((String) k, p.getProperty((String) k));
			z++;
		}
		return pr;
	}
	
	/**
	 * 
	 * @throws PropertyWriteException Failed to write file
	 * @throws PropertyIOException Failed to close file io
	 */
	public void writeProperties() throws PropertyWriteException, PropertyIOException {
		Properties prop = new Properties();
		OutputStream output = null;
		
		try {
			output = new FileOutputStream(location);

			// set the properties value
			for (JProp p : propertyList) {
				prop.setProperty(p.getKey(), p.getValue());
			}
			
			prop.store(output, null);

		} catch (IOException io) {
			throw new PropertyWriteException("");
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					throw new PropertyIOException("");
				}
			}
			
		}

	  }
	
	public void managedWriteProperties() {
		try {
			writeProperties();
		} catch (PropertyWriteException e1) {
			e1.printStackTrace();
		} catch (PropertyIOException e2) {
			e2.printStackTrace();
		}
	}
	
	public Properties managedReadProperties() {
		Properties prop = null;
		try {
			prop=readProperties();
		} catch (PropertyFileNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyReadException e) {
			e.printStackTrace();
		} catch (PropertyIOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public String[] managedReadProperties(String... keys) {
		String[] strArray = null;
		try {
			strArray = readProperties(keys);
		} catch (PropertyFileNotFoundException e) {
			e.printStackTrace();
		} catch (PropertyReadException e) {
			e.printStackTrace();
		} catch (PropertyIOException e) {
			e.printStackTrace();
		}
		return strArray;
	}
	
	
} // END CLASS
