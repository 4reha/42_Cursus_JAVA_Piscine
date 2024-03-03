
public class Hen implements Runnable {

	private Integer count;

	public Hen(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < this.count; i++) {
			System.out.println("Hen");
		}
	}
}