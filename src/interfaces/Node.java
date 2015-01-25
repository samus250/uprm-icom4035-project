package interfaces;

/**
 * This interface contains the necessary methods needed to fully implement a
 * Node
 * 
 * @author samus250
 *
 * @param <E>
 *          Generic Type
 */
public interface Node<E> {

  /**
   * Returns the node's element.
   * 
   * @return The node's element.
   */
  public E getElement();

  /**
   * Sets the node's element.
   * 
   * @param data
   *          - The data to set to the node.
   */
  public void setElement(E data);
}
