package testerClasses;

import java.util.ArrayList;
import java.util.StringTokenizer;

import p3System.DataType;
import p3System.InstanceField;
import p3System.ObjectDataType;
import p3System.P3System;
import p3System.Variable;

/**
 * My first (not professor's) P2System Tester
 * 
 * @author samus250
 *
 */
public class MyP2SystemTester1 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {

    p2Sys.registerVariable("foo", "boolean");
    p2Sys.registerVariable("bar", "boolean");
    p2Sys.setVariableValue("foo", "true");
    p2Sys.setVariableValue("bar", "false");

    p2Sys.registerVariable("samuel", "char");
    p2Sys.setVariableValue("samuel", "@");

    p2Sys.registerVariable("var1", "int");
    p2Sys.registerVariable("var2", "int");
    p2Sys.registerVariable("var3", "int");
    p2Sys.setVariableValue("var1", "66");
    try {
      p2Sys.deletePrimitiveDTVariable("var2");
    } catch (Exception e) {

    }
    // p2Sys.registerVariable("var4", "int");
    showVariables();

    // Now try creating objects..
    System.out.println("\nRegistering new Date Data Type...");
    createObjectDataType("Date month int day int year int");
    showDataTypes();
    System.out.println("Registering variable... ");
    registerReferenceVariable("object1", "Date");
    showVariables();

    p2Sys.registerVariable("foo1", "boolean");
    p2Sys.registerVariable("bar1", "boolean");
    p2Sys.setVariableValue("foo1", "true");
    p2Sys.setVariableValue("bar1", "false");
    p2Sys.registerVariable("foo2", "boolean");
    p2Sys.registerVariable("bar2", "boolean");
    p2Sys.setVariableValue("foo2", "true");
    p2Sys.setVariableValue("bar2", "false");
    p2Sys.registerVariable("foo3", "boolean");
    p2Sys.registerVariable("bar3", "boolean");
    p2Sys.setVariableValue("foo3", "true");
    p2Sys.setVariableValue("bar3", "false");
    // now that we have created the object...
    setInstanceFieldValue("object1", "month", "4");
    setInstanceFieldValue("object1", "year", "2011");
    showVariables();
    setInstanceFieldValue("object1", "day", "2");
    showAllInstanceFields("object1");
    System.out.println();
    showVariables();

  }

  public static void showVariables() {
    ArrayList<Variable> vars = p2Sys.getVarList();
    System.out.println("Name\tType\tAddress\tValue");
    for (Variable v : vars) {
      System.out.println(v.getName() + "\t" + v.getDataType().getName() + "\t" + v.getAddress()
          + "\t" + v.getValue());
    }
  }

  public static void showDataTypes() {
    ArrayList<DataType> types = p2Sys.getTypeList();
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
    p2Sys.setVariableValue(vName, Integer.toString(p2Sys.createObject(tName)));
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
    ObjectDataType dt = (ObjectDataType) p2Sys.getVariableDataType("object1");
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
}
