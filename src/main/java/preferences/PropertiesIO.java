package preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author AdminOfThis
 *
 */
public final class PropertiesIO {

	private static final Logger LOG = LogManager.getLogger(PropertiesIO.class);
	private static String savePath;
	private static Properties properties = new Properties();

	public static boolean getBooleanProperty(String key) {
		return getBooleanProperty(key, false);
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		String value = getProperty(key);
		if (value != null) {
			return Boolean.parseBoolean(value);
		}
		return defaultValue;
	}

	public static Properties getProperties() {
		return properties;
	}

	public static String getProperty(String key) {
		return getProperty(key, null);
	}

	public static String getProperty(String key, String def) {
		if (properties != null && properties.containsKey(key)) {
			return properties.get(key).toString();
		} else {
			return def;
		}
	}

	public static Properties loadProperties() {
		File file = new File(savePath);
		return loadProperties(file);
	}

	/**
	 * Tries to load properties from the specified file
	 * 
	 * @param file The file from which the properties should be loaded
	 *
	 */
	public static Properties loadProperties(File file) {
		if (file != null && file.exists() && file.isFile()) {
			FileInputStream stream = null;
			try {
				Properties result = new Properties();
				stream = new FileInputStream(file);
				result.load(stream);
				properties = result;
				if (!properties.isEmpty()) {
					LOG.info(properties.size() + " Properties loaded");
				}
			} catch (IOException e) {
				properties = new Properties();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						LOG.error("Unable to close file input stream", e);
					}
				}
			}
		} else {
			properties = new Properties();
		}

		return properties;

	}

	public static void saveProperties() {
		PropertiesIO.saveProperties(new File(savePath));
	}

	/**
	 * Saves the specified properties to a file
	 * 
	 * @param pref The properties to save
	 * @param file The file to which the properties get saved. Get's overwritten if
	 *             already existing
	 * @return true if saved successful, false otherwise
	 */
	public static boolean saveProperties(File file) {
		boolean result = false;
		if (file != null) {
			FileOutputStream stream = null;
			try {
				stream = new FileOutputStream(file);
				properties.store(stream, null);

				result = true;
			} catch (IOException e) {
				LOG.warn("Saving the properties failed");
				result = false;
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						LOG.error("Problem closing properties write stream", e);
					}
				}
			}
		}
		return result;
	}

	public static void setProperties(Properties value) {
		properties = value;
	}

	public static void setProperty(String key, Object value) {
		setProperty(key, value, true);

	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param save
	 */
	public static void setProperty(String key, Object value, boolean save) {
		try {
			properties.put(key, value);
			if (save) {
				saveProperties();
			}
		} catch (Exception e) {
			LOG.warn("Problem saving properties", e);
		}
	}

	public static void setSavePath(String save) {
		savePath = save;
	}

	private PropertiesIO() {
		// not used
	}

}
