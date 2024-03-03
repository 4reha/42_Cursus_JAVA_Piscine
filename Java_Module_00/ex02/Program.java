import java.util.Scanner;

public class Program {

  private static Scanner scanner = new Scanner(System.in);

  static int toSum(int number) {
    int sum = 0;

    for (int j = number; j > 0; j /= 10)
      sum += j % 10;

    return sum;
  }

  private static boolean checkPrime(int integer) {
    if (integer < 2)
      return false;

    for (int i = 2; i * i <= integer; i++) {
      if (integer % i == 0)
        return false;
    }
    return true;
  }

  public static void main(String[] args) {
    int primeCount = 0;
    while (true) {
      if (!scanner.hasNextInt()) {
        System.err.println("IllegalArgument");
        scanner.close();
        System.exit(-1);
      }
      int sequence = scanner.nextInt();
      if (sequence == 42)
        break;

      if (checkPrime(toSum(sequence)))
        primeCount++;

    }
    System.out.println("Count of coffee-request - " + primeCount);
    scanner.close();
  }
}
