import java.util.Scanner;

public class Program {
  private static final int MAX_CHARS = 65535;
  private static Scanner scanner = new Scanner(System.in);

  private static short[] countCharacters(String input) {
    short[] charsCount = new short[MAX_CHARS];
    char[] inputChars = input.toCharArray();

    System.out.println(inputChars);

    for (int i = 0; i < inputChars.length; i++) {
      charsCount[inputChars[i]]++;
    }
    return charsCount;
  }

  private static char[][] getTop10(short[] charsCount) {
    char[][] top10 = new char[2][10];


    for (int i = 0; i < MAX_CHARS; i++) {
      if (charsCount[i] > top10[1][9]) {
        top10[0][9] = (char) i;
        top10[1][9] = (char) charsCount[i];
        for (int j = 8; j >= 0; j--) {
          if (top10[1][j] < top10[1][j + 1]) {
            char tempChar = top10[0][j];
            char tempCount = top10[1][j];
            top10[0][j] = top10[0][j + 1];
            top10[1][j] = top10[1][j + 1];
            top10[0][j + 1] = tempChar;
            top10[1][j + 1] = tempCount;
          }
        }
      }
    }
    return top10;
  }

  private static void printGraph(char[][] top10) {
    for (int i = 10; i >= 0; i--) {
    putChart(i, top10[1]);
    }
    for (int j = 0; j < 10; j++) {
      System.out.printf("%3c ", top10[0][j]);
    }
  }

  private static void putChart(int i, char[] topTenCount) {
    for (int j = 0; j < 10; j++) {
      if (topTenCount[j] * 10 / topTenCount[0] == i) {
        System.out.printf("%3d ", (int) topTenCount[j]);
      } else if (topTenCount[j] * 10 / topTenCount[0] > i) {
        System.out.print("  # ");
      } else {
        System.out.print("    ");
      }
    }
    System.out.println();

  }

  public static void main(String[] args) {
    String input = scanner.nextLine();

    short[] charsCount = countCharacters(input);
    char[][] top10 = getTop10(charsCount);
    printGraph(top10);

    scanner.close();
  }
}

// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFKKDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWWWWWOOOOOOO42