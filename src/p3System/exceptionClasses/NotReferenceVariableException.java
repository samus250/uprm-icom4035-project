package p3System.exceptionClasses;

/**
 * NotReferenceVariableException
 * 
 * @author samus250
 *
 */
public class NotReferenceVariableException extends RuntimeException {

  public NotReferenceVariableException() {
  }

  public NotReferenceVariableException(String message) {
    super(message);
  }

  public NotReferenceVariableException(Throwable cause) {
    super(cause);
  }

  public NotReferenceVariableException(String message, Throwable cause) {
    super(message, cause);
  }

}
