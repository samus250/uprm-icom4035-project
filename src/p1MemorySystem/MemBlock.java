package p1MemorySystem;

/**
 * This class implements a Memory Block: a pair of memory addresses.
 * 
 * @author samus250
 *
 */
public class MemBlock {
  private int firstAddress, lastAddress;

  /**
   * Constructor
   * 
   * @param fa
   *          - first address of the memory block.
   * @param la
   *          - last address of the memory block.
   */
  public MemBlock(int fa, int la) {
    firstAddress = fa;
    lastAddress = la;
  }

  /**
   * Returns the first address.
   * 
   * @return The first address.
   */
  public int getAddress() {
    return firstAddress;
  }

  /**
   * Sets the first address.
   * 
   * @param a
   *          - The first address.
   */
  public void setAddress(int a) {
    firstAddress = a;
  }

  /**
   * Returns the last address.
   * 
   * @return The last address.
   */
  public int getLastAddress() {
    return lastAddress;
  }

  /**
   * Sets the last address.
   * 
   * @param lastAddress
   *          - The last address.
   */
  public void setLastAddress(int lastAddress) {
    this.lastAddress = lastAddress;
  }

  /**
   * Returns the memory block size.
   * 
   * @return The size.
   */
  public int getSize() {
    return lastAddress - firstAddress + 1;
  }

  /**
   * Returns a string representation of the memory block.
   */
  public String toString() {
    return "[" + firstAddress + ", " + lastAddress + "]";
  }
}
