package project1;

import java.util.LinkedList;

/**
 * test
 */
public class test {

  public static void main(String[] args) {
    final LinkedList<Integer> list = new LinkedList<>();
    list.add(0);
    list.add(1);
    list.add(2);
    list.remove(1);
    list.add(1);
    // System.out.println(list.get(0));

    for(int i=0;i<3;i++)
      System.out.print(list.indexOf(i)+" ");
    System.out.println("");
    for (int i = 0; i < 3; i++)
      System.out.print(list.get(i) + " ");
  }
}