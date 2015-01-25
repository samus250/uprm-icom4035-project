package p3System.exceptionClasses;

/**
 * NotArrayVariableException.
 * 
 * @author samus250
 *
 */
public class NotArrayVariableException extends RuntimeException {

  public NotArrayVariableException() {
  }

  public NotArrayVariableException(String message) {
    super(message);
  }

  public NotArrayVariableException(Throwable cause) {
    super(cause);
  }

  public NotArrayVariableException(String message, Throwable cause) {
    super(message, cause);
  }

}
