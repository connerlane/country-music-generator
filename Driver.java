import java.util.*;
import java.io.*;
import java.time.*;
public class Driver {

public static int K = 11;
public static int LENGTH = 1000;
public static String FILENAME = "combined.txt";
   public static void main(String args[]) {
      File f = new File(FILENAME);
      MarkovModel m = new MarkovModel(K, f);
      String output= m.randomKgram();

      for (int i = 0; i < LENGTH; i++) {
         char next = m.nextChar(output.substring(output.length() - K));
      	if (next == '\u0000') {
            output += m.randomKgram();
         }
         else {
            output += next;
         }
      }
      System.out.println(output);
      try {
         PrintWriter writer = new PrintWriter(FILENAME + "_rand_" + LocalDateTime.now(), "UTF-16");
         writer.println(output);
         writer.close();
  	   }
      catch (IOException e) {
      }
   }
}