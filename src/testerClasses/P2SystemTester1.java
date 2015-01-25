package testerClasses;

import p3System.P3System;

/**
 * Professor's first P2SystemTester1
 * 
 * @author samus250
 *
 */
public class P2SystemTester1 {

  public static void main(String[] args) {
    P3System p2Sys = new P3System();

    System.out.println("Following variables are of type int: ");
    for (int i = 0; i < 20; i++) {
      p2Sys.registerVariable("var" + i, "int");
      p2Sys.setVariableValue("var" + i, Integer.toString(65 + i));

      System.out.println("var" + i + " = " + p2Sys.getVariableValue("var" + i));
    }

    System.out.println("\n\nMemory content is: ");
    p2Sys.getMemorySystem().showMMASCII(0, 80);

  }

}
