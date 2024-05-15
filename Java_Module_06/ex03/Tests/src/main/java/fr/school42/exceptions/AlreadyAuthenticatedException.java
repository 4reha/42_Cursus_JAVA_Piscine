package fr.school42.exceptions;

public class AlreadyAuthenticatedException extends RuntimeException {

  public AlreadyAuthenticatedException() {
    super("User is already authenticated");
  }

  public AlreadyAuthenticatedException(String message) {
    super(message);
  }

  public AlreadyAuthenticatedException(String message, Throwable cause) {
    super(message, cause);
  }

  public AlreadyAuthenticatedException(Throwable cause) {
    super(cause);
  }

}
