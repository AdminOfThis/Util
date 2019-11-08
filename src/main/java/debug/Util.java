package debug;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public final class Util {

	private static PrintStream emptyStream;

	public static void disableSyso() {
		emptyStream = new PrintStream(new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
			}
		});

		System.setOut(emptyStream);
		System.setErr(emptyStream);
	}

	public static void closeEmptyStream() {
		if (emptyStream != null) {
			emptyStream.close();
		}
	}

}
