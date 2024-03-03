import java.util.Random;

public class Program {

	private static int[] generateArray(int size) {
		int[] array = new int[size];
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			array[i] = rand.nextInt(100);
		}
		return array;
	}

	private static int sumArray(int[] array) {
		int sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	private static int getArg(String[] args, String __prefix) {

		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].startsWith(__prefix)) {
					int arg = Integer.parseInt(args[i].trim().substring(__prefix.length()));
					if (arg > 0)
						return arg;
				}
			}
		} catch (NumberFormatException e) {
		}
		System.err.println("Invalid argument");
		System.exit(-1);
		return 0;
	}

	public static void main(String[] args) {

		int arraySize = getArg(args, "--arraySize=");
		int threadsCount = getArg(args, "--threadsCount=");

		int[] array = generateArray(arraySize);
		int sum = sumArray(array);

		System.out.println("Sum: " + sum);

		int sectionSize = (arraySize) / threadsCount;
		int remainder = (arraySize) % threadsCount;

		Thread[] threads = new Thread[threadsCount];
		for (int i = 0; i < threadsCount; i++) {
			int start = i * sectionSize;
			int end = start + sectionSize - 1;
			if (i == threadsCount - 1)
				end += remainder;

			threads[i] = new SummingThread(array, start, end);
			threads[i].start();
		}

		int sumByThreads = 0;
		for (Thread thread : threads) {
			try {
				thread.join();
				sumByThreads += ((SummingThread) thread).getSum();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Sum by threads: " + sumByThreads);
	}
}
