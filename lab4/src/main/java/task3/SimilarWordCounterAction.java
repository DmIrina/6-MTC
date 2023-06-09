package task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class SimilarWordCounterAction extends RecursiveAction {
    private Set<String> similarWords;
    private Path path;
    private final long threshold = 1000;
    private final long start;
    private final long end;

    public SimilarWordCounterAction(Path path) {
        similarWords = new HashSet<>();
        this.path = path;
        this.start = 0;
        this.end = countLines(path);
    }

    private SimilarWordCounterAction(Path path, Set<String> sameWords, long start, long end) {
        similarWords = sameWords;
        this.path = path;
        this.start = start;
        this.end = end;
    }

    public Set<String> getSimilarWords() {
        return similarWords;
    }


    @Override
    protected void compute() {
        long length = end - start;
        if (length <= threshold) {
            findSimilarWords();
        } else {
            ForkJoinTask.invokeAll(
                    new SimilarWordCounterAction(path, similarWords, start, start + length / 2),
                    new SimilarWordCounterAction(path, similarWords, start + length / 2, end));
        }
    }

    private void findSimilarWords() {
        try (Stream<String> linesStream = Files.lines(path)) {
            linesStream.skip(start)
                    .limit(end)
                    .flatMap(line -> Arrays.stream(line.split("(\\s|\\p{Punct})+")))
                    .forEach(similarWords::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long countLines(Path path) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
