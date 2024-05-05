package fr.school42.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {
  private static final NumberWorker INSTANCE = new NumberWorker();

  @ParameterizedTest
  @ValueSource(ints = { 2, 7, 11, 13, 17, 19, 23 })
  void isPrimeForPrimes(int primeNumber) {
    assertTrue(INSTANCE.isPrime(primeNumber));
  }

  @ParameterizedTest
  @ValueSource(ints = { 4, 6, 8, 9, 10, 12, 14 })
  void isPrimeForNotPrimes(int notPrimeNumber) {
    assertFalse(!INSTANCE.isPrime(notPrimeNumber));
  }

  @ParameterizedTest
  @ValueSource(ints = { 0, 1, -26 })
  void isPrimeForIncorrectNumbers(int invalidNumber) {
    assertThrows(IllegalNumberException.class, () -> INSTANCE.isPrime(invalidNumber));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
  void checkDigitsSum(int number, int sum) {
    assertEquals(sum, INSTANCE.digitsSum(number));
  }
}