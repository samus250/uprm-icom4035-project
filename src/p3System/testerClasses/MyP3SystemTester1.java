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
 * My first P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester1 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
    p2Sys.registerVariable("foo", "boolean");
    p2Sys.registerVariable("bar", "boolean");
    p2Sys.registerVariable("thisArray", "array"); // this creates a reference to
                                                  // an array
    System.out.println("Initial thisArray value: " + p2Sys.getVariableValue("thisArray"));
    int length = 100;
    p2Sys.createAndAssignArrayObject("thisArray", "int", length);
    System.out.println("Length: " + p2Sys.getArrayLength("thisArray"));
    for (int i = 0; i < length; i++) {
      p2Sys.setArrayElementValue("thisArray", i, Integer.toString(65 + i));
    }
    for (int i = 0; i < length; i++) {
      System.out.println("thisArray[" + i + "] = " + p2Sys.getArrayElementValue("thisArray", i));
    }
    System.out.println("Creating thisArray2");
    p2Sys.registerVariable("thisArray2", "array");
    p2Sys.setReferenceVariableValue("thisArray2", "thisArray");
    System.out
        .println("thisArray and thisArray2 point to the exact same array. Change from thisArray2 is seen.");
    p2Sys.setArrayElementValue("thisArray2", 0, "-1");
    System.out.println("Showing thisArray2 contents:");
    for (int i = 0; i < length; i++) {
      System.out.println("thisArray[" + i + "] = " + p2Sys.getArrayElementValue("thisArray2", i));
    }

    p2Sys.getMemorySystem().showMMASCII(6, 50);
    p2Sys.setVariableValue("foo", "true");
    p2Sys.setVariableValue("bar", "false");

    p2Sys.registerVariable("samuel", "char");
    p2Sys.setVariableValue("samuel", "@");

    p2Sys.registerVariable("var1", "int");
    p2Sys.registerVariable("var2", "int");
    p2Sys.registerVariable("var3", "int");
    p2Sys.setVariableValue("var1", "66");
    p2Sys.deletePrimitiveDTVariable("var2");
    showVariables();

    // Now try creating objects..
    System.out.println("\nRegistering new Date and Cuatro Data Type...");
    createObjectDataType("Date month int day int year int");
    createObjectDataType("Cuatro first int second int third int fourth int");
    showDataTypes();
    System.out.println("Registering variable... ");
    registerReferenceVariable("object1", "Date");
    registerReferenceVariable("object2", "Cuatro");
    registerReferenceVariable("object3", "Date");
    showVariables();

    // now that we have created the object...
    setInstanceFieldValue("object1", "month", "4");
    setInstanceFieldValue("object1", "year", "2040");
    setInstanceFieldValue("object1", "day", "2");
    setInstanceFieldValue("object3", "month", "5");
    setInstanceFieldValue("object3", "year", "2011");
    setInstanceFieldValue("object3", "day", "25");
    setInstanceFieldValue("object2", "first", "1");
    showVariables();
    showDataTypes();
    System.out.println("setting reference instance field value of object1 from object3");
    p2Sys.setInstanceFieldValue("object1", "day", p2Sys.getInstanceFieldValue("object3", "day"));
    // showAllInstanceFields("object1");

    System.out.println("\nSetting object1 to object3");
    p2Sys.setReferenceVariableValue("object1", "object3");
    showVariables();

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
