package p3System;

import memoryClasses.Register;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;
import p3System.interfaces.DataTypeMDTProcessor;

/**
 * This is the IntMDTProcessor validator, wich implements DataTypeMDTProcessor.
 * See DataTypeMDTProcessor.
 * 
 * @author samus250
 *
 */
public class IntMDTProcessor implements DataTypeMDTProcessor {

  public void writeValueToMemory(P1MemSystem p1Sys, int address, String value)
      throws InvalidValueException {
    if (value.equalsIgnoreCase("null"))
      value = "-1";

    try {
      int i = Integer.parseInt(value);
      Register r = new Register();
      r.copyIntToRegister(i);
      p1Sys.writeWord(address, r);

    } catch (NumberFormatException e) {
      throw new InvalidValueException("Value " + value + " does not match int data type.");
    }

  }

  public String readValueFromMemory(P1MemSystem p1Sys, int address) {
    Register r = p1Sys.readWord(address);
    return "" + r.getIntFromRegister();
  }

}
