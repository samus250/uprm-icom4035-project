package p3System.interfaces;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;

/**
 * This interface should be implemented by DataType validators, Specific data
 * types validate data in specific ways using these functions.
 * 
 * @author samus250
 *
 */
public interface DataTypeMDTProcessor {
  /**
   * Validates and writes a given String to a given address.
   * 
   * @param p1Sys
   *          the memory system to be written.
   * @param address
   *          the address of the memory system to where the data will be
   *          written.
   * @param value
   *          the value to be written.
   * @throws InvalidValueException
   *           whenever the given data is not valid.
   */
  void writeValueToMemory(P1MemSystem p1Sys, int address, String value)
      throws InvalidValueException;

  /**
   * Reads the value that is in a given address of memory.
   * 
   * @param p1Sys
   *          the memory system of where to read.
   * @param address
   *          the address of the memory system of where to read.
   * @return a string holding the value that was in the memory address.
   */
  String readValueFromMemory(P1MemSystem p1Sys, int address);
}
