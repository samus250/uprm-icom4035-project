package p3System.testerClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import p3System.DataType;
import p3System.InstanceField;
import p3System.ObjectDataType;
import p3System.P3System;
import p3System.Variable;

/**
 * Professor's P3System garbage collection, compaction, and array tester.
 * 
 * @author samus250
 *
 */
public class P3SystemGCandCompactANDArrayTester_1 {

  static final int MTSHOW = 215; // memory positions to show
  private static final P3System p3Sys = new P3System();

  public static void main(String[] args) {
    registerVariable("a1", "int");
    registerVariable("a2", "int");
    registerVariable("a3", "char");
    registerVariable("a4", "int");
    registerVariable("a5", "int");

    setPrimitiveVariable("a1", "1");
    setPrimitiveVariable("a2", "2");
    setPrimitiveVariable("a3", "3");
    setPrimitiveVariable("a4", "4");
    setPrimitiveVariable("a5", "5");

    createObjectDataType("Date m int d int y int");
    createObjectDataType("OT1 a char b boolean c int d char w Date");

    deletePrimitiveVariable("a4");
    showVariables("Variables before the first compact");
    p3Sys.compactMemory();
    showVariables("Variables after the first compact");

    registerArrayVariable("arr1");

    registerArrayVariable("arr2");

    registerArrayVariable("arr3");

    registerArrayVariable("arr4");

    registerReferenceVariable("vDate1", "Date");
    registerReferenceVariable("v1", "OT1");

    createAndAssignArrayObject("arr2", "int", 6);

    createAndAssignArrayObject("arr2", "char", 3);

    createAndAssignArrayObject("arr3", "int", 10);

    System.out.println("Initializing elements in array arr3");
    int value = 65;
    for (int i = 0; i < 10; i++) {
      p3Sys.setArrayElementValue("arr3", i, value + "");
      value++;
    }
    showVariables("Variables after creating an initializing some arrays.");

    System.out.println("Creating a new array for arr1.");
    createAndAssignArrayObject("arr1", "int", 10);
    for (int i = 0; i < 10; i++) {
      p3Sys.setArrayElementValue("arr1", i, p3Sys.getArrayElementValue("arr3", i));
      value++;
    }

    p3Sys.setArrayElementValue("arr2", 1, "a");
    p3Sys.setArrayElementValue("arr2", 2, "b");

    registerVariable("a6", "char");
    registerVariable("a7", "int");

    setPrimitiveVariable("a6", "a");
    setPrimitiveVariable("a7", "2");

    System.out.println("Values in array arr1 before doing gc and compact.");
    for (int i = 0; i < 10; i++) {
      System.out.println("arr1[" + i + "]=" + p3Sys.getArrayElementValue("arr1", i));
    }

    System.out.println("Variables before gc and compact");
    p3Sys.gc();
    p3Sys.compactMemory();
    System.out.println("Values in array arr1 after doing gc and compact.");
    for (int i = 0; i < 10; i++) {
      System.out.println("arr1[" + i + "]=" + p3Sys.getArrayElementValue("arr1", i));
    }

    showVariables("Variables after gc and compact.");
    p3Sys.getMemorySystem().showMMHex(0, MTSHOW);

  }

  private static void showVariables(String msg) {
    System.out.println(msg);
    System.out.println("Registered Variables");
    System.out.println("Var Name \tType  \t\t\tAddress \tValue");
    ArrayList<Variable> vlist = p3Sys.getVarList();
    Collections.sort(vlist, new VariableAddressComparator<Variable>());
    for (Variable v : vlist)
      System.out.println(v);

  }

  private static void createAndAssignArrayObject(String aName, String eType, int length) {
    System.out.println("Creating a new array for array variable " + aName + ", of length " + length
        + " and elements of type " + eType);
    p3Sys.createAndAssignArrayObject(aName, eType, length);

  }

  private static void registerArrayVariable(String vName) {

    System.out.println("Creating varible " + vName + " of type array.");
    p3Sys.registerVariable(vName, "array");

  }

  private static void setPrimitiveVariable(String vName, String value) {
    System.out.println("Assigning: " + vName + " = " + value + ".");
    p3Sys.setVariableValue(vName, value);
  }

  public static void deletePrimitiveVariable(String vName) {
    System.out.println("Deleting variable " + vName + ".");
    p3Sys.deletePrimitiveDTVariable(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
    System.out.println("Assigning: " + vName + "." + fName + " = " + value + ".");
    p3Sys.setInstanceFieldValue(vName, fName, value);
  }

  public static void showInstanceFieldValue(String vName, String fName) {
    String value = p3Sys.getInstanceFieldValue(vName, fName);
    System.out.println("Showing value: " + vName + "." + fName + " = " + value + ".");

  }

  public static void registerReferenceVariable(String vName, String tName) {
    System.out.println("Creating variable " + vName + " of type " + tName + ".");
    p3Sys.registerVariable(vName, tName);
    p3Sys.createAndAssignObject(vName);
  }

  public static void registerVariable(String vName, String tName) {
    System.out.println("Creating variable " + vName + " of type " + tName + ".");
    p3Sys.registerVariable(vName, tName);
  }

  public static void showDataTypes(ArrayList<DataType> tList) {
    Collections.sort(tList);
    System.out.println("Registered DataTypes");
    System.out.println("Type Name \tSize");
    for (DataType dt : tList) {
      System.out.print(dt.getName() + "\t\t" + dt.getSize());
      if (dt instanceof ObjectDataType)
        System.out.println("   Object size = " + ((ObjectDataType) dt).getObjectSize());
      else
        System.out.println();
    }
  }

  public static void createObjectDataType(String s) {
    // s contains the name of the new Object data type, and the
    // the name and type of each one of the instancefileds
    // they are separated by spaces: for example,
    // "Date m int d int y int"
    // Such string is assumed to be well-formed.....
    StringTokenizer st = new StringTokenizer(s);

    String oTName = st.nextToken();
    ArrayList<InstanceField> ifList = new ArrayList<InstanceField>();
    while (st.hasMoreTokens()) {
      String fName = st.nextToken();
      String fTName = st.nextToken();
      DataType fType = p3Sys.getDataType(fTName);
      ifList.add(new InstanceField(fName, fType));
    }
    ObjectDataType odt = new ObjectDataType(oTName, ifList);
    p3Sys.registerObjectDataType(odt);
  }
}
