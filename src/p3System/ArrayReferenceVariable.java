package p3System;

import memoryClasses.Register;
import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidValueException;

/**
 * This class represents an array reference variable.
 * 
 * @author samus250
 *
 */
public class ArrayReferenceVariable extends Variable {
  private DataType arrayType = null;
  private boolean initialized = false;
  private ArrayGarbageCollector arrayGC;

  /**
   * Constructs an array reference variable with the required and given
   * information.
   * 
   * @param name
   *          the name of the array reference variable.
   * @param type
   *          the type of the reference variable.
   * @param p1Sys
   *          the memory system of where this variable is being stored.
   * @param address
   *          the adddress where the variable is being stored in the memory
   *          system.
   * @param arrayGC
   *          an array garbage collector object.
   */
  public ArrayReferenceVariable(String name, DataType type, P1MemSystem p1Sys, int address,
      ArrayGarbageCollector arrayGC) {
    super(name, type, p1Sys, address);
    this.setValue("null");
    initialized = true;
    this.arrayGC = arrayGC;
  }

  /**
   * Constructs an array reference variable with the given and required
   * information.
   * 
   * @param name
   *          the name of the array reference variable.
   * @param type
   *          the type of the reference variable
   * @param p1Sys
   *          the memory system where the array is located.
   * @param address
   *          the address of where this variable is located.
   * @param arrayType
   *          the type of the elements in the array.
   */
  public ArrayReferenceVariable(String name, DataType type, P1MemSystem p1Sys, int address,
      DataType arrayType) {
    super(name, type, p1Sys, address);
    this.arrayType = arrayType;
    this.setValue("null");
  }

  /**
   * Sets the type of the elements in the array.
   * 
   * @param type
   *          the type of the elements in the array.
   */
  public void setArrayType(DataType type) {
    arrayType = type;
  }

  /**
   * Returns the type of the elements in the array.
   * 
   * @return the type of the elements in the array.
   */
  public DataType getArrayType() {
    return arrayType;
  }

  /**
   * Returns the length of the array.
   * 
   * @return the length of the array.
   */
  public int getLength() {
    // this.getValue is the address of the array, DO NOT use address, it is the
    // address of this
    Register reg = p1Sys.readWord(Integer.parseInt(this.getValue()));
    return reg.getIntFromRegister();
  }

  /**
   * Returns the element value in the given index.
   * 
   * @param index
   *          the index of the array.
   * @return the element at the given index.
   */
  public String getElementValue(int index) {
    int a = Integer.parseInt(this.getValue()) + 4 + index * arrayType.getSize();
    return arrayType.readValueFromMemory(p1Sys, a);
  }

  /**
   * Sets the given value in the given index.
   * 
   * @param index
   *          the index where to set the element value.
   * @param value
   *          the value of the element to set at the given index.
   */
  public void setElementValue(int index, String value) {
    int a = Integer.parseInt(this.getValue()) + 4 + index * arrayType.getSize();
    arrayType.writeValueToMemory(p1Sys, a, value);
  }

  // rcf coding
  @Override
  public void setValue(String value) throws InvalidValueException {
    setValue(value, true);
  }

  /**
   * Sets the value of the variable.
   * 
   * @param value
   *          the value of the reference variable (address of array).
   * @param modifyRCF
   *          true if the reference count field should be modified, false
   *          otherwise.
   * @throws InvalidValueException
   *           whenever the given value is invalid.
   */
  public void setValue(String value, boolean modifyRCF) throws InvalidValueException {
    // first get the current value
    int currentAddress = Integer.parseInt(this.getValue());

    if (currentAddress != -1 && initialized && modifyRCF) { // only if it has
                                                            // been initialized
      if (this.getName().equals("arr2"))
        System.out.println("I am decrementing arr2 rcf, current value is " + this.getValue()
            + ", current rcf is " + this.getRCF());
      decrementRCF();
    }

    // now set the new value
    type.writeValueToMemory(p1Sys, address, value);

    // and now modify the rcf value of the new pointed object
    currentAddress = Integer.parseInt(this.getValue());

    if (currentAddress != -1 && modifyRCF)
      incrementRCF();
  }

  // PRIVATE RCF HELPER METHODS
  /**
   * Increments the reference cound field of the array.
   */
  private void incrementRCF() {
    setRCF(getRCF() + 1);
  }

  /**
   * Decrements the reference count field of the array. If it reaches 0, it is
   * sent to garbage collection.
   */
  public void decrementRCF() {
    setRCF(getRCF() - 1);

    if (getRCF() == 0) {
      int address = Integer.parseInt(this.getValue());
      int lastAddress = address + 4 + this.getLength() * this.arrayType.getSize() + 4 - 1;
      arrayGC.sendArrayToGC(address, lastAddress);
    }
  }

  /**
   * Returns the reference count field of the array.
   * 
   * @return the reference count field of the array.
   */
  private int getRCF() {
    // get address of RCF
    int value = Integer.parseInt(this.getValue());
    int length = this.getLength();
    int sizeof = this.arrayType.getSize();
    int rcfAddress = value + 4 + length * sizeof;
    String sRCF = type.readValueFromMemory(p1Sys, rcfAddress);
    return Integer.parseInt(sRCF);
  }

  /**
   * Sets the reference count field of the array.
   * 
   * @param r
   *          the reference count field of the array.
   */
  public void setRCF(int r) {
    // get address of RCF
    int value = Integer.parseInt(this.getValue());
    int length = this.getLength();
    int sizeof = this.arrayType.getSize();
    int rcfAddress = value + 4 + length * sizeof;
    type.writeValueToMemory(p1Sys, rcfAddress, Integer.toString(r));
  }

}
