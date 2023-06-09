package task4;

import helper.TextReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class WordSearcher extends RecursiveTask<List<Appearance>> {
    private static List<Appearance> resFiles;
    private final Set<String> searchedWords;
    private final File file;

    public WordSearcher(List<Appearance> resFiles, Set<String> searchedWords, File file) {
        WordSearcher.resFiles = resFiles;
        this.searchedWords = searchedWords;
        this.file = file;
    }

    @Override
    protected List<Appearance> compute() {
        try {
            processDirectory(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return resFiles;
    }

    private void processDirectory(File file) throws FileNotFoundException {
        if (file.isDirectory() && file.exists()) {
            List<WordSearcher> forks = new ArrayList<>();

            for (File f : Objects.requireNonNull(file.listFiles())) {
                WordSearcher fileSearch = new WordSearcher(resFiles, searchedWords, f);
                fileSearch.fork();
                forks.add(fileSearch);
            }
            forks.forEach(ForkJoinTask::join);

        } else {
            Set<String> wordsFromFile = TextReader.getWordsFromFile(file);
            wordsFromFile.retainAll(searchedWords);
            resFiles.add(new Appearance(file.getName(), wordsFromFile));
        }
    }


    public static void printResult() {
        System.out.println("size: " + resFiles.size());
        for (Appearance file : resFiles) {
            if (file.getWords().size() != 0) {
                System.out.println(file);
            }
        }
        // resFiles.forEach(System.out::println);
    }
}
