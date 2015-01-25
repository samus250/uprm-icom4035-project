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
 * My seventh P3System tester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester7 {

  private static final P3System p3Sys = new P3System();

  public static void main(String[] args) {
    createObjectDataType("Node data int next Node");
    registerReferenceVariable("head", "Node");
    showAllInstanceFields("head");
    setInstanceFieldValue("head", "data", "1");
    showAllInstanceFields("head");
  }

  public static void deletePrimitiveVariable(String vName) {
    System.out.println("\nDeleting variable " + vName + "\n");
    p3Sys.deletePrimitiveDTVariable(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
    System.out.println("\nAssigning: " + vName + "." + fName + " = " + value + "\n");
    p3Sys.setInstanceFieldValue(vName, fName, value);
  }

  public static void setReferenceInstanceFieldValue(String v1Name, String fName, String v2Name) {
    System.out.println("\nAssigning: " + v1Name + "." + fName + " = reference of " + v2Name + "\n");
    p3Sys.setReferenceInstanceFieldValue(v1Name, fName, v2Name);
  }

  public static void setReferenceVariableValue(String v1Name, String v2Name) {
    System.out
        .println("\nCopying reference variable value from " + v2Name + " to " + v1Name + "\n");
    p3Sys.setReferenceVariableValue(v1Name, v2Name);
  }

  public static void showInstanceFieldValue(String vName, String fName) {
    String value = p3Sys.getInstanceFieldValue(vName, fName);
    System.out.println("\nShowing value: " + vName + "." + fName + " = " + value + "\n");

  }

  public static void registerReferenceVariable(String vName, String tName) {
    System.out.println("\nCreating variable " + vName + " of type " + tName + "\n");
    p3Sys.registerVariable(vName, tName);

    p3Sys.createAndAssignObject(vName);

  }

  public static void registerVariable(String vName, String tName) {
    System.out.println("\nCreating variable " + vName + " of type " + tName + "\n");
    p3Sys.registerVariable(vName, tName);
  }

  public static void showVariables(ArrayList<Variable> vlist) {
    System.out.println("\nRegistered Variables");
    Collections.sort(vlist, new VariableAddressComparator<Variable>());
    System.out.println("Var Name \tAddress \tValue");
    for (Variable v : vlist)
      System.out.println(v.getName() + "\t\t" + v.getAddress() + "\t\t" + v.getValue());
  }

  public static void showDataTypes(ArrayList<DataType> tList) {
    Collections.sort(tList);
    System.out.println("\nRegistered DataTypes");
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

  // some methods from here were written from testing and not specified
  public static void showAllInstanceFields(String vName) {
    ObjectDataType dt = (ObjectDataType) p3Sys.getVariableDataType(vName);
    ArrayList<InstanceField> ifl = dt.getInstanceFieldList();
    // getVariableDataType and
    // getInstanceFieldList are not supposed to exist
    System.out.println("Data Type: " + dt.getName());
    System.out.println("IF Name\tIF Type\tIF Value");
    for (InstanceField inst : ifl) {
      System.out.println(inst.getName() + "\t" + inst.getDataType().getName() + "\t"
          + p3Sys.getInstanceFieldValue(vName, inst.getName()));
    }
  }
}
