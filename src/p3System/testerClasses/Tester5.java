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
 * Professor's P3System linked list tester.
 * 
 * @author samus250
 *
 */
public class Tester5 {

  private static final P3System p3Sys = new P3System();

  public static void main(String[] args) {

    createObjectDataType("Date m int d int y int");
    createObjectDataType("OT1 a char b boolean c int d char w Date");

    createObjectDataType("Node data int next Node");

    registerVariable("a1", "int");
    registerVariable("a2", "int");
    registerVariable("a3", "char");
    registerVariable("a4", "int");
    registerVariable("a5", "int");

    registerReferenceVariable("vDate1", "Date");
    registerReferenceVariable("v1", "OT1");

    registerReferenceVariable("head", "Node");
    setInstanceFieldValue("head", "data", "1");
    appendNode("head", "tmp1", "2");
    for (int i = 2; i < 10; i++)
      appendNode("tmp" + (i - 1), "tmp" + i, "" + (i + 1));

    setReferenceInstanceFieldValue("tmp2", "next", "tmp4");
    setReferenceVariableValue("tmp3", "tmp4");

    registerArrayVariable("arr");

    // variables just before compacting
    System.out.println("Variables just before gc and compact.");
    showVariables(p3Sys.getVarList());

    System.out.println("Total memory used before gc and compact " + p3Sys.getTotalMemoryReserved());
    p3Sys.gc();
    compact();
    System.out.println("Total memory used after gc and compact " + p3Sys.getTotalMemoryReserved());

    deletePrimitiveVariable("a2");
    showVariables(p3Sys.getVarList());
    p3Sys.getMemorySystem().showMMHex(0, 250);

  }

  private static void appendNode(String vName, String newName, String value) {
    registerReferenceVariable(newName, "Node");
    setInstanceFieldValue(newName, "data", value);
    setReferenceInstanceFieldValue(vName, "next", newName);
  }

  private static void registerArrayVariable(String vName) {
    System.out.println("Creating varible " + vName + " of type array.");
    p3Sys.registerVariable(vName, "array");
  }

  private static void compact() {
    System.out.println("\nCompaction in action....");
    p3Sys.compactMemory();
  }

  /*
   * private static void setPrimitiveVariable(String vName, String value) {
   * System.out.println("\nAssigning: "+ vName + " = " + value + "\n");
   * p3Sys.setVariableValue(vName, value); }
   */

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
    Collections.sort(vlist, new VariableAddressComparator<Variable>());
    System.out.println("\nRegistered Variables");
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
}
