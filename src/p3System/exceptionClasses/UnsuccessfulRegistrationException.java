package p3System.exceptionClasses;

/**
 * UnsuccessfulRegistrationException
 * 
 * @author samus250
 *
 */
public class UnsuccessfulRegistrationException extends RuntimeException {

  public UnsuccessfulRegistrationException() {
  }

  public UnsuccessfulRegistrationException(String message) {
    super(message);
  }

  public UnsuccessfulRegistrationException(Throwable cause) {
    super(cause);
  }

  public UnsuccessfulRegistrationException(String message, Throwable cause) {
    super(message, cause);
  }

}
