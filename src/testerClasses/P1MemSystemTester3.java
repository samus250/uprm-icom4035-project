package testerClasses;

import memoryClasses.Register;
import p1MemorySystem.P1MemSystem;

/**
 * Professors P1MemSystemTester3
 * 
 * @author samus250
 *
 */
public class P1MemSystemTester3 {

  public static void main(String[] args) {

    final int MS = 64;
    P1MemSystem p1Sys = new P1MemSystem(MS);
    p1Sys.showMMASCII(0, MS - 1);
    Register r = new Register();
    r.setByte(0, (byte) '1');
    r.setByte(1, (byte) '1');
    r.setByte(2, (byte) '1');
    r.setByte(3, (byte) '1');
    System.out.println("Writing word on address 3...");
    p1Sys.writeWord(3, r);
    p1Sys.showMMASCII(0, 10);
    System.out.println("Writing byte at 23");
    p1Sys.writeByte(23, (byte) '1');
    p1Sys.showMMASCII(20, 25);
    System.out.println("Byte at 23 is " + (char) p1Sys.readByte(23));
    System.out.println("Byte at 4 is " + (char) p1Sys.readByte(4));

    for (int i = 0; i < MS; i++) {
      System.out.println("Byte at " + i + " is " + (char) p1Sys.readByte(i));
    }
  }

}
