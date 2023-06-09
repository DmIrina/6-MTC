package task4;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Task4 {
    public static void main(String[] args) {
        Instant start = Instant.now();
        String pathToResources = "src/main/resources";
        String[] searchedWords = new String[]{
                "tech",
                "java",
                "math"};

        List<Appearance> results = Collections.synchronizedList(new ArrayList<>());
        Set<String> searchedWordsSet = new HashSet<>(List.of(searchedWords));
        File file = new File(pathToResources);
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        if (file.isDirectory()) {
            forkJoinPool.invoke(new WordSearcher(results, searchedWordsSet, file));
        } else {
            System.out.println("Given path isn't a directory");
        }

        forkJoinPool.shutdown();

        System.out.println("time = " + Duration.between(start, Instant.now()).toMillis() + " ms");

        WordSearcher.printResult();
    }
}
