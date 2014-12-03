import java.util.Set;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.lang.StringBuilder;
import java.util.Stack;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Arrays;


public class Answer {   
  public static void main(String [] args) { 
    String [] input1 = {"y", "z", "xy"};
    String [] input2 = {"ba", "ab", "cb"};
    
    System.out.println("First Dictionary:");
    System.out.println("\t" + Arrays.toString(input1));
    System.out.println("Resulting order:");
    System.out.println("\t" + answer(input1));

    System.out.println();

    System.out.println("Second Dictionary:");
    System.out.println("\t" + Arrays.toString(input2));
    System.out.println("Resulting order:");
    System.out.println("\t" + answer(input2));
  }

  public static String answer(String[] words) { 
    if (words.length == 1) { 
      // remove the duplicate characters from the string, return the string
      return uniqueLetters(words);
    }

    // Otherwise need to create a graph, hooray
    Graph g = new Graph(uniqueLetters(words).length());
  
    // Process the adjacent pairs of words, and create the graph
    for (int i = 0; i < words.length - 1; i++) { 
      // Get current two words 
      String w1 = words[i]; 
      String w2 = words[i+1];

      // Find first mismatching letter
      for (int j = 0; j < Math.min(w1.length(), w2.length()); j++) { 
        if (w1.charAt(j) != w2.charAt(j)) { 
          g.addEdge(w1.charAt(j), w2.charAt(j));
          break;
        }
      }
    }

    return g.topologicalSort(); 
  } 

  private static String uniqueLetters(String [] words) { 
    StringBuilder builder = new StringBuilder();
    for (String s: words) { 
      builder.append(s);
    }

    String concat = builder.toString();
    StringBuilder resBuild = new StringBuilder(); 
    boolean[] charExists   = new boolean[Character.MAX_VALUE];

    for (int i = 0; i < concat.length(); i++) { 
      char ch = concat.charAt(i);
      if (!charExists[ch]) { 
        charExists[ch] = true;
        resBuild.append(ch);
      }
    }
    return resBuild.toString();
  }

  private static class Graph { 
    private int m_numVert; 
    private HashMap<Character, LinkedList<Character>> m_adj; 

    public Graph(int numVert){
      m_numVert = numVert;
      m_adj = new HashMap<Character, LinkedList<Character>>();
    }

    public void addEdge (char a, char b) { 
      // make sure that b is already a node
      if (m_adj.get(b) == null) { 
        m_adj.put(b, new LinkedList<Character>());
      }
      
      // get list associated with a 
      LinkedList<Character> aList = m_adj.get(a);

      System.out.println("Adding edge: " + a + "->" + b);

      if (aList == null) { 
        aList = new LinkedList<Character>();
        System.out.println("creating new list for " + a);
      }

      // add b to list, if not already in it
      if (!aList.contains(b)) { 
        aList.add(b);
      }

      // update list
      m_adj.put(a, aList);
    }

    private void topologicalSortHelper(Character c, Set<Character>  visited, Stack<Character> stack) { 
      // add current to visited.
      visited.add(c);
      
      System.out.println("TSH: testing " + c);

      // Iterate through all of the values in c's adjacency list
      ListIterator<Character> itr = m_adj.get(c).listIterator(0);

      while (itr.hasNext()) { 
        Character curr = itr.next();
        if (!visited.contains(curr)) { 
          topologicalSortHelper(curr, visited, stack);
        }
      }
      stack.push(c); // Push the current value onto the stack.
    }

    public String topologicalSort() { 
      Stack<Character> stack = new Stack<Character>();

      // Create an empty hash set that will store the nodes we have visited.
      Set<Character> visited = new HashSet<Character>();

      // get the set of keys from the adjacency list, so that we can iterate
      // thorugh them
      Set<Character> keys     = m_adj.keySet();
      Iterator<Character> itr = keys.iterator();
      
      while (itr.hasNext()) {
        Character curr = itr.next();
        System.out.println("TS: Current is = " + curr);
        if (!visited.contains(curr)) { 
          topologicalSortHelper(curr, visited, stack);
        }
      }

      // create a string from the resulting stack
      StringBuilder sb = new StringBuilder();

      while (!stack.empty()) { 
        sb.append(stack.pop());
      }

      return sb.toString();
    }
  }
}
