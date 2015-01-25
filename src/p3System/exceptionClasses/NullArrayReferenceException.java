package p3System.exceptionClasses;

/**
 * NullArrayReferenceException
 * 
 * @author samus250
 *
 */
public class NullArrayReferenceException extends RuntimeException {

  public NullArrayReferenceException() {

  }

  public NullArrayReferenceException(String message) {
    super(message);
  }

  public NullArrayReferenceException(Throwable cause) {
    super(cause);
  }

  public NullArrayReferenceException(String message, Throwable cause) {
    super(message, cause);
  }

}
