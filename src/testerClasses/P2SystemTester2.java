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
 * Professor's second P2SystemTester.
 * 
 * @author samus250
 *
 */
public class P2SystemTester2 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) throws VariableDoesNotExistException,
      NonPrimitiveDataTypeException {

    p2Sys.registerVariable("cvar1", "char");
    p2Sys.setVariableValue("cvar1", "a");
    p2Sys.registerVariable("bvar1", "boolean");
    p2Sys.setVariableValue("bvar1", "true");
    p2Sys.registerVariable("cvar2", "char");
    p2Sys.setVariableValue("cvar2", "c");

    for (int i = 0; i < 20; i++) {
      p2Sys.registerVariable("var" + i, "int");
      p2Sys.setVariableValue("var" + i, Integer.toString(40 + i));
      p2Sys.registerVariable("bvar" + (i + 2), "boolean");
      if (i % 2 == 0)
        p2Sys.setVariableValue("bvar" + (i + 2), "false");
      else
        p2Sys.setVariableValue("bvar" + (i + 2), "true");
    }

    createObjectDataType("Date m int d int y int");
    createObjectDataType("OT1 a char b boolean c int d char w Date");

    registerReferenceVariable("vDate1", "Date");
    registerReferenceVariable("v1", "OT1");

    setInstanceFieldValue("vDate1", "m", "7");
    setInstanceFieldValue("vDate1", "d", "6");
    setInstanceFieldValue("vDate1", "y", "1999");
    showInstanceFieldValue("vDate1", "y");

    p2Sys.getMemorySystem().showMMASCII(0, 150);

    setInstanceFieldValue("vDate1", "m", "1");
    setInstanceFieldValue("vDate1", "d", "2");
    setInstanceFieldValue("vDate1", "y", "1024");
    showInstanceFieldValue("vDate1", "y");

    p2Sys.getMemorySystem().showMMASCII(0, 150);

    showVariables(p2Sys.getVarList());
    showDataTypes(p2Sys.getTypeList());

    deletePrimitiveVariable("var11");
    registerVariable("vDate3", "Date");

    p2Sys.getMemorySystem().showMMASCII(0, 150);

    showVariables(p2Sys.getVarList());
    showDataTypes(p2Sys.getTypeList());

  }

  public static void deletePrimitiveVariable(String vName) throws VariableDoesNotExistException,
      NonPrimitiveDataTypeException {
    System.out.println("\nDeleting variable " + vName + "\n");
    p2Sys.deletePrimitiveDTVariable(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
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
