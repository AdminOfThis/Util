package main;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author AdminOfThis
 *
 */
public final class MainUtil {

	private static final Logger LOG = LogManager.getLogger(MainUtil.class);

	/**
	 * Tries to create a lock file to ensure that only only instance of this program
	 * is running at any given time.
	 * 
	 * @param lockFile The path of the lockFile this method should create
	 * @return false if another instance is already running, true otherwise
	 */
	@SuppressWarnings("resource")
	public static boolean createRunningLockFile(String lockFile) {
		boolean result = false;
		try {
			final File file = new File(lockFile);
			final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
			final FileLock fileLock = randomAccessFile.getChannel().tryLock();
			if (fileLock != null) {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						try {
							fileLock.release();
							randomAccessFile.close();
							file.delete();
						} catch (Exception e) {
							LOG.error("Unable to remove lock file: " + fileLock, e);
						}
					}
				});
				return true;
			}
		} catch (Exception e) {
			LOG.error("Unable to create and/or lock file", e);
		}
		return result;
	}

	private MainUtil() {}
}
