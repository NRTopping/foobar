import java.util.LinkedList;
import java.util.Arrays;
import java.util.ListIterator;

public class Answer {   

  public static void main(String [] args) {
    int[][] m1 = {{0,1}, {1,2}, {2,3}, {3,5}, {4,5}};
    int[][] m2 = {{0, 1000000}, {42, 43}, {0, 1000000}, {42, 43}};


    System.out.println("Array 1: ");
    printArray(m1);
    printArray(sortDescending(m1));
    System.out.println("Max: " + answer(m1));


    System.out.println("Array 2: ");
    printArray(m2);
    printArray(sortDescending(m2));
    System.out.println("Max: " + answer(m2));
  }

  private static void printArray(int [][]a) {
    System.out.print("[");
    for (int i = 0; i < a.length; i++) { 
      System.out.print("[");
      for (int j = 0; j < a[i].length; j++) { 
        System.out.print(a[i][j] + ", ");
      }
      System.out.print("\b\b], ");
    }

    System.out.println("\b\b]");
  }

  // This is an Interval Scheduling Maximization problem
  public static int answer(int[][] meetings) { 

    // sort tasks in descending order of finishing time
    meetings = sortDescending(meetings);
    
    // Copy to a LinkedList
    LinkedList<Integer[]> m = new LinkedList<Integer[]>();
    for (int i = 0; i < meetings.length; i++) { 
      Integer[] a = new Integer[2];
      a[0] = meetings[i][0];
      a[1] = meetings[i][1];
      m.add(a);
    }
    printList(m);
    System.out.println();

    int maxCount = 0; 
    //  Algorithm: 
    while (m.size() > 0) { 
      // Select interval,x , with the earliest finishing time (one at the end), remove x
      Integer[] x = m.remove(m.size() - 1); 
      maxCount++;
    
      System.out.println("Curr Val: [" + x[0] + ", " + x[1] + "]");
      // Remove all intervals intersecting x, from the set of candidate intervals 
      ListIterator<Integer[]> itr = m.listIterator(0);
      while (itr.hasNext()) { 
          Integer[] curr = itr.next(); 
          if (intersects(curr, x)) { 
              System.err.println("REMOVING: [" + curr[0] + ", " + curr[1] + "]");
              itr.remove();   
          }
      }
      printList(m);
    }
      
    return maxCount; 
  } 
 
  private static void printList(LinkedList<Integer[]> m) { 
    ListIterator<Integer[]> it = m.listIterator(0);
    System.out.println("List: ");
    while(it.hasNext()) { 
      Integer [] curr = it.next();
      System.out.print("[" + curr[0] + ", " + curr[1] + "] -> ");
    }
    System.out.print("\b\b\n");
  }

  // determines if a and b intersect
  private static boolean intersects(Integer[] a, Integer[] b) { 
    int aStart = a[0].intValue();
    int aEnd   = a[1].intValue();
    int bStart = b[0].intValue();
    int bEnd   = b[1].intValue();
    
    return (aStart < bEnd && bStart < aEnd);
  }
  
  // sorts the given array in descedning order of finishing time
  // Since number of meetings <= 100, use insertion sort
  private static int[][] sortDescending(int [][] a) { 
    for (int i = 1; i < a.length; i++) { 
      int j = i; 
      
      while (j > 0 && a[j-1][1] < a[j][1]) { // do < since want descending
        int [] temp = a[j];
        a[j]   = a[j-1]; 
        a[j-1] = temp;
        j--; 
      }
    }
    return a;
  }       
}

