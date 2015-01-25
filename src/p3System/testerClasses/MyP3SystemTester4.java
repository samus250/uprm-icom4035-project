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
 * My fourth P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester4 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
    createObjectDataType("Date day int month int year int");
    createObjectDataType("Tres first int second int third int");
    createObjectDataType("Cuatro first int second int third int fourth int");
    createObjectDataType("DTC date Date tres Tres cuatro Cuatro");
    createObjectDataType("TwoChars first char second char");

    // now register everything here
    registerReferenceVariable("dtc1", "DTC"); // this already does
                                              // createAndAssignObject
    registerReferenceVariable("date1", "Date");
    registerReferenceVariable("tres1", "Tres");
    registerReferenceVariable("cuatro1", "Cuatro");
    registerReferenceVariable("twochars1", "TwoChars");

    p2Sys.setInstanceFieldValue("twochars1", "first", "c");
    p2Sys.setInstanceFieldValue("twochars1", "second", "d");
    p2Sys.setInstanceFieldValue("date1", "day", "29");
    p2Sys.setInstanceFieldValue("date1", "month", "04");
    p2Sys.setInstanceFieldValue("date1", "year", "1991");

    p2Sys.setInstanceFieldValue("tres1", "first", "1");
    p2Sys.setInstanceFieldValue("tres1", "second", "2");
    p2Sys.setInstanceFieldValue("tres1", "third", "3");

    p2Sys.setInstanceFieldValue("cuatro1", "first", "4");
    p2Sys.setInstanceFieldValue("cuatro1", "second", "5");
    p2Sys.setInstanceFieldValue("cuatro1", "third", "6");
    p2Sys.setInstanceFieldValue("cuatro1", "fourth", "7");

    p2Sys.setReferenceInstanceFieldValue("dtc1", "date", "date1");
    p2Sys.setReferenceInstanceFieldValue("dtc1", "tres", "tres1");
    p2Sys.setReferenceInstanceFieldValue("dtc1", "cuatro", "cuatro1");

    showVariables();
    p2Sys.getMemorySystem().showReservedBlocks();

    // now lets test garbage collection
    p2Sys.setVariableValue("cuatro1", "null");
    System.out.println();
    showVariables();
    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.gc();
    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.setInstanceFieldValue("dtc1", "cuatro", "null");
    p2Sys.gc();
    p2Sys.getMemorySystem().showReservedBlocks();
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
