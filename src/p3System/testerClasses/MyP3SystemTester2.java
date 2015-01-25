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
 * My second P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester2 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
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

    System.out.println("Variables created: ");
    showVariables();

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

    // Now create an array
    p2Sys.registerVariable("array1", "array");
    p2Sys.registerVariable("array2", "array");

    p2Sys.createAndAssignArrayObject("array1", "int", 20);
    for (int i = 0; i < 20; i++) {
      p2Sys.setArrayElementValue("array1", i, "" + (65 + i));
    }
    p2Sys.createAndAssignArrayObject("array2", "char", 20);
    for (int i = 0; i < 20; i++) {
      char charToInsert = 'a';
      charToInsert += i;
      p2Sys.setArrayElementValue("array2", i, "" + charToInsert);
    }

    System.out.println("Variables before compacting:\n");
    showVariables();

    // show array contents
    for (int i = 0; i < p2Sys.getArrayLength("array1"); i++) {
      System.out.println("array1[" + i + "] = " + p2Sys.getArrayElementValue("array1", i));
    }
    for (int i = 0; i < p2Sys.getArrayLength("array2"); i++) {
      System.out.println("array2[" + i + "] = " + p2Sys.getArrayElementValue("array2", i));
    }

    // now compact memory
    p2Sys.compactMemory();
    System.out.println("Variables after compacting:\n");
    showVariables();
    p2Sys.getMemorySystem().showReservedBlocks();

    // now show array contents:
    for (int i = 0; i < p2Sys.getArrayLength("array1"); i++) {
      System.out.println("array1[" + i + "] = " + p2Sys.getArrayElementValue("array1", i));
    }
    for (int i = 0; i < p2Sys.getArrayLength("array2"); i++) {
      System.out.println("array2[" + i + "] = " + p2Sys.getArrayElementValue("array2", i));
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
}
