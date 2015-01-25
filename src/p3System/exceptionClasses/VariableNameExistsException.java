package p3System.exceptionClasses;

/**
 * VariableNameExistsException
 * 
 * @author samus250
 *
 */
public class VariableNameExistsException extends RuntimeException {

  public VariableNameExistsException() {
  }

  public VariableNameExistsException(String message) {
    super(message);
  }

  public VariableNameExistsException(Throwable cause) {
    super(cause);
  }

  public VariableNameExistsException(String message, Throwable cause) {
    super(message, cause);
  }

}
