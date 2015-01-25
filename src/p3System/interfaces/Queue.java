package p3System.interfaces;

import p3System.exceptionClasses.EmptyQueueException;

/**
 * This class represents a queue data structure.
 * 
 * @author samus250
 *
 * @param <E>
 *          the queue's elements data type
 */
public interface Queue<E> {

  /**
   * Returns the size of the queue.
   * 
   * @return
   */
  int size();

  /**
   * Tests the queue for emptiness
   * 
   * @return true if the queue is empty, false otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the element at the front of the queue.
   * 
   * @return the element at the front of the queue.
   * @throws EmptyQueueException
   *           whenever the queue is empty.
   */
  E front() throws EmptyQueueException;

  /**
   * Dequeues the element at the front.
   * 
   * @return the dequeued element.
   * @throws EmptyQueueException
   *           whenever the queue is empty.
   */
  E dequeue() throws EmptyQueueException;

  /**
   * Enqueues the given element.
   * 
   * @param e
   *          the element to enqueue.
   */
  void enqueue(E e);
}
