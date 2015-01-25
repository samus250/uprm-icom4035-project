package testerClasses;

import p1MemorySystem.MSMControlStructure;

/**
 * Professor's memoroy control structure tester.
 * 
 * @author samus250
 *
 */
public class MemControlStructureTester {

  public static void main(String[] args) {
    MSMControlStructure mcs = new MSMControlStructure(50);
    mcs.showFreeBlocks();
    mcs.showReservedBlocks();
    testReserveSpace(mcs, 50);
    testFreeSpace(mcs, 0, 49);
    testReserveSpace(mcs, 10);
    testFreeSpace(mcs, 4, 4);
    testReserveSpace(mcs, 20);
    testFreeSpace(mcs, 18, 5);
    testFreeSpace(mcs, 30, 5);
    testFreeSpace(mcs, 10, 5);
    testFreeSpace(mcs, 0, 5);
    testFreeSpace(mcs, 60, 5);
    testFreeSpace(mcs, 18, 5);
    testFreeSpace(mcs, 10, 4);
    testReserveSpace(mcs, 30);
    testFreeSpace(mcs, 40, 14);
    testFreeSpace(mcs, 40, 10);
    testFreeSpace(mcs, 5, 5);
    testFreeSpace(mcs, 14, 1);
    testFreeSpace(mcs, 20, 19);
    testFreeSpace(mcs, 39, 5);
    testFreeSpace(mcs, 39, 1);
    testReserveSpace(mcs, 7);
    testReserveSpace(mcs, 9);
    testFreeSpace(mcs, 37, 6);
    testFreeSpace(mcs, 39, 1);
    testReserveSpace(mcs, 7);
    testReserveSpace(mcs, 50);
    testFreeSpace(mcs, 20, 31);
    testFreeSpace(mcs, 20, 30);
    testReserveSpace(mcs, 7);
    testReserveSpace(mcs, 1);
    testFreeSpace(mcs, 20, 0);
    testFreeSpace(mcs, 0, 3);
    testFreeSpace(mcs, 6, 1);
    testFreeSpace(mcs, 18, 7);
    testFreeSpace(mcs, 31, 3);
    testReserveSpace(mcs, 10);
    testReserveSpace(mcs, 10);
    testFreeSpace(mcs, 24, 8);
    testFreeSpace(mcs, 24, 14);
    testFreeSpace(mcs, 23, 5);
    testFreeSpace(mcs, 13, 2);
    testFreeSpace(mcs, 49, 1);
    testFreeSpace(mcs, 0, 1);
    mcs.showFreeBlocks();
    mcs.showReservedBlocks();
    testFreeSpace(mcs, 3, 3);
    testFreeSpace(mcs, 7, 2);
    testReserveSpace(mcs, 9);
    testReserveSpace(mcs, 3);
    testReserveSpace(mcs, 3);
    testReserveSpace(mcs, 3);
    testReserveSpace(mcs, 4);
    testFreeSpace(mcs, 49, 2);
    testFreeSpace(mcs, 49, 3);
    testFreeSpace(mcs, 49, 1);
    testFreeSpace(mcs, 47, 3);
    testFreeSpace(mcs, 49, 7);
  }

  protected static void testReserveSpace(MSMControlStructure mcs, int size) {
    int nbAddress; // address of the reserved block
    System.out.println("Trying to reserve " + size + " bytes ");
    try {
      nbAddress = mcs.reserveMemSpace(size);
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    if (nbAddress != -1) {
      System.out.print("Success!! @adress " + nbAddress + ". ");
      mcs.showFreeBlocks();
      mcs.showReservedBlocks();
    } else
      System.out.println("No block available for " + size + " bytes.");
  }

  protected static void testFreeSpace(MSMControlStructure mcs, int address, int size) {
    System.out.println("Trying to release space of size " + size + " bytes, at address " + address
        + ". ");
    try {
      mcs.freeSpace(address, size);
    } catch (Exception e) {
      System.out.println(e);
      return;
    }
    System.out.println("Success!!");
    mcs.showFreeBlocks();
    mcs.showReservedBlocks();
  }
}
