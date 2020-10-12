import java.io.File;
import java.io.IOException;
import java.util.*;

class Main {
  static final String STOPFILE = "../stop_words.txt";
  static final String INPUTFILE = "../pride-and-prejudice.txt";
  static final int K = 25;

  public static void main(String[] args) {
    String inputPath = (args == null || args.length == 0) ? INPUTFILE : args[0];
    
    try {

      //create file read scanner
      Scanner inputScanner = null, stopScanner = null;
      inputScanner = new Scanner(new File(inputPath));
      stopScanner = new Scanner(new File(STOPFILE));

      //read stop_words.txt
      Set<String> stopWords = new HashSet<>();
      stopScanner.useDelimiter(",");
      while (stopScanner.hasNext()) {
        stopWords.add(stopScanner.next());
      }
      stopScanner.close();

      //read input file
      Map<String, Integer> inputMap = new HashMap<>();
      PriorityQueue<String> heap = new PriorityQueue<>((e1, e2) -> inputMap.get(e1) - inputMap.get(e2));
      while (inputScanner.hasNext()) {
        String line = inputScanner.next().toLowerCase();
        String[] words = line.split("[^a-z]+");
        for (String i : words) {
          if (i.length() < 2 || stopWords.contains(i)) continue;
          inputMap.put(i, inputMap.getOrDefault(i, 0) + 1);
        }
      }
      inputScanner.close();

      //find most K
      for (String i : inputMap.keySet()) {
        heap.offer(i);
        if (heap.size() > K) heap.poll();
      }

      List<String> result = new LinkedList<>();
      while (!heap.isEmpty()) result.add(heap.poll());
      Collections.reverse(result);

      for (String i : result) {
        System.out.printf("%s  -  %d\n", i, inputMap.get(i));
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}