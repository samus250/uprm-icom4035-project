package p3System.exceptionClasses;

/**
 * InvalidArrayLengthException.
 * 
 * @author samus250
 *
 */
public class InvalidArrayLengthException extends RuntimeException {

  public InvalidArrayLengthException() {
  }

  public InvalidArrayLengthException(String message) {
    super(message);
  }

  public InvalidArrayLengthException(Throwable cause) {
    super(cause);
  }

  public InvalidArrayLengthException(String message, Throwable cause) {
    super(message, cause);
  }

}
