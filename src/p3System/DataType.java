package p3System;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;
import p3System.interfaces.DataTypeMDTProcessor;

/**
 * This class represents a data type.
 * 
 * @author samus250
 *
 */
public class DataType implements Comparable<DataType> {
  public static final int SIZEOFINT = 4; // 4 bytes
  public static final int SIZEOFCHAR = 1;
  public static final int SIZEOFBOOLEAN = 1;
  private final String name;
  private final int size;
  private DataTypeMDTProcessor dtp;

  // if null, datatype needs no validator...

  public DataType(String name, int size, DataTypeMDTProcessor dtp) {
    this.name = name;
    this.size = size;
    this.dtp = dtp;
  }

  /**
   * Returns the name of the data type.
   * 
   * @return the name of the data type.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the size of the data type.
   * 
   * @return the size of the data type.
   */
  public int getSize() {
    return size;
  }

  /**
   * Compares this data type to a Variable.
   * 
   * @param other
   *          the Variable to compare.
   * @return -1 if smaller, 1 if larger, 0 if equal
   */
  public int compareTo(Variable other) {
    return this.name.compareTo(other.getName());
  }

  public int compareTo(DataType other) {
    return this.name.compareTo(other.name);
  }

  /**
   * Tests for equality.
   * 
   * @param other
   *          the other data type to test equality.
   * @return true if both data types are equal, false otherwise.
   */
  public boolean equals(DataType other) {
    return this.name.equals(other.name);
  }

  /**
   * Writes the given value to a memory address at a memory system.
   * 
   * @param p1Sys
   *          the memory system where the value will be written.
   * @param address
   *          the address of the memory system where the value will be written.
   * @param value
   *          a string value of the value that will be written.
   * @throws InvalidValueException
   *           whenever the value is invalid for this data type.
   */
  public void writeValueToMemory(P1MemSystem p1Sys, int address, String value)
      throws InvalidValueException {
    // pre: address is valid for the given memory
    dtp.writeValueToMemory(p1Sys, address, value);
  }

  /**
   * Reads the value stored in memory from the given memory system and address.
   * 
   * @param p1Sys
   *          the memory system from where to read the value.
   * @param address
   *          the address of the memory system from where to read the value.
   * @return a string representation of the value in the memory address.
   */
  public String readValueFromMemory(P1MemSystem p1Sys, int address) {
    // pre: address is valid for the given memory
    return dtp.readValueFromMemory(p1Sys, address);
  }

  public String toString() {
    return "<" + this.getName() + ">";
  }
}
