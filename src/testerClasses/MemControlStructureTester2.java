package testerClasses;

import p1MemorySystem.MSMControlStructure;

/**
 * This class tests the MSMControlStructureClass
 * 
 * @author samus250
 *
 */
public class MemControlStructureTester2 {

  public static void main(String[] args) {
    MSMControlStructure mcs = new MSMControlStructure(50);
    mcs.showFreeBlocks();
    MemControlStructureTester.testReserveSpace(mcs, 30);
    MemControlStructureTester.testFreeSpace(mcs, 25, 5);
    MemControlStructureTester.testFreeSpace(mcs, 0, 25);
    MemControlStructureTester.testReserveSpace(mcs, 50);
    MemControlStructureTester.testFreeSpace(mcs, 49, 1);
    MemControlStructureTester.testReserveSpace(mcs, 1);
    MemControlStructureTester.testReserveSpace(mcs, 1);
    MemControlStructureTester.testFreeSpace(mcs, 0, 50);
    MemControlStructureTester.testReserveSpace(mcs, 50);
    MemControlStructureTester.testFreeSpace(mcs, 20, 5);
    MemControlStructureTester.testFreeSpace(mcs, 30, 10);
    MemControlStructureTester.testFreeSpace(mcs, 40, 11);
    MemControlStructureTester.testFreeSpace(mcs, 40, 10);
    MemControlStructureTester.testFreeSpace(mcs, 25, 5);
    MemControlStructureTester.testReserveSpace(mcs, 30);
    MemControlStructureTester.testFreeSpace(mcs, 40, 10);
    MemControlStructureTester.testFreeSpace(mcs, 30, 5);
    MemControlStructureTester.testFreeSpace(mcs, 20, 5);
    MemControlStructureTester.testFreeSpace(mcs, 10, 5);
    MemControlStructureTester.testFreeSpace(mcs, 0, 5);
    MemControlStructureTester.testReserveSpace(mcs, 5);
    MemControlStructureTester.testReserveSpace(mcs, 5);
    MemControlStructureTester.testReserveSpace(mcs, 5);
    MemControlStructureTester.testReserveSpace(mcs, 5);
    MemControlStructureTester.testReserveSpace(mcs, 5);
    MemControlStructureTester.testFreeSpace(mcs, 40, 4);
    MemControlStructureTester.testFreeSpace(mcs, 44, 1);
    MemControlStructureTester.testReserveSpace(mcs, 10);
    for (int i = 49; i >= 0; i -= 2)
      MemControlStructureTester.testFreeSpace(mcs, i, 1);
    for (int i = 0; i < 50; i += 2)
      MemControlStructureTester.testFreeSpace(mcs, i, 1);

    for (int i = 0; i < 50; i += 2)
      MemControlStructureTester.testReserveSpace(mcs, 2);
  }

}
