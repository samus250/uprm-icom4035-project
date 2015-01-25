package p3System.exceptionClasses;

/**
 * InvalidInstanceFieldException
 * 
 * @author samus250
 *
 */
public class InvalidInstanceFieldException extends RuntimeException {

  public InvalidInstanceFieldException() {
  }

  public InvalidInstanceFieldException(String message) {
    super(message);
  }

  public InvalidInstanceFieldException(Throwable cause) {
    super(cause);
  }

  public InvalidInstanceFieldException(String message, Throwable cause) {
    super(message, cause);
  }

}
