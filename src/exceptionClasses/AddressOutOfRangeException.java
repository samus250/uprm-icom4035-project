package exceptionClasses;

/**
 * This class represents an Address Out Of Range Exception
 * 
 * @author samus250
 *
 */
public class AddressOutOfRangeException extends RuntimeException {

  public AddressOutOfRangeException() {

  }

  public AddressOutOfRangeException(String arg0) {
    super(arg0);
  }

  public AddressOutOfRangeException(Throwable arg0) {
    super(arg0);
  }

  public AddressOutOfRangeException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
