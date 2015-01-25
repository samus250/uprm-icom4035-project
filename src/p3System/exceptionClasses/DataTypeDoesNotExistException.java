package p3System.exceptionClasses;

/**
 * DataTypeDoesNotExistException.
 * 
 * @author samus250
 *
 */
public class DataTypeDoesNotExistException extends RuntimeException {

  public DataTypeDoesNotExistException() {

  }

  public DataTypeDoesNotExistException(String message) {
    super(message);
  }

  public DataTypeDoesNotExistException(Throwable cause) {
    super(cause);
  }

  public DataTypeDoesNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

}
