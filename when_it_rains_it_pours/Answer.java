
import java.util.Arrays;

public class Answer {   
  public static void main(String [] args) { 
    int [] input1 = {1, 4, 2, 5, 1, 2, 3}; // res is 6
    //. . . X . . .
    //. X 0 X . . .
    //. X 0 X 0 0 X
    //. X X X 0 X X
    //X X X X X X X
    //1 4 2 5 1 2 3

    int [] input2 = {1, 2, 3, 2, 1}; // res is 0

    System.out.println("Input 1: ");
    System.out.println("  " + Arrays.toString(input1));
    System.out.println("  Water pooled: " + answer(input1));
    
    System.out.println();
    
    System.out.println("Input 2: ");
    System.out.println("  " + Arrays.toString(input2));
    System.out.println("  Water pooled: " + answer(input2));
  }

  public static int answer(int[] heights) { 
      // Find the max height in heights:
      int maxHeight = -1; 
      for (int i = 0; i < heights.length; i++) { 
        if (heights[i] > maxHeight) { 
          maxHeight = heights[i];
        }
      }

      // Now that we know the max height, know how many iterations we have to do
      int water = 0;
      for (int lvl = 2; lvl <= maxHeight; lvl++) { 
        System.out.println("\nLVL: " + lvl);
        for (int i = 0; i < heights.length;) {
          // if the height of current block is greater than or equal to that of
          // our level, it is an edge for a container on this level
          if (heights[i] >= lvl) { 
            System.out.print("  Left: " + i);
            int left = i; // mark the left barrier
            int right; 

            // advance until we find our right border
            for (right = i + 1;  right < heights.length && heights[right] < lvl; right++);

            System.out.print("  Right: " + right);
            System.out.println("  Water: " + (right - left - 1));
            // calculate the difference and add to water count. 
            // right is the border, one greater than it should be
            // make sure right did not go off the edge
            if (right < heights.length) {
              water += (right - left - 1);
            }

            // set i = to right minus 1 (since loop will increment)
            i = right;
          } else { 
            i++;
          }
        } 
      }

      return water;
  } 
}
