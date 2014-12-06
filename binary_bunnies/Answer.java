import java.util.Arrays;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.ListIterator;

public class Answer { 
  public static void main(String [] args) { 
    int[] seq1 = {4,9,8,2,1};           //6
    int[] seq2 = {1,2,3,4,5,6,7,8,9,10};//1

    System.out.println("Sequence 1: ");
    System.out.println("  " + Arrays.toString(seq1));
    System.out.println("Answer 1: "); 
    System.out.println("  " + answer(seq1));
    
    System.out.println();

    System.out.println("Sequence 2: ");
    System.out.println(Arrays.toString(seq2));
    System.out.println("Answer 2: "); 
    System.out.println("  " + answer(seq2));
  }

  public static String answer(int[] seq) { 
    // turn seq into a list.
    LinkedList<Integer> list = new LinkedList<Integer>(); 

    for (int i = 0; i < seq.length; i++) { 
      list.add(seq[i]);
    }

    // call recursive funciton 
    BigInteger res = countWays(list);

    // turn answer from above to string
    return res.toString();
  }

  // ignore first element of list - this is the root.
  //    divide rest of list into two sublists, left and right. 
  //    number of ways these can be orderd is 
  //      ordering of right * ordering of left * 
  //      (right.size + left.size)! / (right.size! * left.size!)
  //
  public static BigInteger countWays(LinkedList<Integer> list) { 
    if (list.size() <= 0) { 
      return BigInteger.ONE;
    }
    // Current root is the first element in the list, remove
    int root = list.removeFirst();
    LinkedList<Integer> leftTree  = new LinkedList<Integer>(); // contains older
    LinkedList<Integer> rightTree = new LinkedList<Integer>(); // contains younger

    ListIterator<Integer> itr = list.listIterator();

    // split values and add to tree. 
    while (itr.hasNext()) { 
      int curr = itr.next();
      if (curr > root) { 
        leftTree.add(curr);
      } else {
        rightTree.add(curr);
      }
    }

    // no longer need original list 
    list.clear();
    list = null;

    // Recursive call for left and right 
    int leftTreeSize     = leftTree.size();
    int rightTreeSize    = rightTree.size();
    BigInteger leftWays  = countWays(leftTree);
    BigInteger rightWays = countWays(rightTree);
    
    // Total ways is (m+n)!/(m! * n!) * leftways * rightways
    BigInteger lrFact = factorial(leftTreeSize + rightTreeSize);
    BigInteger lFact  = factorial(leftTreeSize);
    BigInteger rFact  = factorial(rightTreeSize);

    // my variable name is great!
    BigInteger res = lrFact.divide(lFact.multiply(rFact));

    return res.multiply(leftWays.multiply(rightWays));
  }

  private static BigInteger factorial(int n) { 
    if (n <= 1) { 
      return BigInteger.ONE;
    } else { 
      BigInteger b = BigInteger.valueOf(n);
      return b.multiply(factorial(n-1));
    }
  }
}
