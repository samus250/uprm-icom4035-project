package p3System.testerClasses;

import java.util.Comparator;

import p3System.Variable;

/**
 * A comparator that compares variables by their addresses.
 * 
 * @author samus250
 *
 * @param <T>
 *          comparator generic data type.
 */
public class VariableAddressComparator<T> implements Comparator<T> {

  public int compare(T v1, T v2) {
    Variable vv1 = null, vv2 = null;
    vv1 = (Variable) v1;
    vv2 = (Variable) v2;

    Integer a1 = vv1.getAddress();
    Integer a2 = vv2.getAddress();
    return a1.compareTo(a2);
  }

}
