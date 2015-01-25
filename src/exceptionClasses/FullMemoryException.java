package exceptionClasses;

/**
 * This class represents a FullMemoryException
 * 
 * @author samus250
 *
 */
public class FullMemoryException extends RuntimeException {

  public FullMemoryException() {
    super();
  }

  public FullMemoryException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public FullMemoryException(String arg0) {
    super(arg0);
  }

  public FullMemoryException(Throwable arg0) {
    super(arg0);
  }

}
