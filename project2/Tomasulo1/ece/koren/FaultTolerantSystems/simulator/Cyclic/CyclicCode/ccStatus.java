package CyclicCode;

/**
 * <p>Title: The Cyclic Code Simulator</p>
 * <p>Description: to show the intermediate steps in encoding and decoding, allow the use of different encoding polynomials and find out whether the selected one is a generator polynomial </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Dongha Lee
 * @version 1.0
 */
public class ccStatus {
  public static final int ENCODE = 0;
  public static final int DECODE = 1;
  int n;
  int k;
  int encode;
  String input;
  String gates;
  String controlbits;
  String bits;
  String inputout;
  String output;
  String message;

  ccStatus() {
    input = null;
    gates = null;
    controlbits = null;
    bits = null;
    inputout = "";
    output = "";
    message = "";
    n = 0;
  }

  boolean init(int i, int j, String s, String s1, int l) {
    if (!valid(i, j, s, s1, l))
      return false;
    n = i;
    k = j;
    input = s1;
    gates = s;
    encode = l;
    bits = "";
    controlbits = "    ";
    for (int i1 = 0; i1 < i - j ; i1++) {
      bits += "0";
    }

    //calculate(0);
    return true;
  }

  boolean invalid_input(String s, int i, int j, int l) {
    int i1 = i != 0 ? j : l;
    return s == null || s.length() < 0 || s.length() % i1 != 0;
  }

  boolean invalid_gates(String s, int i, int j) {
    return s == null || s.length() < 0 || s.length() != (i - j) + 1;
  }

  boolean valid() {
    return valid(n, k, gates, input, encode);
  }

  boolean valid(int i, int j, String s, String s1, int l) {
    return i != 0 && j != 0 && !invalid_gates(s, i, j) &&
        !invalid_input(s1, l, i, j);
  }

  boolean isEncoding(){
    return (encode == ENCODE);
  }

  public String toString() {
    String s;
    if (!valid())
      s = "Invalid";
    else
      s = "N=" + n + "  K=" + k + "  gates=" + gates + "  input=" + input;
    return s;
  }

  int calculate(int i, boolean flag){
    int p[] = new int[n - k];
    if(bits==""){
    for (int j = 0; j < n - k ; j++)
      bits += "0";
    }
    for (int j = 0; j < n - k; j++)
      p[j] = Integer.valueOf(bits.substring(j, j+1)).intValue();;

    int nk = n - k - 1;
    int in = 0;
    try{
      if(encode == 0){
        in = Integer.valueOf(input.substring(k - i, k-i+1)).intValue();
      }else{
        in = Integer.valueOf(input.substring(n - i, n-i+1)).intValue();
      }
      inputout = String.valueOf(in) + inputout;
    }catch(StringIndexOutOfBoundsException stringindexoutofboundsexception){
        in = 0;
    }

    int out = ( p[nk] + in ) % 2;
    if(encode == 1) in = out;
    for(int j = nk; j>0; j--)
    {
      if(!gates.substring(j, j+1).equalsIgnoreCase("1")){
        p[j] = p[j - 1];
      }else{
        p[j] = (p[j - 1] + in) % 2;
      }
    }
    p[0] = in; bits = "";
    for (int j = 0; j < n - k; j++)
      bits += p[j];
    output = String.valueOf(out) + output;
    if(i>=n) return i-1;
    return i;
  }
}