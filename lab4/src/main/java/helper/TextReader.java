package helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextReader {
    private List<File> resultList;
    private String output = "output.txt";

    public TextReader() {
        this.resultList = new ArrayList<>();
    }

    public File getFileToSearch(File dir) throws IOException {
        return concatenateFilesToOne(getListOfStringFiles(dir), output);
    }

    public List<File> getFilesToSearch(File dir) throws IOException {
        return getListOfStringFiles(dir);
    }

    private List<File> getListOfStringFiles(File file) throws IOException {
        processDirectory(file);
        return resultList;
    }

    private File concatenateFilesToOne(List<File> files, String outputFileName) {
        File outputFile = new File(outputFileName);

        try {
            FileWriter writer = new FileWriter(outputFile);

            for (File file : files) {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }

                bufferedReader.close();
                reader.close();
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }


    private void processDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        List<File> txtFiles = Arrays.stream(files)
                .filter(file -> (file.isFile() && file.getName().endsWith(".txt")))
                .collect(Collectors.toList());


        Arrays.stream(files)
                .filter(file -> file.isDirectory() && file.exists())
                .forEach(dir -> {
                    try {
                        processDirectory(dir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        resultList.addAll(txtFiles);

    }

    public static Set<String> getWordsFromFile(File file) {
        Stream<String> lines;
        try {
            lines = Files.lines(Path.of(file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Set<String> words = lines.parallel()
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .collect(Collectors.toSet());

        lines.close();
        return words;
    }
}
