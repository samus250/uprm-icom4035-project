package p1MemorySystem;

import exceptionClasses.NodeOutOfBoundsException;
import interfaces.LinkedList;
import interfaces.Node;

public class DLDHDTList<E> implements LinkedList<E> {
  private DNode<E> dHeader, dTrailer;
  private int size;

  /**
   * Constructor. Creates a new DLDHDTList
   */
  public DLDHDTList() {
    // crate dHeader and dTrailer nodes
    dHeader = new DNode<E>();
    dTrailer = new DNode<E>();

    // link them
    dHeader.setNext(dTrailer);
    dTrailer.setPrev(dHeader);

    // init size, not counting dummy nodes
    size = 0;
  }

  public void addFirstNode(Node<E> nuevo) {
    addNodeAfter(dHeader, nuevo);
  }

  public void addNodeAfter(Node<E> target, Node<E> nuevo) {
    DNode<E> dnuevo = (DNode<E>) nuevo;
    DNode<E> nBefore = (DNode<E>) target;
    DNode<E> nAfter = nBefore.getNext();
    nBefore.setNext(dnuevo);
    nAfter.setPrev(dnuevo);
    dnuevo.setPrev(nBefore);
    dnuevo.setNext(nAfter);
    size++;
  }

  // should exceptions be thrown? No... that's why we have dummy nodes!
  public void addNodeBefore(Node<E> target, Node<E> nuevo) {
    DNode<E> dNuevo = (DNode<E>) nuevo;
    DNode<E> nAfter = (DNode<E>) target;
    DNode<E> nBefore = nAfter.getPrev();

    // add dNuevo between nAfter and nBefore
    nBefore.setNext(dNuevo);
    nAfter.setPrev(dNuevo);
    dNuevo.setPrev(nBefore);
    dNuevo.setNext(nAfter);

    size++;
  }

  public Node<E> createNewNode() {
    return new DNode<E>();
  }

  public Node<E> getFirstNode() throws NodeOutOfBoundsException {
    if (size == 0)
      throw new NodeOutOfBoundsException("List is empty.");
    return dHeader.getNext();
  }

  public Node<E> getLastNode() throws NodeOutOfBoundsException {
    if (size == 0)
      throw new NodeOutOfBoundsException("List is empty.");
    return dTrailer.getPrev();
  }

  public Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException {
    // ADD CODE HERE AND MODIFY RETURN ACCORDINGLY
    DNode<E> dTarget = (DNode<E>) target;
    if (dTarget.getNext() == dTrailer) {
      throw new NodeOutOfBoundsException("getNodeAfter: This is the last node.");
    }

    // there is a node after this one...
    return dTarget.getNext();
  }

  public Node<E> getNodeBefore(Node<E> target) throws NodeOutOfBoundsException {
    // ADD CODE HERE AND MODIFY RETURN ACCORDINGLY
    DNode<E> dTarget = (DNode<E>) target;
    if (dTarget.getPrev() == dHeader) {
      throw new NodeOutOfBoundsException("getNodeBefore: This is the first node.");
    }

    // there is a node before this one...
    return dTarget.getPrev();
  }

  public int length() {
    return size;
  }

  public Node<E> removeFirstNode() throws NodeOutOfBoundsException {
    if (size == 0)
      throw new NodeOutOfBoundsException("List is empty.");
    // ADD CODE HERE AND MODIFY RETURN ACCORDINGLY

    DNode<E> ntd = dHeader.getNext();
    dHeader.setNext(ntd.getNext());
    ntd.getNext().setPrev(dHeader);
    size--;
    ntd.cleanLinks();
    return ntd;
  }

  public Node<E> removeLastNode() throws NodeOutOfBoundsException {
    if (size == 0)
      throw new NodeOutOfBoundsException("List is empty.");
    DNode<E> ntd = dTrailer.getPrev(); // aqui no hay algo mal???
    // dTrailer = ntd.getPrev();
    // previos line was line given... next line is my modification
    dTrailer.setPrev(ntd.getPrev());
    ntd.getPrev().setNext(dTrailer);
    size--;
    ntd.cleanLinks();
    return ntd;
  }

  public void removeNode(Node<E> target) {
    // ADD CODE HERE
    if (target == dHeader.getNext()) {
      this.removeFirstNode();
    } else if (target == dTrailer.getPrev()) {
      this.removeLastNode();
    } else {
      DNode<E> ntd = (DNode<E>) target;
      DNode<E> dPrev = ntd.getPrev();
      DNode<E> dNext = ntd.getNext();
      dPrev.setNext(dNext);
      dNext.setPrev(dPrev);
      ntd.cleanLinks();
      size--;
    }
  }

  public Node<E> removeNodeAfter(Node<E> target) throws NodeOutOfBoundsException {
    DNode<E> dtarget = (DNode<E>) target;
    if (dtarget.getNext() == dTrailer)
      throw new NodeOutOfBoundsException("Target node is the last node.");
    removeNode(dtarget.getNext());
    return dtarget;
  }

  public Node<E> removeNodeBefore(Node<E> target) throws NodeOutOfBoundsException {
    DNode<E> dtarget = (DNode<E>) target;
    if (dtarget.getNext() == dHeader)
      throw new NodeOutOfBoundsException("Target node is the first node.");
    removeNode(dtarget.getPrev());
    return dtarget;
  }

  /**
   * Prepares every node so that the garbage collector can free its memory
   * space, at least from the point of view of the list. This method is supposed
   * to be used whenever the list object is not going to be used anymore.
   * Removes all physical nodes (data nodes and control nodes, if any) from the
   * linked list
   */
  private void removeAll() {
    while (dHeader != null) {
      DNode<E> nnode = dHeader.getNext();
      dHeader.setElement(null);
      dHeader.cleanLinks();
      dHeader = nnode;
    }
  }

  /**
   * The execution of this method removes all the data nodes from the current
   * instance of the list.
   */
  public void makeEmpty() {

  }

  protected void finalize() throws Throwable {
    try {
      this.removeAll();
    } finally {
      super.finalize();
    }
  }

  /**
   * Private inner class that represents a doubly linked node.
   * 
   * @author samus250
   *
   * @param <E>
   *          generic type
   */
  private static class DNode<E> implements Node<E> {
    private E element;
    private DNode<E> prev, next;

    /**
     * Default constructor.
     */
    public DNode() {
    }

    /**
     * Constructs a node with given element.
     * 
     * @param e
     *          the element.
     */
    public DNode(E e) {
      element = e;
    }

    /**
     * Constructs a node with given element, previous and next linked nodes.
     * 
     * @param e
     *          the element.
     * @param p
     *          the previous node.
     * @param n
     *          the next node.
     */
    public DNode(E e, DNode<E> p, DNode<E> n) {
      prev = p;
      next = n;
    }

    /**
     * Returns the previous node.
     * 
     * @return the previous node.
     */
    public DNode<E> getPrev() {
      return prev;
    }

    /**
     * Sets the previous node.
     * 
     * @param prev
     *          the previous node.
     */
    public void setPrev(DNode<E> prev) {
      this.prev = prev;
    }

    /**
     * Returns the next node.
     * 
     * @return the next node.
     */
    public DNode<E> getNext() {
      return next;
    }

    /**
     * Sets the next node.
     * 
     * @param next
     *          the next node.
     */
    public void setNext(DNode<E> next) {
      this.next = next;
    }

    public E getElement() {
      return element;
    }

    public void setElement(E data) {
      element = data;
    }

    /**
     * Unlinks the node from previous and next nodes.
     */
    public void cleanLinks() {
      prev = next = null;
    }

  }

}
