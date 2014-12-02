public class Answer {   

  public static void main(String[] args ) { 
    String [] res = answer(Integer.parseInt(args[0]));

    for (int i = 0; i < res.length; i++) { 
      System.out.println("Power: " + pow(3,i) + " Location: " + res[i]); 
    }
  }

  // X is weight on left side.
  public static String[] answer(int x) { 
    int carry = 0; // keeps track of what we need to carry to next place

    int tern = decToTern(x);
    String ternString = String.valueOf(tern);
    char [] ternArray = ternString.toCharArray();
    // Reverse the array 
    for (int i = 0; i < ternArray.length / 2; i++) { 
      char temp = ternArray[i];
      ternArray[i] = ternArray[ternArray.length - 1 - i];
      ternArray[ternArray.length - 1 - i] = temp;
    }
    
    for (int i = 0; i < ternArray.length; i++){
      int temp = Character.getNumericValue(ternArray[i]) + carry;

      // Carry is non-zero, follow these
      switch(temp) { 
        case 3: 
          ternArray[i] = '-'; 
          carry = 1; 
          break;
        case 2:
          ternArray[i] = 'L';
          carry = 1; 
          break;
        case 1: 
          ternArray[i] = 'R';
          carry = 0; 
          break;
        default:
          ternArray[i] = '-'; 
          carry = 0; 
          break;
      }
    }
    int stringArrayLength = ternArray.length; 
    if (carry == 1) { 
      stringArrayLength += 1; 
    }

    // Copy to string array 
    String [] result = new String [stringArrayLength];
    for (int i = 0; i < ternArray.length; i++) { 
      result[i] = String.valueOf(ternArray[i]);
    }

    if (result.length > ternArray.length) { 
      result[result.length - 1] = "R";
    }

    // 
    return result;
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
