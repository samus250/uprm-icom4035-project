package p3System.exceptionClasses;

/**
 * NotNullValueException.
 * 
 * @author samus250
 *
 */
public class NotNullValueException extends RuntimeException {

  public NotNullValueException() {
  }

  public NotNullValueException(String arg0) {
    super(arg0);
  }

  public NotNullValueException(Throwable arg0) {
    super(arg0);
  }

  public NotNullValueException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
