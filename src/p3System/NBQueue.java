package p3System;

import p3System.exceptionClasses.EmptyQueueException;
import p3System.interfaces.Queue;

/**
 * This is an implementation of a queue based in nodes.
 * 
 * @author samus250
 *
 * @param <E>
 *          generic data type.
 */
public class NBQueue<E> implements Queue<E> {
  private int size;
  private Node<E> head;
  private Node<E> tail;

  public NBQueue() {
    head = tail = null;
    size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public E front() throws EmptyQueueException {
    if (isEmpty())
      throw new EmptyQueueException("The queue is empty");

    return head.getElement();
  }

  public E dequeue() throws EmptyQueueException {
    if (isEmpty())
      throw new EmptyQueueException("The queue is empty");

    E etr = head.getElement();
    if (size == 1)
      head = tail = null;
    else
      head = head.getNext();

    size--;
    return etr;
  }

  public void enqueue(E e) {
    Node<E> newNode = new Node<E>(e, null);
    if (size == 0)
      head = newNode;
    else
      tail.setNext(newNode);

    tail = newNode;
    size++;

  }

}
