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
 * Professor's P3Ssytem garbage collection and compaction tester.
 * 
 * @author samus250
 *
 */
public class P3SystemGCandCompactTester_1 {

  static final int MTSHOW = 200; // memory positions to show
  private static final P3System p3Sys = new P3System();

  public static void main(String[] args) {

    registerVariable("a1", "int");
    registerVariable("a2", "int");
    registerVariable("a3", "char");
    registerVariable("a4", "int");
    registerVariable("a5", "int");

    setPrimitiveVariable("a1", "1");
    setPrimitiveVariable("a2", "2");
    setPrimitiveVariable("a3", "3");
    setPrimitiveVariable("a4", "4");
    setPrimitiveVariable("a5", "5");

    createObjectDataType("Date m int d int y int");
    createObjectDataType("OT1 a char b boolean c int d char w Date");

    p3Sys.gc();

    registerReferenceVariable("vDate1", "Date");
    registerReferenceVariable("v1", "OT1");

    setInstanceFieldValue("vDate1", "m", "7");
    setInstanceFieldValue("vDate1", "d", "6");
    setInstanceFieldValue("vDate1", "y", "1999");
    // showInstanceFieldValue("vDate1", "y");

    // p3Sys.getMemorySystem().showMMHex(0, 150);

    setInstanceFieldValue("vDate1", "m", "1");
    setInstanceFieldValue("vDate1", "d", "2");
    setInstanceFieldValue("vDate1", "y", "1024");
    // showInstanceFieldValue("vDate1", "y");

    // p3Sys.getMemorySystem().showMMHex(0, 150);
    showVariables(p3Sys.getVarList());

    registerVariable("a6", "int");
    registerVariable("a7", "int");
    registerVariable("a8", "char");

    setPrimitiveVariable("a6", "6");
    setPrimitiveVariable("a7", "7");
    setPrimitiveVariable("a8", "8");

    registerReferenceVariable("vDate2", "Date");
    setInstanceFieldValue("vDate2", "m", "8");
    setInstanceFieldValue("vDate2", "d", "16");
    setInstanceFieldValue("vDate2", "y", "2048");

    // p3Sys.getMemorySystem().showMMHex(0, 150);
    showVariables(p3Sys.getVarList());

    registerVariable("a9", "int");
    registerVariable("a10", "int");
    setPrimitiveVariable("a9", "9");
    setPrimitiveVariable("a10", "10");

    compact();
    p3Sys.getMemorySystem().showMMHex(0, MTSHOW);
    p3Sys.registerVariable("vDate3", "Date");
    p3Sys.setReferenceVariableValue("vDate3", "vDate2");
    // p3Sys.getMemorySystem().showMMHex(0, 150);

    registerReferenceVariable("vDate4", "Date");
    setInstanceFieldValue("vDate4", "m", "1");
    setInstanceFieldValue("vDate4", "d", "4");
    setInstanceFieldValue("vDate4", "y", "512");
    // p3Sys.setReferenceVariableValue("vDate1", "vDate4");
    // p3Sys.getMemorySystem().showMMHex(0, 150);

    p3Sys.setReferenceVariableValue("vDate3", "vDate1");
    // p3Sys.getMemorySystem().showMMHex(0, 150);

    p3Sys.setReferenceVariableValue("vDate2", "vDate1");

    System.out.println("Before changing value of v1.w");
    p3Sys.getMemorySystem().showMMHex(0, MTSHOW);

    p3Sys.setReferenceInstanceFieldValue("v1", "w", "vDate4");

    System.out.println("After changing value of v1.w");
    p3Sys.getMemorySystem().showMMHex(0, MTSHOW);

    p3Sys.setReferenceVariableValue("vDate4", "vDate1");
    setInstanceFieldValue("vDate4", "m", "12");
    showVariables(p3Sys.getVarList());
    showDataTypes(p3Sys.getTypeList());

    System.out.println("***** TOTAL BYTES IN USE OR RESERVED: " + p3Sys.getTotalMemoryReserved());

    deletePrimitiveVariable("a3");
    deletePrimitiveVariable("a5");
    deletePrimitiveVariable("a9");

    System.out.println("***** TOTAL BYTES IN USE OR RESERVED: " + p3Sys.getTotalMemoryReserved());

    registerReferenceVariable("vDate5", "Date");
    setInstanceFieldValue("vDate4", "m", "1");
    setInstanceFieldValue("vDate4", "d", "4");
    setInstanceFieldValue("vDate4", "y", "512");
    registerVariable("a11", "int");
    registerVariable("a12", "int");
    setPrimitiveVariable("a11", "11");
    setPrimitiveVariable("a12", "12");
    registerReferenceVariable("vDate6", "Date");
    setInstanceFieldValue("vDate4", "m", "1");
    setInstanceFieldValue("vDate4", "d", "4");
    setInstanceFieldValue("vDate4", "y", "512");
    registerReferenceVariable("vDate7", "Date");
    setInstanceFieldValue("vDate4", "m", "1");
    setInstanceFieldValue("vDate4", "d", "4");
    setInstanceFieldValue("vDate4", "y", "512");
    p3Sys.setReferenceVariableValue("vDate5", "vDate1");
    p3Sys.setReferenceVariableValue("vDate7", "vDate1");
    registerVariable("a13", "char");
    registerVariable("a14", "char");
    registerVariable("a15", "char");
    registerVariable("a16", "char");
    setPrimitiveVariable("a13", "F");
    setPrimitiveVariable("a14", "I");

    setPrimitiveVariable("a15", "N");
    setPrimitiveVariable("a16", ".");

    System.out.println("***** TOTAL BYTES IN USE OR RESERVED: " + p3Sys.getTotalMemoryReserved());

    System.out.println("Before GC: ");
    p3Sys.getMemorySystem().showFreeBlocks();

    p3Sys.gc();

    System.out.println("After GC, but prior to compact: ");
    p3Sys.getMemorySystem().showFreeBlocks();

    showVariables(p3Sys.getVarList());
    showDataTypes(p3Sys.getTypeList());

    compact();
    System.out.println("After Compact: ");
    p3Sys.getMemorySystem().showFreeBlocks();

    p3Sys.gc();

    compact();

    compact();

    System.out.println("After compacting memory");

    System.out.println("***** TOTAL BYTES IN USE OR RESERVED: " + p3Sys.getTotalMemoryReserved());

    registerReferenceVariable("v2", "OT1");
    System.out.println("***** TOTAL BYTES IN USE OR RESERVED: " + p3Sys.getTotalMemoryReserved());

    showVariables(p3Sys.getVarList());
    showDataTypes(p3Sys.getTypeList());
    p3Sys.getMemorySystem().showMMHex(0, MTSHOW);
    p3Sys.gc();

    // showObjectInstances();

  }

  private static void compact() {
    System.out.println("Compaction in action....");
    p3Sys.compactMemory();
  }

  /*
   * private static void showObjectInstances() {
   * System.out.println("Showing Object instances: ");
   * p3Sys.showObjectInstances();
   * 
   * }
   */
  private static void setPrimitiveVariable(String vName, String value) {
    System.out.println("Assigning: " + vName + " = " + value + ".");
    p3Sys.setVariableValue(vName, value);
  }

  public static void deletePrimitiveVariable(String vName) {
    System.out.println("Deleting variable " + vName + ".");
    p3Sys.deletePrimitiveDTVariable(vName);
  }

  public static void setInstanceFieldValue(String vName, String fName, String value) {
    System.out.println("Assigning: " + vName + "." + fName + " = " + value + ".");
    p3Sys.setInstanceFieldValue(vName, fName, value);
  }

  public static void showInstanceFieldValue(String vName, String fName) {
    String value = p3Sys.getInstanceFieldValue(vName, fName);
    System.out.println("Showing value: " + vName + "." + fName + " = " + value + ".");

  }

  public static void registerReferenceVariable(String vName, String tName) {
    System.out.println("Creating variable " + vName + " of type " + tName + ".");
    p3Sys.registerVariable(vName, tName);
    p3Sys.createAndAssignObject(vName);
  }

  public static void registerVariable(String vName, String tName) {
    System.out.println("Creating variable " + vName + " of type " + tName + ".");
    p3Sys.registerVariable(vName, tName);
  }

  public static void showVariables(ArrayList<Variable> vlist) {
    Collections.sort(vlist, new VariableAddressComparator<Variable>());
    System.out.println("Registered Variables");
    System.out.println("Var Name \tType  \t\tAddress \tValue");
    for (Variable v : vlist)
      System.out.println(v.getName() + "\t\t" + v.getDataType() + "\t\t" + v.getAddress() + "\t\t"
          + v.getValue());
  }

  public static void showDataTypes(ArrayList<DataType> tList) {
    Collections.sort(tList);
    System.out.println("Registered DataTypes");
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
