package debug;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

class ExecutionTimerTest {

	@RepeatedTest(3)
	@Execution(ExecutionMode.CONCURRENT)
	void testExecuteTime() {

		int time = (int) ((Math.random() + 1.0) * 1000.0);
		Runnable e = () -> {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		};
		System.setOut(new PrintStream(new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {

			}
		}));
		long result = Math.round(ExecutionTimer.executeTime(e) / 1000000.0);
		System.setOut(System.out);
		long min = time - 10;

		long max = time + 10;
		assertTrue(min <= result && result <= max);
	}

}
