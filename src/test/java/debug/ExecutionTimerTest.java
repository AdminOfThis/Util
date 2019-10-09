package debug;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

class ExecutionTimerTest {

	@RepeatedTest(10)
	@Execution(ExecutionMode.CONCURRENT)
	void testExecuteTime() {

		int time = (int) ((Math.random() + 1.0) * 3.0 * 1000.0);
		Runnable e = () -> {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		};
		long result = Math.round(ExecutionTimer.executeTime(e) / 1000000.0);
		long min = time - 10;
		long max = time + 10;
		assertTrue(min <= result && result <= max);
	}

}
