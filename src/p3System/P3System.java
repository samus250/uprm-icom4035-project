package p3System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import memoryClasses.Register;

import p1MemorySystem.MovedMemBlock;
import p1MemorySystem.P1MemSystem;
import p3System.exceptionClasses.DataTypeDoesNotExistException;
import p3System.exceptionClasses.DataTypeExistsException;
import p3System.exceptionClasses.InvalidArrayLengthException;
import p3System.exceptionClasses.InvalidAssignmentException;
import p3System.exceptionClasses.InvalidDataTypeException;
import p3System.exceptionClasses.InvalidFieldNameException;
import p3System.exceptionClasses.InvalidNameException;
import p3System.exceptionClasses.InvalidValueException;
import p3System.exceptionClasses.NonPrimitiveDataTypeException;
import p3System.exceptionClasses.NotAnArrayVariableException;
import p3System.exceptionClasses.NotArrayVariableException;
import p3System.exceptionClasses.NotNullValueException;
import p3System.exceptionClasses.NotPrimitiveDataTypeException;
import p3System.exceptionClasses.NotReferenceVariableException;
import p3System.exceptionClasses.NullArrayReferenceException;
import p3System.exceptionClasses.UnsuccessfulRegistrationException;
import p3System.exceptionClasses.VariableDoesNotExistException;
import p3System.exceptionClasses.VariableNameExistsException;
import p3System.interfaces.IP3System;

/**
 * This is an implementation of the IP2System, See IP2System documentation.
 * 
 * @author samus250.
 *
 */
public class P3System implements IP3System {
  private P1MemSystem p1Sys;
  private HashMap<String, Variable> varsList;
  private HashMap<String, DataType> typesList;
  private ArrayGarbageCollector arrayGC;
  private final int MS = 10000; // size of the memory system

  /**
   * Default constructor.
   */
  public P3System() {
    p1Sys = new P1MemSystem(MS);
    varsList = new HashMap<String, Variable>();
    initTypesList();
    arrayGC = new ArrayGarbageCollector();
  }

  /**
   * Constructor that sets the size of the memory.
   * 
   * @param size
   *          the size of the memory
   */
  public P3System(int size) {
    p1Sys = new P1MemSystem(size);
    varsList = new HashMap<String, Variable>();
    initTypesList();
    arrayGC = new ArrayGarbageCollector();
  }

  /**
   * Private helper class that initializes the types list with primitive types.
   */
  private void initTypesList() {
    typesList = new HashMap<String, DataType>();

    DataType integer = new DataType("int", DataType.SIZEOFINT, new IntMDTProcessor());
    DataType character = new DataType("char", DataType.SIZEOFCHAR, new CharMDTProcessor());
    DataType bool = new DataType("boolean", DataType.SIZEOFBOOLEAN, new BooleanMDTProcessor());

    DataType array = new DataType("array", ObjectDataType.REFERENCESIZE, new IntMDTProcessor());

    typesList.put(integer.getName(), integer);
    typesList.put(character.getName(), character);
    typesList.put(bool.getName(), bool);
    typesList.put(array.getName(), array);
  }

  /**
   * Returns the memory system.
   * 
   * @return the memory system.
   */
  public P1MemSystem getMemorySystem() {
    return p1Sys;
  }

  /**
   * Returns the list of registered variables in no particular order.
   * 
   * @return the list of registered variables in no particular order.
   */
  public ArrayList<Variable> getVarList() {
    ArrayList<Variable> atr = new ArrayList<Variable>();
    for (Variable v : varsList.values())
      atr.add(v);

    return atr;
  }

  /**
   * Returns the list of registered data types in no particular order.
   * 
   * @return the list of registered data types in no particular order.
   */
  public ArrayList<DataType> getTypeList() {
    ArrayList<DataType> atr = new ArrayList<DataType>();
    for (DataType dt : typesList.values())
      atr.add(dt);

    return atr;
  }

  public void registerObjectDataType(ObjectDataType type) throws DataTypeExistsException {
    typesList.put(type.getName(), type);
  }

  /**
   * Returns the data type of a registered variable.
   * 
   * @param name
   * @return
   */
  public DataType getDataType(String name) {
    return typeSearch(name);
  }

  public void registerVariable(String name, String type) throws InvalidNameException,
      VariableNameExistsException, InvalidDataTypeException, UnsuccessfulRegistrationException {
    if (!identifierNameIsValid(name))
      throw new InvalidNameException("Name is invalid: " + name);

    DataType dt = typeSearch(type);
    if (dt == null)
      throw new InvalidDataTypeException("Invalid Data Type: " + type);

    if (variableSearch(name) != null)
      throw new VariableNameExistsException("Variable " + name + " already exists.");

    // reserve some space
    int address = p1Sys.reserveMemory(dt.getSize());
    if (address == -1)
      throw new UnsuccessfulRegistrationException("Unsuccessful Registration: not enough space.");

    Variable nVar;
    if (dt instanceof ObjectDataType)
      nVar = new ReferenceVariable(name, dt, p1Sys, address);
    else if (dt.getName().equals("array"))
      nVar = new ArrayReferenceVariable(name, dt, p1Sys, address, arrayGC); // arrayGC
                                                                            // sent
    else
      nVar = new Variable(name, dt, p1Sys, address);

    // store
    varsList.put(nVar.getName(), nVar);

  }

  public String getVariableValue(String vName) throws VariableDoesNotExistException {
    Variable v = validateVariable(vName);
    return v.getValue();
  }

  /**
   * Returns the data type of the given variable.
   * 
   * @param vName
   *          the name of the variable.
   * @return the data type of the given variable.
   * @throws VariableDoesNotExistException
   *           whenever the given variable does not exist.
   */
  public DataType getVariableDataType(String vName) throws VariableDoesNotExistException {
    Variable v = validateVariable(vName);
    return v.getDataType();
  }

  public void setVariableValue(String vName, String value) throws VariableDoesNotExistException,
      InvalidValueException, NotNullValueException {
    // look for the variable and change its value
    Variable v = variableSearch(vName);

    if (v == null)
      throw new VariableDoesNotExistException("The variable does not exist");

    if ((v instanceof ReferenceVariable || v instanceof ArrayReferenceVariable)
        && !value.equals("null"))
      throw new NotNullValueException("Setting a reference value other than null is prohibited.");

    // set value throws the other exception
    v.setValue(value);
  }

  public String getInstanceFieldValue(String vName, String fName)
      throws VariableDoesNotExistException, NotReferenceVariableException,
      InvalidFieldNameException {
    ReferenceVariable rv = (ReferenceVariable) validateVariable(vName + " reference");

    // this throws the other exception
    return rv.getInstanceFieldValue(fName);
  }

  public void setInstanceFieldValue(String vName, String fName, String value)
      throws VariableDoesNotExistException, NotReferenceVariableException,
      InvalidFieldNameException, InvalidValueException {
    ReferenceVariable rv = (ReferenceVariable) validateVariable(vName + " reference");

    // check if the instance field is a reference. If it is, value must be null.
    ObjectDataType dt = (ObjectDataType) rv.getDataType();
    InstanceField insf = dt.getInstanceField(fName);
    if (insf == null)
      throw new InvalidFieldNameException("Instance Field " + fName + " in data type "
          + dt.getName() + " does not exist.");

    if (insf.getDataType() instanceof ObjectDataType && !value.equalsIgnoreCase("null"))
      throw new InvalidValueException(
          "Setting a reference instance field to a value other than null is prohibited.");

    // this one throws the other exceptions
    rv.setInstanceFieldValue(fName, value);

  }

  public int createObject(String ot) {
    // return -1 if object not defined or not enough memory
    ObjectDataType dt = (ObjectDataType) typeSearch(ot);
    if (dt == null)
      return -1;

    int address = p1Sys.reserveMemory(dt.getObjectSize() + 4); // + 4 added for
                                                               // rcf value
    dt.addRegisteredObject(address); // add created object to the list...

    // modify the rcf value, dt.writeValueToMemory uses IntMDTProcessor from
    // ObjectDataType constructor
    dt.writeValueToMemory(p1Sys, address + dt.getObjectSize(), "0");

    return address;
  }

  public void deletePrimitiveDTVariable(String vName) throws VariableDoesNotExistException,
      NonPrimitiveDataTypeException {
    Variable v = validateVariable(vName + " primitive");

    p1Sys.freeMemory(v.getAddress(), v.getDataType().getSize());
    varsList.remove(v.getName());
  }

  public void setReferenceVariableValue(String v1Name, String v2Name)
      throws VariableDoesNotExistException, InvalidAssignmentException {
    Variable v1 = variableSearch(v1Name);
    Variable v2 = variableSearch(v2Name);

    if (v1 == null || v2 == null)
      throw new VariableDoesNotExistException("One of the given variables does not exist: "
          + v1Name + ", " + v2Name);

    if (!(v1 instanceof ReferenceVariable) && !(v1 instanceof ArrayReferenceVariable))
      throw new InvalidAssignmentException("Variable " + v1Name + " is not a reference");

    if (!(v2 instanceof ReferenceVariable) && !(v2 instanceof ArrayReferenceVariable))
      throw new InvalidAssignmentException("Variable " + v2Name + " is not a reference");

    if (!v1.getDataType().getName().equals(v2.getDataType().getName()))
      throw new InvalidAssignmentException(v1Name + " and " + v2Name
          + " are of different object types.");

    // at this point, both variables are valid
    // the type of the variable must be set first (for garbage collection
    // reasons)
    if (v1 instanceof ArrayReferenceVariable && v2 instanceof ArrayReferenceVariable) {
      ArrayReferenceVariable va1 = (ArrayReferenceVariable) v1;
      ArrayReferenceVariable va2 = (ArrayReferenceVariable) v2;
      va1.setArrayType(va2.getArrayType()); // actually, we might have a problem
                                            // here
    }

    v1.setValue(v2.getValue());
  }

  public void setReferenceInstanceFieldValue(String v1Name, String fName, String v2Name)
      throws VariableDoesNotExistException, NotReferenceVariableException,
      InvalidFieldNameException, InvalidAssignmentException {
    ReferenceVariable vr1 = (ReferenceVariable) validateVariable(v1Name + " reference");
    ReferenceVariable vr2 = (ReferenceVariable) validateVariable(v2Name + " reference");

    // is the instance field a reference?
    InstanceField insf = ((ObjectDataType) vr1.getDataType()).getInstanceField(fName);
    if (!(insf.getDataType() instanceof ObjectDataType))
      throw new NotReferenceVariableException("Instance field " + insf.getName()
          + " is not a reference instance field.");
    if (!insf.getDataType().equals(vr2.getDataType()))
      throw new InvalidAssignmentException("Instance field type " + insf.getDataType().getName()
          + " does not equal reference variable type " + vr2.getDataType().getName());

    vr1.setInstanceFieldValue(fName, vr2.getValue());

  }

  public void createAndAssignObject(String vName) throws VariableDoesNotExistException,
      NotReferenceVariableException {
    ReferenceVariable v = (ReferenceVariable) validateVariable(vName + " reference");

    String vType = v.getDataType().getName();
    v.setValue(Integer.toString(createObject(vType))); // v.setValue should
                                                       // change RCF accordingly

    ObjectDataType ot = (ObjectDataType) v.getDataType();
    for (InstanceField insf : ot.getInstanceFieldList()) {
      if (insf.getDataType() instanceof ObjectDataType) // set null to reference
                                                        // insf only
        v.setInstanceFieldValue(insf.getName(), "null");
    }
  }

  public void createAndAssignArrayObject(String vName, String tName, int length)
      throws VariableDoesNotExistException, NotArrayVariableException,
      DataTypeDoesNotExistException, NotPrimitiveDataTypeException, InvalidArrayLengthException {
    ArrayReferenceVariable va = (ArrayReferenceVariable) validateVariable(vName + " array");
    DataType t = validateDataType(tName + " primitive");

    if (length <= 0)
      throw new InvalidArrayLengthException("Invalid array length.");

    // make some space
    int address = p1Sys.reserveMemory(length * t.getSize() + 4 + 4); // 4 for
                                                                     // length,
                                                                     // 4 for
                                                                     // rcf
    if (address < 0)
      throw new InvalidArrayLengthException("Not enough space for this array.");

    // record length
    Register reg = new Register();
    reg.copyIntToRegister(length);
    p1Sys.writeWord(address, reg);

    // put it on the variable
    // first set type, then value, then RCF to 1
    // but if the array already exists (has a type), then you must first set
    // value (changes rcf) and then set new type
    if (va.getArrayType() != null) {
      va.setValue(Integer.toString(address));
      va.setArrayType(t);
      va.setRCF(1);
    } else {
      va.setArrayType(t);
      va.setValue(Integer.toString(address));
      va.setRCF(1);
    }

    // add it to the registeredArrays
    arrayGC.registerArray(address);
  }

  public void setArrayElementValue(String vName, int index, String value)
      throws VariableDoesNotExistException, NotAnArrayVariableException,
      NullArrayReferenceException, IndexOutOfBoundsException, InvalidValueException {
    ArrayReferenceVariable va = (ArrayReferenceVariable) validateVariable(vName + " array notnull");

    if (index < 0 || index >= va.getLength())
      throw new IndexOutOfBoundsException("Index is out of bounds.");

    va.setElementValue(index, value);
  }

  public String getArrayElementValue(String vName, int index) throws VariableDoesNotExistException,
      NotAnArrayVariableException, NullArrayReferenceException, IndexOutOfBoundsException {
    ArrayReferenceVariable va = (ArrayReferenceVariable) validateVariable(vName + " array notnull");

    if (index < 0 || index >= va.getLength())
      throw new IndexOutOfBoundsException("Index is out of bounds.");

    return va.getElementValue(index);
  }

  public int getArrayLength(String vName) throws VariableDoesNotExistException,
      NotAnArrayVariableException, NullArrayReferenceException {
    ArrayReferenceVariable va = (ArrayReferenceVariable) validateVariable(vName + " array notnull");
    return va.getLength();
  }

  public void gc() {
    // objects
    for (DataType dt : typesList.values()) {
      if (dt instanceof ObjectDataType) {
        ((ObjectDataType) dt).gc(p1Sys);
      }
    }

    // arrays
    arrayGC.gc(p1Sys);
  }

  public void compactMemory() {
    Iterable<MovedMemBlock> movedBlocks = p1Sys.compactMemory();

    for (Variable v : varsList.values()) {
      // adjust address of variable
      MovedMemBlock b = associatedMovedBlock(v.getAddress(), movedBlocks);

      if (b != null)
        v.setAddress(b.getAddress() + v.getAddress() - b.getOldAddress());

      // adjust reference address
      if (v instanceof ReferenceVariable || v instanceof ArrayReferenceVariable) {
        int refValue = Integer.parseInt(v.getValue());
        b = associatedMovedBlock(refValue, movedBlocks);
        if (b != null) { // added, thrown exception in tester 5
          if (v instanceof ArrayReferenceVariable) {
            ((ArrayReferenceVariable) v).setValue(
                Integer.toString(b.getAddress() + refValue - b.getOldAddress()), false);
          } else {
            ((ReferenceVariable) v).setValue(
                Integer.toString(b.getAddress() + refValue - b.getOldAddress()), false);
          }
        }

      }
    }

    for (int i = 0; i < arrayGC.getRegisteredArrays().size(); i++) {
      int address = arrayGC.getRegisteredArrays().get(i);
      MovedMemBlock b = associatedMovedBlock(address, movedBlocks);

      if (b != null) {
        int newAddress = b.getAddress() + address - b.getOldAddress();
        // change it
        arrayGC.getRegisteredArrays().set(i, newAddress);
      }
    }

    // adjust reference of all active objects
    ArrayList<ObjectDataType> odt = new ArrayList<ObjectDataType>();
    for (DataType dt : getTypeList()) {
      if (dt instanceof ObjectDataType) {
        odt.add((ObjectDataType) dt);
      }
    }

    for (ObjectDataType dt : odt) {
      for (int i = 0; i < dt.getRegisteredObjects().size(); i++) {
        int address = dt.getRegisteredObjects().get(i);
        MovedMemBlock b = associatedMovedBlock(address, movedBlocks);

        if (b != null) {
          int newAddress = b.getAddress() + address - b.getOldAddress();
          // change it
          dt.removeRegisteredObject(address);
          dt.addRegisteredObject(newAddress);
        }
      }
    }

    // adjust reference of all instance fields of the objects
    for (ObjectDataType dt : odt) {
      for (int address : dt.getRegisteredObjects()) {
        for (InstanceField insf : dt.getInstanceFieldList()) {
          if ((insf.getDataType() instanceof ObjectDataType)
              || (insf.getDataType().getName().equals("array"))) {
            // first get the instance field value
            int addressToLookFor = insf.getRelativeAddress() + address;
            int oldAddress = Integer.parseInt(dt.readValueFromMemory(p1Sys, addressToLookFor));

            if (oldAddress > -1) {
              // calculate the new address using the professor's algorithm
              MovedMemBlock b = associatedMovedBlock(oldAddress, movedBlocks);
              if (b != null) { // might throw null pointer exception
                int newAddress = b.getAddress() + oldAddress - b.getOldAddress();
                dt.writeValueToMemory(p1Sys, addressToLookFor, Integer.toString(newAddress));
              }
            }
          }

        }
      }
    }
  }

  /**
   * Returns the total memory reserved in the memory system.
   * 
   * @return the memory reserved in the memory system.
   */
  public int getTotalMemoryReserved() {
    return p1Sys.getTotalMemoryReserved();
  }

  // ***********************************
  /* BEGIN OF PRIVATE HELPER METHODS */
  // ***********************************

  /**
   * Finds the associated block that has been moved of the given address.
   */
  private MovedMemBlock associatedMovedBlock(int address, Iterable<MovedMemBlock> movedBlocks) {
    // determines the moved block in movedBlocks that contained v's address
    for (MovedMemBlock b : movedBlocks)
      if (b.getOldAddress() <= address && address < b.getOldAddress() + b.getSize())
        return b;

    return null;
  }

  /**
   * Searches for the given variable name in the type list.
   * 
   * @param name
   *          the variable to search for.
   * @return the variable or null if not found.
   */
  private Variable variableSearch(String name) {
    return varsList.get(name);
  }

  /**
   * Searches for the given data type name in the type list.
   * 
   * @param name
   *          the data type to search for.
   * @return the data type or null if not found.
   */
  private DataType typeSearch(String name) {
    return typesList.get(name);
  }

  /**
   * Validates a variable with the given information.
   * 
   * @param s
   *          the string that contains all the information to validate.
   * @return the variable if it exists, null otherwise.
   * @throws VariableDoesNotExistException
   *           whenever the given variable does not exist.
   * @throws NotReferenceVariableException
   *           whenever the variable is not a reference, and was specified to
   *           be.
   * @throws NotAnArrayVariableException
   *           whenever the variable is not an array, and was specified to be.
   * @throws NonPrimitiveDataTypeException
   *           whenever the variable is not of primitive data type, and was
   *           specified to be.
   * @throws NullArrayReferenceException
   *           whenever the array variable is null, and was specified not to be.
   */
  private Variable validateVariable(String s) throws VariableDoesNotExistException,
      NotReferenceVariableException, NotAnArrayVariableException, NonPrimitiveDataTypeException,
      NullArrayReferenceException {
    StringTokenizer st = new StringTokenizer(s);

    String vName = st.nextToken();
    Variable v = variableSearch(vName);
    if (v == null)
      throw new VariableDoesNotExistException("Variable " + vName + " does not exist.");

    // check all other characteristics
    while (st.hasMoreTokens()) {
      String next = st.nextToken();

      if (next.equalsIgnoreCase("reference")) {
        if (!(v instanceof ReferenceVariable))
          throw new NotReferenceVariableException("Variable " + vName
              + " is not a reference variable.");
      } else if (next.equals("array")) {
        if (!(v instanceof ArrayReferenceVariable))
          throw new NotAnArrayVariableException("Variable " + vName
              + " is not an array reference variable.");
      } else if (next.equals("primitive")) {
        if (v.getDataType() instanceof ObjectDataType)
          throw new NonPrimitiveDataTypeException("Variable " + vName
              + " refers to the non primitive data type " + v.getDataType().getName());
      } else if (next.equals("notnull")) {
        if (v.getValue().equals("-1"))
          throw new NullArrayReferenceException("Variable " + vName + " contains a null reference.");
      } else {
        // ignore for now

      }
    }
    return v;
  }

  /**
   * Validates a data type with the given information.
   * 
   * @param s
   *          the information string.
   * @return the validated data type, null if not found.
   * @throws DataTypeDoesNotExistException
   *           whenever the given data types does not exist.
   * @throws NotPrimitiveDataTypeException
   *           whenever the given data type is not primitive, and was specified
   *           to be.
   */
  private DataType validateDataType(String s) throws DataTypeDoesNotExistException,
      NotPrimitiveDataTypeException {
    StringTokenizer st = new StringTokenizer(s);

    String tName = st.nextToken();
    DataType dt = typeSearch(tName);
    if (dt == null)
      throw new DataTypeDoesNotExistException("DataType " + tName + " does not exist.");

    // check all other characteristics
    while (st.hasMoreTokens()) {
      String next = st.nextToken();

      if (next.equalsIgnoreCase("primitive")) {
        if (!(isPrimitive(dt)))
          throw new NotPrimitiveDataTypeException("Data type " + tName
              + " is not a primitive data type.");
      } else {
        // ignore for now

      }
    }
    return dt;
  }

  /**
   * Checks if the given identifier is valid
   * 
   * @param name
   *          - identifier
   * @return true if valid, false if not.
   */
  private boolean identifierNameIsValid(String name) {
    if (name.length() == 0 || !Character.isJavaIdentifierStart(name.charAt(0)))
      return false;

    for (int i = 1; i < name.length(); i++) {
      if (!Character.isJavaIdentifierPart(name.charAt(i)))
        return false;
    }

    return true;
  }

  /**
   * Tests a data type from primitivity.
   * 
   * @param t
   *          the data type to test.
   * @return true if the data type is primitive, false otherwise.
   */
  private boolean isPrimitive(DataType t) {
    String n = t.getName();
    if (n.equals("int") || n.equals("char") || n.equals("boolean"))
      return true;
    else
      return false;
  }
}
