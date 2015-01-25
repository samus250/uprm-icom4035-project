package exceptionClasses;

/**
 * This class represents a BlockBoundaryViolationException
 * 
 * @author samus250
 *
 */
public class BlockBoundaryViolationException extends RuntimeException {

  public BlockBoundaryViolationException() {

  }

  public BlockBoundaryViolationException(String message) {
    super(message);
  }

  public BlockBoundaryViolationException(Throwable cause) {
    super(cause);
  }

  public BlockBoundaryViolationException(String message, Throwable cause) {
    super(message, cause);
  }

}
