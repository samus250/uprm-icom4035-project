package p3System.exceptionClasses;

/**
 * EmptyQueueException.
 * 
 * @author samus250
 *
 */
public class EmptyQueueException extends RuntimeException {

  public EmptyQueueException() {
  }

  public EmptyQueueException(String arg0) {
    super(arg0);
  }

  public EmptyQueueException(Throwable arg0) {
    super(arg0);
  }

  public EmptyQueueException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
