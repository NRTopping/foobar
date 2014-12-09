// just int representation
//
public class Answer2 { 
  public static void main(String [] args) {
    //printTest(2,1,1);   // 1
    //printTest(3,2,3);   // 2
    //printTest(3,3,1);   // 3
    //printTest(4,3,16);  // 4
    //printTest(4,4,15);  // 5
    //printTest(4,5,6);   // 6
    //printTest(4,6,1);   // 7
    //printTest(5,4,125); // 8
    printTest(5,5,222); // 9
    //printTest(5,6,205); // 10
    //printTest(5,7,120); // 11
    //printTest(5,8,45);  // 12
    //printTest(5,9,10);  // 13
    //printTest(5,10,1);  // 14
  }

  private static void printTest(int n1, int k1, int expected) { 
    System.out.println("[" + expected + "] : [" + answer(n1, k1) + "]\n");
  }

  public static String answer(int N, int K) { 
    return Integer.toString(c(N,K, 0));
  }

  private static int c(int n, int k, int lvl) {
    // TODO memoize 

    // Check known-quantities
    if (k < n - 1 || k > n * (n-1) / 2) { 
      // out of range
      return 0; 
    } else if (k == n - 1) {
      // Cayley's formula
      return (int)Math.pow(n, n-2);
    }

    // Base case's 
    if (n == 1 || n == 2) { 
      return 1; 
    } 

    // Recurssion 
    int d_n = totalGraphsNK(n,k); // total graphs, including unconnected ones
    int sum = 0; 
    for (int i = 1; i < n; i++) { 
      for (int j = i-1; j < Math.min(genCombination(i,2),k) + 1; j++) { 
        int comb       = genCombination(n-1, i-1);
        int rec        = c(i, j, lvl+1);
        int totalGraph = totalGraphsNK(n-i, k-j);
        
        sum += comb * rec * totalGraph; 
      }
    }

    return d_n - sum; 
  }

  private static void printTabbed(String str, int tab) { 
    for (int i = 0; i < tab; i++) { 
      System.out.print("  "); 
    }
    System.out.println(str);
  }

  // returns the total number of simple, labeled graphs for n nodes and k edges. 
  // graphs are not necessarily connected
  private static int totalGraphsNK(int n, int k) { 
    // TODO memoize 
    // total, possible edges for a graph is n choose 2
    int totalEdges = genCombination(n, 2);

    // totalGraphs with K edges is totalEdges choose L
    return genCombination(totalEdges, k);
  }
 
  // Returns the general combination for n choose k
  private static int genCombination(int n, int k) { 
    // TODO memoize 
    if (k < 0 || n < 0 || n < k) { 
      return 0; 
    } 
    
    // C(n,k) = n! / (k! * (n-k)!) 
    return factorial(n) / (factorial(k) * factorial(n-k));
  }

  // calculates the factorial of a given number
  private static int factorial(int n) { 
    // TODO memooize

    if (0 <= n && n <= 1) { 
      return 1;
    } else if (n < 0) {
      return 0; 
    } else {
      return n * factorial(n-1);
    }
  }
}
