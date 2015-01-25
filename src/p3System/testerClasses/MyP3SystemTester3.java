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
 * My third P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester3 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
    createObjectDataType("Date day int month int year int");
    createObjectDataType("Tres first int second int third int");
    createObjectDataType("Cuatro first int second int third int fourth int");
    createObjectDataType("DTC date Date tres Tres cuatro Cuatro");
    createObjectDataType("TwoChars first char second char");
    showDataTypes();
    // first, register some primitives
    // make boolean variables
    for (int i = 0; i < 10; i++) {
      p2Sys.registerVariable("b" + i, "boolean");
      p2Sys.setVariableValue("b" + i, (i % 2 == 0) ? "true" : "false");
    }

    // make int variables
    for (int i = 0; i < 20; i++) {
      p2Sys.registerVariable("i" + i, "int");
      p2Sys.setVariableValue("i" + i, Integer.toString(i + 65));
    }

    // make char variables
    for (int i = 0; i < 10; i++) {
      p2Sys.registerVariable("c" + i, "char");
      char charToInsert = 'a';
      charToInsert += i;
      p2Sys.setVariableValue("c" + i, "" + charToInsert);
    }

    // now register everything here
    registerReferenceVariable("dtc1", "DTC"); // this already does
                                              // createAndAssignObject
    registerReferenceVariable("date1", "Date");
    registerReferenceVariable("tres1", "Tres");
    registerReferenceVariable("cuatro1", "Cuatro");
    registerReferenceVariable("twochars1", "TwoChars");

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
    showAllInstanceFields("dtc1");
    showRegisteredObjects();

    // now destroy even numbered booleans
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 0) {
        try {
          p2Sys.deletePrimitiveDTVariable("b" + i);
        } catch (Exception e) {

        }
      }
    }

    // destroy all even numbered ints
    for (int i = 0; i < 20; i++) {
      if (i % 2 == 0) {
        try {
          p2Sys.deletePrimitiveDTVariable("i" + i);
        } catch (Exception e) {

        }
      }
    }

    // delete all even numbered chars
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 0) {
        try {
          p2Sys.deletePrimitiveDTVariable("c" + i);
        } catch (Exception e) {

        }
      }
    }

    showVariables();
    // now compact memory
    p2Sys.compactMemory();

    System.out.println("Variables after compacting:");
    showVariables();

    showAllInstanceFields("dtc1");
    showRegisteredObjects();
    p2Sys.getMemorySystem().showReservedBlocks();
    p2Sys.getMemorySystem().showMMASCII(0, 160);
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
    System.out.println("\nCreating variable " + vName + " of type " + tName + "\n");
    p2Sys.registerVariable(vName, tName);
    p2Sys.createAndAssignObject(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
    System.out.println("\nAssigning: " + vName + "." + fName + " = " + value);
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
