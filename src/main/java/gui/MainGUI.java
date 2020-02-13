package gui;

import javafx.application.Application;

public abstract class MainGUI extends Application {

	protected static final String LOG_CONFIG_FILE = "./log4j.ini";
	private static MainGUI instance;

	public MainGUI() {
		instance = this;
	}

	public static MainGUI getInstance() {
		return instance;
	}

}
