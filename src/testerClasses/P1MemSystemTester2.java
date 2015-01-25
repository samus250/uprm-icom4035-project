package testerClasses;

import p1MemorySystem.P1MemSystem;

/**
 * This class tests the P1MemSystemClass
 * 
 * @author samus250
 *
 */
public class P1MemSystemTester2 {

  public static void main(String[] args) {
    final int MS = 96;
    P1MemSystem p1ms = new P1MemSystem(MS);
    p1ms.showMMASCII(0, MS - 1);
    P1MemSystemTester.testReserveMemory(p1ms, 96);
    p1ms.showMMASCII(0, MS - 1);

    P1MemSystemTester.testFreeMemory(p1ms, 22, 6);
    p1ms.showMMASCII(0, MS - 1);
    P1MemSystemTester.testFreeMemory(p1ms, 22, 6);
    p1ms.showMMASCII(0, MS - 1);

    P1MemSystemTester.testFreeMemory(p1ms, 20, 10);
    P1MemSystemTester.testFreeMemory(p1ms, 23, 10);
    P1MemSystemTester.testFreeMemory(p1ms, 60, 10);
    p1ms.showMMASCII(0, MS - 1);

    P1MemSystemTester.testFreeMemory(p1ms, 2, 3);
    p1ms.showMMASCII(0, MS - 1);

    P1MemSystemTester.testReserveMemory(p1ms, 20);
    p1ms.showMMASCII(0, MS - 1);

    P1MemSystemTester.testReserveMemory(p1ms, 50);
    p1ms.showMMASCII(0, MS - 1);
    P1MemSystemTester.testReserveMemory(p1ms, 10);
    p1ms.showMMASCII(0, MS - 1);
    P1MemSystemTester.testReserveMemory(p1ms, 10);
    p1ms.showMMASCII(5, 11);
  }

}
