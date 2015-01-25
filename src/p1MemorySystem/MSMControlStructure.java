package p1MemorySystem;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import exceptionClasses.BlockBoundaryViolationException;
import exceptionClasses.InvalidAddressException;
import exceptionClasses.SpaceNotReservedException;

import interfaces.MemControlStructure;
import interfaces.Node;

/**
 * This implements the MemControlStructure ADT, based on doubly linked, dummy
 * header / dummy trailer linked list.
 * 
 * @author samus250
 *
 */
public class MSMControlStructure implements MemControlStructure {
  private DLDHDTList<MemBlock> plist; // list of pairs (MemBlock type of pairs)
  private int memSize; // size of the memory to manage

  /**
   * Constructor. Sets the size of the memory to manage.
   * 
   * @param ms
   *          the size of the memory to manage.
   * @throws InvalidParameterException
   */
  public MSMControlStructure(int ms) throws InvalidParameterException {
    if (ms <= 0)
      throw new InvalidParameterException("Mem size must be > 0");

    memSize = ms;
    plist = new DLDHDTList<MemBlock>();
    Node<MemBlock> freeBlockNode = plist.createNewNode();
    freeBlockNode.setElement(new MemBlock(0, memSize - 1));
    plist.addFirstNode(freeBlockNode);

    // add dummy blocks � this simplifies algorithms to free and
    // reserve memory
    Node<MemBlock> firstDumyFreeNode = plist.createNewNode();
    firstDumyFreeNode.setElement(new MemBlock(-1, -2));
    plist.addFirstNode(firstDumyFreeNode);

    Node<MemBlock> lastDumyFreeNode = plist.createNewNode();
    lastDumyFreeNode.setElement(new MemBlock(memSize + 1, memSize));
    plist.addNodeAfter(freeBlockNode, lastDumyFreeNode);
  }

  public int reserveMemSpace(int size) throws InvalidParameterException {
    if (size <= 0 || size > memSize)
      throw new InvalidParameterException("Invalid size");

    // size is valid
    Node<MemBlock> currentNode = plist.getFirstNode();

    // first discard the (-1, -2) node
    currentNode = plist.getNodeAfter(currentNode);

    while (currentNode != plist.getLastNode()) {
      // skip last dummy node as well
      // extract capacity
      MemBlock currentMemBlock = currentNode.getElement();
      int blockAddress = currentMemBlock.getAddress();
      int blockLastAddress = currentMemBlock.getLastAddress();
      int blockCapacity = blockLastAddress - blockAddress + 1;

      if (blockCapacity == size) {
        plist.removeNode(currentNode);
        return blockAddress;
      } else if (blockCapacity > size) {
        currentMemBlock.setAddress(blockAddress + size); // professor suggested
                                                         // blockAddress + size
                                                         // - 1?
        return blockAddress;
      }

      currentNode = plist.getNodeAfter(currentNode);
    }

    // block of sufficient size was not found
    return -1;
  }

  public void freeSpace(int address, int blockSize) throws InvalidAddressException,
      BlockBoundaryViolationException, SpaceNotReservedException {
    if (address < 0 || address >= memSize)
      throw new InvalidAddressException("Invalid address");

    if (blockSize <= 0 || address + blockSize > memSize) // changed >= memSize
                                                         // to > memSize
      throw new BlockBoundaryViolationException("Invalid block boundaries or incorrect size.");

    // the given address and block sizes are valid
    Node<MemBlock> currentNode = plist.getFirstNode();

    // start looking for nodes
    while (currentNode != plist.getLastNode()) {
      MemBlock currentBlock = currentNode.getElement();
      Node<MemBlock> nextNode = plist.getNodeAfter(currentNode);
      MemBlock nextBlock = nextNode.getElement();
      int b1 = currentBlock.getLastAddress();
      int a2 = nextBlock.getAddress();

      if ((address > b1) && (address + blockSize) <= a2) {
        if (address == b1 + 1 && a2 == address + blockSize) {
          // merge the two nodes
          currentBlock.setLastAddress(nextBlock.getLastAddress());
          plist.removeNode(nextNode);
        } else if (address == b1 + 1) {
          // modify the current node
          currentBlock.setLastAddress(b1 + blockSize);
        } else if (a2 == address + blockSize) {
          // modify the next node
          nextBlock.setAddress(address);
        } else {
          // then address > b1 + 1 and address + blockSize < a2
          // add a new node beginning at address
          Node<MemBlock> newNode = plist.createNewNode();
          MemBlock newBlock = new MemBlock(address, address + blockSize - 1);
          newNode.setElement(newBlock);
          plist.addNodeAfter(currentNode, newNode);
        }

        return;
      }
      currentNode = nextNode;
    }

    // nothing was found
    throw new SpaceNotReservedException("Specified block to free [" + address + ", "
        + (address + blockSize - 1) + "] has bytes that are free.");
  }

  /**
   * Prints a list of free memory blocks on the data structure.
   */
  public void showFreeBlocks() {
    System.out.println("List of free nodes is: ");
    if (plist.length() - 2 != 0) {
      Node<MemBlock> currentNode = plist.getFirstNode();

      // first discard the (-1,-2) node...
      currentNode = plist.getNodeAfter(currentNode);

      while (currentNode != plist.getLastNode()) {
        System.out.print(currentNode.getElement() + " ");
        currentNode = plist.getNodeAfter(currentNode);
      }

      // the last data node is not printed since it is
      // a dummy data node...
      System.out.println();
    } else
      System.out.println("List of free blocks is empty.");
  }

  /**
   * Returns an iterable collection of reserved MemBlocks.
   * 
   * @return an iterable collection of reserved MemBlocks.
   */
  public Iterable<MemBlock> reservedMemBlocks() {
    ArrayList<MemBlock> reservedMemBlocks = new ArrayList<MemBlock>();

    if (plist.length() - 2 != 0) {
      Node<MemBlock> currentNode = plist.getFirstNode();

      // first discard the (-1,-2) node...
      currentNode = plist.getNodeAfter(currentNode);

      // check address 0, see if there is a block reserved
      if (currentNode.getElement().getAddress() != 0)
        reservedMemBlocks.add(new MemBlock(0, currentNode.getElement().getAddress() - 1));

      while (currentNode != plist.getLastNode()) {
        Node<MemBlock> nextNode = plist.getNodeAfter(currentNode);

        int firstAddress = currentNode.getElement().getLastAddress() + 1;
        int lastAddress = nextNode.getElement().getAddress() - 1;
        if (lastAddress == memSize)
          lastAddress--;

        if (firstAddress <= lastAddress)
          reservedMemBlocks.add(new MemBlock(firstAddress, lastAddress));

        currentNode = plist.getNodeAfter(currentNode);
      }

    } else {
      // there are no free nodes, so it is all reserved
      reservedMemBlocks.add(new MemBlock(0, memSize - 1));
    }

    return reservedMemBlocks;
  }

  /**
   * Prints to System.out the reserved blocks.
   */
  public void showReservedBlocks() {
    System.out.println("List of reserved nodes is: ");
    boolean hasElements = false;
    for (MemBlock b : reservedMemBlocks()) {
      hasElements = true;
      System.out.print(b + " ");
    }
    if (!hasElements)
      System.out.println("List of reserved nodes is empty.");
    else
      System.out.println();
  }
}
