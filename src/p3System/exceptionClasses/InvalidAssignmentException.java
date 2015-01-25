package p3System.exceptionClasses;

/**
 * InvalidAssignmentException.
 * 
 * @author samus250
 *
 */
public class InvalidAssignmentException extends RuntimeException {

  public InvalidAssignmentException() {
  }

  public InvalidAssignmentException(String arg0) {
    super(arg0);
  }

  public InvalidAssignmentException(Throwable arg0) {
    super(arg0);
  }

  public InvalidAssignmentException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
