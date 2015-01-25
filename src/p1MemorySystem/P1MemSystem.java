package p1MemorySystem;

import java.util.ArrayList;

import exceptionClasses.BlockBoundaryViolationException;
import exceptionClasses.FullMemoryException;
import memoryClasses.Memory;
import memoryClasses.Register;
import interfaces.MemorySystem;

/**
 * This class implements a memory system and it's basic functions.
 * 
 * @author samus250
 *
 */
public class P1MemSystem implements MemorySystem {
  private static final int DEFMEMSIZE = 64;
  private static final char FREE_CHAR = '0';
  private static final char RESERVED_CHAR = '1';
  private Memory mem; // the memory
  private MSMControlStructure mcs; // ADT to manage free space
  private int reservedSpace; // for FullMemoryException

  /**
   * Default Constructor.
   */
  public P1MemSystem() {
    this(DEFMEMSIZE);
  }

  /**
   * Size based constructor, Constructs a P1MemSystem of the given size.
   * 
   * @param msize
   */
  public P1MemSystem(int msize) {
    if (msize <= 0)
      msize = DEFMEMSIZE;

    mem = new Memory(msize);
    mcs = new MSMControlStructure(msize);
    reservedSpace = 0;

    // init memory to free
    writeBlock(0, mem.getCapacity() - 1, (byte) FREE_CHAR);
  }

  public int reserveMemory(int size) throws FullMemoryException {
    if (reservedSpace >= mem.getCapacity())
      throw new FullMemoryException("Memory is Full");

    int blockAddress = -1;

    try {
      blockAddress = mcs.reserveMemSpace(size);
      if (blockAddress > -1) { // -1 means no space was reserved
        writeBlock(blockAddress, blockAddress + size - 1, (byte) RESERVED_CHAR);
        reservedSpace += size;
      }
    } catch (Exception e) {
      System.out.println(e);
    }

    return blockAddress;
  }

  public void freeMemory(int address, int size) {
    try {
      mcs.freeSpace(address, size);
      writeBlock(address, address + size - 1, (byte) FREE_CHAR);
      reservedSpace -= size;
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void showMMASCII(int initialAddress, int finalAddress) {
    System.out.println("Address\tContent");

    // initial word address must be multiple of Memory.WORDSIZE
    int initialWord = initialAddress - initialAddress % Memory.WORDSIZE;

    for (int i = initialWord; i < mem.getCapacity(); i += Memory.WORDSIZE) {
      // store word address in MAR
      mem.setAddress(i);

      // read to output register and get the register
      mem.read();
      Register output = mem.getOutput();

      // only display necessary bytes
      for (int j = 0; j < Memory.WORDSIZE; j++) {
        int currentByte = i + j;
        if (initialAddress <= currentByte && currentByte <= finalAddress)
          System.out.println(currentByte + "\t" + (char) output.readByte(j));
      }
    }
  }

  /**
   * Private helper method that writes the given byte to every byte in the
   * memory block delimited by the initialAddress and finalAddress.
   * 
   * @param initialAddress
   *          - Initial address of the block to be written.
   * @param finalAddress
   *          - Final address of the block to be written.
   * @param data
   *          - The byte to be written to every byte in the specified block.
   */
  private void writeBlock(int initialAddress, int finalAddress, byte data) {
    // since it will write the same byte to the whole block, it is more
    // efficient
    // this way than using writeByte or readByte methods in a loop...

    for (int i = 0; i < mem.getCapacity(); i += Memory.WORDSIZE) {
      // copy address to MAR
      mem.setAddress(i);

      // create register to write at address from MAR
      Register regToWrite = new Register();
      for (int j = 0; j < Memory.WORDSIZE; j++) {
        // set register's bytes
        int currentByte = i + j;
        if (initialAddress <= currentByte && currentByte <= finalAddress) { // byte
                                                                            // is
                                                                            // to
                                                                            // be
                                                                            // written
          // put given data in this byte
          regToWrite.setByte(j, data);
        } else {
          mem.read();
          regToWrite.setByte(j, mem.getOutput().readByte(j)); // put what is
                                                              // already in
                                                              // memory
        }
      }

      // copy data to input register and write
      mem.setInput(regToWrite);
      mem.write();
    }
  }

  /**
   * Returns the byte that is in the given address.
   * 
   * @param address
   *          the address of the byte to access.
   * @return the byte at the given address.
   */
  public byte readByte(int address) {
    // set address in MAR
    mem.setAddress(address - address % Memory.WORDSIZE);

    // read word to MOR
    mem.read();

    // get word and read the byte from it
    return mem.getOutput().readByte(address % Memory.WORDSIZE);
  }

  /**
   * Writes a given byte to a given address in memory.
   * 
   * @param address
   *          the address where to write the byte.
   * @param data
   *          the byte to write at the given address.
   */
  public void writeByte(int address, byte data) {
    // follow comments in readByte
    mem.setAddress(address - address % Memory.WORDSIZE);
    mem.read();
    Register r = mem.getOutput();
    r.setByte(address % Memory.WORDSIZE, data);
    mem.setInput(r);
    mem.write();
  }

  /**
   * Writes a given word to a given address.
   * 
   * @param address
   *          the address where to write the given word.
   * @param regToWrite
   *          the register (or word) to write.
   * @throws BlockBoundaryViolationException
   *           whenever the given address is out of bounds.
   */
  public void writeWord(int address, Register regToWrite) throws BlockBoundaryViolationException {
    if (address < 0 || address + Memory.WORDSIZE - 1 >= mem.getCapacity())
      throw new BlockBoundaryViolationException("Block is out of boundary of current memory.");

    if (address % Memory.WORDSIZE == 0) {
      // only one word is affected
      // just need to write it to register
      mem.setAddress(address);
      mem.setInput(regToWrite);
      mem.write();
    } else {
      // two words are affected, acquire addresses
      int firstWord = address - address % Memory.WORDSIZE;
      int lastWord = firstWord + Memory.WORDSIZE;

      // read first register
      mem.setAddress(firstWord);
      mem.read();
      Register r = mem.getOutput();

      int indexRegToWrite = 0; // index of the given register...
      int rIndex = address % Memory.WORDSIZE; // index of the register from
                                              // memory

      while (rIndex != 0) {
        // set this byte on the memory register from the given register
        r.setByte(rIndex, regToWrite.readByte(indexRegToWrite));
        rIndex = (rIndex + 1) % Memory.WORDSIZE; // if rIndex = 0, we are on
                                                 // lastReg...
        indexRegToWrite++;
        // profesor puso indexRegToWrite = (indexRegToWrite + 1) %
        // Memory.WORDSIZE
        // no creo que sea necesario
      }
      mem.setInput(r);
      mem.write();

      // now modify bytes from the second word
      mem.setAddress(lastWord);
      mem.read();
      r = mem.getOutput();

      while (indexRegToWrite != Memory.WORDSIZE) {
        r.setByte(rIndex, regToWrite.readByte(indexRegToWrite));
        rIndex++;
        indexRegToWrite++;
      }
      mem.setInput(r);
      mem.write();
    }
  }

  /**
   * Returns the word from the given address.
   * 
   * @param address
   *          the address where to look for the word.
   * @return the word from the given address.
   */
  public Register readWord(int address) {
    Register r = new Register();
    for (int i = 0; i < Memory.WORDSIZE; i++) {
      r.setByte(i, this.readByte(address + i));
    }
    return r;
  }

  /**
   * Compacts the memory and returns an iterable collection of blocks that have
   * been moved.
   * 
   * @return an iterable collection of blocks that have been moved after
   *         compaction.
   */
  public Iterable<MovedMemBlock> compactMemory() {
    ArrayList<MovedMemBlock> movedBlocks = new ArrayList<MovedMemBlock>();
    int available = 0;
    boolean firstTime = false;
    for (MemBlock b : mcs.reservedMemBlocks()) {
      if (firstTime && b.getAddress() == 0) {
        available = b.getSize();
      } else {
        movedBlocks.add(moveBlock(b, available));
        available += b.getSize();
      }
    }
    return movedBlocks;
  }

  /**
   * Moves a given MemBlock to a given address.
   * 
   * @param b
   *          block to move.
   * @param address
   *          where to move it.
   * @return MovedMemBlock with the moved memblock.
   */
  public MovedMemBlock moveBlock(MemBlock b, int address) {
    ArrayList<Byte> data = new ArrayList<Byte>(); // let's leave it like this
                                                  // for now...
    for (int i = 0; i < b.getSize(); i++) {
      data.add(readByte(b.getAddress() + i));
    }

    // it is all in data...
    mcs.freeSpace(b.getAddress(), b.getSize());
    // writeBlock(b.getAddress(), b.getAddress() + b.getSize() - 1,
    // (byte)FREE_CHAR); // write 0s

    // It might be better byte per byte
    for (int i = 0; i < b.getSize(); i++) {
      // reserve and write it at the destination address
      writeByte(mcs.reserveMemSpace(1), data.get(i));
    }

    // somehow change the reserved and free blocks...
    // create the MovedMemBlock
    MovedMemBlock movedBlock = new MovedMemBlock(address, address + b.getSize() - 1);
    movedBlock.setOldAddress(b.getAddress());

    return movedBlock;
  }

  /**
   * Shows the reserved blocks.
   */
  public void showReservedBlocks() {
    mcs.showReservedBlocks();
  }

  /**
   * Shows the free blocks.
   */
  public void showFreeBlocks() {
    mcs.showFreeBlocks();
  }

  /**
   * Shows the memory content in hexadecimal format.
   * 
   * @param initialAddress
   *          the initial address of the content to show.
   * @param finalAddress
   *          the final address of the content to show.
   */
  public void showMMHex(int initialAddress, int finalAddress) {
    // pre: initialAddress is valid, as well as the final address
    System.out.println("\n\nAddress  Content (HEX)");
    for (int addr = initialAddress; addr <= finalAddress; addr++) {
      System.out.println(addr + "\t  " + hexConverter(readByte(addr)));
    }
    System.out.println();
  }

  /**
   * Converts the given byte to a hexadecimal string representation.
   * 
   * @param b
   *          the byte to convert to hexadecimal string.
   * @return the hexadecimal representation of given byte.
   */
  private String hexConverter(byte b) {
    String hd = "0123456789ABCDEF";
    String s = "" + hd.charAt((b >> 4) & ((byte) 15)) + hd.charAt(b & ((byte) 15));
    return s;
  }

  /**
   * Returns the total memory currently reserved by the system.
   * 
   * @return the memory currently reserved by the system.
   */
  public int getTotalMemoryReserved() {
    return reservedSpace;
  }
}
