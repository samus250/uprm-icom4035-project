package testerClasses;

import java.util.Random;

import memoryClasses.Memory;
import memoryClasses.Register;

/**
 * Professor's memory tester.
 * 
 * @author samus250
 *
 */
public class MemoryTester {

  public static void main(String[] args) {
    final int N = 10;
    Memory mem = new Memory();
    final Register dr = new Register();

    int address[] = new int[N];
    int value[] = new int[N];

    Random rnd = new Random();

    for (int i = 0; i < N; i++) {
      address[i] = Math.abs(rnd.nextInt() % (mem.getCapacity() / 4 + 200) * 4);
      value[i] = rnd.nextInt();
    }

    for (int i = 0; i < N; i++) {
      try {
        System.out.println("\nValue to write to mem[" + address[i] + "] = "
            + Integer.toBinaryString(value[i]) + " = " + value[i]);
        dr.copyIntToRegister(value[i]);
        mem.setAddress(address[i]);
        mem.setInput(dr);
        mem.write();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    for (int i = 0; i < N; i++) {

      try {
        mem.setAddress(address[i]);
        mem.read();
        int v = mem.getOutput().getIntFromRegister();
        System.out.println("\nValue read from mem[" + address[i] + "] = "
            + Integer.toBinaryString(v) + " = " + v);

      } catch (Exception e) {
        System.out.println(e);
      }
    }

  }
}
