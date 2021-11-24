module Util {
	exports com.github.adminofthis.util.gui;
	exports com.github.adminofthis.util.main;
	exports com.github.adminofthis.util.preferences;

	requires transitive java.desktop;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive javafx.swing;
	requires transitive org.apache.logging.log4j;
	requires transitive org.junit.jupiter.api;
}