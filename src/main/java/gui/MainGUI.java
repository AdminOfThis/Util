package gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;

public abstract class MainGUI extends Application {

	protected static Logger LOG = LogManager.getLogger(MainGUI.class);
	protected static final String LOG_CONFIG_FILE = "./log4j.ini";
	private static MainGUI instance;

	public MainGUI() {
		instance = this;
	}

	public static MainGUI getInstance() {
		return instance;
	}

	public abstract boolean close();

}
