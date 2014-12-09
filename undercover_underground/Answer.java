import java.math.BigInteger;
import java.util.HashMap;

public class Answer { 
  public static void main(String [] args) {
    int n1 = 2; 
    int k1 = 1; // 1
    System.out.println("[1] : [" + answer(n1, k1) + "]");

    int n2 = 4;
    int k2 = 3; // 16
    System.out.println("[16] : [" + answer(n2, k2) + "]");

    int n3 = 4;
    int k3 = 6; // 1
    System.out.println("[1] : [" + answer(n3, k3) + "]");

    int n4 = 3; 
    int k4 = 2; // 3
    System.out.println("[3] : [" + answer(n4, k4) + "]");

    int n5 = 5; 
    int k5 = 5; // 
    System.out.println("[222] : [" + answer(n5, k5) + "]");

    int n6 = 6; 
    int k6 = 5; 
    System.out.println("[1296] : [" + answer(n6,k6) + "]");
  }

  // 2 <= N <= 20
  // N-1 <= K <= (N * (N - 1)) / 2
  public static String answer(int N, int K) { 
    return enumerate(N, K).toString(); 
  }

  private static HashMap<Integer,HashMap<Integer,BigInteger>> enumerateRes = new HashMap<Integer,HashMap<Integer,BigInteger>>();
  private static HashMap<Integer,HashMap<Integer,BigInteger>> combRes      = new HashMap<Integer,HashMap<Integer,BigInteger>>();
  private static HashMap<Integer,BigInteger> factRes                       = new HashMap<Integer,BigInteger>();

  private static BigInteger enumerate(int n, int k) { 
    // check if solution exists 
    if (enumerateRes.containsKey(n)) { 
      if (enumerateRes.get(n).containsKey(k)) { 
        return enumerateRes.get(n).get(k);
      }
    }

    // Check known-quantities
    if (k < n - 1 || k > n * (n-1) / 2) { 
      // out of range
      return BigInteger.ZERO; 
    } else if (n == 1 || n == 2) { 
      return BigInteger.ONE; 
    }  else if (k == n - 1) {
      // Cayley's formula
      return BigInteger.valueOf(n).pow(n-2);
    }


    // Recurssion 
    BigInteger d_n = totalGraphsNK(n,k); // total graphs, including unconnected ones
    BigInteger sum = BigInteger.ZERO; 

    for (int i = 1; i < n; i++) { 
      for (int j = i-1; j < Math.min(combination(i,2).intValue(),k) + 1; j++) { 
        BigInteger comb       = combination(n-1, i-1);
        BigInteger rec        = enumerate(i, j);
        BigInteger totalGraph = totalGraphsNK(n-i, k-j);
        
        sum = sum.add(comb.multiply(rec.multiply(totalGraph))); 
      }
    }

    // insert value into enumerateRes before returning
    HashMap<Integer, BigInteger> res;
    if (enumerateRes.containsKey(n)) { 
      res = enumerateRes.get(n);
    } else {
      res = new HashMap<Integer, BigInteger>();
    }
    res.put(k, d_n.subtract(sum));
    enumerateRes.put(n, res);
    return d_n.subtract(sum); 
  }

  // returns C(C(n,2),k)
  private static BigInteger totalGraphsNK(int n, int k) { 
    BigInteger nC2 = combination(n, 2);
    return combination(nC2.intValue(), k);
  }

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
