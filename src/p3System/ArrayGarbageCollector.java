package p3System;

import java.util.ArrayList;
import java.util.HashMap;

import p1MemorySystem.MemBlock;
import p1MemorySystem.P1MemSystem;

/**
 * This is an array garbage collector object. It holds the necessary information
 * to efficiently and effectively destroy inactive arrays on a given system.
 * 
 * @author samus250
 *
 */
public class ArrayGarbageCollector {
  HashMap<Integer, Integer> registeredArrays;
  NBQueue<MemBlock> arraysToDelQueue;

  /**
   * Crates an ArrayGarbageCollector object.
   */
  public ArrayGarbageCollector() {
    registeredArrays = new HashMap<Integer, Integer>();
    arraysToDelQueue = new NBQueue<MemBlock>();
  }

  /**
   * Returns an ArrayList of the currently registered arrays.
   * 
   * @return an ArrayList containing the addresses of currently registered
   *         arrays.
   */
  public ArrayList<Integer> getRegisteredArrays() {
    ArrayList<Integer> atr = new ArrayList<Integer>();
    for (Integer i : registeredArrays.values())
      atr.add(i);
    return atr;
  }

  /**
   * Performs garbage collection on the given memory system.
   * 
   * @param p1Sys
   *          the memory system where to perform garbage collection.
   */
  public void gc(P1MemSystem p1Sys) {
    while (!arraysToDelQueue.isEmpty()) {
      int add = arraysToDelQueue.front().getAddress();

      // remove add from registered objects
      removeRegisteredArray(add);

      // release the space from p1Sys...
      p1Sys.freeMemory(add, arraysToDelQueue.front().getLastAddress() - add + 1);

      arraysToDelQueue.dequeue();
    }
  }

  /**
   * Register an array.
   * 
   * @param address
   *          the address where the new array has been registered.
   */
  public void registerArray(int address) {
    registeredArrays.put(address, address);
  }

  /**
   * Removes a registered array.
   * 
   * @param address
   *          the address of where the array to be removed is.
   */
  public void removeRegisteredArray(int address) {
    registeredArrays.remove(address);
  }

  /**
   * Send a given array to garbage collection.
   * 
   * @param address
   *          the address of the array to send to garbage collection.
   * @param lastAddress
   *          the address of the last byte of the array to send to garbage
   *          collection.
   */
  public void sendArrayToGC(int address, int lastAddress) {
    if (arraysToDelQueue.isEmpty())
      arraysToDelQueue.enqueue(new MemBlock(address, lastAddress));
    else if (arraysToDelQueue.front().getAddress() != address) // do not enqueue
                                                               // same address
      arraysToDelQueue.enqueue(new MemBlock(address, lastAddress));
  }
}
