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
 * My sixth P3SystemTester.
 * 
 * @author samus250
 *
 */
public class MyP3SystemTester6 {

  private static final P3System p2Sys = new P3System();

  public static void main(String[] args) {
    createObjectDataType("Date date Date day int month int year int");
    showDataTypes();
    registerReferenceVariable("v1", "Date");
    showAllInstanceFields("v1");
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
      DataType fType = p2Sys.getDataType(fTName); // es null si fTName es igual
                                                  // al objeto que se esta
                                                  // creando
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
