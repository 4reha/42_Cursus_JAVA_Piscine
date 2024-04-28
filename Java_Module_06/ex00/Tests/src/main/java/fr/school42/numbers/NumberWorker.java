package fr.school42.numbers;

public class NumberWorker {

  public boolean isPrime(int number) {
    if (number < 2) {
      throw new IllegalNumberException("Number must be greater than 1");
    }
    for (int i = 2; i < number; i++) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }

  public int digitsSum(int number) {
    if (number < 0) {
      number = -number;
    }
    int sum = 0;
    while (number > 0) {
      sum += number % 10;
      number /= 10;
    }
    return sum;
  }

}
