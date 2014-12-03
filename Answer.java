public class Answer {   

  public static void main(String[] args ) { 
    String [] res = answer(Integer.parseInt(args[0]));

    for (int i = 0; i < res.length; i++) { 
      System.out.println("Power: " + pow(3,i) + " Location: " + res[i]); 
    }
  }

  public static String[] answer(int x) { 
    // convert decimal number to ternary 
    int tern = decToTern(x);

    // Convert tern to balanced ternary 
    String bTern = convertToBalancedTernary(x);

    System.out.println("Ternary:          " + tern);
    System.out.println("Balanced Ternary: " + bTern);

    // Convert string into array
    char [] charArray = bTern.toCharArray();

    String [] result = new String [charArray.length];

    for (int i = 0; i < result.length; i++) { 
      switch(charArray[i]) { 
        case '-':
          result[i] = "L";
          break;
        case '+':
          result[i] = "R";
          break;
        case '0':
          result[i] = "-";
          break;
        default:
          break;
      }
    }
    return result;
  }

  private static String convertToBalancedTernary(int x) {
    if (x == 0) { 
      return "";
    }

    int remainder = x % 3; 
    switch (remainder) { 
      case 2: 
        return "-" + convertToBalancedTernary((x+1)/3);
      case 1: 
        return "+" + convertToBalancedTernary(x/3);
      default: 
        return "0" + convertToBalancedTernary(x/3);
    }
  }
  
  /*
   * Convert a decimal integer into a ternary one
   */
  public static int decToTern(int x) {
    if (x == 0) { 
      return 0;
    }

    int i = 0; 
    int result = 0; 

    while (x > 0) { 
      // find largest power of 3 still less than the number
      while (x / pow(3, i) >= 1) { 
        i++; 
      }
      
      i--; // i is one too large from loop. 

      // Only 2, 1, or 0 can fit in the number. Don't care about 0, so check
      // others
      int power = pow(3, i);
      if (2 * power <= x) { 
        result += 2 * pow(10, i); 
        x -= 2 * power;
      } else { 
        result += 1 * pow(10, i);
        x -= power;
      }

      i = 0; 
    }
    return result;
  }

  /*
   * Calculates the xth power of n
   */
  private static int pow(int n, int x) { 
    int res = 1; 

    for (int i = 0; i < x; i++) {
      res *= n;
    }

    return res;
  }
}
