import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * MarkovModel.java
 * Creates an order K Markov model of the supplied source text.
 * The value of K determines the size of the "kgrams" used
 * to generate the model. A kgram is a sequence of k consecutive
 * characters in the source text.
 *
 * @author     Conner Lane (you@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2015-11-16
 *
 */


public class MarkovModel {

   private HashMap<String, String> model = new HashMap<String, String>();
   private int size;
   private int k;
   private String[] kgrams;
   private Random kgramChooser;
   private Random charChooser;
   private String firstKgram;
   /**
    * Construct the order K model of the file sourceText.
    */
   public MarkovModel(int K, File sourceFile) {
      String sourceText = "";
      k = K;
      try {
      
         Scanner scan = new Scanner(sourceFile);
         sourceText += scan.nextLine();
         while (scan.hasNext()) {
            String nextLine = scan.nextLine();
            if (!nextLine.equals(""))
               sourceText += "\n" + nextLine;
         }
      }
      catch (Exception e) {
      
      }
      int count = 1;
      size = sourceText.length();
      
      kgrams = new String[size - K + 1];
      if (size == K) {
         kgrams[0] = sourceText;
         model.put(sourceText, null);
         return;
      }
      String following = sourceText.substring(K, K+1);
      
      String kgram = sourceText.substring(0, K);
      kgrams[0] = kgram;
      model.put(kgram, following);
      while (count < size - K + 1) {
        
         kgram = sourceText.substring(count, K + count);
         kgrams[count] = kgram;
         if (count == size - K) {
            model.put(kgram, null);
            break;
         }
         following = sourceText.substring(count + K, K + count + 1);
         
         count++;
         if (model.get(kgram) != null)
            model.put(kgram, following.concat((String)model.get(kgram)));
         else 
            model.put(kgram, following);
      }
   }


   /**
    * Construct the order K model of the string sourceText.
    */
   public MarkovModel(int K, String sourceText) {
      k = K;
      int count = 1;
      size = sourceText.length();
      String following = sourceText.substring(K, K+1);
      kgrams = new String[size - K];
      String kgram = sourceText.substring(0, K);
      kgrams[0] = kgram;
      model.put(kgram, following);
      while (count < size - K) {
         following = sourceText.substring(count + K, K + count + 1);
         kgram = sourceText.substring(count, K + count);
         kgrams[count] = kgram;
         count++;
         if (model.get(kgram) != null)
            model.put(kgram, following.concat((String)model.get(kgram)));
         else 
            model.put(kgram, following);
      }
   }


   /** Return the first kgram found in the source text. */
   public String firstKgram() {
      return kgrams[0];
   }


   /** Return a random kgram from the source text. */
   public String randomKgram() {
      kgramChooser = new Random();
      if (size - k == 0)
         return firstKgram();
      return kgrams[kgramChooser.nextInt(size - k + 1)];
   }


   /**
    * Return a single character that follows the given
    * kgram in the source text. Select this character
    * according to the probability distribution of all
    * characters the follow the given kgram in the
    * source text.
    */
   public char nextChar(String kgram) {
      String possibleChars = model.get(kgram);
      if (possibleChars == null) {
         return '\u0000';
      }
      if (possibleChars.length() == 1)
         return possibleChars.charAt(0);
      charChooser = new Random();
      int index = charChooser.nextInt(possibleChars.length());
      char out = possibleChars.charAt(index);
      
      return out;
   }

}
