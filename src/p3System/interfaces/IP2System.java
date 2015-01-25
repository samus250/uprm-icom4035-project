package p3System.interfaces;

import p3System.ObjectDataType;
import p3System.exceptionClasses.DataTypeExistsException;
import p3System.exceptionClasses.InvalidDataTypeException;
import p3System.exceptionClasses.InvalidFieldNameException;
import p3System.exceptionClasses.InvalidNameException;
import p3System.exceptionClasses.InvalidValueException;
import p3System.exceptionClasses.NonPrimitiveDataTypeException;
import p3System.exceptionClasses.NotReferenceVariableException;
import p3System.exceptionClasses.UnsuccessfulRegistrationException;
import p3System.exceptionClasses.VariableDoesNotExistException;
import p3System.exceptionClasses.VariableNameExistsException;

/**
 * This interface represents the P2 Memory System
 * 
 * @author samus250
 *
 */
public interface IP2System {
  /**
   * Registers an ObjectDataType properly defined into the system.
   * 
   * @param type
   *          the new data type
   * @throws DataTypeExistsException
   *           whenever the datatype name clashes with an existing datatype.
   */
  void registerObjectDataType(ObjectDataType type) throws DataTypeExistsException;

  /**
   * Registers a variable in the "general scope" of the system. If valid, the
   * variable will be added to the list of variables (varsList). A memory area
   * in the memory is also reserved for the variable.
   * 
   * @param name
   *          the name of the variable to register
   * @param type
   *          the data type of the variable to register
   * @throws InvalidNameException
   *           whenever the name given does not comply with the specified rules
   *           for identifiers.
   * @throws VariableNameExistsException
   *           whenever the name is valid, but already exists in general scope.
   * @throws InvalidDataTypeException
   *           when the name is valid and there is no scope conflict, but the
   *           data type specified does not exist.
   * @throws UnsuccessfulRegistrationException
   *           whenever the declaration is valid, but no memory block of the
   *           required size is available (free) in memory
   */
  void registerVariable(String name, String type) throws InvalidNameException,
      VariableNameExistsException, InvalidDataTypeException, UnsuccessfulRegistrationException;

  /**
   * Gets the current value stored at the memory area currently assigned to a
   * variable in the current general scope in the system.
   * 
   * @param vName
   *          the name of the variable to consider
   * @return a String object formed by the current value in memory, converted to
   *         String
   * @throws VariableDoesNotExistException
   *           if v is not a variable in the general scopre
   */
  String getVariableValue(String vName) throws VariableDoesNotExistException;

  /**
   * Assigns a value to a variable. Stores it in the memory area associated to
   * the variable, converted as needed.
   * 
   * @param vName
   *          the name of the variable to consider
   * @param value
   *          the value, given as a String object
   * @throws VariableDoesNotExistException
   *           if v is not a variable in the general scope
   * @throws InvalidValueException
   *           if the attempted value does not match the particular data type of
   *           v
   */
  void setVariableValue(String vName, String value) throws VariableDoesNotExistException,
      InvalidValueException;

  /**
   * Gets the value stored in the corresponding memory area for a particular
   * instance field of a given object
   * 
   * @param vName
   *          the name of the variable to consider
   * @param fName
   *          the name of the field whose value is requested
   * @return a String object formed by the current value in memory, converted to
   *         String
   * @throws VariableDoesNotExistException
   *           if v is not a variable in the general scopre
   * @throws NotReferenceVariableException
   *           if the given variable is not a reference variable
   * @throws InvalidFieldNameException
   *           if the given name does not match any of the instance fields in
   *           the particular object type.
   */
  String getInstanceFieldValue(String vName, String fName) throws VariableDoesNotExistException,
      NotReferenceVariableException, InvalidFieldNameException;

  /**
   * Assigns a given value to a particular instance field in a given object.
   * Stores in the corresponding memory area, converted as needed
   * 
   * @param vName
   *          the name of the variable to consider
   * @param fName
   *          the name of the field whose value is requested.
   * @param value
   *          the value, given as a String object
   * @throws VariableDoesNotExistException
   *           if no registered variable has name vName
   * @throws NotReferenceVariableException
   *           if the given variable is not a reference variable
   * @throws InvalidFieldNameException
   *           if the given name does not match any of the instance fields in
   *           the particular object type.
   * @throws InvalidValueException
   *           if the attempted value does not match the particular instance
   *           field
   */
  void setInstanceFieldValue(String vName, String fName, String value)
      throws VariableDoesNotExistException, NotReferenceVariableException,
      InvalidFieldNameException, InvalidValueException;

  /**
   * Reserves space in memory corresponding to a particular object data type. A
   * block of memory is reserved whose size is the sum of the sizes of its
   * instance fields.
   * 
   * @param ot
   *          the object data type.
   * @return the address of the memory area reserved. If the space requested
   *         cannot be reserved, then it returns -1
   */
  int createObject(String ot);

  /**
   * Deletes a given variable from the system and realeases the memory area it
   * occupies.
   * 
   * @param vName
   *          the name of the variable to delete.
   * @throws VariableDoesNotExistException
   *           if no registered variable has name vName
   * @throws NonPrimitiveDataTypeException
   *           if a variable with he given name exists but is not of primitive
   *           type (int, char, or boolean).
   */
  void deletePrimitiveDTVariable(String vName) throws VariableDoesNotExistException,
      NonPrimitiveDataTypeException;
}
