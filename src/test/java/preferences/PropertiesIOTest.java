package preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
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

	private Properties props;

	@BeforeEach
	@AfterEach
	void removeOldFile() {
		File oldFile = new File(SAVE_FILE_PATH);
		if (oldFile.exists()) {
			oldFile.delete();
			oldFile.deleteOnExit();
		}
	}

	@BeforeEach
	void addInitialSettings() {
		props = new Properties();
		props.put(STRING_KEY, STRING_VALUE);
	}

	@Test
	void putAndInsertSetting() {
		assertEquals(STRING_VALUE, props.get(STRING_KEY));
	}

	@Test
	void removeSetting() {
		assertNull(props.get(STRING_VALUE));
	}

	@Test
	void updateSetting() {
		String newString = "new String";
		props.put(STRING_KEY, newString);
		assertEquals(props.get(STRING_KEY), newString);
	}

	@Test
	void saveAndReload() {
		File file = new File(SAVE_FILE_PATH);
		assertFalse(file.exists());
		PropertiesIO.saveProperties(props, file);
		assertTrue(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertEquals(props, loadedProps);
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
	void overwriteSetting() {
		File file = new File(SAVE_FILE_PATH);
		PropertiesIO.saveProperties(props, file);
		assertTrue(file.exists());
		String newString = "new String, balsfnjkaoeanp";
		props.put(STRING_KEY, newString);
		PropertiesIO.saveProperties(props, file);
		assertTrue(file.exists());
		Properties loadedProps = PropertiesIO.loadProperties(file);
		assertEquals(props.get(STRING_KEY), loadedProps.get(STRING_KEY));
		assertEquals(props, loadedProps);
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

}
