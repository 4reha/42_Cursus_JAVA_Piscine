
public class Egg extends Thread {

	private Integer count;
	private final Object lock;

	public Egg(int count, Object lock) {
		this.count = count;
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < this.count; i++) {
			synchronized (lock) {
				System.out.println("Egg");
				lock.notify();
				try {
					if (i < this.count - 1) {
						lock.wait();
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
