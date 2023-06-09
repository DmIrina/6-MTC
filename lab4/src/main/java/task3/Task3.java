package task3;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Task3 {
    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        String pathToResources = "src/main/resources/files/folder";

        String pathToResources1 = "src/main/resources/files/files/lookatme";
        String pathToResources2 = "src/main/resources/files/files/iwantitall";
        String pathToResources3 = "src/main/resources/files/files/text43.txt";
        String path = "src/main/resources/files/files/The Wonderful Wizard of Oz by L. Frank Baum.txt";
        String path1 = "src/main/resources/files/text41.txt";

        ForkJoinPool pool = new ForkJoinPool();

        SimilarWordCounterAction task1 = new SimilarWordCounterAction(Path.of(pathToResources1));
        SimilarWordCounterAction task2 = new SimilarWordCounterAction(Path.of(pathToResources2));
        SimilarWordCounterAction task3 = new SimilarWordCounterAction(Path.of(pathToResources3));

        pool.invoke(task1);
        pool.invoke(task2);
        pool.invoke(task3);

        Set<String> words1 = task1.getSimilarWords();
        Set<String> words2 = task2.getSimilarWords();
        Set<String> words3 = task3.getSimilarWords();

        Set<String> result = words1.parallelStream()
                .filter(words2::contains)
                .filter(words3::contains)
                .collect(Collectors.toCollection(TreeSet::new));


        //result.forEach(System.out::println);
        System.out.println(result);


        System.out.println("time = " + Duration.between(start, Instant.now()).toMillis() + " ms");
    }
}
