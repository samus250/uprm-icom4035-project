package exceptionClasses;

/**
 * This class represents a SpaceNotReservedException
 * 
 * @author samus250
 *
 */
public class SpaceNotReservedException extends RuntimeException {

  public SpaceNotReservedException() {

  }

  public SpaceNotReservedException(String message) {
    super(message);
  }

  public SpaceNotReservedException(Throwable cause) {
    super(cause);
  }

  public SpaceNotReservedException(String message, Throwable cause) {
    super(message, cause);
  }

}
