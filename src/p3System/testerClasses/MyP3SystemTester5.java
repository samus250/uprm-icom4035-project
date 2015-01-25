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
 * My fifth P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester5 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
    // create many arrays, many array variables, and then test garbage
    // collection
    p2Sys.registerVariable("a1", "array");
    p2Sys.registerVariable("a2", "array");
    p2Sys.registerVariable("a3", "array");
    p2Sys.registerVariable("a4", "array");
    p2Sys.registerVariable("a5", "array");
    p2Sys.registerVariable("a6", "array");

    // make two arrays
    p2Sys.createAndAssignArrayObject("a1", "int", 10);
    p2Sys.setReferenceVariableValue("a2", "a1");
    p2Sys.setReferenceVariableValue("a3", "a2");

    p2Sys.createAndAssignArrayObject("a4", "char", 10);
    p2Sys.setReferenceVariableValue("a5", "a4");
    p2Sys.setReferenceVariableValue("a6", "a5");

    // populate char array
    for (int i = 0; i < 10; i++) {
      p2Sys.setArrayElementValue("a4", i, "" + (char) (65 + i));
    }

    // you may populate arrays if you want, but what I really want to do...

    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.setVariableValue("a1", "null");
    p2Sys.setVariableValue("a2", "null");
    p2Sys.gc();
    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.setVariableValue("a3", "null");
    p2Sys.gc();
    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.compactMemory();
    p2Sys.getMemorySystem().showReservedBlocks();

    // show char values (must be the same)
    for (int i = 0; i < 10; i++) {
      System.out.println("a6[" + i + "] = " + p2Sys.getArrayElementValue("a6", i));
    }

  }

  public static void showVariables() {
    ArrayList<Variable> vars = p2Sys.getVarList();
    Collections.sort(vars, new VariableAddressComparator<Variable>());
    System.out.println("Name\tType\tAddress\tValue");
    for (Variable v : vars) {
      System.out.println(v.getName() + "\t" + v.getDataType().getName() + "\t" + v.getAddress()
          + "\t" + v.getValue());
    }
  }

  public static void showDataTypes() {
    ArrayList<DataType> types = p2Sys.getTypeList();
    Collections.sort(types);
    System.out.println("Type\tSize");
    for (DataType t : types) {
      System.out.print(t.getName() + "\t" + t.getSize());
      if (t instanceof ObjectDataType)
        System.out.print("\tObject Size: " + ((ObjectDataType) t).getObjectSize());
      System.out.println();
    }
  }

  public static void createObjectDataType(String s) {
    StringTokenizer st = new StringTokenizer(s);

    String oTName = st.nextToken();
    ArrayList<InstanceField> ifList = new ArrayList<InstanceField>();
    while (st.hasMoreTokens()) {
      String fName = st.nextToken();
      String fTName = st.nextToken();
      DataType fType = p2Sys.getDataType(fTName);
      ifList.add(new InstanceField(fName, fType));
    }
    ObjectDataType odt = new ObjectDataType(oTName, ifList);
    p2Sys.registerObjectDataType(odt);
  }

  public static void registerReferenceVariable(String vName, String tName) {
    // System.out.println("\nCreating variable " + vName + " of type " + tName +
    // "\n");
    p2Sys.registerVariable(vName, tName);
    p2Sys.createAndAssignObject(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
    // System.out.println("\nAssigning: "+ vName + "." +fName + " = " + value);
    p2Sys.setInstanceFieldValue(vName, fName, value);
  }

  public static void showInstanceFieldValue(String vName, String fName) {
    String value = p2Sys.getInstanceFieldValue(vName, fName);
    System.out.println("\nShowing value: " + vName + "." + fName + " = " + value);

  }

  // some methods from here were written from testing and not specified
  public static void showAllInstanceFields(String vName) {
    ObjectDataType dt = (ObjectDataType) p2Sys.getVariableDataType(vName);
    ArrayList<InstanceField> ifl = dt.getInstanceFieldList();
    // getVariableDataType and
    // getInstanceFieldList are not supposed to exist
    System.out.println("Data Type: " + dt.getName());
    System.out.println("IF Name\tIF Type\tIF Value");
    for (InstanceField inst : ifl) {
      System.out.println(inst.getName() + "\t" + inst.getDataType().getName() + "\t"
          + p2Sys.getInstanceFieldValue(vName, inst.getName()));
    }
  }

  public static void showRegisteredObjects() {
    ArrayList<ObjectDataType> odt = new ArrayList<ObjectDataType>();
    for (DataType dt : p2Sys.getTypeList()) {
      if (dt instanceof ObjectDataType) {
        odt.add((ObjectDataType) dt);
      }
    }
    for (ObjectDataType dt : odt) {
      System.out.println("Registered objects for type " + dt.getName() + ":");
      for (int address : dt.getRegisteredObjects()) {
        System.out.print(address + ", ");
      }
      System.out.println();
    }
  }
}
