package testerClasses;

import memoryClasses.Memory;
import memoryClasses.Register;

/**
 * Professor's second memory tester.
 * 
 * @author samus250
 *
 */
public class MemoryTester2 {

  public static void main(String[] args) {
    Memory mem = new Memory();
    final Register dr = new Register();

    for (int value = 60000, address = 0; value < 60004; value++, address += 16) {
      System.out.println("Value written to mem[" + address + "] = " + Integer.toBinaryString(value)
          + " = " + value);
      dr.copyIntToRegister(value);

      mem.setAddress(address);
      mem.setInput(dr);
      mem.write();
    }

    for (int i = 0, address = 0; i < 4; i++, address += 16) {
      mem.setAddress(address);
      mem.read();
      int value = mem.getOutput().getIntFromRegister();
      System.out.println("Value read from mem[" + address + "] = " + Integer.toBinaryString(value)
          + " = " + value);
    }

  }

}
