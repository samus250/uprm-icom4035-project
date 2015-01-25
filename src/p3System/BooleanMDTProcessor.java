package p3System;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;
import p3System.interfaces.DataTypeMDTProcessor;

/**
 * This is the MDTProcessor, which implements DataTypeMDTProcessor, for the
 * primitive boolean data type. See DataTypeMDTProcessor.
 * 
 * @author samus250
 *
 */
public class BooleanMDTProcessor implements DataTypeMDTProcessor {

  public void writeValueToMemory(P1MemSystem p1Sys, int address, String value)
      throws InvalidValueException {
    if (!isValid(value))
      throw new InvalidValueException("Value " + value + " does not match boolean data type.");

    boolean b = Boolean.parseBoolean(value);
    byte data = b ? (byte) 1 : (byte) 0;
    p1Sys.writeByte(address, data);

  }

  public String readValueFromMemory(P1MemSystem p1Sys, int address) {
    return (p1Sys.readByte(address) == 0) ? "false" : "true";
  }

  /**
   * Private inner class for validation purposes.
   * 
   * @param value
   *          the value to validate.
   * @return true if valid, false otherwise.
   */
  private boolean isValid(String value) {
    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false"))
      return false;
    else
      return true;
  }

}
