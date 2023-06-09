package task1;

import helper.TextReader;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {
    public static void main(String[] args) throws IOException {
         String pathToResources = "src/main/resources"; //29
        // String pathToResources = "src/main/resources/files/folder"; //4
        //String pathToResources = "src/main/resources/files/files"; //22
        File resFile = new TextReader().getFileToSearch(new File(pathToResources));
        Instant start = Instant.now();


         int processors = 12;
        //int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(processors);

        WordLengthCounterAction task = new WordLengthCounterAction(resFile.toPath());

        pool.invoke(task);
        pool.shutdown();
        System.out.println("Час = " + Duration.between(start, Instant.now()).toMillis() + " ms");
        task.printResult();

        Map<Integer, AtomicInteger> map = task.getMap();

        double mx = 0.0;
        int wordsCount = 0;
        for (Map.Entry<Integer, AtomicInteger> item : map.entrySet()) {
            int value = Integer.parseInt(String.valueOf(item.getValue()));
            mx += (item.getKey() * value);
            wordsCount += value;
        }
        double mean = mx / wordsCount;

        System.out.printf("Середня довжина: %s%n", mean);

        double sum = 0.0;
        for (Map.Entry<Integer, AtomicInteger> item : map.entrySet()) {
            int value = Integer.parseInt(String.valueOf(item.getValue()));
            sum += (Math.pow(item.getKey(), 2) * value);
        }

        double D = sum / wordsCount - Math.pow(mean, 2);
        System.out.printf("Дисперсія: %s%n", D);

        double standardDeviation = Math.sqrt(D);
        System.out.printf("Стандартне відхилення: %s%n", standardDeviation);
    }
}