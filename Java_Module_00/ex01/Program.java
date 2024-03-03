import java.util.Scanner;

public class Program {

  private static Scanner scanner = new Scanner(System.in);

  private static void primeCheck(int integer) {
    if (integer < 2) {
      System.err.println("IllegalArgument");
      scanner.close();
      System.exit(-1);
    }
    int iter = 0;
    for (int i = 2; i * i <= integer; i++) {
      iter++;
      if (integer % i == 0) {
        System.out.println("false " + iter);
        return;
      }

    }
    System.out.println("true " + (iter + 1));
  }

  public static void main(String[] args) {
    if (scanner.hasNextInt()) {
      int integer = scanner.nextInt();
      primeCheck(integer);
    }
    scanner.close();
  }
}
