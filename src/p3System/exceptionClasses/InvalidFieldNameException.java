package p3System.exceptionClasses;

/**
 * InvalidFieldNameException
 * 
 * @author samus250
 *
 */
public class InvalidFieldNameException extends RuntimeException {

  public InvalidFieldNameException() {
  }

  public InvalidFieldNameException(String message) {
    super(message);
  }

  public InvalidFieldNameException(Throwable cause) {
    super(cause);
  }

  public InvalidFieldNameException(String message, Throwable cause) {
    super(message, cause);
  }

}
