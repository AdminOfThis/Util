package com.github.adminofthis.util.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertiesIOTest {

	private static final String SAVE_FILE_PATH = "save.conf";
	private static final String TEST_FILE_PATH = "/preferences/testFile.conf";

	private static final String STRING_KEY = "string";
	private static final String STRING_VALUE = "stringValue3472890q4twserhdfn";

	private static final String BOOLEAN_KEY = "boolean";
	private static final String BOOLEAN_VALUE = "true";

	private Properties props;

	@BeforeEach
	void addInitialSettings() {
		props = new Properties();
		props.put(STRING_KEY, STRING_VALUE);
	}

	@Test
	void booleanSetting() {
		props.put(BOOLEAN_KEY, BOOLEAN_VALUE);
		PropertiesIO.setProperties(props);
		assertEquals(true, PropertiesIO.getBooleanProperty(BOOLEAN_KEY));
	}

	@Test
	void loadDefaultFile() {
		PropertiesIO.setSavePath(SAVE_FILE_PATH);
		Properties loadedProps = PropertiesIO.loadProperties();
		assertNotNull(loadedProps);
		assertTrue(loadedProps.isEmpty());
	}

	@Test
	void loadDirectoryFile() {
		File file = new File("./test");
		file.mkdir();
		assertTrue(file.exists());
		assertTrue(file.isDirectory());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertNotNull(loadedProps);
		assertTrue(loadedProps.isEmpty());
	}

	@Test
	void loadEmptyFile() {
		File file = new File("empty.conf");
		try {
			file.createNewFile();
			Properties loadedProps = PropertiesIO.loadProperties(file);
			assertNotNull(loadedProps);
			assertTrue(loadedProps.isEmpty());
		} catch (IOException e) {
		} finally {
			file.deleteOnExit();
		}
	}

	@Test
	void loadNonExistingFile() {
		File file = new File("doesNotExist.conf");
		assertFalse(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertNotNull(loadedProps);
		assertTrue(loadedProps.isEmpty());
	}

	@Test
	void loadNullFile() {
		File file = null;
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertNotNull(loadedProps);
		assertTrue(loadedProps.isEmpty());
	}

	@Test
	void loadTestFile() throws URISyntaxException {
		File file;
		file = new File(getClass().getResource(TEST_FILE_PATH).toURI());

		assertNotNull(file);
		System.out.println(file.getAbsolutePath());
		assertTrue(file.exists());
		assertTrue(file.isFile());
		Properties props = PropertiesIO.loadProperties(file);
		assertNotNull(props);
		assertFalse(props.isEmpty());
	}

	@Test
	void overwriteSetting() {
		File file = new File(SAVE_FILE_PATH);
		PropertiesIO.setProperties(props);
		PropertiesIO.saveProperties(file);
		assertTrue(file.exists());
		String newString = "new String, balsfnjkaoeanp";
		PropertiesIO.setProperty(STRING_KEY, newString, false);
		PropertiesIO.setProperty(STRING_KEY, newString);
		PropertiesIO.saveProperties(file);
		assertTrue(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertEquals(props.get(STRING_KEY), loadedProps.get(STRING_KEY));
		assertEquals(props.get(STRING_KEY), PropertiesIO.getProperty(STRING_KEY));
		assertEquals(props.get(STRING_KEY), PropertiesIO.getProperties().get(STRING_KEY));
		assertEquals(props, loadedProps);
	}

	@Test
	void putAndInsertSetting() {
		assertEquals(STRING_VALUE, props.get(STRING_KEY));
	}

	@BeforeEach
	@AfterEach
	void removeOldFile() {
		File oldFile = new File(SAVE_FILE_PATH);
		if (oldFile.exists()) {
			oldFile.delete();
			oldFile.deleteOnExit();
		}
	}

	@Test
	void removeSetting() {
		assertNull(props.get(STRING_VALUE));
	}

	@Test
	void saveAndReload() {
		File file = new File(SAVE_FILE_PATH);
		assertFalse(file.exists());
		PropertiesIO.setProperties(props);
		PropertiesIO.saveProperties(file);
		assertTrue(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertEquals(props, loadedProps);
	}

	@Test
	void saveFileWhileException() {
		File file = new File(SAVE_FILE_PATH);
		PropertiesIO.setProperties(props);
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
			boolean result = PropertiesIO.saveProperties(file);
			assertFalse(result);
		} catch (Exception e) {

//			fail(e);
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
			}
		}
	}

	@Test
	void saveToNullFile() {
		File file = null;
		PropertiesIO.setProperties(props);
		boolean result = PropertiesIO.saveProperties(file);
		assertFalse(result);
	}

	@Test
	void setAndsaveAndReload() {
		PropertiesIO.setSavePath(SAVE_FILE_PATH);
		File file = new File(SAVE_FILE_PATH);
		assertFalse(file.exists());
		PropertiesIO.setProperties(props);
		PropertiesIO.saveProperties();
		assertTrue(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertEquals(props, loadedProps);
	}

	@Test
	void updateSetting() {
		String newString = "new String";
		props.put(STRING_KEY, newString);
		assertEquals(props.get(STRING_KEY), newString);
	}

}
