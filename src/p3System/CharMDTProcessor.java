package p3System;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;
import p3System.interfaces.DataTypeMDTProcessor;

/**
 * This is the CharMDTProcessor, which implements the DataTypeMDTProcessor. See
 * DataTypeMDTProcessor.
 * 
 * @author samus250
 *
 */
public class CharMDTProcessor implements DataTypeMDTProcessor {

  public void writeValueToMemory(P1MemSystem p1Sys, int address, String value)
      throws InvalidValueException {
    if (value.length() != 1)
      throw new InvalidValueException("Value " + value + " does not match char data type.");

    p1Sys.writeByte(address, (byte) value.charAt(0));

  }

  public String readValueFromMemory(P1MemSystem p1Sys, int address) {
    char c = (char) p1Sys.readByte(address);
    return Character.toString(c);
  }

}
