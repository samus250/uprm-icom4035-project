package p3System.exceptionClasses;

/**
 * NotPrimitiveDataTypeException.
 * 
 * @author samus250
 *
 */
public class NotPrimitiveDataTypeException extends RuntimeException {

  public NotPrimitiveDataTypeException() {
  }

  public NotPrimitiveDataTypeException(String message) {
    super(message);
  }

  public NotPrimitiveDataTypeException(Throwable cause) {
    super(cause);
  }

  public NotPrimitiveDataTypeException(String message, Throwable cause) {
    super(message, cause);
  }

}
