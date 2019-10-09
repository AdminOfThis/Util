package preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.sun.istack.internal.logging.Logger;

/**
 * 
 * @author AdminOfThis
 *
 */
public abstract class PropertiesIO {

	private static final Logger LOG = Logger.getLogger(PropertiesIO.class);

	/**
	 * Saves the specified properties to a file
	 * 
	 * @param pref The properties to save
	 * @param file The file to which the properties get saved. Get's overwritten if
	 *             already existing
	 * @return true if saved successful, false otherwise
	 */
	public static boolean saveProperties(Properties pref, File file) {
		try {
			pref.store(new FileOutputStream(file), null);
			return true;
		} catch (IOException e) {
			LOG.warning("Saving the properties failed");
			return false;
		}
	}

	/**
	 * Tries to load properties from the specified file
	 * 
	 * @param file The file from which the properties should be loaded
	 * @return The properties contained in the file, an empty properties object
	 *         otherwise
	 */
	public static Properties loadProperties(File file) {
		if (file != null && file.exists() && file.isFile()) {

			try {
				Properties result = new Properties();
				result.load(new FileInputStream(file));
				return result;
			} catch (IOException e) {
			}
		}
		return new Properties();

	}

}
