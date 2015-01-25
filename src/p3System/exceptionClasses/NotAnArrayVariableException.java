package p3System.exceptionClasses;

/**
 * NotAnArrayVariableException.
 * 
 * @author samus250
 *
 */
public class NotAnArrayVariableException extends RuntimeException {

  public NotAnArrayVariableException() {
  }

  public NotAnArrayVariableException(String message) {
    super(message);
  }

  public NotAnArrayVariableException(Throwable cause) {
    super(cause);
  }

  public NotAnArrayVariableException(String message, Throwable cause) {
    super(message, cause);
  }

}
