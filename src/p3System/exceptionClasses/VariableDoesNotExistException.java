package p3System.exceptionClasses;

/**
 * VariableDoesNotExistException
 * 
 * @author samus250
 *
 */
public class VariableDoesNotExistException extends RuntimeException {

  public VariableDoesNotExistException() {
  }

  public VariableDoesNotExistException(String message) {
    super(message);
  }

  public VariableDoesNotExistException(Throwable cause) {
    super(cause);
  }

  public VariableDoesNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

}
