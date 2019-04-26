package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javafx.application.Application;

public abstract class MainGUI extends Application {

	private static String			title			= "";
	private static String			version			= "";
	private static final String		VERSION_KEY		= "Implementation-Version";
	private static final String		TITLE_KEY		= "Implementation-Title";
	protected static Logger			LOG;
	protected static final String	LOG_CONFIG_FILE	= "./log4j.ini";
	private static MainGUI			instance;

	public static void initialize(String pomTitle) {
		title = getFromManifest(TITLE_KEY, "Frequent", pomTitle);
		version = getFromManifest(VERSION_KEY, "Local", pomTitle);
		initLogger();
	}

	public MainGUI() {
		instance = this;
	}

	public static MainGUI getInstance() {
		return instance;
	}

	public abstract boolean close();

	public abstract String getPOMTitle();

// public static void main(String[] args) {
// initLogger();
// LOG.info("Started");
// launch(args);
// }
	/**
	 * sets up the Log4j logger ba reading the properties file
	 */
	protected static void initLogger() {
		try {
			PropertyConfigurator.configure(LOG_CONFIG_FILE);
			LOG = Logger.getLogger(MainGUI.class);
		}
		catch (Exception e) {
			LOG.fatal("Unexpected error while initializing logging", e);
		}
	}

	public static String getFromManifest(final String key, final String def, String pomTitle) {
		try {
			Enumeration<URL> resources = MainGUI.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
				try {
					Manifest manifest = new Manifest(resources.nextElement().openStream());
					if (pomTitle.equalsIgnoreCase(manifest.getMainAttributes().getValue("Specification-Title")))
						// check that this is your manifest and do what you need
						// or get the next one
						return manifest.getMainAttributes().getValue(key);
				}
				catch (IOException e) {
					LOG.warn(e);
				}
			}
		}
		catch (Exception e) {
			LOG.warn("Unable to read version from manifest");
			LOG.debug("", e);
		}
		return def;
	}

	public String getTitle() {
		return title;
	}

	public static String getVersion() {
		return version;
	}

	public static String getReadableTitle() {
		return getOnlyTitle() + " " + getVersion();
	}

	public static String getOnlyTitle() {
		if (title == null || title.isEmpty()) { return ""; }
		return title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
	}
}
