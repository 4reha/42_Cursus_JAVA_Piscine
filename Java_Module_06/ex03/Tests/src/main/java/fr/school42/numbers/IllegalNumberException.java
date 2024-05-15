package fr.school42.numbers;

public class IllegalNumberException extends RuntimeException {

  public IllegalNumberException(String message) {
    super(message);
  }

  public IllegalNumberException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalNumberException(Throwable cause) {
    super(cause);
  }

  public IllegalNumberException() {
    super();
  }

}
