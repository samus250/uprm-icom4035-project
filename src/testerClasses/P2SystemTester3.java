package testerClasses;

import java.util.ArrayList;
import java.util.StringTokenizer;

import p3System.DataType;
import p3System.InstanceField;
import p3System.ObjectDataType;
import p3System.P3System;
import p3System.Variable;
import p3System.exceptionClasses.NonPrimitiveDataTypeException;
import p3System.exceptionClasses.VariableDoesNotExistException;

/**
 * Professor's third P2SystemTester
 * 
 * @author samus250
 *
 */
public class P2SystemTester3 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {

    createObjectDataType("Triple a int b int c int");
    createObjectDataType("Cuatro a Triple d int");

    registerReferenceVariable("v1", "Triple");

    setIntInstanceFieldValue("v1", "a", "1094795585");
    setIntInstanceFieldValue("v1", "b", "1111638594");
    setIntInstanceFieldValue("v1", "c", "1128481603");

    registerVariable("v3", "int");
    registerReferenceVariable("v2", "Cuatro");

    // setIntInstanceFieldValue("v2", "a", "1162167621");

    setIntVariableValue("v3", "1179010630");

    p2Sys.getMemorySystem().showMMASCII(0, 50);
    showVariables(p2Sys.getVarList());
    showDataTypes(p2Sys.getTypeList());

    p2Sys.createAndAssignObject("v1");
    // p2Sys.setVariableValue("v1",
    // Integer.toString(p2Sys.createObject("Triple")));
    setIntInstanceFieldValue("v1", "a", "1094795585");
    setIntInstanceFieldValue("v1", "b", "1195854759");
    setIntInstanceFieldValue("v1", "c", "1128481603");

    showVariables(p2Sys.getVarList());
    showDataTypes(p2Sys.getTypeList());
    p2Sys.getMemorySystem().showMMASCII(0, 50);

  }

  private static void setIntVariableValue(String vName, String value) {
    System.out.println("\nAssigning: " + vName + " = " + value + "\n");
    p2Sys.setVariableValue(vName, value);
  }

  public static void deletePrimitiveVariable(String vName) throws VariableDoesNotExistException,
      NonPrimitiveDataTypeException {
    System.out.println("\nDeleting variable " + vName + "\n");
    p2Sys.deletePrimitiveDTVariable(vName);
  }

  public static void setIntInstanceFieldValue(String vName, String fName, String value) {
    System.out.println("\nAssigning: " + vName + "." + fName + " = " + value + "\n");
    p2Sys.setInstanceFieldValue(vName, fName, value);
  }

  public static void showInstanceFieldValue(String vName, String fName) {
    String value = p2Sys.getInstanceFieldValue(vName, fName);
    System.out.println("\nShowing value: " + vName + "." + fName + " = " + value + "\n");

  }

  public static void registerReferenceVariable(String vName, String tName) {
    System.out.println("\nCreating variable " + vName + " of type " + tName + "\n");
    p2Sys.registerVariable(vName, tName);
    p2Sys.createAndAssignObject(vName);
    // p2Sys.setVariableValue(vName,
    // Integer.toString(p2Sys.createObject(tName)));
  }

  public static void registerVariable(String vName, String tName) {
    System.out.println("\nCreating variable " + vName + " of type " + tName + "\n");
    p2Sys.registerVariable(vName, tName);
  }

  public static void showVariables(ArrayList<Variable> vlist) {
    System.out.println("\nRegistered Variables");
    System.out.println("Var Name \tAddress \tValue");
    for (Variable v : vlist)
      System.out.println(v.getName() + "\t\t" + v.getAddress() + "\t\t" + v.getValue());
  }

  public static void showDataTypes(ArrayList<DataType> tList) {
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
      DataType fType = p2Sys.getDataType(fTName);
      ifList.add(new InstanceField(fName, fType));
    }
    ObjectDataType odt = new ObjectDataType(oTName, ifList);
    p2Sys.registerObjectDataType(odt);

  }

}
