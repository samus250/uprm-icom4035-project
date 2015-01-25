package p3System;

/**
 * This class represents and internal instance field of objects.
 * 
 * @author samus250
 *
 */
public class InstanceField implements Comparable<InstanceField> {
  private final String name; // the name of the variable (instance field)
  private final DataType type; // data type of the variable
  private int relativeAddress; // its relative address inside the block of
                               // memory

  // allocated by the kind of object...

  public InstanceField(String name, DataType type) {
    this.name = name;
    this.type = type;
  }

  /**
   * Returns the name of the instance field.
   * 
   * @return the name of the instance field.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the address of this instance field relative to the object.
   * 
   * @return the address of this instance field relative to the object.
   */
  public int getRelativeAddress() {
    return relativeAddress;
  }

  /**
   * Sets the address of this instance field relative to the object.
   * 
   * @param relativeAddress
   *          the relative address of this instance field.
   */
  public void setRelativeAddress(int relativeAddress) {
    this.relativeAddress = relativeAddress;
  }

  /**
   * Returns the data type of this instance field.
   * 
   * @return the data type of this instance field.
   */
  public DataType getDataType() {
    // returns reference to the data type object that
    // describes the data type of this instance field
    return type;
  }

  public int compareTo(InstanceField other) {
    return this.name.compareTo(other.getName());
  }

}
