package p3System;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;

/**
 * This represents a Variable.
 * 
 * @author samus250
 *
 */
public class Variable implements Comparable<Variable> {
  protected final String name; // name of the variable
  protected final DataType type; // data type of the variable
  protected P1MemSystem p1Sys; // memory system this variable lives in
  protected int address; // its memory address

  /**
   * Constructs a variable with the given information.
   * 
   * @param name
   *          the name of the variable.
   * @param type
   *          the type of the variable.
   * @param p1Sys
   *          the memory where the variable is located.
   * @param address
   *          the address in memory where variable is located.
   */
  public Variable(String name, DataType type, P1MemSystem p1Sys, int address) {
    this.name = name;
    this.type = type;
    this.p1Sys = p1Sys; // associated memory
    this.address = address; // address in memory
  }

  /**
   * Returns the name of the variable.
   * 
   * @return the name of the variable.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the address of the variable.
   * 
   * @return the address of the variable.
   */
  public int getAddress() {
    return address;
  }

  /**
   * Sets the address of the variable.
   * 
   * @param a
   *          the address of the variable.
   */
  public void setAddress(int a) {
    address = a;
  }

  /**
   * Returns the memory system.
   * 
   * @return the memory system.
   */
  public P1MemSystem getMemorySystem() {
    return p1Sys;
  }

  /**
   * Returns the data type of the variable.
   * 
   * @return the data type of the variable.
   */
  public DataType getDataType() {
    return type;
  }

  /**
   * Stores the given value in the memory area assigned to the variable.
   * 
   * @param value
   *          the value to store.
   * @throws InvalidValueException
   *           whenever the value is of invalid data type.
   */
  public void setValue(String value) throws InvalidValueException {
    type.writeValueToMemory(p1Sys, address, value);
  }

  /**
   * Returns the value of the variable.
   * 
   * @return the value of the variable.
   */
  public String getValue() {
    return type.readValueFromMemory(p1Sys, address);
  }

  /**
   * Compares the variable by name.
   */
  public int compareTo(Variable other) {
    return this.name.compareTo(other.getName());
  }

  public String toString() {
    return this.getName() + "\t" + this.getDataType().toString() + "\t" + this.getAddress() + "\t"
        + this.getValue();
  }
}
