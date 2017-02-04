import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounter {

    private Map wordMap = new HashMap<String, Integer>();

    public WordCounter(String fileName) {
            printAllTheWords(fileName);
            createHashMapOfWordCount(fileName);
            printHashMapOfWordsCount();
            printHashMapOfWordsCountInOrderOfFreq();
    }


    private void createHashMapOfWordCount(String fileName) {
        try (Stream<String> lineStream = Files.lines(Paths.get(fileName))) {

            lineStream.map(line -> Arrays.asList(line.split(" ")))
                    .flatMap(list -> list.stream())
                    .forEach(word -> updateMap(word));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printHashMapOfWordsCount() {
        System.out.println("printHashMapOfWordsCount::::\n");

        wordMap.forEach((word,count)-> System.out.println(word + " : "+ count));
    }

    private void printHashMapOfWordsCountInOrderOfFreq() {
        System.out.println("printHashMapOfWordsCountInOrderOfFreq::::\n");

        List <WordCount> list = new ArrayList<>();
        wordMap.forEach((word,count)-> list.add(new WordCount(word.toString(), new Integer(count.toString()))));

        Comparator<WordCount> descendingComparator = (w1, w2)-> w2.count.compareTo(w1.count);
        list.stream().sorted(descendingComparator).forEach(wc -> System.out.println(wc.word + " ::: "+ wc.count));

    }

    //Class to hold Word Counts
    class WordCount{
        String word;
        Integer count;

        WordCount(String w, Integer c)
        {
            word = w;
            count = c;
        }
    }

    private void updateMap(String word) {
        String toLowerCaseWord = word.trim().toLowerCase();
        if(wordMap.containsKey(toLowerCaseWord)){
            wordMap.put(toLowerCaseWord, (Integer)wordMap.get(toLowerCaseWord) + 1);
        }else{
            wordMap.put(toLowerCaseWord, 1);
        }

    }

    private void printAllTheWords(String fileName) {

        // Tip: Very useful explanation (Diff between map/flatmap)
        // https://javarevisited.blogspot.in/2016/03/difference-between-map-and-flatmap-in-java8.html

        // If map() uses a function, which, instead of returning a single value
        // returns a Stream of values than you have a Stream of Stream of values,
        // and flatmap() is used to flat that into a Stream of values.

        System.out.println("PRINTING CONTENTS OF FILE: "+ fileName);

        try (Stream<String> lineStream = Files.lines(Paths.get(fileName))) {

            lineStream.map(line -> Arrays.asList(line.split(" ")))
                    .flatMap(list -> list.stream())
                    .collect(Collectors.toList())
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("==============================");

}


    public static void main(String[] args) {
        new WordCounter("input-file.txt");
    }
}