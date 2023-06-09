package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class WordLengthCounterAction extends RecursiveAction {
    private final Map<Integer, AtomicInteger> lengthsMap;
    private final Path path;
    private final long THRESHOLD = 10000;
    private final long start;
    private final long end;

    public WordLengthCounterAction(Path path) {
        this.lengthsMap = new ConcurrentHashMap<>();
        this.path = path;
        this.start = 0;
        this.end = countLines(path);
    }

    private WordLengthCounterAction(Path path, Map<Integer, AtomicInteger> wordLengthQuantity, long start, long end) {
        this.lengthsMap = wordLengthQuantity;
        this.path = path;
        this.start = start;
        this.end = end;
    }


    @Override
    protected void compute() {
        long length = end - start;
        if (length <= THRESHOLD) {
            fillLengthsMap();
        } else {
            ForkJoinTask.invokeAll(
                    new WordLengthCounterAction(path, lengthsMap, start, start + length / 2),
                    new WordLengthCounterAction(path, lengthsMap, start + length / 2, end));
        }
    }

    private long countLines(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillLengthsMap() {
        try (Stream<String> linesStream = Files.lines(path)) {
            linesStream
                    .skip(start)
                    .limit(end)
                    .flatMap(line -> Arrays.stream(line.split("(\\s|\\p{Punct})+")))
                    .map(String::length)
                    .forEach(number -> {
                        lengthsMap.putIfAbsent(number, new AtomicInteger(0));
                        lengthsMap.get(number).incrementAndGet();
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, AtomicInteger> getMap() {
        return lengthsMap;
    }

    public void printResult() {
        for (Map.Entry<Integer, AtomicInteger> item : lengthsMap.entrySet()) {
            System.out.printf("%s: %s%n", item.getKey(), item.getValue());
        }
    }
}
