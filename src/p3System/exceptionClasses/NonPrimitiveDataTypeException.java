package p3System.exceptionClasses;

/**
 * NonPrimitiveDataTypeException
 * 
 * @author samus250
 *
 */
public class NonPrimitiveDataTypeException extends RuntimeException {

  public NonPrimitiveDataTypeException() {
  }

  public NonPrimitiveDataTypeException(String arg0) {
    super(arg0);
  }

  public NonPrimitiveDataTypeException(Throwable arg0) {
    super(arg0);
  }

  public NonPrimitiveDataTypeException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

}
