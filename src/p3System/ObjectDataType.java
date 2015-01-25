package p3System;

import java.util.ArrayList;
import java.util.HashMap;

import p1MemorySystem.P1MemSystem;

/**
 * This represents and Object Data Type.
 * 
 * @author samus250
 *
 */
public class ObjectDataType extends DataType {
  protected static final int REFERENCESIZE = 4;
  private int objectSize; // number of bytes required to hold the object

  NBQueue<Integer> objectsToDelQueue; // queue for garbage collector
  private HashMap<Integer, Integer> registeredObjects; // for compact
  private HashMap<String, InstanceField> iFieldsList; // the list of instance
                                                      // fields

  /**
   * Constructs an object data type with the given information.
   * 
   * @param name
   *          the name of the object data type.
   * @param iFieldsList
   *          the list of instance fields.
   */
  public ObjectDataType(String name, ArrayList<InstanceField> iFieldsList) {
    super(name, REFERENCESIZE, new IntMDTProcessor());
    // third parameter - reference is an int

    // init registered objects, contains addresses of registered objects
    registeredObjects = new HashMap<Integer, Integer>();

    this.iFieldsList = new HashMap<String, InstanceField>();
    int relativeAddress = 0;
    for (InstanceField insf : iFieldsList) {
      // System.out.println("Relative address when adding insf " +
      // insf.getName() + " is " + relativeAddress);
      insf.setRelativeAddress(relativeAddress);
      // System.out.println("Relative address while adding insf " +
      // insf.getName() + " has been set to " + insf.getRelativeAddress());
      DataType instanceFieldDT = insf.getDataType();
      if (instanceFieldDT == null) {
        // if it is null, then it is of the same type of this
        InstanceField tempInsf = new InstanceField(insf.getName(), this);
        tempInsf.setRelativeAddress(relativeAddress);
        this.iFieldsList.put(tempInsf.getName(), tempInsf);
        relativeAddress += REFERENCESIZE;
      } else {
        relativeAddress += insf.getDataType().getSize();
        this.iFieldsList.put(insf.getName(), insf);
      }
    }

    // at this moment, the value of relativeAddres is
    // equal to the number of bytes that the object
    // occupies in memory
    objectSize = relativeAddress;

    objectsToDelQueue = new NBQueue<Integer>();
  }

  /**
   * Searches for an instance field with the specified name in the list of
   * instance fields.
   * 
   * @param name
   *          The name of the instance field to search for
   * @return Reference to the object of type InstanceField, null if it does not
   *         exist.
   */
  public InstanceField getInstanceField(String name) {
    return iFieldsList.get(name);
  }

  /**
   * Returns the size the whole object occupies.
   * 
   * @return the size the whole object occupies.
   */
  public int getObjectSize() {
    return objectSize;
  }

  /**
   * Returns the list of the instance fields.
   * 
   * @return An arrayList of the instance fields.
   */
  public ArrayList<InstanceField> getInstanceFieldList() {
    ArrayList<InstanceField> atr = new ArrayList<InstanceField>();
    for (InstanceField insf : iFieldsList.values())
      atr.add(insf);

    return atr;
  }

  /**
   * Returns the list of addresses of registered objects.
   * 
   * @return the list of addresses of registered objects.
   */
  public ArrayList<Integer> getRegisteredObjects() {
    ArrayList<Integer> atr = new ArrayList<Integer>();
    for (Integer i : registeredObjects.values())
      atr.add(i);
    return atr;
  }

  /**
   * Adds a registered object.
   * 
   * @param address
   *          the address of the registered object.
   */
  public void addRegisteredObject(int address) {
    registeredObjects.put(address, address);
  }

  /**
   * Unregisters an object.
   * 
   * @param address
   *          the address of the object to unregister.
   */
  public void removeRegisteredObject(int address) {
    registeredObjects.remove(address);
  }

  /**
   * Sends the object at the given address to garbage collection.
   * 
   * @param address
   *          the address of the object to send to garbage collection.
   */
  public void sendToGC(int address) {
    if (objectsToDelQueue.isEmpty())
      objectsToDelQueue.enqueue(address);
    else if (objectsToDelQueue.front() != address) // do not add the same one
                                                   // more than once!
      objectsToDelQueue.enqueue(address);
  }

  /**
   * Performs garbage collection on the given memory system.
   * 
   * @param p1Sys
   *          the memory system where to perform garbage collection.
   */
  public void gc(P1MemSystem p1Sys) { // this class is needed for creating
                                      // temporary variable
    while (!objectsToDelQueue.isEmpty()) {
      int add = objectsToDelQueue.front(); // must front first and dequeue
                                           // after... reference variable stuff

      // separate some space for the temp reference variable
      int tempAddress = p1Sys.reserveMemory(this.getSize());

      // check for not enough space
      if (tempAddress == -1)
        return;

      // create a temporary Reference variable
      ReferenceVariable r = new ReferenceVariable("temp", this, p1Sys, tempAddress); // the
                                                                                     // name
                                                                                     // of
                                                                                     // the
                                                                                     // var
                                                                                     // really
                                                                                     // doesn't
                                                                                     // matter
      r.setValue(Integer.toString(add)); // reference points to the object in
                                         // question

      for (InstanceField insf : iFieldsList.values()) {
        if (insf.getDataType() instanceof ObjectDataType) {
          ObjectDataType odt = (ObjectDataType) insf.getDataType();
          // if this is a reference instance field, then
          String value = r.getInstanceFieldValue(insf.getName());
          if (!value.equals("-1")) {
            int rcfAddress = Integer.parseInt(value) + odt.getObjectSize();
            int rcf = Integer.parseInt(odt.readValueFromMemory(p1Sys, rcfAddress));
            rcf++;
            odt.writeValueToMemory(p1Sys, rcfAddress, Integer.toString(rcf));
          }
        }
      }

      // release temporary variables
      r.setValue("null"); // there might be a slight problem here... object is
                          // sent to garbage collection again (fixed in
                          // sendToGC)
      p1Sys.freeMemory(tempAddress, this.getSize());

      // remove add from registered objects
      removeRegisteredObject(add);

      // release the space from p1Sys...
      p1Sys.freeMemory(add, this.getObjectSize() + 4); // (plus the damned rcf)

      objectsToDelQueue.dequeue();
    }
  }
}
