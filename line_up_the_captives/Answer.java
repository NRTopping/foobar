import java.math.BigInteger;
import java.util.HashMap;

public class Answer { 
  private static HashMap<Integer,HashMap<Integer,BigInteger>> combRes      = new HashMap<Integer,HashMap<Integer,BigInteger>>();
  private static HashMap<Integer,BigInteger> factRes                       = new HashMap<Integer,BigInteger>();

  public static void main(String [] args) { 
    int x = 1;
    int y = 2; 
    int n = 6; 

    System.out.println("X: " + x + "  Y: " + y + "  N: " + n + "  Answer: " + answer(x,y,n));
  }


  public static String answer(int x, int y, int n) { 
    // Max possible sum from both sides is n+1, cannot be greater than this
    if (x + y > n + 1) { 
      return "0";
    } else if (x == 1 && y == n || y == 1 && x == n) { 
      return "1";
    } else {
      System.out.println("Enumerating");
      return enumerate(x, y, n).toString();
    }
  }

  private static BigInteger enumerate(int x, int y, int n) { 
    BigInteger comb      = combination(x+y, x);
    BigInteger stirling2 = s2(n, x+y);

    return stirling2.divide(comb).subtract(n);
  }

  private static BigInteger s2(int n, int k) { 
    // base cases
    if (k < 1 || n < 1 || (n == 1 && k > 1)) { 
      return BigInteger.ZERO;
    } else if (n == 1 && k == 1) { 
      return BigInteger.ONE;
    } 

    // TODO memoize 
    
    return BigInteger.valueOf(k).multiply(s2(n-1, k)).add(s2(n-1, k-1));
  }


  //private static BigInteger enumerate(int x, int y, int n) { 
    //BigInteger pipsPipes = combination(x+y, x);

    //BigInteger stirling2 = BigInteger.ZERO;

    //for (int j = 0; j < x+y; j++) { 
      //int negOnePow = 1;
      //for (int i = 0; i < (x+y) - j; i++) negOnePow *= -1;
      //int jnth      = (int) Math.pow(j, n);
      //BigInteger pp = combination(x+y, j);

      //stirling2 = stirling2.add(BigInteger.valueOf(negOnePow)
                           //.multiply(BigInteger.valueOf(jnth)
                           //.multiply(pp)));
    //}

    //BigInteger xyFact = factorial(x+y);

    //return stirling2.divide(xyFact).multiply(pipsPipes);
  //}

  private static BigInteger combination(int n, int k) { 
    if (combRes.containsKey(n)) { 
      if (combRes.get(n).containsKey(k)) { 
        return combRes.get(n).get(k);
      }
    }

    if (k < 0 || n < 0 || n < k) { 
      return BigInteger.ZERO; 
    } 
    
    // C(n,k) = n! / (k! * (n-k)!) 
    BigInteger fin =  factorial(n).divide(factorial(k).multiply(factorial(n-k)));
    
    // Store result
    HashMap<Integer, BigInteger> res;
    if (combRes.containsKey(n)) { 
      res = combRes.get(n);
    } else {
      res = new HashMap<Integer, BigInteger>();
    }
    res.put(k, fin);
    combRes.put(n, res);

    return fin;
  }

  private static BigInteger factorial(int n) { 
    if (factRes.containsKey(n)) { 
      return factRes.get(n);
    } else if (n <= 1) { 
      return BigInteger.ONE;
    } else { 
      BigInteger res = BigInteger.valueOf(n).multiply(factorial(n-1));
      factRes.put(n, res);
      return res;
    }
  }
}
