package memoryClasses;

/**
 * This class represents a Register object.
 * 
 * @author samus250
 *
 */
public class Register {
  private byte[] regByte;

  // less significant part of the register is regByte[0]
  // most significant part is regByte[regByte.length-1]

  /**
   * Constructor. Creates a new register of size length().
   */
  public Register() {
    regByte = new byte[Register.length()];
  }

  /**
   * Returns the length of the register.
   * 
   * @return the length of the register.
   */
  public static int length() {
    return Memory.WORDSIZE;
  }

  /**
   * Returns the indicated byte from the register.
   * 
   * @param index
   *          the index of the byte to return.
   * @return the byte at the given index.
   * @throws IndexOutOfBoundsException
   *           whenever the given index is out of bounds.
   */
  public byte readByte(int index) throws IndexOutOfBoundsException {
    if (index < 0 || index >= regByte.length)
      throw new IndexOutOfBoundsException("readByte: Byte index is out of bounds.");

    return regByte[index];
  }

  /**
   * Sets a byte at the given index.
   * 
   * @param index
   *          the index where to set the byte.
   * @param nByte
   *          the byte that will be set.
   * @throws IndexOutOfBoundsException
   *           whenever the given index is out of bounds.
   */
  public void setByte(int index, byte nByte) throws IndexOutOfBoundsException {
    if (index < 0 || index >= regByte.length)
      throw new IndexOutOfBoundsException("setByte: Byte index is out of bounds.");

    regByte[index] = nByte;
  }

  /**
   * Returns a copy of the register.
   * 
   * @return a copy of the register.
   */
  public Register makeCopy() {
    Register reg = new Register();

    for (int i = 0; i < Register.length(); i++)
      reg.setByte(i, this.readByte(i));

    return reg;
  }

  /**
   * Returns an integer representation of the register.
   * 
   * @return an int representation of the register.
   */
  public int getIntFromRegister() {
    // address being referenced is valid to produce an integer value
    int value = 0;
    int lSB;
    for (int index = regByte.length - 1; index >= 0; index--) {
      value = value << Memory.BYTESIZE;
      lSB = 0x000000ff & regByte[index];
      value = value | lSB;
    }

    return value;

  }

  /**
   * Stores the given integer to the register.
   * 
   * @param value
   *          the integer to store.
   */
  public void copyIntToRegister(int value) {
    for (int index = 0; index < regByte.length; index++) {
      regByte[index] = (byte) (value & 0x000000ff);
      value = value >> Memory.BYTESIZE;
    }

  }

  /**
   * Copies a given register to another given register.
   * 
   * @param sReg
   *          source register.
   * @param dReg
   *          destination register.
   */
  public static void copyRegisterToRegister(Register sReg, Register dReg) {
    for (int index = 0; index < Register.length(); index++) {
      dReg.setByte(index, sReg.readByte(index));
    }

  }
}
