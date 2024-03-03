import java.util.Scanner;

public class Program {

  private static Scanner scanner = new Scanner(System.in);
  private static int weeks = 0;

  private static long power(long base, int exponent) {
    if (exponent == 0) {
      return 1;
    } else if (exponent < 0) {
      return 1 / power(base, -exponent);
    } else {
      return base * power(base, exponent - 1);
    }
  }

  private static int getTestMinGrade() {
    int minGrade = 9;
    for (int i = 0; i < 5; i++) {
      if (scanner.hasNextInt()) {
        int grade = scanner.nextInt();
        if (grade < 1 || grade > 9) {
          System.err.println("IllegalArgument");
          scanner.close();
          System.exit(-1);
        }
        minGrade = grade <= minGrade ? grade : minGrade;
      } else {
        System.err.println("IllegalArgument");
        scanner.close();
        System.exit(-1);
      }
    }
    scanner.nextLine();
    return minGrade;
  }

  private static long collectWeekGrades() {
    long grades = 0;
    String input = scanner.nextLine();

    while (weeks < 18 && !input.equals("42")) {
      if (input.equals("Week " + (weeks + 1))) {
        grades = grades * 10 + getTestMinGrade();
        weeks++;
        input = scanner.nextLine();
      } else {
        System.err.println("IllegalArgument");
        scanner.close();
        System.exit(-1);
      }
    }
    scanner.close();

    return grades;
  }

  private static void printGradesGraph(long grades) {
    int week = 1;
    long division = power(10, weeks - 1);
    while (division > 0) {
      System.out.print("Week " + week++ + " ");
      int grade = (int) (grades / division);
      grades %= division;
      division = division / 10;
      for (long i = 0; i < grade; i++) {
        System.out.print("=");
      }
      System.out.println(">");
    }
  }

  public static void main(String[] args) {
    long grades = collectWeekGrades();
    printGradesGraph(grades);
    scanner.close();
  }
}
