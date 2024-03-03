
public class ThreadFactory implements java.util.concurrent.ThreadFactory {
	private static int threadNum = 0;

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, "Thread-" + ++threadNum);
	}
}
