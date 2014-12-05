import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections; 
import java.util.Iterator;

public class Answer2 { 
  public static void main (String [] args) { 
    int [][] g1 = {{0,2,5},
                   {1,1,3},
                   {2,1,1}};
    int f1 = 7;
    int f2 = 12; 

    System.out.println("The Grid:");
    print2DArray(g1);

    System.out.println();

    printDiagArray(g1);

    System.out.println("\nF1: " + f1);
    System.out.println("Answer: " +  answer(f1, g1));

    System.out.println();

    System.out.println("\nF2: " + f2);
    System.out.println("Answer: " + answer(f2, g1));
  }

  private static void print2DArray(int [][] g) { 
    System.out.print("[");
    for (int i = 0; i < g.length; i++) {
      if (i > 0) { 
        System.out.print(" ");
      }
      System.out.print(Arrays.toString(g[i]) + ",\n");
    }
    System.out.println("]");
  }

  // Test 
  private static void printDiagArray(int [][] g) { 
    int n = g.length; 

    for (int c = -n + 2; c < n; c++) { 
      int tmp = n - Math.abs(c) - 1; 
      int x   = tmp; 
      while (x >= 0) { 
        if (c >= 0) { 
          System.out.print(g[x][tmp - x] + "_ ");
        } else { 
          System.out.print(g[n-(tmp-x) - 1][(n-1)-x] + ", ");
        }
        x--;
      }
      System.out.println();
    }
  }

  public static int answer(int food, int[][] grid) { 
    int n = grid.length;

    // Create a 2d array of linked lists, this will store each food path
    IntList[][] fpc = new IntList[n][n];
    
    // Init bottom right corner first. Store value at that location as only
    // element 
    fpc[n - 1][n - 1] = new IntList();
    fpc[n - 1][n - 1].add(grid[n-1][n-1]);

    // Init the bottom row and right columns
    for (int i = n - 2; i >= 0; i--) { 
      // Bottom row
      int prev = fpc[n-1][i+1].iterator().next();
      fpc[n-1][i] = new IntList();
      fpc[n-1][i].add(grid[n-1][i] + prev);

      // Right column
      prev = fpc[i+1][n-1].iterator().next();
      fpc[i][n-1] = new IntList();
      fpc[i][n-1].add(grid[i][n-1] + prev);
    }

    // iterate starting from fpc[n-2][n-2] across diagonals
    for (int c = -n + 2; c < n; c++) { 
      int tmp = n - Math.abs(c) - 1; 
      int x   = tmp; 
      while (x >= 0) {
        int i, j; 
        if (c >= 0) { 
          i = x; 
          j = tmp - x; 
          
          if (i + 1 >= n || j + 1 >= n) { 
            x--;
            continue; // If can't get right or below, continue
          }
          // otherwise get list from the right and below, join and add to curr.
          IntList right = fpc[i][j + 1]; 
          IntList below = fpc[i + 1][j];
          right.addAll(below); 
          Iterator<Integer> itr = right.iterator();
          
          fpc[i][j] = new IntList();
          while(itr.hasNext()) { 
            int curr = itr.next();
            if (curr + grid[i][j] <= food) { 
              fpc[i][j].add(curr + grid[i][j]);
            }
          }

        } else { 
          i = n - (tmp - x) - 1; 
          j = (n - 1) - x; 
          
          if(i + 1 >= n || j + 1 >= n) { 
            x--;
            continue; // if can't get right or below, continue.
          } 
          // otherwise get list from the right and below, join and add to curr.
          IntList right = fpc[i][j + 1]; 
          IntList below = fpc[i + 1][j];
          right.addAll(below); 
          Iterator<Integer> itr = right.iterator();
          
          fpc[i][j] = new IntList();
          while(itr.hasNext()) { 
            int curr = itr.next();
            if (curr + grid[i][j] <= food) { 
              fpc[i][j].add(curr + grid[i][j]);
            }
          }
        }
        x--;
      }
    }
    
    // Look at fpc[0][0], find largest value less than or eqaul to food, return
    // difference
    Iterator<Integer> itr = fpc[0][0].iterator();
    if (!itr.hasNext()) {
        return -1;
    }
    int dif = food - itr.next();
    
    while (itr.hasNext()) {
      int cdiff = food - itr.next();
      if ( cdiff < dif && cdiff >= 0)  { 
        dif = cdiff; 
      }
    }

    return dif; 
  }

  // This is to get past Java Array Generic exception
  private static class IntList extends HashSet<Integer> {}
}
