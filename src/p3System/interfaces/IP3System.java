package p3System.interfaces;

import p3System.exceptionClasses.DataTypeDoesNotExistException;
import p3System.exceptionClasses.InvalidArrayLengthException;
import p3System.exceptionClasses.InvalidAssignmentException;
import p3System.exceptionClasses.InvalidFieldNameException;
import p3System.exceptionClasses.InvalidValueException;
import p3System.exceptionClasses.NotAnArrayVariableException;
import p3System.exceptionClasses.NotArrayVariableException;
import p3System.exceptionClasses.NotPrimitiveDataTypeException;
import p3System.exceptionClasses.NotReferenceVariableException;
import p3System.exceptionClasses.NullArrayReferenceException;
import p3System.exceptionClasses.VariableDoesNotExistException;

/**
 * This is an IP3System. Any implementation should implement this interface.
 * 
 * @author samus250
 *
 */
public interface IP3System extends IP2System {

  /**
   * Assigns value (address) to a given reference variable.
   * 
   * @param v1Name
   *          the destination variable (the one whose value is changed to the
   *          new value).
   * @param v2Name
   *          the source variable (the one whose current value is copied to the
   *          destination variable).
   * @throws VariableDoesNotExistException
   *           whenever one of the variables does not exist as a registered
   *           variable.
   * @throws InvalidAssignmentException
   *           when the variables are valid registered variables, but at least
   *           one of the two is not a reference variable or they are reference
   *           variables but of different ObjectDataType.
   */
  void setReferenceVariableValue(String v1Name, String v2Name)
      throws VariableDoesNotExistException, InvalidAssignmentException;

  /**
   * Assigns value to the instance field whose type is an ObjectDataType (hence
   * the field is a reference field). The value to assign is taken from a
   * reference variable.
   * 
   * @param v1Name
   *          the destination variable (the one whose instance field value is
   *          changed to the new value).
   * @param fName
   *          the name of the instance field hwose value is to be changed.
   * @param v2Name
   *          the source variable (the one whose current value is copied to the
   *          destination variable).
   * @throws VariableDoesNotExistException
   *           when ever one of the variables does not exist as a registered
   *           variable.
   * @throws NotReferenceVariableException
   *           when the first v1Name is a valid registered variable, but not a
   *           reference variable.
   * @throws InvalidFieldNameException
   *           when the fName is not a valid field in the ObjectDataType of the
   *           reference variable.
   * @throws InvalidAssignmentException
   *           when the variables are valid registered variable, as well as the
   *           instance field, but at least one of the two, the source variable
   *           or the instance field is not of type "reference" or they are of
   *           type "reference" but of different ObjectDataType.
   */
  void setReferenceInstanceFieldValue(String v1Name, String fName, String v2Name)
      throws VariableDoesNotExistException, NotReferenceVariableException,
      InvalidFieldNameException, InvalidAssignmentException;

  /**
   * Reserves space in memory corresponding to a particular object data type,
   * which is the data type of a reference variable that is specified. A block
   * of memory is reserved with size equal to the sum of the sizes of its
   * instance fields. The address of the memory block reserved is assigned (as
   * its address value) to the reference variable. This methods presumes that
   * there is enough memory available.
   * 
   * @param vName
   *          the name of the reference variable.
   * @throws VariableDoesNotExistException
   *           whenever the variable does not exist as a registered variable.
   * @throws NotReferenceVariableException
   *           when vName is a valid registered variable, but not a reference
   *           variable.
   */
  void createAndAssignObject(String vName) throws VariableDoesNotExistException,
      NotReferenceVariableException;

  /**
   * Does garbage collection in memory. Marks as "free" or "available" those
   * memory areas corresponding to objects not being referenced anymore.
   * (Releases previously reserved, but not currently in use, memory areas.)
   */
  void gc();

  /**
   * Compacts memory, hence terminating fragmentation. Moves reserved locations
   * as needed for the reserved memory to become a contiguous block at the lower
   * portion of the memory. It also does garbage collection. Any existing
   * variable will continue existing with their current values, but possible
   * moved to other areas in memory.
   */
  void compactMemory();

  /**
   * Reserves a memory area for an array object with elements of a particular
   * primitive data type and assigns its address to a variable of type "array".
   * 
   * @param vName
   *          the name of the array variable.
   * @param tName
   *          the name of the data type of array elements.
   * @param length
   *          the length of the array being created.
   * @throws VariableDoesNotExistException
   *           when vName is not the name of a registered variable.
   * @throws NotArrayVariableException
   *           when the variable exists but is not of type "array".
   * @throws DataTypeDoesNotExistException
   *           if the data type name specified is not a registered data type.
   * @throws NotPrimitiveDataTypeException
   *           when the specified data type is not primitive.
   * @throws InvalidArrayLengthException
   *           when the value is negative or zero.
   */
  void createAndAssignArrayObject(String vName, String tName, int length)
      throws VariableDoesNotExistException, NotArrayVariableException,
      DataTypeDoesNotExistException, NotPrimitiveDataTypeException, InvalidArrayLengthException;

  /**
   * Assigns a value to a given position in an array.
   * 
   * @param vName
   *          the name of the array variable.
   * @param index
   *          the index value of the position in the array.
   * @param value
   *          the value to assign.
   * @throws VariableDoesNotExistException
   *           if vName is not the name of an existing variable.
   * @throws NotAnArrayVariableException
   *           if vName is an existing variable but is not of type "array".
   * @throws NullArrayReferenceException
   *           when the array variable has value null.
   * @throws IndexOutOfBoundsException
   *           if index is out of bounds.
   * @throws InvalidValueException
   *           if the attempted value does not match the particular data type of
   *           the array.
   */
  void setArrayElementValue(String vName, int index, String value)
      throws VariableDoesNotExistException, NotAnArrayVariableException,
      NullArrayReferenceException, IndexOutOfBoundsException, InvalidValueException;

  /**
   * Gets a value to a given position in an array.
   * 
   * @param vName
   *          the name of the array variable
   * @param index
   *          the index value of the position in the array.
   * @return the value as a String.
   * @throws VariableDoesNotExistException
   *           if vName is not the name of an existing variable.
   * @throws NotAnArrayVariableException
   *           if vName is an existing variable but is not of type "array".
   * @throws NullArrayReferenceException
   *           when the array variable has value null.
   * @throws IndexOutOfBoundsException
   *           if index is out of bounds.
   */
  String getArrayElementValue(String vName, int index) throws VariableDoesNotExistException,
      NotAnArrayVariableException, NullArrayReferenceException, IndexOutOfBoundsException;

  /**
   * Returns the length of an array.
   * 
   * @param vName
   *          the name of the array variable.
   * @return the length of the array.
   * @throws VariableDoesNotExistException
   *           if vName is not the name of an existing variable.
   * @throws NotAnArrayVariableException
   *           if vName is an existing variable but is not of type "array".
   * @throws NullArrayReferenceException
   *           when the array variable has value null.
   */
  int getArrayLength(String vName) throws VariableDoesNotExistException,
      NotAnArrayVariableException, NullArrayReferenceException;
}
