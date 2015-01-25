package p3System;

/**
 * This class represents a node.
 * 
 * @author samus250
 *
 * @param <E>
 *          generic type of the node.
 */
public class Node<E> {
  private E element;
  private Node<E> next;

  /**
   * Constructs a node.
   */
  public Node() {
    this(null, null);
  }

  /**
   * Constructs a node with given element and next node.
   * 
   * @param e
   *          the element.
   * @param n
   *          the next node.
   */
  public Node(E e, Node<E> n) {
    element = e;
    next = n;
  }

  /**
   * Returns the node's element.
   * 
   * @return the node's element.
   */
  public E getElement() {
    return element;
  }

  /**
   * Sets the node's element.
   * 
   * @param e
   *          the node's element.
   */
  public void setElement(E e) {
    element = e;
  }

  /**
   * Returns the next node.
   * 
   * @return the next node.
   */
  public Node<E> getNext() {
    return next;
  }

  /**
   * Sets the next node.
   * 
   * @param n
   *          the next node.
   */
  public void setNext(Node<E> n) {
    next = n;
  }
}
