package project1;

import java.util.LinkedList;

/**
 * The structure of Frame.
 *
 */
class Frame {
  String reference; // Use string to represent a reference.

  Frame() {
    // Initialize
    reference = null;
  }
}

public class numberOfPageFaults {

  static int numberOfFrames = 2;

  public static void main(String[] args) {
    // Initialize frames
    Frame frames[] = new Frame[numberOfFrames];
    for (int i = 0; i < numberOfFrames; ++i) {
      frames[i] = new Frame();
    }

    String list = "0010";
    String references[] = list.split("");
    int numberOfPageFault = LRU(frames, numberOfFrames, references);
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

    // Show initial frames information
    System.out.printf("Sequence Number: 0%n");
    for (int i = 0; i < numberOfFrames; ++i) {
      final Frame frame = frames[i];
      final int recentOrder = list.indexOf(i);
      System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tRecent Order: %d%n", i + 1,
          (recentOrder == 0) ? "(least recently used)" : "", frame.reference, recentOrder);
    }

    // Load references in sequence
    final int numberOfReferences = references.length;
    for (int n = 0; n < numberOfReferences; ++n) {
      System.out.printf("Sequence Number: %d%n", n + 1);
      final String reference = references[n];
      System.out.printf("\tReference: %s%n", reference);

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
      System.out.printf("\tPage Fault: %b%n", fault);
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
      for (int i = 0; i < numberOfFrames; ++i) {
        final Frame frame = frames[i];
        final int recentOrder = list.indexOf(i);
        System.out.printf("\tFrame %d%s:%n\t\tReference: %s%n\t\tRecent Order: %d%n", i + 1,
            (recentOrder == 0) ? "(least recently used)" : "", frame.reference, recentOrder);
      }
      System.out.printf("%n");
    }
    return numberOfPageFault;
  }
}