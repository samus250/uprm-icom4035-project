package p3System.exceptionClasses;

/**
 * DataTypeExistsException
 * 
 * @author samus250
 *
 */
public class DataTypeExistsException extends RuntimeException {

  public DataTypeExistsException() {
    super();
  }

  public DataTypeExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataTypeExistsException(String message) {
    super(message);
  }

  public DataTypeExistsException(Throwable cause) {
    super(cause);
  }

}
