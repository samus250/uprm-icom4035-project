package p3System;

import java.util.HashMap;

import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.InvalidInstanceFieldException;
import p3System.exceptionClasses.InvalidValueException;

/**
 * This represents a ReferenceVariable. See Variable.
 * 
 * @author samus250
 *
 */
public class ReferenceVariable extends Variable {
  // this helps setValue know if it has already been initialized or not
  // helps garbage collection
  boolean initialized = false;
  HashMap<String, String> initializedInstanceFields;

  /**
   * Constructs a reference variable with the given and required information.
   * 
   * @param name
   *          the name of the reference variable.
   * @param type
   *          the type of the variable.
   * @param p1Sys
   *          the memory system of the variable.
   * @param address
   *          the address of the variable.
   */
  public ReferenceVariable(String name, DataType type, P1MemSystem p1Sys, int address) {
    super(name, type, p1Sys, address);
    this.setValue("null");
    initialized = true;
    initializedInstanceFields = new HashMap<String, String>();
  }

  /**
   * Returns the string value of the specified instance field.
   * 
   * @param fName
   *          the instance field's name.
   * @return the value of the instance field.
   * @throws InvalidInstanceFieldException
   *           whenever the specified instance field is invalid.
   */
  public String getInstanceFieldValue(String fName) throws InvalidInstanceFieldException {
    InstanceField ifvar = ((ObjectDataType) this.getDataType()).getInstanceField(fName);

    if (ifvar == null)
      throw new InvalidInstanceFieldException(
          "Invalid instance field in getInstanceFieldValue in ReferenceVariable class");

    int a = ifvar.getRelativeAddress() + Integer.parseInt(this.getValue()); // it
                                                                            // is
                                                                            // not
                                                                            // this.address!
    return ifvar.getDataType().readValueFromMemory(this.getMemorySystem(), a);
  }

  /**
   * Sets the value of the specified instance field.
   * 
   * @param fName
   *          the instance field to be set.
   * @param value
   *          the value to be set to the instance field.
   * @throws InvalidInstanceFieldException
   *           whenever the instance field is invalid.
   * @throws InvalidValueException
   *           whenever the value is not of the correct data type.
   */
  public void setInstanceFieldValue(String fName, String value)
      throws InvalidInstanceFieldException, InvalidValueException {
    // we might be setting null for the first time... if yes, we must skip
    // the rcf changes
    InstanceField ifvar = ((ObjectDataType) this.getDataType()).getInstanceField(fName);

    if (ifvar == null)
      throw new InvalidInstanceFieldException(
          "Invalid instance field in setInstanceFieldValue in ReferenceVariable class");

    // calculate appropriate instance field address (from relative)
    // System.out.println("Relative address for instance field " +
    // ifvar.getName() + " is " + ifvar.getRelativeAddress());
    int a = ifvar.getRelativeAddress() + Integer.parseInt(this.getValue());

    // if it is a reference instance field
    if (instanceFieldIsInitialized(fName)) {
      if (ifvar.getDataType() instanceof ObjectDataType) {
        // get the current value...
        int currentAddress = Integer.parseInt(ifvar.getDataType().readValueFromMemory(p1Sys, a));

        // if it contained an address of an object
        if (currentAddress != -1)
          decrementInstanceFieldRCF(ifvar);
      }
    } else {
      initializeInstanceField(fName);
    }

    // write
    ifvar.getDataType().writeValueToMemory(this.getMemorySystem(), a, value);

    // add one to the RCF of the new referenced object
    if (ifvar.getDataType() instanceof ObjectDataType) {
      // get the current value...
      int currentAddress = Integer.parseInt(ifvar.getDataType().readValueFromMemory(p1Sys, a));

      // if it contains an address of an object, not null
      if (currentAddress != -1)
        incrementInstanceFieldRCF(ifvar);
    }
  }

  @Override
  public void setValue(String value) throws InvalidValueException {
    setValue(value, true);
  }

  /**
   * Sets the value of the variable.
   * 
   * @param value
   *          the value of the variable (address to object).
   * @param modifyRCF
   *          true if the reference count field should be modified, false
   *          otherwise.
   * @throws InvalidValueException
   *           whenever the given value is invalid.
   */
  public void setValue(String value, boolean modifyRCF) throws InvalidValueException {
    // first get the current value
    int currentAddress = Integer.parseInt(this.getValue());

    if (currentAddress != -1 && initialized && modifyRCF)
      decrementRCF();

    // now set the new value
    type.writeValueToMemory(p1Sys, address, value);

    // and now modify the rcf value of the new pointed object
    currentAddress = Integer.parseInt(this.getValue());

    if (currentAddress != -1 && modifyRCF)
      incrementRCF();

  }

  // PRIVATE RCF HELPER METHODS

  /**
   * Increments the reference count field.
   */
  private void incrementRCF() {
    setRCF(getRCF() + 1);
  }

  /**
   * Decrements the reference count field, sends to garbage collection if it
   * reaches 0.
   */
  public void decrementRCF() {
    setRCF(getRCF() - 1);
    // if the current RCF is 0, then send the object to garbage collection
    if (getRCF() == 0) {
      ObjectDataType odt = (ObjectDataType) this.getDataType();
      odt.sendToGC(Integer.parseInt(this.getValue()));
    }
  }

  /**
   * Returns the reference count field.
   * 
   * @return the reference count field.
   */
  private int getRCF() {
    ObjectDataType odt = (ObjectDataType) this.getDataType();
    int objectSize = odt.getObjectSize();
    int rcfAddress = Integer.parseInt(this.getValue()) + objectSize;
    String rcf = type.readValueFromMemory(p1Sys, rcfAddress);
    return Integer.parseInt(rcf);
  }

  /**
   * Sets the reference count field.
   * 
   * @param r
   *          the reference count field.
   */
  private void setRCF(int r) {
    ObjectDataType odt = (ObjectDataType) this.getDataType();
    int objectSize = odt.getObjectSize();
    int rcfAddress = Integer.parseInt(this.getValue()) + objectSize;
    type.writeValueToMemory(p1Sys, rcfAddress, Integer.toString(r));
  }

  /**
   * Sets the given instance field's reference count field.
   * 
   * @param ifvar
   *          the instance field.
   * @param r
   *          the reference count field.
   */
  private void setInstanceFieldRCF(InstanceField ifvar, int r) {
    ObjectDataType odt = (ObjectDataType) ifvar.getDataType();
    int a = ifvar.getRelativeAddress() + Integer.parseInt(this.getValue());
    String objAddress = odt.readValueFromMemory(p1Sys, a);
    int rcfAddress = Integer.parseInt(objAddress) + odt.getObjectSize();

    odt.writeValueToMemory(p1Sys, rcfAddress, Integer.toString(r));
  }

  /**
   * Returns the given instance field's reference count field.
   * 
   * @param ifvar
   *          the instance field.
   * @return the reference count field.
   */
  private int getInstanceFieldRCF(InstanceField ifvar) {
    ObjectDataType odt = (ObjectDataType) ifvar.getDataType();
    int a = ifvar.getRelativeAddress() + Integer.parseInt(this.getValue());
    // fixed
    String objAddress = odt.readValueFromMemory(p1Sys, a);
    int rcfAddress = Integer.parseInt(objAddress) + odt.getObjectSize();

    return Integer.parseInt(odt.readValueFromMemory(p1Sys, rcfAddress));
  }

  /**
   * Decrements the given instance field's reference count field.
   * 
   * @param ifvar
   *          the instance field.
   */
  public void decrementInstanceFieldRCF(InstanceField ifvar) {
    setInstanceFieldRCF(ifvar, getInstanceFieldRCF(ifvar) - 1);
    // if the current RCF is 0, then send the object to garbage collection
    if (getInstanceFieldRCF(ifvar) == 0) {
      // get the object data type from the instance field
      ObjectDataType odt = (ObjectDataType) ifvar.getDataType();
      odt.sendToGC(Integer.parseInt(this.getValue()));
    }
  }

  /**
   * Increments the given instance field's reference count field.
   * 
   * @param ifvar
   *          the instance field.
   */
  private void incrementInstanceFieldRCF(InstanceField ifvar) {
    setInstanceFieldRCF(ifvar, getInstanceFieldRCF(ifvar) + 1);
  }

  /**
   * Tests the instance field for initialization.
   * 
   * @param fName
   *          the instance field to test.
   * @return true if it has been initialized, false otherwise.
   */
  private boolean instanceFieldIsInitialized(String fName) {
    return (initializedInstanceFields.get(fName) != null) ? true : false;
  }

  /**
   * Marks the instance field as initialized.
   * 
   * @param fName
   *          the instance field to initialize.
   */
  private void initializeInstanceField(String fName) {
    initializedInstanceFields.put(fName, fName);
  }
}
