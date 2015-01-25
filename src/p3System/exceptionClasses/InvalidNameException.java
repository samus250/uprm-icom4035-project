package p3System.exceptionClasses;

/**
 * InvalidNameException
 * 
 * @author samus250
 *
 */
public class InvalidNameException extends RuntimeException {

  public InvalidNameException() {
  }

  public InvalidNameException(String message) {
    super(message);
  }

  public InvalidNameException(Throwable cause) {
    super(cause);
  }

  public InvalidNameException(String message, Throwable cause) {
    super(message, cause);
  }

}
