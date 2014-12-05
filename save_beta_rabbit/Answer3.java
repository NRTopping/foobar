import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;

public class Answer3 { 
  public static void main (String [] args) { 
    int [][] g1 = {{0,2,5},
                   {1,1,3},
                   {2,1,1}};
    int [][] g2 = {{0,2,5},
                   {1,1,3},
                   {2,1,1}};
    int f1 = 7;
    int f2 = 12; 

    System.out.println("The Grid:");
    print2DArray(g1);

    System.out.println();

    printDiagArray(g2);

    System.out.println("\nF1: " + f1);
    System.out.println("Answer: " +  answer(f1, g1));

    System.out.println();

    System.out.println("\nF2: " + f2);
    System.out.println("Answer: " + answer(f2, g2));
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
    
    // This will store all possible costs at a given position. 
    IntSet [][] fpc = new IntSet[n][n];

    // Init the lower right corner from grid 
    fpc[n-1][n-1] = new IntSet();
    fpc[n-1][n-1].add(grid[n-1][n-1]);

    // Init the bottom row and right column 
    for (int i = n-2; i >= 0; i--) { 
      // modify Grid positions, will not need to refer to these after this step
      // and removed need for iterator
      // Bottom Row
      grid[n-1][i] = grid[n-1][i] + grid[n-1][i+1];
      fpc[n-1][i] = new IntSet();
      fpc[n-1][i].add(grid[n-1][i]);

      // Right Column
      grid[i][n-1] = grid[i][n-1] + grid[i+1][n-1];
      fpc[i][n-1] = new IntSet();
      fpc[i][n-1].add(grid[i][n-1]);
    }
   
    // Now initialize all other valuse by traversint through the array
    // diagonally, starting from bottom right [n-2][n-2]
    for (int c = -n + 2; c < n; c++) { 
      int tmp = n - Math.abs(c) - 1; 
      int x   = tmp;
      while (x >= 0) { 
        int i, j;
        if (c >= 0) { 
          i = x; 
          j = tmp - x;
          createSet(food, grid, i, j, fpc);
        } else { 
          i = n - (tmp - x) - 1;
          j = (n - 1) - x; 
          createSet(food, grid, i, j, fpc);
        }
        x--;
      }
    }  
    
    // Iterate through [0][0], if no elements return -1, else retunr min
    Iterator<Integer> itr = fpc[0][0].iterator();
    int result = Integer.MAX_VALUE;
    if (!itr.hasNext()) { 
      System.out.println("Itr does not have next");
      return -1;
    } else { 
      while (itr.hasNext()) { 
        int currdiff = food - itr.next();
        if (currdiff < result && currdiff >= 0) {
          result = currdiff; 
        }
      }
      return result;
    }
  }

  // Creates the new hashset for a given position based on right and below
  // positions
  private static void createSet(int food, int[][] grid, 
                                int i,    int j, IntSet[][] fpc) { 
    int n = grid.length;
    System.out.println();
    // print out sizes.
    for (int r = 0; r < n; r++) { 
      for (int c = 0; c < n; c++) { 
        if (fpc[r][c] == null) { 
          System.out.print("[N]");
        } else { 
          System.out.print(fpc[r][c].toString());
        }
      }
      System.out.println();
    } 

    System.out.println();
    
    if (i + 1 < n && j + 1 < n) {
      fpc[i][j] = new IntSet();
      IntSet right = fpc[i][j+1];
      IntSet below = fpc[i+1][j];
      
      right.addAll(below);

      Iterator<Integer> itr = right.iterator(); 
      while (itr.hasNext()) { 
        int curr = itr.next();
        if (curr + grid[i][j] <= food) { 
          fpc[i][j].add(curr + grid[i][j]);
        } 
      }
      System.out.println();
    }

  }

  // Used to get pass java's "generic object" error for arrays
  private static class IntSet extends HashSet<Integer> {}
}
