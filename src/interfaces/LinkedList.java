package interfaces;

import exceptionClasses.NodeOutOfBoundsException;

/**
 * This interface contains necessary methods to fully implement a Linked List
 * 
 * @author samus250
 *
 * @param <E>
 *          Generic Type
 */
public interface LinkedList<E> {

  /**
   * Determines the number of nodes currently in the linked list.
   * 
   * @return integer value (>= 0) corresponding to the number of nodes in the
   *         linked list.
   */
  int length();

  /**
   * Returns the node that lies before the given node.
   * 
   * @param target
   *          - The target node.
   * @return The node before the target.
   * @throws NodeOutOfBoundsException
   */
  Node<E> getNodeBefore(Node<E> target) throws NodeOutOfBoundsException;

  /**
   * Returns the node that lies after the given node.
   * 
   * @param target
   *          - The target node.
   * @return - The node after the target.
   * @throws INodeOutOfBoundsException
   */
  Node<E> getNodeAfter(Node<E> target) throws NodeOutOfBoundsException;

  /**
   * Returns the first node in the linked list.
   * 
   * @return The first node in the linked list.
   * @throws NodeOutOfBoundsException
   */
  Node<E> getFirstNode() throws NodeOutOfBoundsException;

  /**
   * Returns the last node in the linked list.
   * 
   * @return The last node in the linked list.
   * @throws NodeOutOfBoundsException
   */
  Node<E> getLastNode() throws NodeOutOfBoundsException;

  /**
   * Adds a node to the first position.
   * 
   * @param nuevo
   *          - Node to add.
   */
  void addFirstNode(Node<E> nuevo);

  /**
   * Adds a node after a given target node.
   * 
   * @param target
   *          - The target node.
   * @param nuevo
   *          - The node to add.
   */
  void addNodeAfter(Node<E> target, Node<E> nuevo);

  /**
   * Adds a node before a given target node.
   * 
   * @param target
   *          - The target node.
   * @param nuevo
   *          - The node to add.
   */
  void addNodeBefore(Node<E> target, Node<E> nuevo);

  /**
   * Removes the first node in the linked list.
   * 
   * @return The node that was removed.
   * @throws NodeOutOfBoundsException
   */
  Node<E> removeFirstNode() throws NodeOutOfBoundsException;

  /**
   * Removes the last node in the linked list.
   * 
   * @return - The node that was removed.
   * @throws NodeOutOfBoundsException
   */
  Node<E> removeLastNode() throws NodeOutOfBoundsException;

  /**
   * Removes the given target node.
   * 
   * @param target
   *          - The node to remove.
   */
  void removeNode(Node<E> target);

  /**
   * Removes the node that lies after the given target node.
   * 
   * @param target
   *          - The target node.
   * @return The node removed.
   * @throws NodeOutOfBoundsException
   */
  Node<E> removeNodeAfter(Node<E> target) throws NodeOutOfBoundsException;

  /**
   * Removes the node that lies before the given target node.
   * 
   * @param target
   *          - The target node.
   * @return The node removed.
   * @throws NodeOutOfBoundsException
   */
  Node<E> removeNodeBefore(Node<E> target) throws NodeOutOfBoundsException;

  /**
   * Creates a new node instance of the type of nodes that the linked list uses.
   * The new node will have all its instance fields initialized to null. The new
   * node is not linked to the list in any way.
   * 
   * @return reference to the new node instance.
   */
  Node<E> createNewNode();

}
