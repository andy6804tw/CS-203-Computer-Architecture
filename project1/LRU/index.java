package project1.LRU;

import java.util.LinkedList;

/**
 * The structure of Frame.
 *
 * @author Magic Len
 */
class Frame {

  String reference; // Use string to represent a reference.
  byte usebit; // Reference bit. If the frame is referenced, set this use bit.

  Frame() {
    // Initialize
    reference = null;
    usebit = 0;
  }
}

/**
 * index
 */
public class index {

  static int numberOfFrames=3;
  public static void main(final String[] args) {
    // Initialize frames
    final Frame frames[] = new Frame[numberOfFrames];
    for (int i = 0; i < numberOfFrames; ++i) {
      frames[i] = new Frame();
    }
    // Make sure references is not null
    final String list="EFABFCFDBCFCBAB";
    final String references[] = list.split("");

    final int numberOfPageFault = LRU(frames, numberOfFrames, references);
    System.out.println(numberOfPageFault);
  }

  /**
   * Least Recently Used Page Replacement.
   *
   * @param frames         input the available frames
   * @param numberOfFrames input the number of frames
   * @param references     input the sequence of references
   */
  private static int LRU(final Frame frames[], final int numberOfFrames, final String... references) {
    // Use linked list to store the index of frames, the order of the list is the
    // order of victims. The larger index, the more recent frame.
    final LinkedList<Integer> list = new LinkedList<>();

    // Initialize list
    for (int i = 0; i < numberOfFrames; ++i) {
      list.add(i);
    }

    // Initialize the number of page fault
    int numberOfPageFault = 0;

    // Load references in sequence
    final int numberOfReferences = references.length;
    for (int n = 0; n < numberOfReferences; ++n) {
      final String reference = references[n];

      // Find whether the reference exists in frames or not. If it is not found, the
      // page fault happened.
      boolean fault = true;
      for (int i = 0; i < numberOfFrames; ++i) {
        final Frame frame = frames[i];
        if (reference.equals(frame.reference)) {
          // Set the recent order, and break the search loop
          final int indexInList = list.indexOf(i);
          list.remove(indexInList);
          list.add(i);
          fault = false;
          break;
        }
      }
      if (fault) {
        // Page fault happened, then use the least recently used frame to replace its
        // reference.
        final int victim = list.get(0);
        frames[victim].reference = reference;
        // Set the recent order
        list.remove(0);
        list.add(victim);

        // Increase the number of page fault
        ++numberOfPageFault;
      }
    }
    return numberOfPageFault;
  }
}