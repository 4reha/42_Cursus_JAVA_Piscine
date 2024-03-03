
public class Egg extends Thread {

	private Integer count;

	public Egg(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		for (int i = 0; i < this.count; i++) {
			System.out.println("Egg");
		}
	}
}
