import java.util.LinkedList;

/**
 * The structure of Frame.
 *
 * @author Magic Len
 */
class Frame {

  String data; // Use string to represent a data.

  Frame() {
    // Initialize
    data = null;
  }
}

/**
 * index
 */
public class index {

  static int numberOfFrames=2;
  public static void main(final String[] args) {
    // Initialize frames
    final Frame frames[] = new Frame[numberOfFrames];
    for (int i = 0; i < numberOfFrames; ++i) {
      frames[i] = new Frame();
    }
    // Make sure datas is not null
    final String list="01002";
    final String datas[] = list.split("");

    final int numberOfPageFault = LRU(frames, numberOfFrames, datas);
    System.out.println(numberOfPageFault);
  }

  /**
   * Least Recently Used Page Replacement.
   *
   * @param frames         input the available frames
   * @param numberOfFrames input the number of frames
   * @param datas     input the sequence of datas
   */
  private static int LRU(final Frame frames[], final int numberOfFrames, final String... datas) {
    // Use linked list to store the index of frames, the order of the list is the
    // order of victims. The larger index, the more recent frame.
    final LinkedList<Integer> list = new LinkedList<>();

    // Initialize list
    for (int i = 0; i < numberOfFrames; ++i) {
      list.add(i);
    }

    // Initialize the number of page fault
    int numberOfPageFault = 0;

    // Load datas in sequence
    final int numberOfdatas = datas.length;
    for (int n = 0; n < numberOfdatas; ++n) {
      final String data = datas[n];

      // Find whether the data exists in frames or not. If it is not found, the
      // page fault happened.
      boolean fault = true;
      for (int i = 0; i < numberOfFrames; ++i) {
        final Frame frame = frames[i];
        if (data.equals(frame.data)) {
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
        // data.
        final int victim = list.get(0);
        frames[victim].data = data;
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