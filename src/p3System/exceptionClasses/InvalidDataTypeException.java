package p3System.exceptionClasses;

/**
 * InvalidDataTypeException
 * 
 * @author samus250
 *
 */
public class InvalidDataTypeException extends RuntimeException {

  public InvalidDataTypeException() {
  }

  public InvalidDataTypeException(String message) {
    super(message);
  }

  public InvalidDataTypeException(Throwable cause) {
    super(cause);
  }

  public InvalidDataTypeException(String message, Throwable cause) {
    super(message, cause);
  }

}
