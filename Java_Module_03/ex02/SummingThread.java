
class SummingThread extends Thread {

	private static int lastId = 0;

	private int[] array;
	private int startIndex, endIndex;
	private int localSum = 0;

	public SummingThread(int[] array, int start, int end) {
		this.setName("Thread " + ++lastId);
		this.array = array;
		this.startIndex = start;
		this.endIndex = end;
	}

	@Override
	public void run() {
		for (int i = startIndex; i <= endIndex; i++) {
			localSum += array[i];
		}
		System.out.println(getName() + ": from " + startIndex + " to " + endIndex + " sum is " + localSum);
	}

	public int getSum() {
		return localSum;
	}
}