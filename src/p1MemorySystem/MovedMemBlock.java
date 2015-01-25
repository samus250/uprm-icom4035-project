package p1MemorySystem;

/**
 * This class extends a MemBlock, and represents a MemBlock that has been moved
 * to another location.
 * 
 * @author samus250
 *
 */
public class MovedMemBlock extends MemBlock {
  private int oldAddress;

  /**
   * Constructs a MovedMemBlock with given first and last addresses.
   * 
   * @param fa
   *          the first address.
   * @param la
   *          the last address.
   */
  public MovedMemBlock(int fa, int la) {
    super(fa, la);
  }

  /**
   * Returns the old (previous) address of the MemBlock.
   * 
   * @return the old (previous) address of the MemBlock.
   */
  public int getOldAddress() {
    return oldAddress;
  }

  /**
   * Sets the old (previous) address of the MemBlock.
   * 
   * @param oldFirstAddress
   *          the old (previous) address of the MemBlock.
   */
  public void setOldAddress(int oldFirstAddress) {
    this.oldAddress = oldFirstAddress;
  }

}
