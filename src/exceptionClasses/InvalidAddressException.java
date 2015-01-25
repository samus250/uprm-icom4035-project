package exceptionClasses;

/**
 * This class represents an Invalid Address Exception
 * 
 * @author samus250
 *
 */
public class InvalidAddressException extends RuntimeException {

  public InvalidAddressException() {
  }

  public InvalidAddressException(String arg0) {
    super(arg0);
  }

}
