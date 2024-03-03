
public class Program {

	private static int getCount(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			System.exit(1);
		}
		if (!args[0].trim().startsWith("--count=")) {
			System.err.println("Invalid argument");
			System.exit(1);
		}
		int count = 0;
		try {
			count = Integer.parseInt(args[0].trim().split("=")[1]);
			if (count <= 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.err.println("Invalid argument");
			System.exit(1);
		}
		return count;
	}

	public static void main(String[] args) {

		int count = getCount(args);

		Object lock = new Object();

		Thread eggThread = new Egg(count, lock);
		Thread henThread = new Thread(new Hen(count, lock));

		eggThread.start();
		henThread.start();

		try {
			eggThread.join();
			henThread.join();
		} catch (InterruptedException e) {
			System.err.println("Thread was interrupted");
		}

		for (int i = 0; i < count; i++) {
			System.out.println("Human");
		}
	}
}