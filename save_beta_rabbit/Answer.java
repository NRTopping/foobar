// N will be 1 to 20 
// Foor units per zombie is <= 10
// can only move down and to the right
// amount of initial food is no larger than 200
// need to get to bottom right corner

import java.util.Arrays;

public class Answer {   
  public static void main(String [] args) { 
    int [][] g1 = {{0,2,5},
                   {1,1,3},
                   {2,1,1}};
    int f1 = 7;
    int f2 = 12; 

    System.out.println("The Grid:");
    print2DArray(g1);

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


  // Can just do dfs?
  public static int answer(int food, int[][] grid) { 

    // Your code goes here.
    return dfs(0, 0, grid, food);
  } 

  // Algorithm 2. 
  
    //Compute food remaining for top row, left column 
    // Then starting at [1][1] and going row by row, 

  // r = row index
  // c = column index
  // grid = grid we are traversing
  // food = how much food we have left at 
  //
  // Return min number of food left
  private static int dfs(int r, int c, int[][] grid, int food) { 
    // base case, at bottom left hand corner
    if (r == grid.length - 1 && c == grid[0].length - 1) { 
      System.out.println("Base Case reached with " + (food - grid[r][c]) + " left");
      return food - grid[r][c]; // return amount of food left minus feeding zombie
    }
    
    System.out.println("\nR: " + r + "  C: " + c + "  Curr food: " + food);
    // otherwise calculate amount of food left
    int foodRemaining = food - grid[r][c];
    System.out.println("Food Reamining: " + foodRemaining);

    // if foodLeft is negative, cannot continue so return
    if (foodRemaining < 0) { 
      return foodRemaining;
    }
    // terminate if not possible to reach end
    if (foodRemaining < grid.length    - 1 - r ||
        foodRemaining < grid[0].length - 1 - c) {
      return -1;    
    }
    int fDown  = -1; 
    int fRight = -1; 

    // otherwise go down (if possible)
    if (r < grid.length - 1) {
      fDown = dfs(r + 1, c, grid, foodRemaining);
    }

    // and go right (if possible)
    if (c < grid[r].length - 1) {
      fRight = dfs(r, c + 1, grid, foodRemaining);
    }

    // if both negative, cannot continue from this point return -1 
    if (fDown < 0 && fRight < 0) {
      return -1;
    } else if (fDown < 0) {
      return fRight;
    } else if (fRight < 0) { 
      return fDown;
    } else {
      // both are positive, meaning could reach the end, return the smaller of
      // the two
      return Math.min(fDown, fRight);
    }
  }
}
