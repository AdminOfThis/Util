package debug;

import java.io.OutputStream;
import java.io.PrintStream;

public final class Util {

	private static PrintStream emptyStream;

	public static void disableSyso() {
		emptyStream = new PrintStream(OutputStream.nullOutputStream());

		System.setOut(emptyStream);
		System.setErr(emptyStream);
	}

	public static void closeEmptyStream() {
		if (emptyStream != null) {
			emptyStream.close();
		}
	}

}
